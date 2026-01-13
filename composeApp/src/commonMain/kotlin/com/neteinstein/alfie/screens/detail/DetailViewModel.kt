package com.neteinstein.alfie.screens.detail

import androidx.lifecycle.ViewModel
import com.neteinstein.alfie.data.MuseumObject
import com.neteinstein.alfie.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
