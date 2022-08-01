package com.mapchat.startup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapchat.R;
import com.mapchat.webendpoint.WebGatewayService;
import com.mapchat.webendpoint.parameterbeans.ProfileRegistrationParam;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        /** On create tells the code where to look for the correct layout for the needed activity. */

        final EditText edtName = findViewById(R.id.edtName);
        final EditText edtEmailId = findViewById(R.id.edtEmail);
        final EditText edtPassword = findViewById(R.id.edtPassword);
        final EditText edtCPassword = findViewById(R.id.edtCPassword);
        final Button btnSignup = findViewById(R.id.btnSignup);
        final ProgressBar prgLoading = findViewById(R.id.prgLoading);


        final Context ctx = this;
/** calls the onClick method to communicate what happens when the button is clicked. */
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Calls that if the name does not match with a piece of data in the document then the code returns an error to the user. */
                if (edtName.getText().toString().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Incorrect Name", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                    return;
                }

                /** Calls that if the email does not match with a piece of data in the document then the code returns an error to the user. */
                if (edtEmailId.getText().toString().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Incorrect Email Id", Toast.LENGTH_SHORT).show();
                    edtEmailId.requestFocus();
                    return;
                }

                /** Calls that if the password does not match with a piece of data in the document then the code returns an error to the user. */
                if (edtPassword.getText().toString().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                    return;
                }



                String name = edtName.getText().toString().trim();
                String emailId = edtEmailId.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String cpassword = edtCPassword.getText().toString().trim();

                /** Checks to make sure that the end of the email ends in "@myport.ac.uk" and will compare it to other emails. */
                if (emailId.lastIndexOf("@myport.ac.uk") > 9 || emailId.lastIndexOf("@myport.ac.uk") < 7) {
                    Toast.makeText(getApplicationContext(), "Incorrect Email Id", Toast.LENGTH_SHORT).show();
                    edtEmailId.requestFocus();
                    return;
                }

                if (!password.equals(cpassword)) {
                    Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                    return;
                }

                prgLoading.setVisibility(View.VISIBLE);
                /** Makes sure that the code can comunicate with the web gate service to get the information in the database. */
                ProfileRegistrationParam param = new ProfileRegistrationParam(name, emailId, password);
                WebGatewayService.getInstance().getGateWayService().signUp(param).enqueue(new Callback<Boolean>() {

                    /** On response method to allow the user to enter their information and check if they match with existing data. */
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean b = response.body();
                        if (b != null && b) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ctx, VerifyEmailActivity.class);
                            i.putExtra("Name", name);
                            i.putExtra("EmailId", emailId);
                            ctx.startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                        prgLoading.setVisibility(View.GONE);
                    }
                    /** Method below creates and on failure method to return if there is an error. */
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();

                        prgLoading.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
