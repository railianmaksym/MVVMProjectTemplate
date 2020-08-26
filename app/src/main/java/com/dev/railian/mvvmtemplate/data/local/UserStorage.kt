package com.dev.railian.mvvmtemplate.data.local

import com.dev.railian.mvvmtemplate.architecture.prefs.KPrefs
import com.dev.railian.mvvmtemplate.architecture.prefs.StringPref

object UserStorage : KPrefs() {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"

    var accessToken by StringPref(keyName = ACCESS_TOKEN, defaultValue = "")

    fun clear() {
        accessToken = ""
    }
}