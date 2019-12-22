package com.android.lexter

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.android.lexter.util.Post
import com.android.lexter.util.debugger
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post.toolbar

class PostActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    companion object {
        const val EXTRA_POST = "extra_post"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,MainActivity::class.java))
            finish() }
        val currentUser= FirebaseAuth.getInstance().currentUser!!.uid

        val post = intent.getParcelableExtra<Post>(EXTRA_POST)


        debugger("poopo$post")
        val img:Uri
        img= post.Blogimage!!.toUri()

                if(post!=null){
                post_title.text=post.PostTitle
                post_desc.text=post.PostText
                    Glide.with(this)
                        .load(img)
                        .into(post_image)





        }
        else{
            Toast.makeText(this,"Enter a text",Toast.LENGTH_LONG).show()
        }

        val pub1= post.publisher.toString()
        debugger("CuurentUser$pub1")

        if(pub1==currentUser){
            fab_edit_post.visibility= View.VISIBLE
            fab_edit_post.setOnClickListener {
                with(Intent(this@PostActivity, ComposeBlog::class.java)) {
                        startActivity(this.apply { putExtra(ComposeBlog.EXTRA_POST, post)

                        })
                    finish()
                }
            }
        }
        else {
            fab_edit_post.visibility=View.GONE

        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@PostActivity,MainActivity::class.java))
        super.onBackPressed()
    }
}
