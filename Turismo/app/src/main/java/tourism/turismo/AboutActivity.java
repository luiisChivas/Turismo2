package tourism.turismo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutActivity extends AppCompatActivity {

    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initComponents();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( AboutActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM RESTAURANT
     */
    private void initComponents(){
        btn_back =(ImageButton) findViewById(R.id.btn_back_about);
    }
}
