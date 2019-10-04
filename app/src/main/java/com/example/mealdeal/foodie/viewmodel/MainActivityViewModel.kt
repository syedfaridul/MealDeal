package com.example.mealdeal.foodie.viewmodel

import android.view.View
import com.example.mealdeal.base.BaseViewModel
import com.example.mealdeal.data.repository.UserRepository
import com.example.mealdeal.util.startLoginActivity
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(
private val repository: UserRepository
) :BaseViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View) {
        repository.logout()
        view.context.startLoginActivity()
    }

}