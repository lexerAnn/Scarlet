package com.android.lexter

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.lexter.util.Post
import com.android.lexter.util.debugger
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.viewitem.view.*
import java.util.*
import kotlin.collections.HashMap


class ComposeBlog : AppCompatActivity() {
    var ref= FirebaseDatabase.getInstance().reference.child("Posts")
    var postID:String= ref.push().key!!
var myUrl:String=""
    private val bucket: StorageReference by lazy {
    FirebaseStorage.getInstance().reference.child("demo-bucket")

    }

    companion object {
        const val EXTRA_POST = "EXTRA_POST"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (intent.hasExtra(EXTRA_POST)) {
            val post = intent.getParcelableExtra<Post>(EXTRA_POST)
            PostTitles.setText(post?.PostTitle)
            PostTexts.setText(post?.PostText)


        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbutton, menu)
        return true
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sendPost->{
                upLoadPost()
                return true
            }
            R.id.attach->{
                upLoadImage()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun upLoadImage() {

        startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpg","image/png","image/jpeg"))
        }, 2)

    }

    private fun upLoadPost() {
        when
        {

            TextUtils.isEmpty(PostTitles.text.toString())->Toast.makeText(this@ComposeBlog,"Enter your Title",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(PostTexts.text.toString())->Toast.makeText(this@ComposeBlog,"Enter your Body",Toast.LENGTH_LONG).show()
            else -> {



                 ref= FirebaseDatabase.getInstance().reference.child("Posts")



                val post = intent.getParcelableExtra<Post>(EXTRA_POST)

               if(intent.hasExtra(EXTRA_POST )){
                    val pub1= post.publisher.toString()
                   debugger("pub->>>>>$pub1")
                   val currentUser= FirebaseAuth.getInstance().currentUser!!.uid
                   debugger("uuuu$currentUser")
                   debugger("xxx$pub1")
                     if(pub1==currentUser){
                        post.publisher
                         ref.child(post.Postid).updateChildren(Post.toHashMap(post.apply {
                             this.PostTitle=PostTitles.text.toString()
                             this.PostText=PostTexts.text.toString()

                         })).addOnCompleteListener {
                             Toast.makeText(
                                 this@ComposeBlog,
                                 "post Updated",
                                 Toast.LENGTH_SHORT
                             ).show()
                             
                             val intent = Intent(this@ComposeBlog, MainActivity::class.java)
                             startActivity(intent)

                         }


                    }
                    else {

                         Toast.makeText(this@ComposeBlog,"CannotBlog",Toast.LENGTH_SHORT).show()
                         val intent = Intent(this@ComposeBlog, MainActivity::class.java)
                         startActivity(intent)

                     }
                }
                else{

                    val post = Post(
                        PostTitles.text.toString(),
                        PostTexts.text.toString().toLowerCase(),
                        postID!!,
                        FirebaseAuth.getInstance().currentUser!!.uid,""



                    )
                    ref.child(postID).setValue(post).addOnCompleteListener {
                        Toast.makeText(this, "Post Successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@ComposeBlog, MainActivity::class.java)
                        startActivity(intent)
               }

                }






            }
        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this@ComposeBlog,MainActivity::class.java))
        super.onBackPressed()
    }

 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     super.onActivityResult(requestCode, resultCode, data)
       val ref = FirebaseDatabase.getInstance().reference.child("Posts")


        val imageUri = data?.data
        debugger("Sara$imageUri")

        if (imageUri != null) bucket.child(System.currentTimeMillis().toString()).putFile(imageUri).addOnSuccessListener { snapshot ->
            snapshot.storage.downloadUrl.addOnCompleteListener {
                val postMap = HashMap<String, Any>()
                if (it.isSuccessful) {
                    val downloadUrl = it.result
                    myUrl = downloadUrl.toString()
                   postMap["blogimage"]=myUrl
                   debugger("Download URI => ${it.result}")
                    debugger("Download URIS=>$imageUri")
                    ref.child(postID!!).updateChildren(postMap)
                    postImg.setImageURI(it.result)

                } else {
                    debugger("Unable to get the download uri")
                    onBackPressed()
                }
            }.addOnFailureListener {
                debugger(it.localizedMessage)


            }
        }

    }
}


