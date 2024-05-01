package com.finalproject.aedvenice.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

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

        db.collection("aedTest")/*TODO: change to Aed*/
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if(document != null){
                    val id = document.id
                    val address = document.getString("indirizzo")
                    val notes = document.getString("note")
                    val geoPoint = document.get("geo_point") as Map<String, Double>
                    val basics = AedBasics(
                        id, address, GeoPoint(geoPoint["latitude"], geoPoint["longitude"]), notes
                        /*TODO: change to lat and long after changing the collection to aed */
                    )
                    val name = document.getString("nome")
                    val city = document.getString("citta")
                    val location = document.getString("ubicazione")
                    val timetable = document.getString("orari")
                    val phoneNumber = document.get("telefono") as List<String>
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

    fun deleteAed(id : String){
        db.collection("aedTest") /*TODO: change to Aed*/
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Aed", "Aed deleted")
            }
            .addOnFailureListener { e ->
                Log.e("Delete Aed", "Error deleting Aed", e)
            }
    }
}