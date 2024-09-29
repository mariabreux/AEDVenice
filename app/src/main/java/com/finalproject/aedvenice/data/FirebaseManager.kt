package com.finalproject.aedvenice.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.BannedUser
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.data.aed.Report
import com.finalproject.aedvenice.data.aed.User
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager{

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

        //db.collection("aed") /*TODO: change to Aed*/
        db.collection("aedTest")
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
        val aedCollection = db.collection("aed") /*TODO: change to Aed*/
        //val aedTestCollection = db.collection("aedTest")

        aedCollection.limit(150)/*aedTestCollection*/.addSnapshotListener { snapshot, exception ->
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
                    val geoPoint = document.get("geo_point") as? Map<String, Double> ?: emptyMap()

                    val aed = AedBasics(
                        id, address, GeoPoint(geoPoint["lat"], geoPoint["lon"]), notes
                        //id, address, GeoPoint(geoPoint["latitude"], geoPoint["longitude"]), notes
                        /*TODO: change to lat and lon after changing the collection to aed */
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

        db.collection("aed")/*TODO: change to Aed*/
        //db.collection("aedTest")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if(document != null){
                    val docId = document.id
                    val address = document.getString("indirizzo")
                    val notes = document.getString("note")
                    val geoPoint = document.get("geo_point") as? Map<String, Double> ?: emptyMap()
                    val basics = AedBasics(
                        docId, address, GeoPoint(geoPoint?.get("lat"), geoPoint?.get("lon")), notes
                        //docId, address, GeoPoint(geoPoint?.get("latitude"), geoPoint?.get("longitude")), notes
                        /*TODO: change to lat and lon after changing the collection to aed */
                    )
                    val name = document.getString("nome")
                    val city = document.getString("citta")
                    val location = document.getString("ubicazione")
                    val timetable = document.getString("orari")
                    val phoneNumber = document.getString("telefono") //as? List<String>
                    val aed = Aed(
                        basics, name, city, location, timetable, phoneNumber
                    )
                    aedLiveData.value = aed
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
        db.collection("aed") /*TODO: change to Aed*/
        //db.collection("aedTest")
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
        db.collection("aed") /*TODO: change to Aed*/
        //db.collection("aedTest")
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

    fun incrementUserReports(
        uuid: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onSuccess2: () -> Unit,
        onFailure2: () -> Unit
    ){
        val usersCollection = db.collection("users")

        usersCollection
            .whereEqualTo("uuid", uuid)
            .get()
            .addOnSuccessListener { snapshot ->
                if(!snapshot.isEmpty){
                    val document = snapshot.documents[0]
                    val documentId = document.id
                    var reports = document.getLong("reports")?.toInt() ?: 0
                    reports += 1

                    usersCollection
                        .document(documentId)
                        .update("reports", reports)
                        .addOnSuccessListener {
                            Log.d("User report", "Report incremented successfully")
                            //If 5 reports of this user were spam, the user is banned
                            if(reports >= 5){
                                banUser(uuid,
                                    {
                                        onSuccess2()
                                    },
                                    {
                                        onFailure2()
                                    })
                            }
                            onSuccess()
                        }
                        .addOnFailureListener {
                            Log.e("User report", "Error incrementing reports")
                            onFailure()
                        }
                }
                else{
                    createUser(uuid)
                }
            }
            .addOnFailureListener {
                Log.i("User report", "Error incrementing reports")
            }
    }

    private fun createUser(uuid: String){
        val user = User(uuid)
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("User", "User added successfully")
            }
            .addOnFailureListener {
                Log.d("User", "Error adding user")
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

    private fun deleteReportsFromUser(uuid: String){
        val repsCollection = db.collection("report")

        repsCollection
            .whereEqualTo("uuid", uuid)
            .get()
            .addOnSuccessListener { snapshot ->
                if(!snapshot.isEmpty){
                    for(document in snapshot){
                        val docId = document.id

                        repsCollection
                            .document(docId)
                            .delete()
                            .addOnSuccessListener {
                                Log.i("Delete Report", "Report with $docId id deleted")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Delete Report", "Error deleting report", e)
                            }
                    }
                } else {
                    Log.i("Delete report", "No reports found with UUID: $uuid")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Delete report", "Error querying reports with UUID: $uuid", e)
            }
    }

    fun banUser(uuid: String, onSuccess: () -> Unit, onFailure: () -> Unit){
        val userCollection = db.collection("users")
        val bannedUser = hashMapOf(
            "uuid" to uuid
        )
        db.collection("bannedUsers")
            .add(bannedUser)
            .addOnSuccessListener {
                Log.d("Banning User", "User banned successfully")
                //When user is banned, delete all reports from that user
                deleteReportsFromUser(uuid)
                //Remove user from "users" database collection
                userCollection
                    .whereEqualTo("uuid", uuid)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        if(!snapshot.isEmpty){
                            val docId = snapshot.documents[0].id

                            userCollection
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Log.i("Removing User", "User removed from database")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Removing User", "Error removing user", e)
                                }
                        }
                    }
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
}