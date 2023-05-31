package com.crickex.india.crickex.btcwithmvvm.ui.login.popup_dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.BillingFirmDialogBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppPreferences
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.Constants
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BillingFirmDialog : DialogFragment() {

    lateinit var binding: BillingFirmDialogBinding
    var billingFirm = ""
    lateinit var appPreferences: AppPreferences
    var firmCode = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BillingFirmDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        EventBus.getDefault().register(this)
        val args = arguments
        if (args != null) {
            if (args.containsKey("title")) {
                binding.referenceTitle.text = args.getString("title")
            }

        }

        appPreferences = AppPreferences(requireContext())

        binding.referenceTitle.text = getString(R.string.billing_firm_check_label)
        binding.preferenceDescription.text = getString(R.string.billing_pref_des)

        if (appPreferences.getBillingFirm() != null) {
            firmCode = appPreferences.getBillingFirm()!!
        }


        if (firmCode == "") {
            appPreferences.saveBillingFirm(billingFirm)
        } else {
            getCheckedStatus()
        }


        takeClick()


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(eventType: String) {

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    private fun getCheckedStatus() {
        if (firmCode.equals("1", ignoreCase = true)) {
            binding.checkBTC.isChecked = true
        } else if (firmCode.equals("4", ignoreCase = true)) {
            binding.checkJSL.isChecked = true
        }
    }

    private fun takeClick() {
        binding.checkBTC.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                billingFirm = "1"
                appPreferences.saveBillingFirm(billingFirm)
                binding.checkJSL.isChecked = false
                binding.checkBTC.isChecked = true

            }
        }

        binding.checkJSL.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                billingFirm = "4"
                appPreferences.saveBillingFirm(billingFirm)
                binding.checkBTC.isChecked = false
                binding.checkJSL.isChecked = true
            }

        }

        binding.ok.setOnClickListener {
            if (!binding.checkBTC.isChecked && !binding.checkJSL.isChecked) {
                Toast.makeText(context, "Please select at least one option", Toast.LENGTH_LONG)
                    .show()
            } else {
                EventBus.getDefault().post(Constants.BILLING_FIRM_EVENT)
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


    companion object {
        fun getInstance(args: Bundle?): BillingFirmDialog {
            val dialog = BillingFirmDialog()
            dialog.arguments = args
            return dialog
        }
    }

}