package com.br.flup.app.core.data

import io.reactivex.Scheduler

interface Scheduler {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}