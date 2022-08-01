package com.mapchat.contact;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapchat.R;
import com.mapchat.storage.DataProvider;
import com.mapchat.storage.models.Contact;
import com.mapchat.storage.models.Profile;
import com.mapchat.webendpoint.WebGatewayService;
import com.mapchat.webendpoint.parameterbeans.ContactRequestParam;
import com.mapchat.webendpoint.parameterbeans.SearchContactParam;
import com.mapchat.webendpoint.returnbeans.ProfileR;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages a contacts activity
 * Searches for new contacts by name or email id
 */

public class SearchContactActivity extends AppCompatActivity {

    String mUserEmailId = "";
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcontact);

        setTitle("Search Contacts");

        mRecyclerAdapter = new RecyclerAdapter(new ArrayList<Contact>());
        mRecycler = findViewById(R.id.rcl_contacts);
        mRecycler.setAdapter(mRecyclerAdapter);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        Cursor c = getContentResolver().query(DataProvider.PROFILE_URI, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            mUserEmailId = c.getString(c.getColumnIndex(Profile.EMAIL_ID));
            c.close();
        }

        final AutoCompleteTextView edtSearch = findViewById(R.id.edtSearch);


        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = edtSearch.getText().toString().trim();
                    if (text.length() == 0) {
                        return;
                    }

                    SearchContactParam param = new SearchContactParam(mUserEmailId, text);
                    WebGatewayService.getInstance().getGateWayService().searchContacts(param).enqueue(new Callback<List<ProfileR>>() {
                        @Override
                        public void onResponse(Call<List<ProfileR>> call, Response<List<ProfileR>> response) {
                            List<ProfileR> prList = response.body();
                            if (prList != null) {
                                List<Contact> contactsList = new ArrayList<>();
                                for (ProfileR prr : prList) {
                                    Contact cc = new Contact();
                                    cc.setProfileName(prr.getProfileName());
                                    cc.setEmailId(prr.getEmailId());
                                    cc.setStatus(0);
                                    contactsList.add(cc);
                                }
                                mRecyclerAdapter.addContacts(contactsList);
                                mRecyclerAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ProfileR>> call, Throwable t) {

                        }
                    });

                } catch (Exception e) {

                }
            }
        });


    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        List<Contact> contacts = new ArrayList<>();

        public RecyclerAdapter(List<Contact> contactList) {
            super();
            this.contacts = contactList;
        }

        public void addContacts(List<Contact> contactList) {
            this.contacts = contactList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().from(parent.getContext()).inflate(R.layout.list_item_searchcontacts, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Contact contact = contacts.get(position);

            holder.emailId.setText(contact.getEmailId());

            holder.fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactRequestParam param = new ContactRequestParam();
                    param.setUserEmailId(mUserEmailId);
                    param.setContactEmailId(contact.getEmailId());

                    WebGatewayService.getInstance().getGateWayService().sendContactRequest(param).enqueue(new Callback<Boolean>() {

                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b = response.body();
                            if (b != null && b) {
                                contacts.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "Successfully Sent Request", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("Error Removing Friend", t.getLocalizedMessage());
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView emailId;
            FloatingActionButton fabAdd;
            FloatingActionButton fabBlock;

            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                emailId = itemView.findViewById(R.id.txt_emailId);
                fabAdd = itemView.findViewById(R.id.fab_add);
                fabBlock = itemView.findViewById(R.id.fab_block);


            }
        }
    }
}
