package com.example.navdrawer.screens.seguridad

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navdrawer.R
import com.example.navdrawer.ui.theme.GrisClaro

@Composable
fun SecurityPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClaro),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        header()
    }
    aviso()
}

@Composable
fun header(){
    Column {
        Text(
            text = "Aviso de privacidad",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black
            ),
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.linea_roja),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = ((-90).dp))
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun aviso(){
    Column {
        LazyColumn{
            item {
                Text(
                    text = "Esta aplicación ha sido desarrollada por un equipo de estudiantes del Tecnológico de Monterrey en colaboración con la organización socio formadora “FRISA” como parte de un proyecto académico. Si tienes alguna pregunta o inquietud sobre la privacidad de tus datos, no dudes en contactarnos a través del siguiente correo electrónico: a00833271@tec.mx.\n" +
                            "La aplicación recopila información de los usuarios, incluyendo nombre, apellido, número de teléfono, correo y contraseña. Asimismo, al registrarse, las organizaciones proporcionan datos como nombre, correo, número de teléfono, dirección, horario de atención, enlaces a páginas web y redes sociales, descripción de la organización, imágenes y palabras clave descriptivas.\n" +
                            "Los datos de los usuarios se recopilan con el fin de verificar la autenticidad de las cuentas durante el proceso de inicio de sesión. Por otro lado, la información de las organizaciones se recopila para mejorar la visibilidad de las mismas y así enriquecer la experiencia del usuario.\n" +
                            "Queremos asegurarte que en ningún momento compartimos los datos de los usuarios con terceros. La información de las organizaciones solo se muestra dentro de la aplicación y no se comparte con entidades externas.\n" +
                            "Todos los datos recopilados se almacenan de manera segura y confidencial en una base de datos protegida. Implementamos medidas de encriptación para proteger la confidencialidad de información sensible, como las contraseñas.\n" +
                            "Respetamos tus derechos en relación con tus datos personales. Tienes el derecho de acceder, rectificar, cancelar u oponerte al uso de tus datos. Además, en caso de cualquier preocupación relacionada con tus datos personales, tienes el derecho de presentar una queja ante la autoridad de protección de datos.\n" +
                            "En caso de realizar modificaciones en nuestra política de privacidad, te informaremos a través de la propia aplicación la próxima vez que intentes iniciar sesión.\n" +
                            "Si necesitas asistencia o tienes alguna pregunta sobre tus datos personales, no dudes en contactar a nuestro equipo de soporte mediante el siguiente correo electrónico: a00833271@tec.mx.",
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 65.dp),
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }
    }
}