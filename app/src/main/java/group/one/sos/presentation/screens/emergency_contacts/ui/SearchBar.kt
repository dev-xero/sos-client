package group.one.sos.presentation.screens.emergency_contacts.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import group.one.sos.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = "Find contacts") },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp)
    )
}