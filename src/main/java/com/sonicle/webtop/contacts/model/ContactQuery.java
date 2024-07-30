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

import com.sonicle.commons.qbuilders.builders.QBuilder;
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
 *
 * @author malbinola
 */
public class ContactQuery extends QueryBuilderWithCValues<ContactQuery> {
	
	public StringProperty<ContactQuery> id() {
		return string("id");
	}
	
	public StringProperty<ContactQuery> name() {
		return string("name");
	}

	public StringProperty<ContactQuery> company() {
		return string("company");
	}
	
	public StringProperty<ContactQuery> companyId() {
		return string("companyId");
	}

	public StringProperty<ContactQuery> email() {
		return string("email");
	}

	public StringProperty<ContactQuery> phone() {
		return string("phone");
	}
	
	public StringProperty<ContactQuery> address() {
		return string("address");
	}
	
	public StringProperty<ContactQuery> notes() {
		return string("notes");
	}

	public StringProperty<ContactQuery> any() {
		return string("any");
	}
	
	public StringProperty<ContactQuery> tag() {
		return string("tag");
	}
	
	public static Condition<ContactQuery> createCondition(String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			return new ContactQuery().any().eq(StringUtils.replace(pattern, "%", "*"));
		} else {
			return null;
		}
	}
	
	public static Condition<ContactQuery> createCondition(QueryObj query, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		boolean smartStringComparison = true;
		
		Condition<ContactQuery> last = new ContactQuery().trueCondition();
		for (Map.Entry<QueryObj.Condition, List<String>> entry : query.groupConditions().entrySet()) {
			final QueryObj.Condition key = entry.getKey();
			final List<String> values = entry.getValue();
			
			if (values.isEmpty() || values.size() == 1) {
				last = new ContactQuery().and(last, createCondition(key, values.isEmpty() ? null : values.get(0), customFieldTypeMapping, timezone, smartStringComparison));
			} else {
				List<Condition<ContactQuery>> conds = new ArrayList<>();
				for (String value : values) {
					conds.add(createCondition(key, value, customFieldTypeMapping, timezone, smartStringComparison));
				}
				last = new ContactQuery().and(last, new ContactQuery().or(conds));
			}
		}
		
		if (!StringUtils.isBlank(query.getAllText())) {
			String[] values = toConditionStringValues(query.getAllText(), smartStringComparison);
			return new ContactQuery().and(last, combineFieldValuesAsCondition(values, v -> new ContactQuery().any().eq(v)));
		} else {
			return last;
		}
	}
	
	private static Condition<ContactQuery> combineFieldValuesAsCondition(final String[] values, final QueryFieldConditionCreatorMethod<ContactQuery, String> creator) {
		if (values.length == 1) {
			return creator.create(values[0]);
		} else {
			List<Condition<ContactQuery>> conds = new ArrayList<>(values.length);
			for (String value : values) {
				conds.add(creator.create(value));
			}
			return new ContactQuery().or(conds);
		}
	}
	
	private static Condition<ContactQuery> createCondition(QueryObj.Condition condition, String value, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone, boolean smartStringComparison) {
		if ("id".equals(condition.keyword)) {
			return new ContactQuery().id().eq(asStringValue(value, false));

		} else if ("name".equals(condition.keyword)) {
			String[] values = toConditionStringValues(value, smartStringComparison);
			return combineFieldValuesAsCondition(values, v -> new ContactQuery().name().eq(v));

		} else if ("company".equals(condition.keyword)) {
			return new ContactQuery().company().eq(asStringValue(value, smartStringComparison));

		} else if ("companyId".equals(condition.keyword)) {
			return new ContactQuery().companyId().eq(value);

		} else if ("email".equals(condition.keyword)) {
			return new ContactQuery().email().eq(asStringValue(value, smartStringComparison));

		} else if ("phone".equals(condition.keyword)) {
			return new ContactQuery().phone().eq(asStringValue(value, smartStringComparison));

		} else if ("address".equals(condition.keyword)) {
			return new ContactQuery().address().eq(asStringValue(value, smartStringComparison));

		} else if ("notes".equals(condition.keyword)) {
			return new ContactQuery().notes().eq(asStringValue(value, smartStringComparison));

		} else if ("tag".equals(condition.keyword)) {
			return new ContactQuery().tag().eq(value);

		} else if (StringUtils.startsWith(condition.keyword, "cfield")) {
			CId cf = new CId(condition.keyword, 2);
			if (!cf.isTokenEmpty(1)) {
				String cfId = cf.getToken(1);
				if (customFieldTypeMapping.containsKey(cfId)) {
					return new ContactQuery().customValueCondition(cfId, customFieldTypeMapping.get(cfId), value, condition.negated, smartStringComparison, timezone);
				}
			}
		}
		
		throw new WTUnsupportedOperationException("Unsupported keyword '{}'", condition.keyword);
	}
}
