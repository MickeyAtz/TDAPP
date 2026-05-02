package com.mky.tdapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mky.tdapp.data.model.Grupo
import com.mky.tdapp.data.repository.GrupoRepository
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class GrupoViewModel(
    private val repository: GrupoRepository
) : ViewModel() {
    var grupos by mutableStateOf<List<Grupo>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadGrupos() {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                grupos = repository.getGrupos()
            } catch (e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun createGrupo(
        nombre: String,
        cicloEscolar: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                repository.createGrupo(nombre, cicloEscolar)

                loadGrupos()

                onSuccess()
            } catch(e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun updateGrupo(id: String, nombre: String, cicloEscolar:String , onSuccess: () -> Unit) {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                repository.updateGrupo(id, nombre, cicloEscolar)
                loadGrupos()
                onSuccess()
            } catch(e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun deleteGrupo(id:String) {
        viewModelScope.launch{

            try {
                repository.deleteGrupo(id)
                loadGrupos()
            } catch (e:Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

}