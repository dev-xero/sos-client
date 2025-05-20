package group.one.sos.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import group.one.sos.presentation.theme.PaleMaroon
import group.one.sos.presentation.theme.Primary
import group.one.sos.presentation.theme.White


@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    textResource: Int,
    secondary: Boolean = false,
    disabled: Boolean = false,
) {
    Button(
        onClick = action,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (secondary) PaleMaroon else Primary,
            contentColor = White
        ),
        enabled = !disabled
    ) {
        Text(text = stringResource(textResource))
    }
}
