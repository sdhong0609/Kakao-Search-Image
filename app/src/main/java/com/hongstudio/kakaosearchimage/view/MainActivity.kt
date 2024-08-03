package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseActivity
import com.hongstudio.kakaosearchimage.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    inflater = ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        val fragments = if (supportFragmentManager.fragments.isEmpty()) {
            listOf(SearchFragment(), FavoriteFragment())
        } else {
            supportFragmentManager.fragments
        }

        val viewPagerAdapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.page_search_title)
                1 -> getString(R.string.page_favorite_title)
                else -> ""
            }
        }.attach()
    }
}
