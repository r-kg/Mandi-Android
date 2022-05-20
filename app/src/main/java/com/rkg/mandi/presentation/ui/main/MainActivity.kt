package com.rkg.mandi.presentation.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkg.mandi.R
import com.rkg.mandi.databinding.MainActivityBinding
import com.rkg.mandi.presentation.binding.MandiTapEvent
import com.rkg.mandi.presentation.binding.SimpleDataBindingPresenter
import com.rkg.mandi.presentation.model.MainItemModel
import com.rkg.mandi.presentation.model.MainItemModel.*
import com.rkg.mandi.presentation.model.state.StateResult
import com.rkg.mandi.presentation.ui.BaseActivity
import com.rkg.mandi.presentation.ui.plant.PlantActivity
import com.rkg.mandi.presentation.utils.VerticalSpaceItemDecoration
import com.rkg.mandi.presentation.utils.launchPlantActivityForResult
import com.rkg.mandi.presentation.utils.startNewMandiActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var listAdapter: MainListAdapter

    private val plantLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val uri = it.data?.getParcelableExtra<Uri>(PlantActivity.EXTRA_URI)
            viewModel.plant()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        initViews()
        initObserves()

        viewModel.collectMandiFlow()
    }

    private fun initViews() {
        listAdapter = MainListAdapter(object : SimpleDataBindingPresenter() {
            override fun onClick(view: View, item: Any) {
                when (item) {
                    is MandiItemModel -> {
                        // start detail view
                    }
                    is MandiTapEvent.PlantTap -> {
                        viewModel.setTargetMandi(item.id)
                        launchPlantActivityForResult(plantLauncher)
                    }
                }
            }

            override fun onLongClick(view: View, item: Any): Boolean {
                when (item) {
                    is MandiItemModel -> {
                        showMandiMenu()
                        return true
                    }
                }
                return super.onLongClick(view, item)
            }
        })

        binding.rvMain.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)

            val spacing = resources.getDimensionPixelSize(R.dimen.spacing_6dp)
            addItemDecoration(VerticalSpaceItemDecoration(spacing, spacing))
        }
    }

    private fun initObserves() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.itemModels.collect { data ->
                    when (data) {
                        is StateResult.Success -> {
                            listAdapter.submitList(data.item)
                        }
                        is StateResult.Failure -> {
                            listAdapter.submitList(null)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun showMandiMenu() {
        val menus = resources.getStringArray(R.array.choose_mandi_menu)
        AlertDialog.Builder(this).apply {
            setItems(menus) { _, which ->
                when (menus[which]) {
                    getString(R.string.delete) -> {}
                    getString(R.string.reset) -> {}
                    else -> {
                        // nothing to do
                    }
                }
            }.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_mandi -> {
                startNewMandiActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
