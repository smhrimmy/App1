package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "social_posts")
data class SocialPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // Instagram, Facebook, LinkedIn
    val caption: String,
    val status: String = "Draft", // Idea, Draft, Scheduled, Published
    val scheduledDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
