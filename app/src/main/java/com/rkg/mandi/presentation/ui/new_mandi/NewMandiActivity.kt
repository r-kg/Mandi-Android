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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rkg.mandi.R

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
    Scaffold(
        topBar = {
            AppBar {

            }
        },
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