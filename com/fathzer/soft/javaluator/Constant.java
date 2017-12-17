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

/**
 * A constant in an expression.
 * <br>Some expressions needs constants. For instance it is impossible to perform trigonometric calculus without using pi.
 * A constant allows you to use mnemonic in your expressions instead of the raw value of the constant.
 * <br>A constant for pi would be defined by :<br>
 * <code>Constant&lt;Double&gt; pi = new Constant&lt;Double&gt;("pi");</code>
 * <br>With such a constant, you will be able to evaluate the expression "sin(pi/4)"
 * @author Jean-Marc Astesana
 * @see <a href="license.html">License information</a>
 * @see AbstractEvaluator#evaluate(Constant, Object)
 */
public class Constant {
	private String name;
	
	/** Constructor
	 * @param name The mnemonic of the constant.
	 * <br>The name is used in expressions to identified the constants.
	 */
	public Constant(String name) {
		this.name = name;
	}

	/** Gets the mnemonic of the constant.
	 * @return the id
	 */
	public String getName() {
		return name;
	}
}
