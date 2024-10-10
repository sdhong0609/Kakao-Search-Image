package com.hongstudio.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.hongstudio.common.DefaultJson
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.ui.DocumentListAdapter
import com.hongstudio.favorite.databinding.FragmentFavoriteBinding
import com.hongstudio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString

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

        (binding?.recyclerViewFavorite?.layoutManager as GridLayoutManager).spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 5 == 4) 2 else 1
            }
        }
        binding?.recyclerViewFavorite?.adapter = adapter

        viewModel.uiState.observe {
            when (it) {
                is FavoriteUiState.Loading -> setVisibility(
                    progressBarVisible = true,
                    noFavoriteVisible = false,
                    recyclerViewVisible = false
                )

                is FavoriteUiState.Empty -> setVisibility(
                    progressBarVisible = false,
                    noFavoriteVisible = true,
                    recyclerViewVisible = false
                )

                is FavoriteUiState.Success -> {
                    setVisibility(
                        progressBarVisible = false,
                        noFavoriteVisible = false,
                        recyclerViewVisible = true
                    )
                    adapter.submitList(it.items)
                }

                is FavoriteUiState.Error -> {
                    setVisibility(
                        progressBarVisible = false,
                        noFavoriteVisible = false,
                        recyclerViewVisible = false
                    )
                    Toast.makeText(context, it.error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setVisibility(
        progressBarVisible: Boolean,
        noFavoriteVisible: Boolean,
        recyclerViewVisible: Boolean
    ) {
        binding?.progressBarFavorite?.visibility = if (progressBarVisible) View.VISIBLE else View.GONE
        binding?.textViewNoFavorite?.visibility = if (noFavoriteVisible) View.VISIBLE else View.GONE
        binding?.recyclerViewFavorite?.visibility = if (recyclerViewVisible) View.VISIBLE else View.GONE
    }

    private fun deleteFavorite(item: DocumentModel) {
        viewModel.deleteFavorite(item)
    }

    private fun onClickItem(item: DocumentModel) {
        val uri = "seongdeok://image/detail".toUri()
            .buildUpon()
            .appendQueryParameter("item", DefaultJson.encodeToString(item))
            .build()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
