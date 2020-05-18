package co.jei.leaseapp.network

import android.content.Context
import co.jei.leaseapp.di.DaggerTestAppComponent
import co.jei.leaseapp.di.TestApplicationModule
import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.models.LeaseList
import io.reactivex.Single
import org.mockito.Mockito
import javax.inject.Inject

class LeaseTestService {

    @Inject
    lateinit var api: LeaseTestAPI

    init {

        var context = Mockito.mock(Context::class.java)

        val component = DaggerTestAppComponent.builder()
            .testApplicationModule(TestApplicationModule(context))
            .build()
        component.inject(this)
    }

    fun getLeaseList(): Single<List<LeaseList>> {
        return api.getLeaseList()
    }

    fun getLeaseInfo(id: String): Single<LeaseInfo> {
        return api.getLeaseInfo(id)
    }
}