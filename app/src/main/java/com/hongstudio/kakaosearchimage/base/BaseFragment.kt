package com.hongstudio.kakaosearchimage.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId), CoroutineScope {

    override val coroutineContext: CoroutineContext =
        viewLifecycleOwner.lifecycleScope.coroutineContext + CoroutineExceptionHandler { _, _ ->
            Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
        }

    protected val launcher = registerForActivityResult(StartActivityForResult()) {
        setData()
    }

    abstract fun bindView(view: View)
    abstract fun setData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }
}
