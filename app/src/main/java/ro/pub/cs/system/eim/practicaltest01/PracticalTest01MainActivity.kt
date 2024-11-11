package ro.pub.cs.system.eim.practicaltest01

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01MainActivity : AppCompatActivity() {

    private var selectedDirectionsCount = 0
    private lateinit var textDirections: TextView
    private lateinit var broadcastReceiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviul_1_main)

        textDirections = findViewById(R.id.text_directions)

        if (savedInstanceState != null) {
            selectedDirectionsCount = savedInstanceState.getInt("selectedDirectionsCount", 0)
            textDirections.text = savedInstanceState.getString("textDirections", "")
        }

        fun updateDirectionText(direction: String) {
            val currentText = textDirections.text.toString()
            textDirections.text = if (currentText.isEmpty()) {
                direction
            } else {
                "$currentText, $direction"
            }
            selectedDirectionsCount++
            textDirections.append("\nTotal directions selected: $selectedDirectionsCount")
        }

        findViewById<Button>(R.id.button_north).setOnClickListener {
            updateDirectionText("North")
        }

        findViewById<Button>(R.id.button_south).setOnClickListener {
            updateDirectionText("South")
        }

        findViewById<Button>(R.id.button_east).setOnClickListener {
            updateDirectionText("East")
        }

        findViewById<Button>(R.id.button_west).setOnClickListener {
            updateDirectionText("West")
        }

        findViewById<Button>(R.id.button_navigate).setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("instructions", textDirections.text.toString())
            startActivityForResult(intent, 1)
        }

        findViewById<Button>(R.id.button_reset_and_navigate).setOnClickListener {
            selectedDirectionsCount = 0
            textDirections.text = ""
            val intent = Intent(this, SecondaryActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_start_service).setOnClickListener {
            val instructions = textDirections.text.toString()
            if (instructions.split(", ").size == 4) {
                val intent = Intent(this, Colocviu1_1Service::class.java)
                intent.putExtra("instructions", instructions)
                startService(intent)
                Log.d("PracticalTest01", "Service started")
            } else {
                Toast.makeText(this, "Please select four directions", Toast.LENGTH_SHORT).show()
            }
        }

        broadcastReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter("ro.pub.cs.system.eim.practicaltest01.broadcast")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedDirectionsCount", selectedDirectionsCount)
        outState.putString("textDirections", textDirections.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val buttonPressed = data?.getStringExtra("button")
            Toast.makeText(this, "Button pressed: $buttonPressed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        stopService(Intent(this, Colocviu1_1Service::class.java))
        Log.d("PracticalTest01", "Service stopped")
    }
}