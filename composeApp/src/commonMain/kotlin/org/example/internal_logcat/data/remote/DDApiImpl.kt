package org.example.internal_logcat.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.internal_logcat.data.remote.api_routes.BASE_URL
import org.example.internal_logcat.data.remote.api_routes.add_form_data
import org.example.internal_logcat.data.remote.api_routes.device_list
import org.example.internal_logcat.data.remote.api_routes.fetch_form_data
import org.example.internal_logcat.data.remote.api_routes.login_route
import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.request.LoginRequest
import org.example.internal_logcat.domain.models.response.DeviceDataResponse
import org.example.internal_logcat.domain.models.response.FormResponse
import org.example.internal_logcat.domain.models.response.LoginResponse
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse

class DDApiImpl(private val client: HttpClient) : DDApi {

    override suspend fun loginApi(email:String,password:String): LoginResponse {
        val loginRequest = LoginRequest(email,password)

        val response: HttpResponse = client.post(BASE_URL+ login_route){
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }
        return response.body()
    }

    override suspend fun devicesListApi(): DeviceDataResponse {
        val response: HttpResponse = client.get(BASE_URL+ device_list){
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    override suspend fun addFormData(formRequest: FormRequest): FormResponse {
        val response: HttpResponse = client.post(BASE_URL+ add_form_data){
            contentType(ContentType.Application.Json)
            setBody(formRequest)
        }
        return response.body()
    }

    override suspend fun fetchFormData(id:String): SingleDeviceResponse {
        val response: HttpResponse = client.get(BASE_URL+fetch_form_data+id){
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    override suspend fun updateFormData(id: String,formRequest: FormRequest): FormResponse {
        val response: HttpResponse = client.put(BASE_URL+ api_routes.update_form_data+id){
            contentType(ContentType.Application.Json)
            setBody(formRequest)
        }
        return response.body()
    }
}