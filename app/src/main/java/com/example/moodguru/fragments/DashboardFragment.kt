package com.example.moodguru.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodguru.PostAdapter
import com.example.moodguru.R
import com.example.moodguru.parseDataModel.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class DashboardFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView

    lateinit var adapter : PostAdapter

    val allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set up our views and click listeners

        postsRecyclerView = view.findViewById(R.id.rvDashboard)

        // steps to populate RecyclerView
        // 1. create layout for each row in list (item_post.xml)
        // 2. create data source for each row (this is the Post class)
        // 3. create adapter that will bridge data and row layout (PostAdapter)
        // 4. set adapter on RecyclerView
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter
        // 5. Set layout manager on RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }


    // Query for all posts in our server
    open fun queryPosts(){
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)
            .include(Post.KEY_AUTHOR)
            .include(Post.KEY_EMOTION)
        // Return posts in descending order (newer posts appear first)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
            .addDescendingOrder("createdAt")

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?){
                if (e != null){
                    // Something went wrong
                    Log.i(TAG, "Error fetching posts")
                }else{
                    if (posts != null){
                        for (post in posts){
                            Log.i(TAG, "Post: " + post.getJournal() + ", Date: " + post.getDate())
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }else{
                        Log.i(TAG, "there isn't any post")
                    }
                }
            }
        })
    }

    companion object{
        const val TAG = "DashboardFragment"
    }
}