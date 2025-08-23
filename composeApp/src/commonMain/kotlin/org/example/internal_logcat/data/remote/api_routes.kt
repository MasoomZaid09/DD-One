package org.example.internal_logcat.data.remote

object api_routes {
    const val BASE_URL = " http://192.168.1.5:8000/"
    const val upload_file = "api/s3/upload-delivery-file"
    const val login_route = "api/logger/auth/login"
    const val device_list = "api/delivery/get-machine-delivery-data"
    const val add_form_data = "api/delivery/add-machine-delivery-data"
    const val fetch_form_data = "api/delivery/get-machine-delivery-data/"

    const val update_form_data = "api/delivery/update-machine-delivery-data/"
}