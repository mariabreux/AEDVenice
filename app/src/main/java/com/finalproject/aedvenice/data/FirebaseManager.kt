package com.finalproject.aedvenice.data

import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.BannedUser
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.data.aed.Report
import com.google.firebase.Firebase
import com.google.firebase.database.core.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirebaseManager(){

    private val db = FirebaseFirestore.getInstance()

    fun createAed(
        aed : Aed,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        val newAed = hashMapOf(
            "nome" to aed.name,
            "indirizzo" to aed.aedBasics?.address,
            "citta" to aed.city,
            "geo_point" to aed.aedBasics?.geoPoint,
            "note" to aed.aedBasics?.notes,
            "orari" to aed.timetable,
            "telefono" to aed.phoneNumber,
            "ubicazione" to aed.location
        )

        db.collection("aedTest") /*TODO: change to Aed*/
            .add(newAed)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "AED created with ID: ${documentReference.id}")
                onSuccess()
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error creating AED", exception)
                onFailure()
            }
    }

    fun getAedBasicsList(onUpdate: (List<AedBasics>) -> Unit){
        val aedCollection = db.collection("aedTest") /*TODO: change to Aed*/

        aedCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("TAG", "Error observing aeds", exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val aeds = mutableListOf<AedBasics>()
                for (document in snapshot.documents) {
                    val id = document.id
                    val address = document.getString("indirizzo")
                    val notes = document.getString("note")
                    val geoPoint = document.get("geo_point") as Map<String, Double>

                    val aed = AedBasics(
                        id, address, GeoPoint(geoPoint["latitude"], geoPoint["longitude"]), notes
                        /*TODO: change to lat and long after changing the collection to aed */
                    )
                    aeds.add(aed)
                }
                onUpdate(aeds)
            }
        }
    }

    fun getAedById(id: String): MutableLiveData<Aed?> {
        val aedLiveData = MutableLiveData<Aed?>()

        if(id.isEmpty()){
            return aedLiveData
        }

        db.collection("aedTest")/*TODO: change to Aed*/
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if(document != null){
                    val id = document.id
                    val address = document.getString("indirizzo")
                    val notes = document.getString("note")
                    val geoPoint = document.get("geo_point") as? Map<String, Double>
                    val basics = AedBasics(
                        id, address, GeoPoint(geoPoint?.get("latitude"), geoPoint?.get("longitude")), notes
                        /*TODO: change to lat and long after changing the collection to aed */
                    )
                    val name = document.getString("nome")
                    val city = document.getString("citta")
                    val location = document.getString("ubicazione")
                    val timetable = "{" + document.getString("orari") + "}" //TODO: delete {} when collection changes
                    val phoneNumber = document.get("telefono") as? List<String>
                    val aed = Aed(
                        basics, name, city, location, timetable, phoneNumber
                    )
                    aedLiveData.value = aed
                    Log.i("get By id", id + aed.city)
                }else {
                    Log.d("GetAedById", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("GetAedById", "get failed with ", exception)
            }
        return aedLiveData
    }

    fun deleteAed(
        id : String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        db.collection("aedTest") /*TODO: change to Aed*/
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Aed", "Aed deleted")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Delete Aed", "Error deleting Aed", e)
                onFailure()
            }
    }

    fun updateAed(
        id: String,
        aed : Aed,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        db.collection("aedTest") /*TODO: change to Aed*/
            .document(id)
            .update(
                mapOf(
                    "nome" to aed.name,
                    "indirizzo" to aed.aedBasics?.address,
                    "citta" to aed.city,
                    "geo_point" to aed.aedBasics?.geoPoint,
                    "note" to aed.aedBasics?.notes,
                    "orari" to aed.timetable,
                    "telefono" to aed.phoneNumber,
                    "ubicazione" to aed.location
                )
            )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    /*Reports*/

    fun createReport(
        id : String,
        coordinates : GeoPoint,
        message : String,
        uuid: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        val newReport = hashMapOf(
            "aed" to id,
            "coordinates" to coordinates,
            "message" to message,
            "uuid" to uuid
        )

        db.collection("report")
            .add(newReport)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "Report created with ID: ${documentReference.id}")
                onSuccess()
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error creating Report", exception)
                onFailure()
            }
    }

    fun deleteReport(
        id : String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        db.collection("report")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Report", "Report deleted $id")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Delete Report", "Error deleting Report", e)
                onFailure()
            }
    }

    fun getReports(onUpdate: (List<Report>) -> Unit) {
        val reportsCollection = db.collection("report")
        reportsCollection
            .addSnapshotListener{ snapshot, e ->
                if(e != null){
                    Log.e("Get Reports From Firestore", "Error getting reports")
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val reports = mutableListOf<Report>()
                    for (document in snapshot.documents) {
                        val reportId = document.id
                        val reportAed = document.getString("aed")
                        val reportMessage = document.getString("message")
                        val geoPoint = document.get("coordinates") as Map<String, Double>
                        val uuid = document.getString("uuid")

                        val rep = Report(reportId, reportAed, GeoPoint(geoPoint["latitude"], geoPoint["longitude"]), reportMessage, uuid)
                        reports.add(rep)
                    }
                    onUpdate(reports)
                }
            }
    }

    fun banUser(uuid: String, onSuccess: () -> Unit, onFailure: () -> Unit){
        val bannedUser = hashMapOf(
            "uuid" to uuid
        )
        db.collection("bannedUsers")
            .add(bannedUser)
            .addOnSuccessListener {
                Log.d("Banning User", "User banned successfully")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("Banning User", "Error banning user")
                onFailure()
            }
    }

    fun getBannedUsers(onUpdate: (List<BannedUser>) -> Unit){
        val bannedUserCollection = db.collection("bannedUsers")
        bannedUserCollection
            .addSnapshotListener{ snapshot, e ->
                if(e != null){
                    Log.e("Get Banned Users From Firestore", "Error getting banned Users")
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val bannedUsers = mutableListOf<BannedUser>()
                    for (document in snapshot.documents) {
                        val uuid = document.getString("uuid")

                        val bU = BannedUser(uuid.toString())
                        bannedUsers.add(bU)
                    }
                    onUpdate(bannedUsers)
                }
            }
    }
    /*TODO: Create a function that checks if user is banned, using getBannedUsers*/
}