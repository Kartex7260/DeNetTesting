package kanti.denet.feat.node.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kanti.denet.feat.node.ui.R
import kanti.denet.feat.node.ui.main.viewmodel.CreateNew
import kanti.denet.feat.node.ui.main.viewmodel.Delete
import kanti.denet.feat.node.ui.main.viewmodel.GoTo
import kanti.denet.feat.node.ui.main.viewmodel.MainIntent
import kanti.denet.feat.node.ui.main.viewmodel.MainUiState
import kanti.denet.feat.node.ui.main.viewmodel.MainViewModel
import kanti.denet.feat.node.ui.main.viewmodel.OnBack
import kanti.denet.shared.asEthereumAddress
import kanti.denet.ui.components.NodeCard
import kanti.denet.ui.components.NodeUiState

@Composable
fun MainScreenRoot() {
    val viewModel = hiltViewModel<MainViewModel>()
    MainScreen(
        state = viewModel.state.collectAsState().value,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    node: NodeUiState?,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            val title = node?.hash?.asEthereumAddress ?: stringResource(id = R.string.root)
            Text(text = title)
        },
        navigationIcon = {
            AnimatedVisibility(visible = node != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainUiState,
    onAction: (MainIntent) -> Unit
) {
    BackHandler(enabled = state.node != null) {
        onAction(OnBack)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                node = state.node,
                scrollBehavior = scrollBehavior,
                onBack = { onAction(OnBack) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(CreateNew) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.children
            ) { nodeUiState ->
                NodeCard(
                    state = nodeUiState,
                    onClick = {
                        onAction(GoTo(nodeUiState.hash))
                    },
                    tailContent = {
                        IconButton(onClick = { onAction(Delete(nodeUiState.hash)) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    MainScreen(
        state = MainUiState(
            node = null,
            children = listOf(
                NodeUiState(hash = "133874cm81375cn81375nc1385cm1385c135c13c"),
                NodeUiState(hash = "4jdjf87358cm37c8135c135c13e3c134c1334c1"),
                NodeUiState(hash = "j38urmx193rm98fd913s3c43cf2445c2454c5c")
            )
        ),
        onAction = {}
    )
}