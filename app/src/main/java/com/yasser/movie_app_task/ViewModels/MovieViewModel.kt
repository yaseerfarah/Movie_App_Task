package com.yasser.movie_app_task.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.yasser.bosta_task.Utils.UiStatus
import com.yasser.movie_app_task.Data.Local.RoomImplement
import com.yasser.movie_app_task.Data.Local.SharedPrefsHelper
import com.yasser.movie_app_task.Data.Remote.RetrofitImplement
import com.yasser.movie_app_task.Model.CategoryModel
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception
import java.net.ConnectException

class MovieViewModel(val context: Context,val roomImplement: RoomImplement,val retrofitImplement: RetrofitImplement,val sharedPrefsHelper: SharedPrefsHelper) : ViewModel(){



    private val _uiState = MutableStateFlow<UiStatus<List<CategoryWithMoviesModel>>>(UiStatus.Loading<List<CategoryWithMoviesModel>>())
    val uiState: StateFlow<UiStatus<List<CategoryWithMoviesModel>>> = _uiState
    private var listOfCategoryWithMoviesModel:MutableList<CategoryWithMoviesModel> = mutableListOf()
    private var listOfCategoryModel:MutableList<CategoryModel> = mutableListOf()


    private suspend fun getAllNewData():List<CategoryWithMoviesModel>{
        var listOfMoviesWithCategory= mutableListOf<CategoryWithMoviesModel>()
        var listOfMoviesWithCategoryJob= mutableListOf<Deferred<CategoryWithMoviesModel?>>()

        coroutineScope {
            listOfCategoryModel.addAll(async{getAllCategoriesData()}.await())
            for (category in listOfCategoryModel){
                listOfMoviesWithCategoryJob.add(async { retrofitImplement.getMoviesByCategory(category)})
            }
            listOfMoviesWithCategoryJob.awaitAll()
        }

        listOfMoviesWithCategory.addAll( listOfMoviesWithCategoryJob.map {
            it.getCompleted()!!
        })
        return listOfMoviesWithCategory



    }

    private suspend fun getAllCategoriesData():List<CategoryModel>{
        return retrofitImplement.getAllCategories()
    }



    private suspend fun saveAllData(listOfCategoryWithMoviesModel: List<CategoryWithMoviesModel>){
        roomImplement.saveAllData(listOfCategoryWithMoviesModel)
        val current=System.currentTimeMillis()
        sharedPrefsHelper.save(SharedPrefsHelper.LAST_UPDATE,System.currentTimeMillis())


    }

    private suspend fun clearAllData(){
        roomImplement.clearAllData()
    }
    private fun checkValidateData():Boolean{
        return System.currentTimeMillis()<(sharedPrefsHelper.get<Long>(SharedPrefsHelper.LAST_UPDATE,0)+SharedPrefsHelper.MILLIS_UNTIL_PROMPT)
    }

    fun getAllMovieWithCategory(){
        viewModelScope.launch(Dispatchers.Default){
            try {
                _uiState.value=UiStatus.Loading<List<CategoryWithMoviesModel>>()
                if (checkValidateData()){
                    listOfCategoryModel.addAll(roomImplement.getAllCategories())
                    listOfCategoryWithMoviesModel.addAll(roomImplement.getAllCategoriesWithMovies())
                }else{
                    clearAllData()
                    listOfCategoryWithMoviesModel.addAll(withContext(Dispatchers.IO){getAllNewData()})
                    saveAllData(listOfCategoryWithMoviesModel)
                }

                _uiState.value=UiStatus.Success<List<CategoryWithMoviesModel>>(info = listOfCategoryWithMoviesModel)

            }catch (ex:Exception){
                if (ex is ConnectException){
                    _uiState.value=UiStatus.NetworkConnectionFailed<List<CategoryWithMoviesModel>>(message = ex.message.toString())
                }else{
                    _uiState.value=UiStatus.Failed<List<CategoryWithMoviesModel>>(message = ex.message.toString())
                }


            }

        }

    }


    fun getCategoryModelById(categoryId:Int):CategoryModel{
        return listOfCategoryModel.first {
            it.id == categoryId
        }
    }


}