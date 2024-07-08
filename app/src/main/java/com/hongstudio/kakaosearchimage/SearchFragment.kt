package com.hongstudio.kakaosearchimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val json = Json { ignoreUnknownKeys = true }
    private lateinit var adapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onClickSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.imageButtonSearch.setOnClickListener {
            onClickSearch()
        }
    }

    private fun onClickSearch() {
        val keyword = binding.editTextSearch.text.toString()
        if (keyword.isNotBlank()) {
            getSearchedImages(keyword)
        }
    }

    private fun getSearchedImages(keyword: String) {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
        val service = retrofit.create(SearchImageService::class.java)
        service.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            .enqueue(object : Callback<GetSearchedImagesResponse> {
                override fun onResponse(
                    call: Call<GetSearchedImagesResponse>,
                    response: Response<GetSearchedImagesResponse>
                ) {
                    val dataset = response.body()!!.documents
                    adapter = ImagesAdapter(dataset, ::onClickFavorite)
                    binding.recyclerViewImageList.layoutManager = LinearLayoutManager(context)
                    binding.recyclerViewImageList.adapter = adapter
                }

                override fun onFailure(
                    call: Call<GetSearchedImagesResponse>,
                    throwable: Throwable
                ) {
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun onClickFavorite(data: Document, position: Int) {
        data.isFavorite = !data.isFavorite
        adapter.notifyItemChanged(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
