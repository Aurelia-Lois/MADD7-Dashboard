package com.example.madd7_ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var navHome: View
    private lateinit var navPrompts: View
    private lateinit var navFeed: View
    private lateinit var navStreak: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHome = findViewById(R.id.nav_home)
        navPrompts = findViewById(R.id.nav_prompts)
        navFeed = findViewById(R.id.nav_feed)
        navStreak = findViewById(R.id.nav_streak)

        navHome.setOnClickListener { switchTab(0) }
        navPrompts.setOnClickListener { switchTab(1) }
        navFeed.setOnClickListener { switchTab(2) }
        navStreak.setOnClickListener { switchTab(3) }

        if (savedInstanceState == null) {
            switchTab(0)
        }
    }

    fun switchTab(index: Int) {
        navHome.isSelected = index == 0
        navPrompts.isSelected = index == 1
        navFeed.isSelected = index == 2
        navStreak.isSelected = index == 3

        val fragment: Fragment = when (index) {
            0 -> HomeFragment()
            1 -> PromptsFragment()
            2 -> FeedFragment()
            3 -> StreakFragment()
            else -> HomeFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}