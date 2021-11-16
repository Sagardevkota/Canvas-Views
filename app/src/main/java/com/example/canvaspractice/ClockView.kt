package com.example.canvaspractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class ClockView(context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

    private var paint = Paint()
    private var fontSize = 0
    private val numeralSpacing = 0
    private var handTruncation = 0
    private  var hourHandTruncation:kotlin.Int = 0
    private var radius = 0
    private var isInit = false;
    private var padding = 0

    private val numbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val rect: Rect = Rect()

    private fun initClock() {
        padding = numeralSpacing + 50
        fontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 13f,
            resources.displayMetrics
        ).toInt()
        //return whether height is max or width is max
        val min = height.coerceAtMost(width)
        radius = min / 2 - padding
        //reduce the size of line if its hand or hour
        handTruncation = min / 20
        hourHandTruncation = min / 7
        paint = Paint()
        isInit = true
    }

    override fun onDraw(canvas: Canvas?) {
        if (!isInit)
            initClock()

        canvas?.drawColor(Color.BLACK)
        drawCircle(canvas)
        drawCenter(canvas)
        drawNumbers(canvas)
        drawHands(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHand(canvas: Canvas?, loc: Int, isHour: Boolean) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius: Int = if (isHour) radius - handTruncation - hourHandTruncation else radius - handTruncation
        canvas?.drawLine(
             (width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2 + cos(angle) * handRadius).toFloat(),
            (height / 2 + sin(angle) * handRadius).toFloat(),
            paint
        )
    }
    private fun drawHands(canvas: Canvas?) {
        paint.color = resources.getColor(R.color.white)
        val c: Calendar = Calendar.getInstance()
        var hour: Int = c.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) hour - 12 else hour
        drawHand(canvas, ((hour + c.get(Calendar.MINUTE) / 60) * 5f).toInt(), true)
        drawHand(canvas, c.get(Calendar.MINUTE), false)
        drawHand(canvas, c.get(Calendar.SECOND), false)
    }

    private fun drawNumbers(canvas: Canvas?) {
        paint.textSize = fontSize.toFloat()
        paint.color = resources.getColor(R.color.purple_200)

        for (number in numbers) {
            val tmp = number.toString()
            paint.getTextBounds(tmp, 0, tmp.length, rect)

            //12hr = 2pi i.e 1hr = pi/6 (30deg)
            // 360deg = 2pi rad so 1 deg = 1/180pi radian
            //30deg = pi/6 and 1 in clock is 30deg 2 in clock is 60 deg so on
            val angle = Math.PI / 6 * number;
            //width/2 is x coordinate of center + cos(deg) * radius (b/h) - rectangle of number to prevent overlap of hands

            /*
            (12)
            A ______________ B   (1)
             |_|           /
             |           /
             |         /
            |        /
            |      /
            |    /
            |@ /
            |/
            O(width/2,height/2)

            sin(pi/6) = AB / r  => AB = sin(pi/6) * r;
            cos(pi/6) = OA/ r  => OA = cos(pi/6) * r;
            x=(x+AB) , y = (y - AO)
           x = width/2 + sin(pi/6) * OB(r) - rect.width/2
           y = y - cos(pi/6) * r - rect.height/2

            */
            val x = (width / 2 + sin(angle) * radius - rect.width() / 2).toInt()
            val y = (height / 2 - cos(angle) * radius + rect.height() / 2).toInt()
            canvas?.drawText(tmp, x.toFloat(), y.toFloat(), paint)
        }
    }

    private fun drawCenter(canvas: Canvas?) {
        paint.style = Paint.Style.FILL
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 12f, paint);
    }

    private fun drawCircle(canvas:Canvas?){

        val min = Math.min(height, width)
        val radius = min / 2 - padding

        paint.apply {
            reset();
            color = resources.getColor(android.R.color.holo_red_light);
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }


        canvas?.drawCircle((width / 2).toFloat(),
            (height / 2).toFloat(), (radius + padding - 10).toFloat(), paint);

    }
}