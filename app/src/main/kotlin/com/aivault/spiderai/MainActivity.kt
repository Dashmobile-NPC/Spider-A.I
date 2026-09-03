package com.aivault.spiderai

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF05070C)
private val Panel = Color(0xFF0B101A)
private val Accent = Color(0xFF57D9FF)
private val Muted = Color(0xFF8A94A6)

data class Message(val text: String, val user: Boolean)

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                requestPermissions = {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                },
                openSettings = {
                    startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.parse("package:$packageName")
                        }
                    )
                },
                openWeb = { url ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            )
        }
    }
}

@Composable
private fun App(
    requestPermissions: () -> Unit,
    openSettings: () -> Unit,
    openWeb: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(
                Message(
                    "SPIDER-AI online. Release-ready build detected. Device tools remain permission-controlled.",
                    false
                )
            )
        )
    }

    MaterialTheme(colorScheme = darkColorScheme(background = Bg, surface = Panel, primary = Accent)) {
        Surface(modifier = Modifier.fillMaxSize(), color = Bg) {
            Column(Modifier.fillMaxSize().padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("SPIDER-AI", color = Accent, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                        Text("CONNECTED MODE", color = Muted, fontSize = 11.sp)
                    }
                    TextButton(onClick = openSettings) { Text("PERMISSIONS") }
                }

                Spacer(Modifier.height(16.dp))
                Box(Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                    Box(
                        Modifier.size(76.dp).background(Accent.copy(alpha = .12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) { Text("AI", color = Accent, fontWeight = FontWeight.Bold, fontSize = 20.sp) }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = requestPermissions, label = { Text("Grant device tools") })
                    AssistChip(onClick = { openWeb("https://www.google.com") }, label = { Text("Open web") })
                }

                Spacer(Modifier.height(12.dp))
                LazyColumn(
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(messages) { message ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = if (message.user) Arrangement.End else Arrangement.Start
                        ) {
                            Surface(
                                color = if (message.user) Color(0xFF102B39) else Panel,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(message.text, color = Color.White, modifier = Modifier.padding(13.dp))
                            }
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask SPIDER-AI…", color = Muted) },
                        singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        enabled = input.isNotBlank(),
                        onClick = {
                            val question = input.trim()
                            messages = messages + Message(question, true) + Message(localReply(question), false)
                            input = ""
                        }
                    ) { Text("SEND") }
                }
            }
        }
    }
}

private fun localReply(question: String): String = when {
    question.contains("hello", true) || question.contains("hi", true) ->
        "Hey. SPIDER-AI here. Ready when you are."
    question.contains("internet", true) || question.contains("search", true) ->
        "Connected mode is enabled. A production AI/web provider can be connected through the tool layer."
    question.contains("permission", true) ->
        "Android controls sensitive access. Use Permissions to grant or revoke device capabilities."
    else ->
        "I got you: \"$question\". The shell is ready for the generative AI and tool providers you choose."
}
