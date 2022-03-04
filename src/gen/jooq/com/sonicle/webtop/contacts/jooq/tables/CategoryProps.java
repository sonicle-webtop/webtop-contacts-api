/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq.tables;



/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CategoryProps extends org.jooq.impl.TableImpl<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contacts.category_props</code>
     */
    public static final CategoryProps CATEGORY_PROPS = new CategoryProps();

    /**
     * The class holding records for this type
     */
    @java.lang.Override
    public java.lang.Class<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> getRecordType() {
        return com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord.class;
    }

    /**
     * The column <code>contacts.category_props.domain_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.String> DOMAIN_ID = createField(org.jooq.impl.DSL.name("domain_id"), org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>contacts.category_props.user_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.String> USER_ID = createField(org.jooq.impl.DSL.name("user_id"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>contacts.category_props.category_id</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.Integer> CATEGORY_ID = createField(org.jooq.impl.DSL.name("category_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>contacts.category_props.hidden</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.Boolean> HIDDEN = createField(org.jooq.impl.DSL.name("hidden"), org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>contacts.category_props.color</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.String> COLOR = createField(org.jooq.impl.DSL.name("color"), org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>contacts.category_props.sync</code>.
     */
    public final org.jooq.TableField<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord, java.lang.String> SYNC = createField(org.jooq.impl.DSL.name("sync"), org.jooq.impl.SQLDataType.VARCHAR(1), this, "");

    private CategoryProps(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CategoryProps(org.jooq.Name alias, org.jooq.Table<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, null, aliased, parameters, org.jooq.impl.DSL.comment(""), org.jooq.TableOptions.table());
    }

    /**
     * Create an aliased <code>contacts.category_props</code> table reference
     */
    public CategoryProps(java.lang.String alias) {
        this(org.jooq.impl.DSL.name(alias), CATEGORY_PROPS);
    }

    /**
     * Create an aliased <code>contacts.category_props</code> table reference
     */
    public CategoryProps(org.jooq.Name alias) {
        this(alias, CATEGORY_PROPS);
    }

    /**
     * Create a <code>contacts.category_props</code> table reference
     */
    public CategoryProps() {
        this(org.jooq.impl.DSL.name("category_props"), null);
    }

    public <O extends org.jooq.Record> CategoryProps(org.jooq.Table<O> child, org.jooq.ForeignKey<O, com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> key) {
        super(child, key, CATEGORY_PROPS);
    }

    @java.lang.Override
    public org.jooq.Schema getSchema() {
        return com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS;
    }

    @java.lang.Override
    public java.util.List<org.jooq.Index> getIndexes() {
        return java.util.Arrays.<org.jooq.Index>asList(com.sonicle.webtop.contacts.jooq.Indexes.CATEGORY_PROPS_AK1);
    }

    @java.lang.Override
    public org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord> getPrimaryKey() {
        return com.sonicle.webtop.contacts.jooq.Keys.CATEGORY_PROPS_PKEY;
    }

    @java.lang.Override
    public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord>> getKeys() {
        return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.contacts.jooq.tables.records.CategoryPropsRecord>>asList(com.sonicle.webtop.contacts.jooq.Keys.CATEGORY_PROPS_PKEY);
    }

    @java.lang.Override
    public CategoryProps as(java.lang.String alias) {
        return new CategoryProps(org.jooq.impl.DSL.name(alias), this);
    }

    @java.lang.Override
    public CategoryProps as(org.jooq.Name alias) {
        return new CategoryProps(alias, this);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public CategoryProps rename(java.lang.String name) {
        return new CategoryProps(org.jooq.impl.DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @java.lang.Override
    public CategoryProps rename(org.jooq.Name name) {
        return new CategoryProps(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @java.lang.Override
    public org.jooq.Row6<java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.String> fieldsRow() {
        return (org.jooq.Row6) super.fieldsRow();
    }
}
