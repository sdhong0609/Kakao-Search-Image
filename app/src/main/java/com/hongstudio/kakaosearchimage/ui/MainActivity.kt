package com.hongstudio.kakaosearchimage.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.ui.base.BaseActivity
import com.hongstudio.kakaosearchimage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : com.hongstudio.ui.base.BaseActivity<ActivityMainBinding>(
    inflater = ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.page_search_title)
                1 -> getString(R.string.page_favorite_title)
                else -> ""
            }
        }.attach()
    }
}
