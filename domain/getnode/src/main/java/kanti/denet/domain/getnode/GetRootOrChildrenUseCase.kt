package kanti.denet.domain.getnode

import kanti.denet.data.model.node.NodeRepository
import javax.inject.Inject

class GetRootOrChildrenUseCase @Inject constructor(
    private val nodeRepository: NodeRepository,
    private val getNodeWithChildrenUseCase: GetNodeWithChildrenUseCase
) {

    suspend operator fun invoke(hash: String?): NodeWithChildren {
        return if (hash == null) {
            NodeWithChildren(
                children = nodeRepository.getChildren(null)
            )
        } else {
            getNodeWithChildrenUseCase(hash)
        }
    }
}