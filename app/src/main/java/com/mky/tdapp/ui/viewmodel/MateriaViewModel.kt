package com.mky.tdapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mky.tdapp.data.model.Materia
import com.mky.tdapp.data.repository.MateriaRepository
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MateriaViewModel(
    private val repository: MateriaRepository
) : ViewModel() {
    var materias by mutableStateOf<List<Materia>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadMaterias() {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                materias = repository.getMaterias()
            } catch (e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun createMateria(
        nombre: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                repository.createMateria(nombre)

                loadMaterias()

                onSuccess()
            } catch(e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun updateMateria(id: Int, nombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch{
            isLoading = true
            error = null

            try {
                repository.updateMateria(id, nombre)
                loadMaterias()
                onSuccess()
            } catch(e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

    fun deleteMateria(id:Int) {
        viewModelScope.launch{

            try {
                repository.deleteMateria(id)
                loadMaterias()
            } catch (e:Exception) {
                error = e.message
            }
            isLoading = false
        }
    }

}