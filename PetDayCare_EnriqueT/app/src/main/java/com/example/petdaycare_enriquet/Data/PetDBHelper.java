package com.example.petdaycare_enriquet.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.petdaycare_enriquet.Data.PetContract.PetEntry;
import com.example.petdaycare_enriquet.Pets;

import java.util.ArrayList;

public class PetDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PetsDB.db";
    private static final int DATABASE_VERSION = 1;

    public PetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertPet(Pets pet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PetEntry.COLUMN_PET_NAME, pet.getName());
        contentValues.put(PetEntry.COLUMN_PET_BREED, pet.getBreed());
        contentValues.put(PetEntry.COLUMN_PET_GENDER, pet.getGender());
        contentValues.put(PetEntry.COLUMN_PET_WEIGHT, pet.getWeight());

        long newRowId = db.insert(PetEntry.TABLE_NAME, null, contentValues);

        return newRowId;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + "("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_GENDER + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_WEIGHT + " DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Pets> displayDatabaseBaseInfo() {
        ArrayList<Pets> myPetsArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        Cursor cursor = db.query(
                PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int nameColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
        int breedColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
        int weightColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
        int genderColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumn);
            String currentBreed = cursor.getString(breedColumn);
            int currentWeight = cursor.getInt(weightColumn);
            String currentGender = cursor.getString(genderColumn);

            if (currentName.isEmpty() || currentBreed.isEmpty() || Integer.toString(currentWeight).isEmpty() || currentGender.isEmpty()) {
                myPetsArray.add(null);
            } else {
                Pets currentPet = new Pets(currentName, currentBreed, currentGender, currentWeight);
                myPetsArray.add(currentPet);
            }
        }
        return myPetsArray;
    }

    public Pets displayPetInfo(int PetIdGiven) {
        Pets myPet = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        Cursor cursor = db.rawQuery("select * from " + PetEntry.TABLE_NAME + " where " + PetEntry._ID + "=" + PetIdGiven + ";", null);

        int nameColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
        int breedColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
        int weightColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
        int genderColumn = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumn);
            String currentBreed = cursor.getString(breedColumn);
            int currentWeight = cursor.getInt(weightColumn);
            String currentGender = cursor.getString(genderColumn);

            if (currentName.isEmpty() || currentBreed.isEmpty() || Integer.toString(currentWeight).isEmpty() || currentGender.isEmpty()) {
                myPet = null;
            } else {
                myPet = new Pets(currentName, currentBreed, currentGender, currentWeight);
            }
        }
        return myPet;
    }

    public void UpdatePet(int PetIdGiven, Pets UpdatePet) {
        SQLiteDatabase db = this.getReadableDatabase();

        /*ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_WEIGHT, UpdatePet.getWeight());
        values.put(PetEntry.COLUMN_PET_BREED, UpdatePet.getBreed());
        values.put(PetEntry.COLUMN_PET_NAME, UpdatePet.getName());
        values.put(PetEntry.COLUMN_PET_GENDER, UpdatePet.getGender());*/

        db.execSQL("UPDATE " + PetEntry.TABLE_NAME + " SET "
                + PetEntry.COLUMN_PET_NAME + "='" + UpdatePet.getName() + "', "
                + PetEntry.COLUMN_PET_BREED + "='" + UpdatePet.getBreed() + "', "
                + PetEntry.COLUMN_PET_GENDER + "='" + UpdatePet.getGender() + "', "
                + PetEntry.COLUMN_PET_WEIGHT + "=" + UpdatePet.getWeight() +
                " WHERE "+PetEntry._ID+ "=" + PetIdGiven + ";");
    }
}
