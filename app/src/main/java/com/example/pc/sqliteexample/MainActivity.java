package com.example.pc.sqliteexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstname;
    private EditText lastname;
    private EditText birthdate;
    private EditText gender;
    private EditText idnumber;
    DatabaseHelper databaseHelper;
    PersonModel personModel;
    List<PersonModel> personData;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        personData = new ArrayList<>();

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initView() {
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        idnumber = findViewById(R.id.idnumber);

        Button add = findViewById(R.id.add);
        Button delete = findViewById(R.id.delete);
        Button get = findViewById(R.id.get);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        get.setOnClickListener(this);

        Button addRealm = findViewById(R.id.addRealm);
        Button deleteRealm = findViewById(R.id.deleteRealm);
        Button getRealm = findViewById(R.id.getRealm);
        addRealm.setOnClickListener(this);
        deleteRealm.setOnClickListener(this);
        getRealm.setOnClickListener(this);
    }

    public void clear(){
        firstname.setText("");
        lastname.setText("");
        gender.setText("");
        birthdate.setText("");
        idnumber.setText("");
    }

    public boolean notEmpty(){
        return (!(firstname.getText().toString().isEmpty()
                && lastname.getText().toString().isEmpty()
                && birthdate.getText().toString().isEmpty()
                && gender.getText().toString().isEmpty()
                && idnumber.getText().toString().isEmpty()
                && idnumber.getText().toString().isEmpty()));
    }


    public void initData(){
        if (notEmpty()) {
            personModel = new PersonModel();
            personModel.setIdNumber(Integer.parseInt(idnumber.getText().toString()));
            personModel.setFirstName(firstname.getText().toString());
            personModel.setLastName(lastname.getText().toString());
            personModel.setGender(gender.getText().toString());
            personModel.setBirthDate(birthdate.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add:
                initData();
                if (personModel != null) {
                    databaseHelper.addPersonData(personModel);
                    Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                }
                clear();
                break;

            case R.id.addRealm:
                initData();
                if (personModel != null) {
                    realm.beginTransaction();
                    realm.copyToRealm(personModel);
//                    PersonModel person = realm.createObject(PersonModel.class,personModel.getIdNumber());
//                    person.setBirthDate(personModel.getBirthDate());
//                    person.setGender(personModel.getGender());
//                    person.setLastName(personModel.getLastName());
//                    person.setFirstName(personModel.getFirstName());
                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                }
                clear();
                break;

            case R.id.delete:
                databaseHelper.deleteAll();
                clear();
                break;

            case R.id.deleteRealm:
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                clear();
                break;

            case R.id.get:
                if (databaseHelper.getPersonsData().size()>0) {
                    personData = databaseHelper.getPersonsData();
                    firstname.setText(personData.get(0).getFirstName());
                    lastname.setText(personData.get(0).getLastName());
                    gender.setText(personData.get(0).getGender());
                    birthdate.setText(personData.get(0).getBirthDate());
                    idnumber.setText(Integer.toString(personData.get(0).getIdNumber()));
                }
                Toast.makeText(this, Integer.toString(personData.size()), Toast.LENGTH_SHORT).show();
                break;

            case R.id.getRealm:
                RealmResults<PersonModel> personDataResult = realm.where(PersonModel.class).findAll();
                if (personDataResult.size()>0) {
                    firstname.setText(personDataResult.get(0).getFirstName());
                    lastname.setText(personDataResult.get(0).getLastName());
                    gender.setText(personDataResult.get(0).getGender());
                    birthdate.setText(personDataResult.get(0).getBirthDate());
                    idnumber.setText(Integer.toString(personDataResult.get(0).getIdNumber()));
                }
                Toast.makeText(this, Integer.toString(personDataResult.size()), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
