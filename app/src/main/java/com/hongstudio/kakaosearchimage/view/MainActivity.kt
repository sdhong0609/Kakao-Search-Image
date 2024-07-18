package com.hongstudio.kakaosearchimage.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val searchFragment =
            supportFragmentManager.findFragmentByTag(SearchFragment.TAG) ?: SearchFragment()
        val favoriteFragment =
            supportFragmentManager.findFragmentByTag(FavoriteFragment.TAG) ?: FavoriteFragment()

        if (supportFragmentManager.fragments.isEmpty()) {
            setCurrentFragment(searchFragment, SearchFragment.TAG)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pageSearch -> setCurrentFragment(searchFragment, SearchFragment.TAG)
                R.id.pageFavorite -> setCurrentFragment(favoriteFragment, FavoriteFragment.TAG)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }
}
