package sqlite_database_handeler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.Baby;
import model.Vaccination;

/**
 * Created by Amal on 5/6/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "MyBabyHealthCare";

    // Table Names
    private static final String TABLE_CHILD = "child";
    private static final String TABLE_VACCINATION = "vaccination";
    private static final String TABLE_CHILD_SHOULD_TAKE_VACCINATION = "child_should_take_vaccination";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_SQL_ID = "sql_id";
    private static final String KEY_ID_CHILD = "id_child";
    private static final String KEY_ID_VACCINATION = "id_vaccination";

    // CHILD Table - column names
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PIC = "pic";
    private static final String KEY_BIRTH = "birth_date";

    // VACCINATION Table - column names
    private static final String KEY_VACCINATION_NAME = "name";
    private static final String KEY_VACCINATION_AGE_FROM = "age_from";
    private static final String KEY_VACCINATION_AGE_TO = "age_to";
    private static final String KEY_VACCINATION_DESCREPTION = "description";


    // Tables' Create Statements
    // Child table create statement
    private static final String CREATE_TABLE_CHILD = "CREATE TABLE IF NOT EXISTS " //CREATE TABLE
            + TABLE_CHILD + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SQL_ID +
            " INTEGER NULL," + KEY_FIRST_NAME + " VARCHAR(50) NOT NULL," + KEY_LAST_NAME +
            " VARCHAR(50) NULL," + KEY_BIRTH + " VARCHAR(50) NOT NULL," + KEY_GENDER +
            " VARCHAR(10) NOT NULL," + KEY_PIC + " BLOB NULL" + ")";

    // Vaccination table create statement
    private static final String CREATE_TABLE_VACCINATION = "CREATE TABLE IF NOT EXISTS " //CREATE TABLE
            + TABLE_VACCINATION + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SQL_ID +
            " INTEGER NULL," + KEY_VACCINATION_NAME + " VARCHAR(100) NOT NULL," + KEY_VACCINATION_AGE_FROM +
            " INTEGER NOT NULL," + KEY_VACCINATION_AGE_TO + " INTEGER NULL," + KEY_VACCINATION_DESCREPTION +
            " TEXT NOT NULL )";

    // child should take Vaccination table create statement
    private static final String CREATE_TABLE_CHILD_SHOULD_TAKE_VACCINATION = "CREATE TABLE IF NOT EXISTS " //CREATE TABLE
            + TABLE_CHILD_SHOULD_TAKE_VACCINATION + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ID_CHILD + " INTEGER,"+ KEY_ID_VACCINATION + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CHILD);
        db.execSQL(CREATE_TABLE_VACCINATION);
        db.execSQL(CREATE_TABLE_CHILD_SHOULD_TAKE_VACCINATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD_SHOULD_TAKE_VACCINATION);

        // create new tables
        onCreate(db);
    }

    /*
    * * Creating a child
    */
    public long createChild(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SQL_ID, baby.getId());
        values.put(KEY_FIRST_NAME, baby.first_name);
        values.put(KEY_LAST_NAME, baby.last_name);
        values.put(KEY_BIRTH, "7-2-2016");//baby.birth_date);
        values.put(KEY_GENDER, baby.gender);
        values.put(KEY_PIC, baby.pic_bytes);

        // insert row
        return db.insert(TABLE_CHILD, null, values);
    }

    /*
     * get single child
     */
    public Baby getChild(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHILD + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

            Baby baby = new Baby(String.valueOf(c.getInt(c.getColumnIndex(KEY_SQL_ID))),
                    String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
            baby.first_name = c.getString(c.getColumnIndex(KEY_FIRST_NAME));
            baby.last_name = c.getString(c.getColumnIndex(KEY_LAST_NAME));
            baby.pic_bytes = c.getBlob(c.getColumnIndex(KEY_PIC));
            baby.gender = c.getString(c.getColumnIndex(KEY_GENDER));
            baby.birth_date = c.getString(c.getColumnIndex(KEY_BIRTH));

            c.close();

            return baby;
        }

        return null;
    }

    /*
    * getting all babies
    * */
    public ArrayList<Baby> getAllChildren() {
        ArrayList<Baby> babies = new ArrayList<Baby>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHILD;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Baby baby = new Baby(String.valueOf(c.getInt(c.getColumnIndex(KEY_SQL_ID))),
                        String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
                baby.first_name = c.getString(c.getColumnIndex(KEY_FIRST_NAME));
                baby.last_name = c.getString(c.getColumnIndex(KEY_LAST_NAME));
                baby.gender = c.getString(c.getColumnIndex(KEY_GENDER));
                baby.birth_date = c.getString(c.getColumnIndex(KEY_BIRTH));

                // adding to babies list
                babies.add(baby);
            } while (c.moveToNext());
        }

        c.close();

        return babies;
    }

    /*
     * Updating a baby
     */
    public int updateBaby(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SQL_ID, baby.getId());
        values.put(KEY_FIRST_NAME, baby.first_name);
        values.put(KEY_LAST_NAME, baby.last_name);
        values.put(KEY_GENDER, baby.gender);
        values.put(KEY_BIRTH, baby.birth_date);

        // updating row
        return db.update(TABLE_CHILD, values, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getSqlite_id()) });
    }

    /*
     * Deleting a baby
     */
    public void deleteBaby(long baby_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHILD, KEY_ID + " = ?",
                new String[] { String.valueOf(baby_id) });
    }

    /*
* * Creating a child
*/
    public long createVaccination(Vaccination v) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SQL_ID, v.getId());
        values.put(KEY_VACCINATION_NAME, v.name);
        values.put(KEY_VACCINATION_AGE_FROM, v.age_from);
        values.put(KEY_VACCINATION_AGE_TO, v.age_to);
        values.put(KEY_VACCINATION_DESCREPTION, v.description);

        // insert row
        return db.insert(TABLE_VACCINATION, null, values);
    }

    /*
    * getting all babies
    * */
    public ArrayList<Vaccination> getAllVaccination() {
        ArrayList<Vaccination> vs = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_VACCINATION;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Vaccination v = new Vaccination(String.valueOf(c.getInt(c.getColumnIndex(KEY_SQL_ID))),
                        String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
                v.name = c.getString(c.getColumnIndex(KEY_VACCINATION_NAME));
                v.age_from = c.getString(c.getColumnIndex(KEY_VACCINATION_AGE_FROM));
                v.age_to = c.getString(c.getColumnIndex(KEY_VACCINATION_AGE_TO));
                v.description = c.getString(c.getColumnIndex(KEY_VACCINATION_DESCREPTION));

                // adding to babies list
                vs.add(v);
            } while (c.moveToNext());
        }

        c.close();

        return vs;
    }

    /*
     * get single child
     */
    public Vaccination getVaccination(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_VACCINATION + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

            Vaccination v = new Vaccination(String.valueOf(c.getInt(c.getColumnIndex(KEY_SQL_ID))),
                    String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
            v.name = c.getString(c.getColumnIndex(KEY_VACCINATION_NAME));
            v.age_from = c.getString(c.getColumnIndex(KEY_VACCINATION_AGE_FROM));
            v.age_to = c.getString(c.getColumnIndex(KEY_VACCINATION_AGE_TO));
            v.description = c.getString(c.getColumnIndex(KEY_VACCINATION_DESCREPTION));

            c.close();

            return v;
        }

        return null;
    }


    /*
    * getting all babies
    * */
    public ArrayList<Vaccination> getAllVaccinationForChild(int id_child) {
        ArrayList<Vaccination> vs = new ArrayList<>();
        ArrayList<Integer> Vs_ids = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHILD_SHOULD_TAKE_VACCINATION;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getInt(c.getColumnIndex(KEY_ID_CHILD)) == id_child)
                    Vs_ids.add(c.getInt(c.getColumnIndex(KEY_ID_VACCINATION)));
            } while (c.moveToNext());
        }

        c.close();

        if(!Vs_ids.isEmpty())
        {
            String selectQuery2 = "SELECT  * FROM " + TABLE_VACCINATION;

            Log.e(LOG, selectQuery2);

            SQLiteDatabase db2 = this.getReadableDatabase();
            Cursor c2 = db2.rawQuery(selectQuery2, null);

            // looping through all rows and adding to list
            if (c2.moveToFirst()) {
                do {
                    if(Vs_ids.contains(c2.getInt(c2.getColumnIndex(KEY_ID))))
                    {
                        Vaccination v = new Vaccination(String.valueOf(c2.getInt(c2.getColumnIndex(KEY_SQL_ID))),
                                String.valueOf(c2.getInt(c2.getColumnIndex(KEY_ID))));
                        v.name = c2.getString(c2.getColumnIndex(KEY_VACCINATION_NAME));
                        v.age_from = c2.getString(c2.getColumnIndex(KEY_VACCINATION_AGE_FROM));
                        v.age_to = c2.getString(c2.getColumnIndex(KEY_VACCINATION_AGE_TO));
                        v.description = c2.getString(c2.getColumnIndex(KEY_VACCINATION_DESCREPTION));

                        vs.add(v);
                    }

                } while (c.moveToNext());
            }

            c2.close();
        }

        return vs;
    }

    /*
    * * Creating a child
    */
    public long createVaccinationShouldChildTake(Vaccination v, Baby b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CHILD, b.getId());
        values.put(KEY_ID_VACCINATION, v.getId());

        // insert row
        return db.insert(TABLE_CHILD_SHOULD_TAKE_VACCINATION, null, values);
    }
}
