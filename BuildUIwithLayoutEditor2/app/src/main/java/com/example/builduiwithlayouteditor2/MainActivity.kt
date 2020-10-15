package com.example.builduiwithlayouteditor2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //Define info list into array

    val firstnames = arrayOf("Antti", "Mikko", "Roger","Mike","Juha")
    val lastnames = arrayOf("Suomalainen", "Pekonen", "Ikkunen","Jackson","Perkele")
    val jobtitles = arrayOf("Genius at time", "CEO of nothing", "Captain of his imaginary ship","Busy discovering dark matter","?")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// show first employee data
        showEmployeeData(0)
    }

    //Display employees data in UI

    fun showEmployeeData(index: Int){
        // show data to UI
        firstnameTextView.text = firstnames[index]
        lastnameTextView.text = lastnames[index]
        jobtitleTextView.text = jobtitles[index]
        employeeInfoTextView.text = lastnames[index] + " " + firstnames[index] + " is . . . ." + getString(R.string.basic_text)
        //load and show image for resources
        var id = 0
        if (index == 0) id = R.drawable.employee1
        else if (index == 1) id = R.drawable.employee2
        else if (index == 2) id = R.drawable.employee3
        else if (index == 3) id = R.drawable.employee4
        else if (index == 4) id = R.drawable.employee5
        imageView.setImageResource(id)
    }

    fun numberClicked(view: View?) {
        // get text show in TextView
        val text = (view as TextView).text.toString()
        // convert text to integer, array is starting from zero position
        val int = text.toInt() - 1
        // call function to show selected employees data
        showEmployeeData(int)
    }

}