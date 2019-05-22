package kablitzkreig.narealawards;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    List<String> list = new ArrayList<>();
    TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchTitleList();
        titleTextView=findViewById(R.id.titleTV);


    }

    public void newRandom(View view){
        Random randomNo=new Random();
        int randomTitle=randomNo.nextInt(list.size());
        randomTitle=(randomTitle==list.size())?randomTitle--:randomTitle;
        titleTextView.setText(list.get(randomTitle));
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
