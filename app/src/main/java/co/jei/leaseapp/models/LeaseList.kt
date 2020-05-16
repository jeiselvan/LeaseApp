package co.jei.leaseapp.models

import com.google.gson.annotations.SerializedName

class LeaseList(@SerializedName("id") val id: String,
                @SerializedName("tenant") val tenant: String)