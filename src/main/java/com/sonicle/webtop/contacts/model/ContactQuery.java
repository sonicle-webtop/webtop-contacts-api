/*
 * Copyright (C) 2025 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2025 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.sonicle.commons.qbuilders.properties.concrete.InstantProperty;
import com.sonicle.commons.qbuilders.properties.concrete.StringProperty;
import com.sonicle.webtop.core.app.sdk.QueryBuilderWithCFields;

/**
 *
 * @author malbinola
 */
public class ContactQuery extends QueryBuilderWithCFields<ContactQuery> {
	public static final String ID = "id";
	public static final String CREATED_AT = "createdAt";
	public static final String UPDATED_AT = "updatedAt";
	public static final String DISPLAY_NAME = "displayName";
	public static final String TITLE = "title";
	public static final String FIRSTNAME = "firstName";
	public static final String LASTNAME = "lastName";
	public static final String NICKNAME = "nickname";
	public static final String MOBILE = "mobile";
	public static final String PAGER1 = "pager1";
	public static final String PAGER2 = "pager2";
	public static final String EMAIL1 = "email1";
	public static final String EMAIL2 = "email2";
	public static final String EMAIL3 = "email3";
	public static final String IM1 = "im1";
	public static final String IM2 = "im2";
	public static final String IM3 = "im3";
	public static final String WORK_TELEPHONE1 = "workTelephone1";
	public static final String WORK_TELEPHONE2 = "workTelephone2";
	public static final String WORK_FAX = "workFax";
	public static final String HOME_TELEPHONE1 = "homeTelephone1";
	public static final String HOME_TELEPHONE2 = "homeTelephone2";
	public static final String HOME_FAX = "homeFax";
	public static final String WORK_ADDRESS = "workAddress";
	public static final String WORK_POSTALCODE = "workPostalCode";
	public static final String WORK_CITY = "workCity";
	public static final String WORK_STATE = "workCity";
	public static final String WORK_COUNTRY = "workCountry";
	public static final String HOME_ADDRESS = "homeAddress";
	public static final String HOME_POSTALCODE = "homePostalCode";
	public static final String HOME_CITY = "homeCity";
	public static final String HOME_STATE = "homeCity";
	public static final String HOME_COUNTRY = "homeCountry";
	public static final String OTHER_ADDRESS = "otherAddress";
	public static final String OTHER_POSTALCODE = "otherPostalCode";
	public static final String OTHER_CITY = "otherCity";
	public static final String OTHER_STATE = "otherCity";
	public static final String OTHER_COUNTRY = "otherCountry";
	public static final String COMPANY = "company";
	public static final String COMPANY_ID = "companyId";
	public static final String FUNCTION = "function";
	public static final String DEPARTMENT = "department";
	public static final String MANAGER = "manager";
	public static final String ASSISTANT = "assistant";
	public static final String ASSISTANT_TELEPHONE = "assistantTelephone";
	public static final String PARTNER = "partner";
	public static final String BIRTHDAY = "birthday";
	public static final String ANNIVERSARY = "anniversary";
	public static final String URL = "url";
	public static final String NOTES = "notes";
	public static final String TAG_ID = "tagId";
	public static final String ANY_NAME = "anyName";
	public static final String ANY_EMAIL = "anyEmail";
	public static final String ANY_PHONES = "anyPhone";
	public static final String ANY_WORK_PHONE = "anyWorkPhone";
	public static final String ANY_HOME_PHONE = "anyHomePhone";
	public static final String ANY_ADDRESS = "anyAddress";
	
	public StringProperty<ContactQuery> id() {
		return string(ID);
	}
	
	public InstantProperty<ContactQuery> createdOn() {
		return instant(CREATED_AT);
	}
	
	public InstantProperty<ContactQuery> updatedOn() {
		return instant(UPDATED_AT);
	}
	
	public StringProperty<ContactQuery> displayName() {
		return string(DISPLAY_NAME);
	}
	
	public StringProperty<ContactQuery> title() {
		return string(TITLE);
	}
	
	public StringProperty<ContactQuery> firstName() {
		return string(FIRSTNAME);
	}
	
	public StringProperty<ContactQuery> lastName() {
		return string(LASTNAME);
	}
	
	public StringProperty<ContactQuery> nickname() {
		return string(NICKNAME);
	}
	
	public StringProperty<ContactQuery> mobile() {
		return string(MOBILE);
	}
	
	public StringProperty<ContactQuery> pager1() {
		return string(PAGER1);
	}
	
	public StringProperty<ContactQuery> pager2() {
		return string(PAGER2);
	}
	
	public StringProperty<ContactQuery> email1() {
		return string(EMAIL1);
	}
	
