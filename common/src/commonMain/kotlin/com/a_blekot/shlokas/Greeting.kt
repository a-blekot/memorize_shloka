package com.a_blekot.shlokas

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}