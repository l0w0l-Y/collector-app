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
import com.kaleksandra.coredata.network.models.MemberResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_DEBOUNCE_DELAY: Long = 300L

@OptIn(FlowPreview::class)
@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val interactor: CollectionInteractor
) : ViewModel() {
    private var _groupState = MutableStateFlow<List<GroupResponse>>(emptyList())
    val groupState: StateFlow<List<GroupResponse>> = _groupState

    private var _membersState = MutableStateFlow<List<MemberResponse>>(emptyList())
    val membersState: StateFlow<List<MemberResponse>> = _membersState

    private val _eventChannel = EventChannel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _input: MutableStateFlow<String> = MutableStateFlow("")
    val input: StateFlow<String> = _input

    init {
        _input.debounce(DEFAULT_DEBOUNCE_DELAY)
            .distinctUntilChanged()
            .onEach { getGroups(it) }
            .launchIn(viewModelScope)
    }

    fun onValueChange(value: String) {
        viewModelScope.launch {
            _input.emit(value)
        }
    }

    fun createCollection(title: String, groupId: Long) {
        viewModelScope.launch {
            interactor.createCollection(
                CollectionDto(title, groupId)
            ).doOnSuccess {
                _eventChannel.send(OnCreateCollectionEvent)
            }
        }
    }

    fun getAllMembersGroup(id: Long) {
        viewModelScope.launch {
            interactor.getAllMembersGroup(id).doOnSuccess {
                _membersState.emit(it)
            }
        }
    }

    private fun getGroups(query: String = "") {
        viewModelScope.launch {
            interactor.getGroups(query).doOnSuccess {
                _groupState.emit(it)
            }
        }
    }
}