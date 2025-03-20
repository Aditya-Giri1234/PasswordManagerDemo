package com.aditya.passwordmanagerdemo.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String = "",
    color: Color = Color.Black
) {
    ElevatedButton(
        modifier = Modifier,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(
            label, style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White ,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}