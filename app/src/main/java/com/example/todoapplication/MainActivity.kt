package com.example.todoapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todoapplication.db.DataObject
import com.example.todoapplication.db.Entity
import com.example.todoapplication.db.myDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import kotlinx.android.synthetic.main.activity_main.add
//import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {

    private lateinit var addBtn: Button
    private lateinit var deleteAllbtn: Button

    private lateinit var recyclerView: RecyclerView

    private lateinit var dataBase: myDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataBase = Room.databaseBuilder(
            applicationContext, myDataBase::class.java, "To_Do"
        ).build()



        recyclerView = findViewById(R.id.recycler_view)

        //Add Records
        addBtn = findViewById(R.id.add)
        addBtn.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        //Delete all Records
        deleteAllbtn = findViewById(R.id.deleteAll)
        deleteAllbtn.setOnClickListener {
            DataObject.deleteAll()
            GlobalScope.launch {
                dataBase.dao().deleteAll()
            }
            setRecycler()
        }

        setRecycler()

    }


    fun setRecycler(){
        recyclerView.adapter = Adapter(DataObject.getAllData())
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}