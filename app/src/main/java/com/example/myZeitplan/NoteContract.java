package com.example.myZeitplan;

import android.provider.BaseColumns;

public class NoteContract {
    public static final String TABLE_NAME = "notes";

    public static class NoteEntry implements BaseColumns {
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TITLE + " TEXT," +
                        COLUMN_NAME_CONTENT + " TEXT," +
                        COLUMN_NAME_DATE + " INTEGER)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
