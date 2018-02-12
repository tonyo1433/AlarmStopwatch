package com.example.tonyo.alarmstopwatch

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by TONYO on 2/9/2018.
 */
class MainStopWatch : AppCompatActivity() {

     lateinit var mBtnStart: ImageView
     lateinit var mBtnPause: ImageView
     lateinit var mBtnReset: ImageView
     lateinit var mBtnLap: ImageView
     lateinit var mTxtPlay: TextView
     lateinit var mTxtPause: TextView
     lateinit var mTxtReset: TextView
     lateinit var mTxtLap: TextView
     lateinit var mBtnStopWatch: TextView
     lateinit var mBtnAlarmClock: TextView
     lateinit var mTimeContainer: LinearLayout
    lateinit var mHandler: Handler
    lateinit var mTxtTimeView: TextView
    var startTime: Long = 0L
    var timeBuff: Long = 0L
    var updateTime: Long = 0L
    var milliSecondTime: Long = 0L
    var sec: Int = 0
    var min: Int = 0
    var millisec: Int = 0
    private val runnable = object : Runnable {
        override fun run() {
            milliSecondTime = SystemClock.uptimeMillis() - startTime
            updateTime = timeBuff + milliSecondTime
            sec = (updateTime / 1000).toInt()
            min = sec / 60
            sec %= 60
            millisec = (updateTime % 1000).toInt()
            mTxtTimeView.text = ("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%2d", millisec))
            mHandler.postDelayed(this, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)
        findViews()

        mBtnStopWatch.setTextColor(Color.GRAY)
        mBtnAlarmClock.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mHandler = Handler()
        setDisableButtonORsetColor()
        buttonOnClicks()

    }

    private fun buttonOnClicks() {
        mBtnStart.setOnClickListener {
            startTime()
        }
        mBtnPause.setOnClickListener {
            pauseTime()
        }
        mBtnReset.setOnClickListener {
            resetTime()
        }
    }

    private fun resetTime() {
        milliSecondTime = 0L
        startTime = 0L
        timeBuff = 0L
        updateTime = 0L
        sec = 0
        min = 0
        millisec = 0
        mTxtTimeView.text = "00:00:00"
        mTimeContainer.removeAllViews()
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mTxtReset.setTextColor(Color.GRAY)
    }

    private fun pauseTime() {
        timeBuff += milliSecondTime
        mHandler.removeCallbacks(runnable)
        mBtnPause.visibility = View.INVISIBLE
        mBtnStart.visibility = View.VISIBLE
        mBtnReset.isEnabled = true
        mBtnReset.setColorFilter(Color.WHITE)
        mTxtReset.setTextColor(Color.WHITE)
        mBtnLap.isEnabled = false
        mBtnLap.setColorFilter(Color.GRAY)
        mTxtLap.setTextColor(Color.GRAY)
        mBtnPause.visibility = View.INVISIBLE
        mTxtPlay.visibility = View.VISIBLE
    }

    private fun startTime() {
        startTime = SystemClock.uptimeMillis()
        mHandler.postDelayed(runnable, 0)
        mBtnStart.visibility = View.INVISIBLE
        mBtnPause.visibility = View.VISIBLE
        mBtnLap.isEnabled = true
        mBtnLap.setColorFilter(Color.WHITE)
        mTxtLap.setTextColor(Color.WHITE)
        mTxtPause.visibility = View.VISIBLE
        mTxtPause.visibility = View.VISIBLE
        mTxtPlay.visibility = View.INVISIBLE
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mTxtReset.setTextColor(Color.GRAY)
    }

    private fun setDisableButtonORsetColor() {
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mTxtReset.setTextColor(Color.GRAY)
        mBtnLap.isEnabled = false
        mBtnLap.setColorFilter(Color.GRAY)
        mTxtLap.setTextColor(Color.GRAY)
    }

    private fun findViews() {
        mBtnStart = findViewById(R.id.btnStart)
        mBtnPause = findViewById(R.id.btnPause)
        mBtnReset = findViewById(R.id.btnReset)
        mTxtTimeView = findViewById(R.id.tvTimer)
        mBtnAlarmClock = findViewById(R.id.toolbar_AlarmClock)
        mBtnStopWatch = findViewById(R.id.toolbar_StopWatch)
    }
}