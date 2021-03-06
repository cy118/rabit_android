package com.cyhee.android.rabit.activity.personlist.followinglist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class FollowingListPresenter(private val view: FollowingListActivity) : FollowingListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun followees(username: String) {
        restClient.followees(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("followees",it.toString())
                            view.showFollowees(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("followees",it.response().toString())
                                Log.d("followees",it.response().body().toString())
                                Log.d("followees",it.response().body().toString())
                                Log.d("followees",it.response().errorBody().toString())
                                Log.d("followees",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("followees",it.toString())
                            }
                        }
                )
    }
}