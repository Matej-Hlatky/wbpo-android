package me.hlatky.wbpo.store

import me.hlatky.wbpo.model.UserSession

/** Stores [UserSession] persistently. */
interface UserSessionStore {

    var value: UserSession?

    // TODO StateFlow<>
    // + updateValue()
}
