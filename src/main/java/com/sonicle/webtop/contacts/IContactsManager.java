/* 
 * Copyright (C) 2014 Sonicle S.r.l.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY SONICLE, SONICLE DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle[at]sonicle[dot]com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * Sonicle logo and Sonicle copyright notice. If the display of the logo is not
 * reasonably feasible for technical reasons, the Appropriate Legal Notices must
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts;

import com.google.gson.annotations.SerializedName;
import com.sonicle.commons.qbuilders.conditions.Condition;
import com.sonicle.commons.LangUtils;
import com.sonicle.commons.beans.ItemsListResult;
import com.sonicle.commons.beans.SortInfo;
import com.sonicle.commons.flags.BitFlags;
import com.sonicle.commons.flags.BitFlagsEnum;
import com.sonicle.webtop.contacts.model.Category;
import com.sonicle.webtop.contacts.model.CategoryBase;
import com.sonicle.webtop.contacts.model.CategoryFSFolder;
import com.sonicle.webtop.contacts.model.CategoryFSOrigin;
import com.sonicle.webtop.contacts.model.CategoryPropSet;
import com.sonicle.webtop.contacts.model.CategoryQuery;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactAttachmentWithBytes;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactEx;
import com.sonicle.webtop.contacts.model.ContactList;
import com.sonicle.webtop.contacts.model.ContactListEx;
import com.sonicle.webtop.contacts.model.ContactListRecipient;
import com.sonicle.webtop.contacts.model.ContactObject;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytes;
import com.sonicle.webtop.contacts.model.ContactQueryUI_OLD;
import com.sonicle.webtop.contacts.model.Grouping;
import com.sonicle.webtop.contacts.model.ListContactsResult;
import com.sonicle.webtop.contacts.model.ShowBy;
import com.sonicle.webtop.contacts.model.ContactListRecipientBase;
import com.sonicle.webtop.contacts.model.ContactLookup;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.contacts.model.ContactQuery;
import com.sonicle.webtop.contacts.model.ContactType;
import com.sonicle.webtop.core.app.model.FolderSharing;
import com.sonicle.webtop.core.app.sdk.WTNotFoundException;
import com.sonicle.webtop.core.app.sdk.WTParseException;
import com.sonicle.webtop.core.model.CustomFieldValue;
import com.sonicle.webtop.core.model.Delta;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import ezvcard.VCard;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author malbinola
 */
public interface IContactsManager {
	public static final String RCPT_ORIGIN_CONTACT_WORK = "contact-work";
	public static final String RCPT_ORIGIN_CONTACT_HOME = "contact-home";
	public static final String RCPT_ORIGIN_CONTACT_OTHER = "contact-other";
	public static final String RCPT_ORIGIN_LIST = "list";
	public static final String RCPT_ORIGIN_LISTITEM = "listitem";
	
