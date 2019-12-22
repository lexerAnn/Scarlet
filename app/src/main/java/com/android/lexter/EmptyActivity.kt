package com.android.lexter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.android.lexter.util.debugger
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_empty.*

class EmptyActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        signUps.setOnClickListener(View.OnClickListener {
            val intent=Intent(this@EmptyActivity,Registration::class.java)
            startActivity(intent)
        })

      logins.setOnClickListener(View.OnClickListener {
          val intent_Login=Intent(this@EmptyActivity,Login::class.java)
          startActivity(intent_Login)
      })
        auth = FirebaseAuth.getInstance()
    }
    override fun onStart() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email
       if(currentUser!=null){
           debugger("Current user$currentUser")
           updateUI(currentUser)
       }
        else{

            Toast.makeText(this,"SignUp or Log in",Toast.LENGTH_LONG).show()
           debugger("Nutin")
       }

        super.onStart()

    }
    private fun updateUI(user: String?){
        debugger("Cureent uaer->>>>>$user")
        if(user!=null) {
            val snackbar = Snackbar.make(linearLayout, "Signin as$user", Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            snackbar.apply {
                duration = BaseTransientBottomBar.LENGTH_SHORT
                show()
            }


        Handler().postDelayed({
            val intent=Intent(this@EmptyActivity,MainActivity::class.java)
            intent.putExtra( "authName",user)
            startActivity(intent)
            finish()
        },4000)

        }
        else{

        }


    }
}
