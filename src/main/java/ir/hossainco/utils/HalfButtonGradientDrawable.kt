package ir.hossainco.utils

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Half Button drawables
 */
class HalfButtonGradientDrawable(
	private val flip: Boolean,
	private val padding: Float,
	private val corner: Float,
	private val middle: Float,
	private val colorStart: Int,
	private val colorEnd: Int,
	private val colorMiddleGradient: Int,
	private val colorMiddleSolid: Int,
	private val colorSolid: Int
) : Drawable() {
	private val gradientPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val middleGradientPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val middleSolidPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val solidPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

	init {
		middleGradientPaint.style = Paint.Style.FILL
		middleGradientPaint.color = colorMiddleGradient
		middleSolidPaint.style = Paint.Style.FILL
		middleSolidPaint.color = colorMiddleSolid
		solidPaint.style = Paint.Style.FILL
		solidPaint.color = colorSolid
	}

	override fun draw(canvas: Canvas) {
		val left = padding
		val top = padding
		val right = canvas.width - padding
		val bottom = canvas.height - padding
		val height = bottom - top
		val cy = height / 2f + padding

		if (flip) {
			/* solid */
			val solidPath = Drawables.createRoundRectPath(left, top, right, cy, corner, corner, 0f, 0f)
			canvas.drawPath(solidPath, solidPaint)
			/* gradient */
			gradientPaint.shader = LinearGradient(0f, 0f, 0f, cy, colorStart, colorEnd, Shader.TileMode.MIRROR)
			val gradientPath = Drawables.createRoundRectPath(left, cy, right, bottom, 0f, 0f, corner, corner)
			canvas.drawPath(gradientPath, gradientPaint)
			/* middleSolid */
			canvas.drawRect(left, cy, right, cy - middle, middleSolidPaint)
			/* middleGradient */
			canvas.drawRect(left, cy + middle, right, cy, middleGradientPaint)
		} else {
			/* gradient */
			gradientPaint.shader = LinearGradient(0f, 0f, 0f, cy, colorStart, colorEnd, Shader.TileMode.MIRROR)
			val gradientPath = Drawables.createRoundRectPath(left, top, right, cy, corner, corner, 0f, 0f)
			canvas.drawPath(gradientPath, gradientPaint)
			/* solid */
			val solidPath = Drawables.createRoundRectPath(left, cy, right, bottom, 0f, 0f, corner, corner)
			canvas.drawPath(solidPath, solidPaint)
			/* middleGradient */
			canvas.drawRect(left, cy - middle, right, cy, middleGradientPaint)
			/* middleSolid */
			canvas.drawRect(left, cy, right, cy + middle, middleSolidPaint)
		}
	}

	override fun setAlpha(alpha: Int) {}

	override fun setColorFilter(cf: ColorFilter?) {}

	override fun getOpacity() = PixelFormat.TRANSPARENT

	override fun getConstantState() = object : Drawable.ConstantState() {
		override fun newDrawable() =
			HalfButtonGradientDrawable(
				flip,
				padding,
				corner,
				middle,
				colorStart,
				colorEnd,
				colorMiddleGradient,
				colorMiddleSolid,
				colorSolid
			)

		override fun getChangingConfigurations() =
			0
	}
}
