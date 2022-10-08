package com.yasser.movie_app_task.Screens.Splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.movie_app_task.R
import com.yasser.movie_app_task.databinding.FragmentMovieListBinding
import com.yasser.movie_app_task.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {



    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = { inflater,parent,bool->
            FragmentSplashBinding.inflate(inflater,parent,bool)
        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navigate(R.id.action_splashFragment_to_homeFragment)
        },2000)

    }



}