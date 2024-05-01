package com.finalproject.aedvenice.data

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.maps.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//class MapsViewModel : ViewModel(){
//    var state by mutableStateOf(MapState())
//}

@HiltViewModel
class ViewModel @Inject constructor(): ViewModel() {
    /*AEDs*/

    private val firebaseManager = FirebaseManager()

    private val _aeds = mutableStateOf<List<AedBasics>>(emptyList())
    val aeds: State<List<AedBasics>> = _aeds

    init {
        getAedBasicsList()
    }

    private fun getAedBasicsList(){
        firebaseManager.getAedBasicsList {  aedBasics ->
            _aeds.value = aedBasics
        }
    }

    fun createAed(/*TODO: receive aed data*/){
        val new = Aed(
            AedBasics(
                null,
                "via dorsoduro",
                GeoPoint(45.4785,
                    12.2533),
                "functioning"),
            "second aed",
            "venice",
            "inside",
            "Monday: 2-4",
            listOf("123", "456")
        )
        firebaseManager.createAed(new)
    }

    fun getAedById(id : String) : MutableLiveData<Aed?>{
        return firebaseManager.getAedById(id)
    }

    fun deleteAed(id : String) {
        firebaseManager.deleteAed(id)
        getAedBasicsList()
    }

        /*MAPS*/

    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null
        )
    )

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.value = state.value.copy(
                        lastKnownLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    companion object {
        private val POLYGON_FILL_COLOR = Color.parseColor("#ABF44336")
    }
}