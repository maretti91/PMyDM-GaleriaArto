
package com.example.galeriaarte


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.galeriaarte.ui.theme.GaleriaArteTheme

//CLASE QUE REPRESENTA UNA OBRA DE ARTE
data class ObraArte(
    val imagen: Int,     // Identificador de imagen en drawable.
    val titulo: String,  // Título de la obra.
    val autor: String,   // Nombre del artista.
    val anio: String     // Año de creación.
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Aplica los colores y estilos de nuestra aplicación.
            GaleriaArteTheme {
                // Scaffold proporciona la estructura de pantalla.
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // innerPadding evita solapamientos con las
                    // barras del sistema.
                    GaleriaArte(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


//FUNCIÓN PRINCIPAL DE LA GALERÍA
@Composable
fun GaleriaArte(modifier: Modifier = Modifier) {
    // Creamos una lista con nuestras tres obras.
    // Cada elemento contiene imagen, título, autor y año.
    val obras = listOf(
        ObraArte(
            imagen = R.drawable.noche_estrellada,
            titulo = "La noche estrellada",
            autor = "Vincent van Gogh",
            anio = "1889"
        ),

        ObraArte(
            imagen = R.drawable.gioconda,
            titulo = "La Gioconda",
            autor = "Leonardo da Vinci",
            anio = "c. 1503-1519"
        ),

        ObraArte(
            imagen = R.drawable.joven_perla,
            titulo = "La joven de la perla",
            autor = "Johannes Vermeer",
            anio = "c. 1665"
        )
    )

    // Variable que guarda la posición de la obra actual.
    // rememberSaveable conserva el índice ante determinadas
    // mutableIntStateOf crea un estado observable.
    // Comienza en 0 porque las listas empiezan en índice 0.
    var obraActual by rememberSaveable {
        mutableIntStateOf(0)
    }

    // Obtenemos la obra que corresponde al índice actual.
    // Cuando obraActual cambia, Compose actualiza la interfaz.
    val obra = obras[obraActual]

    // Column organiza los elementos verticalmente.
    Column(
        modifier = modifier
            .fillMaxSize()   // Ocupa el espacio disponible.
            .padding(16.dp),

        // Centra los elementos horizontalmente.
        horizontalAlignment = Alignment.CenterHorizontally,

        // Centra el contenido verticalmente.
        verticalArrangement = Arrangement.Center
    ) {


        // TÍTULO PRINCIPAL DE LA APLICACIÓN
        Text(
            text = "Galería de Arte",

            // Estilo de encabezado de Material Design.
            style = MaterialTheme.typography.headlineMedium
        )

        // Separación vertical
        Spacer(modifier = Modifier.height(24.dp))


        // IMAGEN DE LA OBRA SELECCIONADA
        Image(
            // Carga la imagen de la obra actual.
            painter = painterResource(id = obra.imagen),

            // Descripción para herramientas de accesibilidad.
            contentDescription = obra.titulo,

            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho.
                .height(350.dp), // Altura

            // Ajusta la imagen sin deformarla ni recortarla.
            contentScale = ContentScale.Fit
        )

        // Separación entre imagen e información.
        Spacer(modifier = Modifier.height(24.dp))


        // INFORMACIÓN DE LA OBRA
        // Muestra el título de la obra seleccionada.
        Text(
            text = obra.titulo,
            style = MaterialTheme.typography.titleLarge
        )

        // Separación entre título y autor.
        Spacer(modifier = Modifier.height(8.dp))

        // Muestra el artista y el año.
        Text(
            text = "${obra.autor} (${obra.anio})",
            style = MaterialTheme.typography.bodyMedium
        )

        // Separación antes de los botones.
        Spacer(modifier = Modifier.height(32.dp))


        // BOTONES DE NAVEGACIÓN
        // Row coloca los botones horizontalmente.
        Row(
            modifier = Modifier.fillMaxWidth(),

            // Distribuye los botones hacia los extremos.
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // BOTÓN ANTERIOR
            Button(
                onClick = {

                    // Si estamos después de la primera obra, retrocedemos una posición.
                    if (obraActual > 0) {
                        obraActual--
                    } else {
                        // Si estamos en la primera obra, saltamos a la última.
                        obraActual = obras.size - 1
                    }
                }
            ) {
                Text(text = "Anterior")
            }

            // BOTÓN SIGUIENTE
            Button(
                onClick = {

                    // Si no estamos en la última obra, avanzamos una posición.
                    if (obraActual < obras.size - 1) {
                        obraActual++
                    } else {

                        // Si estamos en la última obra, volvemos a la primera.
                        obraActual = 0
                    }
                }
            ) {
                Text(text = "Siguiente")
            }
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GaleriaArtePreview() {
    GaleriaArteTheme {
        GaleriaArte()
    }
}
