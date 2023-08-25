package com.a2k.expensemanager

import android.app.Application
import com.google.firebase.FirebaseApp

class APP : Application(){
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}