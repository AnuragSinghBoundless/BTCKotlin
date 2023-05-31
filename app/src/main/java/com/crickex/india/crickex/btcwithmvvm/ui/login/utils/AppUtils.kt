package com.crickex.india.crickex.btcwithmvvm.ui.login.utils


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.images.GalleryImagesModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import java.io.File
import java.util.regex.Pattern


object AppUtils {

    var imageList: ArrayList<GalleryImagesModel> = ArrayList()
    var UPDATE_CODE = 22
    lateinit var appUpdateManager: AppUpdateManager
    lateinit var listner: InstallStateUpdatedListener


    fun inAppUpdate(context: Context, activity: AppCompatActivity) {
        appUpdateManager = AppUpdateManagerFactory.create(context)
        val task: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
        task.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                    AppUpdateType.FLEXIBLE
                )
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        it, AppUpdateType.FLEXIBLE, activity,
                        UPDATE_CODE
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            appUpdateManager.registerListener(listner)
        }

        listner = InstallStateUpdatedListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                popup(activity)
            }
        }

    }

    private fun popup(activity: AppCompatActivity) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            "App Update almost done!",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Reload") {
            appUpdateManager.completeUpdate()
        }
        snackbar.setTextColor(Color.GREEN)
        snackbar.show()
    }


    fun checkUserName(target: CharSequence): Boolean {
        val USER_NAME_STRING = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
        return Pattern.compile(USER_NAME_STRING).matcher(target).matches()
    }


    fun errorShakeEditBox(context: Context, editText: EditText, errorMsg: String) {

        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_info_red, 0)
        val animation = AnimationUtils.loadAnimation(context, R.anim.error_shake)
        editText.startAnimation(animation)
        editText.error = errorMsg


    }

    fun noErrorFoundForEditBox(editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }


    @SuppressLint("HardwareIds")
    fun callDeviceImei(context: Context): String {
        // in the below line, we are setting our imei to our text view.
        val IMEINumber = "c2ba529a14cf978c"
        return IMEINumber
    }

    fun deviceModel(context: Context): String {
        val reqString = (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name)
        return reqString
    }


    fun changeStatusBarColor(context: AppCompatActivity) {
        val window: Window = context.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#ECF8F4")
    }

    fun customizeDrawer(
        context: AppCompatActivity,
        drawer: DrawerLayout,
        toolbar: Toolbar,
        open_drawer: Int,
        close_drawer: Int,
        layout: LinearLayout,
    ) {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            context, drawer, toolbar, open_drawer, close_drawer
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                val moveFactor: Float = drawerView.width * slideOffset
                val min = .9f
                val max = 1f
                val scaleFactor = max - (max - min) * slideOffset
                layout.translationX = moveFactor
                layout.scaleX = scaleFactor
                layout.scaleY = scaleFactor
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }
        drawer.addDrawerListener(toggle)


        //..............06/04/2021.........customizing drawer navigation Indicator.......
        toggle.isDrawerIndicatorEnabled = false
        toggle.toolbarNavigationClickListener = View.OnClickListener { view: View? ->
            drawer.openDrawer(
                GravityCompat.START
            )
        }
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_main)
        drawer.setScrimColor(Color.TRANSPARENT)
        toggle.syncState()
    }


    fun mainRVParms(context: Context, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    fun horizontalRvParams(context: Context, recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }


    fun setFragment(
        fragment: Fragment?,
        removeStack: Boolean,
        activity: FragmentActivity,
        mContainer: Int,
    ) {
        val fragmentManager = activity.supportFragmentManager
        val ftTransaction = fragmentManager.beginTransaction()
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ftTransaction.replace(mContainer, fragment!!)
        } else {
            ftTransaction.replace(mContainer, fragment!!)
            ftTransaction.addToBackStack(null)
        }
        //Log.e("TAG", "Fragment transition is completetd");
        ftTransaction.commit()
    }

    fun createImageUri(context: Context): Uri? {
        val image = File(context.applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            context.applicationContext,
            "com.crickex.india.crickex.btcwithmvvm.fileProvider",
            image
        )
    }


    fun fetchImages(activity: AppCompatActivity): ArrayList<GalleryImagesModel> {
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID
        )
        val imagecursor: Cursor = activity.managedQuery(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, ""
        )
        for (i in 0 until imagecursor.count) {
            imagecursor.moveToPosition(i)
            val dataColumnIndex =
                imagecursor.getColumnIndex(MediaStore.Images.Media.DATA)
            val galleryImagesModel = GalleryImagesModel()
            galleryImagesModel.images = imagecursor.getString(dataColumnIndex)
            imageList.add(galleryImagesModel)
        }
        return imageList
    }




}