package com.kaleksandra.collector.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.collector.presentation.create.model.Event
import com.kaleksandra.collector.presentation.create.model.OnCreateCollectionEvent
import com.kaleksandra.corecommon.ext.EventChannel
import com.kaleksandra.coredata.network.doOnSuccess
import com.kaleksandra.coredata.network.models.CollectionDto
import com.kaleksandra.coredata.network.models.GroupResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val interactor: CollectionInteractor
) : ViewModel() {
    private var _groupState = MutableStateFlow<List<GroupResponse>>(emptyList())
    val groupState: StateFlow<List<GroupResponse>> = _groupState

    private val _eventChannel = EventChannel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            interactor.getAllGroups().doOnSuccess {
                _groupState.emit(it)
            }
        }
    }

    fun createCollection(title: String, groupId: Long) {
        viewModelScope.launch {
            interactor.createCollection(CollectionDto(title, groupId)).doOnSuccess {
                _eventChannel.send(OnCreateCollectionEvent)
            }
        }
    }
}