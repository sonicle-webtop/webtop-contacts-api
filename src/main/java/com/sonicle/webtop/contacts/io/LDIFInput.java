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


import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.LogEntries;
import org.joda.time.LocalDate;
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
		ContactPicture picture = null;
//		// UID
//		if (vCard.getUid() != null) {
//			contact.setPublicUid(deflt(vCard.getUid().getValue()));
//		}
		
		//FirstName
		if (ldapEntry.getAttribute("givenName") !=null) {
			contact.setFirstName(ldapEntry.getAttribute("givenName").getStringValue());
		}
			// LastName
		if (ldapEntry.getAttribute("sn") !=null) {
			contact.setLastName(ldapEntry.getAttribute("sn").getStringValue());
		}	
		//Birthday
		if (ldapEntry.getAttribute("birthday") !=null && ldapEntry.getAttribute("birthmonth") !=null && ldapEntry.getAttribute("birthyear") !=null) {
			 DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			String date=ldapEntry.getAttribute("birthday").getStringValue()+"/"+ldapEntry.getAttribute("birthmonth").getStringValue()+"/"+ldapEntry.getAttribute("birthyear").getStringValue();
			contact.setBirthday(LocalDate.parse(date,fmt));
		}
////		 NICKNAME
		if (ldapEntry.getAttribute("mozillaNickname") !=null) {
			contact.setNickname((ldapEntry.getAttribute("mozillaNickname").getStringValue()));
		}
			//Department
		if (ldapEntry.getAttribute("ou") !=null) {				
			contact.setDepartment(ldapEntry.getAttribute("ou").getStringValue());
		}
			//Department
		if (ldapEntry.getAttribute("title") !=null) {				
			contact.setTitle(ldapEntry.getAttribute("title").getStringValue());
		}
		
		//	homePhone
		if (ldapEntry.getAttribute("homePhone") !=null) {
			contact.setHomeTelephone((ldapEntry.getAttribute("homePhone").getStringValue()));
		}
		//Home Address
		if (ldapEntry.getAttribute("mozillaHomeStreet") !=null) {
			contact.setHomeAddress((ldapEntry.getAttribute("mozillaHomeStreet").getStringValue()));
		}
		//Home City
		if (ldapEntry.getAttribute("mozillaHomeLocalityName") !=null) {
			contact.setHomeCity((ldapEntry.getAttribute("mozillaHomeLocalityName").getStringValue()));
		}
		//Home State/province
		if (ldapEntry.getAttribute("mozillaHomeState") !=null) {
			contact.setHomeState(ldapEntry.getAttribute("mozillaHomeState").getStringValue());
		}
		//Home Postal Code
		if (ldapEntry.getAttribute("mozillaHomePostalCode") !=null) {
			contact.setHomePostalCode((ldapEntry.getAttribute("mozillaHomePostalCode").getStringValue()));
		}
        //Home Country
		if (ldapEntry.getAttribute("mozillaHomeCountryName") !=null) {
			contact.setHomeCountry((ldapEntry.getAttribute("mozillaHomeCountryName").getStringValue()));
		}		
		//Work Address
		if (ldapEntry.getAttribute("street") != null) {
			contact.setWorkAddress((ldapEntry.getAttribute("street").getStringValue()));
		}
		//Home City
		if (ldapEntry.getAttribute("l") != null) {
			contact.setWorkCity((ldapEntry.getAttribute("l").getStringValue()));
		}
		//Home State/province
		if (ldapEntry.getAttribute("st") != null) {
			contact.setWorkState(ldapEntry.getAttribute("st").getStringValue());
		}
		//Home Postal Code
		if (ldapEntry.getAttribute("postalCode") != null) {
			contact.setWorkPostalCode((ldapEntry.getAttribute("postalCode").getStringValue()));
		}
        //Home Country
		if (ldapEntry.getAttribute("c") != null) {
			contact.setWorkCountry((ldapEntry.getAttribute("c").getStringValue()));
		}
				//	 Phone
		if (ldapEntry.getAttribute("telephoneNumber") != null) {
			contact.setWorkTelephone((ldapEntry.getAttribute("telephoneNumber").getStringValue()));
		}
		//mobile
			if (ldapEntry.getAttribute("mobile") != null) {
			contact.setWorkMobile((ldapEntry.getAttribute("mobile").getStringValue()));
		}
			//Work Fax 
		if (ldapEntry.getAttribute("facsimileTelephoneNumber") != null) {
			contact.setWorkFax((ldapEntry.getAttribute("facsimileTelephoneNumber").getStringValue()));
		}
		
		//Work mail 
		if (ldapEntry.getAttribute("mail") != null) {
			contact.setWorkEmail((ldapEntry.getAttribute("mail").getStringValue()));
		}
		//Work mail 
		if (ldapEntry.getAttribute("mozillaSecondEmail") != null) {
			contact.setHomeEmail((ldapEntry.getAttribute("mozillaSecondEmail").getStringValue()));
		}
		//Notes
		if (ldapEntry.getAttribute("description") != null) {			
			contact.setNotes((ldapEntry.getAttribute("description").getStringValue()));
		}
//			//Notes
//		if (ldapEntry.getAttribute("jpegPhoto") != null) {			
//			contact.setNotes((ldapEntry.getAttribute("jpegPhoto").getStringValue()));
//		}


		
		
		return new ContactInput(contact, picture);
	}
	
}
