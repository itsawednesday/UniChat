package com.mapchat.contact;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.mapchat.webendpoint.parameterbeans.GetContactParam;
import com.mapchat.webendpoint.returnbeans.ContactR;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Creates a contact activity instance
 * creates an xml file
 *
 */

public class ContactActivity extends AppCompatActivity {

    String mUserEmailId = "";
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setTitle("Contacts");

        mRecyclerAdapter = new RecyclerAdapter(new ArrayList<Contact>());
        mRecycler = findViewById(R.id.rcl_contacts);
        mRecycler.setAdapter(mRecyclerAdapter);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        Cursor c = getContentResolver().query(DataProvider.PROFILE_URI, null, null, null, null);
        Log.d("Noted", "Email is" + (c == null) + " " + c.getCount());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            mUserEmailId = c.getString(c.getColumnIndex(Profile.EMAIL_ID));
            c.close();

            GetContactParam param = new GetContactParam(mUserEmailId);
            WebGatewayService.getInstance().getGateWayService().getContacts(param).enqueue(new Callback<List<ContactR>>() {

                @Override
                public void onResponse(Call<List<ContactR>> call, Response<List<ContactR>> response) {
                    Log.d("Contacts", "Running");
                    List<ContactR> conList = response.body();
                    Log.d("Contacts", conList.size() + "");
                    if (conList != null) {
                        List<Contact> contactsList = new ArrayList<>();
                        for (ContactR crr : conList) {
                            Contact cc = new Contact();
                            cc.setProfileName("Contact");
                            cc.setEmailId(crr.getContactEmailId());
                            Log.d("Contact ", crr.getContactEmailId() + "");
                            cc.setStatus(cc.getStatus());
                            contactsList.add(cc);
                        }
                        mRecyclerAdapter.addContacts(contactsList);
                        mRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<ContactR>> call, Throwable t) {
                    Log.d("Error", t.getLocalizedMessage() + "");
                }
            });
        }
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
        public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().from(parent.getContext()).inflate(R.layout.list_item_contacts, parent, false);
            return new RecyclerAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

            final Contact contact = contacts.get(position);

            holder.emailId.setText(contact.getEmailId());

            holder.fabRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactRequestParam param = new ContactRequestParam();
                    param.setUserEmailId(mUserEmailId);
                    param.setContactEmailId(contact.getEmailId());

                    WebGatewayService.getInstance().getGateWayService().removeRequest(param).enqueue(new Callback<Boolean>() {

                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b = response.body();
                            if (b != null && b) {
                                contacts.remove(position);
                                notifyDataSetChanged();
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
            FloatingActionButton fabRemove;
            FloatingActionButton fabBlock;

            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                emailId = itemView.findViewById(R.id.txt_emailId);
                fabRemove = itemView.findViewById(R.id.fab_remove);
                fabBlock = itemView.findViewById(R.id.fab_block);


            }
        }
    }
}
