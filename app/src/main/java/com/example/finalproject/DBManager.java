package com.example.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int VERSION = 2;
    private static DBManager DBRef;
    private MainActivity mainActivity;


    public static DBManager getInstance(Context context) {
        if (DBRef == null) {
            DBRef = new DBManager(context);
            Log.d("Database.java", "getting instance of DB.");
        }
        return DBRef;
    }

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mainActivity = (MainActivity) context;
    }

    private static final class TermTable {
        private static final String TABLE = "Term";
        private static final String COL_ID = "term_id";
        private static final String COL_TITLE = "term_title";
        private static final String COL_START = "term_start";
        private static final String COL_END = "term_end";
    }

    private static final class CourseTable {
        private static final String TABLE = "Course";
        private static final String COL_ID = "courds_id";
        private static final String COL_TERM = "course_term";
        private static final String COL_TITLE = "course_title";
        private static final String COL_STATUS = "course_status";
        private static final String COL_START = "course_start";
        private static final String COL_END = "course_end";
        private static final String COL_NAME = "course_name";
        private static final String COL_PHONE = "course_phone";
        private static final String COL_EMAIL = "course_email";
    }

    private static final class AssmtTable {
        private static final String TABLE = "Assmt";
        private static final String COL_ID = "assmt_id";
        private static final String COL_COURSE = "assmt_course";
        private static final String COL_TITLE = "assmt_title";
        private static final String COL_END = "assmt_end";
    }

    private static final class NoteTable {
        private static final String TABLE = "Note";
        private static final String COL_ID = "note_id";
        private static final String COL_COURSE = "note_course";
        private static final String COL_TITLE = "note_title";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database.java", "onCreate 1");
        db.execSQL(
                "CREATE TABLE " + TermTable.TABLE + "(" +
                        TermTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TermTable.COL_TITLE + " TEXT, " +
                        TermTable.COL_START + " TEXT, " +
                        TermTable.COL_END + " TEXT ) "
        );

        db.execSQL(
                "CREATE TABLE " + CourseTable.TABLE + "(" +
                        CourseTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CourseTable.COL_TERM + " INTEGER , " +
                        CourseTable.COL_TITLE + " TEXT, " +
                        CourseTable.COL_START + " DATE, " +
                        CourseTable.COL_END + " DATE, " +
                        CourseTable.COL_STATUS + " CHAR, " +
                        CourseTable.COL_NAME + " TEXT, " +
                        CourseTable.COL_PHONE + " TEXT," +
                        CourseTable.COL_EMAIL + " TEXT, " +
                        "FOREIGN KEY(" + CourseTable.COL_TERM + ") " +
                        "REFERENCES " + TermTable.TABLE + "(" + TermTable.COL_ID + ")) "
        );

        db.execSQL(
                "CREATE TABLE " + AssmtTable.TABLE + "(" +
                        AssmtTable.COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        AssmtTable.COL_COURSE + " INTEGER, " +
                        AssmtTable.COL_TITLE + " TEXT, " +
                        AssmtTable.COL_END + " DATE, " +
                        "FOREIGN KEY(" + AssmtTable.COL_COURSE + ") " +
                        "REFERENCES " + CourseTable.TABLE + "(" + CourseTable.COL_ID + ")) "
        );

        db.execSQL(
                "CREATE TABLE " + NoteTable.TABLE + "(" +
                        NoteTable.COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NoteTable.COL_COURSE + " INTEGER, " +
                        NoteTable.COL_TITLE + " TEXT, " +
                        "FOREIGN KEY(" + NoteTable.COL_COURSE + ") " +
                        "REFERENCES " + CourseTable.TABLE + "(" + CourseTable.COL_ID + ")) "
        );


    }


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        // THESE WILL DELETE ALL DATABASE DATA!!!!
        Log.d("Database.java","onUpgade...");
        db.execSQL("drop table if exists " + TermTable.TABLE);
        db.execSQL("drop table if exists " + CourseTable.TABLE);
        db.execSQL("drop table if exists " + AssmtTable.TABLE);
        db.execSQL("drop table if exists " + NoteTable.TABLE);
        Log.d("Database.java","database upgraded.");
        this.onCreate(db);
    }

    public void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public class AddTermTask extends AsyncTask<Bundle, Void, Bundle> {
        protected Bundle doInBackground(Bundle... bundles) {
            ContentValues values = new ContentValues();
            values.put(TermTable.COL_TITLE, bundles[0].getString("title"));
            values.put(TermTable.COL_START, bundles[0].getString("start"));
            values.put(TermTable.COL_END, bundles[0].getString("end"));
            //https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper
            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            Long id = db.insert(TermTable.TABLE, null, values);
            bundles[0].putLong("id",id);
            return bundles[0];
        }
        protected void onPostExecute(Bundle bundle) {
            mainActivity.receiveNewTerm(bundle);
        }
    }

    public void addTerm(Bundle bundle) {
        Log.d("Database.java","Add term.");
        new AddTermTask().execute(bundle);
        Log.d("DBManager", "New thread started to handle add term.");
    }

    public class GetTermsTask extends AsyncTask<Integer, Void, Cursor> {

        protected Cursor doInBackground(Integer... inta) {
            SQLiteDatabase db = DBManager.DBRef.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TermTable.TABLE + ";",
                    new String[] {}
            );
            Log.d("Database.java", "Term received.");
            return cursor;
        }

        protected void onPostExecute(Cursor cursor) {
            while (cursor.moveToNext()) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", cursor.getLong(0));
                Log.d("DBManager", "Get Terms Task: id = " + String.valueOf(
                        bundle.getLong("ID")
                ));
                bundle.putString("title", cursor.getString(1));
                mainActivity.receiveNewTerm(bundle);
            }
        }
    }

    public void getAllTerms() {
        Log.d("Database.java","Get all terms.");
        new GetTermsTask().execute(1);
        Log.d("DBManager", "New thread started to handle getting all terms.");
    }

    public class DeleteTermTask extends AsyncTask<Long, Void, Void> {

        protected Void doInBackground(Long... idList) {

            String id = String.valueOf(idList[0]);
            Log.d("DbManager", String.valueOf(id));
            //Check for courses associated with this term before deleting

            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            int result = db.delete(TermTable.TABLE, TermTable.COL_ID+"="+ id, null);
            Log.d("Database.java", "Term deleted. result = " + String.valueOf(result));
            mainActivity.confirmDelete(id);
            return null;
        }
    }

    public void deleteTerm(Long id) {
        Long[] idList = new Long[] { id};
        DeleteTermTask task = new DeleteTermTask();
        task.execute(idList);
    }



}