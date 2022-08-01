package com.mapchat.webendpoint;

import com.mapchat.storage.models.Profile;
import com.mapchat.webendpoint.parameterbeans.ContactRequestParam;
import com.mapchat.webendpoint.parameterbeans.CredentialParam;
import com.mapchat.webendpoint.parameterbeans.GetContactParam;
import com.mapchat.webendpoint.parameterbeans.ProfileRegistrationParam;
import com.mapchat.webendpoint.parameterbeans.SearchContactParam;
import com.mapchat.webendpoint.parameterbeans.VerifyEmailParam;
import com.mapchat.webendpoint.returnbeans.ContactR;
import com.mapchat.webendpoint.returnbeans.ContactsReturn;
import com.mapchat.webendpoint.returnbeans.ProfileR;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebGatewayInterface {
    @POST("Login")
    Call<Profile> getProfile(@Body CredentialParam cred);

    @POST("Register")
    Call<Boolean> signUp(@Body ProfileRegistrationParam param);

    @POST("VerifyEmail")
    Call<Profile> verifyEmail(@Body VerifyEmailParam param);

    //Contacts
    @POST("GetContacts")
    Call<List<ContactR>> getContacts(@Body GetContactParam param);

    @POST("GetContactRequestReceived")
    Call<List<ContactR>> getContactRequestReceived(@Body GetContactParam param);

    @POST("GetContactRequestSent")
    Call<ContactsReturn> getContactRequestSent(@Body GetContactParam param);

    @POST("SendContactRequest")
    Call<Boolean> sendContactRequest(@Body ContactRequestParam param);

    @POST("AcceptContactRequest")
    Call<Boolean> acceptRequest(@Body ContactRequestParam param);

    @POST("RejectContactRequest")
    Call<Boolean> rejectRequest(@Body ContactRequestParam param);

    @POST("RemoveContactRequest")
    Call<Boolean> removeRequest(@Body ContactRequestParam param);

    @POST("SearchContacts")
    Call<List<ProfileR>> searchContacts(@Body SearchContactParam param);

}
