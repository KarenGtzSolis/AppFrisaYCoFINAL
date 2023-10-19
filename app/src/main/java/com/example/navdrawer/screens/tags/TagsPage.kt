package com.example.navdrawer.screens.tags

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navdrawer.OrganizationViewModel.OrganizationViewModel
import com.example.navdrawer.R
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.model.TagListItem
import com.example.navdrawer.model.TagsList
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.ui.theme.GrisClaro
import com.example.navdrawer.ui.theme.RojoFrisa
import kotlin.random.Random

@Composable
fun TagsPage(navController: NavController) {



    val selectedTags = remember { mutableStateOf(mutableListOf<String>()) }
    val showDelayedText = remember { mutableStateOf(false) }
    val orgviewModel = OrganizationViewModel(OrgService.instance)
    val tagsResult = remember {
        mutableStateOf(TagsList())
    }

    val organizations = remember { mutableStateOf<List<OrganizationResponse>>(emptyList()) }

    fun searchOrganizationsByTags(tags: List<String>) {
        Log.d("LISTA", "*" + tags.toString())
        // Realiza la búsqueda de organizaciones enlazadas con los tags seleccionados
        orgviewModel.findbytags(tags)
    }

    LaunchedEffect(key1 = orgviewModel) {
        orgviewModel.findbytags.collect { result ->
            if (result != null) {
                tagsResult.value = result
            }
        }
    }

    Column(modifier = Modifier
        .background(GrisClaro)
        .fillMaxSize()) {

        header()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //verticalArrangement = Arrangement.Center
        ) {

            Log.d("Publicaciones", "$selectedTags")

            OrganizationViewModel(OrgService.instance).findbytags(selectedTags.value)

            if (showDelayedText.value) {
                Text(text = "Lista guardadda")
            }

            Text(
                text = "Selecciona tags de tu interes",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color(0xFF000000),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier


            )

            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(380.dp)
            ) {

                items(1) {
                    back(selectedTags = selectedTags.value)
                }
            }


            boton(navController) {
                // Cuando se hace clic en "Listo", realiza la búsqueda de organizaciones
                searchOrganizationsByTags(selectedTags.value)
            }

            LazyColumn {
                items(organizations.value) { organization ->
                    // Aquí debes definir cómo deseas mostrar la información de la organización.
                    // Puedes utilizar un composable personalizado, como "CardOrganizaciones" o "OrgRow".
                }
            }


        }



    }


}

@Composable
fun header(){
    Column (modifier = Modifier
        .fillMaxWidth()
        //.padding(bottom = 70.dp),
        ,horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(id = R.drawable.circulorojo),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                //.offset(x = (125).dp)
                .offset(y = -22.dp)
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun back(TagList: List<TagListItem> = emptyList(), selectedTags: MutableList<String>){



    Column() {
        val orgViewModel = OrganizationViewModel(OrgService.instance)
        val Organizations = remember {
            mutableStateOf(TagList) //aqui va la clase tag list
        }

        val TagList = remember {
            mutableStateOf(TagsList())
        }

        val organization = Organizations.value.find { it.tags == TagList}

        orgViewModel.getAllTags()

        organization?.let {

            Column {
                Text(
                    text = organization.tags.toString(),
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(bottom = 10.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color(0xFF000000),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start

                    )
                )
            }
        }



        LaunchedEffect(key1 = orgViewModel.getAllTags) {
            orgViewModel.getAllTags.collect { result ->
                if (result != null) {

                    val tagsResponse = TagsList()
                    tagsResponse.addAll(result)
                    TagList.value = tagsResponse
                    //Organizations.value = tagsResponse
                    // TagListItem.value.addAll(result)
                    //  Organizations.value = TagList

                }
            }
        }


        FlowRow {

            TagList.value.forEach() { tag ->

                // Text(text = it)
                TagItem(tag, selectedTags)

            }
        }



    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContentView() {


    val tagsPerRow = 5 // Número de etiquetas por fila
    val tagNames = listOf(
        "Autismo",
        "Niños",
        "Discapacidad",
        "Apoyo",
        "Educación",
        "Inclusión",
        "Terapia",
        "Sensibilización",
        "Desarrollo",
        "Comunidad",
        "Recursos",
        "Terapeutas",
        "Familias",
        "Terapias",
        "Aprendizaje",
        "Programas",
        "Apoyo emocional",
        "Comunicación",
        "Juegos",
        "Crianza",
        "Intervención",
        "Terapeutas ocupacionales",
        "Terapeutas del habla",
        "Terapeutas físicos",
        "Grupos de apoyo",
        "Servicios",
        "Terapias alternativas",
        "Tecnología asistencial",
        "Autocuidado",
        "Adaptación"
    )





    FlowRow(
        modifier = Modifier
            .height(430.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 0 until tagNames.size) {
            TagItem(tagNames.size.toString(), mutableListOf<String>())
            if ((i + 1) % tagsPerRow == 0 && i != tagNames.size - 1) {
                // Agregar un divisor entre filas, excepto en la última fila
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }


}

@Composable
fun TagItem(text: String, selectedTags: MutableList<String>){
    var tagColor by remember { mutableStateOf(Color(0xFFB1B1B1)) }
    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0XFFD81C30))
            .background(tagColor)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable {
                if (tagColor == Color(0xFFB1B1B1)) {
                    tagColor = Color(0xFFE7182E)
                    selectedTags.add(text) // Agrega el tag seleccionado a la lista
                } else {
                    tagColor = Color(0xFFB1B1B1)
                    selectedTags.remove(text) // Elimina el tag deseleccionado de la lista
                }
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }


}

private fun generateRandomStrings(): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val randomLength = Random.nextInt(5,15)

    return(1..randomLength)
        .map{
            charPool[Random.nextInt(0, charPool.size)]
        }
        .joinToString("")
}



@Composable
fun boton(navController: NavController,onListoClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {

                onListoClick()
                navController.navigate("HomePage")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(40.dp)
                .width(100.dp),
            colors = ButtonDefaults.buttonColors(RojoFrisa),
        ) {
            Text(
                "Listo",
                fontWeight = FontWeight.ExtraBold,

                )
        }
    }
}