package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.launch

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {

    private val adapter = ImagesListAdapter(::deleteFavorite, ::onClickItem)
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        setData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        setData()
    }

    override fun setData() {
        launch {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            val favorites = dao.getAll()

            adapter.setData(favorites)
        }
    }

    private fun deleteFavorite(documentEntity: DocumentEntity, position: Int) {
        launch {
            val dao = FavoriteDatabase.getDatabase(requireContext()).documentDao()
            dao.delete(documentEntity)
            val dataset = dao.getAll()

            adapter.setData(dataset)
        }
    }

    private fun onClickItem(documentEntity: DocumentEntity) {
        launcher.launch(ImageDetailActivity.newIntent(requireContext(), documentEntity))
    }
}
