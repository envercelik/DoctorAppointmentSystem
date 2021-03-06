package com.envercelik.doctorappointmentsystem.data

import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.Resource.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signup(email: String, password: String): Resource<String> {
        return try {
            Loading<Nothing>()
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Success(result.user!!.uid)
        } catch (e: FirebaseAuthException) {
            Error(e.message ?: "unexpected error occurred")
        } catch (e: Exception) {
            Error("unexpected error occurred. please check your internet connection")
        }
    }

    suspend fun login(email: String, password: String): Resource<String> {
        return try {
            Loading<Nothing>()
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Success(result.user!!.uid)
        } catch (e: FirebaseAuthException) {
            Error(e.message ?: "unexpected error occurred")
        } catch (e: Exception) {
            Error("unexpected error occurred. please check your internet connection")
        }
    }
}