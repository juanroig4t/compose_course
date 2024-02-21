package com.juanroig.composecourse.domain.model.core

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val customDispatcher: CustomDispatchers)

enum class CustomDispatchers {
    IO,
    DEFAULT,
    MAIN,
    UNCONFINED
}
