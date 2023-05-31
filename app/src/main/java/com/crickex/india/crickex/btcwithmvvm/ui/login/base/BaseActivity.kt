package com.crickex.india.crickex.btcwithmvvm.ui.login.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.popup_dialogs.*
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppLoader

open class BaseActivity : AppCompatActivity() {
    var loader: AppLoader = AppLoader()
    var successDialog: SuccessDialog = SuccessDialog()
    var billingFirmDialog: BillingFirmDialog = BillingFirmDialog()
    var demandType: DemandType = DemandType()
    var searchGRDialog: SearchGRDialog = SearchGRDialog()
    var popupSelectProfilePic: PopupSelectProfilePic = PopupSelectProfilePic()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }


    open fun showLoader() {
        val args = Bundle()

        args.putString("from", "")
        loader =
            AppLoader.getInstance(args)
        loader.show(supportFragmentManager, "")
    }

    open fun dismissLoader() {
        val prev: Fragment? = loader
        if (prev != null) {
            loader = prev as AppLoader
            loader.dismiss()
        }

    }

    open fun showDialogSuccessOrError(
        title: String?,
        message: String?,
        success_or_error: String?,
    ) {
        val args = Bundle()
        args.putString("title", title)
        args.putString("message", message)
        args.putString("success_or_error", success_or_error)
        successDialog = SuccessDialog.getInstance(args)
        successDialog.show(supportFragmentManager, "")
    }


    open fun showDemandDialog(title1: String?, title2: String?, des1: String?, des2: String?) {
        val args = Bundle()
        args.putString("title1", title1)
        args.putString("title2", title2)
        args.putString("des1", des1)
        args.putString("des2", des2)
        demandType = DemandType.getInstance(args)
        demandType.show(supportFragmentManager, "")
    }

    open fun showBillingPrefDialog() {
        val args = Bundle()
        args.putString("title", "")
        billingFirmDialog = BillingFirmDialog.getInstance(args)
        billingFirmDialog.show(supportFragmentManager, "")
    }

    open fun showSearchGrDialog() {
        val args = Bundle()
        args.putString("title", "")
        searchGRDialog = SearchGRDialog.getInstance(args)
        searchGRDialog.show(supportFragmentManager, "")
    }

    open fun showImagePickerDialog(
        title1: String?,
        context: Context,
        listener: DialogImageInterface,
    ) {
        val args = Bundle()
        args.putString("title", title1)
        popupSelectProfilePic = PopupSelectProfilePic.getInstance(args)
        popupSelectProfilePic.setListener(object : PopupSelectProfilePic.selectImageListner {
            override fun onSelectFromGallery() {
                listener.onClickGallery()

            }

            override fun onSelectFromCamera() {
                listener.onClickCamera()
            }

        })
        popupSelectProfilePic.show(supportFragmentManager, "")
    }

    interface DialogImageInterface {
        fun onClickGallery()
        fun onClickCamera()
    }
}