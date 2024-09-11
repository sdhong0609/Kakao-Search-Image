package com.hongstudio.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes layoutId: Int,
    private val binder: (View) -> VB
) : Fragment(layoutId), CoroutineScope {

    protected var binding: VB? = null

    override val coroutineContext: CoroutineContext by lazy {
        viewLifecycleOwner.lifecycleScope.coroutineContext + CoroutineExceptionHandler { _, _ ->
            Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
        }
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            binding = binder(view)
        }
        return view
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected fun <T> Flow<T>.observe(onChanged: (T) -> Unit) {
        asLiveData().observe(viewLifecycleOwner) {
            onChanged(it)
        }
    }
}
