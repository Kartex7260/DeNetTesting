package kanti.denet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanti.denet.shared.asEthereumAddress

@Composable
fun NodeCard(
    modifier: Modifier = Modifier,
    state: NodeUiState,
    contentPadding: PaddingValues = PaddingValues(
        start = 12.dp,
        end = 4.dp,
        top = 4.dp,
        bottom = 4.dp
    ),
    onClick: () -> Unit = {},
    tailContent: (@Composable () -> Unit)? = null
) = Card(
    modifier = Modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            role = Role.Button,
            onClick = onClick
        )
        .then(modifier)
) {
    Row(
        modifier = Modifier.padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = state.hash.asEthereumAddress,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (tailContent != null) {
            tailContent()
        }
    }
}

@Preview
@Composable
private fun PreviewNodeCard() {
    NodeCard(
        state = NodeUiState(
            hash = "708255089m091385vn80315135424gg24"
        ),
        tailContent = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    )
}