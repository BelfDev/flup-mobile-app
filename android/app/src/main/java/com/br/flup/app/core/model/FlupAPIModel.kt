package com.br.flup.app.core.model

import com.br.flup.app.authentication.model.*
import com.br.flup.app.catalog.model.ProductResult
import com.br.flup.app.catalog.model.Product
import com.google.gson.annotations.SerializedName
import java.util.*

data class EventSignInRequest(val email: String, val password: String)
data class EmployeeSignInRequest(val name: String, val password: String)

fun SignInForm.asEventData() = EventSignInRequest(this.identifier, this.password)
fun SignInForm.asEmployeeData() = EmployeeSignInRequest(this.identifier, this.password)

interface SignInResponse : DomainMappable<SignInResult> {
    override fun asDomain(): SignInResult
}

data class EventSignInSuccessResponse(val token: String) : SignInResponse {
    override fun asDomain() = Event(token)
}

data class EmployeeSignInSuccessResponse(
    @SerializedName("employe") val employee: Employee
) : SignInResponse {

    override fun asDomain(): SignInResult =
        User(
            employee.userId,
            employee.createdAt,
            employee.name,
            Role.valueOf(employee.roles[0].toUpperCase())
        )

}

data class SignInFailedResponse(
    @SerializedName("msg") val errorMessage: String
) : SignInResponse {
    override fun asDomain(): SignInResult =
        SignInFailure(errorMessage)

}

data class Employee(
    val name: String,
    @SerializedName("user") val userId: String,
    @SerializedName("Created_date") val createdAt: Date,
    @SerializedName("role") val roles: List<String>
)

interface ProductResponse : DomainMappable<ProductResult> {
    override fun asDomain(): ProductResult
}

data class ProductSuccessResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("category") val category: String?,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("displayOnPdv") val visible: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("isMealProduct") val isMealProduct: Boolean,
    @SerializedName("lowStock") val lowStock: Int,
    @SerializedName("stock") val stock: Int
) : ProductResponse {
    override fun asDomain(): ProductResult = Product(
        id,
        category,
        name,
        price,
        visible,
        image,
        isMealProduct,
        lowStock,
        stock
    )
}

