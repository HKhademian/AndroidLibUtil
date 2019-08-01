@file:Suppress("unused", "NAME_SHADOWING", "MemberVisibilityCanBePrivate", "PackageDirectoryMismatch")
@file:SuppressLint("StaticFieldLeak")

package ir.hossainco.utils.ui.drawables

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.Config
import android.graphics.Bitmap.createBitmap
import android.graphics.Color.BLACK
import android.graphics.Shader.TileMode
import android.graphics.drawable.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.provider.MediaStore
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.TextView
import ir.hco.util.BaseApp.Companion.res
import ir.hco.util.BaseApp.Companion.context
import java.lang.Math.*
import kotlin.math.roundToInt

fun dp2px(dp: Float) = (dp * res.displayMetrics.density).roundToInt()
fun sp2px(sp: Float) = (sp * res.displayMetrics.scaledDensity).roundToInt()


fun createScaleBitmap(bitmap: Bitmap, fit: Boolean, width: Int, height: Int, filter: Boolean) = try {
	Bitmap.createScaledBitmap(createSquareBitmap(bitmap, fit)!!, width, height, filter)
} catch (e: Throwable) {
	null
}

fun createSquareBitmap(source: Bitmap?, fit: Boolean) =
	if (source != null) try {
		val size = if (fit) max(source.width, source.height) else min(source.width, source.height)
		val bitmap = Bitmap.createBitmap(size, size, source.config)
		val canvas = Canvas(bitmap)
		canvas.drawBitmap(source, (size - source.width) / 2f, (size - source.height) / 2f, null)
		bitmap
	} catch (e: Throwable) {
		null
	}
	else null

fun recycleBitmap(bitmap: Bitmap) = try {
	bitmap.recycle()
} catch (e: Throwable) {
}

fun copyDrawable(drawable: Drawable?) = try {
	if (drawable == null)
		null
	else
		drawable.constantState!!.newDrawable()
} catch (e: Throwable) {
	null
}


fun createPaddingDrawable(
	left: Int,
	top: Int,
	right: Int,
	bottom: Int,
	vararg drawables: Drawable
) = try {
	val drawable = LayerDrawable(drawables)
	drawable.setLayerInset(0, left, top, right, bottom)
	drawable
} catch (e: Throwable) {
	null
}

fun createPaddingDrawable(leftRight: Int, topBottom: Int, vararg drawables: Drawable) =
	createPaddingDrawable(leftRight, topBottom, leftRight, topBottom, *drawables)

fun createPaddingDrawable(padding: Int, vararg drawables: Drawable) =
	createPaddingDrawable(padding, padding, padding, padding, *drawables)

fun createPaddingDrawable(
	left: Float,
	top: Float,
	right: Float,
	bottom: Float,
	vararg drawables: Drawable
) =
	createPaddingDrawable(
		dp2px(left),
		dp2px(top),
		dp2px(right),
		dp2px(bottom),
		*drawables
	)

fun createPaddingDrawable(leftRight: Float, topBottom: Float, vararg drawables: Drawable) =
	createPaddingDrawable(leftRight, topBottom, leftRight, topBottom, *drawables)

fun createPaddingDrawable(padding: Float, vararg drawables: Drawable) =
	createPaddingDrawable(padding, padding, padding, padding, *drawables)

