package com.yasser.movie_app_task.Screens.MovieList

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.ViewUtils
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.Model.MovieModel
import com.yasser.movie_app_task.R
import com.yasser.movie_app_task.Screens.Details.MovieDetailsFragment
import com.yasser.movie_app_task.Screens.MovieList.Adapter.MovieItemAdapter
import com.yasser.movie_app_task.ViewModels.MovieViewModel
import com.yasser.movie_app_task.ViewModels.ViewModelFactory
import com.yasser.movie_app_task.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import eg.com.invive.barberia_ktx.Utils.DetailsItemDecoration
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    lateinit var categoryWithMoviesModel: CategoryWithMoviesModel

    val adapter: MovieItemAdapter by lazy {
        MovieItemAdapter(
            requireContext(),
            categoryWithMoviesModel.moviesList.toMutableList(),
            object : ItemClickListener<MovieModel> {
                override fun onItemClick(view: View, item: MovieModel) {
                    val bundle=Bundle()
                    bundle.putParcelable(MovieDetailsFragment.MODEL_OBJECT,item)
                    //navigate(R.id.action_global_movieDetailsFragment,bundle)
                    findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_movieDetailsFragment2,bundle)
                }
            }) }


    companion object{
        const val MODEL_OBJECT="Model"

        fun newInstance(categoryWithMoviesModel: CategoryWithMoviesModel):MovieListFragment{
            val fragment = MovieListFragment()
            val args = Bundle()
            args.putParcelable(MODEL_OBJECT, categoryWithMoviesModel)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        arguments?.let {
            categoryWithMoviesModel=it.getParcelable(MODEL_OBJECT)!!
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()


    }

    fun initializeView(){
        ViewUtils.initializeRecyclerView(
            mBinding.recyclerView,
            adapter,
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL),
            null,
            null
        )
    }


}