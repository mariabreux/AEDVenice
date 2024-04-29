package com.finalproject.aedvenice.data

import android.util.Log
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager(){

    private val db = FirebaseFirestore.getInstance()

    fun createAed(aed : Aed){
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
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error creating AED", exception)
            }
    }

    fun getAedBasicsList(onUpdate: (List<AedBasics>) -> Unit){
        val aedCollection = db.collection("aedTest") /*TODO: change to Aed*/
        Log.i("FirebaseManager", "entrei")

        aedCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("TAG", "Error observing aeds", exception)
                return@addSnapshotListener
            }
            Log.i("FirebaseManager", "inside collection")
            if (snapshot != null) {
                Log.i("FirebaseManager", "inside snapshot")
                val aeds = mutableListOf<AedBasics>()
                for (document in snapshot.documents) {
                    val address = document.getString("indirizzo")
                    val notes = document.getString("note")
                    val geoPoint = document.get("geo_point") as Map<String, Double>


                    val aed = AedBasics(
                        address, GeoPoint(geoPoint["latitude"], geoPoint["longitude"]), notes
                    )
                    Log.i("FirebaseManager", "before add")
                    aeds.add(aed)

                    Log.i("FirebaseManager", "after add")
                }
                onUpdate(aeds)
            }
        }
    }
}