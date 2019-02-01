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
package com.sonicle.webtop.contacts.io;


import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.LogEntries;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.ldaptive.LdapEntry;

/**
 *
 * @author Dorian Haxhiaj
 */
public class LDIFInput {
	
public ContactInput fromLDIF(LdapEntry ldapEntry, LogEntries log) throws WTException {
		Contact contact = new Contact();
		
		if (ldapEntry.getAttribute("givenName") != null) { // FirstName
			contact.setFirstName(ldapEntry.getAttribute("givenName").getStringValue());
		}
		if (ldapEntry.getAttribute("sn") != null) { // LastName
			contact.setLastName(ldapEntry.getAttribute("sn").getStringValue());
		}
		if (ldapEntry.getAttribute("mozillaNickname") != null) { // Nickname
			contact.setNickname((ldapEntry.getAttribute("mozillaNickname").getStringValue()));
		}
		if (ldapEntry.getAttribute("mobile") != null) {
			contact.setMobile((ldapEntry.getAttribute("mobile").getStringValue()));
		}
		if (ldapEntry.getAttribute("mail") != null) {
			contact.setEmail1((ldapEntry.getAttribute("mail").getStringValue()));
		}
		if (ldapEntry.getAttribute("mozillaSecondEmail") != null) {
			contact.setEmail2((ldapEntry.getAttribute("mozillaSecondEmail").getStringValue()));
		}
		if (ldapEntry.getAttribute("street") != null) { // Address
			contact.setWorkAddress((ldapEntry.getAttribute("street").getStringValue()));
		}
		if (ldapEntry.getAttribute("postalCode") != null) { // Postal Code
			contact.setWorkPostalCode((ldapEntry.getAttribute("postalCode").getStringValue()));
		}
		if (ldapEntry.getAttribute("l") != null) { // City
			contact.setWorkCity((ldapEntry.getAttribute("l").getStringValue()));
		}
		if (ldapEntry.getAttribute("st") != null) { // State/province
			contact.setWorkState(ldapEntry.getAttribute("st").getStringValue());
		}
		if (ldapEntry.getAttribute("c") != null) { // Country
			contact.setWorkCountry((ldapEntry.getAttribute("c").getStringValue()));
		}
		if (ldapEntry.getAttribute("telephoneNumber") != null) {
			contact.setWorkTelephone1((ldapEntry.getAttribute("telephoneNumber").getStringValue()));
		}
		if (ldapEntry.getAttribute("facsimileTelephoneNumber") != null) {
			contact.setWorkFax((ldapEntry.getAttribute("facsimileTelephoneNumber").getStringValue()));
		}
		if (ldapEntry.getAttribute("mozillaHomeStreet") != null) { // Home Address
			contact.setHomeAddress((ldapEntry.getAttribute("mozillaHomeStreet").getStringValue()));
		}
		if (ldapEntry.getAttribute("mozillaHomePostalCode") != null) { // Home Postal Code
			contact.setHomePostalCode((ldapEntry.getAttribute("mozillaHomePostalCode").getStringValue()));
		}
		if (ldapEntry.getAttribute("mozillaHomeLocalityName") != null) { // Home City
			contact.setHomeCity((ldapEntry.getAttribute("mozillaHomeLocalityName").getStringValue()));
		}
		if (ldapEntry.getAttribute("mozillaHomeState") != null) { // Home State/province
			contact.setHomeState(ldapEntry.getAttribute("mozillaHomeState").getStringValue());
		}
		if (ldapEntry.getAttribute("mozillaHomeCountryName") != null) { // Home Country
			contact.setHomeCountry((ldapEntry.getAttribute("mozillaHomeCountryName").getStringValue()));
		}
		if (ldapEntry.getAttribute("homePhone") != null) {
			contact.setHomeTelephone1((ldapEntry.getAttribute("homePhone").getStringValue()));
		}
		if (ldapEntry.getAttribute("title") != null) { // Job title
			contact.setFunction(ldapEntry.getAttribute("title").getStringValue());
		}
		if (ldapEntry.getAttribute("ou") !=null) { // Department		
			contact.setDepartment(ldapEntry.getAttribute("ou").getStringValue());
		}
		if (ldapEntry.getAttribute("birthday") != null && ldapEntry.getAttribute("birthmonth") != null && ldapEntry.getAttribute("birthyear") != null) {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			contact.setBirthday(DateTimeUtils.parseLocalDate(fmt, ldapEntry.getAttribute("birthday").getStringValue()+"/"+ldapEntry.getAttribute("birthmonth").getStringValue()+"/"+ldapEntry.getAttribute("birthyear").getStringValue()));
		}
		if (ldapEntry.getAttribute("description") != null) {	
			contact.setNotes((ldapEntry.getAttribute("description").getStringValue()));
		}
		
		return new ContactInput(contact);
	}
}
