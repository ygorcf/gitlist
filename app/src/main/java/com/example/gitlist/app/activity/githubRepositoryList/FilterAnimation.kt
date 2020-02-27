package com.example.gitlist.app.activity.githubRepositoryList

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Classe responsavel pelas animacoes dos campos de filtro.
 */
object FilterAnimation {

    /**
     * Metodo de comando para iniciar a animação de altura de uma view.
     *
     * @param view A view que sera animada.
     * @param startHeight A altura inicial.
     * @param endHeight A altura final.
     * @param endListener Um ouvinte de quando terminar a animacao.
     */
    fun startHeightAnimation (view: View, startHeight: Int, endHeight: Int, endListener: (() -> Unit)? = null) {
        val valueAnimator = ValueAnimator.ofInt(startHeight, endHeight)
            .setDuration(500)

        valueAnimator.addUpdateListener {
            view.layoutParams?.height = it.animatedValue as Int
            view.requestLayout()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                endListener?.invoke()
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        })

        AnimatorSet().apply {
            interpolator = AccelerateDecelerateInterpolator()
            play(valueAnimator)
            start()
        }
    }

}