package gykizmir.com.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class StorageActivity extends AppCompatActivity {

    private Button uploadBtn, selectImgBtn;
    private ImageView uploadedImg;
    private StorageReference databaseReference;
    private final int IMG_REQUEST_CODE = 82;
    private Uri selectedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        selectImgBtn = (Button) findViewById(R.id.secBtn);
        uploadBtn = (Button) findViewById(R.id.yukleBtn);
        uploadedImg = (ImageView) findViewById(R.id.image);

        databaseReference = FirebaseStorage.getInstance().getReference();

        selectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resmiSec();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resmiYukle();
            }
        });

    }

    public void resmiSec(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Yuklenecek resmi sec"), IMG_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedData = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedData);
                uploadedImg.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void resmiYukle(){
        if(selectedData != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Yukleniyor...");
            progressDialog.show();

            StorageReference storageReference = databaseReference.child(UUID.randomUUID().toString());
            storageReference.putFile(selectedData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Yukleme Tamamlandi", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Yukleme sirasinda hata olustu", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            double yuklenmeOrani = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Yuklenen : %" + (int) yuklenmeOrani);
                        }
            });

        }

    }
}
