package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.database.DocumentDatabase
import com.hongstudio.kakaosearchimage.databinding.FragmentFavoriteBinding
import com.hongstudio.kakaosearchimage.model.Document
import kotlin.concurrent.thread

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thread {
            val dao = DocumentDatabase.getDatabase(requireContext()).documentDao()
            val dataset = dao.getAll()

            adapter = ImagesAdapter(dataset, ::onClickFavorite)
            activity?.runOnUiThread {
                binding.recyclerViewImageList.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewImageList.adapter = adapter
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
        _binding = null
    }
}
