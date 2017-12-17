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

/** An abstract variable set.
 * <br>Javaluator supports expression that contains variables (for example <i>sin(x)</i>).
 * <br>An AbstractVariableSet converts, during the expression evaluation, each variable to its value.
 * @param <T> The type of the values of the variable (the one handled by the evaluator).
 * @author Jean-Marc Astesana
 * @see <a href="license.html">License information</a>
 */
public interface AbstractVariableSet<T> {
	/** Gets the value of a variable.
	 * @param variableName The name of a variable
	 * @return the variable's value or null if the variable is unknown
	 */
	public T get(String variableName);
}
