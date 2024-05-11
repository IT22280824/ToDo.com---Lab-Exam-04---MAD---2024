package com.example.todoapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.todoapplication.db.DataObject
import com.example.todoapplication.db.Entity
import com.example.todoapplication.db.myDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import kotlinx.android.synthetic.main.activity_update_card.create_priority
//import kotlinx.android.synthetic.main.activity_update_card.create_title
//import kotlinx.android.synthetic.main.activity_update_card.delete_button
//import kotlinx.android.synthetic.main.activity_update_card.update_button

class UpdateCard : AppCompatActivity() {

    private lateinit var createTitle:EditText
    private lateinit var createPriority:EditText
    private lateinit var deleteButton:Button
    private lateinit var updateButton:Button

    private lateinit var dataBase: myDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        dataBase = Room.databaseBuilder(
            applicationContext, myDataBase::class.java, "To_Do"
        ).build()

        createTitle = findViewById(R.id.create_title)
        createPriority = findViewById(R.id.create_priority)
        deleteButton = findViewById(R.id.delete_button)
        updateButton = findViewById(R.id.update_button)


        val pos = intent.getIntExtra("id", -1)

        if (pos != -1){
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority

            createTitle.setText(title)
            createPriority.setText(priority)


            deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    dataBase.dao().deleteTask(
                        Entity(
                            pos+1,
                            createTitle.text.toString(),
                            createPriority.text.toString())
                    )
                }
                Toast.makeText(this, "Task Deleted Successfully!\uD83D\uDC4D", Toast.LENGTH_LONG).show()
                myIntent()
            }

            updateButton.setOnClickListener {
                DataObject.updateData(
                    pos,
                    createTitle.text.toString() ,
                    createPriority.text.toString()
                )
                GlobalScope.launch {
                    dataBase.dao().updateTask(
                        Entity(pos+1, createTitle.text.toString(), createPriority.text.toString())
                    )
                }
                Toast.makeText(this, title+ " || " +priority+ " \nUpdated successfully!", Toast.LENGTH_LONG).show()
                myIntent()
            }

        }

    }


    fun myIntent(){
        val intent  = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}