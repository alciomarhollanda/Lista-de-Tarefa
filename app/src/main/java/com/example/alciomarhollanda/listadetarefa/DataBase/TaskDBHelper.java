package com.example.alciomarhollanda.listadetarefa.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alciomar.hollanda on 23/03/2018.
 */

public class TaskDBHelper extends SQLiteOpenHelper {


    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "%s TEXT, %s TEXT)",TaskContract.TABLE,TaskContract.columns.TAREFA,TaskContract.columns.PRAZO);

        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TaskContract.TABLE);
    }
}
