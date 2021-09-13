package com.a4nt0n64r.partyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.a4nt0n64r.partyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private var nullableBinding: ActivityMainBinding? = null
    private val binding get() = nullableBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nullableBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}


//data class AccountInfoResponse(
//    @SerializedName("userprofile") val userprofiles: ArrayList<UserProfile>,
//    @SerializedName("userpatients") val userpatients: ArrayList<UserPatient>
//)
//
//class UserPatient (
//    @SerializedName("sex") val sex: String,
//    @SerializedName("date of birth") val date_of_birth: String,
//    @SerializedName("address") val address: String,
//    @SerializedName("patientID") val patientId: Int,
//    @SerializedName("first name") val first_name: String,
//    @SerializedName("clinicName") val clinic_name: String,
//    @SerializedName("clinicID") val clinicId: Int,
//    @SerializedName("mobile") val mobile: String,
//    @SerializedName("last name") val last_name: String
//)
//
//
//
//val response = AccountInfoResponse(/* Here goes the objects that is needed to create instance of this class */)
//
//val jsonString = Gson().toJson(response.userpatients)