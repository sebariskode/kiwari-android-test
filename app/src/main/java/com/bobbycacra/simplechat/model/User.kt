package com.bobbycacra.simplechat.model

class User {
    var name = ""
    var avatar = ""
    var email = ""
    var password = ""

    constructor(name: String, avatar: String, email: String, password: String) {
        this.name = name
        this.avatar = avatar
        this.email = email
        this.password = password
    }
}