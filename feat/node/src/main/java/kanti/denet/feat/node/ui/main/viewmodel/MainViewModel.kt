package kanti.denet.feat.node.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.denet.data.model.node.NodeRepository
import kanti.denet.domain.getnode.GetRootOrChildrenUseCase
import kanti.denet.feat.node.ui.common.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.EmptyStackException
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nodeRepository: NodeRepository,
    getRootOrChildrenUseCase: GetRootOrChildrenUseCase
) : ViewModel() {

    private val mStack = Stack<String?>()
    private val mCurrentNode = MutableStateFlow<String?>(null)

    private val mUpdateState = MutableStateFlow(Any())
    val state: StateFlow<MainUiState> = mCurrentNode
        .combine(mUpdateState) { currentNode, _ -> currentNode }
        .map { hash ->
            val nodeWithChildren = getRootOrChildrenUseCase(hash)
            MainUiState(
                node = nodeWithChildren.node?.toUiState(),
                children = nodeWithChildren.children.map { it.toUiState() }
            )
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MainUiState()
        )

    fun onAction(intent: MainIntent) {
        when (intent) {
            is GoTo -> goTo(intent)
            is GoBack -> goBack()
            is CreateNew -> createNew()
            is Delete -> delete(intent)
        }
    }

    private fun goTo(intent: GoTo) {
        val currentNode = mCurrentNode.value
        mStack.push(currentNode)
        mCurrentNode.value = intent.hash
    }

    private fun goBack() {
        try {
            val backNode = mStack.pop()
            mCurrentNode.value = backNode
        } catch (_: EmptyStackException) {
        }
    }

    private fun createNew() {
        viewModelScope.launch(Dispatchers.Default) {
            nodeRepository.createNew()
            mUpdateState.value = Any()
        }
    }

    private fun delete(intent: Delete) {
        viewModelScope.launch(Dispatchers.Default) {
            nodeRepository.delete(intent.hash)
            mUpdateState.value = Any()
        }
    }
}