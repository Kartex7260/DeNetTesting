package kanti.denet.domain.getnode

import kanti.denet.data.model.node.NodeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetNodeWithChildrenUseCase @Inject constructor(
    private val nodeRepository: NodeRepository
) {

    suspend operator fun invoke(hash: String): NodeWithChildren = coroutineScope {
        val nodeDeferred = async { nodeRepository.getNode(hash = hash) }
        val childrenDeferred = async { nodeRepository.getChildren(parentHash = hash) }
        nodeDeferred.start()
        childrenDeferred.start()

        val node = nodeDeferred.await()
        val children = childrenDeferred.await()

        NodeWithChildren(
            node = node,
            children = children
        )
    }
}