fun createTextureDrawable(
	sizeDp: Float,
	color: Int,
	textureColor: Int,
	slash: Boolean,
	backslash: Boolean
): BitmapDrawable? {
	val size = dp2px(sizeDp)
	val paint = Paint(/* Paint.ANTI_ALIAS_FLAG */)
	paint.color = textureColor
	val bitmap = createBitmap(size, size, Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	canvas.drawColor(color)
	if (slash)
		for (i in 0 until size) {
			val y = i.toFloat()
			val x1 = (size - i).toFloat()
			var x2 = (size - i - 1).toFloat()
			if (x2 < 0)
				x2 += size
			canvas.drawPoint(x1, y, paint)
			canvas.drawPoint(x2, y, paint)
		}
	if (backslash)
		for (i in 0 until size) {
			val y = i.toFloat()
			val x1 = i.toFloat()
			var x2 = (i + 1).toFloat()
			if (x2 >= size)
				x2 -= size
			canvas.drawPoint(x1, y, paint)
			canvas.drawPoint(x2, y, paint)
		}
	return createTileDrawable(BitmapDrawable(res, bitmap))
}


fun createGradientDrawable(
	shape: Int,
	color: Int,
	borderColor: Int,
	border: Float,
	corner: Float
): GradientDrawable {
	val drawable = GradientDrawable()
	drawable.shape = shape
	drawable.cornerRadius = corner
	drawable.setStroke(dp2px(border), borderColor)
	drawable.setColor(color)
	return drawable
}

fun createShapeDrawable(color: Int, shape: Shape): ShapeDrawable {
	val drawable = ShapeDrawable(shape)
	drawable.paint.color = color
	return drawable
}


fun createRectGradient(color: Int, borderColor: Int, border: Float, corner: Float) =
	createGradientDrawable(
		GradientDrawable.RECTANGLE,
		color,
		borderColor,
		border,
		corner
	)

fun createOvalGradient(color: Int, borderColor: Int, border: Float) =
	createGradientDrawable(GradientDrawable.OVAL, color, borderColor, border, 0f)


fun createRectShape(color: Int, corner: Float) =
	createShapeDrawable(
		color,
		RoundRectShape(floatArrayOf(corner, corner, corner, corner, corner, corner, corner, corner), null, null)
	)

fun createOvalShape(color: Int) =
	createShapeDrawable(color, OvalShape())


fun createRectDrawable(color: Int, borderColor: Int, border: Float, corner: Float) =
	when {
		border > 0 -> createRectGradient(color, borderColor, border, corner)
		corner > 0 -> createRectShape(color, corner)
		else -> ColorDrawable(color)
	}

fun createOvalDrawable(color: Int, borderColor: Int, border: Float) =
	if (border <= 0)
		createOvalShape(color)
	else
		createOvalGradient(color, borderColor, border)

/* *** */

fun create3DDrawable(front: Drawable?, back: Drawable?, padding: Float, difference: Float): Drawable? {
	val top = difference < 0
	val pad = dp2px(padding)
	val def = dp2px(abs(difference))

	if (back == null)
		return when {
			pad == 0 -> front
			front == null -> null
			else -> createPaddingDrawable(pad, front)
		}

	if (front == null)
		return if (pad == 0) back
		else createPaddingDrawable(pad, back)

	if (def == 0)
		return front

	val drawable = createPaddingDrawable(pad, back, front)
	drawable!!.setLayerInset(1, pad, pad + if (top) def else 0, pad, pad + if (!top) def else 0)
	return drawable
}

fun createRect3D(
	frontColor: Int,
	backColor: Int,
	borderColor: Int,
	border: Float,
	corner: Float,
	padding: Float,
	difference: Float
) = create3DDrawable(
	front = createRectDrawable(frontColor, borderColor, border, corner),
	back = if (difference == 0f) null else createRectDrawable(
		backColor,
		borderColor,
		border,
		corner
	),
	padding = padding,
	difference = difference
)

fun createOval3D(
	frontColor: Int,
	backColor: Int,
	borderColor: Int,
	border: Float,
	padding: Float,
	difference: Float
) = create3DDrawable(
	front = createOvalDrawable(frontColor, borderColor, border),
	back = if (difference == 0f) null else createOvalDrawable(
		backColor,
		borderColor,
		border
	),
	padding = padding,
	difference = difference
)

/* *** */

fun createTileDrawable(drawable: BitmapDrawable?): BitmapDrawable? {
	drawable?.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT)
	return drawable
}

fun createTileDrawable(bitmap: Bitmap) = try {
	createTileDrawable(BitmapDrawable(res, bitmap))
} catch (e: Throwable) {
	null
}

fun createTileDrawable(resId: Int) =
	createTileDrawable(BitmapFactory.decodeResource(res, resId))

fun createSignDrawable(text: String, sizeDp: Float, sizeSp: Float, color: Int, typeface: Typeface): BitmapDrawable {
	val size = dp2px(sizeDp)
	val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	paint.typeface = typeface
	paint.color = color
	paint.textSize = sp2px(sizeSp).toFloat()
	// paint.setShadowLayer(1f, 0f, 1f, Colors.huv(color, 0.75f));
	paint.isAntiAlias = true
	val bounds = Rect()
	paint.getTextBounds(text, 0, text.length, bounds)

	val bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	val x = (bitmap.width - bounds.width()) / 2
	val y = (bitmap.height + bounds.height()) / 2
	canvas.drawText(text, x.toFloat(), y.toFloat(), paint)

	return BitmapDrawable(res, bitmap)
}

