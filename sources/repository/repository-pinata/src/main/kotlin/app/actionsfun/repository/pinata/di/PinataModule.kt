package app.actionsfun.repository.pinata.di

import app.actionsfun.repository.pinata.interactor.GetPinataMetadata
import app.actionsfun.repository.pinata.interactor.internal.GetPinataMetadataImpl
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val PinataModule = module {
    single<GetPinataMetadata> {
        GetPinataMetadataImpl(
            okhttp = get(),
            json = get(qualifier = StringQualifier("AppJson"))
        )
    }
}