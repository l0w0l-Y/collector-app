package com.kaleksandra.collector.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.coredata.network.models.CollectionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCollectionViewModel @Inject constructor(
    private val interactor: CollectionInteractor
) : ViewModel() {
    fun addCollection(title: String, groupId: Long) {
        viewModelScope.launch {
            interactor.addCollection(CollectionDto(title, groupId))
        }
    }
}