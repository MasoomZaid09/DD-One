package org.example.internal_logcat.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.down_arrow
import internallogcat.composeapp.generated.resources.rem_regular
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.example.internal_logcat.utils.AppColors

@Composable
fun DropDownCustom(options: List<String>) :String{

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    Box(

        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(
                AppColors.editTextColor
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().clickable {
                expanded = true
            }) {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .background(AppColors.editTextColor)
                    .padding(16.dp),
                color = AppColors.textGrey,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )

            Image(
                painterResource(Res.drawable.down_arrow),
                modifier = Modifier.size(30.dp).padding(end = 5.dp),
                contentDescription = "Dropdown Arrow"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(AppColors.editTextColor)
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = label,
                            color = AppColors.textGrey,
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(Res.font.rem_regular))
                        )
                    },
                    onClick = {
                        selectedOption = label
                        expanded = false
                    }
                )
            }
        }
    }

    return selectedOption
}
