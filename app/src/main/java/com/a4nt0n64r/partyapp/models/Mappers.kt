package com.a4nt0n64r.partyapp.models

import com.a4nt0n64r.partyapp.models.network.ApiResponse
import com.a4nt0n64r.partyapp.models.network.PersonNM
import com.a4nt0n64r.partyapp.models.ui.PartyUI
import com.a4nt0n64r.partyapp.models.ui.PersonUI

fun ApiResponse.toPartyUI(): PartyUI {
    return PartyUI(
        this.pictureUrl,
        this.partyName,
        this.partyStarter,
        this.pictureOfPartyStarter,
        this.invites.map { it.toPersonUI() })
}

fun PersonNM.toPersonUI(): PersonUI {
    return PersonUI(this.picture, this.name)
}
