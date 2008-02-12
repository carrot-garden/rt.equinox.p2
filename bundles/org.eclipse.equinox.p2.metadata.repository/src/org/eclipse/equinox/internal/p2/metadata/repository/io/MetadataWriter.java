/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Genuitec, LLC - added license support
 *******************************************************************************/
package org.eclipse.equinox.internal.p2.metadata.repository.io;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.equinox.internal.p2.persistence.XMLWriter;
import org.eclipse.equinox.internal.provisional.p2.metadata.*;

public abstract class MetadataWriter extends XMLWriter implements XMLConstants {

	public MetadataWriter(OutputStream output, ProcessingInstruction[] piElements) throws UnsupportedEncodingException {
		super(output, piElements);
		// TODO: add a processing instruction for the metadata version
	}

	/**
	 * Writes a list of {@link IInstallableUnit}.
	 * @param units An Iterator of {@link IInstallableUnit}.
	 * @param size The number of units to write
	 */
	protected void writeInstallableUnits(Iterator units, int size) {
		if (size == 0)
			return;
		start(INSTALLABLE_UNITS_ELEMENT);
		attribute(COLLECTION_SIZE_ATTRIBUTE, size);
		while (units.hasNext())
			writeInstallableUnit((IInstallableUnit) units.next());
		end(INSTALLABLE_UNITS_ELEMENT);
	}

	protected void writeInstallableUnit(IInstallableUnit resolvedIU) {
		IInstallableUnit iu = resolvedIU.unresolved();
		start(INSTALLABLE_UNIT_ELEMENT);
		attribute(ID_ATTRIBUTE, iu.getId());
		attribute(VERSION_ATTRIBUTE, iu.getVersion());
		attribute(SINGLETON_ATTRIBUTE, iu.isSingleton(), true);
		attribute(FRAGMENT_ATTRIBUTE, iu.isFragment(), false);

		if (iu.isFragment() && iu instanceof IInstallableUnitFragment) {
			IInstallableUnitFragment fragment = (IInstallableUnitFragment) iu;
			attribute(FRAGMENT_HOST_ID_ATTRIBUTE, fragment.getHostId());
			attribute(FRAGMENT_HOST_RANGE_ATTRIBUTE, fragment.getHostVersionRange());
		}

		writeUpdateDescriptor(resolvedIU, resolvedIU.getUpdateDescriptor());
		writeProperties(iu.getProperties());
		writeProvidedCapabilities(iu.getProvidedCapabilities());
		writeRequiredCapabilities(iu.getRequiredCapabilities());
		writeTrimmedCdata(IU_FILTER_ELEMENT, iu.getFilter());
		writeTrimmedCdata(APPLICABILITY_FILTER_ELEMENT, iu.getApplicabilityFilter());

		writeArtifactKeys(iu.getArtifacts());
		writeTouchpointType(iu.getTouchpointType());
		writeTouchpointData(iu.getTouchpointData());
		writeLicenses(iu.getLicense());
		writeCopyright(iu.getCopyright());

		end(INSTALLABLE_UNIT_ELEMENT);
	}

	protected void writeProvidedCapabilities(ProvidedCapability[] capabilities) {
		if (capabilities != null && capabilities.length > 0) {
			start(PROVIDED_CAPABILITIES_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, capabilities.length);
			for (int i = 0; i < capabilities.length; i++) {
				start(PROVIDED_CAPABILITY_ELEMENT);
				attribute(NAMESPACE_ATTRIBUTE, capabilities[i].getNamespace());
				attribute(NAME_ATTRIBUTE, capabilities[i].getName());
				attribute(VERSION_ATTRIBUTE, capabilities[i].getVersion());
				end(PROVIDED_CAPABILITY_ELEMENT);
			}
			end(PROVIDED_CAPABILITIES_ELEMENT);
		}
	}

	protected void writeRequiredCapabilities(RequiredCapability[] capabilities) {
		if (capabilities != null && capabilities.length > 0) {
			start(REQUIRED_CAPABILITIES_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, capabilities.length);
			for (int i = 0; i < capabilities.length; i++) {
				writeRequiredCapability(capabilities[i]);
			}
			end(REQUIRED_CAPABILITIES_ELEMENT);
		}
	}

	protected void writeUpdateDescriptor(IInstallableUnit iu, IUpdateDescriptor descriptor) {
		if (descriptor == null)
			return;

		start(UPDATE_DESCRIPTOR_ELEMENT);
		attribute(ID_ATTRIBUTE, descriptor.getId());
		attribute(VERSION_RANGE_ATTRIBUTE, descriptor.getRange());
		attribute(UPDATE_DESCRIPTOR_SEVERITY, descriptor.getSeverity());
		attribute(DESCRIPTION_ATTRIBUTE, descriptor.getDescription());
		end(UPDATE_DESCRIPTOR_ELEMENT);

	}

