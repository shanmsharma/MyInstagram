package com.sharma.user.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sharma.authentication.R

@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel(), navController: NavController) {
    val profileState by viewModel.profileState.collectAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var postText by remember { mutableStateOf("") } // Chat input

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.LightGray
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                UserProfileTopBar(
                    selectedImageUri = selectedImageUri,
                    username = username,
                    onUsernameChange = { username = it },
                    email = email,
                    bio = bio,
                    onBioChange = { bio = it }
                )

                UserFeedSection()

                UserChatBar(
                    postText = postText,
                    onPostTextChange = { postText = it }
                )
            }
        }
    }
}

@Composable
fun UserProfileTopBar(
    selectedImageUri: Uri?,
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    bio: String,
    onBioChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // ✅ Fixed Height (~30% of the screen)
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // **Profile Picture (Left)**
            AsyncImage(
                model = selectedImageUri ?: R.drawable.default_profile,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // **Editable User Information (Right, 1/3 of the screen)**
            Column(
                modifier = Modifier.weight(1f) // Takes remaining space
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {},
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false // Email is non-editable
                )
                OutlinedTextField(
                    value = bio,
                    onValueChange = onBioChange,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun UserFeedSection() {
    Box(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "User Feed Coming Soon...",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun UserChatBar(
    postText: String,
    onPostTextChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Gray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = postText,
            onValueChange = onPostTextChange,
            label = { Text("Write something...") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Button(
            onClick = { /* TODO: Implement Post Submission */ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Post")
        }
    }
}

