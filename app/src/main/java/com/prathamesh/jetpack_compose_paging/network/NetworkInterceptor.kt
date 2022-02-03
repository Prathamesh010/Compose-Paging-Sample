package com.prathamesh.jetpack_compose_paging.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val API_KEY = "8874e455b5eeca2413cb3beb44bac348"
        val request= chain.request()
        val _url = request.url.newBuilder()
            .addQueryParameter("api_key",API_KEY).build()
        return chain.proceed(request.newBuilder().url(_url).build())
    }
}