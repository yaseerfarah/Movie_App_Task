package com.yasser.movie_app_task.Screens.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.UiStatus
import com.yasser.bosta_task.Utils.ViewUtils.Companion.hide
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.R
import com.yasser.movie_app_task.Screens.Details.MovieDetailsFragment
import com.yasser.movie_app_task.Utils.StatefulLayout
import com.yasser.movie_app_task.ViewModels.MovieViewModel
import com.yasser.movie_app_task.ViewModels.ViewModelFactory
import com.yasser.movie_app_task.databinding.FragmentHomeBinding
import com.yasser.movie_app_task.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment :BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var movieViewModel:MovieViewModel
    lateinit var viewPagerAdapter:ViewPagerAdapter
    lateinit var statefulLayout: StatefulLayout
    private var listOfCategoryWithMoviesModel:MutableList<CategoryWithMoviesModel> = mutableListOf()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater,parent,bool->
            FragmentHomeBinding.inflate(inflater,parent,bool)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        movieViewModel.getAllMovieWithCategory()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                movieViewModel.uiState.collect {
                    when(it){
                        is UiStatus.Success->{
                            initViewPagerWithTabLayout(it.info)
                           statefulLayout.showContent()
                        }
                        is UiStatus.Loading->{
                            statefulLayout.showLoading()

                        }
                        is UiStatus.NetworkConnectionFailed->{
                            statefulLayout.showError(requireActivity().getString(R.string.check_connection))

                        }
                        is UiStatus.Failed->{
                            statefulLayout.showError(requireActivity().getString(R.string.check_connection))
                        }
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    fun initViewPagerWithTabLayout(list:List<CategoryWithMoviesModel>){
        listOfCategoryWithMoviesModel.clear()
        listOfCategoryWithMoviesModel.addAll(list)
        viewPagerAdapter= ViewPagerAdapter(this,listOfCategoryWithMoviesModel)
        mBinding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(
            mBinding.tabLayout, mBinding.viewPager
        ) { tab, position ->
            tab.text = listOfCategoryWithMoviesModel[position].categoryModel.name
        }.attach()
    }


    fun initializeView(){

        statefulLayout=StatefulLayout(mBinding.contentLayout,mBinding.errorLayout.errorContent,mBinding.progressBar,mBinding.errorLayout.errorBody)

           mBinding.errorLayout.tryBtn.setOnClickListener {
               movieViewModel.getAllMovieWithCategory()
           }


    }



}