package com.rkg.mandi.presentation.ui.new_mandi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkg.mandi.R
import com.rkg.mandi.presentation.utils.findActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMandiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityView()
        }
    }
}

@Composable
fun ActivityView() {
    val activity = LocalContext.current.findActivity()
    val viewModel: NewMandiViewModel = viewModel()

    Scaffold(
        topBar = { AppBar { activity?.finish() } },
    ) {

    }
}

@Composable
fun AppBar(onClick: () -> Unit) {
    TopAppBar(
        title = { },
        backgroundColor = Color.White,
        elevation = 3.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            }
        }
    )
}