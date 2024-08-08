package com.hongstudio.kakaosearchimage.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hongstudio.kakaosearchimage.ui.favorite.FavoriteFragment
import com.hongstudio.kakaosearchimage.ui.search.SearchFragment

class ViewPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> = listOf(SearchFragment(), FavoriteFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
