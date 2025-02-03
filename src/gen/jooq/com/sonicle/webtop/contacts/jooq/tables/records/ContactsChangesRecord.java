/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables.records;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsChangesRecord extends org.jooq.impl.UpdatableRecordImpl<ContactsChangesRecord> implements org.jooq.Record5<java.lang.Long, java.lang.Integer, java.lang.String, org.joda.time.DateTime, java.lang.String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>contacts.contacts_changes.id</code>.
     */
    public void setId(java.lang.Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>contacts.contacts_changes.id</code>.
     */
    public java.lang.Long getId() {
        return (java.lang.Long) get(0);
    }

    /**
     * Setter for <code>contacts.contacts_changes.category_id</code>.
     */
    public void setCategoryId(java.lang.Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>contacts.contacts_changes.category_id</code>.
     */
    public java.lang.Integer getCategoryId() {
        return (java.lang.Integer) get(1);
    }

    /**
     * Setter for <code>contacts.contacts_changes.contact_id</code>.
     */
    public void setContactId(java.lang.String value) {
        set(2, value);
    }

    /**
     * Getter for <code>contacts.contacts_changes.contact_id</code>.
     */
    public java.lang.String getContactId() {
        return (java.lang.String) get(2);
    }

    /**
     * Setter for <code>contacts.contacts_changes.change_timestamp</code>.
     */
    public void setChangeTimestamp(org.joda.time.DateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>contacts.contacts_changes.change_timestamp</code>.
     */
    public org.joda.time.DateTime getChangeTimestamp() {
        return (org.joda.time.DateTime) get(3);
    }

    /**
     * Setter for <code>contacts.contacts_changes.change_type</code>.
     */
    public void setChangeType(java.lang.String value) {
        set(4, value);
    }

    /**
     * Getter for <code>contacts.contacts_changes.change_type</code>.
     */
    public java.lang.String getChangeType() {
        return (java.lang.String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Record1<java.lang.Long> key() {
        return (org.jooq.Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Row5<java.lang.Long, java.lang.Integer, java.lang.String, org.joda.time.DateTime, java.lang.String> fieldsRow() {
        return (org.jooq.Row5) super.fieldsRow();
    }

    @java.lang.Override
    public org.jooq.Row5<java.lang.Long, java.lang.Integer, java.lang.String, org.joda.time.DateTime, java.lang.String> valuesRow() {
        return (org.jooq.Row5) super.valuesRow();
    }

    @java.lang.Override
    public org.jooq.Field<java.lang.Long> field1() {
        return com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES.ID;
    }

    @java.lang.Override
    public org.jooq.Field<java.lang.Integer> field2() {
        return com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES.CATEGORY_ID;
    }

    @java.lang.Override
    public org.jooq.Field<java.lang.String> field3() {
        return com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES.CONTACT_ID;
    }

    @java.lang.Override
    public org.jooq.Field<org.joda.time.DateTime> field4() {
        return com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES.CHANGE_TIMESTAMP;
    }

    @java.lang.Override
    public org.jooq.Field<java.lang.String> field5() {
        return com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES.CHANGE_TYPE;
    }

    @java.lang.Override
    public java.lang.Long component1() {
        return getId();
    }

    @java.lang.Override
    public java.lang.Integer component2() {
        return getCategoryId();
    }

    @java.lang.Override
    public java.lang.String component3() {
        return getContactId();
    }

    @java.lang.Override
    public org.joda.time.DateTime component4() {
        return getChangeTimestamp();
    }

    @java.lang.Override
    public java.lang.String component5() {
        return getChangeType();
    }

    @java.lang.Override
    public java.lang.Long value1() {
        return getId();
    }

    @java.lang.Override
    public java.lang.Integer value2() {
        return getCategoryId();
    }

    @java.lang.Override
    public java.lang.String value3() {
        return getContactId();
    }

    @java.lang.Override
    public org.joda.time.DateTime value4() {
        return getChangeTimestamp();
    }

    @java.lang.Override
    public java.lang.String value5() {
        return getChangeType();
    }

    @java.lang.Override
    public ContactsChangesRecord value1(java.lang.Long value) {
        setId(value);
        return this;
    }

    @java.lang.Override
    public ContactsChangesRecord value2(java.lang.Integer value) {
        setCategoryId(value);
        return this;
    }

    @java.lang.Override
    public ContactsChangesRecord value3(java.lang.String value) {
        setContactId(value);
        return this;
    }

    @java.lang.Override
    public ContactsChangesRecord value4(org.joda.time.DateTime value) {
        setChangeTimestamp(value);
        return this;
    }

    @java.lang.Override
    public ContactsChangesRecord value5(java.lang.String value) {
        setChangeType(value);
        return this;
    }

    @java.lang.Override
    public ContactsChangesRecord values(java.lang.Long value1, java.lang.Integer value2, java.lang.String value3, org.joda.time.DateTime value4, java.lang.String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ContactsChangesRecord
     */
    public ContactsChangesRecord() {
        super(com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES);
    }

    /**
     * Create a detached, initialised ContactsChangesRecord
     */
    public ContactsChangesRecord(java.lang.Long id, java.lang.Integer categoryId, java.lang.String contactId, org.joda.time.DateTime changeTimestamp, java.lang.String changeType) {
        super(com.sonicle.webtop.contacts.jooq.tables.ContactsChanges.CONTACTS_CHANGES);

        setId(id);
        setCategoryId(categoryId);
        setContactId(contactId);
        setChangeTimestamp(changeTimestamp);
        setChangeType(changeType);
    }
}
