package com.sagardev.piechartview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


class PieChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint: Paint = Paint().apply {
        color = Color.parseColor("#0000")
        strokeWidth = 5f
        isAntiAlias = true
    }
    init {
        initChartView(context,attributeSet)
    }
    private var fontSize:Float?=null
    private var textColor:Int?=null
    private var circleColor:Int?=null
    private var lineColor:Int?=null

    private fun initChartView(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PieChartView)
        fontSize = a.getDimension(R.styleable.PieChartView_fontSize, 25f)
        textColor = a.getInt(R.styleable.PieChartView_textColor, R.color.black)
        circleColor = a.getInt(R.styleable.PieChartView_circleColor, R.color.black)
        lineColor = a.getInt(R.styleable.PieChartView_lineColor, R.color.black)

        a.recycle()
    }

    private var points = intArrayOf(20, 30, 40, 50, 80, 10, 21)

    fun setPoints(points:IntArray){
        this.points = points
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawCircle(canvas)
        drawParts(canvas)
    }

    private fun drawParts(canvas: Canvas?) {
        val padding = 10f
        val min = Math.min(height, width)
        val radius = min / 2 - padding
        val pointsSum = points.sum().toFloat()
        val centerX = width / 2f
        val centerY = height / 2f
        var accumulatedDegree = 0.0
        for (point in points) {
            val pointPercent = point.div(pointsSum) * 100
            val degree = pointPercent.div(100) * 360

            accumulatedDegree += degree
            val angle = (Math.PI / 180) * accumulatedDegree

            //to get center of triangle we need prevCircum(x,y) currentCircum(x,Y) and center(x,y)
            val prevAngle = (Math.PI / 180) * (accumulatedDegree - degree)

            val prevCircumX = (centerX + sin(prevAngle) * radius).toFloat()
            val prevCircumY = (centerY - cos(prevAngle) * radius).toFloat()

            val circumX = (centerX + sin(angle) * radius).toFloat()
            val circumY = (centerY - cos(angle) * radius).toFloat()


            val triangleCenterX = (prevCircumX + circumX + centerX) / 3f
            val triangleCenterY = (prevCircumY + circumY + centerY) / 3f


            canvas?.drawLine(centerX, centerY, circumX, circumY,
                paint.apply {
                    color = lineColor!!
                    strokeWidth = 1f

                }
            )

            canvas?.drawText(
                "${String.format("%.2f",pointPercent)} %",
                triangleCenterX,
                triangleCenterY,
                paint.apply {
                    strokeWidth = 1f
                    textSize = fontSize!!
                    color = textColor!!
                })
        }


    }

    private fun drawCircle(canvas: Canvas?) {

        val padding = 10f
        val min = Math.min(height, width)
        val radius = min / 2 - padding

        paint.apply {
            reset()
            color = circleColor!!
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }


        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (radius + padding - 10), paint
        )

    }


}