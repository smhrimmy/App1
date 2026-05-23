package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val checkInTime: Long,
    val checkOutTime: Long? = null,
    val checkInLat: Double? = null,
    val checkInLng: Double? = null,
    val status: String, // Present, Late, Absent
    val notes: String = ""
)
