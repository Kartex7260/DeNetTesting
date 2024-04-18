package kanti.denet.data.model.node.datasource.local

import kanti.denet.data.model.node.Node
import kanti.denet.data.model.node.datasoure.local.NodeLocalDataSource
import kanti.denet.data.room.node.NodeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NodeRoomDataSource @Inject constructor(
    private val nodeDao: NodeDao
) : NodeLocalDataSource {

    override suspend fun getNode(hash: String): Node? = withContext(Dispatchers.Default) {
        nodeDao.getNode(hash = hash)?.toNode()
    }

    override suspend fun getChildren(parentHash: String?): List<Node> {
        return withContext(Dispatchers.Default) {
            nodeDao.getChildren(parentHash = parentHash).map { it.toNode() }
        }
    }

    override suspend fun insert(node: Node): Boolean = withContext(Dispatchers.Default) {
        nodeDao.insert(node.toEntity()) != -1
    }

    override suspend fun delete(hash: String) {
        nodeDao.delete(hash)
    }

    override suspend fun deleteChildren(parentHash: String?) {
        nodeDao.deleteChildren(parentHash)
    }
}