	public StringProperty<ContactQuery> email2() {
		return string(EMAIL2);
	}
	
	public StringProperty<ContactQuery> email3() {
		return string(EMAIL3);
	}
	
	public StringProperty<ContactQuery> im1() {
		return string(IM1);
	}
	
	public StringProperty<ContactQuery> im2() {
		return string(IM2);
	}
	
	public StringProperty<ContactQuery> im3() {
		return string(IM3);
	}
	
	public StringProperty<ContactQuery> workTelephone1() {
		return string(WORK_TELEPHONE1);
	}
	
	public StringProperty<ContactQuery> workTelephone2() {
		return string(WORK_TELEPHONE2);
	}
	
	public StringProperty<ContactQuery> workFax() {
		return string(WORK_FAX);
	}
	
	public StringProperty<ContactQuery> homeTelephone1() {
		return string(HOME_TELEPHONE1);
	}
	
	public StringProperty<ContactQuery> homeTelephone2() {
		return string(HOME_TELEPHONE2);
	}
	
	public StringProperty<ContactQuery> homeFax() {
		return string(HOME_FAX);
	}

	public StringProperty<ContactQuery> workAddress() {
		return string(WORK_ADDRESS);
	}
	
	public StringProperty<ContactQuery> workPostalCode() {
		return string(WORK_POSTALCODE);
	}
	
	public StringProperty<ContactQuery> workCity() {
		return string(WORK_CITY);
	}
	
	public StringProperty<ContactQuery> workState() {
		return string(WORK_STATE);
	}
	
	public StringProperty<ContactQuery> workCountry() {
		return string(WORK_COUNTRY);
	}

	public StringProperty<ContactQuery> homeAddress() {
		return string(HOME_ADDRESS);
	}
	
	public StringProperty<ContactQuery> homePostalCode() {
		return string(HOME_POSTALCODE);
	}
	
	public StringProperty<ContactQuery> homeCity() {
		return string(HOME_CITY);
	}
	
	public StringProperty<ContactQuery> homeState() {
		return string(HOME_STATE);
	}
	
	public StringProperty<ContactQuery> homeCountry() {
		return string(HOME_COUNTRY);
	}
	
	public StringProperty<ContactQuery> otherAddress() {
		return string(OTHER_ADDRESS);
	}
	
	public StringProperty<ContactQuery> otherPostalCode() {
		return string(OTHER_POSTALCODE);
	}
	
	public StringProperty<ContactQuery> otherCity() {
		return string(OTHER_CITY);
	}
	
	public StringProperty<ContactQuery> otherState() {
		return string(OTHER_STATE);
	}
	
	public StringProperty<ContactQuery> otherCountry() {
		return string(OTHER_COUNTRY);
	}
	
	public StringProperty<ContactQuery> company() {
		return string(COMPANY);
	}
	
	public StringProperty<ContactQuery> companyId() {
		return string(COMPANY_ID);
	}
	
	public StringProperty<ContactQuery> function() {
		return string(FUNCTION);
	}
	
	public StringProperty<ContactQuery> department() {
		return string(DEPARTMENT);
	}
	
	public StringProperty<ContactQuery> manager() {
		return string(MANAGER);
	}
	
	public StringProperty<ContactQuery> assistant() {
		return string(ASSISTANT);
	}
	
	public StringProperty<ContactQuery> assistantTelephone() {
		return string(ASSISTANT_TELEPHONE);
	}
	
	public StringProperty<ContactQuery> partner() {
		return string(PARTNER);
	}
	
	public InstantProperty<ContactQuery> birthday() {
		return instant(BIRTHDAY);
	}
	
	public InstantProperty<ContactQuery> anniversary() {
		return instant(ANNIVERSARY);
	}
	
	public StringProperty<ContactQuery> url() {
		return string(URL);
	}
	
	public StringProperty<ContactQuery> notes() {
		return string(NOTES);
	}
	
	public StringProperty<ContactQuery> tagId() {
		return string(TAG_ID);
	}
	
	public StringProperty<ContactQuery> anyName() {
		return string(ANY_NAME);
	}
	
	public StringProperty<ContactQuery> anyEmail() {
		return string(ANY_EMAIL);
	}
	
	public StringProperty<ContactQuery> anyPhone() {
		return string(ANY_PHONES);
	}
	
	public StringProperty<ContactQuery> anyWorkPhone() {
		return string(ANY_WORK_PHONE);
	}
	
	public StringProperty<ContactQuery> anyHomePhone() {
		return string(ANY_HOME_PHONE);
	}
	
	public StringProperty<ContactQuery> anyAddress() {
		return string(ANY_ADDRESS);
	}
}
