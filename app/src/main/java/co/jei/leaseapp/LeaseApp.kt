package co.jei.leaseapp

import android.app.Application
import android.content.Context
import co.jei.leaseapp.di.AppComponent
import co.jei.leaseapp.di.AppModule
import co.jei.leaseapp.di.DaggerAppComponent

class LeaseApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appComponent = buildComponent()
    }

    protected fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}