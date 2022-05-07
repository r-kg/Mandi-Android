package com.rkg.mandi.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.rkg.mandi.R
import com.rkg.mandi.databinding.MainActivityBinding
import com.rkg.mandi.presentation.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
    }
}