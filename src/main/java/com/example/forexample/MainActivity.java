package com.example.forexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button button;
    TextView textView;
    EditText editText;
    ArrayList<String> tasks;
    ArrayAdapter<String> arrayAdapter;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);

        // Initialize DatabaseHandler
        dbHandler = new DatabaseHandler(this);

        // Load tasks from the database
        tasks = new ArrayList<>(dbHandler.getAllTasks());

        // Initialize the ArrayAdapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(arrayAdapter);

        // Set button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        // Set long click listener to delete an item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String task = tasks.get(position);
                dbHandler.deleteTask(task);
                tasks.remove(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Task removed: " + task, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void addTask() {
        String task = editText.getText().toString();
        if (!task.isEmpty()) {
            tasks.add(task);
            dbHandler.addTask(task);
            arrayAdapter.notifyDataSetChanged();
            Log.d("MainActivity", "Task added: " + task);
            editText.setText("");
            int count = arrayAdapter.getCount();
            String message = "Total number of tasks: " + count;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
