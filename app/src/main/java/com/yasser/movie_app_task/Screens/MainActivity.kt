package com.yasser.movie_app_task.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yasser.movie_app_task.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}