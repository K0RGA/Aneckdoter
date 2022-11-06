package com.example.aneckdoter

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.aneckdoter.ui.JokeListFragment
import com.example.aneckdoter.ui.LikeListFragment
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val fragmentContainer = R.id.fragmentContainer
        Drawer()
            .withActivity(this)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withHeader(R.layout.drawer_header)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.drawer_item_random_joke),
                PrimaryDrawerItem().withName(R.string.drawer_item_like))
            .withOnDrawerItemClickListener { _, _, position, _, _ ->
                when (position){
                    1 -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(fragmentContainer,JokeListFragment.get())
                        addToBackStack("joke_list")
                    }
                    2 -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(fragmentContainer, LikeListFragment.newInstance())
                        addToBackStack("like_list")
                    }
                }
            }
            .build()

        val isFragmentContainerEmpty = (savedInstanceState == null)
        if (isFragmentContainerEmpty){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainer, JokeListFragment.get())
                addToBackStack("joke_list")
            }
        }
    }


}