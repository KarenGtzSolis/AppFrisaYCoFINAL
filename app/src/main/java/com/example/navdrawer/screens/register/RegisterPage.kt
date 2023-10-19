package com.example.navdrawer.screens.register

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.UserService
import com.example.navdrawer.ui.theme.BlancoGris
import com.example.navdrawer.ui.theme.GrisClaro

import com.example.navdrawer.ui.theme.RojoFrisa
import com.example.navdrawer.viewModel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterPage(navController: NavController,
                 viewModel: AppViewModel) {
    val userviewModel = UserViewModel(UserService.instance)
    val showDelayedText = remember { mutableStateOf(false) }

    /*
    val phone= remember {
        mutableStateOf("")
    }

    val password= remember {
        mutableStateOf("")
    }

    val validatepassword=remember{
        mutableStateOf("")
    }

     */

    val registrationResult = remember {
        mutableStateOf(UserRegistrationResponse())
    }

    LaunchedEffect(key1 = viewModel) {
        userviewModel.registrationResult.collect { result ->
            if (result != null) {
                registrationResult.value = result
                viewModel.setLoggedIn()
                showDelayedText.value = true
            }
        }
    }

    if (!viewModel.isUserLoggedIn()) {

        Column(
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
                text = "Registro",
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
            val lastname = remember { mutableStateOf("") }
            val phone = remember { mutableStateOf("") }
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val tags = remember { mutableStateOf("") }
            val favorites = remember { mutableStateOf("") }
            val description = remember { mutableStateOf("") }

            OutlinedTextField(
                value = name.value,
                onValueChange = { newValue ->
                    name.value = newValue
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
                value = lastname.value,
                onValueChange = {
                    lastname.value = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlancoGris,
                    unfocusedBorderColor = BlancoGris,
                    focusedContainerColor = BlancoGris,
                    unfocusedContainerColor = BlancoGris
                ),
                label = { Text("Apellido") },
                modifier = Modifier
                    .padding(5.dp)
                    .width(330.dp)
                    .height(59.dp)
                //.offset(y = (140).dp)
                ,
                shape = RoundedCornerShape(90)
            )

            OutlinedTextField(
                value = phone.value, onValueChange = {
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

            /*
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

         */

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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                label = { Text("Contraseña") },
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
                label = { Text("Descripción de Necesidad") },
                modifier = Modifier
                    .padding(5.dp)
                    .width(330.dp)
                    .height(150.dp)
                //.offset(y = (140).dp)
                ,
                shape = RoundedCornerShape(20)
            )

            // Botón de Registro
            Button(
                onClick = {
                    viewModel.setLoggedIn()
                    navController.navigate("TagsPage")
                    userviewModel.addUser(
                        name.value,
                        lastname.value,
                        phone.value.trim(),
                        password.value,
                        description.value
                    )
                },
                colors = ButtonDefaults.buttonColors(RojoFrisa),
                modifier = Modifier
                    .padding(16.dp)
                    .width(160.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(100))
                    .background(color = BlancoGris)
            ) {
                Text(
                    "REGISTRARSE",
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
                Text(text = "Registro Exitoso")
                Text(text = "En 5 segundos serás redirigido a la pantalla de inicio.")
                navController.navigate("TagsPage")
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
}