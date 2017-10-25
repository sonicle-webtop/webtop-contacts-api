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

import com.sonicle.webtop.contacts.model.Category;
import com.sonicle.webtop.contacts.model.CategoryFolder;
import com.sonicle.webtop.contacts.model.CategoryRoot;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.contacts.model.ContactsList;
import com.sonicle.webtop.contacts.model.FolderContacts;
import com.sonicle.webtop.core.sdk.WTException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
	
	public List<CategoryRoot> listIncomingCategoryRoots() throws WTException;
	public HashMap<Integer, CategoryFolder> listIncomingCategoryFolders(String rootShareId) throws WTException;
	public List<Integer> listCategoryIds() throws WTException;
	public List<Integer> listIncomingCategoryIds() throws WTException;
	public List<Category> listCategories() throws WTException;
	public Category getCategory(int categoryId) throws WTException;
	public Category getBuiltInCategory() throws WTException;
	public Category addCategory(Category cat) throws WTException;
	public Category addBuiltInCategory() throws WTException;
	public Category updateCategory(Category cat) throws WTException;
	public boolean deleteCategory(int categoryId) throws WTException;
	public List<FolderContacts> listFolderContacts(Collection<Integer> categoryFolderIds, String searchMode, String pattern) throws WTException;
	public Contact getContact(int contactId) throws WTException;
	public void addContact(Contact contact) throws WTException;
	public void addContact(Contact contact, ContactPicture picture) throws WTException;
	public void updateContact(Contact contact) throws WTException;
	public void updateContact(Contact contact, ContactPicture picture) throws WTException;
	public ContactPicture getContactPicture(int contactId) throws WTException;
	public void updateContactPicture(int contactId, ContactPicture picture) throws WTException;
	public void deleteContact(int contactId) throws WTException;
	public void deleteContact(Collection<Integer> contactIds) throws WTException;
	public void moveContact(boolean copy, int contactId, int targetCategoryId) throws WTException;
	public ContactsList getContactsList(int contactId) throws WTException;
	public void addContactsList(ContactsList list) throws WTException;
	public void updateContactsList(ContactsList list) throws WTException;
	public void deleteContactsList(int contactsListId) throws WTException;
	public void deleteContactsList(Collection<Integer> contactsListIds) throws WTException;
	public void moveContactsList(boolean copy, int contactsListId, int targetCategoryId) throws WTException;
}
