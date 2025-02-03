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

import com.sonicle.commons.qbuilders.conditions.Condition;
import com.sonicle.commons.qbuilders.properties.concrete.StringProperty;
import com.sonicle.commons.web.json.CId;
import com.sonicle.commons.web.json.bean.QueryObj;
import com.sonicle.webtop.core.app.sdk.QueryBuilderWithCValues;
import com.sonicle.webtop.core.app.sdk.WTUnsupportedOperationException;
import com.sonicle.webtop.core.model.CustomField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;

/**
 * @deprecated use ContactQueryUI instead
 * @author malbinola
 */
@Deprecated
public class ContactQueryUI_OLD extends QueryBuilderWithCValues<ContactQueryUI_OLD> {
	
	public StringProperty<ContactQueryUI_OLD> id() {
		return string("id");
	}
	
	public StringProperty<ContactQueryUI_OLD> name() {
		return string("name");
	}

	public StringProperty<ContactQueryUI_OLD> company() {
		return string("company");
	}
	
	public StringProperty<ContactQueryUI_OLD> companyId() {
		return string("companyId");
	}

	public StringProperty<ContactQueryUI_OLD> email() {
		return string("email");
	}

	public StringProperty<ContactQueryUI_OLD> phone() {
		return string("phone");
	}
	
	public StringProperty<ContactQueryUI_OLD> address() {
		return string("address");
	}
	
	public StringProperty<ContactQueryUI_OLD> notes() {
		return string("notes");
	}

	public StringProperty<ContactQueryUI_OLD> any() {
		return string("any");
	}
	
	public StringProperty<ContactQueryUI_OLD> tag() {
		return string("tag");
	}
	
	public static Condition<ContactQueryUI_OLD> createCondition(String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			return new ContactQueryUI_OLD().any().eq(StringUtils.replace(pattern, "%", "*"));
		} else {
			return null;
		}
	}
	
	public static Condition<ContactQueryUI_OLD> createCondition(QueryObj query, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		boolean smartStringComparison = true;
		
		Condition<ContactQueryUI_OLD> last = new ContactQueryUI_OLD().selfCondition();
		for (Map.Entry<QueryObj.Condition, List<String>> entry : query.groupConditions().entrySet()) {
			final QueryObj.Condition key = entry.getKey();
			final List<String> values = entry.getValue();
			
			if (values.isEmpty() || values.size() == 1) {
				last = new ContactQueryUI_OLD().and(last, createCondition(key, values.isEmpty() ? null : values.get(0), customFieldTypeMapping, timezone, smartStringComparison));
			} else {
				List<Condition<ContactQueryUI_OLD>> conds = new ArrayList<>();
				for (String value : values) {
					conds.add(createCondition(key, value, customFieldTypeMapping, timezone, smartStringComparison));
				}
				last = new ContactQueryUI_OLD().and(last, new ContactQueryUI_OLD().or(conds));
			}
		}
		
		if (!StringUtils.isBlank(query.getAllText())) {
			String[] values = asStringValues(query.getAllText(), smartStringComparison);
			return new ContactQueryUI_OLD().and(last, combineFieldValuesAsCondition(values, v -> new ContactQueryUI_OLD().any().eq(v)));
		} else {
			return last;
		}
	}
	
	private static Condition<ContactQueryUI_OLD> combineFieldValuesAsCondition(final String[] values, final QueryFieldConditionCreatorMethod<ContactQueryUI_OLD, String> creator) {
		if (values.length == 1) {
			return creator.create(values[0]);
		} else {
			List<Condition<ContactQueryUI_OLD>> conds = new ArrayList<>(values.length);
			for (String value : values) {
				conds.add(creator.create(value));
			}
			return new ContactQueryUI_OLD().or(conds);
		}
	}
	
	private static Condition<ContactQueryUI_OLD> createCondition(QueryObj.Condition condition, String value, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone, boolean smartStringComparison) {
		if ("id".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().id().eq(asStringValue(value, false));

		} else if ("name".equals(condition.keyword)) {
			String[] values = asStringValues(value, smartStringComparison);
			return combineFieldValuesAsCondition(values, v -> new ContactQueryUI_OLD().name().eq(v));

		} else if ("company".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().company().eq(asStringValue(value, smartStringComparison));

		} else if ("companyId".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().companyId().eq(value);

		} else if ("email".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().email().eq(asStringValue(value, smartStringComparison));

		} else if ("phone".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().phone().eq(asStringValue(value, smartStringComparison));

		} else if ("address".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().address().eq(asStringValue(value, smartStringComparison));

		} else if ("notes".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().notes().eq(asStringValue(value, smartStringComparison));

		} else if ("tag".equals(condition.keyword)) {
			return new ContactQueryUI_OLD().tag().eq(value);

		} else if (StringUtils.startsWith(condition.keyword, "cfield")) {
			CId cf = new CId(condition.keyword, 2);
			if (!cf.isTokenEmpty(1)) {
				String cfId = cf.getToken(1);
				if (customFieldTypeMapping.containsKey(cfId)) {
					return new ContactQueryUI_OLD().customValueCondition(cfId, customFieldTypeMapping.get(cfId), value, condition.negated, smartStringComparison, timezone);
				}
			}
		}
		
		throw new WTUnsupportedOperationException("Unsupported keyword '{}'", condition.keyword);
	}
}
