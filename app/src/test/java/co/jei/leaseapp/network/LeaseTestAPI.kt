package co.jei.leaseapp.network

import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.models.LeaseList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface LeaseTestAPI {

    @GET("v1/leases")
    fun getLeaseList(): Single<List<LeaseList>>

    @GET("v1/leases/{id}")
    fun getLeaseInfo(@Path("id") id: String): Single<LeaseInfo>
}