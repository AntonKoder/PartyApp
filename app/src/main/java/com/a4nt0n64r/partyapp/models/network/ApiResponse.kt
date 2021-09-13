package com.a4nt0n64r.partyapp.models.network

data class ApiResponse(
    val pictureUrl: String,
    val partyName: String,
    val partyStarter: String,
    val pictureOfPartyStarter: String,
    val invites: List<PersonNM>
)
