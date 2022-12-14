package com.example.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme


const val API_KEY = "05d896deae20466d97b121834220811"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Omsk", this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String,context: Context) {
    val state = remember{
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box (modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Temp in $name = ${state.value}")
        }
        Box (modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(onClick = {
                getResault(name,state, context)

            },modifier = Modifier.padding(5.dp)
                .fillMaxWidth()
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

private fun getResault(city : String, state : MutableState<String>, context: Context) {
    val url = "https://api.weatherapi.com/v1/current.json?" +
            "key=$API_KEY&" + "q=$city" + "&aqi=no"
//    https://api.weatherapi.com/v1/current.json?key=05d896deae20466d97b121834220811&q=London&aqi=no
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            state.value = response
        },
        {
            error ->
            Log.d("MyLog", "Error $error")
        }

        )
    queue.add(stringRequest)
}