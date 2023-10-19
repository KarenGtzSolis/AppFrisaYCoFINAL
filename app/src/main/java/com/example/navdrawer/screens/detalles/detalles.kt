package com.example.navdrawer.screens.detalles

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R
import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel
import com.example.navdrawer.model.PostsGetAll
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa

@Composable
fun detalles(postTitle: String = "", navController: NavHostController){
    Column(
        modifier = Modifier
            .background(GrisClaro)
            .fillMaxSize()
    ) {
        header(postTitle)
        titulo(postTitle)
        Contenido(postTitle)
    }
}


@Composable
fun header(postTitle: String = "") {
    Column {
        val pubViewModel = OrganizationViewModel(OrgService.instance)
        val publicaciones = remember {
            mutableStateOf(PostsGetAll())
        }

        val publicacion = publicaciones.value.find { it.title == postTitle }

        publicacion?.let {
            AsyncImage(
                model = publicacion.image,
                contentDescription = null,
            )
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

    }
}


@Composable
fun titulo(postTitle: String = ""){
    Column {
        val pubViewModel = OrganizationViewModel(OrgService.instance)
        val publicaciones = remember {
            mutableStateOf(PostsGetAll())
        }

        val publicacion = publicaciones.value.find { it.title == postTitle }

        publicacion?.let {
            Text(
                text = publicacion.title, modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(end = 20.dp)
                    .padding(top = 12.dp),
                style = TextStyle(
                    color = RojoFrisa,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                )
            )
            Text(
                text = "Fecha de publicación: " + publicacion.date, modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(end = 20.dp)
                    .padding(top = 12.dp),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                )
            )


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

    }

}

@Composable
fun Contenido(postTitle: String = ""){

    Column {
        val pubViewModel = OrganizationViewModel(OrgService.instance)
        val publicaciones = remember {
            mutableStateOf(PostsGetAll())
        }

        val publicacion = publicaciones.value.find { it.title == postTitle }

        publicacion?.let {
            LazyColumn() {
                item {
                    Text(
                        text = publicacion.content, modifier = Modifier
                            .padding(start = 20.dp)
                            .padding(end = 20.dp)
                            .padding(top = 12.dp),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Justify,
                            fontSize = 16.sp,
                        )
                    )
                }
            }
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
    }
}