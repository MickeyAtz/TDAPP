package com.mky.tdapp.ui.screens

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mky.tdapp.data.remote.MateriaApi
import com.mky.tdapp.data.remote.RetrofitInstance
import com.mky.tdapp.data.repository.MateriaRepository
import com.mky.tdapp.ui.components.AppButton

import com.mky.tdapp.ui.components.AppItemRow
import com.mky.tdapp.ui.components.AppTextField
import com.mky.tdapp.ui.components.ScreenHeader
import com.mky.tdapp.ui.viewmodel.MateriaViewModel

@Preview(showBackground = true)
@Composable
fun MateriasScreen() {

    val context = LocalContext.current

    val api = remember { RetrofitInstance.retrofit.create(MateriaApi::class.java) }
    val repository = remember { MateriaRepository(api) }
    val viewModel = remember { MateriaViewModel(repository) }

    LaunchedEffect(Unit) {
        viewModel.loadMaterias()
    }
    // Estados del modal
    var showDialog by remember { mutableStateOf(false) }
    var nombreMateria by remember { mutableStateOf("") }
    var materiaEditId by remember {mutableStateOf<Int?>(null)}



    // Contenido principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        //Estado de carga
        if(viewModel.isLoading){
            CircularProgressIndicator()
        }

        viewModel.error?.let {
            Text(
                text = "Error $it",
                color = MaterialTheme.colorScheme.error
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            ScreenHeader(
                title = "Materias",
                onButtonClick = {showDialog = true}
            )

            // Lista
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.materias, key = { it.id }) { materia ->
                    AppItemRow(
                        title = materia.nombre,
                        onEdit = {
                            nombreMateria = materia.nombre
                            materiaEditId = materia.id
                            showDialog = true
                        },
                        onDelete = {
                            viewModel.deleteMateria(materia.id)
                        }
                    )
                }
            }
        }



        if(showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    nombreMateria =  ""
                },
                title = {
                    Text("Nueva materia")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = nombreMateria,
                            onValueChange = {nombreMateria = it},
                            label = {Text("Nombre")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if(nombreMateria.isBlank()) return@TextButton

                            if(materiaEditId == null) {
                                viewModel.createMateria(nombreMateria) {
                                    Toast
                                        .makeText(context, "Materia creada", Toast.LENGTH_SHORT)
                                        .show()
                                    showDialog = false
                                    nombreMateria = ""
                                }
                            } else {
                                viewModel.updateMateria(
                                    id = materiaEditId!!,
                                    nombre = nombreMateria
                                ) {
                                    Toast
                                        .makeText(context, "Materia actualizada", Toast.LENGTH_SHORT)
                                        .show()
                                    showDialog = false
                                    nombreMateria = ""
                                    materiaEditId = null
                                }
                            }


                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            nombreMateria = ""
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}