package kanti.denet.domain.getnode

import kanti.denet.data.model.node.Node
import kanti.denet.data.model.node.NodeRepository
import javax.inject.Inject

class GetRootNodesUseCase @Inject constructor(
    private val nodeRepository: NodeRepository
) {

    suspend operator fun invoke(): List<Node> {
        return nodeRepository.getChildren(null)
    }
}