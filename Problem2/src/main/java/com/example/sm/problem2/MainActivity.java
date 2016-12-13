package com.example.sm.problem2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyBaseAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> emp_list = new ArrayList<>();

        adapter = new MyBaseAdapter(this, emp_list);
        listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);

        Employee employee;

        switch (v.getId()){
            case R.id.btn_inc:
                // need something here
                employee = adapter.mData.get(adapter.selected_position);
                employee.increase();
                edit_salary.setText(employee.getSalary()+"");
                break;

            case R.id.btn_dec:
                // need something here
                employee = adapter.mData.get(adapter.selected_position);
                employee.decrease();
                edit_salary.setText(employee.getSalary()+"");
                break;

            case R.id.btn_store:
                // need something here
                adapter.mData.add(new Employee(edit_name.getText().toString(), Integer.parseInt(edit_age.getText().toString()), Integer.parseInt(edit_salary.getText().toString())));
                break;

            case R.id.btn_modify:
                employee = adapter.mData.get(adapter.selected_position);
                employee.setName(edit_name.getText().toString());
                employee.setAge(Integer.parseInt(edit_age.getText().toString()));
                employee.setSalary(Integer.parseInt(edit_salary.getText().toString()));
                break;

            case R.id.btn_delete:
                // need something here
                adapter.mData.remove(adapter.selected_position);
                break;
        }
        adapter.notifyDataSetChanged();
    }
}

interface Payment {
    void increase();
    void decrease();
}
