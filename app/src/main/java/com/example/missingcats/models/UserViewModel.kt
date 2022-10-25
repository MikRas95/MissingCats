package com.example.missingcats.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingcats.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel: ViewModel() {
    private val repository = AuthAppRepository()
    val mutableLiveData: MutableLiveData<FirebaseUser> = repository.userLiveData

}

