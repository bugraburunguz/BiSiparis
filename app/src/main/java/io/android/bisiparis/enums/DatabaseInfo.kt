package io.android.bisiparis.enums


enum class DatabaseInfo {

    DatabaseName {
        override fun toString(): String {
            return "Customer"
        }
    },

    DatabaseVersion {
        override fun toString(): String {
            return "1"
        }
    }
}