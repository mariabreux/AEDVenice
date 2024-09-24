package com.finalproject.aedvenice.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.data.aed.Report
import com.finalproject.aedvenice.maps.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.core.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ViewModel @Inject constructor(): ViewModel() {
    /*UUID*/
    private fun generateUUID(): String{
        return UUID.randomUUID().toString()
    }

    fun getUUID(context: Context): String{
        //To allow the UUID to persist through multiple app sessions
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val storedUUID = sharedPreferences.getString("uuid", null)

        if(storedUUID == null){ // if there is not yet an UUID generated
            val newUUID = generateUUID()
            //store the created uuid in sharedPreferences
            with(sharedPreferences.edit()){
                putString("uuid", newUUID)
                apply()
            }
            return newUUID
        } else{
            return storedUUID
        }
    }

    /*AEDs*/

    private val firebaseManager = FirebaseManager()

    private val _aeds = mutableStateOf<List<AedBasics>>(emptyList())
    val aeds: State<List<AedBasics>> = _aeds

    private val _reports = mutableStateOf<List<Report>>(emptyList())
    val reports: State<List<Report>> = _reports
    //var report = mutableListOf<Report>()

    var adminMode = mutableStateOf(false)
    init {
        getAedBasicsList()
    }

    private fun getAedBasicsList(){
        firebaseManager.getAedBasicsList {  aedBasics ->
            _aeds.value = aedBasics
        }
    }

    fun createAed(newAed: Aed,/*TODO: receive aed data, */onSuccess: () -> Unit, onFailure: () -> Unit){
        firebaseManager.createAed(newAed, onSuccess, onFailure)
    }

    fun getAedById(id : String) : MutableLiveData<Aed?>{
        return firebaseManager.getAedById(id)
    }

    fun deleteAed(id : String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseManager.deleteAed(id, onSuccess, onFailure)
        getAedBasicsList()
    }

    fun updateAed(id : String, aed : Aed /*TODO: receive aed data*/, onSuccess: () -> Unit, onFailure: () -> Unit){
        val update = Aed(
            AedBasics(
                null,
                "lost",
                GeoPoint(45.4785,
                    12.2533),
                "functioning"),
            "second aed",
            "venice",
            "inside",
            "Monday: 2-4",
            listOf("123", "456")
        )
        firebaseManager.updateAed(id, update, onSuccess, onFailure)
        getAedBasicsList()
    }

    /*REPORTS*/
    fun createReport(context: Context, id : String, coordinates : GeoPoint, message : String, onSuccess: () -> Unit, onFailure: () -> Unit){
        firebaseManager.createReport(id, coordinates, message, getUUID(context), onSuccess, onFailure)
    }

    fun deleteReport(id : String, onSuccess: () -> Unit, onFailure: () -> Unit){
        firebaseManager.deleteReport(id, onSuccess, onFailure)
    }

    fun getReports(onUpdate: (List<Report>) -> Unit){
        firebaseManager.getReports { rep ->
            _reports.value = rep
            onUpdate(reports.value)
            //report = rep.toMutableList()
            //onUpdate(report)
        }
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
            Log.e("Get Device Location", "Error getting device location")
        }
    }

    companion object {
        private val POLYGON_FILL_COLOR = Color.parseColor("#ABF44336")
    }
}