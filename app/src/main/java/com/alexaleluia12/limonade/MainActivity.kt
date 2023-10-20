package com.alexaleluia12.limonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexaleluia12.limonade.ui.theme.LimonadeTheme

val defaultLemon = Lemon(R.drawable.lemon_restart, "Empty Glass", "Tab to restart")
val lemonsData = mapOf(
    1 to Lemon(R.drawable.lemon_tree, "Lemon Tree", "Tab the lemon tree to select a lemon"),
    2 to Lemon(R.drawable.lemon_squeeze, "Lemon", "Tab on lemon many times to squeeze it"),
    3 to Lemon(R.drawable.lemon_drink, "Glass lemon juice", "Tab on the lass to drink it"),
    4 to defaultLemon
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RandomTapSingleton.rangeValues = 2..4
        RandomTapSingleton.rezet()
        setContent {
            LimonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LimonadeApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LimonadeApp() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Limonade",
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth(),
            color = Color.Black,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
        )
        // clicar na imagen troca de imagen e texto
        CoreLimonade(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
            lemonId = 1
        )

    }
}


@Composable
fun CoreLimonade(modifier: Modifier = Modifier, lemonId: Int) {
    var lemonState by remember { mutableStateOf(lemonId) }
    val lemon = lemonsData[lemonState] ?: defaultLemon
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(lemon.resource),
            contentDescription = lemon.imgDescription,
            modifier = Modifier.clickable{
                if (lemonState == 2) {
                    Log.d("bnt", "shoud click ${RandomTapSingleton.times} times")
                    val finished = RandomTapSingleton.increment()
                    if (finished)
                        lemonState++

                } else if (lemonState == 4) {
                    lemonState = 1
                } else {
                    lemonState += 1
                }

            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(lemon.imgLabel, fontSize = 18.sp)
    }
}


data class Lemon(val resource: Int, val imgDescription: String, val imgLabel: String)

// fonte: https://blog.mindorks.com/how-to-create-a-singleton-class-in-kotlin/
object RandomTapSingleton {
    var rangeValues = 1..10
    internal var times = rangeValues.random()
    private var count = 0
    fun increment(): Boolean {
        count++
        if (count == times) {
            rezet()
            return true
        }
        return false
    }
    fun rezet() {
        times = rangeValues.random()
        count = 0
    }
}
