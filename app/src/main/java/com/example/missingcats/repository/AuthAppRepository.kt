package com.example.missingcats.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthAppRepository {
    private var auth = Firebase.auth
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val errorMessageLiveData:MutableLiveData<String> = MutableLiveData()
    val currentUser = auth.currentUser

    init {

    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = currentUser
            } else {
                errorMessageLiveData.value = task.exception?.message.toString()
            }
        }
    }
    fun logOut(){
        auth.signOut()
        userLiveData.value = auth.currentUser
    }
}