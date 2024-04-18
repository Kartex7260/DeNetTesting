package kanti.denet.data.model.node

import kanti.denet.data.model.node.datasoure.local.NodeLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kotlincrypto.core.Digest
import javax.inject.Inject
import kotlin.random.Random

class NodeRepositoryImpl @Inject constructor(
    private val localDataSource: NodeLocalDataSource,
    private val digest: Digest
) : NodeRepository {

    override suspend fun getNode(hash: String): Node {
        return localDataSource.getNode(hash = hash)
    }

    override suspend fun getChildren(parentHash: String?): List<Node> {
        return localDataSource.getChildren(parentHash = parentHash)
    }

    override suspend fun createNew(parentHash: String?): String = withContext(Dispatchers.Default) {
        var generatedHash: String? = null
        while (generatedHash == null) {
            val newHash = generateNewHash()
            if (!localDataSource.hashIsContained(newHash)) {
                generatedHash = newHash
                val node = Node(hash = newHash, parentHash = parentHash)
                localDataSource.insert(node)
            }
        }
        generatedHash
    }

    override suspend fun delete(hash: String): Unit = withContext(Dispatchers.Default) {
        localDataSource.delete(hash)
        coroutineScope {
            launch {
                deleteChildren(hash)
            }
        }
    }

    private suspend fun deleteChildren(hash: String) {
        val children = getChildren(parentHash = hash)
        localDataSource.deleteChildren(parentHash = hash)
        for (child in children) {
            deleteChildren(child.hash)
        }
    }

    private fun generateNewHash(): String {
        val bytes = Random.Default.nextBytes(32)
        val rawHash = digest.digest(bytes)
        return String(rawHash)
    }
}