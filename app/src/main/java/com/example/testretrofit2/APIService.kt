package com.example.testretrofit2

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    // ...

    @POST
    suspend fun testSelf(@Body requestBody: RequestBody, @Url url: String): Response<ResponseBody>

    @POST("/api/v1/create")
    suspend fun createEmployee(@Body requestBody: RequestBody): Response<ResponseBody>


    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<ResponseBody>
    // ...
}