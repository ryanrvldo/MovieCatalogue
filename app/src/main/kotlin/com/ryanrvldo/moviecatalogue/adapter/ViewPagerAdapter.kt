package com.ryanrvldo.moviecatalogue.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragmentList = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}