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

package org.apache.isis.core.metamodel.facets.object.mixin;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.services.ServicesInjector;

public class MixinFacetForDomainObjectAnnotation extends MixinFacetAbstract {

    public static Class<? extends Facet> type() {
        return MixinFacet.class;
    }

    private MixinFacetForDomainObjectAnnotation(
            final Class<?> mixinType,
            final String value, final Class<?> constructorType,
            final FacetHolder holder,
            final ServicesInjector servicesInjector) {
        super(mixinType, value, constructorType, holder, servicesInjector);
    }

    public static MixinFacet create(
            final List<DomainObject> domainObjects,
            final Class<?> candidateMixinType,
            final FacetHolder facetHolder,
            final ServicesInjector servicesInjector) {
        return domainObjects.stream()
                .filter(domainObject -> domainObject.nature() == Nature.MIXIN)
                .findFirst()
                .map(domainObject -> {
                    final Constructor<?>[] constructors = candidateMixinType.getConstructors();
                    for (Constructor<?> constructor : constructors) {
                        final Class<?>[] constructorTypes = constructor.getParameterTypes();
                        if(constructorTypes.length != 1) {
                            continue;
                        }
                        final Class<?> constructorType = constructorTypes[0];
                        return new MixinFacetForDomainObjectAnnotation(
                                        candidateMixinType, domainObject.mixinMethod(), constructorType, facetHolder,
                                        servicesInjector);
                    }
                    // else
                    return null;
                })
                .orElse(null);
    }

}
