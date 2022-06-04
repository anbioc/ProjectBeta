package projectbeta.amir.io.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.amir.io.R

@Composable
fun initMainView(viewModel: MainViewModel, navController: NavController) {
    val state = viewModel.liveData.observeAsState()
    Scaffold(topBar = {
        createTopBar()
    }, bottomBar = {
        BottomAppBar(
            // Defaults to null, that is, No cutout
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)
            )
        ) {
            /* Bottom app bar content */
        }
    },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                /* FAB content */
            }
        }, drawerContent = {
            Text(text = "menu 1")
            Text(text = "Menu 2")
        }) {
        Box(modifier = Modifier.padding(it))
        when (state.value) {
            InitialState -> {
                createInitialState()
            }
            is ItemsAvailable -> {
                createList(state, navController)
            }
        }
    }
}

@Composable
private fun createInitialState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedFloatingActionButton(
            onClick = { /* ... */ },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            },
            text = { Text("Like") }
        )

        Button(
            onClick = {},
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)
        ) {
            Text(text = "Click to show you!")
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Text(text = stringResource(R.string.hello_there))
        CircularProgressIndicator(modifier = Modifier.offset(0.dp, 16.dp))
        Text(
            text = stringResource(R.string.items_processing),
            modifier = Modifier.offset(0.dp, 16.dp)
        )
    }
}

@Composable
private fun createTopBar() {
    TopAppBar(title = { Text("Project Beta") },
        actions = {
            IconButton(onClick = { }) {
                Text(text = "Hi")
            }
        }
    )
}

@Composable
private fun createList(state: State<MainViewModelState?>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .offset(0.dp, 40.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = (state.value as ItemsAvailable).data,
            itemContent = {
                prepareListItems(it, navController)
            }
        )
    }
}

@OptIn(ExperimentalGraphicsApi::class)
@Composable
private fun prepareListItems(item: MainItemDomainEntity, navController: NavController) {
    Row {
        Card(
            backgroundColor = Color.hsv(0.4f, 0.3f, 0.5f, 0.4f),
            elevation = 10.dp,
            border = BorderStroke(16.dp, Color.hsv(0.3f, 0.7f, 0.7f, 0.4f)), modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .offset(4.dp, 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable(onClick = { navController.navigate(R.id.action_mainFragment_to_detailFragment) }),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    SolidColor(Color.Green)
                    Text(text = "")
                }
                Text(text = item.name, style = MaterialTheme.typography.h6, color = Color.Green)
                Text(
                    text = stringResource(R.string.view_detail),
                    style = MaterialTheme.typography.caption,
                )
            }
        }

    }
}
