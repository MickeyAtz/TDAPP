package com.mky.tdapp.data.remote

import com.mky.tdapp.data.model.GrupoRequest
import com.mky.tdapp.data.model.GrupoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GrupoApi {
    @GET("grupo")
    suspend fun getGrupos(): Response<GrupoResponse>

    @POST("grupo")
    suspend fun createGrupo(
        @Body request: GrupoRequest
    ) : Response<Unit>


    @PUT("grupo/{id}")
    suspend fun updateGrupo(
        @Path("id") id: String,
        @Body request: GrupoRequest
    ): Response<Unit>

    @PUT("grupo/baja/{id}")
    suspend fun deleteGrupo(
        @Path("id") id: String
    ): Response<Unit>
}