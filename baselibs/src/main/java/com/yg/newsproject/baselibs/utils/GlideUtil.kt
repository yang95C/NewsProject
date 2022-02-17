package com.yg.newsproject.baselibs.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yg.newsproject.baselibs.R

class GlideUtil private constructor() {

    companion object {
        /**
         * 加载圆形图片
         *
         * @param context
         * @param imageView
         * @param url   类型为url file resId byte[] uri
         * @param anim      是否使用淡入淡出动画 true动画 false没有动画
         */
        fun loadCircleImage(
            context: Context,
            imageView: ImageView,
            url: String,
            placeholderResId: Int,
            errorResId: Int,
            anim: Boolean
        ) {
            if (anim) {
                Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .transform(CircleCrop())
                    .into(imageView);
            } else {
                Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .transform(CircleCrop())
                    .into(imageView)
            }
        }


        /**
         * 加载图片
         *
         * @param context
         * @param imageView
         * @param url          类型为url file resId byte[] uri
         * @param placeholderResId 加载占位图
         * @param errorResId       错误占位图
         * @param anim             是否使用淡入淡出动画 true动画 false没有动画
         */
        fun loadImage(
            context: Context,
                      imageView: ImageView,
                      url: String,
                      placeholderResId: Int,
                      errorResId: Int,
                      anim: Boolean
        ){
            if (anim) {
                Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .into(imageView)
            } else {
                Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .into(imageView)
            }
        }

        fun loadImage(
            context: Context,
            imageView: ImageView,
            url: String
        ){
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                .placeholder(R.drawable.shape_pic_load_bg)
                .error(R.drawable.shape_pic_load_bg)
                .into(imageView)
        }

        /**
         * 加载圆角图片
         *
         * @param context
         * @param imageView
         * @param url    类型为url file resId byte[] uri
         * @param radius     圆角半径
         * @param anim       是否使用淡入淡出动画 true动画 false没有动画
         */
        fun loadRoundImage(
            context: Context,
            imageView: ImageView,
            url: String,
            errorResId: Int,
            radius:Int,
            anim: Boolean
        ){
            if (anim) {
                Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(errorResId)
                    .error(errorResId)
                    .transform(RoundedCorners(radius))
                    .into(imageView)
            } else {
                Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //缓存转换后的资源
                    .placeholder(errorResId)
                    .error(errorResId)
                    .transform(RoundedCorners(radius))
                    .into(imageView)
            }
        }
    }

}