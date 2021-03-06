package com.cyhee.android.rabit.activity.goal

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalContract {
    interface View : BaseView<Presenter> {
        fun showGoalInfos(goalInfo: GoalInfo)
    }

    interface Presenter : BasePresenter {
        fun goalInfos(id: Long)
        fun toggleLikeForGoal(id: Long, post:Boolean)
        fun toggleLikeForGoalLog(id: Long, post:Boolean)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
        fun deleteGoal(id: Long)
        fun report(id: Long, reportType: ReportType)
    }
}