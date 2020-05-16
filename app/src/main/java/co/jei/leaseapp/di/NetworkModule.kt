package co.jei.leaseapp.di

import android.content.Context
import co.jei.leaseapp.BuildConfig.BASE_URL
import co.jei.leaseapp.network.LeaseAPI
import co.jei.leaseapp.network.LeaseService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesLeaseAPI(context: Context): LeaseAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LeaseAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLeaseService(): LeaseService {
        return LeaseService()
    }

    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun getCache(context: Context): Cache {
        return Cache(
            File(context.cacheDir, "okhttp_cache_lease"),
            (10 * 1024 * 1024).toLong()
        )
    }

    @Provides
    @Singleton
    fun getHttpClient(appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .cache(getCache(appContext))
            .build()
    }

    @Provides
    @Singleton
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}