package com.example.tonyo.alarmstopwatch

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

     lateinit var mAlarmManager: AlarmManager
     lateinit var mTimePicker: TimePicker
     lateinit var mtvUpdate_Text: TextView
     lateinit var mContext: Context
     lateinit var mbtnSetAlarm: ImageView
     lateinit var mbtnStopAlarm: ImageView
     lateinit var btnStopWatch: TextView
     lateinit var btnAlarmClock: TextView
     var mHour: Int = 0
     var mMinute: Int = 0
     lateinit var mPendingIntent: PendingIntent

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()

        val mCalendar: Calendar = Calendar.getInstance()
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        btnStop.setColorFilter(Color.GRAY)
        btnStop.isEnabled = false
        btnSet.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                mCalendar.set(Calendar.HOUR_OF_DAY,mTimePicker.hour)
                mCalendar.set(Calendar.MINUTE,mTimePicker.minute)
                mCalendar.set(Calendar.SECOND,0)
                mCalendar.set(Calendar.MILLISECOND,0)
                mHour = mTimePicker.hour
                mMinute = mTimePicker.minute

            }
            else{
                mCalendar.set(Calendar.HOUR_OF_DAY,mTimePicker.currentHour)
                mCalendar.set(Calendar.MINUTE,mTimePicker.currentMinute)
                mCalendar.set(Calendar.SECOND,0)
                mCalendar.set(Calendar.MILLISECOND,0)
                mHour = mTimePicker.currentHour
                mMinute = mTimePicker.currentMinute

            }
            var tvHour: String = mHour.toString()
            var tvMin: String = mMinute.toString()
            if(mHour > 12){
                tvHour = (mHour - 12).toString()
            }
            if(mMinute < 10){
                tvMin = "0$mMinute"
            }
            setAlarmText("Alarm Set to \n: $tvHour:$tvMin")
            intent.putExtra("extra","on")
            mPendingIntent = PendingIntent.getBroadcast(this@MainActivity,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,mCalendar.timeInMillis,mPendingIntent)
            btnStop.setColorFilter(Color.WHITE)
            btnStop.isEnabled = true

        }
        btnStop.setOnClickListener {
            setAlarmText("Alarm Off")
            mPendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager.cancel(mPendingIntent)
            intent.putExtra("extra","off")
            sendBroadcast(intent)
        }
        btnAlarmClock.setTextColor(Color.GRAY)
        btnStopWatch.setOnClickListener {
            val intent = Intent(this, MainStopWatch ::class.java)
            startActivity(intent)

        }

    }

    private fun setAlarmText(s: String) {
        mtvUpdate_Text.text = s
    }

    private fun findViews() {
        mtvUpdate_Text = findViewById(R.id.tvInfo)
        mbtnSetAlarm = findViewById(R.id.btnSet)
        mbtnStopAlarm = findViewById(R.id.btnStop)
        this.mContext = this
        mTimePicker = findViewById(R.id.tvTimePicker)
        mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        btnAlarmClock = findViewById(R.id.toolbar_AlarmClock)
        btnStopWatch = findViewById<Button>(R.id.toolbar_StopWatch) as TextView
    }
    }
