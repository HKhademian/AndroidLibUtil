package ir.hossainco.utils.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.widget.TextView

/** source: https://stackoverflow.com/questions/10754265/androiddraw-line-on-a-textview/10770670#10770670 */
class LinedTextView(context: Context, lineColor: Int? = null, lineColorRes: Int? = null) : TextView(context) {
	private val rect = Rect()
	private val paint = Paint()

	init {
		paint.style = Paint.Style.STROKE
		paint.color = when {
			lineColor != null -> lineColor
			lineColorRes != null -> context.resources.getColor(lineColorRes)
			else -> Color.CYAN
		}
	}

	override fun onDraw(canvas: Canvas) {
		val count = lineCount
		val rect = rect
		val paint = paint
		val height = measuredHeight
		val width = measuredWidth

		rect.set(0, 0, width, 0)

		var last = 0
		for (i in 0 until count) {
			val baseline = getLineBounds(i, rect)
			last = baseline
			canvas.drawLine(rect.left.toFloat(), baseline + 1f, rect.right.toFloat(), baseline + 1f, paint)
		}

		val baseline = when {
			count > 0 -> last / count
			else -> 60
		}

		for (i in count until ((height / baseline) + 1))
			canvas.drawLine(
				rect.left.toFloat(),
				baseline * (i + 1) + 1f,
				rect.right.toFloat(),
				baseline * (i + 1) + 1f,
				paint
			)

		super.onDraw(canvas)
	}
}
