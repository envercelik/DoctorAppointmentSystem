package com.envercelik.doctorappointmentsystem.data

import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.Resource.*
import com.envercelik.doctorappointmentsystem.ui.model.User
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseProfileService {
    private val userCollection = Firebase.firestore.collection("users")

    suspend fun createUserInFireStore(user: User, uid: String): Resource<Boolean> {
        return try {
            Loading<Nothing>()
            userCollection.document(uid).set(user).await()
            Success(true)
        } catch (e: FirebaseFirestoreException) {
            Error(e.message ?: "unexpected error occurred")
        } catch (e: Exception) {
            Error("unexpected error occurred. please check your internet connection")
        }
    }

    suspend fun getUserRoleFromFirestore(uid: String): Resource<String> {
        return try {
            Loading<String>()
            val userDocumentById = userCollection.document(uid).get().await()
            val role = userDocumentById.data!!["role"].toString()
            Success(role)
        } catch (e: FirebaseFirestoreException) {
            Error(e.message ?: "unexpected error occurred")
        } catch (e: Exception) {
            Error("unexpected error occurred. please check your internet connection")
        }
    }
}