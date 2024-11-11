package ro.pub.cs.system.eim.practicaltest01

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            setResult(Activity.RESULT_OK, intent.putExtra("button", "Register"))
            finish()
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            setResult(Activity.RESULT_OK, intent.putExtra("button", "Cancel"))
            finish()
        }
    }
}