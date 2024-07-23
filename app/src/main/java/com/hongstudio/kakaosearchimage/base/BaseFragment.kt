package com.hongstudio.kakaosearchimage.base

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

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
