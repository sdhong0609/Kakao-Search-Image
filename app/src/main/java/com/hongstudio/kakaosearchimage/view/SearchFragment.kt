package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import com.hongstudio.kakaosearchimage.service.RetrofitObject
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    layoutId = R.layout.fragment_search,
    binder = FragmentSearchBinding::bind
) {

    private val adapter = ImagesListAdapter(
        onClickFavorite = ::onClickFavorite,
        onClickItem = ::onClickItem
    )
    private var dataset: List<DocumentEntity> = emptyList()
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        setData()
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

        launch {
            val response = RetrofitObject.searchImageService.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            dataset = response.documents.map { it.toEntity() }
            setData()
        }
    }

    override fun setData() {
        launch {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            val favorites = dao.getAll()

            val updatedDataset = dataset.map { documentEntity ->
                if (favorites.any { it.thumbnailUrl == documentEntity.thumbnailUrl }) {
                    documentEntity.copy(isFavorite = true)
                } else {
                    documentEntity
                }
            }

            adapter.setData(updatedDataset)
        }
    }

    private fun onClickFavorite(documentEntity: DocumentEntity, position: Int) {
        val updatedDocumentEntity = documentEntity.copy(isFavorite = !documentEntity.isFavorite)

        launch {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            if (updatedDocumentEntity.isFavorite) {
                dao.insert(updatedDocumentEntity)
            } else {
                dao.delete(updatedDocumentEntity)
            }

            adapter.updateItem(updatedDocumentEntity, position)
        }
    }

    private fun onClickItem(documentEntity: DocumentEntity) {
        launcher.launch(ImageDetailActivity.newIntent(requireContext(), documentEntity))
    }
}
