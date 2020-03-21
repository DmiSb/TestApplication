package ru.dmisb.test_app.ui.screen.detail

sealed class DetailState {
    class OnSuccessSaved : DetailState()
    class OnSuccessDelete: DetailState()
    class OnError(val error: String) : DetailState()
}