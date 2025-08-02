package app.actionsfun.repository.pinata.interactor

import app.actionsfun.repository.pinata.interactor.model.PinataMetadata

interface GetPinataMetadata {

    suspend fun get(url: String): PinataMetadata
}
