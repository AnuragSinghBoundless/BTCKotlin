package com.crickex.india.crickex.btcwithmvvm.ui.login

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.RvImageListItemBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.images.GalleryImagesModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.GlideHelper

class AdapterRVImage() : RecyclerView.Adapter<AdapterRVImage.ImageHolder>() {

    lateinit var context: Context
    private var listImage = ArrayList<GalleryImagesModel>()
    private var callGridClick: CallGridClick? = null

    constructor(
        context: Context,
        listImage: ArrayList<GalleryImagesModel>,
        callGridClick: CallGridClick,
    ) :
            this() {
        this.context = context
        this.listImage = listImage
        this.callGridClick = callGridClick
    }

    inner class ImageHolder(view: RvImageListItemBinding) : RecyclerView.ViewHolder(view.root) {
        var binding: RvImageListItemBinding

        init {
            binding = view
            // Click Listeners
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder = ImageHolder(
        RvImageListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AdapterRVImage.ImageHolder, position: Int) {
        GlideHelper.LoadImageSimple(
            context,
            listImage[position].images,
            R.drawable.logo_truck,
            holder.binding.rvImg
        )

        holder.binding.rvImg.setOnClickListener {

            callGridClick!!.onClick(position)

        }

    }

    interface CallGridClick {
        fun onClick(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}