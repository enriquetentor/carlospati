package com.example.petdaycare_enriquet.Data;

import android.provider.BaseColumns;

public class PetContract {

    private PetContract() {

    }

    public static final class PetEntry implements BaseColumns {
        public final static String TABLE_NAME = "pets";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PET_NAME = "name";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_GENDER = "gender";
        public final static String COLUMN_PET_WEIGHT = "weight";
    }

}
