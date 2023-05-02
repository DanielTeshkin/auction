package com.example.auctionapp.data.entity

object LotContract {

    const val TABLE_NAME = "lots"

    object Column {
        const val ID = "id"
        const val CITY = "city"
        const val DATE = "date"
        const val TITLE = "title"
        const val PRICE = "price"
        const val IMAGE = "image"
        const val DESCRIPTION = "description"
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
        const val CATEGORY = "category"
    }
}

