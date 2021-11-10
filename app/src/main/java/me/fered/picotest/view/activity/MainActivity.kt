package me.fered.picotest.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import me.fered.picotest.R

class MainActivity : AppCompatActivity() {

    companion object {
        fun startMainActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            val handler = Handler()
            handler.postDelayed(
                {
                    (context as Activity).finish()
                }, 1000
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateStatusBarColorTransparent()
        tvStartGameAnimal.setOnClickListener {
            AnimalActivity.startGameActivity(this)
        }
        tvStartGameFlowers.setOnClickListener {
            FlowerActivity.startGameActivity(this)
        }


    }


    fun updateStatusBarColorTransparent() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}