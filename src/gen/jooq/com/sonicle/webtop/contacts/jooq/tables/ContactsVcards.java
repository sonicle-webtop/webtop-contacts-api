/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ContactsVcards extends org.jooq.impl.TableImpl<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contacts.contacts_vcards</code>
     */
    public static final ContactsVcards CONTACTS_VCARDS = new ContactsVcards();

    /**
     * The class holding records for this type
     */
    @java.lang.Override
    public java.lang.Class<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> getRecordType() {
        return com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord.class;
    }

    /**
     * The column <code>contacts.contacts_vcards.contact_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord, java.lang.String> CONTACT_ID = createField(org.jooq.impl.DSL.name("contact_id"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>contacts.contacts_vcards.raw_data</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord, java.lang.String> RAW_DATA = createField(org.jooq.impl.DSL.name("raw_data"), org.jooq.impl.SQLDataType.CLOB, this, "");

    private ContactsVcards(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ContactsVcards(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, null, aliased, parameters, org.jooq.impl.DSL.comment(""), org.jooq.TableOptions.table());
    }

    /**
     * Create an aliased <code>contacts.contacts_vcards</code> table reference
     */
    public ContactsVcards(java.lang.String alias) {
        this(org.jooq.impl.DSL.name(alias), CONTACTS_VCARDS);
    }

    /**
     * Create an aliased <code>contacts.contacts_vcards</code> table reference
     */
    public ContactsVcards(org.jooq.Name alias) {
        this(alias, CONTACTS_VCARDS);
    }

    /**
     * Create a <code>contacts.contacts_vcards</code> table reference
     */
    public ContactsVcards() {
        this(org.jooq.impl.DSL.name("contacts_vcards"), null);
    }

    public <O extends org.jooq.Record> ContactsVcards(org.jooq.Table<O> child, org.jooq.ForeignKey<O, com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> key) {
        super(child, key, CONTACTS_VCARDS);
    }

    @java.lang.Override
    public org.jooq.Schema getSchema() {
        return com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS;
    }

    @java.lang.Override
    public org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord> getPrimaryKey() {
        return com.sonicle.webtop.contacts.jooq.Keys.CONTACTS_VCARDS_PKEY;
    }

    @java.lang.Override
    public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord>> getKeys() {
        return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord>>asList(com.sonicle.webtop.contacts.jooq.Keys.CONTACTS_VCARDS_PKEY);
    }

    @java.lang.Override
    public java.util.List<org.jooq.ForeignKey<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord, ?>> getReferences() {
        return java.util.Arrays.<org.jooq.ForeignKey<com.sonicle.webtop.contacts.jooq.tables.records.ContactsVcardsRecord, ?>>asList(com.sonicle.webtop.contacts.jooq.Keys.CONTACTS_VCARDS__CONTACTS_VCARDS_CONTACT_ID_FKEY);
    }

    private transient com.sonicle.webtop.contacts.jooq.tables.Contacts _contacts;

    public com.sonicle.webtop.contacts.jooq.tables.Contacts contacts() {
        if (_contacts == null)
            _contacts = new com.sonicle.webtop.contacts.jooq.tables.Contacts(this, com.sonicle.webtop.contacts.jooq.Keys.CONTACTS_VCARDS__CONTACTS_VCARDS_CONTACT_ID_FKEY);

        return _contacts;
    }

    @java.lang.Override
    public ContactsVcards as(java.lang.String alias) {
        return new ContactsVcards(org.jooq.impl.DSL.name(alias), this);
    }

    @java.lang.Override
    public ContactsVcards as(org.jooq.Name alias) {
        return new ContactsVcards(alias, this);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public ContactsVcards rename(java.lang.String name) {
        return new ContactsVcards(org.jooq.impl.DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public ContactsVcards rename(org.jooq.Name name) {
        return new ContactsVcards(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Row2<java.lang.String, java.lang.String> fieldsRow() {
        return (org.jooq.Row2) super.fieldsRow();
    }
}
