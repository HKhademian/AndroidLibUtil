package ir.hco.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ir.hco.util.BaseApp.Companion.context

interface Source

interface ColorSource : Source {
	fun getColor(context: Context): Int

	val color get() = getColor(context)

	companion object {
		val WHITE = ofInt(Color.WHITE)
		val BLACK = ofInt(Color.BLACK)
		val RED = ofInt(Color.RED)
		val BLUE = ofInt(Color.BLUE)
		val GREEN = ofInt(Color.GREEN)
		val YELLOW = ofInt(Color.YELLOW)
		val GRAY = ofInt(Color.GRAY)
		val NULL = ofInt(0)

		fun of(@ColorRes resid: Int) = object : ColorSource {
			override fun getColor(context: Context) =
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					context.resources.getColor(resid, context.theme)
				} else {
					context.resources.getColor(resid)
				}
		}

		fun ofInt(@ColorInt color: Int) = object : ColorSource {
			override fun getColor(context: Context) =
				color
		}
	}
}

interface StringSource : Source {
	fun getString(context: Context, vararg formatArgs: Any): String?

	val string get() = getString(context)

	fun setText(view: TextView, vararg formatArgs: Any) {
		view.text = getString(view.context, *formatArgs)
	}

	companion object {
		val NULL = of(null)
		val EMPTY = of("")

		fun CharSequence?.toSource() =
			of(this)

		fun of(@StringRes resid: Int) = object : StringSource {
			override fun getString(context: Context, vararg formatArgs: Any) =
				if (formatArgs.isNotEmpty())
					context.resources.getString(resid, *formatArgs)
				else context.resources.getString(resid)
		}

		fun of(text: CharSequence?) = object : StringSource {
			override fun getString(context: Context, vararg formatArgs: Any) =
				when {
					text == null -> null
					formatArgs.isNotEmpty() -> String.format(context.local, text.toString(), *formatArgs)
					else -> text.toString()
				}
		}
	}
}

interface DrawableSource : Source {
	fun getDrawable(context: Context): Drawable?

	val drawable get() = getDrawable(context)

	fun setImage(view: ImageView) {
		view.setImageDrawable(getDrawable(view.context))
	}

	fun setBackground(view: View) {
		view.background = getDrawable(view.context)
	}

	companion object {
		val NULL = of(null)
		val EMPTY = ofColor(0)

		fun Bitmap.toSource() =
			of(this)

		fun Drawable?.toSource() =
			of(this)

		fun of(bitmap: Bitmap) = object : DrawableSource {
			override fun getDrawable(context: Context) =
				BitmapDrawable(context.resources, bitmap)
		}

		fun of(drawable: Drawable?) = object : DrawableSource {
			override fun getDrawable(context: Context) =
				drawable
		}

		fun of(@DrawableRes resid: Int) = object : DrawableSource {
			override fun getDrawable(context: Context) =
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					context.resources.getDrawable(resid, context.theme)
				} else {
					context.resources.getDrawable(resid)
				}
		}

		fun of(source: ColorSource) = object : DrawableSource {
			override fun getDrawable(context: Context) =
				ColorDrawable(source.getColor(context))
		}

		fun ofColor(@ColorInt color: Int) = object : DrawableSource {
			override fun getDrawable(context: Context) =
				ColorDrawable(color)
		}
	}
}
