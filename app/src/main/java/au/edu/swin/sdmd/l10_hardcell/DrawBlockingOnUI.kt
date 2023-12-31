package au.edu.swin.sdmd.l10_hardcell

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

class DrawBlockingOnUI {
  companion object {
    // setting up
    lateinit var progressBar: ProgressBar
    fun init(progressBar: ProgressBar
    ) {
      // looper setup and callback
      this.progressBar = progressBar
    }

    // draw on looper thread
    fun draw(rule: Int,
             imageView: ImageView,
             width: Int, height: Int) {
      imageView.setImageBitmap(null)
      progressBar.visibility = View.VISIBLE

      val ca = ElemCA(width, height)
      ca.setNumber(rule)

      // blocking code
      ca.drawCA()

      imageView.setImageBitmap(ca.getCA())
      progressBar.visibility = View.INVISIBLE
    }

    fun onDestroy() {
      // nothing yet
    }
  }

}