package com.mky.tdapp.ui.screens

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mky.tdapp.data.remote.GrupoApi
import com.mky.tdapp.data.remote.MateriaApi
import com.mky.tdapp.data.remote.RetrofitInstance
import com.mky.tdapp.data.repository.GrupoRepository
import com.mky.tdapp.data.repository.MateriaRepository

import com.mky.tdapp.ui.components.AppItemRow
import com.mky.tdapp.ui.components.ScreenHeader
import com.mky.tdapp.ui.viewmodel.GrupoViewModel
import com.mky.tdapp.ui.viewmodel.MateriaViewModel

@Preview(showBackground = true)
@Composable
fun GruposScreen() {

    val context = LocalContext.current

    val api = remember { RetrofitInstance.retrofit.create(GrupoApi::class.java) }
    val repository = remember { GrupoRepository(api) }
    val viewModel = remember { GrupoViewModel(repository) }

    LaunchedEffect(Unit) {
        viewModel.loadGrupos()
    }
    // Estados del modal
    var showDialog by remember { mutableStateOf(false) }
    var nombreGrupo by remember { mutableStateOf("") }
    var cicloEscolarGrupo by remember { mutableStateOf("") }
    var grupoEditedID by remember {mutableStateOf<String?>(null)}



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
                title = "Grupos",
                onButtonClick = {showDialog = true}
            )

            // Lista
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.grupos, key = { it.id }) { grupo ->
                    AppItemRow(
                        title = grupo.nombre,
                        subtitle = grupo.ciclo_escolar,
                        onEdit = {
                            nombreGrupo = grupo.nombre
                            cicloEscolarGrupo = grupo.ciclo_escolar
                            grupoEditedID = grupo.id
                            showDialog = true
                        },
                        onDelete = {
                            viewModel.deleteGrupo(grupo.id)
                        }
                    )
                }
            }
        }



        if(showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    nombreGrupo =  ""
                },
                title = {
                    Text("Nueva materia")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = nombreGrupo,
                            onValueChange = {nombreGrupo = it},
                            label = {Text("Nombre")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(16.dp))

                        OutlinedTextField(
                            value = cicloEscolarGrupo,
                            onValueChange = {cicloEscolarGrupo = it},
                            label = {Text("Ciclo Escolar")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if(nombreGrupo.isBlank() || cicloEscolarGrupo.isBlank()) return@TextButton

                            if(grupoEditedID == null) {
                                viewModel.createGrupo(nombreGrupo, cicloEscolarGrupo) {
                                    Toast
                                        .makeText(context, "Grupo creado", Toast.LENGTH_SHORT)
                                        .show()
                                    showDialog = false
                                    nombreGrupo = ""
                                    cicloEscolarGrupo = ""
                                }
                            } else {
                                viewModel.updateGrupo(
                                    id = grupoEditedID!!,
                                    nombre = nombreGrupo,
                                    cicloEscolar = cicloEscolarGrupo
                                ) {
                                    Toast
                                        .makeText(context, "Grupo actualizado", Toast.LENGTH_SHORT)
                                        .show()
                                    showDialog = false
                                    nombreGrupo = ""
                                    grupoEditedID = null
                                    cicloEscolarGrupo = ""
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
                            nombreGrupo = ""
                            cicloEscolarGrupo = ""
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}