package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tourism.turismo.list.RecomendationList;
import tourism.turismo.model.Attraction;
import tourism.turismo.model.NightLife;
import tourism.turismo.model.PubRestaurant;
import tourism.turismo.model.Recomendation;
import tourism.turismo.model.TourismPlace;

public class RecomendationActivity extends AppCompatActivity {
    private DatabaseReference database_reference_attraction;
    private DatabaseReference database_reference_tourism;
    private DatabaseReference database_reference_pub_restaurant;
    private DatabaseReference database_reference_night_life;
    private ListView list_view_recomendation;
    private List<TourismPlace> tourisms_places;
    private List<Attraction> attractions;
    private List<Recomendation> recomendations;
    private List<PubRestaurant> pubs_restaurants;
    private List<NightLife> nights_lifes;
    private String user = "";
    private String id_city = "";
    private ProgressDialog progressDialog;
    private ImageButton btn_back;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);
        progressDialog = new ProgressDialog(RecomendationActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        receiveData();
        initComponent();
        attractions = new ArrayList<Attraction>();
        tourisms_places = new ArrayList<TourismPlace>();
        recomendations = new ArrayList<Recomendation>();
        pubs_restaurants = new ArrayList<PubRestaurant>();
        nights_lifes = new ArrayList<NightLife>();
        database_reference_attraction
                = FirebaseDatabase.getInstance().getReference("attraction");
        database_reference_tourism
                = FirebaseDatabase.getInstance().getReference("tourism_place");
        database_reference_night_life
                = FirebaseDatabase.getInstance().getReference("night_life");
        database_reference_pub_restaurant
                = FirebaseDatabase.getInstance().getReference("pub_restaurant");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RecomendationActivity.this,
                        MenuActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Cerrando sesión...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent( RecomendationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        });
        waiting(5000);
    }

    /**
     * THIS METHOD START IN THE MOMENT THAT THE CLASS IS EXECUTED
     */
    @Override
    protected void onStart() {
        super.onStart();
        recomendations.clear();
        database_reference_attraction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attractions.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Attraction attraction = postSnapshot.getValue(Attraction.class);
                    attractions.add(attraction);
                }
                for (Attraction attraction : attractions) {
                    Recomendation recomendation = new Recomendation(attraction.getAttraction_id(),
                            attraction.getName_attraction(), attraction.getDescription_attraction(),
                            attraction.getId_city(), attraction.getLatitude(),
                            attraction.getLongitude(), attraction.getValoration());
                    recomendations.add(recomendation);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database_reference_pub_restaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pubs_restaurants.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    PubRestaurant pub_restaurant = postSnapshot.getValue(PubRestaurant.class);
                    pubs_restaurants.add(pub_restaurant);
                }
                for (PubRestaurant pub_restaurant : pubs_restaurants) {
                    Recomendation recomendation =
                            new Recomendation(pub_restaurant.getId_pub_restaurant(),
                            pub_restaurant.getName_pub_restaurant(),
                            pub_restaurant.getDescription_pub_restaurant(),
                            pub_restaurant.getId_city(), pub_restaurant.getLatitude(),
                            pub_restaurant.getLongitude(), pub_restaurant.getValoration());
                    recomendations.add(recomendation);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database_reference_night_life.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nights_lifes.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    NightLife night_life = postSnapshot.getValue(NightLife.class);
                    nights_lifes.add(night_life);
                }
                for (NightLife night_life : nights_lifes) {
                    Recomendation recomendation = new Recomendation(night_life.getNight_life_id(),
                            night_life.getName_night_life(),
                            night_life.getDescription_night_life(),
                            night_life.getId_city(), night_life.getLatitude(),
                            night_life.getLongitude(), night_life.getValoration());
                    recomendations.add(recomendation);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database_reference_tourism.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tourisms_places.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    TourismPlace tourism_place = postSnapshot.getValue(TourismPlace.class);
                    tourisms_places.add(tourism_place);
                }
                for (TourismPlace tourism_place : tourisms_places) {
                    Recomendation recomendation =
                            new Recomendation(tourism_place.getTourism_id(),
                                    tourism_place.getName_place(),
                                    tourism_place.getDescription_place(),
                                    tourism_place.getCity_id(), tourism_place.getLatitude(),
                                    tourism_place.getLongitude(), tourism_place.getValoration());
                    recomendations.add(recomendation);
                }

                RecomendationList recomendation_adapter =
                        new RecomendationList(RecomendationActivity.this,
                        recomendations, user,id_city);
                list_view_recomendation.setAdapter(recomendation_adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
     *  THE METHOD RECEIVES THE DATA WHEN A LIST OF CITIES 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = extras.getString("user");
            id_city = extras.getString("id_city");
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM PUB AND RESTAURANT
     */
    public void initComponent(){
        list_view_recomendation =(ListView) findViewById(R.id.list_recomendation);
        btn_back = (ImageButton) findViewById(R.id.btn_back_recomendation);
        btn_logout =(Button) findViewById(R.id.btn_logout_recomendation);
    }
}
