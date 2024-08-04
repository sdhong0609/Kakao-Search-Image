package com.hongstudio.kakaosearchimage.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.databinding.FragmentSearchBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import com.hongstudio.kakaosearchimage.ui.ImagesListAdapter
import com.hongstudio.kakaosearchimage.ui.imagedetail.ImageDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    layoutId = R.layout.fragment_search,
    binder = FragmentSearchBinding::bind
) {
    private val viewModel: SearchViewModel by viewModels { SearchViewModel.Factory }

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

        launch {
            viewModel.searchedItems.collectLatest {
                adapter.setData(it)
            }
        }
    }

    private fun onClickFavorite(item: DocumentEntity) {
        viewModel.onClickFavorite(item)
    }

    private fun onClickItem(item: DocumentEntity) {
        startActivity(ImageDetailActivity.newIntent(requireContext(), item))
    }
}
