package com.example.pc.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersonModelDatabase.TABLE_NAME + " (" +
                    PersonModelDatabase.idNumber + " INTEGER PRIMARY KEY," +
                    PersonModelDatabase.firstName + " TEXT," +
                    PersonModelDatabase.lastName + " TEXT," +
                    PersonModelDatabase.gender + " TEXT," +
                    PersonModelDatabase.birthDate + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PersonModelDatabase.TABLE_NAME;

    private static final int DB_VERSION = 2;

    DatabaseHelper(Context context) {

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

    List<PersonModel> getPersonsData(){

        List<PersonModel> personData = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        try(Cursor cursor = db.query(PersonModelDatabase.TABLE_NAME,
                new String[]{PersonModelDatabase.idNumber,
                        PersonModelDatabase.firstName,
                        PersonModelDatabase.lastName,
                        PersonModelDatabase.gender,
                        PersonModelDatabase.birthDate},
                null, null, null, null, null)){

            cursor.moveToFirst();

            do {
                PersonModel personModel = new PersonModel();

                if (cursor.getCount()>0) {
                    personModel.setIdNumber(cursor.getInt(cursor.getColumnIndex(PersonModelDatabase.idNumber)));
                    personModel.setFirstName(cursor.getString(cursor.getColumnIndex(PersonModelDatabase.firstName)));
                    personModel.setLastName(cursor.getString(cursor.getColumnIndex(PersonModelDatabase.lastName)));
                    personModel.setGender(cursor.getString(cursor.getColumnIndex(PersonModelDatabase.gender)));
                    personModel.setBirthDate(cursor.getString(cursor.getColumnIndex(PersonModelDatabase.birthDate)));
                }
                personData.add(personModel);

            } while (cursor.moveToNext());
        }

        return personData;
    }

    void addPersonData(PersonModel personModel) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PersonModelDatabase.idNumber, personModel.getIdNumber());
        contentValues.put(PersonModelDatabase.firstName, personModel.getFirstName());
        contentValues.put(PersonModelDatabase.lastName, personModel.getLastName());
        contentValues.put(PersonModelDatabase.gender, personModel.getGender());
        contentValues.put(PersonModelDatabase.birthDate, personModel.getBirthDate());

         db.insert(PersonModelDatabase.TABLE_NAME, null, contentValues);
    }

//    public int deletePersonData(int id) {
//
//        String idText = id + "";
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        db.delete(PersonModelDatabase.TABLE_NAME, "1", null);
//
//        return db.delete(PersonModelDatabase.TABLE_NAME,
//
//                PersonModelDatabase.idNumber + " = ?",
//
//                new String[]{idText});
//    }

    void deleteAll() {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(PersonModelDatabase.TABLE_NAME, "1", null);
    }



}
