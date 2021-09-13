package com.a4nt0n64r.partyapp.models.ui

data class PartyUI(
    val pictureUrl: String,
    val partyName: String,
    val partyStarter: String,
    val pictureOfPartyStarter: String,
    val invites: List<PersonUI>
)
