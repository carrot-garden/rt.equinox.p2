/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.p2.tests.updatesite;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.equinox.internal.p2.updatesite.SiteXMLAction;
import org.eclipse.equinox.internal.provisional.p2.core.repository.IRepository;
import org.eclipse.equinox.internal.provisional.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.internal.provisional.p2.metadata.query.IUPropertyQuery;
import org.eclipse.equinox.internal.provisional.p2.query.Collector;
import org.eclipse.equinox.internal.provisional.p2.query.Query;
import org.eclipse.equinox.internal.provisional.spi.p2.metadata.repository.RepositoryReference;
import org.eclipse.equinox.p2.publisher.*;
import org.eclipse.equinox.p2.publisher.eclipse.FeaturesAction;
import org.eclipse.equinox.p2.tests.*;

/**
 * Tests for {@link org.eclipse.equinox.internal.p2.updatesite.SiteXMLAction}.
 */
public class SiteXMLActionTest extends AbstractProvisioningTest {
	private TestMetadataRepository metadataRepository;
	private IPublisherResult actionResult;
	private URI siteLocation;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		actionResult = new PublisherResult();
		PublisherInfo info = new PublisherInfo();
		metadataRepository = new TestMetadataRepository(new IInstallableUnit[0]);
		info.setMetadataRepository(metadataRepository);
		siteLocation = TestData.getFile("updatesite", "SiteXMLActionTest/site.xml").toURI();
		FeaturesAction featuresAction = new FeaturesAction(new File[] {TestData.getFile("updatesite", "SiteXMLActionTest")});
		featuresAction.perform(info, actionResult, new NullProgressMonitor());

		SiteXMLAction action = new SiteXMLAction(siteLocation, null);
		action.perform(info, actionResult, getMonitor());
	}

	public void testQualifier() {
		Query categoryQuery = new IUPropertyQuery(IInstallableUnit.PROP_TYPE_CATEGORY, Boolean.toString(true));
		Collector results = actionResult.query(categoryQuery, new Collector(), new NullProgressMonitor());
		Iterator iter = results.iterator();
		while (iter.hasNext()) {
			IInstallableUnit unit = (IInstallableUnit) iter.next();
			assertTrue("1.0", unit.getId().startsWith(URIUtil.toUnencodedString(siteLocation)));
			assertEquals("2.0", "Test Category Label", unit.getProperty(IInstallableUnit.PROP_NAME));
		}
	}

	/**
	 * Tests that associate sites are generated correctly.
	 */
	public void testAssociateSite() {
		Collection references = metadataRepository.getReferences();
		assertEquals("1.0", 2, references.size());
		boolean metadataFound = false, artifactFound = false;
		for (Iterator it = references.iterator(); it.hasNext();) {
			RepositoryReference ref = (RepositoryReference) it.next();
			assertEquals("1.1", "http://download.eclipse.org/eclipse/updates/3.5", ref.Location.toString());
			assertEquals("1.2", IRepository.ENABLED, ref.Options);
			assertEquals("1.3", "Eclipse Project Update Site", ref.Nickname);

			if (ref.Type == IRepository.TYPE_METADATA)
				metadataFound = true;
			else if (ref.Type == IRepository.TYPE_ARTIFACT)
				artifactFound = true;
		}
		assertTrue("1.3", metadataFound);
		assertTrue("1.4", artifactFound);
	}

	public void testMirrorsURL() {
		String mirrorsURL = (String) metadataRepository.getProperties().get(IRepository.PROP_MIRRORS_URL);
		assertEquals("1.0", "http://www.eclipse.org/downloads/download.php?file=/eclipse/updates/3.4&format=xml", mirrorsURL);
	}
}
