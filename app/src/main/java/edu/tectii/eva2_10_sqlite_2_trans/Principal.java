package edu.tectii.eva2_10_sqlite_2_trans;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    TextView txtVwDatos;
    SQLiteDatabase sqlMiBd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtVwDatos = (TextView)findViewById(R.id.txtVxDatos);
        sqlMiBd = openOrCreateDatabase("prueba_trans", MODE_PRIVATE,null);
        try {
            sqlMiBd.execSQL("create table datos(" +
            "id integer primary key autoincrement," +
            "nombre text);");
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        //
        sqlMiBd.beginTransaction();
        try {
            sqlMiBd.execSQL("insert into datos(nombre)values('Panchito');");
            sqlMiBd.execSQL("insert into datos(nombre)values('Fulanito');");
            int i = 1/0;
            sqlMiBd.execSQL("insert into datos(nombre)values('Perenganito');");
            sqlMiBd.execSQL("insert into datos(nombre)values('Pepe');");
            sqlMiBd.setTransactionSuccessful();
        }catch (Exception E){
            E.printStackTrace();
        }finally {
            sqlMiBd.endTransaction();
        }
        Cursor cursor = sqlMiBd.rawQuery("select * from datos",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            txtVwDatos.append(cursor.getString(cursor.getColumnIndex("nombre")));
            txtVwDatos.append("\n");
            cursor.moveToNext();

        }
    }
}
