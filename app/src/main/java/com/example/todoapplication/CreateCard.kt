package com.example.todoapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class CreateCard : AppCompatActivity() {

    private lateinit var create_title: EditText
    private lateinit var create_priority: EditText
    private lateinit var save_button: Button

    private lateinit var dataBase: myDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        dataBase = Room.databaseBuilder(
            applicationContext, myDataBase::class.java, "To_Do"
        ).build()


        create_title = findViewById(R.id.create_title)
        create_priority = findViewById(R.id.create_priority)

        save_button = findViewById(R.id.save_button)
        save_button.setOnClickListener {

            if(create_title.text.toString().trim{it <= ' '}.isNotEmpty()
                && create_priority.text.toString().trim{it <= ' '}.isNotEmpty()){

                var title = create_title.getText().toString()
                var priority = create_priority.getText().toString()
                DataObject.setData(title, priority)


                GlobalScope.launch {
                    dataBase.dao().insertTask(Entity(0, title, priority))
                }

                Toast.makeText(this, "Task Added Successfully!\uD83C\uDF89\uD83C\uDF89", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }

    }
}