package com.envercelik.doctorappointmentsystem.data

import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.Resource.*
import com.envercelik.doctorappointmentsystem.ui.model.Doctor
import com.envercelik.doctorappointmentsystem.ui.model.User
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
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

    suspend fun getAllDoctorsFromFirestore() = flow {
        try {
            emit(Loading())
            val doctors: MutableList<Doctor> = mutableListOf()
            val doctorsSnapshot = userCollection.whereEqualTo("role", "doctor").get().await()
            for (doctorDocument in doctorsSnapshot.documents) {
                val doctor = doctorDocument.toObject<Doctor>()
                doctor?.let {
                    doctors.add(doctor)
                }
            }
            emit(Success<List<Doctor>>(doctors))
        } catch (e: FirebaseFirestoreException) {
            emit(Error(e.message ?: "unexpected error occurred"))
        } catch (e: Exception) {
            emit(Error("unexpected error occurred. please check your internet connection"))
        }
    }
}