package ro.pub.cs.system.eim.practicaltest01

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Colocviu1_1Service : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val instructions = intent?.getStringExtra("instructions") ?: ""

        Handler(Looper.getMainLooper()).postDelayed({
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val broadcastIntent = Intent("ro.pub.cs.system.eim.practicaltest01.broadcast")
            broadcastIntent.putExtra("message", "Time: $currentTime, Instructions: $instructions")
            sendBroadcast(broadcastIntent)
            Log.d("Colocviu1_1Service", "Broadcast sent: Time: $currentTime, Instructions: $instructions")
            stopSelf()
        }, 5000)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}