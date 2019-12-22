package com.android.lexter.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid:String,
    var userName:String,
    var userEmail:String,
    var userPassword:String

):Parcelable{
    constructor():this ("","","","")

}
