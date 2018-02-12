package com.example.tonyo.alarmstopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by TONYO on 2/9/2018.
 */
class AlarmBroadcastReceiver : BroadcastReceiver (){
    override fun onReceive(context: Context?, intent: Intent?) {

        var textResult: String = intent!!.getStringExtra("extra")

        var mService = Intent(context,MainAlarm::class.java)
        mService.putExtra("extra",textResult)
        context!!.startService(mService)
    }
}