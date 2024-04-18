package kanti.denet.data.model.node.datasoure.local

import kanti.denet.data.model.node.Node

interface NodeLocalDataSource {

    suspend fun getNode(hash: String): Node?

    suspend fun getChildren(parentHash: String?): List<Node>

    suspend fun insert(node: Node): Boolean

    suspend fun delete(hash: String)

    suspend fun deleteChildren(parentHash: String?)
}