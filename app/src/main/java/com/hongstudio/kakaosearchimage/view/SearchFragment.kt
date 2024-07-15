package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.DocumentDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.hongstudio.kakaosearchimage.model.Document
import com.hongstudio.kakaosearchimage.model.GetSearchedImagesResponse
import com.hongstudio.kakaosearchimage.service.RetrofitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    private val adapter = ImagesAdapter(::onClickFavorite)

    override fun bindView(view: View) {
        binding = FragmentSearchBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.editTextSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchedImages(binding?.editTextSearch?.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        binding?.imageButtonSearch?.setOnClickListener {
            getSearchedImages(binding?.editTextSearch?.text.toString())
        }

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter
    }

    private fun getSearchedImages(keyword: String) {
        if (keyword.isBlank()) return

        RetrofitObject.searchImageService.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            .enqueue(object : Callback<GetSearchedImagesResponse> {
                override fun onResponse(
                    call: Call<GetSearchedImagesResponse>,
                    response: Response<GetSearchedImagesResponse>
                ) {
                    val dataset = response.body()?.documents ?: emptyList()
                    thread {
                        val dao = DocumentDatabase.getDatabase(requireContext()).documentDao()
                        val favorites = dao.getAll()

                        val updatedDataset = dataset.map { document ->
                            if (favorites.any { it.thumbnailUrl == document.thumbnailUrl }) {
                                document.copy(isFavorite = true)
                            } else {
                                document
                            }
                        }

                        activity?.runOnUiThread {
                            adapter.setData(updatedDataset)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<GetSearchedImagesResponse>,
                    throwable: Throwable
                ) {
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun onClickFavorite(document: Document, position: Int) {
        val updatedDocument = document.copy(isFavorite = !document.isFavorite)
        adapter.setUpdatedDocument(updatedDocument, position)

        thread {
            val dao = DocumentDatabase.getDatabase(requireContext()).documentDao()
            if (updatedDocument.isFavorite) {
                dao.insert(updatedDocument)
            } else {
                dao.delete(updatedDocument)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
