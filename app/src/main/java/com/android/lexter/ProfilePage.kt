package com.android.lexter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.lexter.adapter.PostAdapterView
import com.android.lexter.adapter.UserPostAdapter
import com.android.lexter.util.Post
import com.android.lexter.util.debugger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.fragment_home.*

class ProfilePage : AppCompatActivity() {
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }


    private lateinit var adapter: UserPostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        toolbarProfile.setNavigationOnClickListener {
            startActivity(Intent(this@ProfilePage,MainActivity::class.java))
            finish() }
            setupList()

                val post = intent.getParcelableExtra<Post>(PostActivity.EXTRA_POST)

            fabs_edit_post.setOnClickListener {
                with(Intent(this@ProfilePage, ComposeBlog::class.java)) {
                    startActivity(this.apply { putExtra(ComposeBlog.EXTRA_POST, post)

                    })
                    finish()
                }
            }



    }


    private fun setupList() {
        adapter = UserPostAdapter(this@ProfilePage)
        Userrecycler.apply {
            // Adapter
            this.adapter = this@ProfilePage.adapter
            // Layout Manager
            this.layoutManager = LinearLayoutManager(this@ProfilePage)
        }

       updatePost()
    }
    private fun updatePost() {
        val postRef=database.reference.child("Posts")
        postRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val currentUser= FirebaseAuth.getInstance().currentUser!!.uid
                val posts= mutableListOf<Post?>()
                if(p0.exists()){
                    val id:String
                    p0.children.forEach {



                            val postItem = it.getValue(Post::class.java)
                        debugger("currentData->>>>>${postItem!!.publisher}")
                        if(postItem!!.publisher==currentUser) {
                            posts.add(postItem)
                            adapter.submitList(posts)
                        }
                        else{
                            debugger("nope")
                        }
//

                    }


                    adapter.submitList(posts)
                }

                else {
                    debugger("mo post ")
                }

            }



        })

    }



    override fun onBackPressed() {
        startActivity(Intent(this@ProfilePage,MainActivity::class.java))
        super.onBackPressed()
    }
}
