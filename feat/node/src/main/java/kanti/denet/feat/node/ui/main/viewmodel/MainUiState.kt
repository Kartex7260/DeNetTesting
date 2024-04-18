package kanti.denet.feat.node.ui.main.viewmodel

import androidx.compose.runtime.Immutable
import kanti.denet.ui.components.NodeUiState

@Immutable
data class MainUiState(
    val node: NodeUiState? = null,
    val children: List<NodeUiState> = listOf()
)
