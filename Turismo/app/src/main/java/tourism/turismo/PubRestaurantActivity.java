package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tourism.turismo.list.PubRestaurantList;
import tourism.turismo.model.PubRestaurant;
public class PubRestaurantActivity extends AppCompatActivity {


    private Button btn_save_pub_restaurant;
    private EditText edt_name_pub_restaurant;
    private EditText edt_description_pub_restaurant;
    private DatabaseReference database_reference;
    private ListView list_view_pub_restaurant;
    private List<PubRestaurant> pubs_restaurants;
    private String user ="";
    private String id_city ="";
    public static String pub_restaurant_id;
    public static  String valoration;
    private ImageButton btn_back;
    private EditText edt_latitude;
    private EditText edt_longitude;
    private ProgressDialog progressDialog;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_restaurant);
        progressDialog = new ProgressDialog(PubRestaurantActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        pubs_restaurants = new ArrayList<PubRestaurant>();
        database_reference = FirebaseDatabase.getInstance().getReference("pub_restaurant");
        receiveData();//METHOD INVOKED TO RECEIVES DATA
        initComponents();//METHOD INVOKED TO INITIATE THE COMPONENTS

        //ACTION OF THE BUTTON TO SAVE OR UPDATE AN OBJECT
        btn_save_pub_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String name = edt_name_pub_restaurant.getText().toString().trim();
                    String description = edt_description_pub_restaurant.getText().toString().trim();
                    String latitude = edt_latitude.getText().toString().trim();
                    String longitude = edt_longitude.getText().toString().trim();
                    if (TextUtils.isEmpty(valoration)) {
                        valoration = "1.0";
                    }
                    if (TextUtils.isEmpty(pub_restaurant_id)) {
                        String id = database_reference.push().getKey();
                        PubRestaurant pub_restaurant = new PubRestaurant(id, name, description,
                                                       id_city, latitude, longitude, valoration);
                        database_reference.child(id).setValue(pub_restaurant);
                        Toast.makeText(PubRestaurantActivity.this,
                                "Pub or restaurant created successfully!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        database_reference
                                .child(pub_restaurant_id)
                                .child("name_pub_restaurant")
                                .setValue(name);
                        database_reference
                                .child(pub_restaurant_id)
                                .child("description_pub_restaurant")
                                .setValue(description);
                        database_reference
                                .child(pub_restaurant_id)
                                .child("id_city")
                                .setValue(id_city);
                        database_reference
                                .child(pub_restaurant_id)
                                .child("latitude")
                                .setValue(latitude);
                        database_reference
                                .child(pub_restaurant_id)
                                .child("longitude")
                                .setValue(longitude);
                        database_reference
                                .child(pub_restaurant_id)
                                .child("valoration")
                                .setValue(valoration);
                        pub_restaurant_id = null;
                        Toast.makeText(PubRestaurantActivity.this,
                                "Pub or restaurant updated successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                    edt_description_pub_restaurant.setText(null);
                    edt_name_pub_restaurant.setText(null);
                    edt_latitude.setText(null);
                    edt_longitude.setText(null);
                    waiting(5000);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PubRestaurantActivity.this,
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
                Intent intent = new Intent( PubRestaurantActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        });
        waiting(5000);
    }

    /**
     * METHOD THA VALIDATES THAT THE FIELDS AREN'T NO EMPTY
     * @return
     */
    private boolean validateContent(){
        if(edt_name_pub_restaurant.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa el nombre del lugar",
                    Toast.LENGTH_SHORT).show();
            edt_name_pub_restaurant.setFocusable(true);
            return false;
        }
        if(edt_description_pub_restaurant.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa la descripción del lugar",
                    Toast.LENGTH_SHORT).show();
            edt_description_pub_restaurant.setFocusable(true);
            return false;
        }
        if(edt_latitude.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa la latitud",
                    Toast.LENGTH_SHORT).show();
            edt_latitude.setFocusable(true);
            return false;
        }
        if(edt_longitude.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa la longitud",
                    Toast.LENGTH_SHORT).show();
            edt_longitude.setFocusable(true);
            return false;
        }
        return true;
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
                pubs_restaurants.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    PubRestaurant pub_restaurant = postSnapshot.getValue(PubRestaurant.class);
                    pubs_restaurants.add(pub_restaurant);
                }
                PubRestaurantList pub_restaurant_adapter = new PubRestaurantList(
                        PubRestaurantActivity.this, pubs_restaurants,database_reference,
                        edt_name_pub_restaurant,edt_description_pub_restaurant,user,id_city,
                        edt_latitude, edt_longitude);
                list_view_pub_restaurant.setAdapter(pub_restaurant_adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *  THE METHOD RECEIVES THE DATA WHEN A OF MAIN CLICKED
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
    public void initComponents(){
        btn_save_pub_restaurant = (Button) findViewById(R.id.btn_save_pub_restaurant);
        edt_name_pub_restaurant =(EditText) findViewById(R.id.edt_name_pub_restaurant);
        edt_description_pub_restaurant
                = (EditText) findViewById(R.id.edt_description_pub_restaurant);
        list_view_pub_restaurant = (ListView) findViewById(R.id.list_pub_restaurant);
        btn_back = (ImageButton) findViewById(R.id.btn_back_pub_restaurant);
        edt_latitude = (EditText) findViewById(R.id.edt_latitude_pub_restaurant);
        edt_longitude =(EditText) findViewById(R.id.edt_longitude_pub_restaurant);
        btn_logout =(Button) findViewById(R.id.btn_logout_restaurant);
        if (!user.equals("luis@gmail.com")){
            btn_save_pub_restaurant.setVisibility(View.GONE);
            edt_description_pub_restaurant.setVisibility(View.GONE);
            edt_name_pub_restaurant.setVisibility(View.GONE);
            edt_longitude.setVisibility(View.GONE);
            edt_latitude.setVisibility(View.GONE);
        }
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
