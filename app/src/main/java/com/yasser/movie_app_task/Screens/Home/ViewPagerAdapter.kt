package com.yasser.movie_app_task.Screens.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.Screens.MovieList.MovieListFragment


class ViewPagerAdapter(fragmentActivity: Fragment,val listOfCategoryWithMoviesModel: List<CategoryWithMoviesModel>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return MovieListFragment.newInstance(listOfCategoryWithMoviesModel[position])
    }

    override fun getItemCount(): Int {
        return listOfCategoryWithMoviesModel.size
    }


}