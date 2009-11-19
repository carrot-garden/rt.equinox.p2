/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.equinox.p2.operations;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.*;
import org.eclipse.equinox.internal.p2.core.helpers.LogHelper;
import org.eclipse.equinox.internal.p2.operations.Activator;
import org.eclipse.equinox.internal.p2.operations.Messages;
import org.eclipse.equinox.internal.provisional.p2.core.ProvisionException;

/**
 * A job that loads a set of metadata repositories and caches the loaded repositories.
 * This job can be used when repositories are loaded by a client who wishes to 
 * maintain (and pass along) the in-memory references to the repositories.  For example,
 * repositories can be loaded in the background and then passed to another
 * component, thus ensuring that the repositories remain loaded in memory.
 * 
 * @since 2.0
 *
 */
public class LoadMetadataRepositoryJob extends RepositoryJob {

	/**
	 * An object representing the family of jobs that load repositories.
	 */
	public static final Object LOAD_FAMILY = new Object();

	/**
	 * The key that should be used to set a property on a repository load job to indicate
	 * that authentication should be suppressed when loading the repositories.
	 */
	public static final QualifiedName SUPPRESS_AUTHENTICATION_JOB_MARKER = new QualifiedName(Activator.ID, "SUPPRESS_AUTHENTICATION_REQUESTS"); //$NON-NLS-1$

	/**
	 * The key that should be used to set a property on a repository load job to indicate
	 * that load errors should be accumulated into a single status rather than reported
	 * as they occur.
	 */
	public static final QualifiedName ACCUMULATE_LOAD_ERRORS = new QualifiedName(Activator.ID, "ACCUMULATE_LOAD_ERRORS"); //$NON-NLS-1$

	private List repoCache = new ArrayList();
	private RepositoryTracker tracker;
	private MultiStatus accumulatedStatus;

	/**
	 * Create a job that loads the metadata repositories known by the specified RepositoryTracker.
	 * @param session the provisioning session providing the necessary services
	 * @param tracker the tracker that knows which repositories should be loaded
	 */
	public LoadMetadataRepositoryJob(ProvisioningSession session, RepositoryTracker tracker) {
		super(Messages.PreloadRepositoryJob_LoadJobName, session, tracker.getKnownRepositories(session));
		this.tracker = tracker;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.p2.operations.ProvisioningJob#runModal(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus runModal(IProgressMonitor monitor) {
		SubMonitor sub = SubMonitor.convert(monitor, Messages.PreloadMetadataRepositoryJob_ContactingSites, locations.length * 100);
		if (sub.isCanceled())
			return Status.CANCEL_STATUS;
		for (int i = 0; i < locations.length; i++) {
			if (sub.isCanceled())
				return Status.CANCEL_STATUS;
			try {
				repoCache.add(getSession().loadMetadataRepository(locations[i], sub.newChild(100)));
			} catch (ProvisionException e) {
				handleLoadFailure(e, locations[i]);
			}
		}
		return getCurrentStatus();
	}

	private void handleLoadFailure(ProvisionException e, URI location) {
		int code = e.getStatus().getCode();
		// special handling when the repo location is bad.  We don't want to continually report it
		if (code == ProvisionException.REPOSITORY_NOT_FOUND || code == ProvisionException.REPOSITORY_INVALID_LOCATION) {
			if (tracker.hasNotFoundStatusBeenReported(location))
				return;
			tracker.addNotFound(location);
		}

		if (shouldAccumulateFailures()) {
			// Some ProvisionExceptions include an empty multi status with a message.  
			// Since empty multi statuses have a severity OK, The platform status handler doesn't handle
			// this well.  We correct this by recreating a status with error severity
			// so that the platform status handler does the right thing.
			IStatus status = e.getStatus();
			if (status instanceof MultiStatus && ((MultiStatus) status).getChildren().length == 0)
				status = new Status(IStatus.ERROR, status.getPlugin(), status.getCode(), status.getMessage(), status.getException());
			if (accumulatedStatus == null) {
				accumulatedStatus = new MultiStatus(Activator.ID, ProvisionException.REPOSITORY_NOT_FOUND, new IStatus[] {status}, Messages.PreloadMetadataRepositoryJob_SomeSitesNotFound, null);
			} else {
				accumulatedStatus.add(status);
			}
			// Always log the complete exception so the detailed stack trace is in the log.  
			LogHelper.log(e);
		} else {
			tracker.reportLoadFailure(location, getErrorStatus(null, e));
		}
	}

	private boolean shouldAccumulateFailures() {
		return getProperty(LoadMetadataRepositoryJob.SUPPRESS_AUTHENTICATION_JOB_MARKER) != null;
	}

	/**
	 * Report the accumulated status to the repository tracker.  If there has been
	 * no status accumulated, or if the job has been cancelled, do not report
	 * anything.
	 */
	public void reportAccumulatedStatus() {
		IStatus status = getCurrentStatus();
		if (status.isOK() || status.getSeverity() == IStatus.CANCEL)
			return;
		// report status
		tracker.reportLoadFailure(null, status);
		// Reset the accumulated status so that next time we only report the newly not found repos.
		accumulatedStatus = null;
	}

	private IStatus getCurrentStatus() {
		if (accumulatedStatus != null) {
			// If there is only missing repo to report, use the specific message rather than the generic.
			if (accumulatedStatus.getChildren().length == 1)
				return accumulatedStatus.getChildren()[0];
			return accumulatedStatus;
		}
		return Status.OK_STATUS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
	 */
	public boolean belongsTo(Object family) {
		return family == LOAD_FAMILY;
	}
}