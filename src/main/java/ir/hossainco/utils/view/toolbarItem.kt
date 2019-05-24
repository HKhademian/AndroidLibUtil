package ir.hossainco.utils.view

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import ir.hossainco.utils.ui.drawables.createRectGradientButton
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.padding
import org.jetbrains.anko.toast

fun ViewManager.toolbarItem(
	@IdRes id: Int? = null,

	@DrawableRes imageRes: Int? = null,
	image: Drawable? = null,

	@StringRes hintRes: Int? = null,
	hint: String? = null,

	onClickListener: (view: View) -> Unit = {}
) = imageView {
	if (id != null) {
		setId(id)
	}

	// image = ResourcesCompat.getDrawable(resources, imageRes, null)
	if (imageRes != null)
		setImageResource(imageRes)
	else if (image != null)
		setImageDrawable(image)

	background = createRectGradientButton(0, 0, 0x33aaaaaa.toInt(), 0, 0, 0)
	padding = dip(8)
	layoutParams = LinearLayout.LayoutParams(dip(48), MATCH_PARENT)

	setOnLongClickListener {
		when {
			hintRes != null -> {
				context.toast(hintRes)
				true
			}
			hint != null -> {
				context.toast(hint)
				true
			}
			else -> false
		}
	}

	setOnClickListener { onClickListener(it) }
}
