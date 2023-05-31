package com.crickex.india.crickex.btcwithmvvm.ui.login.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    private var activity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as BaseActivity
    }

    open fun showDialogSuccessOrError(
        title: String?,
        message: String?,
        success_or_error: String?,
    ) {
        (activity as BaseActivity).showDialogSuccessOrError(title, message, success_or_error)

    }

    open fun showLoader() {
        (activity as BaseActivity).showLoader()
    }

    open fun dismissLoader() {
        (activity as BaseActivity).dismissLoader()
    }

    open fun showDemandDialog(
        title1: String?, title2: String?, des1: String?, des2: String?,
    ) {
        (activity as BaseActivity).showDemandDialog(title1, title2, des1, des2)
    }

    open fun showSearchGr() {
        (activity as BaseActivity).showSearchGrDialog()
    }

    open fun showImagePickerDialog(title1: String?,context: Context, listner: BaseActivity.DialogImageInterface) {
        (activity as BaseActivity).showImagePickerDialog(title1,context, listner)
    }
}