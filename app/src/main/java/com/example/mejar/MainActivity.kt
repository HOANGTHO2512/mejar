package com.example.mejar

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mejar.ui.theme.MejarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MejarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main()
                }
            }
        }
    }
}
@Composable
fun FirstScreen(navController: NavController){
    val context = LocalContext.current
    var url by remember { mutableStateOf("https://www.pu.edu.tw") }
    Column(modifier = Modifier.fillMaxSize()
        .background(Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TextField(
            value = url,
            onValueChange = { newText ->
                url = newText
            },
            label = { Text(text = "網址") },
            placeholder = { Text(text = "tronclass") }
        )
        Text(
            text = "SecondAcyivity\n網址為：" + url!!
        )
        Button(onClick = {
            navController.navigate("JumpSecond")
        }) {
            Text(text = "我是畫面1，按一下跳至畫面2")
        }
        Button(
            onClick = {
                var it = Intent(Intent.ACTION_VIEW)
                it.data = Uri.parse(url)
                context.startActivity(it)
            }
        )
        {
            Text(text = "開啟瀏覽器")
        }
        Button(
            onClick = {
                var it = Intent(Intent.ACTION_SENDTO)
                it.data = Uri.parse("mailto:lehoangtho25122004@gmail.com")
                context.startActivity(it)
            }
        )
        {
            Text(text = "寄發電子郵件")
        }
        Button(
            onClick = {
                var it = Intent(Intent.ACTION_WEB_SEARCH)
                it.putExtra(SearchManager.QUERY, "靜宜資管")
                context.startActivity(it)
            }
        )
        {
            Text(text = "搜尋關鍵字")
        }
        Button(
            onClick = {
                var it = Intent(Intent.ACTION_VIEW)
                it.data = Uri.parse("geo:24.2267756,120.5771591")
                context.startActivity(it)
            }
        )
        {
            Text(text = "Google Map查詢")
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = (context as Activity)
    var url:String? = context.intent.getStringExtra("website")
    Column {
        Button(
            onClick = {
                activity.finish()
            })
        {
            Text(text = "回到MainActivity")
        }
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url!!)
            }
        )
    }
}
@Composable
fun SecondScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("JumpFirst")
        }) {
            Text(text = "我是畫面2，按一下跳至畫面1")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    Column {
        TopAppBar(
            title = { Text(text = "頁面轉換實例") },
            navigationIcon = {
                IconButton(onClick = {
                    Toast.makeText(context, "您點選了導覽圖示", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Navigation icon")
                }
            },
            actions = {
                IconButton(
                    onClick = { Toast.makeText(context, "作者：黎黃壽", Toast.LENGTH_SHORT).show() }
                ) {
                    Icon(Icons.Rounded.AccountBox, contentDescription = "Author")
                }
                IconButton(
                    onClick = { showMenu = true }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
                DropdownMenu(
                    expanded = showMenu, onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("畫面1") },
                        onClick = { navController.navigate("JumpFirst")})
                    DropdownMenuItem(
                        text = { Text("畫面2") },
                        onClick = { navController.navigate("JumpSecond")})
                }
            }
        )
    NavHost(navController = navController, startDestination = "JumpFirst"){
        composable("JumpFirst"){
            FirstScreen(navController = navController)
        }
        composable("JumpSecond"){
            SecondScreen(navController = navController)
        }
    }
}
}
