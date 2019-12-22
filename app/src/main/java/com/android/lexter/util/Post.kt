package com.android.lexter.util

import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    var PostTitle: String,
    var PostText: String,
    var Postid: String,
    var publisher: String,
    var Blogimage: String?="",
    var timestamp: Long = System.currentTimeMillis(),
    val sendername: String? = ""

    ): Parcelable {
    constructor(): this("","","","","")

    companion object {
        fun toHashMap(post: Post)= hashMapOf<String, Any?>(
            "postTitle" to post.PostTitle,
            "postText" to post.PostText,
            "postid" to post.Postid,
            "publisher" to post.publisher,
            "blogimage" to post.Blogimage,
            "timestamp" to post.timestamp,
            "sender" to post.sendername
        )
    }
}