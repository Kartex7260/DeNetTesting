package kanti.denet.feat.node.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val mStack = Stack<String>()
    private val mCurrentNode = MutableStateFlow("")

    val state: StateFlow<MainUiState> = mCurrentNode
        .map { MainUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MainUiState()
        )

    fun onAction(intent: MainIntent) {
    }

    companion object {

        private const val CURRENT_NODE_KEY = "currentNode"
        private const val STACK_KEY = "stack"
    }
}