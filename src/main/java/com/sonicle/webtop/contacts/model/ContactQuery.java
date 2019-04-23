/*
 * Copyright (C) 2019 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2019 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.github.rutledgepaulv.qbuilders.builders.QBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.properties.concrete.StringProperty;
import com.sonicle.commons.web.json.bean.QueryObj;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author malbinola
 */
public class ContactQuery extends QBuilder<ContactQuery> {
		
	public StringProperty<ContactQuery> name() {
		return string("name");
	}

	public StringProperty<ContactQuery> company() {
		return string("company");
	}

	public StringProperty<ContactQuery> email() {
		return string("email");
	}

	public StringProperty<ContactQuery> phone() {
		return string("phone");
	}

	public StringProperty<ContactQuery> everywhere() {
		return string("everywhere");
	}
	
	/**
	 * Only for backward compatibility: it make possible to use like pattern 
	 * as query source. (remove when transition to new code is done)
	 * @param pattern
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public static Condition<ContactQuery> toCondition(String pattern) {
		Condition<ContactQuery> result = null;
		if (!StringUtils.isBlank(pattern)) {
			ContactQuery q = (result == null) ? new ContactQuery() : result.and();
			result = q.everywhere().eq(StringUtils.replace(pattern, "%", "*"));
		}
		return result;
	}
	
	public static Condition<ContactQuery> toCondition(QueryObj query) {
		Condition<ContactQuery> result = null;
		for (QueryObj.Condition queryCondition : query.conditions) {
			ContactQuery q = (result == null) ? new ContactQuery() : result.and();
			switch(queryCondition.keyword) {
				case "name":
					result = q.name().eq(queryCondition.value);
					break;
				case "company":
					result = q.company().eq(queryCondition.value);
					break;
				case "email":
					result = q.email().eq(queryCondition.value);
					break;
				case "phone":
					result = q.phone().eq(queryCondition.value);
					break;
				default:
					throw new UnsupportedOperationException(queryCondition.keyword);
			}
		}
		
		if (!StringUtils.isBlank(query.allText)) {
			ContactQuery q = (result == null) ? new ContactQuery() : result.and();
			result = q.everywhere().eq(query.allText);
		}
		
		return result;
	}
}
