package com.example.ui.leads

import android.widget.Toast
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadDetailScreen(leadId: Int, viewModel: MainViewModel, navController: NavController) {
    val lead by viewModel.getLeadById(leadId).collectAsStateWithLifecycle(initialValue = null)
    val calls by viewModel.getCallsForLead(leadId).collectAsStateWithLifecycle(initialValue = emptyList())
    val context = LocalContext.current

    var showPropertyDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lead?.fullName ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (lead == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Quick Actions
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickActionButton(
                            icon = Icons.Default.Phone,
                            label = "Call Bridge",
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Toast.makeText(context, "Call Bridge Mock: Attempting to connect agent with ${lead!!.fullName} via Twilio...", Toast.LENGTH_LONG).show()
                            
                            // Mock Call Bridge / Open Dialer for Local Simulation
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${lead!!.phone}")
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Dialer not available on this device", Toast.LENGTH_SHORT).show()
                            }
                            viewModel.logCall(lead!!.id, "Connected")
                        }
                        QuickActionButton(
                            icon = Icons.AutoMirrored.Filled.Message,
                            label = "WhatsApp",
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://wa.me/${lead!!.phone.replace("+", "")}")
                            }
                            // To avoid crash if whatsapp not installed in emulator
                            try { context.startActivity(intent) } catch(e: Exception) {}
                        }
                        QuickActionButton(
                            icon = Icons.Default.Share,
                            label = "Send Property",
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            showPropertyDialog = true
                        }
                    }
                }

                item {
                    HorizontalDivider()
                }

                // Lead Info
                item {
                    Text("Lead Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoRow("Phone", lead!!.phone)
                            InfoRow("Email", lead!!.email)
                            InfoRow("Source", lead!!.source)
                            InfoRow("Requirement", "${lead!!.propertyType} in ${lead!!.preferredLocation}")
                            InfoRow("Budget", "${lead!!.budgetMin / 100000}L - ${lead!!.budgetMax / 100000}L")
                            InfoRow("Status", lead!!.status)
                            InfoRow("Temperature", lead!!.temperature)
                        }
                    }
                }

                item {
                    Text("Activity Timeline", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                items(calls) { call ->
                    val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                    ListItem(
                        leadingContent = { Icon(Icons.Default.Phone, contentDescription = null) },
                        headlineContent = { Text("Call: ${call.outcome}") },
                        supportingContent = { Text(sdf.format(Date(call.startedAt))) },
                        trailingContent = { Text("${call.durationSeconds}s") }
                    )
                }
                
                item { Spacer(modifier = Modifier.height(40.dp)) }
            }
            
            if (showPropertyDialog) {
                // Simple dialog mock for sending property
                AlertDialog(
                    onDismissRequest = { showPropertyDialog = false },
                    title = { Text("Share Property") },
                    text = { Text("Select a property to share with ${lead!!.fullName}") },
                    confirmButton = {
                        TextButton(onClick = { showPropertyDialog = false }) { Text("Share via WhatsApp") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showPropertyDialog = false }) { Text("Cancel") }
                    }
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun QuickActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: androidx.compose.ui.graphics.Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {
        FilledIconButton(
            onClick = onClick,
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = color)
        ) {
            Icon(icon, contentDescription = label)
        }
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}
