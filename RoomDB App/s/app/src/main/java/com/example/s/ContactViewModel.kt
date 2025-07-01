package com.example.s

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s.data.Repository
import com.example.s.data.entity.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val isSortedByName=MutableStateFlow(true)

    private val contact=isSortedByName.flatMapLatest {
        if(it){
            repository.getAllContactSortByName()
        }
        else{
            repository.getAllContactSortByCreationDate()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())




    val _state = MutableStateFlow(AppState())

    val state=combine(
        _state,
        contact,
        isSortedByName

    ){
        _state,contact,isSortedByName->
        _state.copy(
            allContact = contact,

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppState())

    fun changeSorting(){
        isSortedByName.value=!isSortedByName.value
    }
    fun insert() {
        viewModelScope.launch {
            val contact = Contact(
                id = state.value.id.value,
                Name = state.value.name.value,
                phoneNumber = state.value.phoneNumber.value,
                email = state.value.email.value,
                dateOfCreation = System.currentTimeMillis(),
                isActive = true,
                image = state.value.image.value

            )
            repository.insertContact(contact)
            state.value.id.value=0
            state.value.name.value=""
            state.value.phoneNumber.value=""
            state.value.email.value=""
            state.value.dateOfCreation.value=0
            state.value.image= mutableStateOf(ByteArray(0))
        }

    }

    fun delete(){
        viewModelScope.launch {
            val contact=Contact(
                id = state.value.id.value,
                Name = state.value.name.value,
                phoneNumber = state.value.phoneNumber.value,
                email = state.value.email.value,
                dateOfCreation = state.value.dateOfCreation.value,
                isActive = true
            )
            repository.deleteContact(contact)

            state.value.id.value=0
            state.value.name.value=""
            state.value.phoneNumber.value=""
            state.value.email.value=""
            state.value.dateOfCreation.value=0

        }
    }

}


data class AppState(
    var loading: Boolean = false,
    var id: MutableState<Int> = mutableIntStateOf(0),
    var allContact: List<Contact> = emptyList(),
    var error: String = "",
    var name: MutableState<String> = mutableStateOf(""),
    var phoneNumber: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),
    var dateOfCreation: MutableState<Long> = mutableStateOf(0),
    var image: MutableState<ByteArray> = mutableStateOf(ByteArray(0))
)