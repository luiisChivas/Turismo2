package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import tourism.turismo.model.City;
import tourism.turismo.model.CityId;

public class MenuActivity extends AppCompatActivity {

    private Button btn_tourism;
    private Button btn_pub_restaurant;
    private Button btn_night_life;
    private Button btn_attraction;
    private String id_city = "";
    private String user = "";
    private ImageButton btn_back;
    private ProgressDialog progressDialog;
    private Button btn_recomendation;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        progressDialog = new ProgressDialog(MenuActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        initComponents();
        receiveData();
        btn_tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuActivity.this,TourismActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });

        btn_pub_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuActivity.this,
                        PubRestaurantActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        btn_attraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuActivity.this,
                        AttractionActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        btn_night_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuActivity.this,
                        NightLifeActivity.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuActivity.this,
                        CityActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        btn_recomendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MenuActivity.this,
                        RecomendationActivity.class);
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
                progressDialog.dismiss();
                finish();
                Intent intent = new Intent( MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        waiting(2000);
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
     *  THE METHOD RECEIVES DATA WHEN A LIST OF CITIES IS CLICKED
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
     * THIS METHOD INITIALIZE THE VARIABLES OF THE SCREEN MAIN
     */
    public void initComponents(){
        btn_tourism = (Button) findViewById(R.id.btn_tourism_menu);
        btn_pub_restaurant = (Button) findViewById(R.id.btn_pub_restaurant_menu);
        btn_attraction = (Button) findViewById(R.id.btn_attraction_menu);
        btn_night_life = (Button) findViewById(R.id.btn_nightLife_menu);
        btn_back = (ImageButton) findViewById(R.id.btn_back_main);
        btn_recomendation =(Button) findViewById(R.id.btn_recomendation);
        btn_logout =(Button) findViewById(R.id.btn_logout_menu);
        if(user.equals("")){
            CityId id = new CityId();
            id_city = id.getCity_id();
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
