package com.android.lexter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.progressbar.*

class Login : AppCompatActivity() {
    private var k = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toolbarlogin.setNavigationOnClickListener {
            startActivity(Intent(this@Login,EmptyActivity::class.java))
            finish() }

    userLogin.setOnClickListener(View.OnClickListener {
        loginUser()
    })


    }

    override fun onBackPressed() {
        k++;
        if(k == 1) {
            Toast.makeText(this@Login,"Click one more time to exist app", Toast.LENGTH_SHORT).show()
        } else {
            finish()
            super.onBackPressed();
        }
    }

    private fun loginUser() {

        val Lname=Loginname.text.toString()
        val Lpassword=LoginPassword.text.toString()
        when{
            TextUtils.isEmpty(Lname)->Toast.makeText(this,"Empty Name Field",Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(Lpassword)->Toast.makeText(this,"Empty Password Field",Toast.LENGTH_SHORT).show()
else->{
    llProgressBar.visibility=View.VISIBLE
    val mAuth:FirebaseAuth=FirebaseAuth.getInstance()
    mAuth.signInWithEmailAndPassword(Lname,Lpassword).addOnCompleteListener { task ->
        if(task.isSuccessful){
            val intent=Intent(this@Login,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val message=task.exception!!.toString()
            Toast.makeText(this,"Error$message",Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut()
                llProgressBar.visibility=View.GONE
        }    }

}
        }
        }


        }





