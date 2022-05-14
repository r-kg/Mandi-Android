package com.rkg.mandi.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkg.mandi.R
import com.rkg.mandi.databinding.MainActivityBinding
import com.rkg.mandi.presentation.binding.SimpleDataBindingPresenter
import com.rkg.mandi.presentation.model.MainItemModel
import com.rkg.mandi.presentation.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var listAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        initViews()
        initObserves()
    }

    private fun initViews() {
        listAdapter = MainListAdapter(object : SimpleDataBindingPresenter() {
            override fun onClick(view: View, item: Any) {
                when (item) {
                    is MainItemModel.MandiItemModel -> {}
                }
            }
        })

        binding.rvMain.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initObserves() {

    }

}