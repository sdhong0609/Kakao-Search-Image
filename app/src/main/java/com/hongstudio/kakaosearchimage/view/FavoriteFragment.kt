package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseFragment
import com.hongstudio.kakaosearchimage.database.DocumentDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.model.Document
import kotlin.concurrent.thread

class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null
    private val adapter = ImagesAdapter(::onClickFavorite)

    override fun bindView(view: View) {
        binding = FragmentFavoriteBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewImageList?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewImageList?.adapter = adapter

        thread {
            val dao = DocumentDatabase.getDatabase(requireContext()).documentDao()
            val dataset = dao.getAll()

            activity?.runOnUiThread {
                adapter.setData(dataset)
            }
        }
    }

    private fun onClickFavorite(data: Document, position: Int) {
        data.isFavorite = !data.isFavorite
        adapter.notifyItemChanged(position)

        thread {
            val dao = DocumentDatabase.getDatabase(requireContext()).documentDao()
            if (data.isFavorite) {
                dao.insert(data)
            } else {
                dao.delete(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
