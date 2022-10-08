package com.yasser.movie_app_task.Screens.MovieList.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Utils.BaseDiffUtil
import com.yasser.bosta_task.Utils.ViewUtils.Companion.loadImage
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.movie_app_task.Constants
import com.yasser.movie_app_task.Model.MovieModel
import com.yasser.movie_app_task.R
import com.yasser.movie_app_task.databinding.MovieItemBinding


class MovieItemAdapter(
    val context: Context,
    var movieList: MutableList<MovieModel>,
    val onItemClicked: ItemClickListener<MovieModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PhotoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.movie_item,parent,false))



    override fun getItemCount(): Int =movieList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoHolder).bind(context,movieList[holder.bindingAdapterPosition],onItemClicked)
    }


    fun updateList(newPhotoList: List<MovieModel>) {
        val placeInfoBaseDiffUtil: BaseDiffUtil =
            BaseDiffUtil(context, this.movieList, newPhotoList)
        val diffResult = DiffUtil.calculateDiff(placeInfoBaseDiffUtil)
        this.movieList.clear()
        this.movieList.addAll(newPhotoList)
        diffResult.dispatchUpdatesTo(this)
    }

//////////////////////////////////////////////////////////////////////////////////
    class PhotoHolder(var movieBinding: MovieItemBinding) : RecyclerView.ViewHolder(movieBinding.root) {
        fun bind(context: Context,movieModel: MovieModel, onItemClicked: ItemClickListener<MovieModel>) {

            movieBinding.progress.show()
            movieBinding.image.loadImage(Constants.ImageLink+movieModel.poster_path,null,movieBinding.progress)
            movieBinding.title.text=movieModel.title.trim()
            movieBinding.container
                .setOnClickListener { onItemClicked.onItemClick(movieBinding.root,movieModel) }

        }
    }


}