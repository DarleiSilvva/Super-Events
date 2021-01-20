package empresa_x.studio.com.superevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import empresa_x.studio.com.superevents.R
import empresa_x.studio.com.superevents.InicioActivity.LogoLauncher
import android.content.Intent
import android.view.Window
import android.widget.ImageView
import empresa_x.studio.com.superevents.MainActivity


class InicioActivity : AppCompatActivity() {
    private val SLEEP_TIMER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.inicio_do_app)
        supportActionBar!!.hide()
        val logoLauncher = LogoLauncher()
        logoLauncher.start()
    }

    private inner class LogoLauncher : Thread() {
        override fun run() {
            try {
                sleep(1000 * SLEEP_TIMER.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val intent = Intent(this@InicioActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}