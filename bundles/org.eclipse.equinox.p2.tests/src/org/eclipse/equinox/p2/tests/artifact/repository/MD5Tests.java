/*******************************************************************************
 *  Copyright (c) 2005, 2008 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.p2.tests.artifact.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.internal.p2.metadata.ArtifactKey;
import org.eclipse.equinox.internal.provisional.p2.artifact.repository.*;
import org.eclipse.equinox.internal.provisional.p2.core.ProvisionException;
import org.eclipse.equinox.internal.provisional.p2.core.Version;
import org.eclipse.equinox.internal.provisional.p2.metadata.IArtifactKey;
import org.eclipse.equinox.p2.tests.AbstractProvisioningTest;

public class MD5Tests extends AbstractProvisioningTest {
	File testRepo = null;
	IArtifactRepository repo = null;

	protected void setUp() throws Exception {
		super.setUp();
		testRepo = getTestData("Repository with MD5", "testData/artifactRepo/simpleWithMD5");
		repo = getArtifactRepositoryManager().loadRepository(testRepo.toURI(), new NullProgressMonitor());
		assertNotNull("1.0", repo);
	}

	public void testCheckMD5() {
		IArtifactKey[] keys = repo.getArtifactKeys();
		for (int i = 0; i < keys.length; i++) {
			IArtifactDescriptor[] desc = repo.getArtifactDescriptors(keys[i]);
			for (int j = 0; j < desc.length; j++) {
				IStatus status = repo.getArtifact(desc[j], new ByteArrayOutputStream(500), new NullProgressMonitor());
				//All artifacts that are expected to fail MD5 check are those whose id starts with bogus
				if (desc[j].getArtifactKey().getId().startsWith("bogus")) {
					assertNotOK(status);
					continue;
				}
				assertOK("2.1 " + desc[j], status);
			}
		}
	}

	public void testBug249035_ArtifactIdentity() {
		//MD5 sum should not affect the identity of the artifact

		ArtifactDescriptor descriptor = new ArtifactDescriptor(new ArtifactKey("osgi.bundle", "aaPlugin", new Version("1.0.0")));
		descriptor.setProperty(IArtifactDescriptor.DOWNLOAD_MD5, "42");

		try {
			repo.getOutputStream(descriptor);
			fail("3.1 - Expected Artifact exists exception did not occur.");
		} catch (ProvisionException e) {
			assertTrue("3.2", e.getMessage().contains("The artifact is already available in the repository"));
		}
	}

	protected void tearDown() throws Exception {
		getArtifactRepositoryManager().removeRepository(testRepo.toURI());
		super.tearDown();
	}
}
