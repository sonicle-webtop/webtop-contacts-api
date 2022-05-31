/*
 * This file is generated by jOOQ.
 */
package com.sonicle.webtop.contacts.jooq;



/**
 * Convenience access to all sequences in contacts.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>contacts.seq_categories</code>
     */
    public static final org.jooq.Sequence<java.lang.Long> SEQ_CATEGORIES = org.jooq.impl.Internal.createSequence("seq_categories", com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>contacts.seq_contacts</code>
     */
    public static final org.jooq.Sequence<java.lang.Long> SEQ_CONTACTS = org.jooq.impl.Internal.createSequence("seq_contacts", com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>contacts.seq_list_recipients</code>
     */
    public static final org.jooq.Sequence<java.lang.Long> SEQ_LIST_RECIPIENTS = org.jooq.impl.Internal.createSequence("seq_list_recipients", com.sonicle.webtop.contacts.jooq.Contacts.CONTACTS, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}