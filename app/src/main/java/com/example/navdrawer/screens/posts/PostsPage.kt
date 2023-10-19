package com.example.navdrawer.screens.posts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navdrawer.R
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.model.PostRegisterResponse
import com.example.navdrawer.screens.home.CardOrganizaciones
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.BlancoGris
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.Purple80
import com.example.navdrawer.ui.theme.RojoFrisa
import com.example.navdrawer.viewModel.OrgViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostsPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrisClaro),
        horizontalAlignment = Alignment.CenterHorizontally) {
        header()
    }
    FormularioPost(navController = navController)
}

@Composable
fun header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Haz una publicación",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 40.dp)
                .align(Alignment.CenterHorizontally)
            //.offset(y = ((-50).dp))
        )

        // Imagen de línea roja
        Image(
            painter = painterResource(id = R.drawable.linea_roja),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = ((-110).dp))
        )   
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormularioPost(navController: NavController) {
    val orgViewModel = OrgViewModel(OrgService.instance)
    val showDelayedText = remember { mutableStateOf(false) }

    Column {
        FlowRow(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp)
                .fillMaxSize()
                .background(color = GrisClaro),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            // Text Input y otros elementos

            val title = remember { mutableStateOf("") }
            val content = remember { mutableStateOf("") }
            val image = remember { mutableStateOf("") }

            OutlinedTextField(
                value = title.value,
                onValueChange = {
                    title.value = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlancoGris,
                    unfocusedBorderColor = BlancoGris,
                    focusedContainerColor = BlancoGris,
                    unfocusedContainerColor = BlancoGris
                ),
                label = { Text("Título") },
                modifier = Modifier
                    .padding(5.dp)
                    .padding(start = 10.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
                    .height(65.dp)
                //.offset(y = (130).dp)
                ,
                shape = RoundedCornerShape(90)
            )

            OutlinedTextField(
                value = content.value,
                onValueChange = {
                    content.value = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlancoGris,
                    unfocusedBorderColor = BlancoGris,
                    focusedContainerColor = BlancoGris,
                    unfocusedContainerColor = BlancoGris
                ),
                label = { Text("Contenido") },
                modifier = Modifier
                    .padding(5.dp)
                    .padding(start = 10.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
                    .height(270.dp)
                //.offset(y = (140).dp)
                ,
                shape = RoundedCornerShape(10)
            )

            OutlinedTextField(
                value = image.value,
                onValueChange = {
                    image.value = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlancoGris,
                    unfocusedBorderColor = BlancoGris,
                    focusedContainerColor = BlancoGris,
                    unfocusedContainerColor = BlancoGris
                ),
                label = { Text("URL Imagen") },
                modifier = Modifier
                    .padding(5.dp)
                    .padding(start = 10.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
                    .height(65.dp)
                //.offset(y = (140).dp)
                ,
                shape = RoundedCornerShape(90)
            )

            // Botón de Registro
            Button(
                onClick = {
                    // Redirige a la página de registro (RegisterPage)
                    navController.navigate("HomePage")
                    orgViewModel.addPost(title.value, content.value, image.value)
                },
                colors = ButtonDefaults.buttonColors(RojoFrisa),
                modifier = Modifier
                    .padding(16.dp)
                    .width(200.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(100))
                    .background(color = Purple80)
            ) {
                Text(
                    "PUBLICAR",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            LaunchedEffect(showDelayedText) {
                if (showDelayedText.value) {
                    launch {
                        //navController.navigate("TagsPage")
                        delay(5000)
                        // Delay for 2 seconds (adjust as needed)
                        showDelayedText.value = false
                    }
                }
            }
            if (showDelayedText.value) {
                Text(text = "Post Exitoso")
                Text(text = "En 5 segundos serás redirigido a la pantalla de inicio.")
                navController.navigate("HomePage")
            }

        }
    }

}