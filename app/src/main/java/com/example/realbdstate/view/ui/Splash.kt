package com.example.realbdstate.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realbdstate.databinding.ActivitySplashBinding
import com.example.realbdstate.view.user.SignIn_Activity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var internetDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startApps()

    }

    override fun onResume() {
        super.onResume()

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->

                when(isConnectedToInternet){
                    true->{
                        //startApps()
                    }else ->{
                    Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

    private fun startApps() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    this,
                    SignIn_Activity::class.java
                )
            )
            finish()

        }, 1000)
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