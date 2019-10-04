package com.example.mealdeal.di.component

import android.app.Application
import com.example.mealdeal.base.BaseApplication
import com.example.mealdeal.di.builder.ActivityBuilder
import com.example.mealdeal.di.module.ApplicationModule
import com.example.mealdeal.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@SuppressWarnings("unchecked")
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ContextModule::class,
    ActivityBuilder::class
    ])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: BaseApplication)



}
