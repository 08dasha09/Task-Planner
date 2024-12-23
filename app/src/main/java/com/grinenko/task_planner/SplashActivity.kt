package com.grinenko.task_planner

import android.annotation.SuppressLint
import android.widget.RelativeLayout
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TaskPlanner_Splash)

        val splashText = TextView(this).apply {
            text = "АІ234 Гриненко Д.С."
            textSize = 24f
            setTextColor(resources.getColor(android.R.color.black))
            setPadding(16, 16, 16, 16)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                addRule(RelativeLayout.CENTER_HORIZONTAL)
            }
        }

        val layout = RelativeLayout(this)
        layout.addView(splashText)

        setContentView(layout)

        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2 seconds
    }
}
