package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val location: String,
    val address: String,
    val type: String, // Apartment, Villa, Plot, Commercial
    val price: Long,
    val sizeSqft: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val status: String = "Available", // Available, Hold, Sold, Rented
    val description: String,
    val createdAt: Long = System.currentTimeMillis(),
    val imageUrl: String? = null
)
