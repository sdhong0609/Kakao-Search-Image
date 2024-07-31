package com.hongstudio.kakaosearchimage.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected val launcher = registerForActivityResult(StartActivityForResult()) {
        setData()
    }

    protected val uiScope = lifecycleScope + CoroutineExceptionHandler { _, _ ->
        Toast.makeText(context, "에러 발생", Toast.LENGTH_SHORT).show()
    }

    abstract fun bindView(view: View)
    abstract fun setData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }
}
