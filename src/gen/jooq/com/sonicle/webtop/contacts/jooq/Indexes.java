/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq;



/**
 * A class modelling indexes of tables in contacts.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final org.jooq.Index CATEGORY_PROPS_AK1 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("category_props_ak1"), com.sonicle.webtop.contacts.jooq.tables.CategoryProps.CATEGORY_PROPS, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.CategoryProps.CATEGORY_PROPS.CATEGORY_ID }, false);
    public static final org.jooq.Index CONTACTS_AK1 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak1"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_TIMESTAMP }, false);
    public static final org.jooq.Index CONTACTS_AK2 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak2"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.LASTNAME }, false);
    public static final org.jooq.Index CONTACTS_AK3 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak3"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.SEARCHFIELD }, false);
    public static final org.jooq.Index CONTACTS_AK4 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak4"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.BIRTHDAY }, false);
    public static final org.jooq.Index CONTACTS_AK5 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak5"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.ANNIVERSARY }, false);
    public static final org.jooq.Index CONTACTS_AK6 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_ak6"), com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.IS_LIST, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.REVISION_STATUS, com.sonicle.webtop.contacts.jooq.tables.Contacts.CONTACTS_.HREF }, false);
    public static final org.jooq.Index CONTACTS_TAGS_AK1 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("contacts_tags_ak1"), com.sonicle.webtop.contacts.jooq.tables.ContactsTags.CONTACTS_TAGS, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.ContactsTags.CONTACTS_TAGS.TAG_ID }, false);
    public static final org.jooq.Index HISTORY_CATEGORIES_AK1 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("history_categories_ak1"), com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.CHANGE_TIMESTAMP }, false);
    public static final org.jooq.Index HISTORY_CATEGORIES_AK2 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("history_categories_ak2"), com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.DOMAIN_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.USER_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryCategories.HISTORY_CATEGORIES.CHANGE_TIMESTAMP }, false);
    public static final org.jooq.Index HISTORY_CONTACTS_AK1 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("history_contacts_ak1"), com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS.CATEGORY_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS.CHANGE_TIMESTAMP }, false);
    public static final org.jooq.Index HISTORY_CONTACTS_AK2 = org.jooq.impl.Internal.createIndex(org.jooq.impl.DSL.name("history_contacts_ak2"), com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS, new org.jooq.OrderField[] { com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS.CONTACT_ID, com.sonicle.webtop.contacts.jooq.tables.HistoryContacts.HISTORY_CONTACTS.CHANGE_TIMESTAMP }, false);
}
