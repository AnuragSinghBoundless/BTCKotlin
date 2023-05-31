package com.crickex.india.crickex.btcwithmvvm.ui.login.popup_dialogs

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
import com.crickex.india.crickex.btcwithmvvm.databinding.PopupSelectProfileBinding

class PopupSelectProfilePic : DialogFragment() {
    lateinit var binding: PopupSelectProfileBinding
    private var listener: selectImageListner? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = PopupSelectProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    fun setListener(listener: selectImageListner?) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val args = arguments
        if (args != null) {
            if (args.containsKey("title")) {
                binding.successTitle.text = args.getString("title")
            }
        }

        binding.gallery.setOnClickListener {
            if (listener != null) {
                listener!!.onSelectFromGallery()
                dismiss()
            }
        }
        binding.camera.setOnClickListener {
            if (listener != null) {
                listener!!.onSelectFromCamera()
                dismiss()
            }
        }
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


    interface selectImageListner {
        fun onSelectFromGallery()
        fun onSelectFromCamera()
    }


    companion object {
        fun getInstance(args: Bundle?): PopupSelectProfilePic {
            val dialog = PopupSelectProfilePic()
            dialog.arguments = args
            return dialog
        }
    }
}