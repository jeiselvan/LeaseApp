package co.jei.leaseapp.tests

import android.content.Context
import co.jei.leaseapp.di.DaggerTestAppComponent
import co.jei.leaseapp.di.TestApplicationModule
import co.jei.leaseapp.models.LeaseList
import co.jei.leaseapp.network.LeaseTestService
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject


class WebServicesTest {
    
    @Inject
    lateinit var leaseService: LeaseTestService

    var context = mock(Context::class.java)

    @Before
    fun setUp() {
        val component = DaggerTestAppComponent.builder()
            .testApplicationModule(TestApplicationModule(context))
            .build()
        component.inject(this)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @Test
    fun getLeaseList_Response_Success() {
        val it = leaseService.getLeaseList().test()
        it.assertNoErrors()
    }

    @Test
    fun getLeaseList_Response_Failure() {
        val it = leaseService.getLeaseList().test()
        if (it.errors().isNotEmpty()) {
            assert(false)
        }
    }

    @Test
    fun getLeaseList_Response_List_Not_Empty() {
        val list = leaseService.getLeaseList().blockingGet()
        assertNotNull(list)
    }

    @Test
    fun getLeaseList_Response_List_Empty() {
        val list = leaseService.getLeaseList().blockingGet()
        assertNull(list)
    }

    @Test
    fun getLeaseList_isValidModel() {
        val list = leaseService.getLeaseList().blockingGet()
        assertTrue(list.stream().anyMatch { e -> e is LeaseList })
    }

    @Test
    fun getLeaseList_Size_Success() {
        val list = leaseService.getLeaseList().blockingGet()
        assertEquals(list.size, 3)
    }

    @Test
    fun getLeaseList_Size_Failure() {
        val list = leaseService.getLeaseList().blockingGet()
        assertEquals(list.size, 2)
    }

    @Test
    fun getLeaseList_ContainsItem() {
        val list = leaseService.getLeaseList().blockingGet()
        assert(list.stream().anyMatch { item -> "lease-a" == item.id })
    }

    @Test
    fun getLeaseInfo_Response_Success() {
        val it = leaseService.getLeaseInfo("lease-a").test()
        it.assertNoErrors()
    }

    @Test
    fun getLeaseInfo_Response_Failure() {
        val it = leaseService.getLeaseInfo("lease-a").test()
        if (it.errors().isNotEmpty()) {
            assert(false)
        }
    }

    @Test
    fun getLeaseInfo_Response_Not_Empty() {
        val data = leaseService.getLeaseInfo("lease-a").blockingGet()
        assertNotNull(data)
    }

    @Test
    fun getLeaseInfo_Response_Empty() {
        val data = leaseService.getLeaseInfo("lease-a").blockingGet()
        assertNull(data)
    }

    @Test
    fun getLeaseInfo_Response_Id_Valid() {
        val data = leaseService.getLeaseInfo("lease-a").blockingGet()
        assertEquals(data.id, "lease-a")
    }

    @Test
    fun getLeaseInfo_Response_Id_Not_Valid() {
        val data = leaseService.getLeaseInfo("lease-a").blockingGet()
        assertEquals(data.id, "lease-b")
    }
}
