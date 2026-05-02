package com.mky.tdapp.data.repository

import com.mky.tdapp.data.model.Materia
import com.mky.tdapp.data.model.MateriaRequest
import com.mky.tdapp.data.remote.MateriaApi

class MateriaRepository(
    private val api: MateriaApi
){
    suspend fun getMaterias() : List<Materia> {
        val response = api.getMaterias()

        if(!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            throw Exception("Error ${response.code()}: $errorBody")
        }

        return response.body()?.materias ?: emptyList()
    }

    suspend fun createMateria(nombre: String) {
        val response = api.createMateria(MateriaRequest(nombre))

        if(!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()

            val message = errorBody
                ?.replace("{\"message\":\"", "")
                ?.replace("\"}", "")
                ?: "Error desconocido"

            throw Exception(message)
        }
    }

    suspend fun updateMateria(id: Int, nombre: String ) {
        val response = api.updateMateria(id, MateriaRequest(nombre))

        if(!response.isSuccessful){
            val msg = response.errorBody()?.string()
            throw Exception(msg ?: "Error al actualizar materia")
        }
    }

    suspend fun deleteMateria(id: Int) {
        val response = api.deleteMateria(id)

        if(!response.isSuccessful) {
            val msg = response.errorBody()?.string()
            throw Exception(msg ?: "Error al eliminar materia")
        }
    }
}