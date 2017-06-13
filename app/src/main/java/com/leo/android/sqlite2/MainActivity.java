package com.leo.android.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "MyLog";

    private String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония", "Германия",  "Египет",
            "Италия",  "Франция", "Канада"};
    private int people[] = {1400, 311, 195, 142, 128, 82, 80, 66, 62, 35};
    private String region[] = { "Азия",  "Америка",  "Америка",  "Европа",  "Азия",  "Европа",  "Африка",
            "Европа",  "Европа",  "Америка"};

    private Button mAllBtn, mFuncBtn, mPopulationBtn, mPopGroupBtn, mGroupCountBtn, mFinalSrotingBtn;
    private EditText mFunctionText, mPopulationText, mGroupPopulationText;
    private RadioGroup mRadioGroup;

    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAllBtn = (Button) findViewById(R.id.All_context);
        mAllBtn.setOnClickListener(this);

        mFuncBtn = (Button) findViewById(R.id.Function_Button);
        mFuncBtn.setOnClickListener(this);

        mPopulationBtn = (Button) findViewById(R.id.Population_Button);
        mPopulationBtn.setOnClickListener(this);

        mPopGroupBtn = (Button) findViewById(R.id.Population_Group);
        mPopGroupBtn.setOnClickListener(this);

        mGroupCountBtn = (Button) findViewById(R.id.Population_Group_Count);
        mGroupCountBtn.setOnClickListener(this);

        mFinalSrotingBtn = (Button) findViewById(R.id.Sorting_Button);
        mFinalSrotingBtn.setOnClickListener(this);

        mFunctionText = (EditText) findViewById(R.id.Function_Text);
        mPopulationText = (EditText) findViewById(R.id.Population_text);
        mGroupPopulationText = (EditText) findViewById(R.id.Group_Population_Text);

        mRadioGroup = (RadioGroup) findViewById(R.id.Sort);

        mDBHelper = new DBHelper(this);
        //подключаемся к базе
        mDatabase =  mDBHelper.getWritableDatabase();

        //проверка существования записей
        Cursor cursor = mDatabase.query("myTable", null, null, null, null, null, null);
        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            //заполняем таблицу
            for (int i = 0; i < 10; i++){
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);
                Log.d(LOG_TAG, "id = " + mDatabase.insert("myTable", null, contentValues));
            }

        }
        cursor.close();
        mDBHelper.close();
        //эмулируем нажатие кнопки mAllBtn
        onClick(mAllBtn);

    }

    @Override
    public void onClick(View view) {

        //подключаемся к базе
        mDatabase = mDBHelper.getWritableDatabase();

        //читаем данные с полей
        String functionText = mFunctionText.getText().toString();
        String populationText = mPopulationText.getText().toString();
        String groupPopulationText = mGroupPopulationText.getText().toString();

        // переменные для Query
        String[] columns = null; // по какой функции проводится выборка элементов
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        //курсор
        Cursor cursor = null;

        switch (view.getId()){
            //все записи
            case R.id.All_context:
                Log.d(LOG_TAG, "--- Все записи ---");
                cursor = mDatabase.query("myTable", null, null, null, null, null, null);
                break;
            //функция
            case R.id.Function_Button:
                Log.d(LOG_TAG, "--- Функция ");
                columns = new String[] {functionText};
                cursor = mDatabase.query("myTable", columns, null, null, null, null, null);
                break;
            //население больше чем
            case R.id.Population_Button:
                Log.d(LOG_TAG, "--- Насление больше ");
                selection = "people > ?";
                selectionArgs = new String[] {populationText};
                cursor = mDatabase.query("myTable", null, selection, selectionArgs, null, null, null);
                break;
            //Население по региону
            case R.id.Population_Group:
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                cursor = mDatabase.query("myTable", columns, null, null, groupBy, null, null);
                break;
            //Население по региону больше чем
            case R.id.Population_Group_Count:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + groupPopulationText + " ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + groupPopulationText;
                cursor = mDatabase.query("myTable", columns, null, null, groupBy, having, null);
                break;
            //Сортировка
            case R.id.Sorting_Button:
                //сортировка по
                switch (mRadioGroup.getCheckedRadioButtonId()){

                    //наименованию
                    case R.id.radio_Name:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    //населениею
                    case R.id.Radio_Population:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    //региону
                    case R.id.Radio_Region:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;

                }

                cursor = mDatabase.query("myTable", null, null, null, null, null, orderBy);
                break;
        }

        if (cursor != null){
            if (cursor.moveToFirst()){
                String str;
                do {
                    str = "";
                    for (String cn : cursor.getColumnNames()){
                        str = str.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } else Log.d(LOG_TAG, "Cursor is null");

        mDBHelper.close();

    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            //создаем таблицу с полями
            sqLiteDatabase.execSQL("create table myTable (" + "id integer primary key autoincrement, "
            + "name text, " + "people integer, " + "region text" + ") ;");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}

