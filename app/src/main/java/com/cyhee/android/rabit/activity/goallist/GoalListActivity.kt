package com.cyhee.android.rabit.activity.goallist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goallist.*
import android.support.v7.widget.GridLayoutManager
import com.cyhee.android.rabit.activity.decoration.CardItemDeco
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.settings.SettingsActivity
import com.cyhee.android.rabit.listener.IntentListener
import kotlinx.android.synthetic.main.item_complete_usertopbar.*

class GoalListActivity: AppCompatActivity(), GoalListContract.View {

    override var presenter : GoalListContract.Presenter = GoalListPresenter(this)
    private var goalListAdapter: GoalListViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallist)
        bottomBar.selectTabAtPosition(2)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.userGoalInfos(username)

            val gridLayoutManager = GridLayoutManager(this, 2)
            goalListListView.layoutManager = gridLayoutManager

            usernameText.text = username

            searchBtn.setOnClickListener(IntentListener.toSearchListener())
            toUpBtn.setOnClickListener{
                goalListListView.smoothScrollToPosition(0)
            }

            // swipe refresh
            goalListSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@GoalListActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                goalListAdapter?.clear()
                presenter.userGoalInfos(username)
            }
        }

        bottomBar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tabHome -> {
                    val intentToMain = Intent(this, MainActivity::class.java)
                    intentToMain.putExtra("username", user)
                    startActivity(intentToMain)
                }
                R.id.tabWall -> {
                    val intentToMyWall = Intent(this, MyWallActivity::class.java)
                    intentToMyWall.putExtra("username", user)
                    startActivity(intentToMyWall)
                }
                R.id.tabSetting -> {
                    val intentToSettings = Intent(this, SettingsActivity::class.java)
                    startActivity(intentToSettings)
                }
            }
        }

    }

    override fun showGoals(goalInfos: MutableList<GoalInfo>) {
        if (goalListAdapter == null) {
            goalListAdapter = GoalListViewAdapter(goalInfos)
            goalListListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalListListView.adapter = goalListAdapter
        } else {
            goalListAdapter!!.appendGoalInfos(goalInfos)
        }

        goalListListView.addItemDecoration(CardItemDeco(this))
        goalListSwipeRefresh?.isRefreshing = false
    }

}