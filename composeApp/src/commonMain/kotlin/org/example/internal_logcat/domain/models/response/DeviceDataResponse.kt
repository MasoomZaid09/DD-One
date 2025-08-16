package org.example.internal_logcat.domain.models.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.internal_logcat.presentation.ui.mini_features.AccountScreen

@Serializable
data class DeviceDataResponse(
    @SerialName("data")
    val `data`: ArrayList<Data>,
    @SerialName("message")
    val message: String = "",
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("statusValue")
    val statusValue: String = ""
)

@Serializable
data class Data(
    @SerialName("accountDepartment")
    val accountDepartment: ArrayList<AccountDepartment> = arrayListOf<AccountDepartment>(
        AccountDepartment()
    ),
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("dispatchDepartment")
    val dispatchDepartment: ArrayList<DispatchDepartment> = arrayListOf<DispatchDepartment>(
        DispatchDepartment()
    ),
    @SerialName("_id")
    val id: String = "",
    @SerialName("productionDepartment")
    val productionDepartment: ArrayList<ProductionDepartment> = arrayListOf<ProductionDepartment>(
        ProductionDepartment()
    ),
    @SerialName("serviceDepartment")
    val serviceDepartment: ArrayList<ServiceDepartment> = arrayListOf<ServiceDepartment>(
        ServiceDepartment()
    ),
    @SerialName("userId")
    val userId: String = "",
)

@Serializable
data class AccountDepartment(
    @SerialName("amount")
    val amount: String = "",
    @SerialName("billedTo")
    val billedTo: String = "",
    @SerialName("deliveryBill")
    val deliveryBill: String = "",
    @SerialName("deliveryNote")
    val deliveryNote: String = "",
    @SerialName("ewayBill")
    val ewayBill: String = "",
    @SerialName("_id")
    val id: String = "",
    @SerialName("invoiceNumber")
    val invoiceNumber: String = "",
    @SerialName("shippedTo")
    val shippedTo: String = ""
)

@Serializable
data class DispatchDepartment(
    @SerialName("address")
    val address: String = "",
    @SerialName("city")
    val city: String = "",
    @SerialName("hospitalName")
    val hospitalName: String = "",
    @SerialName("_id")
    val id: String = "",
    @SerialName("state")
    val state: String = ""
)

@Serializable
data class ProductionDepartment(
    @SerialName("androidMacAddress")
    val androidMacAddress: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("deviceId")
    val deviceId: String = "",
    @SerialName("dhrFile")
    val dhrFile: String = "",
    @SerialName("exhaleValveType")
    val exhaleValveType: String = "",
    @SerialName("_id")
    val id: String = "",
    @SerialName("ipMacAddress")
    val ipMacAddress: String = "",
    @SerialName("model")
    val model: String = "",
    @SerialName("neoSensorType")
    val neoSensorType: String = "",
    @SerialName("screenSize")
    val screenSize: String = "",
    @SerialName("serialNumber")
    val serialNumber: String = "",
    @SerialName("softwareVersion")
    val softwareVersion: String = "",
    @SerialName("type")
    val type: String = ""
)

@Serializable
data class ServiceDepartment(
    @SerialName("dispatchDate")
    val dispatchDate: String = "",
    @SerialName("_id")
    val id: String = "",
    @SerialName("installationDate")
    val installationDate: String = "",
    @SerialName("installationOrFeedbackReport")
    val installationOrFeedbackReport: String = "",
    @SerialName("serviceEngineerName")
    val serviceEngineerName: String = ""
)

