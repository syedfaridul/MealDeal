package com.example.mealdeal.base

import com.example.mealdeal.di.component.DaggerApplicationComponent
import dagger.android.support.DaggerApplication


@SuppressWarnings("unchecked")
class BaseApplication : DaggerApplication() {


    private val component = DaggerApplicationComponent.builder()
        .application(this)
        .build()
    override fun applicationInjector()=component

}