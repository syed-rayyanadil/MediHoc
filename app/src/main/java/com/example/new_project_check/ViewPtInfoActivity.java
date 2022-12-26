package com.example.new_project_check;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewPtInfoActivity extends AppCompatActivity {

    private ImageView dispic;
    private TextView dis, treat, dname, hname, dat, aget;
    private Button back;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pt_info);

        dispic = findViewById(R.id.ivProfilePic2);
  //      pict = findViewById(R.id.ivtreatPic);
        dis = findViewById(R.id.tvDisease);
        treat = findViewById(R.id.tvtreatment);
        dname = findViewById(R.id.tvdocname);
        hname = findViewById(R.id.tvhospname);
        dat = findViewById(R.id.tvdate);
        aget = findViewById(R.id.tvAgetreat);
        back = (Button)findViewById(R.id.btnBack);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(dispic);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               /* RecordAddClass record = dataSnapshot.getValue(RecordAddClass.class);
                dis.setText("Disease: " + record.getDisease());
                dis.setText("" +record.getDisease());
                treat.setText("Treatment: " +record.getTreat());
                dname.setText("Doctor Name: " + record.getDoctorname());
                hname.setText("Hospital Name: " + record.getHospanme());
                dat.setText("Date of treatment: " + record.getDatetr());
                aget.setText("Age at the time treatment: " + record.getAgetr())*/;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewPtInfoActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });
    }

    private void Back(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ViewPtInfoActivity.this, PatientPortalActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}