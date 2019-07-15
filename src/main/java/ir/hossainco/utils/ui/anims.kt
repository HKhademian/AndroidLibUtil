@file:Suppress("PackageDirectoryMismatch")

package ir.hossainco.utils.ui.anims

import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun <T : View> T.animatedChange(
	@AnimRes animBeforeRes: Int = android.R.anim.fade_out,
	@AnimRes animAfterRes: Int = android.R.anim.fade_in,
	change: T.() -> Unit
) {
	val animBefore = AnimationUtils.loadAnimation(context, animBeforeRes)!!
	val animAfter = AnimationUtils.loadAnimation(context, animAfterRes)!!
	animatedChange(animBefore, animAfter, change)
}

fun <T : View> T.animatedChange(
	animBefore: Animation,
	animAfter: Animation,
	change: T.() -> Unit
) {
	animBefore.setAnimationListener(object : AnimationListener {
		override fun onAnimationStart(animation: Animation) = Unit
		override fun onAnimationRepeat(animation: Animation) = Unit
		override fun onAnimationEnd(animation: Animation) {
			change()

//			animAfter.setAnimationListener(object : AnimationListener {
//				override fun onAnimationStart(animation: Animation) = Unit
//				override fun onAnimationRepeat(animation: Animation) = Unit
//				override fun onAnimationEnd(animation: Animation) = Unit
//			})

			startAnimation(animAfter)
		}
	})
	startAnimation(animBefore)
}
