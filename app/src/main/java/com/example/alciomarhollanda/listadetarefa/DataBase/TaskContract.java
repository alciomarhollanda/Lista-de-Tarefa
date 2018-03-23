package com.example.alciomarhollanda.listadetarefa.DataBase;

import android.provider.BaseColumns;

/**
 * Created by alciomar.hollanda on 23/03/2018.
 */

public class TaskContract {

    public static final String DB_NAME = "br.com.alciomar";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tarefas";

    public class columns{
        public static final String TAREFA = "tarefa";
        public static final String PRAZO = "prazo";
        public static final String _ID = BaseColumns._ID;

    }
}
