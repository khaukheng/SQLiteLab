package my.edu.um.fsktm.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserSQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.User.TABLE_NAME + "(" +
                    UserContract.User.COLUMN_PHONE + " TEXT," +
                    UserContract.User.COLUMN_NAME + " TEXT," +
                    UserContract.User.COLUMN_EMAIL + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME;
    private static final String selection = UserContract.User.COLUMN_NAME + "=?";
    private String[] allColumn = {
            UserContract.User.COLUMN_PHONE,
            UserContract.User.COLUMN_NAME,
            UserContract.User.COLUMN_EMAIL
    };

    public UserSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertUser(UserRecord userRecord) {

        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_PHONE, userRecord.getPhone());
        values.put(UserContract.User.COLUMN_NAME, userRecord.getName());
        values.put(UserContract.User.COLUMN_EMAIL, userRecord.getEmail());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(UserContract.User.TABLE_NAME, null, values);

        database.close();
    }

    public boolean deleteUser(String name) {
        String[] selectionArgs = {name};
        SQLiteDatabase database = this.getWritableDatabase();
        int deletedRows = database.delete(UserContract.User.TABLE_NAME, selection, selectionArgs);
        database.close();
        return deletedRows > 0;

    }

    public boolean updateUser(String name, ContentValues contentValues){
        String[] selectionArgs = {name};
        SQLiteDatabase database = this.getWritableDatabase();
        int updatedRows = database.update(UserContract.User.TABLE_NAME, contentValues, selection, selectionArgs);
        database.close();
        return updatedRows > 0;
    }

    public List<UserRecord> getAllUsers() {
        List<UserRecord> records = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(UserContract.User.TABLE_NAME, allColumn, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            UserRecord userRecord = new UserRecord();
            userRecord.setPhone(cursor.getString(0));
            userRecord.setName(cursor.getString(1));
            userRecord.setEmail(cursor.getString(2));
            records.add(userRecord);
            Log.i("user Record", cursor.toString());
            cursor.moveToNext();

        }

        return records;
    }


    //    debugging purposes only
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

}
