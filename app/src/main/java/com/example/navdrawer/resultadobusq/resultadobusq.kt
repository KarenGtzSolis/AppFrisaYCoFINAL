package com.example.navdrawer.screens.resultadobusq


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.BlancoGris
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa

@Composable
fun ResultadoBusqueda(navController: NavController, viewModel: AppViewModel) {






    Column(modifier = Modifier
        .background(GrisClaro)
        .fillMaxSize()) {
    }
    header()
    contenidoBusqueda(navController = navController, viewModel = viewModel)
}


@Composable
fun header(){
    Text(
        text = "Resultados para: Tag",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
        modifier = Modifier
            .padding(20.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrgColumn(
    orgname: String,
    url: String,
    onItemClick: (String) -> Unit = {}
) {
    FlowRow {
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
                        .fillMaxWidth()
                        .background(BlancoGris)
                ) {

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
                }
            }
        }
    }
}

@Composable
fun contenidoBusqueda(navController: NavController, viewModel: AppViewModel) {
    val orgViewModel = OrganizationViewModel(OrgService.instance)


    val loggedIn = remember {
        mutableStateOf(viewModel.isUserLoggedIn())
    }

    val Organizations = remember {
        mutableStateOf(OrganizationResponse())
    }
    val selectedTags = remember { mutableStateOf(mutableListOf<String>()) }


    LaunchedEffect(selectedTags.value) {
        orgViewModel.findbytags(selectedTags.value) // Realiza la búsqueda cuando selectedTags cambia

        // Observa los resultados de la búsqueda y actualiza Organizations
        orgViewModel.getOrgResult.collect { result ->
            if (result != null) {
                Organizations.value = result
                Log.d("Organizaciones", "Organizaciones actualizadas: ${Organizations.value.size} organizaciones")

            }
        }
    }

    /*if (Organizations.value.isNotEmpty()) {
        LazyColumn {
            items(Organizations.value) { organization ->

                Text(organization.name)

            }
        }
    } else {

        Text("No se encontraron organizaciones.")
    }
    */

    LazyColumn(
        modifier = Modifier.padding(end = 12.dp),
        content = {
            items(items = Organizations.value) { organization ->
                CardOrganizaciones(
                    postTitle = organization.name, // Supongo que el nombre de la organización es relevante aquí
                    // Asegúrate de proporcionar la imagen de la organización de acuerdo a tu modelo de datos
                    organization.image
                ) { postTitle ->
                    Log.d("Organizaciones", "$postTitle")
                    navController.navigate("detalles/$postTitle")
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )


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