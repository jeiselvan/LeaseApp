package co.jei.leaseapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule(context: Context) {

    private val mContext = context

    @Singleton
    @Provides
    open fun provideContext(): Context {
        return mContext
    }
}