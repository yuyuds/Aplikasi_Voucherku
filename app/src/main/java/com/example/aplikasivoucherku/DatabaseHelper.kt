package com.example.aplikasivoucherku

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.aplikasivoucherku.model.VoucherModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DatabaseHelper(context:Context): SQLiteOpenHelper(
    context,DATABASE_NAME, null,DATABASE_VERSION
) {
    companion object{
        private val DATABASE_NAME = "voucherku"
        private val DATABASE_VERSION = 2
        //table name
        private val TABLE_ACCOUNT = "account"
        //column account table
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_TELEPON = "telepon"
        private val COLUMN_ALAMAT = "alamat"
        private val COLUMN_PASSWORD = "password"
        private val COLUMN_LEVEL = "level"

        //table name
        private val TABLE_VOUCHER = "voucher"
        //column account table
        private val COLUMN_ID_VOUCHER = "idVoucher"
        private val COLUMN_NAMA_VOUCHER = "voucherName"
        private val COLUMN_PRICE_VOUCHER = "price"
        private val COLUMN_IMAGE = "photo"
    }

    //create table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT, "
            + COLUMN_TELEPON + " TEXT, " + COLUMN_ALAMAT + " TEXT, "+ COLUMN_PASSWORD + " TEXT, "+ COLUMN_LEVEL + " INT) ")

    //create table voucher sql query
    private val CREATE_VOUCHER_TABLE = ("CREATE TABLE " + TABLE_VOUCHER + "("
            + COLUMN_ID_VOUCHER + " INT PRIMARY KEY, " + COLUMN_NAMA_VOUCHER + " TEXT, "
            + COLUMN_PRICE_VOUCHER + " INT, " + COLUMN_IMAGE + " BLOB)")

    //drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"
    private val DROP_VOUCHER_TABLE = "DROP TABLE IF EXISTS $TABLE_VOUCHER"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
        p0?.execSQL(CREATE_VOUCHER_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        p0?.execSQL(DROP_VOUCHER_TABLE)
        onCreate(p0)
    }

    //login check
    fun checkLogin(email:String, password:String):Boolean{
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        //selection criteria
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        //selection arguments
        val selectionArgs = arrayOf(email,password)

        val cursor = db.query(TABLE_ACCOUNT, //table to query
            columns, //columns to return
            selection, //columns for WHERE clause
            selectionArgs, //the values for the WHERE clause
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        //check data available or not
        return cursorCount > 0
    }

    //add User
    fun addAccount(email:String, name:String, telepon:String,level:Int,alamat:String,password:String){
        val db = this.readableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_TELEPON, telepon)
        values.put(COLUMN_ALAMAT, alamat)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_LEVEL, level)

        db.insert(TABLE_ACCOUNT, null, values)
        db.close()
    }

    fun checkData(email:String):String{
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_ACCOUNT, //table to query
            columns, //columns to return
            selection, //columns for WHERE clause
            selectionArgs, //the values for the WHERE clause
            null,
            null,
            null)

        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }

    fun addVoucher(voucher: VoucherModel): Long {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_VOUCHER, voucher.id)
        values.put(COLUMN_NAMA_VOUCHER, voucher.name)
        values.put(COLUMN_PRICE_VOUCHER, voucher.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte: ByteArray
        voucher.image.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)


        val result = db.insert(TABLE_VOUCHER, null, values)

//
//        if (result==(0).toLong()){
//            Toast.makeText(,"Add menu Failed", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Add menu Success", Toast.LENGTH_SHORT).show()
//        }

        return result
        db.close()
    }

    fun showAllVoucher():ArrayList<VoucherModel>{
        val listModel = ArrayList<VoucherModel>()
        val db = this.readableDatabase
        var cursor: Cursor?=null
        try{
            cursor = db.rawQuery("SELECT * FROM $TABLE_VOUCHER", null)
        } catch (se: SQLException){
            db.execSQL(CREATE_VOUCHER_TABLE)
            return ArrayList()
        }

        var voucherid:Int
        var name:String
        var price:Int
        var imageArray:ByteArray
        var imageBmp:Bitmap

        if(cursor.moveToFirst()){
            do {
                voucherid = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_VOUCHER))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA_VOUCHER))
                price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE_VOUCHER))
                imageArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))

                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp = BitmapFactory.decodeStream(byteInputStream)
                val model = VoucherModel(id = voucherid, name = name, price = price,image = imageBmp)
                listModel.add(model)
            } while ( cursor.moveToNext())
        }
        return listModel
    }
    fun getVoucher(voucherId: String): ArrayList<VoucherModel>{
        val db = this.readableDatabase
        val listModel = arrayListOf<VoucherModel>()
        val selectQuery = "SELECT  * FROM $TABLE_VOUCHER WHERE $COLUMN_ID_VOUCHER = ?"

        var id:Int
        var name:String
        var price:Int
        var imageArray:ByteArray
        var imageBmp:Bitmap

        db.rawQuery(selectQuery, arrayOf(voucherId)).use{
            if (it.moveToFirst()){
                id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID_VOUCHER))
                name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAMA_VOUCHER))
                price= it.getInt(it.getColumnIndexOrThrow(COLUMN_PRICE_VOUCHER))
                imageArray= it.getBlob(it.getColumnIndexOrThrow(COLUMN_IMAGE))
                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp= BitmapFactory.decodeStream(byteInputStream)
                val model = VoucherModel( id = id, price = price, image = imageBmp, name = name)
                listModel.add(model)
            }

        }
        return listModel

    }
    fun updateVoucher(voucher: VoucherModel, voucherId: String): Int {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAMA_VOUCHER, voucher.name)
        values.put(COLUMN_PRICE_VOUCHER, voucher.price)
        values.put(COLUMN_ID_VOUCHER, voucher.id)

        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        voucher.image.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)

        val result = db.update(TABLE_VOUCHER, values, "$COLUMN_ID_VOUCHER= $voucherId",null)

        return result
        db.close()

    }
    fun deleteVoucher(voucherId: Int): Int {
        val db = this.readableDatabase

        val result = db.delete(TABLE_VOUCHER,"$COLUMN_ID_VOUCHER= $voucherId",null)

        return result
        db.close()
    }
}