package com.example.aneckdoter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
                    1 -> supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, JokeListFragment.newInstance())
                            .commit()
                    2 -> supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, LikeListFragment.newInstance())
                            .addToBackStack("")
                            .commit()
                }
            }
            .build()

        val isFragmentContainerEmpty = (savedInstanceState == null)
        if (isFragmentContainerEmpty){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, JokeListFragment.newInstance())
                .commit()
        }
    }


}