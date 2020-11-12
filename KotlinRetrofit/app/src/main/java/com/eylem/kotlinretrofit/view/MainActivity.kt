package com.eylem.kotlinretrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylem.kotlinretrofit.R
import com.eylem.kotlinretrofit.adapter.CryptoAdapter
import com.eylem.kotlinretrofit.model.CryptoModel
import com.eylem.kotlinretrofit.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL="https://api.nomics.com/v1/"
    private var cryptoModels: ArrayList<CryptoModel>?=null
    private var cryptoAdapter: CryptoAdapter?=null
    private var compositeDisposable: CompositeDisposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //API key: c3015198c7b6f88355d3993da453f777
        //API URL: https://api.nomics.com/v1/prices?key=c3015198c7b6f88355d3993da453f777

        compositeDisposable= CompositeDisposable()

        val layoutManager: RecyclerView.LayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        loadData()
    }

    private fun loadData(){
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))
    }

    private fun handleResponse(cryptoList: List<CryptoModel>){
        cryptoModels=ArrayList(cryptoList)
        cryptoModels?.let {
            cryptoAdapter=CryptoAdapter(it)
            recyclerView.adapter=cryptoAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}