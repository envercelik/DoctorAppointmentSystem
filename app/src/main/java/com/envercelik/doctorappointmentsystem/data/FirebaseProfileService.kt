package com.envercelik.doctorappointmentsystem.data

import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.Resource.*
import com.envercelik.doctorappointmentsystem.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseProfileService {
    private val userCollection = Firebase.firestore.collection("users")

    suspend fun createUserInFireStore(user: User, uid: String): Resource<Void> {
        return try {
            Loading<Void>()
            val result = userCollection.document(uid).set(user).await()
            Success(result)
        } catch (e: Exception) {
            Error(e.message.toString())
        }
    }
}