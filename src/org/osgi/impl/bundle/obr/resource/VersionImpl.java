/*
 * $Id: AbstractURLStreamHandlerService.java 4729 2007-04-06 02:38:06Z bjhargrave $
 * 
 * Copyright (c) OSGi Alliance (2002, 2006, 2007). All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgi.impl.bundle.obr.resource;

import java.util.regex.*;

import org.osgi.framework.*;

public class VersionImpl {
	Version			high;
	Version			low;
	char			start	= '[';
	char			end		= ']';

	static String	V		= "[0-9]+(\\.[0-9]+(\\.[0-9]+(\\.[a-zA-Z0-9_-]+)?)?)?";
	static Pattern	RANGE	= Pattern.compile("(\\(|\\[)(" + V + "),(" + V +
									")(\\)|\\])");

	public VersionImpl(String string) {
		string = string.trim();
		Matcher m = RANGE.matcher(string);
		if (m.matches()) {
			start = m.group(1).charAt(0);
			low = new Version(m.group(2));
			high = new Version(m.group(6));
			end = m.group(10).charAt(0);
			if ( low.compareTo(high) >=0 )
				throw new IllegalArgumentException("Low Range is higher than High Range: " + low + "-" + high );
			
		}
		else
			high = low = new Version(string);
	}


	public boolean isRange() {
		return high != low;
	}
	
	public boolean includeLow() {
		return start == '[';
	}
	
	public boolean includeHigh() {
		return start == ']';
	}
	
	public String toString() {
		if ( high == low ) 
			return high.toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append(start);
		sb.append(low);
		sb.append(',');
		sb.append(high);
		sb.append(end);		
		return sb.toString();
	}
}