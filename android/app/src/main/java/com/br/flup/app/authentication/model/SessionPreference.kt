package com.br.flup.app.authentication.model

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import com.chibatching.kotpref.enumpref.nullableEnumValuePref

object SessionPreference : KotprefModel() {

    var eventName by nullableStringPref()
    var token by nullableStringPref()
    var userId by nullableStringPref()
    var role by nullableEnumValuePref<Role>()

}