package com.br.flup.app.core.manager

import com.br.flup.app.authentication.model.Role
import com.br.flup.app.authentication.model.SessionPreference
import com.br.flup.app.authentication.model.User
import com.chibatching.kotpref.bulk

object SessionManager {

    val isAuthenticated: Boolean
        get() = SessionPreference.userId != null

    val sessionToken: String?
        get() = SessionPreference.token

    val eventName: String?
        get() = SessionPreference.eventName

    val userId: String?
        get() = SessionPreference.userId

    val role: Role?
        get() = SessionPreference.role

    fun storeSessionInfo(eventName: String, token: String) {
        SessionPreference.bulk {
            this.eventName = eventName
            this.token = token
        }
    }

    fun storeUserInfo(user: User) {
        SessionPreference.bulk {
            this.userId = user.id
            this.role = user.role
        }
    }

    fun clearSession() {
        SessionPreference.clear()
    }
}