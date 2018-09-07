package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class InfoRecomendationActivity extends AppCompatActivity {

    private TextView txt_name;
    private TextView txt_description;
    private Button btn_how_to_get;
    public String user = "";
    public String id_place;
    public String id_city;
    private ImageButton btn_back;
    private String latitude;
    private String longitude;
    private ProgressDialog progressDialog;
    private Button btn_logout;
    private TextView txt_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_recomendation);
        progressDialog = new ProgressDialog(InfoRecomendationActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        initComponents();
        receiveData();
        txt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( InfoRecomendationActivity.this,
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
                Intent intent = new Intent( InfoRecomendationActivity.this,
                                            RecomendationActivity.class);
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
                Intent intent = new Intent( InfoRecomendationActivity.this,
                                           Mapa.class);
                intent.putExtra("id_city", id_city);
                intent.putExtra("user", user);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("name_screen", "recomendation");
                intent.putExtra("name", ""+txt_name.getText().toString());
                intent.putExtra("description", ""+txt_description.getText().toString());
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
                Intent intent = new Intent( InfoRecomendationActivity.this,
                                            LoginActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
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
     *  THE METHOD RECEIVES DATA WHEN A LIST OF RECOMENDATIONS IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = extras.getString("user");
            id_city = extras.getString("id_city");
            id_place = extras.getString("id");
            txt_name.setText(extras.getString("name"));
            txt_description.setText(extras.getString("description"));
            latitude =extras.getString("latitude");
            longitude = extras.getString("longitude");
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM INFO RECOMENDATIONS
     */
    public void initComponents(){
        txt_name =(TextView) findViewById(R.id.txt_name_recomendation);
        txt_description = (TextView) findViewById(R.id.txt_description_recomendation);
        btn_how_to_get =(Button) findViewById(R.id.btn_how_to_get_recomendation);
        btn_back = (ImageButton) findViewById(R.id.btn_back_info_recomendation);
        btn_logout =(Button) findViewById(R.id.btn_logout_recomendation_info);
        txt_menu =(TextView) findViewById(R.id.txt_info_recomendation);
    }

}
