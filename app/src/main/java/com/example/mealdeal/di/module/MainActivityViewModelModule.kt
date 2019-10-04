package com.example.mealdeal.di.module

import androidx.lifecycle.ViewModel
import com.example.mealdeal.auth.vmfactory.AuthViewModel
import com.example.mealdeal.foodie.viewmodel.MainActivityViewModel
import com.example.mealdeal.vmfactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindsComicViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

}