package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText task1,task2,task3,date;
    Button btn77;
    TextView names,phones,dates;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main09);


        task1 = findViewById(R.id.task1);
        task2=findViewById(R.id.task2);
        task3=findViewById(R.id.task3);
        date=findViewById(R.id.date);

        btn77=findViewById(R.id.btn77);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btn77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String task1 = task1.getText().toString().trim();
                String task2= task2.getText().toString().trim();
                String task3= task3.getText().toString();
                String date= dates.getText().toString();




                //add to list from user
                fAuth.createUserWithEmailAndPassword(task2,task3,dates).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentreference=fstore.collection("users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("task1",task1);
                            user.put("task2",task2);
                            user.put("task3",task3);
                            user.put("date",date);
                            documentreference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG","onSuccess:user Profile is created for"+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity2.class));

                        }else {

                            Toast.makeText(MainActivity.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        btn77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });


    }
}