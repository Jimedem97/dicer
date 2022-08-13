package de.jimedem.dicer.states

sealed class CheckConnectionState {
    object Success : CheckConnectionState()
    data class Error(val errorMessage: String) : CheckConnectionState()
    object Loading : CheckConnectionState()
    object Initial: CheckConnectionState()
}
