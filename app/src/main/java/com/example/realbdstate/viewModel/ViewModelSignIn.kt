package com.example.realbdstate.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.realbdstate.repository.SignInRepository
import com.google.firebase.auth.AuthCredential

class ViewModelSignIn(application: Application):AndroidViewModel(application) {

    private var signInRepository = SignInRepository()
    var authenticateUserLiveData: LiveData<String>? = null

    fun signInWithGoogle(authCredential: AuthCredential){
        authenticateUserLiveData = signInRepository.firebaseSignInWithGoogle(authCredential)
    }

}