package com.android.lexter

import com.android.lexter.util.debugger
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.lexter.util.Post
import com.android.lexter.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.progressbar.*

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        signUp.setOnClickListener(View.OnClickListener {
            createAccount()
            debugger("clicked")
        })

        auth = FirebaseAuth.getInstance()
    }
private fun createAccount(){
val name=userName.text.toString()
    val email=userEmail.text.toString()
    val password=userPassword.text.toString()
    when{
        TextUtils.isEmpty(name)->Toast.makeText(this,"Name is empty",Toast.LENGTH_SHORT).show()
        TextUtils.isEmpty(email)->Toast.makeText(this,"Email is empty,",Toast.LENGTH_SHORT).show()
        TextUtils.isEmpty(password)->Toast.makeText(this,"Passowrd is empty",Toast.LENGTH_SHORT).show()

        else -> {
llProgressBar.visibility=View.VISIBLE
            val mAuth:FirebaseAuth= FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserInfo(name, email, password)
                        //Toast.makeText(this, "Account is Created", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        val message=task.exception!!.toString()
                        debugger("error:$message")
                        Toast.makeText(this,"Error:$message",Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                       llProgressBar.visibility=View.GONE

                    }
                }

        }
    }


}


    private fun saveUserInfo(name: String, email: String, password: String) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
            val user=User(
                FirebaseAuth.getInstance().currentUser!!.uid,
                userName.text.toString(),
                userEmail.text.toString(),
                userPassword.text.toString()
            )

            val userUid=user.uid
        debugger("useruid${user.userName}")

        userRef.child(currentUserID).setValue(user).addOnCompleteListener {
            debugger("created")
            Toast.makeText(this,"Account Created",Toast.LENGTH_LONG).show()
            with(Intent(this@Registration, MainActivity::class.java)) {

                startActivity(this.apply {putExtra(MainActivity.EXTRA_POST,user)})
            }
        }

    }


    }


