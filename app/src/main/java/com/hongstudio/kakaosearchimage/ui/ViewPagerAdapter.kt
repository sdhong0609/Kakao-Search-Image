package com.hongstudio.kakaosearchimage.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hongstudio.favorite.FavoriteFragment
import com.hongstudio.search.SearchFragment

class ViewPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> = listOf(com.hongstudio.search.SearchFragment(), com.hongstudio.favorite.FavoriteFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