fun createButton(primary: Drawable?, secondary: Drawable?, disabled: Drawable?): StateListDrawable {
	val drawable = StateListDrawable()
	if (disabled != null)
		drawable.addState(intArrayOf(-android.R.attr.state_enabled), disabled)
	drawable.addState(intArrayOf(android.R.attr.state_selected), secondary)
	drawable.addState(intArrayOf(android.R.attr.state_focused), secondary)
	if (SDK_INT >= 14)
		drawable.addState(intArrayOf(android.R.attr.state_hovered), secondary)
	drawable.addState(intArrayOf(android.R.attr.state_pressed), secondary)
	drawable.addState(intArrayOf(), primary)
	return drawable
}

fun createRectGradientButton(
	primaryBackColor: Int,
	primaryBorderColor: Int,
	secondaryBackColor: Int,
	secondaryBorderColor: Int,
	disabledBackColor: Int,
	disabledBorderColor: Int,
	border: Float = 1f,
	corner: Float = 4f
) = createButton(
	primary = createRectGradient(primaryBackColor, primaryBorderColor, border, corner),
	secondary = createRectGradient(
		secondaryBackColor,
		secondaryBorderColor,
		border,
		corner
	),
	disabled = createRectGradient(
		disabledBackColor,
		disabledBorderColor,
		border,
		corner
	)
)

fun createOvalGradientButton(
	primaryBackColor: Int,
	primaryBorderColor: Int,
	secondaryBackColor: Int,
	secondaryBorderColor: Int,
	disabledBackColor: Int,
	disabledBorderColor: Int,
	border: Float = 1f
) = createButton(
	primary = createOvalGradient(primaryBackColor, primaryBorderColor, border),
	secondary = createOvalGradient(secondaryBackColor, secondaryBorderColor, border),
	disabled = createOvalGradient(disabledBackColor, disabledBorderColor, border)
)

fun create3DButton(
	front: ShapeDrawable,
	back: ShapeDrawable,
	disabled: ShapeDrawable,
	padding: Float,
	difference: Float
) = createButton(
	primary = create3DDrawable(front, back, padding, difference),
	secondary = create3DDrawable(back, disabled, padding, difference / 2f),
	disabled = disabled
)

fun createRect3DButton(
	primaryColor: Int,
	secondaryColor: Int,
	disabledColor: Int,
	border: Float,
	corner: Float,
	padding: Float,
	difference: Float
) = createButton(
	primary = createRect3D(
		primaryColor,
		secondaryColor,
		disabledColor,
		border,
		corner,
		padding,
		difference
	),
	secondary = createRect3D(
		secondaryColor,
		disabledColor,
		disabledColor,
		border,
		corner,
		padding,
		difference / 2f
	),
	disabled = createRectShape(disabledColor, corner)
)

fun createOval3DButton(
	primaryColor: Int,
	secondaryColor: Int,
	disabledColor: Int,
	border: Float,
	padding: Float,
	difference: Float
) = createButton(
	primary = createOval3D(
		primaryColor,
		secondaryColor,
		disabledColor,
		border,
		padding,
		difference
	),
	secondary = createOval3D(
		secondaryColor,
		disabledColor,
		disabledColor,
		border,
		padding,
		difference / 2f
	),
	disabled = createOvalShape(disabledColor)
)


/* Canvas */

fun draw(context: Context, canvas: Canvas, text: CharSequence) {
	//	draw(canvas, "Venison turkey short loin  pork belly tri-tip. Pastrami jerky pancetta tail salami shoulder. Ribeye ball tip short loin, andouille shankle pork chop shank short ribs pork belly tongue jerky beef venison pig pork. Bresaola t-bone cow rump flank. Ball tip spare ribs strip steak, cow beef ribs corned beef chuck chicken salami hamburger shankle drumstick. Venison ham beef sirloin, pastrami meatloaf brisket tail ball tip chicken bacon. Shank shoulder tail cow short loin tenderloin, pork ground round tongue salami jowl short ribs turkey t-bone fatback.");
	val view = TextView(context)
	view.text = text
	view.setTextColor(BLACK)
	view.measure(
		makeMeasureSpec(canvas.width, View.MeasureSpec.EXACTLY),
		makeMeasureSpec(canvas.height, View.MeasureSpec.EXACTLY)
	)
	view.layout(50, 50, view.measuredWidth, view.measuredHeight)
	draw(canvas, view)
}

