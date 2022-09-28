package com.karson.draganddraw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

private const val TAG = "BoxDrawingView"

class BoxDrawingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var currentBox: Box? = null

    private val boxen = mutableListOf<Box>()

    private val boxPaint = Paint().apply {
        color = 0x22ff0000
    }

    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

//    private lateinit var currentPoint: PointF
//
//    private val gestureListener = object : GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
//        override fun onDown(event: MotionEvent): Boolean {
//            currentBox = Box(currentPoint)
//            boxen.add(currentBox!!)
//            return true
//        }
//
//        override fun onShowPress(e: MotionEvent?) {
//            Log.i(TAG, "onShowPress")
//        }
//
//        override fun onSingleTapUp(e: MotionEvent?): Boolean {
//            Log.i(TAG, "onSingleTapUp")
//            return false
//        }
//
//        override fun onScroll(
//            e1: MotionEvent?,
//            e2: MotionEvent?,
//            distanceX: Float,
//            distanceY: Float
//        ): Boolean {
//            Log.i(TAG, "onScroll")
//            updateCurrentBox(currentPoint)
//            return true
//        }
//
//        override fun onLongPress(e: MotionEvent) {
//            Log.i(TAG, "onLongPress" + e.action)
//        }
//
//        val FLING_MIN_DISTANCE = 100
//        val FLING_MIN_VELOCITY = 200
//
//        override fun onFling(
//            e1: MotionEvent,
//            e2: MotionEvent,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            Log.i(TAG, "onFling")
//            if (e1.x - e2.x > FLING_MIN_DISTANCE && abs(velocityX) > FLING_MIN_VELOCITY) {
//                Log.i(TAG, "onFling left")
//            } else if (e2.x - e1.x > FLING_MIN_DISTANCE && abs(velocityX) > FLING_MIN_VELOCITY) {
//                Log.i(TAG, "onFling right")
//            }
//            updateCurrentBox(currentPoint)
//            currentBox = null
//            return true
//        }
//
//        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//            Log.i(TAG, "onSingleTapConfirmed")
//            return true
//        }
//
//        override fun onDoubleTap(e: MotionEvent): Boolean {
//            Log.i(TAG, "onDoubleTap")
//            return true
//        }
//
//        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
//            Log.i(TAG, "onDoubleTap")
//            return true
//        }
//
//    }
//
//    private val gestureDetector = GestureDetectorCompat(context, gestureListener)
//
//    init {
//        gestureDetector.setIsLongpressEnabled(false)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        currentPoint = PointF(event.x, event.y)
//        return gestureDetector.onTouchEvent(event)
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            //事件被父类拦截
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.i(TAG, "current $current action $action")
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPaint(backgroundPaint)
        boxen.forEach { box ->
//            Log.i(TAG, "boxen ${box.top} ${box.bottom} ${boxen.size}")
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }


}