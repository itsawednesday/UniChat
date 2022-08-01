package com.mapchat.startup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapchat.R;
import com.mapchat.dashboard.DashboardActivity;
import com.mapchat.storage.DataProvider;
import com.mapchat.storage.models.Profile;
import com.mapchat.webendpoint.WebGatewayService;
import com.mapchat.webendpoint.parameterbeans.CredentialParam;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Creation of the login activity xml file
 * Identifies and responds to invalid user inputs
 *
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText edtUsername = findViewById(R.id.edtEmail);
        final EditText edtPassword = findViewById(R.id.edtPassword);
        final Button btnSignin = findViewById(R.id.btnSignin);
        final ProgressBar prgLoading = findViewById(R.id.prgLoading);
        final TextView txvSignup = findViewById(R.id.txvSignup);

        final AppCompatActivity ctx = this;

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsername.getText().toString().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Incorrect Username", Toast.LENGTH_SHORT).show();
                    edtUsername.requestFocus();
                    return;
                }

                if (edtPassword.getText().toString().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                    return;
                }

                final String username = edtUsername.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                prgLoading.setVisibility(View.VISIBLE);

                CredentialParam cred = new CredentialParam(username, password);
                WebGatewayService.getInstance().getGateWayService().getProfile(cred).enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Profile p = response.body();

                        if (p != null) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            //Store profile data to sql database so that when load next time open direct to dashboard
                            ContentValues cv = new ContentValues();
                            cv.put(Profile.PROFILE_NAME, p.getProfileName());
                            cv.put(Profile.EMAIL_ID, username);
                            Uri ret = getContentResolver().insert(DataProvider.PROFILE_URI, cv);

                            Intent i = new Intent(ctx, DashboardActivity.class);
                            ctx.startActivity(i);
                            ctx.finish();
                        } else {

                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                        prgLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error !" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        prgLoading.setVisibility(View.GONE);
                    }
                });
            }
        });

        txvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, SignUpActivity.class);
                ctx.startActivity(i);
            }
        });


        Cursor c = getContentResolver().query(DataProvider.PROFILE_URI, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.close();

            Intent i = new Intent(this, DashboardActivity.class);
            this.startActivity(i);
            this.finish();
        }
    }
}
