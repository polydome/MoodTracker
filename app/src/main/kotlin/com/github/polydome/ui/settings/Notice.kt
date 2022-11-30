package com.github.polydome.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Notice(modifier: Modifier = Modifier, text: String, type: NoticeType = NoticeType.Info, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        elevation = 12.dp,
        backgroundColor = type.backgroundColor,
        onClick = onClick
    ) {
        Column(
            Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.End
        ) {
            Box(modifier = Modifier.background(type.primaryColor).height(6.dp).fillMaxWidth())
            Row(
                modifier = Modifier
                    .padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Icon(
                    imageVector = type.icon,
                    contentDescription = null
                )
                Text(text)
            }
        }
    }
}

data class NoticeType(val backgroundColor: Color, val primaryColor: Color, val icon: ImageVector) {
    companion object
}

val NoticeType.Companion.Info: NoticeType
    get() = NoticeType(
        primaryColor = Color(0xff1e7898),
        backgroundColor = Color(0xffbee0ec),
        icon = Icons.Filled.Info
    )