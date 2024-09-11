package com.hongstudio.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.ui.DocumentListAdapter
import com.hongstudio.favorite.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.ui.imagedetail.ImageDetailActivity
import com.hongstudio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {
    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter = DocumentListAdapter(
        onClickFavorite = ::deleteFavorite,
        onClickItem = ::onClickItem
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        viewModel.favoriteItems.observe {
            adapter.submitList(it)
        }
    }

    private fun deleteFavorite(item: DocumentModel) {
        viewModel.deleteFavorite(item)
    }

    private fun onClickItem(item: DocumentModel) {
        startActivity(ImageDetailActivity.newIntent(context ?: return, item))
    }
}
