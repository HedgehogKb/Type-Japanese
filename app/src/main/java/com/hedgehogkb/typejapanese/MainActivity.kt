package com.hedgehogkb.typejapanese

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hedgehogkb.typejapanese.ui.theme.TypeJapaneseTheme

val romanjiChecker = RomanjiChecker()
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TypeJapaneseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TypeJapaneseLayout()
                }
            }
        }
    }
}

fun getRandomKana(): String {
    when((1..2).random()) {
        1 -> {
            return romanjiChecker.getHiraganaMap().keys.random()
        }
        else -> {
            return romanjiChecker.getKatakanaMap().keys.random()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TypeJapaneseLayout(
    modifier: Modifier = Modifier
) {
    var currentKana by remember { mutableStateOf("") }
    var romanjiGuess by remember { mutableStateOf("") }
    if (currentKana == "") currentKana = getRandomKana()

    val doneLogic = {
        if (romanjiChecker.doesRomanjiMatchKana(currentKana, romanjiGuess)) {
            // Correct answer stuff:
            currentKana = getRandomKana()
            romanjiGuess = ""
        } else {
            // Wrong answer logic
            romanjiGuess = ""
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding()
    ) {
        // Displays Hiragana/Katakana character(s)
        Text(
            text = currentKana,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        // For Hiragana/Katakana input()
        TextField(
            label = { Text(stringResource(R.string.your_answer)) },
            value = romanjiGuess,
            onValueChange = { romanjiGuess = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { doneLogic() }
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            onClick = doneLogic
        ) {
            Text(
                text = stringResource(R.string.check_answer)
            )
        }
    }
}

@Composable
fun ResponseBar(

    modifier: Modifier = Modifier,
) {

}