package co.jei.leaseapp.tests

import android.content.Context
import co.jei.leaseapp.di.DaggerTestAppComponent
import co.jei.leaseapp.di.TestApplicationModule
import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.models.LeaseList
import co.jei.leaseapp.network.LeaseTestService
import co.jei.leaseapp.rules.RxImmediateSchedulerRule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

class WebServicesTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Inject
    lateinit var leaseService: LeaseTestService

    var context = mock(Context::class.java)

    @Before
    fun setUp() {
        val component = DaggerTestAppComponent.builder()
            .testApplicationModule(TestApplicationModule(context))
            .build()
        component.inject(this)
    }

    @Test
    fun getLeaseList() {
        leaseService.getLeaseList().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<LeaseList>>() {
                override fun onSuccess(list: List<LeaseList>) {
                    assertNotNull(list)
                    assert(true)
                }

                override fun onError(e: Throwable) {
                    assert(false)
                }
            })
    }

    @Test
    fun getLeaseInfo() {
        leaseService.getLeaseInfo("lease-a").subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<LeaseInfo>() {
                override fun onSuccess(info: LeaseInfo) {
                    assertNotNull(info)
                    assert(true)
                }

                override fun onError(e: Throwable) {
                    assert(false)
                }
            })
    }
}
