package com.example.data.dao

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Leads
    @Query("SELECT * FROM leads ORDER BY createdAt DESC")
    fun getAllLeads(): Flow<List<Lead>>
    
    @Query("SELECT * FROM leads WHERE id = :id LIMIT 1")
    fun getLeadById(id: Int): Flow<Lead?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: Lead): Long

    @Update
    suspend fun updateLead(lead: Lead)

    // Properties
    @Query("SELECT * FROM properties ORDER BY createdAt DESC")
    fun getAllProperties(): Flow<List<Property>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property)
    
    // Attendance
    @Query("SELECT * FROM attendance ORDER BY checkInTime DESC")
    fun getAllAttendance(): Flow<List<Attendance>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance)

    @Update
    suspend fun updateAttendance(attendance: Attendance)
    
    // CallLog
    @Query("SELECT * FROM calls WHERE leadId = :leadId ORDER BY startedAt DESC")
    fun getCallsForLead(leadId: Int): Flow<List<CallLog>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallLog(callLog: CallLog)

    // SocialPosts
    @Query("SELECT * FROM social_posts ORDER BY createdAt DESC")
    fun getAllSocialPosts(): Flow<List<SocialPost>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocialPost(post: SocialPost)
}
