package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private double lat_place = 0.0;
    private double lng_place = 0.0;
    private String name_place = "";
    private double lat_my_location= 0.0;
    private double lng_my_location = 0.0;
    private TextView mensaje1;
    private TextView mensaje2;
    private String user;
    private String id_city;
    private ImageButton btn_back_activity;
    private String name_screen;
    private String description;
    private ProgressDialog progressDialog;
    private Button btn_logout;
    private TextView txt_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        initComponentsScreen();
        progressDialog = new ProgressDialog(Mapa.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                                             android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                             != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest
                                    .permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
        receiveDatas();
        final SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        fragment.getMapAsync(this);
        btn_back_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name_screen.equals("tourism")){
                    Intent intent = new Intent( Mapa.this,
                                               InfoTourismPlaceActivity.class);
                    intent.putExtra("id_city", id_city);
                    intent.putExtra("user", user);
                    intent.putExtra("latitude", ""+lat_place);
                    intent.putExtra("longitude", ""+lng_place);
                    intent.putExtra("name", name_place);
                    intent.putExtra("description", description);
                    startActivity(intent);
                    finish();
                }else if(name_screen.equals("pubRestaurant")){
                    Intent intent = new Intent( Mapa.this,
                                               InfoPubRestaurantActivity.class);
                    intent.putExtra("id_city", id_city);
                    intent.putExtra("user", user);
                    intent.putExtra("latitude", ""+lat_place);
                    intent.putExtra("longitude", ""+lng_place);
                    intent.putExtra("name", name_place);
                    intent.putExtra("description", description);
                    startActivity(intent);
                    finish();
                }else if(name_screen.equals("nightLife")){
                    Intent intent = new Intent( Mapa.this,
                                                InfoNightLifeActivity.class);
                    intent.putExtra("id_city", id_city);
                    intent.putExtra("user", user);
                    intent.putExtra("latitude", ""+lat_place);
                    intent.putExtra("longitude", ""+lng_place);
                    intent.putExtra("name", name_place);
                    intent.putExtra("description", description);
                    startActivity(intent);
                    finish();
                }else if(name_screen.equals("attraction")){
                    Intent intent = new Intent( Mapa.this,
                                               InfoAttractionActivity.class);
                    intent.putExtra("id_city", id_city);
                    intent.putExtra("user", user);
                    intent.putExtra("latitude", ""+lat_place);
                    intent.putExtra("longitude", ""+lng_place);
                    intent.putExtra("name", name_place);
                    intent.putExtra("description", description);
                    startActivity(intent);
                    finish();
                }else if(name_screen.equals("recomendation")){
                    Intent intent = new Intent( Mapa.this,
                                               InfoRecomendationActivity.class);
                    intent.putExtra("id_city", id_city);
                    intent.putExtra("user", user);
                    intent.putExtra("latitude", ""+lat_place);
                    intent.putExtra("longitude", ""+lng_place);
                    intent.putExtra("name", name_place);
                    intent.putExtra("description", description);
                    startActivity(intent);
                    finish();
                }
            }
        });

        txt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Mapa.this, MenuActivity.class);
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
                Intent intent = new Intent( Mapa.this, LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        });
    }

    /**
     * THIS METHOD DO TIME WAIT FOR EXECUTED OR ADD MARKER THE MAP
     * @param milisegundos
     */
    public void waiting2(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    /**
     * THIS METHOD INITIALIZE THE VARIABLES OF THE SCREEN MAP
     */
    public void initComponentsScreen(){
        mensaje1 = (TextView) findViewById(R.id.mensaje_id_mapa);
        mensaje2 = (TextView) findViewById(R.id.mensaje_id2_mapa);
        btn_back_activity = (ImageButton) findViewById(R.id.btn_back_activity);
        btn_logout =(Button) findViewById(R.id.btn_logout_map);
        txt_menu =(TextView) findViewById(R.id.txt_mapa);
        mensaje1.setVisibility(View.GONE);
        mensaje2.setVisibility(View.GONE);
    }

    /**
     * THIS METHOD RECEIVES DATA OF THE SCREEN PREVIOUS
     */
    public void receiveDatas(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat_place = Double.parseDouble(extras.getString("latitude"));
            lng_place = Double.parseDouble(extras.getString("longitude"));
            user = extras.getString("user");
            id_city = extras.getString("id_city");
            name_place = extras.getString("name");
            description = extras.getString("description");
            name_screen = extras.getString("name_screen");
            Log.d("USER", user);
        }
    }

    /**
     * THIS METHOD DO TIME WAIT FOR EXECUTED OR ADD MARKER THE MAP
     * @param milisegundos
     */
    public void waiting(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(lat_my_location, lng_my_location)).title("Mi ubicación"));
            map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(lat_my_location, lng_my_location), 18));
                addMarker();
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    /**
     * THIS METHOD CREATE A MAP
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        waiting(15000);
    }

    /**
     * THIS METHOD ADD MARKER IN A MAP OF THE POSITION THE LOCAL
     */
    public void addMarker(){
        map.addMarker(new MarkerOptions().position(new LatLng(lat_place,lng_place))
                .title(name_place));
        LatLng pos1,pos2;
        pos1 = new LatLng(lat_my_location,lng_my_location);
        pos2 = new LatLng(lat_place,lng_place);
        Log.d("MY LATITUDE" , ""+lat_my_location);
        map.addPolyline(new PolylineOptions().add(pos1,pos2).width(5).color(Color.RED));
    }

    /**
     * THIS METHOD START THE LOCATION ACTUAL
     */
    private void locationStart() {
        LocationManager location_manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Mapa.Localizacion local = new Mapa.Localizacion();
        local.setMainActivity(this);
        final boolean gps_enabled = location_manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gps_enabled) {
            Intent settings_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settings_intent);
        }
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,},
                    1000);
            return;
        }
        location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0, 0, (LocationListener) local);
        location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, (LocationListener) local);
        mensaje1.setText("Localización agregada");
        mensaje2.setText("");
    }

    /**
     * THIS METHOD VALIDATE THAT THE PERMISSION DON´T BE NULL
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    /**
     * THIS METHOD GET THE ADDRESS OF THE STREET FROM LATITUDE AND LONGITUDE
     * @param loc
     */
    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    mensaje2.setText("Mi direccion es: \n"+ address.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * HERE BEGINS THE CLASS LOCATION
     */
    public class Localizacion implements LocationListener {
        Mapa main_activity;

        public Mapa getMainActivity() {
            return main_activity;
        }
        public void setMainActivity(Mapa main_activity) {
            this.main_activity = main_activity;
        }

        /**
         * THIS METHOD IS EXECUTED EVERY TIME THE GPS RECEIVES NEW COORDINATES
         * DUE TI THE DETETCTION OF A CHAGE OF LOCATION
         * @param loc
         */
        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            lat_my_location =  loc.getLatitude();
            lng_my_location =   loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "+ loc.getLatitude()
                         + "\n Long = " + loc.getLongitude();
            mensaje1.setText(Text);
            this.main_activity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            mensaje1.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            mensaje1.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
