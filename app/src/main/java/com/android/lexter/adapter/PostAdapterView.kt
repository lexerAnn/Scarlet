package com.android.lexter.adapter

import android.app.Activity
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.lexter.MainActivity
import com.android.lexter.PostActivity
import com.android.lexter.util.Post
import com.android.lexter.R
import com.android.lexter.util.User
import com.android.lexter.util.convertLongToTime
import com.android.lexter.util.debugger
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_post.view.*
import kotlinx.android.synthetic.main.viewitem.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostViewHolder (val v: View): RecyclerView.ViewHolder(v)
class PostAdapterView(private val host: Activity):ListAdapter<Post,PostViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<Post> =

            object: DiffUtil.ItemCallback<Post>() {
                override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                    newItem.Postid==oldItem.Postid


                override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                    newItem == oldItem
            }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
LayoutInflater.from(parent.context).inflate(
    R.layout.viewitem,parent,false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val posts=getItem(position)
        holder.v.userBlogTitle.text=posts.PostTitle
        holder.v.userBlogBody.text=posts.PostText
        holder.v.userpost_time.text = convertLongToTime(System.currentTimeMillis())
        holder.v.userpostname.text=posts.sendername





      if (posts.Blogimage.isNullOrEmpty())holder.v.userblogImage.visibility=View.GONE
else{
    Glide.with(host).asBitmap()
        .load(posts.Blogimage)
        .placeholder(R.color.content_placeholder)
        .error(R.color.content_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(holder.v.userblogImage)
      }
        holder.v.setOnClickListener {
            host.startActivity(
                Intent(host,
                    PostActivity::class.java
                ).apply { putExtra(PostActivity.EXTRA_POST,posts)

                }
            )
        }


    }



}