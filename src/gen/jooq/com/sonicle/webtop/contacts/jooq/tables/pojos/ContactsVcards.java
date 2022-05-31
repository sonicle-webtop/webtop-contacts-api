/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables.pojos;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsVcards implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.lang.Integer contactId;
    private java.lang.String  rawData;

    public ContactsVcards() {}

    public ContactsVcards(ContactsVcards value) {
        this.contactId = value.contactId;
        this.rawData = value.rawData;
    }

    public ContactsVcards(
        java.lang.Integer contactId,
        java.lang.String  rawData
    ) {
        this.contactId = contactId;
        this.rawData = rawData;
    }

    /**
     * Getter for <code>contacts.contacts_vcards.contact_id</code>.
     */
    public java.lang.Integer getContactId() {
        return this.contactId;
    }

    /**
     * Setter for <code>contacts.contacts_vcards.contact_id</code>.
     */
    public void setContactId(java.lang.Integer contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for <code>contacts.contacts_vcards.raw_data</code>.
     */
    public java.lang.String getRawData() {
        return this.rawData;
    }

    /**
     * Setter for <code>contacts.contacts_vcards.raw_data</code>.
     */
    public void setRawData(java.lang.String rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContactsVcards (");

        sb.append(contactId);
        sb.append(", ").append(rawData);

        sb.append(")");
        return sb.toString();
    }
}