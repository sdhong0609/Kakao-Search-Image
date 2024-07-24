package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlin.concurrent.thread

class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null
    private val adapter = ImagesListAdapter(::deleteFavorite, ::onClickItem)

    override fun bindView(view: View) {
        binding = FragmentFavoriteBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        setData()
    }

    override fun setData() {
        thread {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            val favorites = dao.getAll()

            activity?.runOnUiThread {
                adapter.setData(favorites)
            }
        }
    }

    private fun deleteFavorite(documentEntity: DocumentEntity, position: Int) {
        thread {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            dao.delete(documentEntity)
            val dataset = dao.getAll()

            activity?.runOnUiThread {
                adapter.setData(dataset)
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
