package kanti.denet.feat.node.ui.common

import kanti.denet.data.model.node.Node
import kanti.denet.ui.components.NodeUiState

fun Node.toUiState(): NodeUiState {
    return NodeUiState(
        hash = hash
    )
}