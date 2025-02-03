/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Categories extends org.jooq.impl.TableImpl<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contacts.categories</code>
     */
    public static final Categories CATEGORIES = new Categories();

    /**
     * The class holding records for this type
     */
    @java.lang.Override
    public java.lang.Class<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord> getRecordType() {
        return com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord.class;
    }

    /**
     * The column <code>contacts.categories.category_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.Integer> CATEGORY_ID = createField(org.jooq.impl.DSL.name("category_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('contacts.seq_categories'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>contacts.categories.domain_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> DOMAIN_ID = createField(org.jooq.impl.DSL.name("domain_id"), org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>contacts.categories.user_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> USER_ID = createField(org.jooq.impl.DSL.name("user_id"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>contacts.categories.revision_timestamp</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, org.joda.time.DateTime> REVISION_TIMESTAMP = createField(org.jooq.impl.DSL.name("revision_timestamp"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "", new com.sonicle.jooq.jsr310.OffsetDateTimeJodaConverter());

    /**
     * The column <code>contacts.categories.creation_timestamp</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, org.joda.time.DateTime> CREATION_TIMESTAMP = createField(org.jooq.impl.DSL.name("creation_timestamp"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "", new com.sonicle.jooq.jsr310.OffsetDateTimeJodaConverter());

    /**
     * The column <code>contacts.categories.built_in</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.Boolean> BUILT_IN = createField(org.jooq.impl.DSL.name("built_in"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>contacts.categories.provider</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> PROVIDER = createField(org.jooq.impl.DSL.name("provider"), org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.field("'local'::character varying", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>contacts.categories.name</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> NAME = createField(org.jooq.impl.DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>contacts.categories.description</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> DESCRIPTION = createField(org.jooq.impl.DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>contacts.categories.color</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> COLOR = createField(org.jooq.impl.DSL.name("color"), org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>contacts.categories.sync</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> SYNC = createField(org.jooq.impl.DSL.name("sync"), org.jooq.impl.SQLDataType.VARCHAR(1).nullable(false), this, "");

    /**
     * The column <code>contacts.categories.is_default</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.Boolean> IS_DEFAULT = createField(org.jooq.impl.DSL.name("is_default"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>contacts.categories.parameters</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> PARAMETERS = createField(org.jooq.impl.DSL.name("parameters"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>contacts.categories.remote_sync_frequency</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.Short> REMOTE_SYNC_FREQUENCY = createField(org.jooq.impl.DSL.name("remote_sync_frequency"), org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>contacts.categories.remote_sync_timestamp</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, org.joda.time.DateTime> REMOTE_SYNC_TIMESTAMP = createField(org.jooq.impl.DSL.name("remote_sync_timestamp"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "", new com.sonicle.jooq.jsr310.OffsetDateTimeJodaConverter());

    /**
     * The column <code>contacts.categories.remote_sync_tag</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord, java.lang.String> REMOTE_SYNC_TAG = createField(org.jooq.impl.DSL.name("remote_sync_tag"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    private Categories(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Categories(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, null, aliased, parameters, org.jooq.impl.DSL.comment(""), org.jooq.TableOptions.table());
    }

    /**
     * Create an aliased <code>contacts.categories</code> table reference
     */
    public Categories(java.lang.String alias) {
        this(org.jooq.impl.DSL.name(alias), CATEGORIES);
    }

    /**
     * Create an aliased <code>contacts.categories</code> table reference
     */
    public Categories(org.jooq.Name alias) {
        this(alias, CATEGORIES);
    }

    /**
     * Create a <code>contacts.categories</code> table reference
     */
    public Categories() {
        this(org.jooq.impl.DSL.name("categories"), null);
    }

    public <O extends org.jooq.Record> Categories(org.jooq.Table<O> child, org.jooq.ForeignKey<O, com.sonicle.webtop.contacts.jooq.tables.records.CategoriesRecord> key) {
        super(child, key, CATEGORIES);
    }

    @java.lang.Override
    public org.jooq.Schema getSchema() {
        return com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS;
    }

    @java.lang.Override
    public Categories as(java.lang.String alias) {
        return new Categories(org.jooq.impl.DSL.name(alias), this);
    }

    @java.lang.Override
    public Categories as(org.jooq.Name alias) {
        return new Categories(alias, this);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public Categories rename(java.lang.String name) {
        return new Categories(org.jooq.impl.DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public Categories rename(org.jooq.Name name) {
        return new Categories(name, null);
    }

    // -------------------------------------------------------------------------
    // Row16 type methods
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Row16<java.lang.Integer, java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.Short, org.joda.time.DateTime, java.lang.String> fieldsRow() {
        return (org.jooq.Row16) super.fieldsRow();
    }
}
