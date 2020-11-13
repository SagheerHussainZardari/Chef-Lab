package com.sagheerhussainzardari.cheflab.Models

data class BookSellModel(
    var bookId: String,
    var bookName: String,
    var bookAuthor: String,
    var bookPrice: String,
    var bookSellerUID: String,
    var bookSellerName: String,
    var bookSellerPhone: String,
    var bookSellerCity: String,
    var bookRatings: String,
    var bookImageUrl: String
)