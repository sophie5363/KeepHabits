package com.example.keephabits.ui.fragments.habitlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keephabits.R
import com.example.keephabits.data.models.Habit
import com.example.keephabits.ui.fragments.habitlist.adapters.HabitListAdapter
import com.example.keephabits.ui.viewmodels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_habit_list.*
import kotlinx.coroutines.InternalCoroutinesApi


class HabitList : Fragment(R.layout.fragment_habit_list) {

    private lateinit var habitList: List<Habit>
    @InternalCoroutinesApi
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var adapter: HabitListAdapter

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Adapter
        adapter = HabitListAdapter()
        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(context)


        //ViewModel
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer{
            adapter.setData(it)
            habitList = it

            if(it.isEmpty()){
                rv_habits.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_habits.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }
        })

        //Montre l'options menu dans ce fragment
        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(habitList)
            swipeToRefresh.isRefreshing = false
        }

        fab_add.setOnClickListener{
            findNavController().navigate(R.id.action_habitList_to_createHabitsItem)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_main, menu)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_delete -> habitViewModel.deleteAllHabits()
        }

        return super.onOptionsItemSelected(item)
    }

}