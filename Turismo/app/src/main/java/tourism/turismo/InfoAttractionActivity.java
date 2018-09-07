package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tourism.turismo.model.Attraction;
import tourism.turismo.model.NightLife;

public class InfoAttractionActivity extends AppCompatActivity {

    private TextView txt_name_attraction_info;
    private TextView txt_description_attraction_info;
    private Button btn_how_to_get;
    private DatabaseReference database_reference;
    private List<Attraction> attractions = new ArrayList<Attraction>();
    public String user = "";
    public String id_attraction;
    public String id_city;
    private ImageButton btn_back;
    private String latitude;
    private String longitude;
    private ProgressDialog progressDialog;
    private RatingBar rating_bar;
    private Button btn_logout;
    private TextView txt_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_attraction);
        progressDialog = new ProgressDialog(InfoAttractionActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        database_reference = FirebaseDatabase.getInstance().getReference("attraction");
        initComponents();
        receiveData();

        txt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( InfoAttractionActivity.this,
                                           MenuActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoAttractionActivity.this,
                                           AttractionActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                finish();
            }
        });

        btn_how_to_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoAttractionActivity.this, Mapa.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("name_screen", "attraction");
                intent.putExtra("name", ""
                        + txt_name_attraction_info.getText().toString());
                intent.putExtra("description", ""
                        + txt_description_attraction_info.getText().toString());
                startActivity(intent);
                finish();
            }
        });
        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Iterator<Attraction> it = attractions.iterator();
                while (it.hasNext()) {
                    Attraction attraction = it.next();
                    if (attraction.getAttraction_id().equals(id_attraction)) {
                        progressDialog.setMessage("Espere un momento...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        database_reference.child(id_attraction).child("valoration").setValue("" + v);
                        waiting(5000);
                        Toast.makeText(InfoAttractionActivity.this,
                                "Gracias por valorar este lugar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Cerrando sesión...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent( InfoAttractionActivity.this,
                                           LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        });
        waiting(5000);
    }
    /**
     * THIS METHOD DO TIME WAIT FOR EXECUTED OR ADD MARKER THE MAP
     * @param milisegundos
     */
    public void waiting(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, milisegundos);
    }

    /**
     * THIS METHOD START IN THE MOMENT THAT THE CLASS IS EXECUTED
     */
    @Override
    protected void onStart() {
        super.onStart();
        database_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attractions.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Attraction attraction = postSnapshot.getValue(Attraction.class);
                    attractions.add(attraction);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    /**
     *  THE METHOD RECEIVES DATA WHEN A LIST OF ATTRACTIONS IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = extras.getString("user");
            id_city = extras.getString("id_city");
            id_attraction = extras.getString("id");
            txt_name_attraction_info.setText(extras.getString("name"));
            txt_description_attraction_info.setText(extras.getString("description"));
            latitude =extras.getString("latitude");
            longitude = extras.getString("longitude");
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM INFO ATTRACTION
     */
    public void initComponents(){
        txt_name_attraction_info =(TextView) findViewById(R.id.txt_name_attraction_info);
        txt_description_attraction_info =
                (TextView) findViewById(R.id.txt_description_attraction_info);
        btn_how_to_get =(Button) findViewById(R.id.btn_how_to_get_attraction);
        btn_back = (ImageButton) findViewById(R.id.btn_back_info_attraction);
        rating_bar =(RatingBar) findViewById(R.id.rating_bar_attraction);
        btn_logout =(Button) findViewById(R.id.btn_logout_attraction_info);
        txt_menu =(TextView) findViewById(R.id.txt_info_attraction);
        rating_bar.setRating(Float.parseFloat("1.0"));
    }

    /**
     * THE METHOD IMPLEMENTS THE RETURN ARROW TO THE PREVIOUS VIEW IN THE HEAD OF THE ACTIVITY
     */
    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
