package com.cyhee.android.rabit.activity.likelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_likelist.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*


class LikeListActivity: AppCompatActivity(), LikeListContract.View {

    override var presenter : LikeListContract.Presenter = LikeListPresenter(this)
    private var likeListAdapter: LikeListViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        when {
            intent.hasExtra("goalId") -> {
                val goalId = intent.getLongExtra("goalId", -1)
                presenter.likesForGoal(goalId)

            }
            intent.hasExtra("goalLogId") -> {
                val goalLogId = intent.getLongExtra("goalLogId", -1)
                presenter.likesForGoalLog(goalLogId)
            }
            else -> Toast.makeText(this, "전달된 goal/goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showLikes(likes: MutableList<Like>) {
        if (likeListAdapter == null) {
            likeListAdapter = LikeListViewAdapter(likes)
            likeListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            likeListLayout.findViewById<RecyclerView>(R.id.listView).adapter = likeListAdapter
        } else {
            likeListAdapter!!.appendLikes(likes)
        }
    }

}