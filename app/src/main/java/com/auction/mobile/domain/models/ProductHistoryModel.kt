package com.auction.mobile.domain.models

data class ProductHistoryModel(
    val price: Int,
    val dateCreated: String,
    val user: AuthorModel
): java.io.Serializable
