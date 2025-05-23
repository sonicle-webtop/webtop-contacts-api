/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables.pojos;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsTags implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.lang.String contactId;
    private java.lang.String tagId;

    public ContactsTags() {}

    public ContactsTags(ContactsTags value) {
        this.contactId = value.contactId;
        this.tagId = value.tagId;
    }

    public ContactsTags(
        java.lang.String contactId,
        java.lang.String tagId
    ) {
        this.contactId = contactId;
        this.tagId = tagId;
    }

    /**
     * Getter for <code>contacts.contacts_tags.contact_id</code>.
     */
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * Setter for <code>contacts.contacts_tags.contact_id</code>.
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for <code>contacts.contacts_tags.tag_id</code>.
     */
    public java.lang.String getTagId() {
        return this.tagId;
    }

    /**
     * Setter for <code>contacts.contacts_tags.tag_id</code>.
     */
    public void setTagId(java.lang.String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContactsTags (");

        sb.append(contactId);
        sb.append(", ").append(tagId);

        sb.append(")");
        return sb.toString();
    }
}
