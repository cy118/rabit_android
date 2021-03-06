package com.cyhee.android.rabit.activity.goal

import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.client.ReportClient
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.ContentType
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.model.ReportType
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalPresenter(private val view: GoalActivity) : GoalContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalInfos(id: Long) {
        restClient.goalInfoWithRate(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goal",it.toString())
                            view.showGoalInfos(it)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("goal",it.response().toString())
                                Log.d("goal",it.response().body().toString())
                                Log.d("goal",it.response().body().toString())
                                Log.d("goal",it.response().errorBody().toString())
                                Log.d("goal",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("goal",it.toString())
                            }
                        }
                )
    }

    override fun toggleLikeForGoal(id: Long, post: Boolean) {
        if (post)
            PostClient.postLikeForGoal(id, scopeProvider) {
                view.toggleLike(true)
            }
        else
            restClient.deleteLikeForGoal(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(false)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )

    }

    override fun postCommentForGoal(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoal(id, comment, scopeProvider)
    }

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if (post)
            PostClient.postLikeForGoalLog(id, scopeProvider) {
                //view.toggleLike(true)
            }
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                //view.toggleLike(false)
                            },
                            {

                            }
                    )
    }

    override fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoalLog(id, comment, scopeProvider)
    }

    override fun deleteGoal(id: Long) {
        restClient.deleteGoal(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                        },
                        {
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }

    override fun report(id: Long, reportType: ReportType) {
        ReportClient.report(ContentType.GOAL, id, reportType, scopeProvider, {
            DialogHandler.confirmDialog(R.string.report_done, view)
        }, {
            DialogHandler.errorDialog(it, view)
        })
    }
}