package com.a4nt0n64r.partyapp.repository.network

import com.a4nt0n64r.partyapp.models.ui.PartyUI

interface Callback {
    fun onSuccess(value: PartyUI)
    fun onError(message: String)
}
