/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables.pojos;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsChanges implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.lang.Long         id;
    private java.lang.Integer      categoryId;
    private java.lang.String       contactId;
    private org.joda.time.DateTime changeTimestamp;
    private java.lang.String       changeType;

    public ContactsChanges() {}

    public ContactsChanges(ContactsChanges value) {
        this.id = value.id;
        this.categoryId = value.categoryId;
        this.contactId = value.contactId;
        this.changeTimestamp = value.changeTimestamp;
        this.changeType = value.changeType;
    }

    public ContactsChanges(
        java.lang.Long         id,
        java.lang.Integer      categoryId,
        java.lang.String       contactId,
        org.joda.time.DateTime changeTimestamp,
        java.lang.String       changeType
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.contactId = contactId;
        this.changeTimestamp = changeTimestamp;
        this.changeType = changeType;
    }

    /**
     * Getter for <code>contacts.contacts_changes.id</code>.
     */
    public java.lang.Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>contacts.contacts_changes.id</code>.
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>contacts.contacts_changes.category_id</code>.
     */
    public java.lang.Integer getCategoryId() {
        return this.categoryId;
    }

    /**
     * Setter for <code>contacts.contacts_changes.category_id</code>.
     */
    public void setCategoryId(java.lang.Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Getter for <code>contacts.contacts_changes.contact_id</code>.
     */
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * Setter for <code>contacts.contacts_changes.contact_id</code>.
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for <code>contacts.contacts_changes.change_timestamp</code>.
     */
    public org.joda.time.DateTime getChangeTimestamp() {
        return this.changeTimestamp;
    }

    /**
     * Setter for <code>contacts.contacts_changes.change_timestamp</code>.
     */
    public void setChangeTimestamp(org.joda.time.DateTime changeTimestamp) {
        this.changeTimestamp = changeTimestamp;
    }

    /**
     * Getter for <code>contacts.contacts_changes.change_type</code>.
     */
    public java.lang.String getChangeType() {
        return this.changeType;
    }

    /**
     * Setter for <code>contacts.contacts_changes.change_type</code>.
     */
    public void setChangeType(java.lang.String changeType) {
        this.changeType = changeType;
    }

    @Override
    public String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContactsChanges (");

        sb.append(id);
        sb.append(", ").append(categoryId);
        sb.append(", ").append(contactId);
        sb.append(", ").append(changeTimestamp);
        sb.append(", ").append(changeType);

        sb.append(")");
        return sb.toString();
    }
}
