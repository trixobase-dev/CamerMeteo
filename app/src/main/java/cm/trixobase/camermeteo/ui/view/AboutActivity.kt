package cm.trixobase.camermeteo.ui.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 05/05/26
 */

class AboutActivity : ApplicationActivity() {

    private var showDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(R.string.about)) },
                    content = { MyContent(Modifier.padding(it)) }
                )
            }
        }
    }

    @Composable
    private fun MyContent(modifier: Modifier = Modifier) {
        val context = LocalContext.current.applicationContext
        showDialog = remember { mutableStateOf(false) }
        val colors = MaterialTheme.colorScheme

        Surface {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                MyLine()
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CamerMétéo",
                            textAlign = TextAlign.Center,
                            color = colors.primary,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.comic)),
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.iv_logo),
                            contentDescription = "CamerMeteo Logo",
                            modifier = Modifier.size(120.dp)
                        )
                        Text(
                            text = context.getString(R.string.version) + " 1.0",
                            textAlign = TextAlign.Center,
                            color = colors.primary,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom) {
                            Button(
                                onClick = { showDialog.value = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.width(95.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.iv_trixobase),
                                        contentDescription = "Trixobase Logo",
                                        modifier = Modifier
                                            .size(25.dp)
                                            .padding(end = 5.dp)
                                    )
                                    Text(
                                        text = context.getString(R.string.plus),
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                    }
                }
            }
        }
        if (showDialog.value)
            MyDialogTrixo()
    }

    @Composable
    fun MyDialogTrixo() {
        val context = LocalContext.current.applicationContext
        val location = context.getString(R.string.trixobase_enterprise_location)
        val description = context.getString(R.string.trixobase_enterprise_description)
        val phoneNumber = context.getString(R.string.trixobase_enterprise_phone_number)

        AlertDialog(
            containerColor = Color.Black,
            onDismissRequest = { showDialog.value = false },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp),
                        painter = painterResource(id = R.drawable.iv_trixo_inc),
                        contentDescription = "Trixobase Enterprise Logo"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = description,
                        lineHeight = 20.sp,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.akt)),
                        textAlign = TextAlign.Justify)
                    Spacer(Modifier.height(15.dp))
                    Row(
                        Modifier.fillMaxWidth().height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_location),
                            contentDescription = "Localisation")
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = location,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            textAlign = TextAlign.Center,)
                    }
                    Row(
                        Modifier.fillMaxWidth().height(40.dp).clickable {
                            Utils.phone.launchCall(context)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_call),
                            contentDescription = "Service client")
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = phoneNumber,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            textAlign = TextAlign.Center,)
                    }
                    Row(
                        Modifier.fillMaxWidth().height(40.dp).clickable {
                            Utils.phone.openBrowser(context)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_web),
                            contentDescription = "web site")
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "https://trixobase.com",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            textAlign = TextAlign.Center,)
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

}