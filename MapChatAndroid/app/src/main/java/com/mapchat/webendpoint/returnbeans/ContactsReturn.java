package com.mapchat.webendpoint.returnbeans;

import java.util.List;

public class ContactsReturn {
    private List<ContactR> contactList;

    public ContactsReturn() {
    }

    public ContactsReturn(List<ContactR> contactList) {
        this.contactList = contactList;
    }

    public List<ContactR> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactR> contactList) {
        this.contactList = contactList;
    }
}
