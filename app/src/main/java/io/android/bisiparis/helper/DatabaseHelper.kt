package io.android.bisiparis.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import io.android.bisiparis.enums.DatabaseInfo
import io.androidedu.datapersistance.ui.sql.model.CustomerOrderInfo


class DatabaseHelper(context: Context)

    : SQLiteOpenHelper(context, DatabaseInfo.DatabaseName.toString(), null, DatabaseInfo.DatabaseVersion.toString().toInt()) {

    private val TABLE_CustomerS = "customers"

    private val KEY_ID = "id"
    private val KEY_NAME = "name"
    private val KEY_SOUP = "soup"
    private val KEY_FOOD = "food"
    private val KEY_DRINK = "drink"
    private val KEY_DESSERT = "dessert"


    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE $TABLE_CustomerS " +
                "($KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NAME TEXT," +
                "$KEY_SOUP TEXT," +
                "$KEY_FOOD TEXT," +
                "$KEY_DRINK TEXT," +
                "$KEY_DESSERT TEXT)"

        db!!.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CustomerS")
        onCreate(db)
    }

    fun addCustomer(customerInfo: CustomerOrderInfo) {

        val db = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, customerInfo.customerName)
        contentValues.put(KEY_SOUP, customerInfo.soup)
        contentValues.put(KEY_FOOD, customerInfo.food)
        contentValues.put(KEY_DRINK, customerInfo.drink)
        contentValues.put(KEY_DESSERT, customerInfo.desserts)

        db.insert(TABLE_CustomerS, null, contentValues)
        db.close()
    }

    fun updateCustomer(customerOrderInfo: CustomerOrderInfo): Int {

        val db = writableDatabase

        val contentValues = ContentValues()

        if (!TextUtils.isEmpty(customerOrderInfo.customerName.trim()))
            contentValues.put(KEY_NAME, customerOrderInfo.customerName)

        if (!TextUtils.isEmpty(customerOrderInfo.soup.trim()))
            contentValues.put(KEY_SOUP, customerOrderInfo.food)

        if (!TextUtils.isEmpty(customerOrderInfo.food.trim()))
            contentValues.put(KEY_FOOD, customerOrderInfo.food)

        if (!TextUtils.isEmpty(customerOrderInfo.drink.trim()))
            contentValues.put(KEY_DRINK, customerOrderInfo.drink)

        if (!TextUtils.isEmpty(customerOrderInfo.food.trim()))
            contentValues.put(KEY_DESSERT, customerOrderInfo.food)

        val isSuccess = db.update(TABLE_CustomerS, contentValues, "$KEY_ID = ?", arrayOf(customerOrderInfo.customerID.toString()))

        db.close()

        return isSuccess
    }

    fun deleteCustomer(customerOrderInfo: CustomerOrderInfo) {

        val db = writableDatabase


        db.delete(TABLE_CustomerS, "$KEY_ID = ?", arrayOf(customerOrderInfo.customerID.toString()))
        db.close()
    }

    fun getAllCustomer(): ArrayList<CustomerOrderInfo> {

        val db = readableDatabase

        val customerInfoList = ArrayList<CustomerOrderInfo>()

        val selectQuery = "SELECT  * FROM $TABLE_CustomerS"

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {

            do {

                val customerInfo = CustomerOrderInfo(cursor.getString(0).toLong(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5))

                customerInfoList.add(customerInfo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return customerInfoList
    }

    fun getCustomer(customerID: Long): CustomerOrderInfo {

        val db = this.readableDatabase

        val cursor = db.query(TABLE_CustomerS, arrayOf(KEY_ID, KEY_NAME, KEY_SOUP, KEY_FOOD, KEY_DRINK, KEY_DESSERT),
                "$KEY_ID = ?", arrayOf(customerID.toString()), null, null, null, null)

//        if (cursor != null)
//            cursor.moveToFirst()
        cursor?.moveToFirst()

        val customerInfo = CustomerOrderInfo(cursor.getString(0).toLong(),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5))

        cursor.close()
        db.close()

        return customerInfo
    }
}