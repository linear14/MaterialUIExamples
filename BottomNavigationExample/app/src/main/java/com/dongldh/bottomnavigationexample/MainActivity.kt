package com.dongldh.bottomnavigationexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        basic.setOnClickListener { startActivity(Intent(this, BasicActivity::class.java)) }
        shift.setOnClickListener { startActivity(Intent(this, ShiftActivity::class.java)) }
        icon.setOnClickListener { startActivity(Intent(this, IconActivity::class.java)) }
    }
}