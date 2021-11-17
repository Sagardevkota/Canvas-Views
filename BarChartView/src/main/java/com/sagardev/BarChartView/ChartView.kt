package com.sagardev.BarChartView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Comparator

class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    init {
        initChartView(context, attrs)
    }

    private fun initChartView(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ChartView)
        barColor = a.getInt(R.styleable.ChartView_barColor, R.color.purple_200)
        textColor = a.getInt(R.styleable.ChartView_textColor, R.color.purple_200)
        pointsColor = a.getInt(R.styleable.ChartView_pointsColor, R.color.purple_200)
        numberSpacing = a.getInt(R.styleable.ChartView_numberSpacing, 10)
        a.recycle()
    }

    private var barColor: Int? = null
    private var textColor: Int? = null
    private var pointsColor: Int? = null
    private var numberSpacing: Int? = null
    private var items: List<Item> = listOf(
        Item("This", 20),
        Item("Is", 50),
        Item("Fake", 10),
        Item("Data", 80),
        Item("Unless", 120),
        Item("Set By", 10),
        Item("You", 80),
    )

    private val paint = Paint().apply {
        color = Color.parseColor("#0000")
        strokeWidth = 2f
    }

    data class Item(val name: String, val value: Int)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawChart(canvas)
    }


    fun setItems(array: List<Item>) {
        this.items = array
    }

    private fun drawChart(canvas: Canvas?) {

        //for items name
        val bottomPadding = 40f
        //space before drawing lines and graphs
        val leftPadding = 70f
        //find the max value from list of item.value
        val maxValue = items.maxWithOrNull(Comparator.comparingInt { it.value })?.value ?: 0

        val heightMinusBtmSpace = height - bottomPadding
        //divide height by max value to get pixels per unit value vertically
        val PPU_VERTICAL = heightMinusBtmSpace.div(maxValue)
        val widthMinusLeftSpace = width - leftPadding
        //divide width by number of items to get pixels per item value horizontally
        val PPU_HORIZONTAL = widthMinusLeftSpace.div(items.size)
        //25% part for left and 25% right gap of horizontal bar and multiply with per pixel unit of item
        val barLeftRightSpace = 0.25f * PPU_HORIZONTAL
        // 50% for width of bar
        val barWidth = 0.5f * PPU_HORIZONTAL


        paint.apply {
            textSize = height.toFloat() / 50
        }

        //vertical line
        canvas?.drawLine(
            leftPadding - 10f,
            0f,
            leftPadding - 10f,
            heightMinusBtmSpace,
            paint
        )

        //horizontal line
        canvas?.drawLine(
            leftPadding - 10f,
            heightMinusBtmSpace,
            width.toFloat(),
            heightMinusBtmSpace,
            paint
        )


        var i = 0

        //loop until i goes up to the max item.value
        while (i <= maxValue) {
            //draw 0,20,40,60 with i + numberspacing
            canvas?.drawText(
                "$i ",
                leftPadding - 60f,
                heightMinusBtmSpace - i * PPU_VERTICAL + 20f, //it is above the height - (10,20,40)* per pixel
                paint
            )
            i += numberSpacing!!

        }
        var j = 0
        //loop through item
        for (item in items) {
            if (item.value < 0) continue

            val value = item.value

            /*
            |      |     |
            |      |     |
            |      |     |
            |      |___ _|
            |___________________________________
               <-------- 1000------------------->
      70f                  30   60    30       30   60    30
     leftpadding           Space Width Space  Space Width Space
                           25%   50%   25%        25%   50%   25%
     0PPU   +  leftpadding + 30 + 1 PPU + leftpadding+ 30 + 2PPU...........

             120 ppu

            *
            *
            * */
            canvas?.drawRect(
                PPU_HORIZONTAL * j + leftPadding + barLeftRightSpace,
                heightMinusBtmSpace - value * PPU_VERTICAL,
                PPU_HORIZONTAL * j + leftPadding + barLeftRightSpace + barWidth,
                heightMinusBtmSpace,
                paint.apply { color = barColor!! }
            )

            //items name below the graph
            canvas?.drawText(
                "${item.name} ",
                PPU_HORIZONTAL * j + leftPadding + barLeftRightSpace,
                height.toFloat() - 10f,
                paint.apply { color = textColor!! }
            )
            j++

            //points
            canvas?.drawText(
                "$value ",
                leftPadding - 60f,
                heightMinusBtmSpace - value * PPU_VERTICAL + 20f,
                paint.apply { color = pointsColor!! }
            )


        }


    }

}