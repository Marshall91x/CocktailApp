package it.edelmonte.cocktailapp.util

import org.koin.dsl.module

//Kotlin D.I. with koin library
@JvmField
val koinInjector = module {
    single {
        CloudManager()
    }
}