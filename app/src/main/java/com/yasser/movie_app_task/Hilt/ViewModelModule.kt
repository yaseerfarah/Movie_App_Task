package com.yasser.movie_app_task.Hilt

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yasser.movie_app_task.Data.Local.RoomImplement
import com.yasser.movie_app_task.Data.Local.SharedPrefsHelper
import com.yasser.movie_app_task.Data.Remote.ApiEndpoints
import com.yasser.movie_app_task.Data.Remote.RetrofitImplement
import com.yasser.movie_app_task.ViewModels.MovieViewModel
import com.yasser.movie_app_task.ViewModels.ViewModelFactory
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@InstallIn(SingletonComponent::class)
@Module
class ViewModelModule {
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(
        RetentionPolicy.RUNTIME
    )
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)


    @Singleton
    @Provides
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }


    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    fun movieViewModel(@ApplicationContext context: Context,roomImplement: RoomImplement,retrofitImplement: RetrofitImplement,sharedPrefsHelper: SharedPrefsHelper): ViewModel {
        return MovieViewModel(context,roomImplement,retrofitImplement,sharedPrefsHelper)
    }

}