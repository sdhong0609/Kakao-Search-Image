package com.hongstudio.kakaosearchimage.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.ui.ImagesListAdapter
import com.hongstudio.kakaosearchimage.ui.imagedetail.ImageDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {
    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter = ImagesListAdapter(
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

    private fun deleteFavorite(item: LocalDocument) {
        viewModel.deleteFavorite(item)
    }

    private fun onClickItem(localDocument: LocalDocument) {
        startActivity(ImageDetailActivity.newIntent(context ?: return, localDocument))
    }
}
