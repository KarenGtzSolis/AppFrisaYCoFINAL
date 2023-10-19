package com.example.navdrawer.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp



import coil.compose.AsyncImage


import com.example.navdrawer.model.GetAllFavorites

import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel

import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.model.PostsGetAll
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.BlancoGris
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa


@Composable
fun FavsPage(navController: NavController, appviewModel: AppViewModel) {

    Column(
        modifier = Modifier
            .background(GrisClaro)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        headerFavs()
        orgsFavs(navController, appviewModel)
    }
}

@Composable
fun headerFavs(){
    Column {
        Text(
            text = "Organizaciones Favoritas",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun orgsFavs(navController: NavController, appviewModel: AppViewModel){
    val favsViewModel = OrganizationViewModel(OrgService.instance)
    val favorites = remember {
        mutableStateOf(GetAllFavorites())
    }

    LaunchedEffect(key1 = favsViewModel.getAllFavorites) {
        favsViewModel.getAllFavorites(appviewModel.getToken())
        favsViewModel.getAllFavorites.collect { result ->
            if (result != null) {
                val favoritesResponse = GetAllFavorites()
                favoritesResponse.addAll(result)
                favorites.value = favoritesResponse
            }
        }
    }

    Log.d("Favoritos", "Hola")
    for (favorite in favorites.value) {
        Log.d("Favoritos", favorite.name)
        Log.d("Favoritos", favorite.email)
    }

    val loggedIn = remember {
        mutableStateOf(appviewModel.isUserLoggedIn())
    }

    Column(modifier = Modifier.padding(top = 12.dp).padding(end = 12.dp).padding(start = 12.dp)) {
        LazyColumn {
            items(items = favorites.value) {
                FavRow(orgname = it.name, it.image) { orgname ->
                    Log.d("Organizaciones", "$orgname")
                    //navController.navigate("movieDetail/$movie") // Navega a la pantalla de detalles con el nombre de la película
                    navController.navigate("AboutPage/" + orgname)
                }
            }
        }
    }

}

@Composable
fun FavRow(
    orgname: String,
    image: String,
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(287.dp)
            .height(250.dp)
            .offset(x = (3.dp))
            .clickable { }
            .fillMaxWidth()
            .clickable {
                onItemClick(orgname)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlancoGris)
        ) {

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .width(287.dp) // Adjust the height as needed
            ) {
                AsyncImage(model = image, contentDescription = null, modifier = Modifier.fillMaxWidth())
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    tint = RojoFrisa, // Change the color as needed
                    modifier = Modifier
                        .size(40.dp) // Adjust the size as needed
                        .align(Alignment.TopEnd)
                        .padding(end = 10.dp)
                        .padding(top = 10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Text(text = orgname,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = RojoFrisa
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}
