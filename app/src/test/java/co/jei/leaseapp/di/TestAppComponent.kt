package co.jei.leaseapp.di

import co.jei.leaseapp.tests.WebServicesTest
import co.jei.leaseapp.network.LeaseTestService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(TestNetworkModule::class), (TestApplicationModule::class)])
interface TestAppComponent {
    fun inject(webServicesTest: WebServicesTest)
    fun inject(leaseTestService: LeaseTestService)
}