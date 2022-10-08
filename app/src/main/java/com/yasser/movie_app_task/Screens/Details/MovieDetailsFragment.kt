package com.yasser.movie_app_task.Screens.Details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.ViewUtils
import com.yasser.bosta_task.Utils.ViewUtils.Companion.loadImage
import com.yasser.movie_app_task.Constants
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.Model.MovieModel
import com.yasser.movie_app_task.R
import com.yasser.movie_app_task.Screens.MovieList.Adapter.MovieItemAdapter
import com.yasser.movie_app_task.Screens.MovieList.MovieListFragment
import com.yasser.movie_app_task.ViewModels.MovieViewModel
import com.yasser.movie_app_task.ViewModels.ViewModelFactory
import com.yasser.movie_app_task.databinding.FragmentHomeBinding
import com.yasser.movie_app_task.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieModel: MovieModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = { inflater,parent,bool->
            FragmentMovieDetailsBinding.inflate(inflater,parent,bool)
        }


    companion object{
        const val MODEL_OBJECT="Model"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        arguments?.let {
            movieModel=it.getParcelable(MODEL_OBJECT)!!
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    fun initializeView(){
        movieModel.backdrop_path?.let {
            mBinding.coverImage.loadImage(Constants.ImageLink+it,null,null)
        }
        mBinding.movieImage.loadImage(Constants.ImageLink+movieModel.poster_path,null,null)

        mBinding.content.tvTitle.text=movieModel.title
        mBinding.content.tvDescription.text=movieModel.overview
        mBinding.content.tvRate.text=movieModel.vote_average
        mBinding.content.ratingBar.rating=(movieModel.vote_average.toFloat()/2)
        mBinding.content.tvPeople.text=movieModel.popularity

        movieModel.genre_ids.map {
            val chip=Chip(requireContext())
            chip.text=movieViewModel.getCategoryModelById(it).name
            chip.isClickable=false
            mBinding.content.chipGroup.addView(chip)
        }

        mBinding.backBtn.setOnClickListener {
            navigateUp()
        }




    }


}