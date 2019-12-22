package com.android.lexter.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.lexter.MainActivity
import com.android.lexter.PostActivity
import com.android.lexter.R
import com.android.lexter.util.Post
import com.android.lexter.util.debugger
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.userpost.view.*


class UserPostViewHolder (val v: View): RecyclerView.ViewHolder(v)
class UserPostAdapter(private val host: Activity): ListAdapter<Post, UserPostViewHolder>(UserPostAdapter.DIFF_UTIL){
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<Post> =

            object: DiffUtil.ItemCallback<Post>() {
                override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                    newItem.Postid==oldItem.Postid


                override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                    newItem == oldItem
            }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        return UserPostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.userpost,parent,false)
        )
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        val pos=getItem(position)
        holder.v.FTitle.text=pos.PostTitle
        holder.v.FBody.text=pos.PostText
        debugger("Recycler${holder.v.FBody}")


        if (pos.Blogimage.isNullOrEmpty())holder.v.FImage.visibility=View.GONE
        else{
            Glide.with(host).asBitmap()
                .load(pos.Blogimage)
                .placeholder(R.color.content_placeholder)
                .error(R.color.content_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.v.FImage)
        }
        holder.v.setOnClickListener {
            host.startActivity(
                Intent(host,
                    PostActivity::class.java
                ).apply { putExtra(PostActivity.EXTRA_POST,pos)
                    putExtra(MainActivity.EXTRA_POST,pos)
                }
            )
        }


    }

}
