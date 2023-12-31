package au.edu.swin.sdmd.l10_hardcell

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*

// These can be edited for your machine/device
const val HEIGHT_SHORT = 300
const val WIDTH_SHORT = 200
const val HEIGHT_LONG = 3000
const val WIDTH_LONG = 2000

//not used: val BACKGROUND = Executors.newFixedThreadPool(2)
class MainActivity : AppCompatActivity() {
//    private var width: Int = WIDTH_SHORT
//    private var height: Int = HEIGHT_SHORT
//
//    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        var width: Int = WIDTH_SHORT
        var height: Int = HEIGHT_SHORT

//        var progressBar: ProgressBar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // changing size of image to change drawing time
        val switchSize = findViewById<Switch>(R.id.largeSwitch)
        switchSize.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                height = HEIGHT_LONG
                width = WIDTH_LONG
            } else {
                height = HEIGHT_SHORT
                width = WIDTH_SHORT
            }
        }

        // on UI thread
        DrawBlockingOnUI.init(progressBar)
        val onUI = findViewById<Button>(R.id.onUI)
        onUI.setOnClickListener {
            // drawCA(::drawCASync, it)
            val rule = getRule();
            if (rule > -1) {
                updateButtonOnClick(it)
                val imageView = findViewById<ImageView>(R.id.imageView)
                DrawBlockingOnUI.draw(rule, imageView, width, height)
            }
        }

        // on looper/handler
        // looper setup and callback
        DrawThreadedLooper.init(lifecycle, progressBar)
        val button = findViewById<Button>(R.id.looper)
        button.setOnClickListener {
            // drawCA(::drawCALooper, it)
            val rule = getRule();
            if (rule > -1 ) {
                updateButtonOnClick(it)
                val imageView = findViewById<ImageView>(R.id.imageView)
                DrawThreadedLooper.draw(rule, imageView, width, height)
            }
        }

        // using coroutines (recommended)
        DrawThreadedCoroutine.init(progressBar)
        val suspendButton = findViewById<Button>(R.id.suspend)
        suspendButton.setOnClickListener {
            val rule = getRule();
            if (rule > -1 ) {
                updateButtonOnClick(it)
                val imageView = findViewById<ImageView>(R.id.imageView)
                DrawThreadedCoroutine.draw(rule, imageView, width, height)
            }
        }
    }

    /* get the rule number input. Display error message if not valid */
    private fun getRule(): Int {
        val etRule = findViewById<EditText>(R.id.etRule)
        val input = etRule.text.toString()
        if (input.isNotEmpty() && input.toInt() in 0..255) {
            return input.toInt()
        } else {
            etRule.error = "Number must be 0-255"
            return -1;
        }
    }

    /* change button appearance on click */
    private fun updateButtonOnClick(button: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button.windowToken, 0)
    }

    // cleaning up from both coroutines and handler
    override fun onDestroy() {
        super.onDestroy()
        DrawBlockingOnUI.onDestroy()
        DrawThreadedCoroutine.onDestroy();
        DrawThreadedLooper.onDestroy()
    }

}