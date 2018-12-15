package com.example.navadon.androidnamecard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navadon.androidnamecard.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ValueEventListener valueEventListener;
    private ProgressDialog progressDialog;
    ActivityMainBinding binding;
    private HashMap<String, Drawable> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        viewModel = new MainViewModel();
        teams = new HashMap<>();
        teams.put("560611024", ContextCompat.getDrawable(MainActivity.this, R.drawable.kim));
        teams.put("580611030", ContextCompat.getDrawable(MainActivity.this, R.drawable.billy));
        teams.put("580611024", ContextCompat.getDrawable(MainActivity.this, R.drawable.dew));
        teams.put("570611040", ContextCompat.getDrawable(MainActivity.this, R.drawable.yok));

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setViewmodel(viewModel);
        getUser("kim");
    }

    private void getUser(String name){
        if(valueEventListener != null){
            FirebaseDatabase.getInstance().getReference().removeEventListener(valueEventListener);
            valueEventListener = null;
        }

        progressDialog.show();
        valueEventListener = FirebaseDatabase.getInstance().getReference().child("users").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewModel = dataSnapshot.getValue(MainViewModel.class);
                binding.txtName.setText(viewModel.getName());
                binding.txtStudentID.setText(viewModel.getStudentid());
                binding.txtID.setText(viewModel.getCitizenid());
                binding.txtFaculty.setText(viewModel.getFaculty());

                ImageView imageView = findViewById(R.id.image);
                imageView.setImageDrawable(teams.get(viewModel.getStudentid()));

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View v) {
        TextView textView = findViewById(R.id.txtStudent_ID);
        String currentStudentId = textView.getText().toString();

       switch (currentStudentId){
           case "560611024":
               getUser("billy");
               break;
           case "580611030":
               getUser("dew");
               break;
           case "580611024":
               getUser("yok");
               break;
           case "570611040":
               getUser("kim");
               break;
       }
    }

    private void changePage(Class i){
        startActivity(new Intent(this,i));
    }

    // To hide Android soft keyboard
    private void hideKeyboardInput(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


}
