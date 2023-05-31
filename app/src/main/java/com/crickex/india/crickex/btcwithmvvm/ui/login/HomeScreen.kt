package com.crickex.india.crickex.btcwithmvvm.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.ActivityHomeScreenBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseActivity
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.AccessGalleryRVScreen
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery.GalleryFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home.HomeFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.slideshow.SlideshowFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppPreferences
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils
import com.google.android.material.navigation.NavigationView


class HomeScreen : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeScreenBinding
    lateinit var appPreferences: AppPreferences

    var homeFragment: HomeFragment? = null
    var galleryFragment: GalleryFragment? = null
    var slideshowFragment: SlideshowFragment? = null
    private var previousFragment: Fragment? = null
    var navHeaderView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeScreen.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        AppUtils.inAppUpdate(this, this)



        homeFragment = HomeFragment()
        AppUtils.setFragment(homeFragment, true, this, R.id.fr_bottom_navigation_view)

        AppUtils.changeStatusBarColor(this)

        appPreferences = AppPreferences(this)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val layout: LinearLayout = binding.mainLayout
        AppUtils.customizeDrawer(
            this,
            drawerLayout,
            binding.appBarHomeScreen.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close,
            layout
        )


        val navView: NavigationView = binding.navView
        // val navController = findNavController(R.id.nav_host_fragment_content_home_screen)

        navHeaderView = navView.getHeaderView(0)
        val menu: Menu = navView.menu

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.logout
            ), drawerLayout
        )

        /*    setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)*/




        navView.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (id == R.id.logout) {
                appPreferences.logoutUser()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            } else if (id == R.id.nav_access_gallery) {
                startActivity(Intent(this, AccessGalleryRVScreen::class.java))
                drawerLayout.close()
            }
            true
        }

        takeClick()
        setBottomNavigationView()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppUtils.UPDATE_CODE) {
            if (resultCode != RESULT_OK) {

            }
        }
    }

    private fun setBottomNavigationView() {
        /*   homeFragment = HomeFragment.getInstance()
           addShowHideFragment(homeFragment!!, HomeFragment.TAG, false)*/
        binding.navigation.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.nav_home -> {
                    homeFragment = HomeFragment()
                    AppUtils.setFragment(homeFragment, true, this, R.id.fr_bottom_navigation_view)

                }
                R.id.nav_gallery -> {
                    galleryFragment = GalleryFragment()
                    AppUtils.setFragment(
                        galleryFragment,
                        true,
                        this,
                        R.id.fr_bottom_navigation_view
                    )


                }
                R.id.navigation_schedule -> {
                    slideshowFragment = SlideshowFragment()
                    AppUtils.setFragment(
                        slideshowFragment,
                        true,
                        this,
                        R.id.fr_bottom_navigation_view
                    )
                }

            }

            true
        }
    }


    private fun takeClick() {
        binding.appBarHomeScreen.setFirm.setOnClickListener {
            showBillingPrefDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_screen, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_screen)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}