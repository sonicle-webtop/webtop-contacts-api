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
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;

/**
 *
 * @author malbinola
 */
public class ContactQuery extends QueryBuilderWithCValues<ContactQuery> {
	
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
	
	public static Condition<ContactQuery> toCondition(String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			return new ContactQuery().any().eq(StringUtils.replace(pattern, "%", "*"));
		} else {
			return null;
		}
	}
	
	public static Condition<ContactQuery> toCondition(QueryObj query, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		boolean smartStringComparison = true;
		ContactQuery q = new ContactQuery();
		
		Condition<ContactQuery> last = q.trueCondition();
		for (Map.Entry<String, Collection<QueryObj.Condition>> entry : query.getConditionsMap().entrySet()) {
			q = last.and();
			int pos = 0;
			for (QueryObj.Condition queryCondition : entry.getValue()) {
				pos++;
				if (pos > 1) q = last.or();
				
				if ("name".equals(queryCondition.keyword)) {
					last = q.name().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("company".equals(queryCondition.keyword)) {
					last = q.company().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("email".equals(queryCondition.keyword)) {
					last = q.email().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("phone".equals(queryCondition.keyword)) {
					last = q.phone().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("address".equals(queryCondition.keyword)) {
					last = q.address().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("notes".equals(queryCondition.keyword)) {
					last = q.notes().eq(asStringValue(queryCondition.value, smartStringComparison));
					
				} else if ("tag".equals(queryCondition.keyword)) {
					last = q.tag().eq(queryCondition.value);
					
				} else if (StringUtils.startsWith(queryCondition.keyword, "cfield")) {
					CId cf = new CId(queryCondition.keyword, 2);
					if (!cf.isTokenEmpty(1)) {
						String cfId = cf.getToken(1);
						if (customFieldTypeMapping.containsKey(cfId)) {
							last = q.customValueCondition(cfId, customFieldTypeMapping.get(cfId), queryCondition.value, queryCondition.negated, smartStringComparison, timezone);
						}
					}			
					
				} else {
					throw new WTUnsupportedOperationException("Unsupported keyword '{}'", queryCondition.keyword);
				}
			}
		}
		
		if (!StringUtils.isBlank(query.allText)) {
			return last.and().any().eq(asStringValue(query.allText, smartStringComparison));
		} else {
			return last;
		}
	}
	
	/*
	public static Condition<ContactQuery> toCondition(QueryObj query, Map<String, CustomField.Type> customFieldTypeMapping, DateTimeZone timezone) {
		boolean smartStringComparison = true;
		Condition<ContactQuery> result = null;
		
		for (Map.Entry<String, Collection<QueryObj.Condition>> entry : query.getConditionsMap().entrySet()) {
			ContactQuery q = (result == null) ? new ContactQuery() : result.and();
			
			ArrayList<Condition<ContactQuery>> cndts = new ArrayList<>();
			for (QueryObj.Condition queryCondition : entry.getValue()) {
				if ("name".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().name().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("company".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().company().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("email".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().email().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("phone".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().phone().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("address".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().address().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("notes".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().notes().eq(asStringValue(queryCondition.value, smartStringComparison)));
					
				} else if ("tag".equals(queryCondition.keyword)) {
					cndts.add(new ContactQuery().tag().eq(queryCondition.value));
					
				} else if (StringUtils.startsWith(queryCondition.keyword, "cfield")) {
					Condition<ContactQuery> cond = null;
					CompId cf = new CompId(2).parse(queryCondition.keyword, false);
					if (!cf.isTokenEmpty(1)) {
						String cfId = cf.getToken(1);
						if (customFieldTypeMapping.containsKey(cfId)) {
							cond = new ContactQuery().customValueCondition(cfId, customFieldTypeMapping.get(cfId), queryCondition.value, queryCondition.negated, timezone);
						}
					}
					if (cond != null) cndts.add(cond);					
					
				} else {
					throw new WTUnsupportedOperationException("Unsupported keyword '{}'", queryCondition.keyword);
				}
			}
			result = q.or(cndts);
		}
		
		if (!StringUtils.isBlank(query.allText)) {
			ContactQuery q = (result == null) ? new ContactQuery() : result.and();
			result = q.any().eq(asStringValue(query.allText, smartStringComparison));
		}
		
		return result;
	}
	*/
}
