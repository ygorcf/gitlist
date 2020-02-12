package com.example.gitlist.app.activity.githubRepositoryList

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd

object FilterAnimation {

    fun startHeightAnimation (view: View, startHeight: Int, endHeight: Int, endListener: (() -> Unit)? = null) {
        val valueAnimator = ValueAnimator.ofInt(startHeight, endHeight)
            .setDuration(500)

        valueAnimator.addUpdateListener {
            view.layoutParams?.height = it.animatedValue as Int
            view.requestLayout()
        }
        valueAnimator.doOnEnd {
            endListener?.invoke()
        }

        AnimatorSet().apply {
            interpolator = AccelerateDecelerateInterpolator()
            play(valueAnimator)
            start()
        }
    }

}