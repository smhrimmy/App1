package com.example.data.repository

import com.example.data.dao.AppDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class MainRepository(private val appDao: AppDao) {

    val allLeads: Flow<List<Lead>> = appDao.getAllLeads()
    val allProperties: Flow<List<Property>> = appDao.getAllProperties()
    val allAttendance: Flow<List<Attendance>> = appDao.getAllAttendance()
    val allSocialPosts: Flow<List<SocialPost>> = appDao.getAllSocialPosts()

    fun getLeadById(id: Int): Flow<Lead?> = appDao.getLeadById(id)
    fun getCallsForLead(leadId: Int): Flow<List<CallLog>> = appDao.getCallsForLead(leadId)

    suspend fun insertLead(lead: Lead) = appDao.insertLead(lead)
    suspend fun updateLead(lead: Lead) = appDao.updateLead(lead)
    suspend fun insertProperty(property: Property) = appDao.insertProperty(property)
    suspend fun insertCallLog(callLog: CallLog) = appDao.insertCallLog(callLog)
    
    suspend fun checkIn(attendance: Attendance) = appDao.insertAttendance(attendance)
    suspend fun checkOut(attendance: Attendance) = appDao.updateAttendance(attendance)
    
    suspend fun insertSocialPost(post: SocialPost) = appDao.insertSocialPost(post)
}
