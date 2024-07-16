    package com.maxlabs.asdemo.presenter.di


    import com.maxlabs.asdemo.data.api.InspectionApiService
    import dagger.Module
    import dagger.Provides
    import dagger.hilt.InstallIn
    import dagger.hilt.components.SingletonComponent
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)

    class NetModule(){
        @Provides
        @Singleton
        fun provideRetrofit():Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("http://127.0.0.1:5001")
                .baseUrl("http://10.0.2.2:5001/")
                //.baseUrl("http://localhost:5001")
                .build()
        }

        @Provides
        @Singleton
        fun provideAPIService(retrofit: Retrofit):InspectionApiService{
            return retrofit.create(InspectionApiService::class.java)
        }
    }

















