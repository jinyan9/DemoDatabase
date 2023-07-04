package sg.edu.rp.c346.id22022452.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;
    ArrayList<String> taskList;
    ArrayList<Task> taskList1;
    ArrayAdapter<Task> aa;
    int insertCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the UI elements
        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);
        // Initialize the taskList (for ArrayList<Task> getTasks) and ArrayAdapter
        taskList1 = new ArrayList<>();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList1);
        lv.setAdapter(aa);
        // Set the onClickListener for the insert button
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the insert counter
                insertCounter++;
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);
                // Insert a task
                db.insertTask("Submit RJ", "25 Apr 2021");
                // Retrieve tasks from the database
                ArrayList<Task> tasks = db.getTasks();
                db.close();
                // Add tasks to the taskList
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    String taskString = (insertCounter + i) + " (" + task.getId() + ")\n" +
                            task.getDescription() + "\n" +
                            task.getDate() + "\n" + "MainActivity.java";
                    taskList.add(taskString);
                }
                // Notify the ArrayAdapter that the data has changed
                aa.notifyDataSetChanged();
            }
        });
        // Set the onClickListener for the get tasks button
        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);
                // Retrieve tasks from the database
                ArrayList<String> data = db.getTaskContent();
                ArrayList<Task> data1 = db.getTasks();
                db.close();
                // Display the retrieved tasks in the TextView
                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);
                // Clear the current taskList
                taskList1.clear();
                //To get all objects from ArrayList<Task> getTasks (addALL is important)
                taskList1.addAll(data1);
                // Notify the ArrayAdapter that the data has changed
                aa.notifyDataSetChanged();
            }
        });
    }
}