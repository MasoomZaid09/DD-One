package org.dd_healthcare.internal_logcat.domain.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormRequest(
    @SerialName("accountDepartment")
    var accountDepartment: ArrayList<AccountDepartment> = ArrayList(),
    @SerialName("dispatchDepartment")
    var dispatchDepartment: ArrayList<DispatchDepartment> = ArrayList(),
    @SerialName("productionDepartment")
    var productionDepartment: ArrayList<ProductionDepartment> = ArrayList(),
    @SerialName("serviceDepartment")
    var serviceDepartment: ArrayList<ServiceDepartment> = ArrayList(),
    @SerialName("userId")
    var userId: String = ""
)

@Serializable
data class AccountDepartment(
    @SerialName("amount")
    var amount: String = "",
    @SerialName("billedTo")
    var billedTo: String = "",
    @SerialName("deliveryBill")
    var deliveryBill: String = "",
    @SerialName("deliveryNote")
    var deliveryNote: String = "",
    @SerialName("ewayBill")
    var ewayBill: String = "",
    @SerialName("invoiceNumber")
    var invoiceNumber: String = "",
    @SerialName("shippedTo")
    var shippedTo: String = ""
)

@Serializable
data class DispatchDepartment(
    @SerialName("address")
    var address: String = "",
    @SerialName("city")
    var city: String = "",
    @SerialName("hospitalName")
    var hospitalName: String = "",
    @SerialName("state")
    var state: String = ""
)

@Serializable
data class ProductionDepartment(
    @SerialName("androidMacAddress")
    var androidMacAddress: String = "",
    @SerialName("description")
    var description: String = "",
    @SerialName("deviceId")
    var deviceId: String = "",
    @SerialName("dhrFile")
    var dhrFile: String = "",
    @SerialName("exhaleValveType")
    var exhaleValveType: String ="",
    @SerialName("ipMacAddress")
    var ipMacAddress: String = "",
    @SerialName("model")
    var model: String = "",
    @SerialName("neoSensorType")
    var neoSensorType: String = "",
    @SerialName("screenSize")
    var screenSize: String = "",
    @SerialName("serialNumber")
    var serialNumber: String = "",
    @SerialName("softwareVersion")
    var softwareVersion: String = "",
    @SerialName("type")
    var type: String = ""
)

@Serializable
data class ServiceDepartment(
    @SerialName("dispatchDate")
    var dispatchDate: String = "",
    @SerialName("installationDate")
    var installationDate: String = "",
    @SerialName("installationOrFeedbackReport")
    var installationOrFeedbackReport: String = "",
    @SerialName("serviceEngineerName")
    var serviceEngineerName: String = ""
)