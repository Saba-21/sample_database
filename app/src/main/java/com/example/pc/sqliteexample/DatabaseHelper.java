package com.example.pc.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 16-Dec-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Database.PersonModel.TABLE_NAME + " (" +
                    Database.PersonModel.idNumber + " INTEGER PRIMARY KEY," +
                    Database.PersonModel.firstName + " TEXT," +
                    Database.PersonModel.lastName + " TEXT," +
                    Database.PersonModel.gender + " TEXT," +
                    Database.PersonModel.birthDate + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Database.PersonModel.TABLE_NAME;

    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, "MyDb.db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public List<PersonModel> getPersonsData(){

        List<PersonModel> personData = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(Database.PersonModel.TABLE_NAME,
                new String[]{Database.PersonModel.idNumber,
                        Database.PersonModel.firstName,
                        Database.PersonModel.lastName,
                        Database.PersonModel.gender,
                        Database.PersonModel.birthDate},
                null, null, null, null, null);

        cursor.moveToFirst();

        do {
            PersonModel personModel = new PersonModel();

            if (cursor.getCount()>0) {
                personModel.setIdNumber(cursor.getInt(cursor.getColumnIndex(Database.PersonModel.idNumber)));
                personModel.setFirstName(cursor.getString(cursor.getColumnIndex(Database.PersonModel.firstName)));
                personModel.setLastName(cursor.getString(cursor.getColumnIndex(Database.PersonModel.lastName)));
                personModel.setGender(cursor.getString(cursor.getColumnIndex(Database.PersonModel.gender)));
                personModel.setBirthDate(cursor.getString(cursor.getColumnIndex(Database.PersonModel.birthDate)));
            }
            personData.add(personModel);

        } while (cursor.moveToNext());

        return personData;
    }

    public long addPersonData(PersonModel personModel) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.PersonModel.idNumber, personModel.getIdNumber());
        contentValues.put(Database.PersonModel.firstName, personModel.getFirstName());
        contentValues.put(Database.PersonModel.lastName, personModel.getLastName());
        contentValues.put(Database.PersonModel.gender, personModel.getGender());
        contentValues.put(Database.PersonModel.birthDate, personModel.getBirthDate());

        return db.insert(Database.PersonModel.TABLE_NAME, null, contentValues);
    }

    public int deletePersonData(int id) {

        String idText = id + "";

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(Database.PersonModel.TABLE_NAME,

                Database.PersonModel.idNumber + " = ?",

                new String[]{idText});
    }

}
