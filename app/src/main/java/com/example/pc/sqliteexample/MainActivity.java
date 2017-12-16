package com.example.pc.sqliteexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstname;
    private EditText lastname;
    private EditText birthdate;
    private EditText gender;
    private EditText idnumber;
    private Button add;
    private Button delete;
    private Button get;
    DatabaseHelper databaseHelper;
    PersonModel personModel;
    List<PersonModel> personData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        personModel = new PersonModel();








    }

    private void initView() {
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        idnumber = findViewById(R.id.idnumber);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        get = findViewById(R.id.get);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        get.setOnClickListener(this);
    }

    public void clear(){
        firstname.setText("");
        lastname.setText("");
        gender.setText("");
        birthdate.setText("");
        idnumber.setText("");
    }

    @Override
    public void onClick(View v) {
        if (!(firstname.getText().toString().isEmpty() && lastname.getText().toString().isEmpty()
                && birthdate.getText().toString().isEmpty() && gender.getText().toString().isEmpty()
                && idnumber.getText().toString().isEmpty())) {
            personModel.setIdNumber(Integer.parseInt(idnumber.getText().toString()));
            personModel.setFirstName(firstname.getText().toString());
            personModel.setLastName(lastname.getText().toString());
            personModel.setGender(gender.getText().toString());
            personModel.setBirthDate(birthdate.getText().toString());
        }

        switch (v.getId()) {
            case R.id.add:
                if (personModel != null && personModel.getIdNumber()!=0) {
                    long rowId = databaseHelper.addPersonData(personModel);
                    Toast.makeText(getApplicationContext(), Long.toString(rowId), Toast.LENGTH_SHORT).show();
                }
                clear();
                break;
            case R.id.delete:
                int rowCount = databaseHelper.deletePersonData(personModel.getIdNumber());
                Toast.makeText(getApplicationContext(), Integer.toString(rowCount), Toast.LENGTH_SHORT).show();
                clear();
                break;
            case R.id.get:
                if (databaseHelper.getPersonsData().get(0).getIdNumber()!=0) {
                    personData = databaseHelper.getPersonsData();
                    firstname.setText(personData.get(0).getFirstName());
                    lastname.setText(personData.get(0).getLastName());
                    gender.setText(personData.get(0).getGender());
                    birthdate.setText(personData.get(0).getBirthDate());
                    idnumber.setText(Integer.toString(personData.get(0).getIdNumber()));
                }
                Toast.makeText(this, Integer.toString(personData.size()), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
