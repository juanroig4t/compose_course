package com.juanroig.composecourse.data.di

import com.juanroig.composecourse.data.BuildConfig
import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.datasource.remote.MovieRemoteDatasourceImp
import com.juanroig.composecourse.data.datasource.remote.RetrofitMovieNetworkApi
import com.juanroig.composecourse.data.datasource.remote.util.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        retrofitMovieNetworkApi: RetrofitMovieNetworkApi
    ): MovieRemoteDatasource {
        return MovieRemoteDatasourceImp(retrofitMovieNetworkApi)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideBasicAuthInterceptor(): BasicAuthInterceptor {
        return BasicAuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        basicAuthInterceptor: BasicAuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
//            .connectTimeout(50000, TimeUnit.MILLISECONDS)
//            .callTimeout(50000, TimeUnit.MILLISECONDS)
//            .readTimeout(50000, TimeUnit.MILLISECONDS)
//            .writeTimeout(50000, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(basicAuthInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providerRetrofitMovieNetworkApi(
        httpClient: OkHttpClient
    ): RetrofitMovieNetworkApi {
        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = object :
                Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter =
                    retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

                override fun convert(value: ResponseBody) =
                    if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(httpClient)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitMovieNetworkApi::class.java)
    }

}
