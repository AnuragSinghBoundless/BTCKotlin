package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crickex.india.crickex.btcwithmvvm.databinding.DemandItemsBinding
import com.crickex.india.crickex.btcwithmvvm.databinding.ViewGrItemsBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewGr.ViewGrModelItem

class ViewGrAdapter() : RecyclerView.Adapter<ViewGrAdapter.MyHolderClass>() {
    lateinit var context: Context
    var listGr = ArrayList<ViewGrModelItem>()

    constructor(context: Context, listGr: ArrayList<ViewGrModelItem>) : this() {
        this.context = context
        this.listGr = listGr
    }


    inner class MyHolderClass(view: ViewGrItemsBinding) :
        RecyclerView.ViewHolder(view.root) {
        var binding: ViewGrItemsBinding

        init {
            binding = view
            // Click Listeners
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderClass =
        MyHolderClass(
            ViewGrItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewGrAdapter.MyHolderClass, position: Int) {
        val grModel = listGr[position]
        holder.binding.demandName.text = "Company Name - ${grModel.CompanyName}"
        holder.binding.createdBy.text = "Gr Date - ${grModel.GRDt}"
        holder.binding.apprvedBy.text = "Entry Date - ${grModel.GREntryDate}"
        holder.binding.vNum.text = "Gr No. - ${grModel.GRNo}"
        holder.binding.ammount.text = "Party Invoice - ${grModel.PrtInvNo}"
        holder.binding.nurration.text =
            "Consigner - ${grModel.CnsrNm}" + " " + "Consignee - ${grModel.CnsneNm}" + "\n" + "From - ${grModel.FromStnName}" + " " + "To - ${grModel.ToStnName}"
        holder.binding.remarks.text =
            "Truck No. - ${grModel.TrkIDNo}" + " " + "CH No. - ${grModel.ChNo}" + "\n" + "E-Way Bill No. - ${grModel.EWayBillNo}"


    }

    override fun getItemCount(): Int {
        return listGr.size
    }
}