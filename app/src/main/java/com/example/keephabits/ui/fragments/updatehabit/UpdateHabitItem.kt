package com.example.keephabits.ui.fragments.updatehabit


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.keephabits.R
import com.example.keephabits.data.models.Habit
import com.example.keephabits.ui.viewmodels.HabitViewModel
import com.example.keephabits.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habits_item.*
import kotlinx.android.synthetic.main.fragment_update_habit_item.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


class UpdateHabitItem : Fragment(R.layout.fragment_update_habit_item), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""


    @InternalCoroutinesApi
    private lateinit var habitViewModel: HabitViewModel
    private val args by navArgs<UpdateHabitItemArgs>()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //Récupération des données de la liste d'habitudes
        et_habitTitle_update.setText(args.selectedHabit.habit_title)
        et_habitDescription_update.setText(args.selectedHabit.habit_description)

        //Choix d'un drawable
        drawableSelected()

        //Nouveau choix de date et d'heure
        pickDateAndTime()

        //Confirmation des changements de l'item sélectionné
        btn_confirm_update.setOnClickListener{
            updateHabit()
        }

        //Montre l'option menu dans ce fragment en haut à droite
        setHasOptionsMenu(true)
    }

    @InternalCoroutinesApi
    private fun updateHabit(){
        //Récupération du texte des editTests
        title = et_habitTitle_update.text.toString()
        description = et_habitDescription_update.text.toString()

        //Création d'une string timeStamp pour le recyclerView
        timeStamp = "$cleanDate $cleanTime"

        //Vérification que le formulaire est complet avant de soumettre les données à la BDD
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)){
            val habit = Habit(args.selectedHabit.id, title, description, timeStamp, drawableSelected)

            //Ajout de l'habitude si tous les champs sont remplis
            habitViewModel.updateHabit(habit)
            Toast.makeText(context, "Habitude modifiée avec succès", Toast.LENGTH_SHORT).show()

            //Retour au fragment d'accueil
            findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
        } else {
            Toast.makeText(context, "Veuillez remplir tous les champs!", Toast.LENGTH_SHORT).show()
        }
    }

    //Crée un sélecteur pour les icônes qui apparaîtront dans les cellules du recyclerView
    private fun drawableSelected(){
        iv_fastFoodSelected_update.setOnClickListener(){
            iv_fastFoodSelected_update.isSelected = !iv_fastFoodSelected_update.isSelected
            drawableSelected = R.drawable.ic_fastfood_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_smokingSelected_update.isSelected = false
            iv_teaSelected_update.isSelected = false
        }

        iv_smokingSelected_update.setOnClickListener(){
            iv_smokingSelected_update.isSelected = !iv_smokingSelected_update.isSelected
            drawableSelected = R.drawable.ic_baseline_smoke_free_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_fastFoodSelected_update.isSelected = false
            iv_teaSelected_update.isSelected = false
        }

        iv_teaSelected_update.setOnClickListener(){
            iv_teaSelected_update.isSelected = !iv_teaSelected_update.isSelected
            drawableSelected = R.drawable.ic_baseline_emoji_food_beverage_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_fastFoodSelected_update.isSelected = false
            iv_smokingSelected_update.isSelected = false
        }


    }

    //Click listeners pour les pickers de la date et de l'heure
    private fun pickDateAndTime(){
        btn_pickDate_update.setOnClickListener{
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime_update.setOnClickListener{
            getTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
    }

    //Récupère l'heure actuelle
    private fun getTimeCalendar(){
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)

    }

    //Récupère la date actuelle
    private fun getDateCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }

    //Crée l'options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_delete -> deleteHabit(args.selectedHabit)
        }

        return super.onOptionsItemSelected(item)
    }

    @InternalCoroutinesApi
    private fun deleteHabit(habit: Habit){
        habitViewModel.deleteHabit(habit)
        Toast.makeText(context, "Habitude effacée avec succès", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
    }

}