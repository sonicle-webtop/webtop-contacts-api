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

import com.sonicle.commons.qbuilders.conditions.Condition;
import com.sonicle.commons.LangUtils;
import com.sonicle.webtop.contacts.model.Category;
import com.sonicle.webtop.contacts.model.CategoryPropSet;
import com.sonicle.webtop.contacts.model.ShareFolderCategory;
import com.sonicle.webtop.contacts.model.ShareRootCategory;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactAttachmentWithBytes;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactObject;
import com.sonicle.webtop.contacts.model.ContactObjectChanged;
import com.sonicle.webtop.contacts.model.ContactObjectWithVCard;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytes;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytesOld;
import com.sonicle.webtop.contacts.model.ContactQuery;
import com.sonicle.webtop.contacts.model.ContactsList;
import com.sonicle.webtop.contacts.model.Grouping;
import com.sonicle.webtop.contacts.model.ListContactsResult;
import com.sonicle.webtop.contacts.model.ShowBy;
import com.sonicle.webtop.contacts.model.ContactType;
import com.sonicle.webtop.core.model.CustomFieldValue;
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
	public Set<Integer> listAllCategoryIds() throws WTException;
	public Map<Integer, Category> listCategories() throws WTException;
	public Map<Integer, DateTime> getCategoriesLastRevision(Collection<Integer> categoryIds) throws WTException;
	public Category getCategory(int categoryId) throws WTException;
	public Category getBuiltInCategory() throws WTException;
	public Category addCategory(Category cat) throws WTException;
	public Category addBuiltInCategory() throws WTException;
	public void updateCategory(Category cat) throws WTException;
	public boolean deleteCategory(int categoryId) throws WTException;
	public CategoryPropSet getCategoryCustomProps(int categoryId) throws WTException;
	public Map<Integer, CategoryPropSet> getCategoryCustomProps(Collection<Integer> categoryIds) throws WTException;
	public CategoryPropSet updateCategoryCustomProps(int categoryId, CategoryPropSet propertySet) throws WTException;
	public List<ContactObject> listContactObjects(int categoryId, ContactObjectOutputType outputType) throws WTException;
	public LangUtils.CollectionChangeSet<ContactObjectChanged> listContactObjectsChanges(int categoryId, DateTime since, Integer limit) throws WTException;
	public ContactObjectWithVCard getContactObjectWithVCard(int categoryId, String href) throws WTException;
	public List<ContactObjectWithVCard> getContactObjectsWithVCard(int categoryId, Collection<String> hrefs) throws WTException;
	public ContactObject getContactObject(int contactId, ContactObjectOutputType outputType) throws WTException;
	public void addContactObject(int categoryId, String href, VCard vCard) throws WTException;
	public void updateContactObject(int categoryId, String href, VCard vCard) throws WTException;
	public void deleteContactObject(int categoryId, String href) throws WTException;
	public boolean existContact(Collection<Integer> categoryIds, Condition<ContactQuery> conditionPredicate) throws WTException;
	
	/**
	 * @deprecated
	 */
	@Deprecated
	public ListContactsResult listContacts(Collection<Integer> categoryIds, boolean listOnly, Grouping groupBy, ShowBy showBy, String pattern, int page, int limit, boolean returnFullCount) throws WTException;
	
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, String pattern) throws WTException;
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, Condition<ContactQuery> conditionPredicate) throws WTException;
	public ListContactsResult listContacts(Collection<Integer> categoryIds, ContactType type, Grouping groupBy, ShowBy showBy, Condition<ContactQuery> conditionPredicate, int page, int limit, boolean returnFullCount) throws WTException;
	public Contact getContact(int contactId) throws WTException;
	public ContactAttachmentWithBytes getContactAttachment(int contactId, String attachmentId) throws WTException;
	public ContactCompany getContactCompany(int contactId) throws WTException;
	public Map<String, CustomFieldValue> getContactCustomValues(int contactId) throws WTException;
	public Contact addContact(Contact contact) throws WTException;
	public Contact addContact(Contact contact, String vCardRawData) throws WTException;
	public void updateContact(Contact contact) throws WTException;
	public void updateContact(Contact contact, boolean processPicture) throws WTException;
	public ContactPictureWithBytes getContactPicture(int contactId) throws WTException;
	public void updateContactPicture(int contactId, ContactPictureWithBytesOld picture) throws WTException;
	public void deleteContact(int contactId) throws WTException;
	public void deleteContact(Collection<Integer> contactIds) throws WTException;
	public void moveContact(boolean copy, int contactId, int targetCategoryId) throws WTException;
	public void moveContact(boolean copy, Collection<Integer> contactIds, int targetCategoryId) throws WTException;
	public ContactsList getContactsList(int contactId) throws WTException;
	public void addContactsList(ContactsList list) throws WTException;
	public void addToContactsList(int contactsListId, ContactsList list) throws WTException;
	public void updateContactsList(ContactsList list) throws WTException;
	public void deleteContactsList(int contactsListId) throws WTException;
	public void deleteContactsList(Collection<Integer> contactsListIds) throws WTException;
	public void moveContactsList(boolean copy, int contactsListId, int targetCategoryId) throws WTException;
	public void moveContactsList(boolean copy, Collection<Integer> contactIds, int targetCategoryId) throws WTException;
	public void updateContactCategoryTags(final UpdateTagsOperation operation, final int categoryId, final Set<String> tagIds) throws WTException;
	public void updateContactTags(final UpdateTagsOperation operation, final Collection<Integer> contactIds, final Set<String> tagIds) throws WTException;
}