	/**
	 * @deprecated Use listMyCategoryIds() instead.
	 */
	@Deprecated public Set<Integer> listCategoryIds() throws WTException;
	/**
	 * @deprecated Use listMyCategories() instead.
	 */
	@Deprecated public Map<Integer, Category> listCategories() throws WTException;
	/**
	 * @deprecated Use listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQueryApi> filterQuery, final Integer page, final Integer limit, final boolean returnFullCount) instead.
	 */
	@Deprecated public ListContactsResult listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final String pattern) throws WTException;
	/**
	 * @deprecated Use listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQueryApi> filterQuery, final Integer page, final Integer limit, final boolean returnFullCount) instead.
	 */
	@Deprecated public ListContactsResult listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQueryUI_OLD> conditionPredicate) throws WTException;
	/**
	 * @deprecated Use listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQueryApi> filterQuery, final Integer page, final Integer limit, final boolean returnFullCount) instead.
	 */
	@Deprecated public ListContactsResult listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQueryUI_OLD> conditionPredicate, final int page, final int limit, final boolean returnFullCount) throws WTException;
	/**
	 * @deprecated Use existAnyContact(final Collection<Integer> categoryIds, final Condition<ContactQueryApi> filterQuery) instead.
	 */
	@Deprecated public boolean existContact(final Collection<Integer> categoryIds, final Condition<ContactQueryUI_OLD> conditionPredicate) throws WTException;
	
	public Set<FolderSharing.SubjectConfiguration> getFolderShareConfigurations(final UserProfileId originProfileId, final FolderSharing.Scope scope) throws WTException;
	public void updateFolderShareConfigurations(final UserProfileId originProfileId, final FolderSharing.Scope scope, final Set<FolderSharing.SubjectConfiguration> configurations) throws WTException;
	public Map<UserProfileId, CategoryFSOrigin> listIncomingCategoryOrigins() throws WTException;
	public CategoryFSOrigin getIncomingCategoryOriginByFolderId(final int calendarId) throws WTException;
	public Map<Integer, CategoryFSFolder> listIncomingCategoryFolders(final CategoryFSOrigin origin) throws WTException;
	public Map<Integer, CategoryFSFolder> listIncomingCategoryFolders(final UserProfileId originProfileId) throws WTException;
	public Set<Integer> listMyCategoryIds() throws WTException;
	public Set<Integer> listIncomingCategoryIds() throws WTException;
	public Set<Integer> listIncomingCategoryIds(final UserProfileId owner) throws WTException;
	public Set<Integer> listAllCategoryIds() throws WTException;
	public Map<Integer, Category> listMyCategories() throws WTException;
	public Map<Integer, Category> listIncomingCategories() throws WTException;
	public Map<Integer, Category> listIncomingCategories(final UserProfileId originProfileId) throws WTException;
	public ItemsListResult<Category> listCategories(final Condition<CategoryQuery> filterQuery, final Set<SortInfo> sortInfo, final Integer page, final Integer limit, final boolean returnFullCount) throws WTException;
	public ItemsListResult<Category> listCategories(final String filterQuery, final Set<SortInfo> sortInfo, final Integer page, final Integer limit, final boolean returnFullCount) throws WTException;
	public Map<Integer, DateTime> getCategoriesItemsLastRevision(final Collection<Integer> categoryIds) throws WTException;
	public UserProfileId getCategoryOwner(final int categoryId) throws WTException;
	public Integer getDefaultCategoryId() throws WTException;
	public Integer getBuiltInCategoryId() throws WTException;
	public boolean existCategory(final int categoryId) throws WTException;
	public Category getCategory(final int categoryId) throws WTException;
	public Category getBuiltInCategory() throws WTException;
	public Category addCategory(final CategoryBase category) throws WTException;
	public Category addBuiltInCategory() throws WTException;
	public void updateCategory(final int categoryId, final CategoryBase category) throws WTNotFoundException, WTException;
	public boolean deleteCategory(final int categoryId) throws WTNotFoundException, WTException;
	public CategoryPropSet getCategoryCustomProps(final int categoryId) throws WTException;
	public Map<Integer, CategoryPropSet> getCategoriesCustomProps(final Collection<Integer> categoryIds) throws WTException;
	public CategoryPropSet updateCategoryCustomProps(final int categoryId, final CategoryPropSet propertySet) throws WTException;
	public List<ContactObject> listContactObjects(final int categoryId, final ContactObjectOutputType outputType) throws WTException;
	public ContactObject getContactObject(final int categoryId, final String href, final ContactObjectOutputType outputType) throws WTException;
	public List<ContactObject> getContactObjects(final int categoryId, final Collection<String> hrefs, final ContactObjectOutputType outputType) throws WTException;
	public ContactObject getContactObject(final String contactId, final ContactObjectOutputType outputType) throws WTException;
	public void addContactObject(final int categoryId, final String href, final VCard vCard) throws WTException;
	public void updateContactObject(final int categoryId, final String href, final VCard vCard) throws WTException;
	public void deleteContactObject(final int categoryId, final String href) throws WTException;
	public ItemsListResult<ContactLookup> listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final Condition<ContactQuery> filterQuery, final Integer page, final Integer limit, final boolean returnFullCount) throws WTException;
	public ItemsListResult<ContactLookup> listContacts(final Collection<Integer> categoryIds, final ContactType type, final Grouping groupBy, final ShowBy showBy, final String filterQuery, final Integer page, final Integer limit, final boolean returnFullCount) throws WTException;
	public ItemsListResult<ContactObject> listContacts(final Collection<Integer> categoryIds, final Condition<ContactQuery> filterQuery, final Set<SortInfo> sortInfo, final Integer page, final Integer limit, final boolean returnFullCount, final ContactObjectOutputType outputType) throws WTException;
	public ItemsListResult<ContactObject> listContacts(final Collection<Integer> categoryIds, final String filterQuery, final Set<SortInfo> sortInfo, final Integer page, final Integer limit, final boolean returnFullCount, final ContactObjectOutputType outputType) throws WTException;
	public Delta<ContactObject> listContactsDelta(final int categoryId, final String syncToken, final ContactObjectOutputType outputType) throws WTParseException, WTException;
	public Delta<ContactObject> listContactsDelta(final int categoryId, final DateTime since, final ContactObjectOutputType outputType) throws WTException;
	public boolean existAnyContact(final Collection<Integer> categoryIds, final Condition<ContactQuery> filterQuery) throws WTException;
	public boolean existAnyContact(final Collection<Integer> categoryIds, final String filterQuery) throws WTException;
	public Contact getContact(final String contactId) throws WTException;
	public Contact getContact(final String contactId, final BitFlags<ContactGetOption> opts) throws WTException;
	public ContactPictureWithBytes getContactPicture(final String contactId) throws WTException;
	public ContactPicture getContactPicture(final String contactId, final boolean metaOnly) throws WTException;
	public ContactCompany getContactCompany(final String contactId) throws WTException;
	public ContactAttachmentWithBytes getContactAttachment(final String contactId, final String attachmentId) throws WTException;
	public Map<String, CustomFieldValue> getContactCustomValues(final String contactId) throws WTException;
	public Contact addContact(ContactEx contact) throws WTException;
	public Contact addContact(ContactEx contact, String vCardRawData) throws WTException;
	public void updateContact(final String contactId, final ContactEx contact) throws WTException;
	public void updateContact(final String contactId, final ContactEx contact, final BitFlags<ContactUpdateOption> opts) throws WTException;
	public void updateContactPicture(final String contactId, final ContactPictureWithBytes picture) throws WTException;	
	public void deleteContact(final String contactId) throws WTException;
	public void deleteContact(final Collection<String> contactIds) throws WTException;
	public void moveContact(final boolean copy, final String contactId, final int targetCategoryId) throws WTException;
	public void moveContact(final boolean copy, final String contactId, final int targetCategoryId, BitFlags<ContactGetOption> opts) throws WTException;
	public void moveContact(final boolean copy, final Collection<String> contactIds, final int targetCategoryId) throws WTException;
	public void moveContact(final boolean copy, final Collection<String> contactIds, final int targetCategoryId, BitFlags<ContactGetOption> opts) throws WTException;
	public ContactList<ContactListRecipient> getContactList(final String contactId) throws WTException;
	public ContactList<ContactListRecipient> getContactList(final String contactId, final BitFlags<ContactGetOption> opts) throws WTException;
	public ContactList<ContactListRecipient> addContactList(final ContactListEx<ContactListRecipientBase> contact) throws WTException;
	public void updateContactList(final String contactId, final ContactListEx<ContactListRecipientBase> contact) throws WTException;
	public void updateContactList(final String contactId, final ContactListEx<ContactListRecipientBase> contact, final BitFlags<ContactUpdateOption> opts) throws WTException;
	public void updateContactsListRecipients(final String contactId, final Collection<ContactListRecipientBase> recipients, final boolean append) throws WTException;
	public void deleteContactList(final String contactId) throws WTException;
	public void deleteContactList(final Collection<String> contactIds) throws WTException;
	
	public void updateContactCategoryTags(final UpdateTagsOperation operation, final int categoryId, final Set<String> tagIds) throws WTException;
	public void updateContactTags(final UpdateTagsOperation operation, final Collection<String> contactIds, final Set<String> tagIds) throws WTException;
	
	public static enum ContactGetOption implements BitFlagsEnum<ContactGetOption> {
		PICTURE(1<<0), ATTACHMENTS(1<<1), TAGS(1<<2), CUSTOM_VALUES(1<<3), LIST_RECIPIENTS(1<<4);
		
		private int mask = 0;
		private ContactGetOption(int mask) { this.mask = mask; }
		@Override
		public long mask() { return this.mask; }
	}
	
	public static enum ContactUpdateOption implements BitFlagsEnum<ContactUpdateOption> {
		PICTURE(1<<0), ATTACHMENTS(1<<1), TAGS(1<<2), CUSTOM_VALUES(1<<3), LIST_RECIPIENTS(1<<4);
		
		private int mask = 0;
		private ContactUpdateOption(int mask) { this.mask = mask; }
		@Override
		public long mask() { return this.mask; }
	}
	
	public static enum ImportMode {
		@SerializedName("copy") COPY,
		@SerializedName("append") APPEND
	}
}
