package com.example.alciomarhollanda.listadetarefa;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alciomarhollanda.listadetarefa.DataBase.TaskContract;
import com.example.alciomarhollanda.listadetarefa.DataBase.TaskDBHelper;

public class MainActivity extends AppCompatActivity {


    private TaskDBHelper helper;
    private ListView listaTarefas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addTarefa = findViewById(R.id.addTarefa);
        helper = new TaskDBHelper(this);
        listaTarefas = findViewById(R.id.listaTarefas);

        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textoTarefa = view.findViewById(R.id.textoTarefaLista);
                String tarefa = textoTarefa.getText().toString();

                String sql = String.format("DELETE FROM %s WHERE %s = '%s'"
                        ,TaskContract.TABLE, TaskContract.columns.TAREFA, tarefa);

                SQLiteDatabase sqlite = helper.getWritableDatabase();
                sqlite.execSQL(sql);

                Toast.makeText(MainActivity.this, "Tarefa excluida com sucesso!", Toast.LENGTH_SHORT).show();
                updateUI();
                return false;
            }
        });

        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarefa();
            }
        });

        updateUI();
    }

    private void updateUI() {
        SQLiteDatabase sqlite = helper.getReadableDatabase();

        Cursor cursor = sqlite.query(TaskContract.TABLE, new String[]{TaskContract.columns._ID,
                TaskContract.columns.TAREFA,TaskContract.columns.PRAZO },
                null,null,null,null,null);

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.celula_tarefa,
                cursor,
                new String[]{TaskContract.columns.TAREFA,TaskContract.columns.PRAZO},
                new int[]{R.id.textoTarefaLista, R.id.textoPrazo},
                0
        );

        listaTarefas.setAdapter(listAdapter);
    }

    private void addTarefa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alerView = getLayoutInflater().inflate(R.layout.alert_tarefa,null);

        final EditText textoTarefa = alerView.findViewById(R.id.textoTarefaAlert);
        final EditText textoPrazo = alerView.findViewById(R.id.textoPrazo);

        builder.setView(alerView);

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // salvar a tarefa

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.columns.TAREFA, textoTarefa.getText().toString());
                values.put(TaskContract.columns.PRAZO, textoPrazo.getText().toString());

                db.insertWithOnConflict(TaskContract.TABLE,null, values, SQLiteDatabase.CONFLICT_IGNORE);

                updateUI();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // salvar a tarefa
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }
}
