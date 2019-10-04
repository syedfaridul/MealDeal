package com.example.mealdeal.di.module

import android.app.Application
import android.content.Context
import com.example.mealdeal.data.remote.FirebaseSource
import com.example.mealdeal.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class ApplicationModule {



    @Provides
    internal fun providesUserRepository(firebaseSource: FirebaseSource
    )= UserRepository(firebaseSource)

    @Provides
    internal fun providesfirebaseSource(): FirebaseSource =
        FirebaseSource()


}