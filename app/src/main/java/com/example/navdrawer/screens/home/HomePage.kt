package com.example.navdrawer.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController

import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.BlancoGris
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa
import coil.compose.AsyncImage
import com.example.navdrawer.model.PostsGetAll


@Composable
fun HomePage(navController: NavController, appviewModel: AppViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClaro)
    ) {
        header(appviewModel)
    }

    /*if(appViewModel.isOrgLoggedIn()){
        BotonPost(navController = navController).setVisibility(View.VISIBLE)
    }*/
    contenidoOrgs(navController, appviewModel)
    BotonPost(navController, appviewModel)
}

@Composable
fun BotonPost(navController: NavController, appViewModel: AppViewModel) {

    val icon = Icons.Default.Add

    if (appViewModel.isOrgLoggedIn()) {

        Button(
            onClick = {
                navController.navigate("PostsPage")
            },
            shape = CircleShape, // Set the shape to make it circular
            contentPadding = PaddingValues(16.dp),// Optional padding to make it larger
            modifier = Modifier
                .offset(y = (15.dp))
                .offset(x = (280.dp)),
            colors = ButtonDefaults.buttonColors(RojoFrisa)

        ) {
            Icon(
                imageVector = icon,
                contentDescription = null, // You can provide a content description if needed
                tint = Color.White // Optional: You can set the icon color
            )
        }
    } else
        Button(
            onClick = {
                navController.navigate("PostsPage")
            },
            shape = CircleShape, // Set the shape to make it circular
            contentPadding = PaddingValues(16.dp),// Optional padding to make it larger
            modifier = Modifier
                .offset(y = (15.dp))
                .offset(x = (280.dp)),
            colors = ButtonDefaults.buttonColors(Color.Transparent)

        ) {
            Icon(
                imageVector = icon,
                contentDescription = null, // You can provide a content description if needed
                tint = Color.Transparent // Optional: You can set the icon color
            )
        }
}

@Composable
fun contenidoOrgs(navController: NavController, viewModel: AppViewModel){
    val orgViewModel = OrganizationViewModel(OrgService.instance)
    val loggedIn = remember {
        mutableStateOf(viewModel.isUserLoggedIn())
    }

    val Organizations = remember {
        mutableStateOf(OrganizationResponse())
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

    val posttitulo: List<String> = listOf(
        "¿Qué es autismo?",
        "¿Necesitas ayuda?"
    )

    Column(modifier = Modifier
        .padding(start = 12.dp)
        .padding(top = 12.dp)) {
        LazyRow(
            modifier = Modifier
                .padding(top = 100.dp),
            content = {
                items(items = Organizations.value) {
                    OrgRow(orgname = it.name, it.image) { orgname ->
                        Log.d("Organizaciones", "$orgname")
                        navController.navigate("AboutPage/" + orgname)
                    }
                }
            },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )

        Column (modifier = Modifier.padding(end = 12.dp)){
            Text(
                text = "Publicaciones",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        val pubViewModel = OrganizationViewModel(OrgService.instance)
        val publicaciones = remember {
            mutableStateOf(PostsGetAll())
        }

        LaunchedEffect(key1 = pubViewModel.getAllPosts) {
            pubViewModel.getAllPosts()
            pubViewModel.getAllPosts.collect { result ->
                if (result != null) {
                    val organizationsResponse = PostsGetAll()
                    organizationsResponse.addAll(result)
                    publicaciones.value = organizationsResponse
                }
            }
        }

        Log.d("ContenidoOrgs", "Publicaciones:")
        for (post in publicaciones.value) {
            Log.d("ContenidoOrgs", "Título: ${post.title}")
            Log.d("ContenidoOrgs", "Imagen: ${post.image}")
            Log.d("ContenidoOrgs", "Contenido: ${post.content}")
        }

        LazyColumn(
            modifier = Modifier
                .padding(end = 12.dp),
            content = {
                items(items = publicaciones.value) {
                    CardOrganizaciones(postTitle = it.title,  it.image) { postTitle ->
                        Log.d("Publicaciones", "$postTitle")
                        navController.navigate("detalles/" + postTitle)
                    }
                }
            },
            verticalArrangement = Arrangement.spacedBy(10.dp)
        )
    }
}


@Composable
fun OrgRow(
    orgname: String,
    url: String,
    onItemClick: (String) -> Unit = {}
) {
    val navController = rememberNavController()

    Row {
        Card(
            modifier = Modifier
                .padding(1.dp)
                .width(287.dp) // Reduce the width
                .height(250.dp) // Reduce the height
                .clickable {
                    onItemClick(orgname)
                },
            colors = CardDefaults.cardColors(containerColor = BlancoGris),
            shape = RoundedCornerShape(20.dp),
        ) {

            Column(
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        //height(200.dp) // Adjust the height as needed
                        .width(287.dp)
                        .background(BlancoGris)
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    Text(
                        text = orgname,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = RojoFrisa
                        ),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(bottom = 0.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    }
                }
            }
        }
    }
}

@Composable
fun CardOrganizaciones(
    postTitle:String,
    image: String,
    onItemClick:(String) -> Unit = {}
) { //Se agrego navController
    Card(
        modifier = Modifier
            .padding(bottom = 15.dp)
            .height(230.dp) // Reduce the height
            .fillMaxWidth()
            .clickable {
                onItemClick(postTitle)
                //navController.navigate("detalles")
            },


        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlancoGris)

    ) {
        Column(
            verticalArrangement = Arrangement.Center, // Alineación vertical al centro
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(model = image, contentDescription = null)
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Publicación", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Red ))

                Text(postTitle, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                    modifier = Modifier)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun header(appViewModel: AppViewModel){
    Column(
        //modifier = Modifier.background(GrisClaro)
    ) {
        Image(
            painter = painterResource(id = R.drawable.orillainicio),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(160.dp)
                .offset(y = (-55.dp))
                .offset(x = (-80).dp)
        )
        FlowRow {
            Text(
                text = "     ¡Hola, ",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .offset(y = -160.dp),
            )
            if (appViewModel.isUserLoggedIn()) {
                Text(
                    text = appViewModel.getUsername(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .offset(y = -160.dp),
                )
            } else
                if(appViewModel.isOrgLoggedIn())
                Text(
                    text = "OSC",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .offset(y = -160.dp),
                )
                else
                    Text(
                        text = "Usuario",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .offset(y = -160.dp),
                    )


            Text(
                text = "!",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .offset(y = -160.dp),
            )
        }
        Text(text = "Organizaciones", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), color = Color.Black,
            modifier = Modifier
                .padding(20.dp)
                .offset(y = -140.dp)
        )
    }
}

@Composable
fun CardOrganizaciones(
    postTitle:String,
    onItemClick:(String) -> Unit = {}
) { //Se agrego navController
    Card(
        modifier = Modifier
            .padding(bottom = 15.dp)
            .height(230.dp) // Reduce the height
            .fillMaxWidth()
            .clickable {
                onItemClick(postTitle)
                //navController.navigate("detalles")
            },


        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlancoGris)

    ) {
        Column(
            verticalArrangement = Arrangement.Center, // Alineación vertical al centro
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.imagenorg
                ),
                contentDescription = "Imagen de la organización",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Artículo", style = TextStyle( fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red )
                )

                Text(postTitle, style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                    modifier = Modifier

                )
            }
        }
    }
}
