package com.example.aneckdoter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.example.aneckdoter.ui.BestListFragment
import com.example.aneckdoter.ui.JokeListFragment
import com.example.aneckdoter.ui.LikeListFragment
import com.mikepenz.iconics.typeface.FontAwesome
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val (toolbar: Toolbar, fragmentContainer) = createToolbar()
        createDrawer(toolbar, fragmentContainer)
        initialize(savedInstanceState, fragmentContainer)
    }

    private fun initialize(savedInstanceState: Bundle?, fragmentContainer: Int) {
        val isFragmentContainerEmpty = (savedInstanceState == null)
        if (isFragmentContainerEmpty) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainer, JokeListFragment.get())
                addToBackStack("joke_list")
            }
        }
    }

    private fun createToolbar(): Pair<Toolbar, Int> {
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.drawer_item_random_joke)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val fragmentContainer = R.id.fragmentContainer
        return Pair(toolbar, fragmentContainer)
    }

    private fun createDrawer(toolbar: Toolbar, fragmentContainer: Int) {
        Drawer()
            .withActivity(this)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withHeader(R.layout.drawer_header)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.drawer_item_random_joke)
                    .withIcon(getDrawable(R.drawable.random)),
                PrimaryDrawerItem().withName(R.string.drawer_item_like)
                    .withIcon(getDrawable(R.drawable.heart)),
                PrimaryDrawerItem().withName(R.string.drawer_item_best_joke)
                    .withIcon(getDrawable(R.drawable.crown))
            )
            .withOnDrawerItemClickListener { _, _, position, _, _ ->
                when (position) {
                    1 -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(fragmentContainer, JokeListFragment.get())
                            addToBackStack("joke_list")
                            toolbar.setTitle(R.string.drawer_item_random_joke)
                        }
                    }
                    2 -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(fragmentContainer, LikeListFragment.newInstance())
                            addToBackStack("like_list")
                        }
                        toolbar.setTitle(R.string.drawer_item_like)
                    }
                    3 -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(fragmentContainer, BestListFragment.get())
                            addToBackStack("best_list")
                        }
                        toolbar.setTitle(R.string.drawer_item_best_joke)
                    }
                }
            }
            .build()
    }
}