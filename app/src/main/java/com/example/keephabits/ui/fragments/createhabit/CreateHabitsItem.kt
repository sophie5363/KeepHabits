package com.example.keephabits.ui.fragments.createhabit


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.keephabits.R
import com.example.keephabits.data.models.Habit
import com.example.keephabits.ui.viewmodels.HabitViewModel
import com.example.keephabits.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habits_item.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class CreateHabitsItem : Fragment(R.layout.fragment_create_habits_item),
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{

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

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //Ajoute une habitude à la BDD
        btn_confirm.setOnClickListener{
            addHabittoDB()
        }

        //Choisit une date et une heure
        pickDateAndTime()

        //Choisit une image à ajouter à l'item dans la vue liste
        drawableSelected()
    }

    //Click listeners pour les pickers de la date et de l'heure
    private fun pickDateAndTime(){
        btn_pickDate.setOnClickListener{
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener{
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    @InternalCoroutinesApi
    private fun addHabittoDB(){

        //Récupération du texte des editTexts
        title = et_habitTitle.text.toString()
        description = et_habitDescription.text.toString()


        //Création d'une string timeStamp pour le recyclerView
        timeStamp = "$cleanDate $cleanTime"


        //Vérification que le formulaire est complet avant de soumettre les données à la BDD
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)){
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            //Ajout de l'habitude si tous les champs sont remplis
            habitViewModel.addHabit(habit)
            Toast.makeText(context, "Habitude créée avec succès", Toast.LENGTH_SHORT).show()

            //Retour au fragment d'accueil
            findNavController().navigate(R.id.action_createHabitsItem_to_habitList)
        } else {
            Toast.makeText(context, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show()
        }
    }

    //Crée un sélecteur pour les icônes qui apparaîtront dans les cellules du recyclerView
    private fun drawableSelected(){
        iv_fastFoodSelected.setOnClickListener(){
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_smokingSelected.setOnClickListener(){
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_smoke_free_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_fastFoodSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_teaSelected.setOnClickListener(){
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_emoji_food_beverage_24

            //Dé-sélectionne les autres icônes quand on en choisit une
            iv_fastFoodSelected.isSelected = false
            iv_smokingSelected.isSelected = false
        }

    }

    //Paramètre la date dans le champ prévu
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }

    //Paramètre l'heure dans le champ prévu
    override fun onTimeSet(TimePicker: TimePicker?, p1: Int, p2: Int) {
        Log.d("Fragment", "Time: $p1:$p2")

        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected.text = "Heure: $cleanTime"
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

}