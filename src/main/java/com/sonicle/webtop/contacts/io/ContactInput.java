/*
 * Copyright (C) 2017 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2017 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.io;

import com.sonicle.webtop.contacts.model.ContactAttachmentWithStream;
import com.sonicle.webtop.contacts.model.ContactBase;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.core.app.ezvcard.BinaryType;
import com.sonicle.webtop.core.app.ezvcard.XAttachment;
import com.sonicle.webtop.core.model.CustomFieldValue;
import ezvcard.VCard;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author malbinola
 */
public class ContactInput {
	public final ContactBase contact;
	public final ContactCompany contactCompany;
	public final ContactPicture contactPicture;
	public final Set<String> tagNames;
	public final VCard sourceObject;

	public ContactInput(ContactBase contact, ContactCompany contactCompany, ContactPicture contactPicture, Set<String> tagNames, VCard sourceObject) {
		this.contact = contact;
		this.contactCompany = contactCompany;
		this.contactPicture = contactPicture;
		this.tagNames = tagNames;
		this.sourceObject = sourceObject;
	}
	
	public List<ContactAttachmentWithStream> extractAttachments() {
		ArrayList<ContactAttachmentWithStream> attachments = new ArrayList<>();
		if (sourceObject != null) {
			for (XAttachment xatt : sourceObject.getProperties(XAttachment.class)) {
				byte[] bytes = xatt.getData();
				if (bytes != null) {
					BinaryType btype = xatt.getContentType();
					attachments.add(new ContactAttachmentWithStream(new ByteArrayInputStream(bytes), (btype != null) ? btype.getMediaType() : null, xatt.getFilename(), (long)bytes.length));
				}
			}
			sourceObject.removeProperties(XAttachment.class);
		}
		return attachments;
	}
	
	public Map<String, CustomFieldValue> extractCustomFieldsValues(final Set<String> validCustomFieldsIds) {
		Map<String, CustomFieldValue> values = new LinkedHashMap<>();
		//TODO: add support to XCustomFieldValue
		/*
		if (sourceObject != null) {
			for (XCustomFieldValue xval : sourceObject.getProperties(XCustomFieldValue.class)) {
				String id = xval.getID();
				if (validCustomFieldsIds.contains(id)) {
					CustomFieldValue value = new CustomFieldValue();
					values.put(id, value);
				}
			}
		}
		*/
		return values;
	}
}
