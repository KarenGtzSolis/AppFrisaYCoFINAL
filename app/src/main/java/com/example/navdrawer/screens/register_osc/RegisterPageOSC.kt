package com.example.navdrawer.screens.register_osc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navdrawer.R
import com.example.navdrawer.model.OrgRegisterResponse
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
fun RegisterPageOSC(navController: NavController) {
    val orgViewModel = OrgViewModel(OrgService.instance)
    val showDelayedText=remember{mutableStateOf(false)}

    val orgRegisterResult = remember {
        mutableStateOf(OrgRegisterResponse())
    }

    LaunchedEffect(key1= orgViewModel) {
        orgViewModel.orgRegisterResult.collect { result ->
            if (result != null) {
                orgRegisterResult.value = result

                showDelayedText.value=true
            }
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrisClaro)
    ) {
        Image(
            painter = painterResource(id = R.drawable.orilla1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(160.dp)
                .offset(y = (0.dp))
                .offset(x = ((-120).dp))
        )

        // Texto de Registro
        Text(
            text = "Registro OSC",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = ((-100).dp))
        )

        // Imagen de línea roja
        Image(
            painter = painterResource(id = R.drawable.linea_roja),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = ((-190).dp))
        )
    }

    FlowRow(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 130.dp)
            .fillMaxSize()
            .background(color = GrisClaro),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        // Text Input y otros elementos
        val name = remember { mutableStateOf("") }
        val phone = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val street = remember { mutableStateOf("") }
        val suburb = remember { mutableStateOf("") }
        val city = remember { mutableStateOf("") }
        val state = remember { mutableStateOf("") }
        val schedule = remember { mutableStateOf("") }
        val linkWeb = remember { mutableStateOf("") }
        val linkFacebook = remember { mutableStateOf("") }
        val linkInstagram = remember { mutableStateOf("") }
        val linkTwitter= remember { mutableStateOf("") }
        val linkOther = remember { mutableStateOf("") }
        val description = remember { mutableStateOf("") }
        val image = remember { mutableStateOf("") }
        val tags = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Nombre") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (130).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                phone.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Teléfono") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Correo") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        Text(text = "Dirección",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 5.dp)
        )


        OutlinedTextField(
            value = street.value,
            onValueChange = {
                street.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Calle y Núm. Exterior") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = suburb.value,
            onValueChange = {
                suburb.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Colonia") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = city.value,
            onValueChange = {
                city.value = it
                            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Ciudad") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value =state.value,
            onValueChange = {
                state.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Estado") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        Text(text = "Horario de Atención",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 5.dp)
        )

        OutlinedTextField(
            value = schedule.value,
            onValueChange = {
                schedule.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Ingresa los horarios de atención") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(100.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(20)
        )

        Text(text = "Ligas Externas",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 5.dp)
        )

        OutlinedTextField(
            value = linkWeb.value,
            onValueChange = {
                linkWeb.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Página Web") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = linkFacebook.value,
            onValueChange = {
                linkFacebook.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Facebook") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = linkInstagram.value,
            onValueChange = {
                linkInstagram.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Instagram") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )
        OutlinedTextField(
            value = linkTwitter.value,
            onValueChange = {
                linkTwitter.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Twitter") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = linkOther.value,
            onValueChange = {
                linkOther.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Otro") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = description.value,
            onValueChange = {
                description.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Descripción de osc") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
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
            label = { Text("URL de imagen") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = tags.value,
            onValueChange = {
                tags.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Tags") },
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlancoGris,
                unfocusedBorderColor = BlancoGris,
                focusedContainerColor = BlancoGris,
                unfocusedContainerColor = BlancoGris
            ),
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(5.dp)
                .width(330.dp)
                .height(59.dp)
            //.offset(y = (140).dp)
            ,
            shape = RoundedCornerShape(90)
        )


        // Botón de Registro
        Button(
            onClick = {
                // Redirige a la página de registro (RegisterPage)
                //navController.navigate("TagsPage")
                orgViewModel.addOrg(name.value, phone.value.trim(), email.value,
                    street.value, suburb.value, city.value,state.value, schedule.value,
                    linkWeb.value, linkFacebook.value, linkInstagram.value,linkTwitter.value,
                    linkOther.value, description.value, image.value,
                    listOf(tags.value), password.value)
            },
            colors = ButtonDefaults.buttonColors(RojoFrisa),
            modifier = Modifier
                .padding(16.dp)
                .width(160.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(100))
                .background(color = Purple80)
        ) {
            Text("REGISTRARSE",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp)
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
            Text(text = "Registro Exitoso")
            Text(text = "En 5 segundos serás redirigido a la pantalla de inicio.")
            navController.navigate("HomePage")
        }

        // Imagen de orilla2
        /*
        Image(
            painter = painterResource(id = R.drawable.orilla2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(190.dp)
        ) */
    }
}
