package com.kaleksandra.featureprofile.presentation

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kaleksandra.coretheme.Dimen
import com.kaleksandra.coreui.compose.string
import com.kaleksandra.featureprofile.R
import com.kaleksandra.featureprofile.domain.models.Profile

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profileState.collectAsState()
    ProfileScreen(profile)
}

@Composable
fun ProfileScreen(
    profile: Profile?
) {
    profile?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                AsyncImage(
                    it.header,
                    null,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        it.avatar,
                        null,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                Dimen.radius_4,
                                MaterialTheme.colorScheme.background,
                                CircleShape
                            )
                            .clip(CircleShape),
                        contentScale = ContentScale.FillHeight
                    )
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = Dimen.padding_8),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = Dimen.padding_28)
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.padding_20)
                    .weight(1f)
            ) {
                Text(
                    text = string(id = R.string.title_mail),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = it.email,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Dimen.padding_8),
                )
            }
            OutlinedButton(
                onClick = { /* TODO: Add onLogOut function */ },
                modifier = Modifier.padding(bottom = Dimen.padding_20),
            ) {
                Text(
                    text = string(R.string.button_log_out),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Preview() {
    ProfileScreen(
        Profile(
            "http://192.168.15.226:8080/api/collection/image/e9524da128a50166f2f0b35e2a8822fa.jpg",
            "http://192.168.15.226:8080/api/collection/image/main_2x.jpg",
            "Aleksandra10",
            "a.testova@gmail.com"
        )
    )
}