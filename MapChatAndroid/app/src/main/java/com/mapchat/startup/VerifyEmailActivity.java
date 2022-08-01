package com.mapchat.startup;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapchat.R;
import com.mapchat.dashboard.DashboardActivity;
import com.mapchat.storage.DataProvider;
import com.mapchat.storage.models.Profile;
import com.mapchat.webendpoint.WebGatewayService;
import com.mapchat.webendpoint.parameterbeans.VerifyEmailParam;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailActivity extends AppCompatActivity {

    /**
     * onCreate method for creation of the xml object
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyemail);

        Bundle b = getIntent().getExtras();
        final String name = b.getString("Name");
        final String emailId = b.getString("EmailId");

        final EditText edtVerificationNo = findViewById(R.id.edtVerfificationNo);
        final Button btnVerfify = findViewById(R.id.btnVerify);
        final ProgressBar prgLoading = findViewById(R.id.prgLoading);

        final AppCompatActivity ctx = this;

        /**
         * onClick listener identifies invalid user input
         * catches a wrong verification number input
         */
        btnVerfify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vNumber = edtVerificationNo.getText().toString().trim();
                if (vNumber == null || vNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "OTP Number is not Given!", Toast.LENGTH_SHORT).show();
                    edtVerificationNo.requestFocus();
                    return;
                }

                VerifyEmailParam vem = new VerifyEmailParam();
                vem.setEmailId(emailId);
                vem.setOtp(vNumber);

                prgLoading.setVisibility(View.VISIBLE);

                WebGatewayService.getInstance().getGateWayService().verifyEmail(vem).enqueue(new Callback<Profile>() {

                    /**
                     * saves the profile to the sqlite database
                     * loads profile to the dashboard
                     *
                     * @param call
                     * @param response
                     */
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Profile p = response.body();
                        if (p != null) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            //Save Profile to sqlite database so that next time loads directly to dashboard
                            ContentValues cv = new ContentValues();
                            cv.put(Profile.PROFILE_NAME, name);
                            cv.put(Profile.EMAIL_ID, emailId);
                            Uri ret = getContentResolver().insert(DataProvider.PROFILE_URI, cv);

                            //navigate to dashboard actvity
                            Intent i = new Intent(ctx, DashboardActivity.class);
                            ctx.startActivity(i);
                            ctx.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                        prgLoading.setVisibility(View.GONE);
                    }

                    /**
                     * displays error message when the application fails to acquire a profile
                     *
                     * @param call
                     * @param t
                     */
                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();

                        prgLoading.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
