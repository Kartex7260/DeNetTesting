package kanti.denet.domain.getnode

import kanti.denet.data.model.node.Node

data class NodeWithChildren(
    val node: Node = Node(),
    val children: List<Node> = listOf()
)
