package kanti.denet.feat.node.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.denet.domain.getnode.GetRootOrChildrenUseCase
import kanti.denet.feat.node.ui.common.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getRootOrChildrenUseCase: GetRootOrChildrenUseCase
) : ViewModel() {

    private val mStack = Stack<String>()
    private val mCurrentNode = MutableStateFlow<String?>("")

    val state: StateFlow<MainUiState> = mCurrentNode
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
    }
}