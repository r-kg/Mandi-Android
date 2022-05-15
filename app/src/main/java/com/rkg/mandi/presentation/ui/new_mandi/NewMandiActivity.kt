package com.rkg.mandi.presentation.ui.new_mandi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkg.mandi.R
import com.rkg.mandi.presentation.utils.findActivity
import com.rkg.mandi.presentation.widget.compose.ColumnDivider
import com.rkg.mandi.presentation.widget.compose.LoadingProgress
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

    val title: String by viewModel.title.collectAsState()
    val description: String by viewModel.description.collectAsState()

    val isDoneEnabled: Boolean by viewModel.isEditDone.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                onClick = { activity?.finish() },
                onEditDone = { viewModel.addMandi() },
                isDoneEnabled
            )
        },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            NoStyleTextField(
                value = title,
                onValueChange = { viewModel.setTitle(it) },
                maxLength = 20,
                singleLine = true,
                hint = stringResource(id = R.string.hint_new_mandi_title)
            )
            ColumnDivider()
            NoStyleTextField(
                value = description,
                onValueChange = { viewModel.setDescription(it) },
                modifier = Modifier.fillMaxHeight(),
                maxLength = 100,
                hint = stringResource(id = R.string.hint_new_mandi_desc)
            )
        }
    }

    if (isLoading) {
        LoadingProgress()
    }
}

@Composable
fun AppBar(onClick: () -> Unit, onEditDone: () -> Unit, doneEnabled: Boolean) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_new_mandi)) },
        backgroundColor = Color.White,
        elevation = 3.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            }
        },
        actions = {
            TextButton(
                onClick = onEditDone,
                enabled = doneEnabled,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.primary),
                    disabledContentColor = colorResource(id = R.color.gray40)
                )
            ) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    )
}