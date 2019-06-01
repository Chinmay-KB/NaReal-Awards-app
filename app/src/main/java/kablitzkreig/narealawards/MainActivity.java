package kablitzkreig.narealawards;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    List<String> list = new ArrayList<>();
    TextView titleTextView;
    ImageView refreshImageView;
    ConstraintLayout certificateLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        certificateLayout=(ConstraintLayout)findViewById(R.id.certificateCL);
        fetchTitleList();
        titleTextView=findViewById(R.id.titleTV);
        refreshImageView=findViewById(R.id.refreshIV);


    }

    public void newRandom(View view){
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clock_rotate);
        refreshImageView.startAnimation(aniRotate);
        Random randomNo=new Random();
        int randomTitle=randomNo.nextInt(list.size());
        randomTitle=(randomTitle==list.size())?randomTitle--:randomTitle;
        titleTextView.setText(list.get(randomTitle));
    }

    public void exportCertificate(View v){
    }

    public void fetchTitleList(){
        db.collection("titles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getString("title"));
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(MainActivity.this,"Not working",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

   }
