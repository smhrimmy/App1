package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calls")
data class CallLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val leadId: Int,
    val status: String,
    val durationSeconds: Int,
    val outcome: String,
    val startedAt: Long = System.currentTimeMillis()
)
