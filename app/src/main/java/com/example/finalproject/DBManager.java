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
    private TermActivity termActivity;
    private CourseActivity courseActivity;


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
        private static final String COL_START = "course_start";
        private static final String COL_END = "course_end";
        private static final String COL_STATUS = "course_status";
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
                        CourseTable.COL_TERM + " INTEGER, " +
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
    public void setTermActivity(TermActivity activity) { termActivity = activity; }
    public void setCourseActivity(CourseActivity activity) {courseActivity = activity;}

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

    public class GetTermDataTask extends AsyncTask<Long, Void, Cursor> {
        protected Cursor doInBackground(Long... inta) {
            Long id = inta[0];
            SQLiteDatabase db = DBManager.DBRef.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TermTable.TABLE +
                            " WHERE " + TermTable.COL_ID + " = " + id + ";",
                    new String[] {}
            );
            Log.d("Database.java", "Term received.");
            return cursor;
        }

        protected void onPostExecute(Cursor cursor) {
            while (cursor.moveToNext()) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", cursor.getLong(0));
                bundle.putString("title", cursor.getString(1));
                bundle.putString("start", cursor.getString(2));
                bundle.putString("end", cursor.getString(3));
                Log.d("DBManager", "Get Term data Task: id = " + String.valueOf(
                        bundle.getLong("ID")
                ));

                termActivity.receiveTermData(bundle);
            }
        }
    }

    public void getTermData(Long id) {
        new GetTermDataTask().execute(id);
    }

    public class DeleteTermTask extends AsyncTask<Long, Void, Void> {

        protected Void doInBackground(Long... idList) {

            String id = String.valueOf(idList[0]);
            Log.d("DbManager", id);
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

    public class UpdateTermDataTask extends AsyncTask<Bundle,Void, Integer> {
        protected Integer doInBackground(Bundle... params) {
            Bundle bundle = params[0];
            ContentValues values = new ContentValues();
            values.put(TermTable.COL_ID, bundle.getString("id"));
            values.put(TermTable.COL_TITLE, bundle.getString("title"));
            values.put(TermTable.COL_START, bundle.getString("start"));
            values.put(TermTable.COL_END, bundle.getString("end"));
            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            Integer result =  db.update(
                    TermTable.TABLE,
                    values,
                    TermTable.COL_ID + "=?",
                    new String[] { bundle.getString("id") }
            );
            return result;
        }

        @Override
        protected void onPostExecute(Integer rows) {
            termDataSaveReturnResult(rows);
        }
    }

    public void updateTermData(Bundle bundle) {
        new UpdateTermDataTask().execute(new Bundle[] { bundle });
    }

    public void termDataSaveReturnResult(int result) {
        termActivity.verifySave(result);
    }

    public class AddCourseTask extends AsyncTask<Bundle, Void, Bundle> {
        protected Bundle doInBackground(Bundle... bundles) {
            ContentValues values = new ContentValues();
            values.put(CourseTable.COL_TITLE, "New Course");
            values.put(CourseTable.COL_TERM, bundles[0].getLong("term_id"));
            //https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper
            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            Long id = db.insert(CourseTable.TABLE, null, values);
            bundles[0].putLong("id",id);
            bundles[0].putString("title", "New Course");
            return bundles[0];
        }
        protected void onPostExecute(Bundle bundle) {
            termActivity.receiveNewCourse(bundle);
        }
    }

    public void addCourseToTerm(Long termId) {
        Log.d("DBManager","Add course to term");
        Bundle bundle = new Bundle();
        bundle.putLong("term_id", termId);
        new AddCourseTask().execute(new Bundle[] {bundle});
    }

    public class DeleteCourseTask extends AsyncTask<Long, Void, Void> {

        protected Void doInBackground(Long... idList) {

            String id = String.valueOf(idList[0]);
            //Check for courses associated with this term before deleting

            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            int result = db.delete(CourseTable.TABLE, CourseTable.COL_ID+"="+ id, null);
            Log.d("Database.java", "Course deleted. result = " + String.valueOf(result));
            termActivity.confirmDelete(id);
            return null;
        }
    }

    public void deleteCourse(Long id) {
        Log.d("DBManager","Delete course.");
        new DeleteCourseTask().execute(new Long[] { id } );
    }

    public class GetCoursesTask extends AsyncTask<Long, Void, Cursor> {

        protected Cursor doInBackground(Long... params) {
            Long term_id = params[0];
            SQLiteDatabase db = DBManager.DBRef.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + CourseTable.TABLE +
                            " WHERE " + CourseTable.COL_TERM + "="+term_id+  ";",
                    new String[] {}
            );
            Log.d("Database.java", "Term received.");
            return cursor;
        }

        protected void onPostExecute(Cursor cursor) {
            while (cursor.moveToNext()) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", cursor.getLong(0));
                Log.d("DBManager", "Get Courses Task: id = " + String.valueOf(
                        bundle.getLong("ID")
                ));
                bundle.putString("title", cursor.getString(2 ));
                termActivity.receiveNewCourse(bundle);
            }
        }
    }

    public void getAllCoursesData(Long termId) {
        new GetCoursesTask().execute(termId);
    }

    public class GetCourseDataTask extends AsyncTask<Long, Void, Cursor> {
        protected Cursor doInBackground(Long... inta) {
            Long id = inta[0];
            SQLiteDatabase db = DBManager.DBRef.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + CourseTable.TABLE +
                            " WHERE " + CourseTable.COL_ID + " = " + id + ";",
                    new String[]{}
            );
            Log.d("Database.java", "Term received.");
            return cursor;
        }

        protected void onPostExecute(Cursor cursor) {
            while (cursor.moveToNext()) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", cursor.getLong(0));
                bundle.putString("title", cursor.getString(2));
                bundle.putString("start", cursor.getString(3));
                bundle.putString("end", cursor.getString(4));
                bundle.putString("status", cursor.getString(5));
                bundle.putString("name", cursor.getString(6));
                bundle.putString("phone", cursor.getString(7));
                bundle.putString("email", cursor.getString(8));
                Log.d("DBManager", "Get Term data Task: id = " + String.valueOf(
                        bundle.getLong("ID")
                ));

                courseActivity.updateCourseDetailFragment(bundle);
            }
        }
    }

    public void getCourseData(Long courseId) {
        new GetCourseDataTask().execute(courseId);
    }

    public class UpdateCourseDataTask extends AsyncTask<Bundle,Void, Integer> {
        protected Integer doInBackground(Bundle... params) {
            Bundle bundle = params[0];
            ContentValues values = new ContentValues();
            values.put(CourseTable.COL_ID, bundle.getString("id"));
            values.put(CourseTable.COL_TITLE, bundle.getString("title"));
            values.put(CourseTable.COL_START, bundle.getString("start"));
            values.put(CourseTable.COL_END, bundle.getString("end"));
            values.put(CourseTable.COL_STATUS, bundle.getString("status"));
            values.put(CourseTable.COL_NAME, bundle.getString("name"));
            values.put(CourseTable.COL_PHONE, bundle.getString("phone"));
            values.put(CourseTable.COL_EMAIL, bundle.getString("email"));
            SQLiteDatabase db = DBManager.DBRef.getWritableDatabase();
            Integer result =  db.update(
                    CourseTable.TABLE,
                    values,
                    CourseTable.COL_ID + "=?",
                    new String[] { bundle.getString("id") }
            );
            return result;
        }

        @Override
        protected void onPostExecute(Integer rows) {
            termDataSaveReturnResult(rows);
        }
    }
    public void updateCourseData(Bundle bundle) {

    }

}