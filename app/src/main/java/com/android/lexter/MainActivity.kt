package com.android.lexter

import com.android.lexter.fragments.HomeFragment
import com.android.lexter.fragments.SignOutFrag
import com.android.lexter.util.debugger
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.android.lexter.util.Post
import com.android.lexter.util.User
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.viewitem.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val EXTRA_POST = "extra_post"
    }
    private var k = 0
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleFragments(item)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawer.addDrawerListener(drawerLayout)
        drawerLayout.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(nav_view.menu.findItem(R.id.homeUser)).apply {
            addFragment(HomeFragment())
        }



            fab.setOnClickListener(View.OnClickListener {
                startActivity(Intent(this@MainActivity,ComposeBlog::class.java))
            })


    }

    private fun handleFragments(item: MenuItem) {
        when (item.itemId) {
            R.id.signoutt -> {
                addFragment(SignOutFrag())



            }
            R.id.profile -> {
                val user = intent.getParcelableExtra<User>(MainActivity.EXTRA_POST)
            val intent=Intent(this@MainActivity, ProfilePage::class.java)
                startActivity(intent)

            }




        }

        drawer.closeDrawers()
    }



    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment,fragment::class.java.simpleName)
            .commit()

    }

    override fun onBackPressed() {
        k++;
        if(k == 1) {
            Toast.makeText(this,"Clicked one more to close",Toast.LENGTH_LONG).show()

         }else {
            finish()
            super.onBackPressed();
        }
    }

}
