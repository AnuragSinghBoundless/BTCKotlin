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
import com.crickex.india.crickex.btcwithmvvm.databinding.SuccessDialogBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.Constants

class SuccessDialog : DialogFragment() {

    private lateinit var binding: SuccessDialogBinding
    var SUCCESS_OR_ERROR = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = SuccessDialogBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val args = arguments
        if (args != null) {
            if (args.containsKey("title")) {
                binding.successTitle.text = args.getString("title")
            }
            if (args.containsKey("message")) {
                binding.description.text = args.getString("message")
            }
            if (args.containsKey("success_or_error")) {
                SUCCESS_OR_ERROR = args.getString("success_or_error")!!
            }

        }
        if (SUCCESS_OR_ERROR.equals(Constants.ERROR_LOTTIE, ignoreCase = true)) {
            binding.success.setAnimation("error2.json")
            binding.success.playAnimation()
            binding.success.loop(false)
        } else {
            binding.success.setAnimation("success_dialog_animation.json")
            binding.success.playAnimation()
            binding.success.loop(false)
        }

        binding.ok.setOnClickListener {
            dismiss()
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

    companion object {
        fun getInstance(args: Bundle?): SuccessDialog {
            val dialog = SuccessDialog()
            dialog.arguments = args
            return dialog
        }
    }

}