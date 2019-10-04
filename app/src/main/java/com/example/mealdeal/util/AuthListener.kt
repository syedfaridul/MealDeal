package com.example.mealdeal.util

interface AuthListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}