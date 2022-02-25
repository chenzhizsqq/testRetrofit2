package com.example.testretrofit2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import retrofit2.converter.gson.GsonConverterFactory

interface APIService {
    // ...

    @POST
    suspend fun testSelf(@Body requestBody: RequestBody, @Url url: String): Response<ResponseBody>

    @POST("/api/v1/create")
    suspend fun createEmployee(@Body requestBody: RequestBody): Response<ResponseBody>


    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<ResponseBody>
    // ...

    @POST("/EthpJson.aspx")
    suspend fun testAspxPost(@Body requestBody: RequestBody): Response<ResponseBody>


    companion object {
        var retrofitService: APIService? = null
        fun getInstance() : APIService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://ssl.ethp.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(APIService::class.java)
            }
            return retrofitService!!
        }

        //POST时候，常用的
        fun requestBody(jsonObject: JSONObject): RequestBody {
            // Convert JSONObject to String
            val jsonObjectString = jsonObject.toString()

            // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            return requestBody
        }
    }

    @POST("/EthpJson.aspx")
    suspend fun testAspxPost2(@Body requestBody: RequestBody): Response<testJson>
}

/* testAspxPost2函数用时的数值 begin */
class TestViewModel : ViewModel() {
    //专门对应json数据中的posts数据List
    val postsDataList = MutableLiveData<testJson>()

}

data class testJson(
    val message: String,
    val result: jsonResult,
    val status: Int
)

data class jsonResult(
    val hpid: String,
    val mail: String,
    val phone: String,
    val tenantid: String,
    val token: String,
    val usercode: String,
    val userid: String,
    val userkana: String,
    val username: String
)
/* testAspxPost2函数用时的数值 end */