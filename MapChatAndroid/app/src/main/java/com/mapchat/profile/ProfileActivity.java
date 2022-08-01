package com.mapchat.profile;


import android.database.Cursor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mapchat.R;
import com.mapchat.storage.DataProvider;
import com.mapchat.storage.models.Profile;




import de.hdodenhof.circleimageview.CircleImageView;

//This activity will display a users profile page.

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private TextInputEditText ProfileName;
    TextInputEditText ProfileEmail;
    private CircleImageView ProfileImage;
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        // getContentResolver will retrieve data of user name and email from SQLite database via a Cursor
        // Text will be displayed by setText

        Cursor c = getContentResolver().query(DataProvider.PROFILE_URI, null, null, null, null);
        Log.d("Noted", "Email is" + (c == null) + " " + c.getCount());
        if (c != null && c.getCount() > 0) {

            c.moveToFirst();
            String AddUserName = c.getString(c.getColumnIndex(Profile.PROFILE_NAME));
            String AddUserEmail = c.getString(c.getColumnIndex(Profile.EMAIL_ID));

            c.close();

            //Start the search for user id name and email activity_profile.xml
            ProfileName.setText(AddUserName);
            ProfileEmail.setText(AddUserEmail);
        }

        ProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)


            // onClick will trigger the intent for the users image.
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE);


            }
        });
    }


    //Method onActivityResult will handle it when the user selects an image, it will check and if it is an imge with PICK_IMAGE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.profile_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            TextView ProfileName = (TextView) findViewById(R.id.name);
            TextView ProfileEmail = (TextView) findViewById(R.id.email);




            }

        }
    }



