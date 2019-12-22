package com.android.lexter.fragments

import com.android.lexter.adapter.PostAdapterView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.lexter.util.Post
import com.android.lexter.R
import com.android.lexter.util.debugger
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private lateinit var adapter: PostAdapterView
    var puid:String?=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        debugger("Reading")
        setupList()
        super.onViewCreated(view, savedInstanceState)


7
    }


    private fun setupList() {
        adapter = PostAdapterView(this@HomeFragment.requireActivity())
        recyclerView.apply {
            // Adapter
            this.adapter = this@HomeFragment.adapter
            // Layout Manager
            this.layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }
        updatePost()
    }
    private fun updatePost() {
        val userRef=database.reference.child("Users")
        val postRef=database.reference.child("Posts")
        postRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val posts= mutableListOf<Post?>()
                    p0.children.forEach {
                         val postItem=it.getValue(Post::class.java)
                        posts.add(postItem)
                        debugger("post$$postItem")
                        puid= postItem?.PostText
                        debugger("$puid" )
                    }
                    debugger("now$puid")

                    adapter.submitList(posts)
                }
                else {
                  debugger("mo post ")
                    }

            }



        })
    debugger("out->>>>>>>>>>>>>>>>>>>>>$puid")
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        val postAdap = PostAdapterView()
//        recyclerView.apply {
//            adapter = postAdap
//            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
//            setHasFixedSize(false)
//        }
//        super.onActivityCreated(savedInstanceState)
//    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
