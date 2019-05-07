/*
 * Copyright (C) 2018 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2018 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author malbinola
 */
public class BaseContact {
	protected Integer contactId;
	protected Integer categoryId;
	protected RevisionStatus revisionStatus;
	protected String publicUid;
	protected String title;
	protected String firstName;
	protected String lastName;
	protected String displayName;
	protected String nickname;
	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public RevisionStatus getRevisionStatus() {
		return revisionStatus;
	}

	public void setRevisionStatus(RevisionStatus revisionStatus) {
		this.revisionStatus = revisionStatus;
	}
	
	public String getPublicUid() {
		return publicUid;
	}

	public void setPublicUid(String publicUid) {
		this.publicUid = publicUid;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getDisplayName(boolean fallback) {
		String dn = getDisplayName();
		if (fallback) {
			return !StringUtils.isBlank(dn) ? dn : getFullName(true);
		} else {
			return dn;
		}
	}
	
	public String getComputedDisplayName(ShowBy showBy) {
		if (ShowBy.FIRST_LAST.equals(showBy)) {
			return buildFullName(getFirstName(), getLastName());
		} else if (ShowBy.LAST_FIRST.equals(showBy)) {
			return buildFullName(getLastName(), getFirstName());
		} else if (ShowBy.DISPLAY.equals(showBy)) {
			return getDisplayName(true);
		} else {
			return null;
		}
	}
	
	public String getFullName() {
		return getFullName(true);
	}
	
	public String getFullName(boolean firstLastOnly) {
		if (firstLastOnly) {
			return BaseContact.buildFullName(getFirstName(), getLastName());
		} else {
			return StringUtils.join(getTitle(), " ", getFirstName(), " ", getLastName()).trim();
		}
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(getContactId())
				.append(getFirstName())
				.append(getLastName())
				.toString();
	}
	
	public static String buildFullName(String firstName, String lastName) {
		return StringUtils.trim(StringUtils.defaultString(firstName) + " " + StringUtils.defaultString(lastName));
	}
	
	public static enum RevisionStatus {
		@SerializedName("N") NEW,
		@SerializedName("M") MODIFIED,
		@SerializedName("D") DELETED;
	}
}
