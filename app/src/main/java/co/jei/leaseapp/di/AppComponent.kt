package co.jei.leaseapp.di

import co.jei.leaseapp.network.LeaseService
import co.jei.leaseapp.viewmodels.LeaseViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun inject(leaseService: LeaseService)

    fun inject(leaseService: LeaseViewModel)
}