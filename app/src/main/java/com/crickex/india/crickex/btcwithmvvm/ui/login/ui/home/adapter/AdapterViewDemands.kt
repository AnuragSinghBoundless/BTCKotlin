package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crickex.india.crickex.btcwithmvvm.databinding.DemandItemsBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand.DemandTypeModel


class AdapterViewDemands() : RecyclerView.Adapter<AdapterViewDemands.MyDemandViewHollder>() {

    var list = ArrayList<DemandTypeModel>()
    lateinit var context: Context

    constructor(
        context: Context,

        list: ArrayList<DemandTypeModel>,
    ) : this() {
        this.context = context
        this.list = list
    }

    inner class MyDemandViewHollder(view: DemandItemsBinding) :
        RecyclerView.ViewHolder(view.root) {

        var binding: DemandItemsBinding

        init {
            binding = view
            // Click Listeners
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDemandViewHollder =
        MyDemandViewHollder(
            DemandItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )




    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyDemandViewHollder, position: Int) {

        val dataModel = list[position]
        holder.binding.demandName.text = dataModel.DemdTypeParticulars
        holder.binding.vNum.text = "Vehicle No. - ${dataModel.DemdFor}"
        holder.binding.ammount.text = "Rs. - ${dataModel.DemdAmt}"
        holder.binding.nurration.text = dataModel.AutoNarration
        holder.binding.createdBy.text = "Dmd By : " + dataModel.CreatedBy.split(":")[0]
        if (dataModel.ApprovedBy != ":") {
            holder.binding.apprvedBy.text = "Aprvd By : " + dataModel.ApprovedBy.split(":")[0]
        }
        holder.binding.remarks.text = "Remarks - ${dataModel.Remarks}"
        holder.binding.payStatus.text = dataModel.DemdStatusParticular
        holder.binding.msgCount.text = "MsgCount - ${dataModel.MsgCount}"
        if (dataModel.DemdStatus > 14) {
            holder.binding.delete.visibility = View.GONE
            holder.binding.edit.visibility = View.GONE
        }


    }
}