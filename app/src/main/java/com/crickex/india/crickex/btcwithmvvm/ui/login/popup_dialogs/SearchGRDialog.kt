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
import com.crickex.india.crickex.btcwithmvvm.databinding.SearchGrBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SearchGRDialog : DialogFragment() {

    lateinit var binding: SearchGrBinding



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(eventType: String) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        EventBus.getDefault().register(this)

        binding.search.setOnClickListener {
            val searchedString = binding.edGrSearch.text.toString()
            EventBus.getDefault().post(searchedString)

            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
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
        fun getInstance(args: Bundle?): SearchGRDialog {
            val dialog = SearchGRDialog()
            dialog.arguments = args
            return dialog
        }
    }
}