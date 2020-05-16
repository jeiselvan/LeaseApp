package co.jei.leaseapp.models

import com.google.gson.annotations.SerializedName

data class LeaseInfo(
    @SerializedName("id") val id: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("rent") val rent: Int,
    @SerializedName("frequency") val frequency: String,
    @SerializedName("payment_day") val payment_day: String
)