package com.aditya.passwordmanagerdemo.ui.widgets.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import com.aditya.passwordmanagerdemo.ui.components.AddHorizontalSpace

@Composable
fun PasswordItem(modifier: Modifier = Modifier, passwordInfo: PasswordInfo , onItemClick : (PasswordInfo) -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ) ,
        onClick = {
            onItemClick(passwordInfo)
        } ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 10.dp) ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                passwordInfo.passwordType,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            AddHorizontalSpace(10)
            Text("********" , style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            ))
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                onItemClick(passwordInfo)
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight , contentDescription = "" , tint = Color.Black)
            }
        }
    }
}