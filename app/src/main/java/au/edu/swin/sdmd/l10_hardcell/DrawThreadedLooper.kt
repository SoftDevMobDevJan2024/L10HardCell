package au.edu.swin.sdmd.l10_hardcell

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle

class DrawThreadedLooper {
  companion object DrawHandler {
    // setting up for looper thread
    lateinit var looperThread: LooperThread<ImageView>
    lateinit var progressBar: ProgressBar
    lateinit var lifeCycle: Lifecycle

    fun init(lifeCycle: Lifecycle,
             progressBar: ProgressBar) {
      // looper setup and callback
      this.progressBar = progressBar
      this.lifeCycle = lifeCycle

      // create Looper thread and register it as a lifecycle's Observer
      val responseHandler = Handler(Looper.myLooper()!!)
      looperThread = LooperThread(responseHandler) {
        // call-back
        image, bitmap ->
        image.setImageBitmap(bitmap)
        progressBar.visibility = View.INVISIBLE
      }

      lifeCycle.addObserver(looperThread)
    }

    // draw on looper thread
    fun draw(rule: Int,
             imageView: ImageView,
             width: Int, height: Int) {
      imageView.setImageBitmap(null)
      progressBar.visibility = View.VISIBLE

      val ca = ElemCA(width, height)
      ca.setNumber(rule)

      looperThread.queueCA(imageView, ca)
    }

    fun onDestroy() {
      // remove looperThread as Observer
      lifeCycle.removeObserver(looperThread)
    }
  }
}