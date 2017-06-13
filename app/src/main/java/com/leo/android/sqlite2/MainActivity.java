package com.leo.android.sqlite2;

import android.content.Context;
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

    private SQLiteDatabase db;
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
        mDBHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View view) {

    }

    class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            //создаем таблицу с полями
            db.execSQL("create tabble myTable (" + "id integer primary key autoincrement, "
            + "name text, " + "people integer, " + "region text" + ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}

