package kanti.denet.feat.node.ui.main.viewmodel

sealed interface MainIntent

internal data object CreateNew : MainIntent

internal data object GoBack : MainIntent

internal data class GoTo(val hash: String) : MainIntent

internal data class Delete(val hash: String) : MainIntent