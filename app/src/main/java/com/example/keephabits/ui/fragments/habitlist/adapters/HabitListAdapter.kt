package com.example.keephabits.ui.fragments.habitlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.keephabits.R
import com.example.keephabits.data.models.Habit
import com.example.keephabits.ui.fragments.habitlist.HabitListDirections
import com.example.keephabits.utils.Calculations
import kotlinx.android.synthetic.main.recycler_habit_item.view.*

class HabitListAdapter: RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {

    var habitList = emptyList<Habit>()
    var TAG = "HabitListAdapter"

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.cv_cardView.setOnClickListener{
                val position = adapterPosition
                Log.d(TAG, "Item cliqué à la position $position")
                Log.d(TAG, "ID: ${habitList[position].id} ")

                val action = HabitListDirections.actionHabitListToUpdateHabitItem(habitList[position])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_habit_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitListAdapter.MyViewHolder, position: Int) {
        val currentHabit = habitList[position]

        holder.itemView.iv_habit_icon.setImageResource(currentHabit.imageId)
        holder.itemView.tv_item_title.text = currentHabit.habit_title
        holder.itemView.tv_item_description.text = currentHabit.habit_description
        holder.itemView.tv_timeElapsed.text = Calculations.calcultateTimeBetweenDates(currentHabit.habit_startTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Depuis ${currentHabit.habit_startTime}"
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun setData(habit: List<Habit>) {
        this.habitList = habit
        notifyDataSetChanged()
    }
}