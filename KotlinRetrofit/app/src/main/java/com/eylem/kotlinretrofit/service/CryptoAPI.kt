package com.eylem.kotlinretrofit.service

import com.eylem.kotlinretrofit.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface CryptoAPI {
    @GET("prices?key=c3015198c7b6f88355d3993da453f777")
    fun getData(): Observable<List<CryptoModel>>
}