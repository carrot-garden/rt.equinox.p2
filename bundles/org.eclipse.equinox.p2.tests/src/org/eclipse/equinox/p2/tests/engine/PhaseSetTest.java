/*******************************************************************************
 *  Copyright (c) 2007, 2009 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.p2.tests.engine;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.internal.provisional.p2.engine.*;
import org.eclipse.equinox.p2.tests.AbstractProvisioningTest;

/**
 * Simple test of the engine API.
 */
public class PhaseSetTest extends AbstractProvisioningTest {
	public PhaseSetTest(String name) {
		super(name);
	}

	public PhaseSetTest() {
		super("");
	}

	public void testNullPhases() {
		try {
			new PhaseSet(null) {
				// empty PhaseSet
			};
		} catch (IllegalArgumentException exepcted) {
			return;
		}
		fail();
	}

	public void testNoTrustCheck() {
		PhaseSet set1 = new DefaultPhaseSet();
		PhaseSet set2 = DefaultPhaseSet.createDefaultPhaseSet(DefaultPhaseSet.PHASE_CHECK_TRUST);
		assertTrue("1.0", !set1.equals(set2));
	}

	public void testEmptyPhases() {
		IProfile profile = createProfile("PhaseSetTest");
		PhaseSet phaseSet = new PhaseSet(new Phase[] {}) {
			// empty PhaseSet
		};
		InstallableUnitOperand op = new InstallableUnitOperand(createResolvedIU(createIU("iu")), null);
		InstallableUnitOperand[] operands = new InstallableUnitOperand[] {op};

		ProvisioningContext context = new ProvisioningContext();
		IStatus result = phaseSet.perform(null, new EngineSession(profile, null, context), profile, operands, context, new NullProgressMonitor());
		assertTrue(result.isOK());
	}
}
