package com.sdhong.jonbeowin.view.uistate

data class AssetDetailUiState(
    val name: String,
    val buyDateString: String
) {
    companion object {
        val Default = AssetDetailUiState(
            name = "",
            buyDateString = ""
        )
    }
}