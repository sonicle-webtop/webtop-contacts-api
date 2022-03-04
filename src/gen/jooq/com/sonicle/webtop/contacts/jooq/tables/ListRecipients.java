/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ListRecipients extends org.jooq.impl.TableImpl<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contacts.list_recipients</code>
     */
    public static final ListRecipients LIST_RECIPIENTS = new ListRecipients();

    /**
     * The class holding records for this type
     */
    @java.lang.Override
    public java.lang.Class<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> getRecordType() {
        return com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord.class;
    }

    /**
     * The column <code>contacts.list_recipients.list_recipient_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, java.lang.Integer> LIST_RECIPIENT_ID = createField(org.jooq.impl.DSL.name("list_recipient_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('contacts.seq_list_recipients'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>contacts.list_recipients.contact_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, java.lang.Integer> CONTACT_ID = createField(org.jooq.impl.DSL.name("contact_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>contacts.list_recipients.recipient</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, java.lang.String> RECIPIENT = createField(org.jooq.impl.DSL.name("recipient"), org.jooq.impl.SQLDataType.VARCHAR(320).nullable(false), this, "");

    /**
     * The column <code>contacts.list_recipients.recipient_contact_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, java.lang.Integer> RECIPIENT_CONTACT_ID = createField(org.jooq.impl.DSL.name("recipient_contact_id"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>contacts.list_recipients.recipient_type</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, java.lang.String> RECIPIENT_TYPE = createField(org.jooq.impl.DSL.name("recipient_type"), org.jooq.impl.SQLDataType.VARCHAR(3), this, "");

    private ListRecipients(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ListRecipients(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, null, aliased, parameters, org.jooq.impl.DSL.comment(""), org.jooq.TableOptions.table());
    }

    /**
     * Create an aliased <code>contacts.list_recipients</code> table reference
     */
    public ListRecipients(java.lang.String alias) {
        this(org.jooq.impl.DSL.name(alias), LIST_RECIPIENTS);
    }

    /**
     * Create an aliased <code>contacts.list_recipients</code> table reference
     */
    public ListRecipients(org.jooq.Name alias) {
        this(alias, LIST_RECIPIENTS);
    }

    /**
     * Create a <code>contacts.list_recipients</code> table reference
     */
    public ListRecipients() {
        this(org.jooq.impl.DSL.name("list_recipients"), null);
    }

    public <O extends org.jooq.Record> ListRecipients(org.jooq.Table<O> child, org.jooq.ForeignKey<O, com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> key) {
        super(child, key, LIST_RECIPIENTS);
    }

    @java.lang.Override
    public org.jooq.Schema getSchema() {
        return com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS;
    }

    @java.lang.Override
    public org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord> getPrimaryKey() {
        return com.sonicle.webtop.contacts.jooq.Keys.LIST_RECIPIENTS_PKEY;
    }

    @java.lang.Override
    public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord>> getKeys() {
        return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord>>asList(com.sonicle.webtop.contacts.jooq.Keys.LIST_RECIPIENTS_PKEY);
    }

    @java.lang.Override
    public java.util.List<org.jooq.ForeignKey<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, ?>> getReferences() {
        return java.util.Arrays.<org.jooq.ForeignKey<com.sonicle.webtop.contacts.jooq.tables.records.ListRecipientsRecord, ?>>asList(com.sonicle.webtop.contacts.jooq.Keys.LIST_RECIPIENTS__LIST_RECIPIENTS_CONTACT_ID_FKEY);
    }

    private transient com.sonicle.webtop.contacts.jooq.tables.Contacts _contacts;

    public com.sonicle.webtop.contacts.jooq.tables.Contacts contacts() {
        if (_contacts == null)
            _contacts = new com.sonicle.webtop.contacts.jooq.tables.Contacts(this, com.sonicle.webtop.contacts.jooq.Keys.LIST_RECIPIENTS__LIST_RECIPIENTS_CONTACT_ID_FKEY);

        return _contacts;
    }

    @java.lang.Override
    public ListRecipients as(java.lang.String alias) {
        return new ListRecipients(org.jooq.impl.DSL.name(alias), this);
    }

    @java.lang.Override
    public ListRecipients as(org.jooq.Name alias) {
        return new ListRecipients(alias, this);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public ListRecipients rename(java.lang.String name) {
        return new ListRecipients(org.jooq.impl.DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public ListRecipients rename(org.jooq.Name name) {
        return new ListRecipients(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Row5<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String> fieldsRow() {
        return (org.jooq.Row5) super.fieldsRow();
    }
}
