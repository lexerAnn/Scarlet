package com.android.lexter.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Uu(
    var uid: String="",
    var userEmail: String="",
    var userName:String="",
    var userPassword:String="'"


): Parcelable {
    constructor(): this("","","","")

}