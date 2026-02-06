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

import com.sonicle.commons.qbuilders.conditions.Condition;
import com.sonicle.commons.qbuilders.properties.concrete.StringProperty;
import com.sonicle.commons.web.json.CId;
import com.sonicle.commons.web.json.bean.QueryObj;
import com.sonicle.webtop.core.app.sdk.QueryBuilder;
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
public class ContactQueryUI extends ContactQuery {
	public static final String ANY = "any";
	public static final String ANY_COMPANY = "anyCompany";
	
	public StringProperty<ContactQuery> any() {
		return string(ANY);
	}
	
	public StringProperty<ContactQuery> anyCompany() {
		return string(ANY_COMPANY);
	}
	
	public static Condition<ContactQuery> build(String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			return new ContactQueryUI().any().like(StringUtils.replace(pattern, "%", "*"));
		} else {
			return null;
		}
	}
	
	public static Condition<ContactQuery> build(QueryObj query, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		Condition<ContactQuery> last = new ContactQueryUI().selfCondition();
		for (Map.Entry<QueryObj.Condition, List<String>> entry : query.groupConditions().entrySet()) {
			final QueryObj.Condition key = entry.getKey();
			final List<String> values = entry.getValue();
			
			if (values.isEmpty() || values.size() == 1) {
				last = new ContactQueryUI().and(last, createCondition(key, values.isEmpty() ? null : values.get(0), customFieldTypeMapping, timezone));
			} else {
				List<Condition<ContactQuery>> conds = new ArrayList<>();
				for (String value : values) {
					conds.add(createCondition(key, value, customFieldTypeMapping, timezone));
				}
				last = new ContactQueryUI().and(last, new ContactQuery().or(conds));
			}
		}
		
		if (!StringUtils.isBlank(query.getAllText())) {
			String[] values = asStringValues(query.getAllText(), true);
			return new ContactQueryUI().and(last, combineFieldValuesAsCondition(values, v -> new ContactQueryUI().any().like(v)));
		} else {
			return last;
		}
	}
	
	private static Condition<ContactQuery> combineFieldValuesAsCondition(final String[] values, final QueryBuilder.QueryFieldConditionCreatorMethod<ContactQuery, String> creator) {
		if (values.length == 1) {
			return creator.create(values[0]);
		} else {
			List<Condition<ContactQuery>> conds = new ArrayList<>(values.length);
			for (String value : values) {
				conds.add(creator.create(value));
			}
			return new ContactQueryUI().and(conds);
		}
	}
	
	private static Condition<ContactQuery> createCondition(QueryObj.Condition condition, String value, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		if ("id".equals(condition.keyword)) {
			return new ContactQueryUI().id().eq(asStringValue(value, false));

		} else if ("name".equals(condition.keyword)) {
			String[] values = asStringValues(value, true);
			return combineFieldValuesAsCondition(values, v -> new ContactQueryUI().anyName().like(v));

		} else if ("company".equals(condition.keyword)) {
			return new ContactQueryUI().anyCompany().like(asStringValue(value, true));

		} else if ("companyId".equals(condition.keyword)) {
			return new ContactQueryUI().companyId().eq(value);

		} else if ("email".equals(condition.keyword)) {
			return new ContactQueryUI().anyEmail().like(asStringValue(value, true));

		} else if ("phone".equals(condition.keyword)) {
			return new ContactQueryUI().anyPhone().like(asStringValue(value, true));

		} else if ("address".equals(condition.keyword)) {
			return new ContactQueryUI().anyAddress().like(asStringValue(value, true));

		} else if ("taxCode".equals(condition.keyword)) {
			return new ContactQueryUI().taxCode().like(asStringValue(value, true));

		} else if ("vatNumber".equals(condition.keyword)) {
			return new ContactQueryUI().vatNumber().like(asStringValue(value, true));

		} else if ("eInvoicingCode".equals(condition.keyword)) {
			return new ContactQueryUI().eInvoicingCode().like(asStringValue(value, true));

		} else if ("notes".equals(condition.keyword)) {
			return new ContactQueryUI().notes().like(asStringValue(value, true));

		} else if ("tag".equals(condition.keyword)) {
			return new ContactQueryUI().tagId().eq(value);

		} else if (StringUtils.startsWith(condition.keyword, "cfield")) {
			CId cf = new CId(condition.keyword, 2);
			if (!cf.isTokenEmpty(1)) {
				String cfId = cf.getToken(1);
				if (customFieldTypeMapping.containsKey(cfId)) {
					return new ContactQueryUI().customFieldCondition(cfId, customFieldTypeMapping.get(cfId), value, condition.negated, true, timezone);
				}
			}
		}
		
		throw new WTUnsupportedOperationException("Unsupported keyword '{}'", condition.keyword);
	}
}
