package com.bobbycacra.simplechat.model

class Message {
    var from = ""
    var date = ""
    var textContent = ""

    constructor(from: String, date: String, textContent: String) {
        this.from = from
        this.date = date
        this.textContent = textContent
    }
}