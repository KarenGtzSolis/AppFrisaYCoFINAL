package com.example.navdrawer.screens.about


import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Organization
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R
import com.example.navdrawer.model.AddFavoriteOrganization
import com.example.navdrawer.model.AddFavoriteOrganizationResponse
import com.example.navdrawer.model.GetFavoriteOrganizationResponse
import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.service.UserService
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa
import com.example.navdrawer.viewModel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutPage(orgname: String = "", navController: NavHostController, appViewModel: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClaro)
    ) {
        LazyColumn() {
            item {
                header(orgname)
                acciones(orgname, appViewModel)
                descripcion(orgname)
                contacto(orgname)
                ubicacion(orgname)
                redes(orgname)
            }
        }
    }
}


@Composable
fun header(orgname: String = "") {
    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(OrganizationResponse()) //aqui va la clase tag list
        }

        val organization = Organizations.value.find { it.name == orgname }

        organization?.let {
            //IMAGEN DE LA ORGANIZACIÓN
            AsyncImage(
                model = organization.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(400.dp)
                    .height(200.dp)
                )

            // NOMBRE DE LA ORGANIZACIÓN
            Text(
                text = organization.name, modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 12.dp)
                    .padding(end = 20.dp),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 23.sp,
                )
            )
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
        }
    }
}

@Composable
fun acciones(orgname: String = "", appViewModel: AppViewModel) {
    Column() {

        val orgViewModel = OrganizationViewModel(OrgService.instance)


        val Organizations = remember {
            mutableStateOf(OrganizationResponse())
        }
        val organization = Organizations.value.find { it.name == orgname }

        val user= UserViewModel(UserService.instance)

        val addedFavResult = remember {
            mutableStateOf(AddFavoriteOrganizationResponse("Añadido a favoritos"))
        }

        val showDelayedText = remember { mutableStateOf(false)}

        LaunchedEffect(key1 = appViewModel) {
            user.addOrgFavoriteResult.collect { result ->
                if (result != null) {
                    addedFavResult.value = result
                    showDelayedText.value = true
                    //viewModel.setLoggedIn()
                    //showDelayedText.value = true
                }
                else{
                    showDelayedText.value=false
                }
            }
        }

        val Favorites = remember {
            mutableStateOf(AddFavoriteOrganization())
        }
        //val userFavs = Favorites.value.find { it.name == orgname }

        val favorite=remember{mutableStateOf("")}

        organization?.let {
            Row() {
                var mensaje by remember { mutableStateOf("Visita la organización www.org.com") }
                val userId=remember{mutableStateOf("")}
                val orgId= remember {
                    mutableStateOf("")
                }

                val intent = remember {
                    Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, mensaje)
                        type = "text/plain"
                    }
                }

                val shareLauncher: ActivityResultLauncher<Intent> =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult()
                    ) { _ ->
                        // Handle the result if needed
                    }

                Image(
                    painter = painterResource(id = R.drawable.compartir),
                    contentDescription = "Share",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            shareLauncher.launch(Intent.createChooser(intent, "Share to"))
                        },
                    colorFilter = ColorFilter.tint(RojoFrisa),

                    )

                var isFavorito by remember { mutableStateOf(false)}
                val user= UserViewModel(UserService.instance)

                IconToggleButton(
                    checked = isFavorito,
                    onCheckedChange = {
                        isFavorito = it

                        val userId = appViewModel.getToken()
                        val orgId = organization?._id ?: ""
                        Log.d("FavoriteDebug", "User ID: $userId, Org ID: $orgId")

                        user.addUserFavoriteOrganization(userId, orgId)
                        //user.addUserFavoriteOrganization("6526589f58e7e069b647bfd6",organization._id)
                    }
                ) {
                    if (isFavorito) {
                        // Agregar aqui codigo para actualizar BD correspondiente con el id del favorito.
                        // OrgViewModel.updateFavorites(id)
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorito",
                            tint = RojoFrisa,
                            modifier = Modifier.size(32.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "No Favorito",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
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
    }
}


@Composable
fun descripcion(orgname: String = "") {
    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(OrganizationResponse())
        }
        val organization = Organizations.value.find { it.name == orgname }

        organization?.let {
            // DESCRIPCIÓN
            Text(
                text = "Descripción",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 1.dp)
                    .padding(end = 20.dp)
            )

            // DESCRIPCIÓN DE LA ORGANIZACIÓN
            Text(
                text = organization.description, modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 3.dp)
                    .padding(end = 20.dp),

                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
        }
    }
}

@Composable
fun contacto(orgname: String = "") {
    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(OrganizationResponse())
        }
        val organization = Organizations.value.find { it.name == orgname }

        organization?.let {
            Text(
                text = "Contacto",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 12.dp)
            )

            Text(
                text = organization.email, modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 3.dp),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = organization.phone, modifier = Modifier
                    .padding(start = 20.dp),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = organization.linkWeb, modifier = Modifier
                    .padding(start = 20.dp),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ubicacion(orgname: String = "") {
    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(OrganizationResponse())
        }
        val organization = Organizations.value.find { it.name == orgname }

        organization?.let {
            Row(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loca),
                    contentDescription = "Ubicación",
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                        .padding(start = 20.dp)
                        .padding(top = 12.dp),
                    colorFilter = ColorFilter.tint(RojoFrisa)
                )

                FlowRow(
                    modifier = Modifier
                        .padding(top = 12.dp)

                ) {

                    Text(
                        text = organization.street + ", ",
                        modifier = Modifier
                            .padding(start = 10.dp),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Text(
                        text = organization.city + ", ", modifier = Modifier
                            .padding(start = 2.dp),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Text(
                        text = organization.suburb + ", ", modifier = Modifier
                            .padding(start = 2.dp),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Text(
                        text = " " + organization.state, modifier = Modifier
                            .padding(start = 2.dp),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
        }
    }
}

@Composable
fun redes(orgname: String = "") {
    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(OrganizationResponse())
        }
        val organization = Organizations.value.find { it.name == orgname }

        organization?.let {
            Text(
                text = "Redes Sociales",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 12.dp)
            )

            //BOTONES DE REDES SOCIALES
            Row(
                modifier = Modifier
                    .padding(start = 17.dp),

                ) {
                val insta = organization.linkInstagram
                val face = organization.linkFacebook
                val twitter = organization.linkTwitter
                val otro = organization.linkOther
                val context = LocalContext.current

                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = "Insta",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(5.dp)
                        .clickable {
                            val uri = Uri.parse(insta)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)


                        }, colorFilter = ColorFilter.tint(RojoFrisa)
                )

                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Face",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(5.dp)
                        .clickable {
                            val uri = Uri.parse(face)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)

                        }, colorFilter = ColorFilter.tint(RojoFrisa)
                )

                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "Twitter",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(5.dp)
                        .clickable {
                            val uri = Uri.parse(twitter)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)

                        }, colorFilter = ColorFilter.tint(RojoFrisa)
                )

                Image(
                    painter = painterResource(id = R.drawable.otro),
                    contentDescription = "Más",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clickable {
                            val uri = Uri.parse(otro)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)

                        },
                    colorFilter = ColorFilter.tint(RojoFrisa)
                )
            }
        }
        LaunchedEffect(key1 = orgViewModel.getOrgResult) {
            orgViewModel.getUserFavoriteOrganization()
            orgViewModel.getOrgResult.collect { result ->
                if (result != null) {
                    val organizationsResponse = OrganizationResponse()
                    organizationsResponse.addAll(result)
                    Organizations.value = organizationsResponse
                }
            }
        }
    }
}