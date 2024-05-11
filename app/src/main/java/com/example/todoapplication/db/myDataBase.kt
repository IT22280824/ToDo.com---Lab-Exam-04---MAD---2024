package com.example.todoapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1)
abstract class myDataBase: RoomDatabase() {

    abstract fun dao():CardDAO

}