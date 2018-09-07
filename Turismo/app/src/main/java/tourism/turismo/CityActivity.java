package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tourism.turismo.model.City;
import tourism.turismo.list.CityList;

public class CityActivity extends AppCompatActivity {

    private EditText edt_name_city;
    private Button btn_save;
    private DatabaseReference databaseReference;
    private ListView list_view_users;
    private List<City> cities = new ArrayList<City>();
    public static String city_id = "";
    public String user = "";
    private ProgressDialog progressDialog;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        progressDialog = new ProgressDialog(CityActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        receiveData();
        databaseReference = FirebaseDatabase.getInstance().getReference("city");
        initComponents();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String name_city = edt_name_city.getText().toString().trim();
                    if (TextUtils.isEmpty(city_id)) {
                        String id = databaseReference.push().getKey();
                        City city = new City(id, name_city);
                        databaseReference.child(id).setValue(city);
                        Toast.makeText(CityActivity.this, "City created successfully!",
                                      Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child(city_id).child("name").setValue(name_city);
                        Toast.makeText(CityActivity.this, "City update successfully!",
                                       Toast.LENGTH_SHORT).show();
                    }
                    edt_name_city.setText(null);
                    waiting(5000);
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Cerrando sesión...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent( CityActivity.this, LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        });
        waiting(5000);
    }


    /**
     * METHOD THAT VALIDATES THAT THE FIELDS ARE NOT EMPTY
     * @return
     */
    private boolean validateContent(){
        if(edt_name_city.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Ingresa el nombre de la ciudad",
                           Toast.LENGTH_SHORT).show();
            edt_name_city.setFocusable(true);
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cities.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    City city = postSnapshot.getValue(City.class);
                    cities.add(city);
                }
                CityList cityAdapter = new CityList(CityActivity.this, cities,
                                                   databaseReference,edt_name_city, user);
                list_view_users.setAdapter(cityAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     *  THE METHOD RECEIVES THE DATA WHEN A LIST OF CITIES 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = extras.getString("user");
            Log.d("USER", user);
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM CITY
     */
    public void initComponents(){
        edt_name_city = (EditText) findViewById(R.id.edt_name_city);
        btn_save =(Button) findViewById(R.id.btn_save);
        list_view_users = (ListView) findViewById(R.id.list_view_city);
        btn_logout =(Button) findViewById(R.id.btn_logout_city);
           if (!user.equals("luis@gmail.com")) {
               edt_name_city.setVisibility(View.GONE);
               btn_save.setVisibility(View.GONE);
           }
    }
}
