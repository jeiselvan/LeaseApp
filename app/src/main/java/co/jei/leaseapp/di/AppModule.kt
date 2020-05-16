package co.jei.leaseapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private val mContext = context

    @Singleton
    @Provides
    fun provideContext(): Context {
        return mContext
    }
}