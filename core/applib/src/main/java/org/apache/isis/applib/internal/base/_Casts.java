/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.applib.internal.base;

import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

/**
 * <h1>- internal use only -</h1>
 * <p>
 * Casting Utilities
 * </p>
 * <p>
 * <b>WARNING</b>: Do <b>NOT</b> use any of the classes provided by this package! <br/> 
 * These may be changed or removed without notice!
 * </p>
 * 
 * @since 2.0.0
 */
public final class _Casts {

	private _Casts(){}
	
	@SuppressWarnings("unchecked")
	public static <T> T uncheckedCast(@Nullable Object obj) {
		return (T) obj;
	}

	/**
	 * Returns the casts of {@code value} to {@code cls}, or if this fails returns the result of {@code orElse}
	 * @param value
	 * @param cls
	 * @param orElse
	 * @return
	 */
	public static <T> T castToOrElse(@Nullable Object value, Class<T> cls, Supplier<T> orElse) {
		Objects.requireNonNull(cls);
		Objects.requireNonNull(orElse);

		try {
			return cls.cast(value);
		} catch (Exception e) {
			return orElse.get();	
		}
		
	}
	
}
