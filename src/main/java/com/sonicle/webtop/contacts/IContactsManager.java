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
import com.sonicle.commons.BitFlag;
import com.sonicle.commons.BitFlagEnum;
import com.sonicle.commons.qbuilders.conditions.Condition;
import com.sonicle.commons.LangUtils;
import com.sonicle.webtop.contacts.model.Category;
import com.sonicle.webtop.contacts.model.CategoryPropSet;
import com.sonicle.webtop.contacts.model.ShareFolderCategory;
import com.sonicle.webtop.contacts.model.ShareRootCategory;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactAttachmentWithBytes;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactEx;
import com.sonicle.webtop.contacts.model.ContactList;
import com.sonicle.webtop.contacts.model.ContactListEx;
import com.sonicle.webtop.contacts.model.ContactObject;
import com.sonicle.webtop.contacts.model.ContactObjectChanged;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytes;
import com.sonicle.webtop.contacts.model.ContactQuery;
import com.sonicle.webtop.contacts.model.Grouping;
import com.sonicle.webtop.contacts.model.ListContactsResult;
import com.sonicle.webtop.contacts.model.ShowBy;
import com.sonicle.webtop.contacts.model.ContactType;
import com.sonicle.webtop.contacts.model.ContactListRecipient;
import com.sonicle.webtop.core.model.CustomFieldValue;
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
	
	public List<ShareRootCategory> listIncomingCategoryRoots() throws WTException;
	public Map<Integer, ShareFolderCategory> listIncomingCategoryFolders(String rootShareId) throws WTException;
	public Set<Integer> listCategoryIds() throws WTException;
	public Set<Integer> listIncomingCategoryIds() throws WTException;
	public Set<Integer> listIncomingCategoryIds(final UserProfileId owner) throws WTException;
	public Set<Integer> listAllCategoryIds() throws WTException;
	public Map<Integer, Category> listCategories() throws WTException;
	public Map<Integer, Category> listIncomingCategories() throws WTException;
	public Map<Integer, Category> listIncomingCategories(final UserProfileId owner) throws WTException;
	public Map<Integer, DateTime> getCategoriesLastRevision(Collection<Integer> categoryIds) throws WTException;
	public Integer getDefaultCategoryId() throws WTException;
	public Integer getBuiltInCategoryId() throws WTException;
	public Category getCategory(int categoryId) throws WTException;
	public Category getBuiltInCategory() throws WTException;
	public Category addCategory(Category cat) throws WTException;
	public Category addBuiltInCategory() throws WTException;
	public void updateCategory(Category cat) throws WTException;
	public boolean deleteCategory(int categoryId) throws WTException;
	public CategoryPropSet getCategoryCustomProps(int categoryId) throws WTException;
	public Map<Integer, CategoryPropSet> getCategoryCustomProps(Collection<Integer> categoryIds) throws WTException;
	public CategoryPropSet updateCategoryCustomProps(int categoryId, CategoryPropSet propertySet) throws WTException;
	public List<ContactObject> listContactObjects(final int categoryId, final ContactObjectOutputType outputType) throws WTException;
	public LangUtils.CollectionChangeSet<ContactObjectChanged> listContactObjectsChanges(final int categoryId, final DateTime since, final Integer limit) throws WTException;
	public ContactObject getContactObject(final int categoryId, final String href, final ContactObjectOutputType outputType) throws WTException;
	public List<ContactObject> getContactObjects(final int categoryId, final Collection<String> hrefs, final ContactObjectOutputType outputType) throws WTException;
	public ContactObject getContactObject(final int contactId, final ContactObjectOutputType outputType) throws WTException;
	public void addContactObject(final int categoryId, final String href, final VCard vCard) throws WTException;
	public void updateContactObject(final int categoryId, final String href, final VCard vCard) throws WTException;
	public void deleteContactObject(final int categoryId, final String href) throws WTException;
	public boolean existContact(Collection<Integer> categoryIds, Condition<ContactQuery> conditionPredicate) throws WTException;
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, String pattern) throws WTException;
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, Condition<ContactQuery> conditionPredicate) throws WTException;
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, Condition<ContactQuery> conditionPredicate, int page, int limit, boolean returnFullCount) throws WTException;
	public Contact getContact(final int contactId) throws WTException;
	public Contact getContact(final int contactId, final BitFlag<ContactGetOptions> opts) throws WTException;
	public ContactPictureWithBytes getContactPicture(final int contactId) throws WTException;
	public ContactCompany getContactCompany(final int contactId) throws WTException;
	public ContactAttachmentWithBytes getContactAttachment(final int contactId, final String attachmentId) throws WTException;
	public Map<String, CustomFieldValue> getContactCustomValues(final int contactId) throws WTException;
	public Contact addContact(ContactEx contact) throws WTException;
	public Contact addContact(ContactEx contact, String vCardRawData) throws WTException;
	public void updateContact(final int contactId, final ContactEx contact) throws WTException;
	public void updateContact(final int contactId, final ContactEx contact, final BitFlag<ContactUpdateOptions> opts) throws WTException;
	public void updateContactPicture(final int contactId, final ContactPictureWithBytes picture) throws WTException;	
	public void deleteContact(final int contactId) throws WTException;
	public void deleteContact(final Collection<Integer> contactIds) throws WTException;
	public void moveContact(final boolean copy, final int contactId, final int targetCategoryId) throws WTException;
	public void moveContact(final boolean copy, final int contactId, final int targetCategoryId, BitFlag<ContactGetOptions> opts) throws WTException;
	public void moveContact(final boolean copy, final Collection<Integer> contactIds, final int targetCategoryId) throws WTException;
	public void moveContact(final boolean copy, final Collection<Integer> contactIds, final int targetCategoryId, BitFlag<ContactGetOptions> opts) throws WTException;
	public ContactList getContactList(final int contactId) throws WTException;
	public ContactList getContactList(final int contactId, final BitFlag<ContactGetOptions> opts) throws WTException;
	public ContactList addContactList(final ContactListEx contact) throws WTException;
	public void updateContactList(final int contactId, final ContactListEx contact) throws WTException;
	public void updateContactList(final int contactId, final ContactListEx contact, final BitFlag<ContactUpdateOptions> opts) throws WTException;
	public void updateContactsListRecipients(final int contactId, final Collection<ContactListRecipient> recipients, final boolean append) throws WTException;
	public void deleteContactList(final int contactId) throws WTException;
	public void deleteContactList(final Collection<Integer> contactIds) throws WTException;
	
	public void updateContactCategoryTags(final UpdateTagsOperation operation, final int categoryId, final Set<String> tagIds) throws WTException;
	public void updateContactTags(final UpdateTagsOperation operation, final Collection<Integer> contactIds, final Set<String> tagIds) throws WTException;
	
	public static enum ContactGetOptions implements BitFlagEnum {
		PICTURE(1), ATTACHMENTS(2), TAGS(4), CUSTOM_VALUES(8), LIST_RECIPIENTS(16);
		
		private int value = 0;
		private ContactGetOptions(int value) { this.value = value; }
		@Override
		public int value() { return this.value; }
	}
	
	public static enum ContactUpdateOptions implements BitFlagEnum {
		PICTURE(1), ATTACHMENTS(2), TAGS(4), CUSTOM_VALUES(8), LIST_RECIPIENTS(16);
		
		private int value = 0;
		private ContactUpdateOptions(int value) { this.value = value; }
		@Override
		public int value() { return this.value; }
	}
	
	public static enum ImportMode {
		@SerializedName("copy") COPY,
		@SerializedName("append") APPEND
	}
}
