package ir.hco.util.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.view.Gravity.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ir.hco.util.*
import ir.hco.util.StringSource.Companion.toSource
import ir.hossainco.utils.ui.TextSize
import ir.hossainco.utils.ui.drawables.createRect3D
import ir.hossainco.utils.ui.drawables.createRectGradientButton
import ir.hossainco.utils.view.appTextView
import ir.hossainco.utils.view.factory
import org.jetbrains.anko.*

fun ViewManager.simpleToolbar(
	@IdRes id: Int? = R.id.toolbar,

	backColor: ColorSource = ColorSource.of(R.color.primaryColor),
	edgeColor: ColorSource = ColorSource.of(R.color.primaryDarkColor),
	textColor: ColorSource = ColorSource.of(R.color.primaryTextColor),

	title: StringSource = StringSource.of(R.string.app_title),
	subtitle: StringSource = StringSource.of(R.string.app_subtitle),

	onHomeClick: () -> Unit = {},

	@StyleRes theme: Int = 0,
	init: (@AnkoViewDslMarker SimpleToolbar).() -> Unit = {}
) =
	factory({ SimpleToolbar(it, id, backColor, edgeColor, textColor, title, subtitle, onHomeClick) }, theme, init)

@SuppressLint("ViewConstructor")
open class SimpleToolbar(
	context: Context,
	@IdRes id: Int? = null,

	backColor: ColorSource = ColorSource.of(R.color.primaryColor),
	edgeColor: ColorSource = ColorSource.of(R.color.primaryDarkColor),
	textColor: ColorSource = ColorSource.of(R.color.primaryTextColor),

	title: StringSource = StringSource.of(R.string.app_title),
	subtitle: StringSource = StringSource.of(R.string.app_subtitle),

	onHomeClick: () -> Unit = {}
) : _LinearLayout(context) {
	lateinit var titleView: TextView private set
	lateinit var subtitleView: TextView private set
	lateinit var iconView: ImageView private set
	val actionView: LinearLayout

	val actions
		get() = actionView.children

	var title: StringSource
		get() = titleView.text.toSource()
		set(value) {
			val text = value.getString(context)
			titleView.text = text
			titleView.isVisible = text != null
		}

	var subtitle: StringSource
		get() = subtitleView.text.toSource()
		set(value) {
			val text = value.getString(context)
			subtitleView.text = text
			subtitleView.isVisible = text != null
		}

	init {
		if (id != null) this.id = id

		gravity = CENTER
		background = createRect3D(
			backColor.getColor(context),
			edgeColor.getColor(context),
			0, 0f, 0f, 0f, 2f
		)

		linearLayout {
			gravity = CENTER

			iconView = imageView(context.applicationInfo.icon) {
				padding = dip(4)
			}.lparams(width = dip(56), height = dip(56))

			verticalLayout {
				gravity = CENTER_VERTICAL or FILL_HORIZONTAL

				titleView = appTextView(
					dark = false,
					textSize = TextSize.LargeTextSize
				) {
					padding = dip(2)
					gravity = CENTER_VERTICAL or START
					this.textColor = textColor.getColor(context)
					setShadowLayer(0f, 0f, 0f, 0)
					setLineSpacing(0f, 0f)
					title.setText(this)
				}
				subtitleView = appTextView(
					dark = false,
					textSize = TextSize.MediumTextSize
				) {
					padding = dip(2)
					gravity = CENTER_VERTICAL or START
					this.textColor = textColor.getColor(context)
					setShadowLayer(0f, 0f, 0f, 0)
					setLineSpacing(0f, 0f)
					subtitle.setText(this)
				}
			}.lparams(width = WRAP_CONTENT, height = WRAP_CONTENT)

			background = createRectGradientButton(0, 0, 0x33aaaaaa, 0, 0, 0)
			setOnClickListener {
				onHomeClick()
			}
			setOnLongClickListener {
				val text = titleView.text
				context.toast(if (text.isNullOrBlank()) BaseApp.appLabel else text)
				true
			}
		}.lparams(width = WRAP_CONTENT, height = WRAP_CONTENT)

		view()
			.lparams(width = 0, height = 0, weight = 1f)

		actionView = linearLayout {
			gravity = CENTER_VERTICAL or END
		}.lparams(width = WRAP_CONTENT, height = MATCH_PARENT)
	}

	fun action(
		@IdRes id: Int? = null,
		icon: DrawableSource = DrawableSource.NULL,
		hint: StringSource = StringSource.NULL,
		onClick: (view: View) -> Unit = {}
	) = actionView.imageView {
		val context = context!!

		if (id != null) this.id = id

		background = createRectGradientButton(0, 0, 0x33aaaaaa, 0, 0, 0)
		padding = dip(12)
		layoutParams = LayoutParams(dip(48), MATCH_PARENT)

		// image = ResourcesCompat.getDrawable(resources, imageRes, null)
		icon.setImage(this)

		val hintText = hint.getString(context)
		if (!hintText.isNullOrEmpty())
			setOnLongClickListener {
				context.toast(hintText)
				true
			}

		setOnClickListener { onClick(it) }
	}

	fun inflate(
		@MenuRes menuRes: Int,
		inflater: MenuInflater,
		listener: (MenuItem) -> Boolean
	): Menu {
		val menu = PopupMenu(context, this).menu
		inflater.inflate(menuRes, menu)
		return inflate(menu, listener)
	}

	fun inflate(
		activity: FragmentActivity,
		@MenuRes menuRes: Int
	): Menu {
		return inflate(menuRes, activity.menuInflater, activity::onOptionsItemSelected)
	}

	fun inflate(
		fragment: Fragment,
		@MenuRes menuRes: Int
	): Menu {
		return inflate(menuRes, fragment.activity!!.menuInflater, fragment::onOptionsItemSelected)
	}

	fun inflate(
		menu: Menu,
		listener: (MenuItem) -> Boolean
	): Menu {
		actionView.removeAllViews()
		menu.children.toList().asReversed().forEach {
			action(id = it.itemId, hint = StringSource.of(it.title), icon = DrawableSource.of(it.icon)) { _ ->
				listener(it)
			}.apply {
				isVisible = it.isVisible
				isEnabled = it.isEnabled
			}
		}
		return menu
	}

	fun inflate(
		listener: (MenuItem) -> Boolean,
		init: (Menu) -> Unit
	): Menu {
		return inflate(PopupMenu(context, this).menu.apply(init), listener)
	}
}
