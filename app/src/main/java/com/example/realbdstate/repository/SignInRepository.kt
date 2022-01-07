package com.example.realbdstate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.realbdstate.utils.Constant.TAG
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInRepository {

    private val firebaseAuth = Firebase.auth

    //firebase sign in with google
    fun firebaseSignInWithGoogle(authCredential: AuthCredential?): MutableLiveData<String> {
        Log.d(TAG, "firebaseSignInWithGoogle: $authCredential")
        val authMutableLiveData = MutableLiveData<String>()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (authCredential != null) {
                    firebaseAuth.signInWithCredential(authCredential).await()
                    withContext(Dispatchers.Main){
                        authMutableLiveData.postValue("Successful")
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    authMutableLiveData.postValue(e.toString())
                }
            }
        }

        return authMutableLiveData


    }
}