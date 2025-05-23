/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables.pojos;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsAttachments implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.lang.String       contactAttachmentId;
    private java.lang.String       contactId;
    private org.joda.time.DateTime revisionTimestamp;
    private java.lang.Short        revisionSequence;
    private java.lang.String       filename;
    private java.lang.Long         size;
    private java.lang.String       mediaType;

    public ContactsAttachments() {}

    public ContactsAttachments(ContactsAttachments value) {
        this.contactAttachmentId = value.contactAttachmentId;
        this.contactId = value.contactId;
        this.revisionTimestamp = value.revisionTimestamp;
        this.revisionSequence = value.revisionSequence;
        this.filename = value.filename;
        this.size = value.size;
        this.mediaType = value.mediaType;
    }

    public ContactsAttachments(
        java.lang.String       contactAttachmentId,
        java.lang.String       contactId,
        org.joda.time.DateTime revisionTimestamp,
        java.lang.Short        revisionSequence,
        java.lang.String       filename,
        java.lang.Long         size,
        java.lang.String       mediaType
    ) {
        this.contactAttachmentId = contactAttachmentId;
        this.contactId = contactId;
        this.revisionTimestamp = revisionTimestamp;
        this.revisionSequence = revisionSequence;
        this.filename = filename;
        this.size = size;
        this.mediaType = mediaType;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.contact_attachment_id</code>.
     */
    public java.lang.String getContactAttachmentId() {
        return this.contactAttachmentId;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.contact_attachment_id</code>.
     */
    public void setContactAttachmentId(java.lang.String contactAttachmentId) {
        this.contactAttachmentId = contactAttachmentId;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.contact_id</code>.
     */
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.contact_id</code>.
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.revision_timestamp</code>.
     */
    public org.joda.time.DateTime getRevisionTimestamp() {
        return this.revisionTimestamp;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.revision_timestamp</code>.
     */
    public void setRevisionTimestamp(org.joda.time.DateTime revisionTimestamp) {
        this.revisionTimestamp = revisionTimestamp;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.revision_sequence</code>.
     */
    public java.lang.Short getRevisionSequence() {
        return this.revisionSequence;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.revision_sequence</code>.
     */
    public void setRevisionSequence(java.lang.Short revisionSequence) {
        this.revisionSequence = revisionSequence;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.filename</code>.
     */
    public java.lang.String getFilename() {
        return this.filename;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.filename</code>.
     */
    public void setFilename(java.lang.String filename) {
        this.filename = filename;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.size</code>.
     */
    public java.lang.Long getSize() {
        return this.size;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.size</code>.
     */
    public void setSize(java.lang.Long size) {
        this.size = size;
    }

    /**
     * Getter for <code>contacts.contacts_attachments.media_type</code>.
     */
    public java.lang.String getMediaType() {
        return this.mediaType;
    }

    /**
     * Setter for <code>contacts.contacts_attachments.media_type</code>.
     */
    public void setMediaType(java.lang.String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContactsAttachments (");

        sb.append(contactAttachmentId);
        sb.append(", ").append(contactId);
        sb.append(", ").append(revisionTimestamp);
        sb.append(", ").append(revisionSequence);
        sb.append(", ").append(filename);
        sb.append(", ").append(size);
        sb.append(", ").append(mediaType);

        sb.append(")");
        return sb.toString();
    }
}
