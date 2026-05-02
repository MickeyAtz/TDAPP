package com.mky.tdapp.data.repository

import com.mky.tdapp.data.model.Grupo
import com.mky.tdapp.data.model.GrupoRequest
import com.mky.tdapp.data.remote.GrupoApi

class GrupoRepository(
    private val api: GrupoApi
){
    suspend fun getGrupos() : List<Grupo> {
        val response = api.getGrupos()

        if(!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            throw Exception("Error ${response.code()}: $errorBody")
        }

        return response.body()?.grupos ?: emptyList()
    }

    suspend fun createGrupo(nombre: String, cicloEescolar: String) {
        val response = api.createGrupo(GrupoRequest(nombre, cicloEescolar))

        if(!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()

            val message = errorBody
                ?.replace("{\"message\":\"", "")
                ?.replace("\"}", "")
                ?: "Error desconocido"

            throw Exception(message)
        }
    }

    suspend fun updateGrupo(id: String, nombre: String, cicloEscolar: String ) {
        val response = api.updateGrupo(id, GrupoRequest(nombre, cicloEscolar))

        if(!response.isSuccessful){
            val msg = response.errorBody()?.string()
            throw Exception(msg ?: "Error al actualizar grupo")
        }
    }

    suspend fun deleteGrupo(id: String) {
        val response = api.deleteGrupo(id)

        if(!response.isSuccessful) {
            val msg = response.errorBody()?.string()
            throw Exception(msg ?: "Error al eliminar grupo")
        }
    }
}