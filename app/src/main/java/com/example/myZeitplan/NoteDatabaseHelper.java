package com.example.myZeitplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + NoteContract.TABLE_NAME + " (" +
                        NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY," +
                        NoteContract.NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                        NoteContract.NoteEntry.COLUMN_NAME_CONTENT + " TEXT," +
                        NoteContract.NoteEntry.COLUMN_NAME_DATE + " INTEGER)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NoteContract.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NoteContract.NoteEntry.COLUMN_NAME_CONTENT, note.getContent());
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DATE, note.getDateInMillis());

        db.insert(NoteContract.TABLE_NAME, null, values);
        db.close();
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NoteContract.NoteEntry.COLUMN_NAME_CONTENT, note.getContent());
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DATE, note.getDateInMillis());

        String selection = NoteContract.NoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(note.getId()) };

        db.update(NoteContract.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = NoteContract.NoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(note.getId()) };

        db.delete(NoteContract.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();

        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TITLE,
                NoteContract.NoteEntry.COLUMN_NAME_CONTENT,
                NoteContract.NoteEntry.COLUMN_NAME_DATE
        };

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                NoteContract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                NoteContract.NoteEntry.COLUMN_NAME_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_CONTENT));
                long dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_DATE));
                Date date = new Date(dateInMillis);

                Note note = new Note();
                note.setId((int) id);
                note.setTitle(title);
                note.setContent(content);
                note.setTimestamp(dateInMillis);

                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return noteList;
    }
}

