package com.example.mealdeal.di.builder

import com.example.mealdeal.auth.ui.AuthActivity
import com.example.mealdeal.di.module.ApplicationModule
import com.example.mealdeal.di.module.AuthViewModelModule
import com.example.mealdeal.di.module.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.mealdeal.auth.ui.SignUpActivity
import com.example.mealdeal.di.module.MainActivityViewModelModule
import com.example.mealdeal.foodie.ui.MainActivity


@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = [ApplicationModule::class,
        ViewModelFactoryModule::class,AuthViewModelModule::class])
    internal abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [ApplicationModule::class,
        ViewModelFactoryModule::class,AuthViewModelModule::class])
    internal abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector(modules = [ApplicationModule::class,
        ViewModelFactoryModule::class,MainActivityViewModelModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}