/*******************************************************************************
 *  Copyright (c) 2007, 2017 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.frameworkadmin.tests;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	private static BundleContext ctx;

	@Override
	public void start(BundleContext context) throws Exception {
		ctx = context; 

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ctx = null;
	}

	public static BundleContext getContext() {
		return ctx;
	}
}
