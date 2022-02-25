package com.example.testretrofit2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testretrofit2.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

//CLEARTEXT communication to dummy.restapiexample.com not permitted by network security policy
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //自己测试的
        binding.testSelf.setOnClickListener { testSelf() }

        //demo
        binding.testPut.setOnClickListener { testPut() }
        binding.getMethod.setOnClickListener { getMethod() }

        binding.aspxPost.setOnClickListener { testAspxPost() }
    }

    private fun testSelf() {
        binding.jsonContent.text = ""

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ssl.ethp.net/EthpJson.aspx/")
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("app", "EtOfficeLogin")
        jsonObject.put("uid", "demo1@xieyi.co.jp")
        jsonObject.put("password", "pass")
        jsonObject.put("registrationid", "6")
        jsonObject.put("device", "android")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.testSelf(requestBody, "")

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonContent.text = prettyJson

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }


    private fun getMethod() {

        binding.jsonContent.text = ""

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            /*
             * For @Query: You need to replace the following line with val response = service.getEmployees(2)
             * For @Path: You need to replace the following line with val response = service.getEmployee(53)
             */

            // Do the GET request and get response
            val response = service.getEmployees()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.e("Pretty Printed JSON :", prettyJson)
                    binding.jsonContent.text = prettyJson

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }


    private fun testPut() {
        binding.jsonContent.text = ""

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", "Jackchen")
        jsonObject.put("salary", "3540chen")
        jsonObject.put("age", "23")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.createEmployee(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonContent.text = prettyJson

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    private fun testAspxPost(){

        val ApiUrl = "https://ssl.ethp.net"

        val jsonObject = JSONObject()
        jsonObject.put("app", "EtOfficeLogin")
        jsonObject.put("uid", "demo1@xieyi.co.jp")
        jsonObject.put("password", "pass")
        jsonObject.put("registrationid", "6")
        jsonObject.put("device", "android")


        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiUrl)
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.testAspxPost(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonContent.text = prettyJson

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}