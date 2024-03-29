package com.kaleksandra.collector.presentation.create

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.collector.domain.models.Group
import com.kaleksandra.collector.domain.models.Member
import com.kaleksandra.collector.presentation.create.model.Event
import com.kaleksandra.collector.presentation.create.model.OnCreateCollectionEvent
import com.kaleksandra.corecommon.ext.event.EventChannel
import com.kaleksandra.coredata.network.doOnSuccess
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
import java.io.File
import javax.inject.Inject

private const val DEFAULT_DEBOUNCE_DELAY: Long = 300L

@OptIn(FlowPreview::class)
@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val interactor: CollectionInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var _groupState = MutableStateFlow<List<Group>>(emptyList())
    val groupState: StateFlow<List<Group>> = _groupState

    private var _membersState = MutableStateFlow<List<Member>>(emptyList())
    val membersState: StateFlow<List<Member>> = _membersState

    private val _eventChannel = EventChannel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _input: MutableStateFlow<String> = MutableStateFlow("")

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

    fun getAllMembersGroup(id: Long) {
        viewModelScope.launch {
            interactor
                .getAllMembersGroup(id)
                .doOnSuccess {
                    _membersState.emit(it)
                }
        }
    }

    fun sendCollection(uris: Map<Long, Uri>, title: String, groupId: Long) {
        viewModelScope.launch {
            interactor
                .createCollection(title, groupId)
                .doOnSuccess {
                    savePhotos(uris, it)
                }
        }
    }

    private fun savePhotos(uris: Map<Long, Uri>, collectionId: Long) {
        viewModelScope.launch {
            uris.forEach {
                it.value.path?.let { uri ->
                    interactor
                        .uploadImage(
                            File(uri), it.key
                        )
                        .doOnSuccess { cardId ->
                            interactor.setCardCollection(cardId, collectionId)
                        }
                }
            }
            _eventChannel.send(OnCreateCollectionEvent)
        }
    }

    private fun getGroups(query: String = "") {
        viewModelScope.launch {
            interactor
                .getGroups(query)
                .doOnSuccess {
                    _groupState.emit(it)
                }
        }
    }
}