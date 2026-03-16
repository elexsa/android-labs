package com.example.laba3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "PizzaOrders.db", null, 1) {

    // Створюємо таблицю з двома колонками: id та текст замовлення
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE orders (id INTEGER PRIMARY KEY AUTOINCREMENT, order_details TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS orders")
        onCreate(db)
    }

    // Функція для збереження замовлення
    fun insertOrder(orderDetails: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("order_details", orderDetails)
        val result = db.insert("orders", null, values)
        return result != -1L // Повертає true, якщо успішно
    }

    // Функція для отримання всіх замовлень
    fun getAllOrders(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM orders", null)
        var allOrders = ""

        if (cursor.moveToFirst()) {
            do {
                // Беремо текст з другої колонки (індекс 1)
                allOrders += cursor.getString(1) + "\n\n---\n\n"
            } while (cursor.moveToNext())
        }
        cursor.close()
        return allOrders
    }
}