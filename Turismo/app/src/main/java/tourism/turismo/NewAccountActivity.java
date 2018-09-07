package tourism.turismo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class NewAccountActivity extends AppCompatActivity {
    private EditText edt_name_user;
    private EditText edt_email;
    private EditText edt_password_new;
    private EditText edt_password_confirm;
    private Button btn_register;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageButton btn_back_new_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        initComponents();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });
        btn_back_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( NewAccountActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * THIS METHOD CREATE A NEW USER FOR THE APPLICATION
     */
    private void signupUser() {
        String name =edt_name_user.getText().toString().trim();
        final String email = edt_email.getText().toString().trim();
        String password = edt_password_new.getText().toString().trim();
        String password_confirm = edt_password_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Ingrese un nombre usuario",
                           Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese un correo electrónico",
                           Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe ingresar una contraseña",
                            Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password_confirm)) {
            Toast.makeText(this, "Por favor confirme la contraseña",
                           Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals(password_confirm)){
            progressDialog.setMessage("Registrando al usuario...Por favor espere...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(NewAccountActivity.this, "Usuario: "
                                        + email + " registrado correctamente!!",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(NewAccountActivity.this,
                                        LoginActivity.class); //ProfileActivity
                                startActivity(intent);
                            } else {
                                if (task.getException() //validating that user already exists
                                        instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(NewAccountActivity.this,
                                            "Esta cuenta no existe", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(NewAccountActivity.this,
                                            "No se pudo registrar el usuario",
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                            progressDialog.dismiss();
                        }
                    });
        }else{
            Toast.makeText(NewAccountActivity.this, "No coiciden las contraseñas",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * THIS METHOD INITIALIZE THE COMPONENTS OF THE SCREM NEW ACCOUNT
     */
    private void initComponents() {
        edt_name_user = (EditText) findViewById(R.id.edt_name_user);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password_new =(EditText) findViewById(R.id.edt_password_new);
        edt_password_confirm = (EditText) findViewById(R.id.edt_confirm_password);
        btn_register = (Button) findViewById(R.id.btn_register_user);
        btn_back_new_account =(ImageButton) findViewById(R.id.btn_back_new_account);
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

