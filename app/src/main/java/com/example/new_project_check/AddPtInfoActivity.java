package com.example.new_project_check;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddPtInfoActivity extends AppCompatActivity {

    Button ch,up;
    EditText txtDis,txtTreat,txtDocName,txtHosName,txtDate,txtAge;
    Datas datas;
    ImageView img;
    StorageReference mStorageRef;
    DatabaseReference dbreff;
    private StorageTask uploadTask;
    public Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pt_info);
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");
        dbreff=FirebaseDatabase.getInstance().getReference().child("Datas");
        ch=(Button)findViewById(R.id.btnchoose);
        up=(Button)findViewById(R.id.btnUpload);
        img=(ImageView)findViewById(R.id.imgview);

        txtDis=(EditText)findViewById(R.id.txtDis);
        txtTreat=(EditText)findViewById(R.id.txtTreat);
        txtDocName=(EditText)findViewById(R.id.txtDocName);
        txtHosName=(EditText)findViewById(R.id.txtHosName);
        txtDate=(EditText)findViewById(R.id.txtDate);
        txtAge=(EditText)findViewById(R.id.txtAge);
        datas=new Datas();

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask!= null && uploadTask.isInProgress())
                {
                    Toast.makeText(AddPtInfoActivity.this,"Upload in Progress",Toast.LENGTH_LONG).show();
                }
                else{
                    Fileuploader();
                }
            }
        });


    }

    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader()
    {
        String imageid;
        imageid =System.currentTimeMillis()+"."+getExtension(imguri);
        datas.setDisesae(txtDis.getText().toString().trim());
        datas.setTreatment(txtTreat.getText().toString().trim());
        datas.setDocName(txtDocName.getText().toString().trim());
        datas.setHopName(txtHosName.getText().toString().trim());
        datas.setDate1(txtDate.getText().toString().trim());
        datas.setImageid(imageid);
        int a=Integer.parseInt(txtAge.getText().toString().trim());
        datas.setAgeee(a);
        dbreff.push().setValue(datas);

        StorageReference Ref = mStorageRef.child(imageid);

        uploadTask= Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(AddPtInfoActivity.this,"Image Successfully Uploaded",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void Filechooser()
    {
        Intent intent=new Intent();
        intent.setType("image/ ");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            img.setImageURI(imguri);
        }
    }
}