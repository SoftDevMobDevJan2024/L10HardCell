package au.edu.swin.sdmd.l10_hardcell

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DrawThreadedCoroutine {
  companion object DrawCoroutine : CoroutineScope {
    // setting up
    private lateinit var progressBar: ProgressBar
    private lateinit var job: Job

    /* override coroutineContext:
        to launch the drawing thread from the main thread with job.
       Why from the MAIN thread? so that we can update the view from
       the drawing thread
         */
    override val coroutineContext: CoroutineContext
      get() = Dispatchers.Main + job

    fun init(progressBar: ProgressBar) {
      // setup
      this.progressBar = progressBar
      job = Job() // coroutine setup
    }

    // draw on thread
     fun draw(rule: Int,
             imageView: ImageView,
             width: Int, height: Int) {
      imageView.setImageBitmap(null)
      progressBar.visibility = View.VISIBLE

      val ca = ElemCA(width, height)
      ca.setNumber(rule)

      launch {
        // one approach -- note the icon in the border
        val bm = withContext(Dispatchers.Default) {
          ca.processCA()
        }

        imageView.setImageBitmap(bm)
        // An alternative approach, for those who like async/await
        // val bm = async { ca.processCA() }
        // imageView.setImageBitmap(bm.await())
        progressBar.visibility = View.INVISIBLE
      }
    }

    fun onDestroy() {
      job.cancel()
    }
  }
}