fun draw(canvas: Canvas, view: View) {
	canvas.save()
	canvas.translate(view.left.toFloat(), view.top.toFloat())
	view.draw(canvas)
	canvas.restore()
	//	view.setDrawingCacheEnabled(true);
	//	final Bitmap viewCache = view.getDrawingCache();
	//	if (viewCache != null)
	//		canvas.drawBitmap(viewCache, view.getLeft(), view.getTop(), null);
	//	view.setDrawingCacheEnabled(false);
}

fun createRoundRectPath(
	left: Float,
	top: Float,
	right: Float,
	bottom: Float,
	rx: Float,
	ry: Float,
	drawTopCorners: Boolean,
	drawBottomCorners: Boolean
): Path {
	var rx = rx
	var ry = ry
	val path = Path()
	val width = right - left
	val height = bottom - top
	if (rx < 0) rx = 0f
	if (ry < 0) ry = 0f
	if (rx > width / 2) rx = width / 2
	if (ry > height / 2) ry = height / 2
	val widthMinusCorners = width - 2 * rx
	val heightMinusCorners = height - 2 * ry

	if (drawTopCorners) {
		path.moveTo(right, top + ry)
		path.rQuadTo(0f, -ry, -rx, -ry)//top-right corner
		path.rLineTo(-widthMinusCorners, 0f)
		path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
		path.rLineTo(0f, heightMinusCorners)
	} else {
		path.moveTo(right, top)
		path.rLineTo(-widthMinusCorners, 0f)
		path.rLineTo(0f, heightMinusCorners)
	}
	if (drawBottomCorners) {
		path.rQuadTo(0f, ry, rx, ry)//bottom-left corner
		path.rLineTo(widthMinusCorners, 0f)
		path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
	} else {
		path.rLineTo(0f, ry)
		path.rLineTo(width, 0f)
		path.rLineTo(0f, -ry)
	}

	path.rLineTo(0f, -heightMinusCorners)
	path.close()

	return path
}

/* Path */

fun createRoundRectPath(
	left: Float,
	top: Float,
	right: Float,
	bottom: Float,
	tl: Float,
	tr: Float,
	bl: Float,
	br: Float
): Path {
	var tl = tl
	var tr = tr
	var bl = bl
	var br = br
	val path = Path()
	val rect = RectF()
	val width = right - left
	val height = bottom - top
	val maxCorner = min(width / 2, height / 2)
	if (tl < 0) tl = 0f
	if (tl > maxCorner) tl = maxCorner
	if (tr < 0) tr = 0f
	if (tr > maxCorner) tr = maxCorner
	if (bl < 0) bl = 0f
	if (bl > maxCorner) bl = maxCorner
	if (br < 0) br = 0f
	if (br > maxCorner) br = maxCorner

	//top-left corner
	path.moveTo(left, top + tl)
	rect.set(right - 2 * tl, top, right, top + 2 * tl)
	path.arcTo(rect, 0f, -90f, false)

	//top-right corner
	path.rLineTo(width - tl - tr, 0f)
	rect.set(left, top, left + 2 * tr, top + 2 * tr)
	path.arcTo(rect, 270f, -90f, false)

	//bottom-right corner
	path.rLineTo(0f, height - tr - br)
	rect.set(left, bottom - 2 * br, left + 2 * br, bottom)
	path.arcTo(rect, 180f, -90f, false)

	//bottom-left corner
	path.rLineTo(-width + br + bl, 0f)
	rect.set(right - 2 * bl, bottom - 2 * bl, right, bottom)
	path.arcTo(rect, 90f, -90f, false)

	path.rLineTo(0f, -height + bl + tl)

	path.close()

	return path
}

fun toBitmap(drawable: Drawable?): Bitmap? {
	if (drawable == null) return null
	if (drawable is BitmapDrawable) return drawable.bitmap

	val intrinsicWidth = drawable.intrinsicWidth
	val intrinsicHeight = drawable.intrinsicHeight

	if (intrinsicWidth > 0 && intrinsicHeight > 0)
		try {
			val bitmap = createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888)
			val canvas = Canvas(bitmap)
			drawable.setBounds(0, 0, canvas.width, canvas.height)
			drawable.draw(canvas)
			return bitmap
		} catch (e: Throwable) {
			e.printStackTrace()
		}

	return null
}

/* toBitmap */

fun toBitmap(resid: Int) =
	BitmapFactory.decodeResource(res, resid)

fun toBitmap(uri: Uri) = try {
	MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
} catch (e: Throwable) {
	null
}
