package com.hongstudio.kakaosearchimage.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import com.hongstudio.kakaosearchimage.ui.ImagesListAdapter
import com.hongstudio.kakaosearchimage.ui.imagedetail.ImageDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {
    private val viewModel: FavoriteViewModel by viewModels { FavoriteViewModel.Factory }

    private val adapter = ImagesListAdapter(
        onClickFavorite = ::deleteFavorite,
        onClickItem = ::onClickItem
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        launch {
            viewModel.favoriteItems.collectLatest {
                adapter.setData(it)
            }
        }
    }

    private fun deleteFavorite(item: DocumentEntity) {
        viewModel.deleteFavorite(item)
    }

    private fun onClickItem(documentEntity: DocumentEntity) {
        startActivity(ImageDetailActivity.newIntent(context ?: return, documentEntity))
    }
}
