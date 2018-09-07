package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tourism.turismo.model.TourismPlace;
import tourism.turismo.list.TourismPlaceList;

public class TourismActivity extends AppCompatActivity {

    private EditText edt_name_place;
    private EditText edt_description_place;
    private Button btn_save;
    private DatabaseReference database_reference;
    private ListView list_view_place_tourism;
    private List<TourismPlace> tourisms = new ArrayList<TourismPlace>();
    public static String tourism_place_id;
    public static  String valoration;
    public String user = "";
    public String id_city = "";
    private Button btn_select_photo;
    private ImageButton btn_back_tourism;
    //  private StorageReference storageReference;
    // private static final int GALERY_INTENT = 1;
    private ImageView txt_name_photo;
    private EditText edt_latitude;
    private EditText edt_longitude;
    private ProgressDialog progressDialog;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);
        progressDialog = new ProgressDialog(TourismActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //storageReference = FirebaseStorage.getInstance().getReference();
        database_reference = FirebaseDatabase.getInstance().getReference("tourism_place");
        receiveData();//METHOD INVOKED TO RECEIVES DATA
        initComponents();//METHOD INVOKED TO INITIATE THE COMPONENTS

        //ACTION OF THE BUTTON TO SAVE OR UPDATE AN OBJECT
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String name_place = edt_name_place.getText().toString().trim();
                    String description = edt_description_place.getText().toString().trim();
                    String latitude = edt_latitude.getText().toString().trim();
                    String longitude = edt_longitude.getText().toString().trim();
                    if (TextUtils.isEmpty(valoration)) {
                        valoration = "1.0";
                    }
                    if (TextUtils.isEmpty(tourism_place_id)) {
                        String id = database_reference.push().getKey();
                        TourismPlace tourism_place = new TourismPlace(id, name_place, description,
                                                      id_city, latitude, longitude, valoration);
                        database_reference.child(id).setValue(tourism_place);
                        Toast.makeText(TourismActivity.this,
                                "Tourism place created successfully!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        database_reference
                                .child(tourism_place_id)
                                .child("name_place")
                                .setValue(name_place);
                        database_reference
                                .child(tourism_place_id)
                                .child("description_place")
                                .setValue(description);
                        database_reference
                                .child(tourism_place_id)
                                .child("city_id")
                                .setValue(id_city);
                        database_reference
                                .child(tourism_place_id)
                                .child("latitude")
                                .setValue(latitude);
                        database_reference
                                .child(tourism_place_id)
                                .child("longitude")
                                .setValue(longitude);
                        database_reference
                                .child(tourism_place_id)
                                .child("valoration")
                                .setValue(valoration);
                        Toast.makeText(TourismActivity.this,
                                "Tourism place update successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                    edt_name_place.setText(null);
                    edt_description_place.setText(null);
                    edt_latitude.setText(null);
                    edt_longitude.setText(null);
                    waiting(5000);
                }
            }
        });

        btn_back_tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TourismActivity.this,MenuActivity.class);
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
                Intent intent = new Intent( TourismActivity.this,
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
        if(edt_name_place.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa el nombre del lugar",
                    Toast.LENGTH_SHORT).show();
            edt_name_place.setFocusable(true);
            return false;
        }
        if(edt_description_place.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa la descripción del lugar",
                    Toast.LENGTH_SHORT).show();
            edt_description_place.setFocusable(true);
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
                tourisms.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    TourismPlace tourism = postSnapshot.getValue(TourismPlace.class);
                    tourisms.add(tourism);
                }
                TourismPlaceList tourism_adapter
                        = new TourismPlaceList(TourismActivity.this,
                        tourisms,database_reference, edt_name_place, edt_description_place, user,
                        id_city, edt_latitude, edt_longitude);
                list_view_place_tourism.setAdapter(tourism_adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     *  THE METHOD RECEIVES THE DATA WHEN A MAIN 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = extras.getString("user");
            id_city = extras.getString("id_city");
            Log.d("USER", user);
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM TOURISM PLACE
     */
    public void initComponents(){
        edt_description_place = (EditText) findViewById(R.id.edt_description_place);
        edt_name_place = (EditText) findViewById(R.id.edt_name_place);
        btn_save =(Button) findViewById(R.id.btn_save_tourism);
        list_view_place_tourism = (ListView) findViewById(R.id.list_view_tourism);
        btn_select_photo = (Button) findViewById(R.id.btn_select_photo);
        txt_name_photo =(ImageView) findViewById(R.id.txt_name_photo);
        btn_select_photo.setVisibility(View.GONE);
        txt_name_photo.setVisibility(View.GONE);
        btn_back_tourism =(ImageButton) findViewById(R.id.btn_back_tourism);
        edt_latitude = (EditText) findViewById(R.id.edt_latitude_place);
        edt_longitude =(EditText) findViewById(R.id.edt_longitude_place);
        btn_logout =(Button) findViewById(R.id.btn_logout_tourism);
        if (!user.equals("luis@gmail.com")){
            edt_name_place.setVisibility(View.GONE);
            edt_description_place.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
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
