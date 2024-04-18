package kanti.denet.data.model.node

interface NodeRepository {

    suspend fun getNode(hash: String): Node

    suspend fun getChildren(parentHash: String): List<Node>
}