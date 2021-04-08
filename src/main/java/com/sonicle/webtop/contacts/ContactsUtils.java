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
package com.sonicle.webtop.contacts;

import com.sonicle.commons.Base58;
import com.sonicle.commons.IdentifierUtils;
import com.sonicle.commons.InternetAddressUtils;
import com.sonicle.commons.beans.VirtualAddress;
import static com.sonicle.webtop.contacts.IContactsManager.RCPT_ORIGIN_LIST;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.VCardUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.mail.internet.InternetAddress;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author malbinola
 */
public class ContactsUtils {
	private static final Pattern PATTERN_VIRTUALRCPT_LIST = Pattern.compile("^" + RCPT_ORIGIN_LIST + "-(\\d+)$");
	public static final String CARDDAV_ADDRESSBOOK_URL = "/addressbooks/{0}/{1}";
	public static final String CATEGORY_LINK_CARDDAV = "cardDav";
	
	public static int decodeAsCategoryId(String categoryPublicUid) throws WTException {
		try {
			return Integer.valueOf(new String(Base58.decode(categoryPublicUid)));
		} catch(RuntimeException ex) { // Not a Base58 input
			throw new WTException(ex, "Invalid category UID encoding");
		}
	}
	
	public static String encodeAsCategoryUid(int categoryId) {
		return Base58.encode(StringUtils.leftPad(String.valueOf(categoryId), 10, "0").getBytes());
	}
	
	public static String buildHref(String publicUid) {
		return publicUid + ".vcf";
	}
	
	public static String buildContactUid(int contactId, String internetName) {
		String id = IdentifierUtils.getUUIDTimeBased(true) + "." + String.valueOf(contactId);
		return VCardUtils.buildUid(DigestUtils.md5Hex(id), internetName);
	}
	
	public static boolean isListVirtualRecipient(InternetAddress ia) {
		return StringUtils.startsWithIgnoreCase(ia.getAddress(), RCPT_ORIGIN_LIST + "-") && StringUtils.endsWithIgnoreCase(ia.getAddress(), "@com.sonicle.webtop.contacts");
	}
	
	public static boolean isListVirtualRecipient(String virtualRecipientAddress) {
		InternetAddress ia = InternetAddressUtils.toInternetAddress(virtualRecipientAddress);
		return ia != null ? isListVirtualRecipient(ia) : false;
	}
	
	public static Integer virtualRecipientToListId(InternetAddress ia) {
		return virtualRecipientToListId(new VirtualAddress(ia.getAddress()).getLocal());
	}
	
	/**
	 * Tries to extract a contactList ID from passed virtualAddress. For example
	 * if recipient is "list-1234", value "1234" is returned. Null in case of no match.
	 * @param virtualRecipient
	 * @return 
	 */
	public static Integer virtualRecipientToListId(String virtualRecipient) {
		Matcher matcher = PATTERN_VIRTUALRCPT_LIST.matcher(virtualRecipient);
		return matcher.matches() ? Integer.valueOf(matcher.group(1)) : null;
	}
}
