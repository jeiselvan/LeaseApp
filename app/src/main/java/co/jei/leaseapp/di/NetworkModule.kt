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

    /**
     * Provides the Lease API
     */
    @Provides
    @Singleton
    open fun providesLeaseAPI(context: Context): LeaseAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LeaseAPI::class.java)
    }

    /**
     * Provides the Lease Service
     */
    @Provides
    @Singleton
    fun provideLeaseService(): LeaseService {
        return LeaseService()
    }

    /**
     *  Logging interceptor implementation
     */
    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * Cache your responses if needed.
     */
    @Provides
    @Singleton
    fun getCache(context: Context): Cache {
        return Cache(
            File(context.cacheDir, "okhttp_cache_lease"),
            (10 * 1024 * 1024).toLong()
        )
    }

    /**
     * The Okhttp client implementation
     */
    @Provides
    @Singleton
    fun getHttpClient(appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .cache(null)
            .build()
    }

    /**
     * Disposables to clear the network calls
     */
    @Provides
    @Singleton
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}