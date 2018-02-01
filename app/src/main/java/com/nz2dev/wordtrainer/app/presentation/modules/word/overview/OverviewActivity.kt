package com.nz2dev.wordtrainer.app.presentation.modules.word.overview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nz2dev.wordtrainer.app.R

/**
 * Created by nz2Dev on 31.01.2018
 */
class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_default, null)
                .commit()
    }

}
