package com.mky.tdapp.data.remote

import com.mky.tdapp.data.model.MateriaRequest
import com.mky.tdapp.data.model.MateriasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MateriaApi {
    @GET("materia")
    suspend fun getMaterias(): Response<MateriasResponse>

    @POST("materia")
    suspend fun createMateria(
        @Body request: MateriaRequest
    ) : Response<Unit>


    @PUT("materia/{id}")
    suspend fun  updateMateria(
        @Path("id") id: Int,
        @Body request: MateriaRequest
    ): Response<Unit>

    @PUT("materia/baja/{id}")
    suspend fun deleteMateria(
        @Path("id") id: Int
    ): Response<Unit>
}