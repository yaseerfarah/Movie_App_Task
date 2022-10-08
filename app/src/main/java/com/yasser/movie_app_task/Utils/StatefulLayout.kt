package com.yasser.movie_app_task.Utils

import android.view.View
import android.widget.TextView
import com.yasser.bosta_task.Utils.ViewUtils.Companion.hide
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show

class StatefulLayout(private val contentLayout:View,private val errorLayout:View,private val progressLayout:View,private val errorText:TextView,) {

    fun showContent(){
        contentLayout.show()
        errorLayout.hide()
        progressLayout.hide()
    }
    fun showError(message:String){
        contentLayout.hide()
        errorLayout.show()
        progressLayout.hide()
        errorText.text=message
    }
    fun showLoading(){
        contentLayout.hide()
        errorLayout.hide()
        progressLayout.show()
    }


}