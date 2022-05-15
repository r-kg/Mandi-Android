package com.rkg.mandi.presentation.ui.new_mandi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkg.mandi.R
import com.rkg.mandi.presentation.utils.findActivity
import com.rkg.mandi.presentation.widget.compose.ColumnDivider
import com.rkg.mandi.presentation.widget.compose.NoStyleTextField
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

    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppBar { activity?.finish() } },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            NoStyleTextField(
                value = title,
                onValueChange = { title = it },
                maxLength = 10,
                singleLine = true,
                hint = stringResource(id = R.string.hint_new_mandi_title)
            )
            ColumnDivider()


        }
    }
}

@Composable
fun AppBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_new_mandi)) },
        backgroundColor = Color.White,
        elevation = 3.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            }
        }
    )
}