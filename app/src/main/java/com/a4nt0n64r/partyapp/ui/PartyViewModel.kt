package com.a4nt0n64r.partyapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a4nt0n64r.partyapp.di.components.DaggerNetworkComponent
import com.a4nt0n64r.partyapp.di.components.NetworkComponent
import com.a4nt0n64r.partyapp.models.ui.PartyUI
import com.a4nt0n64r.partyapp.repository.network.Callback
import com.a4nt0n64r.partyapp.repository.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyViewModel : ViewModel() {
    @Inject
    lateinit var networkRepository: NetworkRepository

    init {
        val networkComponent = getNetworkComponent()
        networkComponent.inject(this)
    }

    private fun getNetworkComponent(): NetworkComponent {
        return DaggerNetworkComponent.builder()
            .build()
    }

    private val _party = MutableLiveData<PartyUI>()
    val party: LiveData<PartyUI> get() = _party

    fun getParty() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getParty(object : Callback {
                override fun onError(message: String) {
                    Log.d("__ERROR", "in ViewModel $message")
                }

                override fun onSuccess(value: PartyUI) {
                    Log.d("__SUCCESS", "in ViewModel")
                    _party.postValue(value)
                }
            })
        }
    }
}
