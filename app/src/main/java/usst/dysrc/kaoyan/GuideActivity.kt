package usst.dysrc.kaoyan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

class GuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_guide)
        Thread(Runnable {
            Thread.sleep(3000)
            finish()
            startActivity(Intent().setClass(this, MainActivity::class.java))
        }).start()
    }
}