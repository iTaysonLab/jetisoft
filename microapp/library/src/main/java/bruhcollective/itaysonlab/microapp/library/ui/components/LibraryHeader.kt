package bruhcollective.itaysonlab.microapp.library.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.repository.UserRepository
import bruhcollective.itaysonlab.microapp.library.R
import bruhcollective.itaysonlab.microapp.library.ui.LibraryScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryHeader(
    currentSortDirection: LibraryScreenViewModel.SortDirection,
    currentSortField: UserRepository.GameSortField,
    currentPlatformFilter: String?,
    currentPlatformFiltersAvailable: List<String>,
    onSortDirectionChange: (LibraryScreenViewModel.SortDirection) -> Unit,
    onSortFieldChange: (UserRepository.GameSortField) -> Unit,
    onPlatformFilterChange: (String?) -> Unit,
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    var dropdownState by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .weight(1f)
                .drawWithContent {
                    drawContent()

                    val halfHeight = size.height / 2f
                    drawRect(
                        Brush.linearGradient(
                            colors = listOf(Color.Transparent, surfaceColor),
                            start = Offset(
                                y = halfHeight,
                                x = size.width * 0.8f
                            ), end = Offset(
                                y = halfHeight,
                                x = size.width
                            )
                        )
                    )
                },
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(currentPlatformFiltersAvailable) { filter ->
                val selected = currentPlatformFilter == filter

                FilterChip(onClick = {
                    onPlatformFilterChange(if (selected) null else filter)
                }, label = {
                    Text(text = filter)
                }, leadingIcon = {
                    if (selected) {
                        Icon(Icons.Rounded.Done, contentDescription = null)
                    }
                }, selected = selected)
            }
        }

        IconButton(
            onClick = { dropdownState = true },
            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
        ) {
            LibraryHeaderDropdown(
                expanded = dropdownState,
                onDismiss = { dropdownState = false },
                currentSortDirection = currentSortDirection,
                currentSortField = currentSortField,
                onSortDirectionChange = onSortDirectionChange,
                onSortFieldChange = onSortFieldChange
            )

            Icon(Icons.Rounded.Sort, contentDescription = null)
        }
    }
}

@Composable
private fun LibraryHeaderDropdown(
    expanded: Boolean,
    currentSortDirection: LibraryScreenViewModel.SortDirection,
    currentSortField: UserRepository.GameSortField,
    onDismiss: () -> Unit,
    onSortDirectionChange: (LibraryScreenViewModel.SortDirection) -> Unit,
    onSortFieldChange: (UserRepository.GameSortField) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(x = 0.dp, y = 4.dp)
    ) {
        Row(
            Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp)
                .fillMaxWidth()
        ) {
            val strokeSelected = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            val strokeUnselected = CardDefaults.outlinedCardBorder()
            val colorsSelected =
                CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primary)
            val colorsUnselected = CardDefaults.outlinedCardColors()

            val isDesc = currentSortDirection == LibraryScreenViewModel.SortDirection.Desc

            OutlinedCard(
                Modifier.weight(1f),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                border = if (isDesc) strokeSelected else strokeUnselected,
                colors = if (isDesc) colorsSelected else colorsUnselected
            ) {
                Icon(Icons.Rounded.Sort, contentDescription = null, modifier = Modifier
                    .clickable {
                        onSortDirectionChange(LibraryScreenViewModel.SortDirection.Desc)
                    }
                    .padding(vertical = 16.dp)
                    .fillMaxWidth())
            }

            OutlinedCard(
                Modifier.weight(1f),
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                border = if (!isDesc) strokeSelected else strokeUnselected,
                colors = if (!isDesc) colorsSelected else colorsUnselected
            ) {
                Icon(Icons.Rounded.Sort, contentDescription = null, modifier = Modifier
                    .clickable {
                        onSortDirectionChange(LibraryScreenViewModel.SortDirection.Asc)
                    }
                    .padding(vertical = 16.dp)
                    .graphicsLayer(scaleY = -1f)
                    .fillMaxWidth())
            }
        }

        Text(
            text = stringResource(id = R.string.sort),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .padding(top = 4.dp),
            fontSize = 13.sp
        )

        UserRepository.GameSortField.values().forEach { field ->
            DropdownMenuItem(text = {
                Text(
                    stringResource(id = when (field) {
                        UserRepository.GameSortField.RecentlyPlayed -> R.string.sort_recently
                        UserRepository.GameSortField.ReleaseDate -> R.string.sort_release
                        UserRepository.GameSortField.Name -> R.string.sort_name
                    })
                )
            }, onClick = {
                onDismiss()
                onSortFieldChange(field)
            }, trailingIcon = {
                if (currentSortField == field) {
                    Icon(
                        Icons.Rounded.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            })
        }
    }
}