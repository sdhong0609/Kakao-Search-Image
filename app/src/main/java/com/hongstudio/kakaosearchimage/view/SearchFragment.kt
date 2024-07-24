package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import com.hongstudio.kakaosearchimage.model.GetSearchedImagesResponse
import com.hongstudio.kakaosearchimage.service.RetrofitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    private val adapter = ImagesListAdapter(::onClickFavorite, ::onClickItem)
    private var dataset: List<DocumentEntity> = emptyList()

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
                    dataset = response.body()?.documents?.map { it.toEntity() } ?: emptyList()
                    setData()
                }

                override fun onFailure(
                    call: Call<GetSearchedImagesResponse>,
                    throwable: Throwable
                ) {
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun setData() {
        thread {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            val favorites = dao.getAll()

            val updatedDataset = dataset.map { documentEntity ->
                if (favorites.any { it.thumbnailUrl == documentEntity.thumbnailUrl }) {
                    documentEntity.copy(isFavorite = true)
                } else {
                    documentEntity
                }
            }

            activity?.runOnUiThread {
                adapter.setData(updatedDataset)
            }
        }
    }

    private fun onClickFavorite(documentEntity: DocumentEntity, position: Int) {
        val updatedDocumentEntity = documentEntity.copy(isFavorite = !documentEntity.isFavorite)

        thread {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            if (updatedDocumentEntity.isFavorite) {
                dao.insert(updatedDocumentEntity)
            } else {
                dao.delete(updatedDocumentEntity)
            }

            activity?.runOnUiThread {
                adapter.setUpdatedDocument(updatedDocumentEntity, position)
            }
        }
    }

    private fun onClickItem(documentEntity: DocumentEntity) {
        launcher.launch(ImageDetailActivity.newIntent(requireContext(), documentEntity))
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
