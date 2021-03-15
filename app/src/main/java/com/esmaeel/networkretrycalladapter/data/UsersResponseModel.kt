package com.esmaeel.networkretrycalladapter.data

data class UsersResponseModel(
    val `data`: List<Data?>? = listOf(),
    val page: Int? = 0,
    val per_page: Int? = 0,
    val support: Support? = Support(),
    val total: Int? = 0,
    val total_pages: Int? = 0
) {
    data class Data(
        val avatar: String? = "",
        val email: String? = "",
        val first_name: String? = "",
        val id: Int? = 0,
        val last_name: String? = ""
    )

    data class Support(
        val text: String? = "",
        val url: String? = ""
    )
}