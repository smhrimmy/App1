package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.model.*
import com.example.data.repository.MainRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = MainRepository(database.appDao())

    val allLeads: StateFlow<List<Lead>> = repository.allLeads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProperties: StateFlow<List<Property>> = repository.allProperties
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    val allAttendance: StateFlow<List<Attendance>> = repository.allAttendance
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSocialPosts: StateFlow<List<SocialPost>> = repository.allSocialPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getLeadById(id: Int) = repository.getLeadById(id)
    fun getCallsForLead(leadId: Int) = repository.getCallsForLead(leadId)

    fun addLead(lead: Lead) {
        viewModelScope.launch { repository.insertLead(lead) }
    }
    
    fun updateLead(lead: Lead) {
        viewModelScope.launch { repository.updateLead(lead) }
    }

    fun addProperty(property: Property) {
        viewModelScope.launch { repository.insertProperty(property) }
    }

    fun logCall(leadId: Int, outcome: String, duration: Int = Random.nextInt(30, 300)) {
        viewModelScope.launch {
            repository.insertCallLog(
                CallLog(
                    leadId = leadId,
                    status = "Completed",
                    durationSeconds = duration,
                    outcome = outcome
                )
            )
        }
    }
    
    fun checkIn(lat: Double, lng: Double) {
        viewModelScope.launch {
            repository.checkIn(
                Attendance(
                    checkInTime = System.currentTimeMillis(),
                    checkInLat = lat,
                    checkInLng = lng,
                    status = "Present"
                )
            )
        }
    }
    
    fun checkOut(attendance: Attendance) {
        viewModelScope.launch {
            repository.checkOut(
                attendance.copy(checkOutTime = System.currentTimeMillis())
            )
        }
    }

    fun addSocialPost(post: SocialPost) {
        viewModelScope.launch { repository.insertSocialPost(post) }
    }

    init {
        // Seed some data if empty
        viewModelScope.launch {
            repository.allProperties.first().takeIf { it.isEmpty() }?.let {
                addProperty(Property(title = "Luxury Villa", location = "Gurgaon", address = "Golf Course Rd", type = "Villa", price = 12000000, sizeSqft = 3500, bedrooms = 4, bathrooms = 4, description = "Premium villa with pool"))
                addProperty(Property(title = "City Apartment", location = "Delhi", address = "South Ext", type = "Apartment", price = 8500000, sizeSqft = 1800, bedrooms = 3, bathrooms = 2, description = "Spacious 3BHK"))
            }
            repository.allLeads.first().takeIf { it.isEmpty() }?.let {
                addLead(Lead(fullName = "Rahul Sharma", phone = "+919999999999", email = "rahul@example.com", source = "Website", propertyType = "Apartment", budgetMin = 7500000, budgetMax = 12000000, preferredLocation = "Gurgaon"))
                addLead(Lead(fullName = "Priya Singh", phone = "+918888888888", email = "priya@example.com", source = "Facebook", propertyType = "Villa", budgetMin = 10000000, budgetMax = 20000000, preferredLocation = "Delhi"))
            }
        }
    }
}
