package com.hongstudio.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.hongstudio.common.model.DocumentListItemType
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.ui.DocumentListAdapter
import com.hongstudio.common.ui.EndlessRecyclerViewScrollListener
import com.hongstudio.search.databinding.FragmentSearchBinding
import com.hongstudio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    layoutId = R.layout.fragment_search,
    binder = FragmentSearchBinding::bind
) {
    private val viewModel: SearchViewModel by viewModels()

    private val adapter = DocumentListAdapter(
        onClickFavorite = ::onClickFavorite,
        onClickItem = ::onClickItem
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.editTextSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getSearchedItems(binding?.editTextSearch?.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        binding?.imageButtonSearch?.setOnClickListener {
            viewModel.getSearchedItems(binding?.editTextSearch?.text.toString())
        }

        val scrollListener = object : EndlessRecyclerViewScrollListener(
            layoutManager = binding?.recyclerViewSearch?.layoutManager as GridLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadNextData(page)
            }
        }


        binding?.recyclerViewSearch?.run {
            adapter = this@SearchFragment.adapter

            (layoutManager as GridLayoutManager).spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when ((adapter as DocumentListAdapter).getItemViewType(position)) {
                        DocumentListItemType.PROGRESSBAR.ordinal -> 2
                        else -> if (position % 5 == 4) 2 else 1
                    }
                }
            }

            addOnScrollListener(scrollListener)
        }

        viewModel.uiState.observe {
            when (it) {
                is SearchUiState.Idle -> setVisibility(
                    progressBarVisible = false,
                    noResultVisible = false,
                    recyclerViewVisible = false
                )

                is SearchUiState.Loading -> setVisibility(
                    progressBarVisible = true,
                    noResultVisible = false,
                    recyclerViewVisible = false
                )

                is SearchUiState.Empty -> setVisibility(
                    progressBarVisible = false,
                    noResultVisible = true,
                    recyclerViewVisible = false
                )

                is SearchUiState.Success -> {
                    adapter.submitList(it.items)
                    setVisibility(
                        progressBarVisible = false,
                        noResultVisible = false,
                        recyclerViewVisible = true
                    )
                }

                is SearchUiState.Error -> {
                    setVisibility(
                        progressBarVisible = false,
                        noResultVisible = false,
                        recyclerViewVisible = false
                    )
                    Toast.makeText(context, it.error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setVisibility(
        progressBarVisible: Boolean,
        noResultVisible: Boolean,
        recyclerViewVisible: Boolean
    ) {
        binding?.progressBarSearch?.visibility = if (progressBarVisible) View.VISIBLE else View.GONE
        binding?.textViewNoResult?.visibility = if (noResultVisible) View.VISIBLE else View.GONE
        binding?.recyclerViewSearch?.visibility = if (recyclerViewVisible) View.VISIBLE else View.GONE
    }

    private fun onClickFavorite(item: DocumentModel) {
        viewModel.onClickFavorite(item)
    }

    private fun onClickItem(item: DocumentModel) {
        val uri = "seongdeok://image/detail".toUri()
            .buildUpon()
            .appendQueryParameter("item", Json.encodeToString(item))
            .build()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
