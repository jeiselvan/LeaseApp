package co.jei.leaseapp.network

import co.jei.leaseapp.LeaseApp
import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.models.LeaseList
import io.reactivex.Single
import javax.inject.Inject

class LeaseService {

    @Inject
    lateinit var api: LeaseAPI

    init {
        LeaseApp.appComponent.inject(this)
    }

    fun getLeaseList(): Single<List<LeaseList>> {
        return api.getLeaseList()
    }

    fun getLeaseInfo(id: String): Single<LeaseInfo> {
        return api.getLeaseInfo(id)
    }
}