	protected void writeRequiredCapability(RequiredCapability capability) {
		start(REQUIRED_CAPABILITY_ELEMENT);
		attribute(NAMESPACE_ATTRIBUTE, capability.getNamespace());
		attribute(NAME_ATTRIBUTE, capability.getName());
		attribute(VERSION_RANGE_ATTRIBUTE, capability.getRange());
		attribute(CAPABILITY_OPTIONAL_ATTRIBUTE, capability.isOptional(), false);
		attribute(CAPABILITY_MULTIPLE_ATTRIBUTE, capability.isMultiple(), false);

		writeTrimmedCdata(CAPABILITY_FILTER_ELEMENT, capability.getFilter());

		String[] selectors = capability.getSelectors();
		if (selectors.length > 0) {
			start(CAPABILITY_SELECTORS_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, selectors.length);
			for (int j = 0; j < selectors.length; j++) {
				writeTrimmedCdata(CAPABILITY_SELECTOR_ELEMENT, selectors[j]);
			}
			end(CAPABILITY_SELECTORS_ELEMENT);
		}

		end(REQUIRED_CAPABILITY_ELEMENT);
	}

	protected void writeArtifactKeys(IArtifactKey[] artifactKeys) {
		if (artifactKeys != null && artifactKeys.length > 0) {
			start(ARTIFACT_KEYS_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, artifactKeys.length);
			for (int i = 0; i < artifactKeys.length; i++) {
				start(ARTIFACT_KEY_ELEMENT);
				attribute(ARTIFACT_KEY_NAMESPACE_ATTRIBUTE, artifactKeys[i].getNamespace());
				attribute(ARTIFACT_KEY_CLASSIFIER_ATTRIBUTE, artifactKeys[i].getClassifier());
				attribute(ID_ATTRIBUTE, artifactKeys[i].getId());
				attribute(VERSION_ATTRIBUTE, artifactKeys[i].getVersion());
				end(ARTIFACT_KEY_ELEMENT);
			}
			end(ARTIFACT_KEYS_ELEMENT);
		}
	}

	protected void writeTouchpointType(TouchpointType touchpointType) {
		start(TOUCHPOINT_TYPE_ELEMENT);
		attribute(ID_ATTRIBUTE, touchpointType.getId());
		attribute(VERSION_ATTRIBUTE, touchpointType.getVersion());
		end(TOUCHPOINT_TYPE_ELEMENT);
	}

	protected void writeTouchpointData(TouchpointData[] touchpointData) {
		if (touchpointData != null && touchpointData.length > 0) {
			start(TOUCHPOINT_DATA_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, touchpointData.length);
			for (int i = 0; i < touchpointData.length; i++) {
				TouchpointData nextData = touchpointData[i];
				Map instructions = nextData.getInstructions();
				if (instructions.size() > 0) {
					start(TOUCHPOINT_DATA_INSTRUCTIONS_ELEMENT);
					attribute(COLLECTION_SIZE_ATTRIBUTE, instructions.size());
					for (Iterator iter = instructions.entrySet().iterator(); iter.hasNext();) {
						Map.Entry entry = (Map.Entry) iter.next();
						start(TOUCHPOINT_DATA_INSTRUCTION_ELEMENT);
						attribute(TOUCHPOINT_DATA_INSTRUCTION_KEY_ATTRIBUTE, entry.getKey());
						cdata((String) entry.getValue(), true);
						end(TOUCHPOINT_DATA_INSTRUCTION_ELEMENT);
					}
				}
			}
			end(TOUCHPOINT_DATA_ELEMENT);
		}
	}

	private void writeTrimmedCdata(String element, String filter) {
		String trimmed;
		if (filter != null && (trimmed = filter.trim()).length() > 0) {
			start(element);
			cdata(trimmed);
			end(element);
		}
	}

	private void writeLicenses(License license) {
		if (license != null) {
			// In the future there may be more than one license, so we write this 
			// as a collection of one.
			// See bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=216911
			start(LICENSES_ELEMENT);
			attribute(COLLECTION_SIZE_ATTRIBUTE, 1);
			start(LICENSE_ELEMENT);
			if (license.getURL() != null)
				attribute(URL_ATTRIBUTE, license.getURL().toExternalForm());
			cdata(license.getBody(), true);
			end(LICENSE_ELEMENT);
			end(LICENSES_ELEMENT);
		}
	}

	private void writeCopyright(Copyright copyright) {
		if (copyright != null) {
			start(COPYRIGHT_ELEMENT);
			if (copyright.getURL() != null)
				attribute(URL_ATTRIBUTE, copyright.getURL().toExternalForm());
			cdata(copyright.getBody(), true);
			end(COPYRIGHT_ELEMENT);
		}
	}

}
