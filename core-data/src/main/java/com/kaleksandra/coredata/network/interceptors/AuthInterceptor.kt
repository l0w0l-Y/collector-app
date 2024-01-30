package com.kaleksandra.coredata.network.interceptors

import com.kaleksandra.coredata.network.database.DataStoreProvider
import com.kaleksandra.coredata.network.database.TOKEN_KEY
import com.kaleksandra.coredata.network.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val PREFIX_TOKEN: String = "Bearer"
private const val AUTHORIZATION_HEADER: String = "Authorization"

class AuthInterceptor @Inject constructor(
    dataStore: DataStoreProvider,
    @ApplicationScope mainScope: CoroutineScope
) : Interceptor {

    private var token = ""

    init {
        dataStore.get(TOKEN_KEY)
            .distinctUntilChanged()
            .onEach { token = it ?: "" }
            .launchIn(mainScope)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$PREFIX_TOKEN $token")
            .build()
        return chain.proceed(request)
    }
}