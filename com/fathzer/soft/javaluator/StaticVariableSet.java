/* 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License
 * <http://opensource.org/licenses/lgpl-3.0.html> for more details.
 */
package com.fathzer.soft.javaluator;

import java.util.HashMap;
import java.util.Map;

/** A static variable set.
 * <br>Here, static means that the values of variables are set before starting to evaluate the expressions.
 * @param <T> The type of the values of the variable (the one handled by the evaluator).
 * @author Jean-Marc Astesana
 * @see <a href="license.html">License information</a>
 */
public class StaticVariableSet<T> implements AbstractVariableSet<T> {
	private final Map<String, T> varToValue;
	
	/** Constructor.
	 * <br>Builds a new empty variable set.
	 */
	public StaticVariableSet() {
		this.varToValue = new HashMap<String, T>();
	}
	
	/** Gets the value of a variable.
	 * @param variableName The name of the variable.
	 * @return The value of the variable.
	 */
	public T get(String variableName) {
		return this.varToValue.get(variableName);
	}
	
	/** Sets a variable value.
	 * @param variableName The variable name
	 * @param value The variable value (null to remove a variable from the set).
	 */
	public void set(String variableName, T value) {
		this.varToValue.put(variableName, value);
	}
}
