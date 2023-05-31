package com.crickex.india.crickex.btcwithmvvm.ui.login.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.ActivityLoginBinding
import com.crickex.india.crickex.btcwithmvvm.databinding.AppLoaderBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery.GalleryFragment

class AppLoader : DialogFragment() {
    private lateinit var binding: AppLoaderBinding



        var TAG=AppLoader::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = AppLoaderBinding.inflate(
            layoutInflater
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.d("ABSDIALOGFRAG", "Exception", e)
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        fun getInstance(args: Bundle?): AppLoader {
            val dialog = AppLoader()
            dialog.arguments = args
            return dialog
        }
    }
}