/*
 * Copyright (C) 2024 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2024 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.core.app.sdk.WTParseException;
import java.util.ArrayList;
import net.sf.qualitycheck.Check;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author malbinola
 * @param <T>
 */
public class Delta<T> {
	public static final DateTimeFormatter SYNCTOKEN_FMT = DateTimeUtils.createFormatter("yyyyMMddHHmmssSSS", DateTimeZone.UTC);
	private final String nextSyncToken;
	private final ArrayList<ChangedItem<T>> items;
	
	public Delta(String nextSyncToken, ArrayList<ChangedItem<T>> items) {
		this.nextSyncToken = Check.notNull(nextSyncToken, "nextSyncToken");
		this.items = Check.notNull(items, "items");
	}
	
	public Delta(DateTime lastSyncTimestamp, ArrayList<ChangedItem<T>> items) {
		this(printSyncToken(lastSyncTimestamp), items);
	}

	public String getNextSyncToken() {
		return nextSyncToken;
	}

	public ArrayList<ChangedItem<T>> getItems() {
		return items;
	}
	
	public static DateTime parseSyncToken(final String syncToken, final boolean silent) throws WTParseException {
		final DateTime datetime = !StringUtils.isBlank(syncToken) ? SYNCTOKEN_FMT.parseDateTime(syncToken) : null;
		if (datetime == null && !silent) {
			throw new WTParseException("Unable to parse '{}' as syncToken", syncToken);
		}
		return datetime;
	}
	
	public static String printSyncToken(final DateTime timestamp) {
		return timestamp != null ? SYNCTOKEN_FMT.print(timestamp) : null;
	}
}
