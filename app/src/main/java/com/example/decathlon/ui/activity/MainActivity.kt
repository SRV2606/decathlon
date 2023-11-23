package com.example.decathlon.ui.activity


import android.content.Intent
import androidx.activity.viewModels
import com.example.calendar.viewmodel.MainViewModel
import com.example.decathlon.R
import com.example.decathlon.base.BaseActivity
import com.example.decathlon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val mainViewModel by viewModels<MainViewModel>()
    override fun readArguments(extras: Intent) {
        TODO("Not yet implemented")
    }

    override fun setupUi() {
        TODO("Not yet implemented")
    }

    override fun observeData() {
        TODO("Not yet implemented")
    }

    override fun setListener() {
        TODO("Not yet implemented")
    }


}