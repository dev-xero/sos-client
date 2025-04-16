package group.one.sos.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import group.one.sos.R

@Composable
fun ContactPill(
    modifier: Modifier = Modifier,
    displayName: String,
    phoneNumber: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (displayName.length > 48) displayName.substring(48) + "..."
                    else displayName,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(R.drawable.ic_right_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}