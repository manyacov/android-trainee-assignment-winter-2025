package details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalTextDim
import com.manyacov.resources.R
import com.manyacov.resources.theme.LocalDim
import com.manyacov.resources.theme.color.setOutlinedTextFieldColors

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String? = null,
    onValueChange: (String) -> Unit = {},
    searchTrack: (String) -> Unit = {},
    onNextFocus: KeyboardActionScope.() -> Unit = {},
    setColorScheme: @Composable () -> TextFieldColors = { setOutlinedTextFieldColors() }
) {
    val trackName by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LocalDim.current.spaceSize14),
        colors = setColorScheme(),

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onNext = onNextFocus
        ),

        placeholder = {
            Text(
                text = label,
                fontSize = LocalTextDim.current.textSize18,
                color = DarkGray
            )
        },
        singleLine = true,
        textStyle = TextStyle.Default.copy(
            color = MaterialTheme.colorScheme.inverseSurface,
            fontSize = LocalTextDim.current.textSize18
        ),

        trailingIcon = {
            IconButton(onClick = { searchTrack(trackName) }) {
                Icon(
                    modifier = Modifier.padding(vertical = LocalDim.current.spaceSize4),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search_track",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        },

        value = value.orEmpty(),
        onValueChange = onValueChange
    )
}

@Preview(backgroundColor = 0xFF1C1B1B)
@Composable
fun OutlinedHiddenTextInputBoxPreview() {
    AvitoPlayerTheme {
        AppSearchBar(
            label = "Search track...",
            value = null
        )
    }
}