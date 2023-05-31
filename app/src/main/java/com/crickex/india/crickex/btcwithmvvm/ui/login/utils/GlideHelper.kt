package com.crickex.india.crickex.btcwithmvvm.ui.login.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget

object GlideHelper {
    fun LoadImageSimpleNoPlaceHolder(context: Context?, url: String?, imageView: ImageView?) {
        if (context != null) {
            Glide.with(context.applicationContext).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //.skipMemoryCache(true)
                .into(imageView!!)
        }
    }

    fun LoadImageNoCaching(
        context: Context?,
        url: String?,
        placeHolder: Int,
        imageView: ImageView?
    ) {
        if (context != null) {
            Glide.with(context.applicationContext).load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true) //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .placeholder(placeHolder)
                .into(imageView!!)
        }
    }

    fun LoadImageSimple(context: Context?, url: String?, placeHolder: Int, imageView: ImageView?) {
        if (context != null) {
            Glide.with(context.applicationContext).load(url)
                .centerCrop() //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .placeholder(placeHolder)
                .into(imageView!!)
        }
    }

    fun LoadImageSimpleWihoutCenterCrop(
        context: Context?,
        url: String?,
        placeHolder: Int,
        imageView: ImageView?
    ) {
        if (context != null) {
            Glide.with(context.applicationContext).load(url) //.centerCrop()
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .placeholder(placeHolder)
                .into(imageView!!)
        }
    }

    fun LoadImageSimpleWithoutPlaceholder(context: Context?, url: String?, imageView: ImageView?) {
        if (context != null) {
            Glide.with(context.applicationContext).load(url) // .centerCrop()
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                //.placeholder(placeHolder)
                .into(imageView!!)
        }
    }

    fun LoadCircularImageMethod1(
        mContext: Context, imageView: ImageView,
        placeholder: Int, url: String?
    ) {
        Glide.with(mContext)
            .asBitmap()
            .load(url)
            .placeholder(placeholder)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    fun LoadCircularImageMethod2(
        context: Context?,
        url: String?,
        placeholder: Int,
        imageView: ImageView?
    ) {
        Glide.with(context!!)
            .load(url) //.transform(new CircleTransform(context))
            .placeholder(placeholder)
            .into(imageView!!)
    }

    fun LoadImageOuterLayoutZero(
        context: Context?,
        url: String?,
        placeHolder: Int,
        imageView: ImageView
    ) {
        imageView.layout(0, 0, 0, 0)
        if (context != null) {
            Glide.with(context.applicationContext).load(url)
                .fitCenter() //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .placeholder(placeHolder)
                .into(imageView)
        }
    }

    /*public static void LoadLocalRoundCornerImage(Context context, byte[] bytes, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .bitmapTransform(new RoundedCornersTransformation(context,
                            context.getResources().getDimensionPixelOffset(R.dimen.dp_12), 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .load(bytes)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
    }*/
    fun LoadLocalCircularImage(context: Context?, bytes: ByteArray?, imageView: ImageView) {
        if (context != null) {

            /*Glide.with(context).load(bytes)
                    .bitmapTransform(new RoundedCornersTransformation(context,
                            context.getResources().getDimensionPixelOffset(R.dimen.dp_12), 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);*/
            Glide.with(context)
                .asBitmap()
                .load(bytes)
                .centerCrop()
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })
        }
    }

    /*public static void LoadLocalCircularImageInCircularImageView(final Context context,
                                                                 String url, int placeHolder,
                                                                 final com.makeramen.roundedimageview.RoundedImageView imageView) {

        if (context != null) {

            */
    /*Glide.with(context).load(bytes)
                    .bitmapTransform(new RoundedCornersTransformation(context,
                            context.getResources().getDimensionPixelOffset(R.dimen.dp_12), 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);*/
    /*


            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });


        }
    }*/
    fun LoadRoundCornerImageMethod1(
        context: Context?,
        url: String?,
        placeHolder: Int,
        imageView: ImageView
    ) {
        if (context != null) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .placeholder(placeHolder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.cornerRadius = 12.0f
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })
        }
    } /*public static void LoadRoundCornerImageMethod2(Context context, String url, int placeHolder, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .bitmapTransform(new RoundedCornersTransformation(context,
                            context.getResources().getDimensionPixelOffset(R.dimen.dp_12), 0,
                            RoundedCornersTransformation.CornerType.ALL)).placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.skipMemoryCache(true)
                    //.centerCrop()
                    .into(imageView);
        }
    }
    */
}