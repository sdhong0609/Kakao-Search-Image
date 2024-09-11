package com.hongstudio.kakaosearchimage.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.local.model.LocalDocument
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.hongstudio.kakaosearchimage.ui.ImagesListAdapter
import com.hongstudio.kakaosearchimage.ui.imagedetail.ImageDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    layoutId = R.layout.fragment_search,
    binder = FragmentSearchBinding::bind
) {
    private val viewModel: SearchViewModel by viewModels()

    private val adapter = ImagesListAdapter(
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

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        viewModel.searchedItems.observe {
            adapter.submitList(it)
        }
    }

    private fun onClickFavorite(item: com.hongstudio.local.model.LocalDocument) {
        viewModel.onClickFavorite(item)
    }

    private fun onClickItem(item: com.hongstudio.local.model.LocalDocument) {
        startActivity(ImageDetailActivity.newIntent(context ?: return, item))
    }
}
