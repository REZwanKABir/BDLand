package com.example.realbdstate.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivitySignInBinding
import com.example.realbdstate.utils.Constant.TAG
import com.example.realbdstate.viewModel.ViewModelSignIn
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignIn_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModelSignIn: ViewModelSignIn
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isInternetConnected = false
    private var internetDisposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelInit()
        signInMethod()

        binding.apply {
            signInWithGoogle.setOnClickListener {
              when(isInternetConnected){
                  true ->{
                      signIn()
                      return@setOnClickListener
                  }
                  else->{
                      Toast.makeText(
                          this@SignIn_Activity,
                          "Check Your Internet Connection",
                          Toast.LENGTH_SHORT
                      ).show()
                      return@setOnClickListener
                  }
              }
            }
        }
    }

    private fun signInMethod() {
        try {
            Log.d(TAG, "signInMethod: is called")
            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
        }catch (e:Exception){
            Toast.makeText(this, "Error1 $e", Toast.LENGTH_SHORT).show()
        }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "code: is called")
        val intent: Intent? = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {

            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "google account: ${account.email}")
            Toast.makeText(this, "Google Account ${account.email}", Toast.LENGTH_SHORT)
                .show()
            getGoogleAuthCredential(account)

        } catch (e: ApiException) {
            Toast.makeText(this, "Sign In Failed!", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "ERROR3: $e", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "error: $e")

        }
    }

    private fun getGoogleAuthCredential(account: GoogleSignInAccount) {
        Log.d(TAG, "getGoogleAuthCredential: is called")
        try {
            val googleTokenId = account.idToken
            val authCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
            signInWithGoogle(authCredential)
        }catch (e:Exception){
            Toast.makeText(this, "Error4 $e", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "getGoogleAuthCredential: error4 $e")
        }
    }

    private fun signInWithGoogle(authCredential: AuthCredential) {
        viewModelSignIn.signInWithGoogle(authCredential)
        viewModelSignIn.authenticateUserLiveData?.observe(this,{ auth->

            when(auth){
                "Successful"->{
                    startActivity(Intent(this,UserDetails::class.java))
                }
                else->{
                    Toast.makeText(this, "ERROR5: $auth", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "signInWithGoogle: error5 $auth")
                }
            }
        })
    }

    private fun signIn() {
        resultLauncher.launch(Intent(googleSignInClient.signInIntent))

    }

    private fun viewModelInit() {
        viewModelSignIn = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[ViewModelSignIn::class.java]

    }

    override fun onResume() {
        super.onResume()
        //checking device connection to internet
        internetDisposable = ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->
                isInternetConnected = isConnectedToInternet
            }
    }

    override fun onPause() {
        super.onPause()
        safelyDispose(internetDisposable)
    }

    private fun safelyDispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }
}