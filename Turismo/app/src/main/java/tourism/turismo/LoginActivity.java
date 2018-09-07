package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_user;
    private EditText edt_password;
    private Button btn_enter;
    private TextView txt_new_account;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button btn_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        initComponents();
        txt_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(i);
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM LOGIN
     */
    private void initComponents() {
        edt_user = (EditText) findViewById(R.id.edt_user);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        txt_new_account = (TextView) findViewById(R.id.txt_new_count);
        btn_about =(Button) findViewById(R.id.btn_about);
    }


    /**
     * THIS METHOD INITIATES SESSION WITH THE USER WITH FIREBASE
     */
    private void loginUser() {
        final String email = edt_user.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Autenticando usuario...Espere por favor!...");
        progressDialog.show();
        //Loguin user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this,
                                                  CityActivity.class);
                            i.putExtra("user", email);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "Usario en sesión!!",
                                           Toast.LENGTH_LONG).show();
                            finish();
                        } else {//validating that user already exists
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(LoginActivity.this,
                                               "El usuario no existe.",
                                                 Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(LoginActivity.this,
                                                "Usuario no encontrado.",
                                                 Toast.LENGTH_LONG).show();
                            }

                        }
                        progressDialog.dismiss();
                    }
                });

    }

}
