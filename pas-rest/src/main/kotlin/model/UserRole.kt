package model

import java.lang.Exception

enum class UserRole {
    ADMIN, WORKER, CLIENT;

    companion object{
        @JvmStatic
        fun fromString(role: String): UserRole {
            return when(role){
                "ADMIN" -> ADMIN
                "WORKER" -> WORKER
                "CLIENT" -> CLIENT
                else -> throw Exception()
            }
        }
    }
}