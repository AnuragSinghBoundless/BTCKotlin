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
import com.crickex.india.crickex.btcwithmvvm.databinding.DemandsTypeDialogBinding

class DemandType : DialogFragment() {
    private lateinit var binding: DemandsTypeDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DemandsTypeDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val args = arguments
        if (args != null) {
            if (args.containsKey("title1")) {
                binding.dialogHeading.text = args.getString("title1")
                binding.totalAmt.text = args.getString("title2")
                binding.dialogDes1.text = args.getString("des1")
                binding.dialogDes2.text = args.getString("des2")

            }

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
        fun getInstance(args: Bundle?): DemandType {
            val dialog = DemandType()
            dialog.arguments = args
            return dialog
        }
    }

}