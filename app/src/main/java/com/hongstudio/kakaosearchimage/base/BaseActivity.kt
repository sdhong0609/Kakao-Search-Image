package com.hongstudio.kakaosearchimage.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<VB : ViewBinding>(
    private val inflater: (LayoutInflater) -> VB
) : AppCompatActivity(), CoroutineScope {

    protected lateinit var binding: VB

    override val coroutineContext: CoroutineContext =
        lifecycleScope.coroutineContext + CoroutineExceptionHandler { _, _ ->
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding)
    }

    private fun setContentView(binding: ViewBinding) {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    protected fun <T> Flow<T>.observe(onChanged: (T) -> Unit) {
        asLiveData().observe(this@BaseActivity) {
            onChanged(it)
        }
    }
}
