package com.example.gitlist.network.service

import com.example.gitlist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Objeto de criar classes de servico do retrofit.
 *
 * @property retrofit A instancia do retrofit.
 */
object ServiceFactory {

    private var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                val client = OkHttpClient.Builder()
                if (BuildConfig.DEBUG) {
                    client.addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                }
                field = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return field
        }

    /**
     * Metodo de obter um servico do retrofit.
     *
     * @param klass A classe java da interface do servico do retrofit a ser criado.
     * @return O servico do retrofit referente a classe passada como parametro.
     */
    fun <T> createService(klass: Class<T>): T {
            return retrofit!!.create(klass)
    }

}