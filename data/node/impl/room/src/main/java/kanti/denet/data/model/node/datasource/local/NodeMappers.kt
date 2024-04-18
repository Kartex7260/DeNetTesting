package kanti.denet.data.model.node.datasource.local

import kanti.denet.data.model.node.Node
import kanti.denet.data.room.node.NodeEntity

fun NodeEntity.toNode(): Node {
    return Node(
        hash = hash,
        parentHash = parentHash
    )
}

fun Node.toEntity(): NodeEntity {
    return NodeEntity(
        hash = hash,
        parentHash = parentHash
    )
}