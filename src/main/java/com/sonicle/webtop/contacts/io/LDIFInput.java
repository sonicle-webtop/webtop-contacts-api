/*
 * Copyright (C) 2021 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2021 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.io;

import com.sonicle.commons.LangUtils;
import com.sonicle.commons.time.JodaTimeUtils;
import com.sonicle.webtop.contacts.model.ContactBase;
import com.sonicle.webtop.core.app.util.log.BufferingLogHandler;
import com.sonicle.webtop.core.app.util.log.LogEntry;
import com.sonicle.webtop.core.app.util.log.LogHandler;
import com.sonicle.webtop.core.app.util.log.LogMessage;
import com.sonicle.webtop.core.sdk.WTException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.SearchResult;
import org.ldaptive.io.LdifReader;
import com.sonicle.webtop.core.app.io.BeanHandler;

/**
 *
 * @author malbinola
 */
public class LDIFInput {
	private LogHandler logHandler = null;
	private BeanHandler<ContactInput> beanHandler = null;
	
	public LDIFInput withLogHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
		return this;
	}
	
	public LDIFInput withBeanHandler(BeanHandler<ContactInput> beanHandler) {
		this.beanHandler = beanHandler;
		return this;
	}
	
	public List<ContactInput> parseLDIF(File file) throws IOException, WTException {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			final LdifReader ldifReader = new LdifReader(fr);
			SearchResult resLDIF = ldifReader.read();
			return parseLDIFObjects(resLDIF.getEntries());
			
		} finally {
			IOUtils.closeQuietly(fr);
		}
	}
	
	public List<ContactInput> parseLDIF(InputStream is) throws IOException, WTException {
		final LdifReader ldifReader = new LdifReader(new InputStreamReader(is));
		SearchResult resLDIF = ldifReader.read();
		return parseLDIFObjects(resLDIF.getEntries());
	}
	
	public List<ContactInput> parseLDIFObjects(Collection<LdapEntry> ldapEntries) throws WTException {
		ArrayList<ContactInput> results = (beanHandler == null) ? new ArrayList<>() : null;
		
		int count = 0;
		for (LdapEntry ldapEntry : ldapEntries) {
			count++;
			BufferingLogHandler buffLogHandler = createBufferingLogHandler(new LogMessage(0, LogEntry.Level.INFO, "LDIF #{} [{}]", count, ldapEntry.toString()));
			
			try {
				final ContactInput result = parseLDIFObject(ldapEntry, buffLogHandler);
				if (result.contact.trimFieldLengths()) {
					log(buffLogHandler, 1, LogEntry.Level.WARN, "Some fields were truncated due to max-length");
				}
				if (beanHandler != null) {
					beanHandler.handle(result);
				} else if (results != null) {
					results.add(result);
				}

			} catch(Throwable t) {
				log(buffLogHandler, 0, LogEntry.Level.ERROR, "Reason: {}", LangUtils.getThrowableMessage(t));
			}
			
			flushToLogHandler(buffLogHandler);
		}
		return results;
	}
	
	public ContactInput parseLDIFObject(LdapEntry ldapEntry) throws WTException {
		return parseLDIFObject(ldapEntry, null);
	}
	
	private ContactInput parseLDIFObject(LdapEntry ldapEntry, LogHandler logHandler) throws WTException {
		ContactBase contact = new ContactBase();

		if (ldapEntry.getAttribute("givenName") != null) { // FirstName
			contact.setFirstName(getAttributeValueAsString(ldapEntry.getAttribute("givenName")));
		}
		if (ldapEntry.getAttribute("sn") != null) { // LastName
			contact.setLastName(getAttributeValueAsString(ldapEntry.getAttribute("sn")));
		}
		if (ldapEntry.getAttribute("cn") != null) { // DisplayName
			contact.setDisplayName(getAttributeValueAsString(ldapEntry.getAttribute("cn")));
		}
		if (ldapEntry.getAttribute("mozillaNickname") != null) { // Nickname
			contact.setNickname(getAttributeValueAsString(ldapEntry.getAttribute("mozillaNickname")));
		}
		if (ldapEntry.getAttribute("mobile") != null) {
			contact.setMobile(getAttributeValueAsString(ldapEntry.getAttribute("mobile")));
		}
		if (ldapEntry.getAttribute("mail") != null) {
			contact.setEmail1(getAttributeValueAsString(ldapEntry.getAttribute("mail")));
		}
		if (ldapEntry.getAttribute("mozillaSecondEmail") != null) {
			contact.setEmail2(getAttributeValueAsString(ldapEntry.getAttribute("mozillaSecondEmail")));
		}
		if (ldapEntry.getAttribute("street") != null) { // Address
			contact.setWorkAddress(getAttributeValueAsString(ldapEntry.getAttribute("street")));
		}
		if (ldapEntry.getAttribute("postalCode") != null) { // Postal Code
			contact.setWorkPostalCode(getAttributeValueAsString(ldapEntry.getAttribute("postalCode")));
		}
		if (ldapEntry.getAttribute("l") != null) { // City
			contact.setWorkCity(getAttributeValueAsString(ldapEntry.getAttribute("l")));
		}
		if (ldapEntry.getAttribute("st") != null) { // State/province
			contact.setWorkState(getAttributeValueAsString(ldapEntry.getAttribute("st")));
		}
		if (ldapEntry.getAttribute("c") != null) { // Country
			contact.setWorkCountry(getAttributeValueAsString(ldapEntry.getAttribute("c")));
		}
		if (ldapEntry.getAttribute("telephoneNumber") != null) {
			contact.setWorkTelephone1(getAttributeValueAsString(ldapEntry.getAttribute("telephoneNumber")));
		}
		if (ldapEntry.getAttribute("facsimileTelephoneNumber") != null) {
			contact.setWorkFax(getAttributeValueAsString(ldapEntry.getAttribute("facsimileTelephoneNumber")));
		}
		if (ldapEntry.getAttribute("mozillaHomeStreet") != null) { // Home Address
			contact.setHomeAddress(getAttributeValueAsString(ldapEntry.getAttribute("mozillaHomeStreet")));
		}
		if (ldapEntry.getAttribute("mozillaHomePostalCode") != null) { // Home Postal Code
			contact.setHomePostalCode(getAttributeValueAsString(ldapEntry.getAttribute("mozillaHomePostalCode")));
		}
		if (ldapEntry.getAttribute("mozillaHomeLocalityName") != null) { // Home City
			contact.setHomeCity(getAttributeValueAsString(ldapEntry.getAttribute("mozillaHomeLocalityName")));
		}
		if (ldapEntry.getAttribute("mozillaHomeState") != null) { // Home State/province	
			contact.setHomeState(getAttributeValueAsString(ldapEntry.getAttribute("mozillaHomeState")));
		}
		if (ldapEntry.getAttribute("mozillaHomeCountryName") != null) { // Home Country
			contact.setHomeCountry(getAttributeValueAsString(ldapEntry.getAttribute("mozillaHomeCountryName")));
		}
		if (ldapEntry.getAttribute("homePhone") != null) {
			contact.setHomeTelephone1(getAttributeValueAsString(ldapEntry.getAttribute("homePhone")));
		}
		if (ldapEntry.getAttribute("title") != null) { // Job title
			contact.setFunction(getAttributeValueAsString(ldapEntry.getAttribute("title")));
		}
		if (ldapEntry.getAttribute("ou") != null) { // Department
			contact.setDepartment(getAttributeValueAsString(ldapEntry.getAttribute("ou")));
		}
		if (ldapEntry.getAttribute("birthday") != null && ldapEntry.getAttribute("birthmonth") != null && ldapEntry.getAttribute("birthyear") != null) {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			contact.setBirthday(JodaTimeUtils.parseLocalDate(fmt, ldapEntry.getAttribute("birthday").getStringValue() + "/" + ldapEntry.getAttribute("birthmonth").getStringValue() + "/" + ldapEntry.getAttribute("birthyear").getStringValue()));
		}
		if (ldapEntry.getAttribute("description") != null) {
			contact.setNotes(getAttributeValueAsString(ldapEntry.getAttribute("description")));
		}
		return new ContactInput(contact, null, null, null, null, null);
	}
	
	private String getAttributeValueAsString(LdapAttribute attribute) {
		if (attribute.isBinary()) {
			return new String(attribute.getBinaryValue());
		} else {
			return attribute.getStringValue();
		}
	}
	
	private BufferingLogHandler createBufferingLogHandler(LogMessage firstLogMessage) {
		if (logHandler != null) {
			return new BufferingLogHandler() {
				@Override
				public List<LogEntry> first() {
					return Arrays.asList(firstLogMessage);
				}
			};
		} else {
			return null;
		}
	}
	
	private void flushToLogHandler(BufferingLogHandler bufferingLogHandler) {
		if (logHandler != null && bufferingLogHandler != null) {
			final List<LogEntry> entries = bufferingLogHandler.flush();
			if (entries != null) logHandler.handle(entries);
		}
	}
	
	private void log(LogHandler handler, int depth, LogEntry.Level level, String message, Object... arguments) {
		if (handler != null) {
			try {
				handler.handle(new LogMessage(depth, level, message, arguments));
			} catch(Throwable t) {}
		}
	}
}
