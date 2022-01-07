package com.example.realbdstate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignInUserModel(
    var uId: String = "null",
    var name: String = "null",
    var email: String = "null",
    var imageUrl: String = "null"
): Parcelable