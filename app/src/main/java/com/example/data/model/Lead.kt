package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class Lead(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val phone: String,
    val email: String,
    val source: String,
    val propertyType: String,
    val budgetMin: Long,
    val budgetMax: Long,
    val preferredLocation: String,
    val status: String = "New", // New, Contacted, Interested, Site Visit Scheduled, Won, Lost
    val temperature: String = "Cold", // Cold, Warm, Hot
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val nextFollowUpAt: Long? = null,
    val assignedAgentId: Int = 1
)
