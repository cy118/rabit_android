package com.cyhee.android.rabit.activity.goallogwrite

import android.net.Uri
import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalLogWriteContract {
    interface View : BaseView<Presenter> {
       fun showGoalNames(goals: MutableList<Goal>?)
    }

    interface Presenter : BasePresenter {
        fun goalNames()
        fun postGoalLog(parenId: Long, goalLog: GoalLogFactory.Post)
        fun editGoalLog(id: Long, goalLog: GoalLogFactory.Post)
        fun upload(parentId: Long, goalLog: GoalLogFactory.Post, fileUri: Uri?)
        fun editUpload(id: Long, goalLog: GoalLogFactory.Post, fileUri: Uri?)
    }
}