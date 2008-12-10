/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.equinox.internal.p2.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Message class for provisioning UI messages.  
 * 
 * @since 3.4
 */
public class ProvUIMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.equinox.internal.p2.ui.messages"; //$NON-NLS-1$
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, ProvUIMessages.class);
	}

	public static String AcceptLicensesWizardPage_AcceptMultiple;
	public static String AcceptLicensesWizardPage_AcceptSingle;
	public static String AcceptLicensesWizardPage_ItemsLabel;
	public static String AcceptLicensesWizardPage_LicenseTextLabel;
	public static String AcceptLicensesWizardPage_NoLicensesDescription;
	public static String AcceptLicensesWizardPage_RejectMultiple;
	public static String AcceptLicensesWizardPage_RejectSingle;
	public static String AcceptLicensesWizardPage_ReviewExtraLicensesDescription;
	public static String AcceptLicensesWizardPage_ReviewLicensesDescription;
	public static String AcceptLicensesWizardPage_Title;
	public static String ApplicationInRestartDialog;
	public static String ApplyProfileChangesDialog_ApplyChanges;
	public static String CategoryElementCollector_Uncategorized;
	public static String ColocatedRepositoryManipulator_AddSiteOperationLabel;
	public static String ColocatedRepositoryManipulator_ManageSites;
	public static String ColocatedRepositoryManipulator_RemoveSiteOperationLabel;
	public static String InstalledSoftwarePage_RevertLabel;
	public static String InstalledSoftwarePage_RevertTooltip;
	public static String IUCopyrightPropertyPage_NoCopyright;
	public static String IUCopyrightPropertyPage_ViewLinkLabel;
	public static String IUDetailsLabelProvider_KB;
	public static String IUDetailsLabelProvider_Bytes;
	public static String IUDetailsLabelProvider_ComputingSize;
	public static String IUDetailsLabelProvider_Unknown;
	public static String IUGeneralInfoPropertyPage_ContactLabel;
	public static String IUGeneralInfoPropertyPage_CouldNotOpenBrowser;
	public static String IUGeneralInfoPropertyPage_DescriptionLabel;
	public static String IUGeneralInfoPropertyPage_DocumentationLink;
	public static String IUGeneralInfoPropertyPage_IdentifierLabel;
	public static String IUGeneralInfoPropertyPage_NameLabel;
	public static String IUGeneralInfoPropertyPage_ProviderLabel;
	public static String IUGeneralInfoPropertyPage_VersionLabel;
	public static String IULicensePropertyPage_NoLicense;
	public static String IULicensePropertyPage_ViewLicenseLabel;
	public static String ProfileChangeRequestBuildingRequest;
	public static String ProfileElement_InvalidProfile;
	public static String ProfileModificationAction_NoChangeRequestProvided;
	public static String ProfileModificationAction_NoExplanationProvided;
	public static String ProfileModificationAction_ResolutionOperationLabel;
	public static String ProfileModificationWizardPage_DetailsLabel;
	public static String ProfileModificationWizardPage_NothingSelected;
	public static String ProfileModificationWizardPage_ResolutionOperationLabel;
	public static String ProfileModificationWizardPage_UnexpectedError;
	// utility error messages
	public static String ProvisioningUtil_NoRepositoryManager;
	public static String ProvisioningUtil_LoadRepositoryFailure;
	public static String ProvisioningUtil_NoProfileRegistryFound;
	public static String ProvisioningUtil_NoPlannerFound;
	public static String ProvisioningUtil_NoDirectorFound;
	public static String ProvisioningUtil_NoEngineFound;

	// viewer support
	public static String ProvDropAdapter_InvalidDropTarget;
	public static String ProvDropAdapter_NoIUsToDrop;
	public static String ProvDropAdapter_UnsupportedDropOperation;

	// Provisioning operations
	public static String ProvisioningOperation_ExecuteErrorTitle;
	public static String ProvisioningOperation_RedoErrorTitle;
	public static String ProvisioningOperation_UndoErrorTitle;
	public static String ProvisioningOperationRunner_ErrorExecutingOperation;
	public static String InstallIUOperationLabel;
	public static String InstallIUCommandLabel;
	public static String InstallIUCommandTooltip;
	public static String InstallIUProgress;
	public static String InstallWizardPage_NoCheckboxDescription;
	public static String InstallWizardPage_Title;
	public static String UninstallDialog_UninstallMessage;
	public static String UninstallIUOperationLabel;
	public static String UninstallIUCommandLabel;
	public static String UninstallIUCommandTooltip;
	public static String UninstallIUProgress;
	public static String UninstallWizardPage_Description;
	public static String UninstallWizardPage_Title;
	public static String UpdateIUOperationLabel;
	public static String UpdateIUCommandLabel;
	public static String UpdateIUCommandTooltip;
	public static String UpdateIUProgress;
	public static String RefreshAction_Label;
	public static String RefreshAction_Tooltip;
	public static String RemoveColocatedRepositoryAction_Label;
	public static String RemoveColocatedRepositoryAction_OperationLabel;
	public static String RemoveColocatedRepositoryAction_Tooltip;
	public static String RevertIUCommandLabel;
	public static String RevertIUCommandTooltip;

	// Property pages
	public static String IUPropertyPage_NoIUSelected;
	public static String RepositoryPropertyPage_DescriptionFieldLabel;
	public static String RepositoryPropertyPage_NameFieldLabel;
	public static String RepositoryPropertyPage_URLFieldLabel;

	public static String RepositoryPropertyPage_NoRepoSelected;

	// Dialog groups
	public static String RepositoryGroup_LocalRepoBrowseButton;
	public static String RepositoryGroup_ArchivedRepoBrowseButton;
	public static String RepositoryGroup_RepositoryFile;
	public static String RepositoryGroup_SelectRepositoryDirectory;
	public static String RepositoryGroup_URLRequired;
	public static String RepositoryManipulationDialog_Add;
	public static String RepositoryManipulationDialog_Description;
	public static String RepositoryManipulationDialog_Export;
	public static String RepositoryManipulationDialog_Import;
	public static String RepositoryManipulationDialog_LocationColumnTitle;
	public static String RepositoryManipulationDialog_NameColumnTitle;
	public static String RepositoryManipulationDialog_Properties;
	public static String RepositoryManipulationDialog_Remove;
	public static String RepositoryManipulationDialog_Title;
	public static String RepositoryManipulatorDropTarget_DragAndDropJobLabel;
	public static String RepositoryManipulatorDropTarget_DragSourceNotValid;

	public static String AddColocatedRepositoryAction_Label;
	public static String AddColocatedRepositoryAction_Tooltip;
	public static String AddColocatedRepositoryDialog_AddSiteTitle;
	// Dialogs
	public static String AddRepositoryDialog_DuplicateURL;
	public static String AddRepositoryDialog_InvalidURL;
	public static String AddRepositoryDialog_Title;
	public static String AvailableIUElement_ProfileNotFound;
	public static String AvailableIUGroup_LoadingRepository;
	public static String AvailableIUGroup_RefreshOperationLabel;
	public static String AvailableIUGroup_ViewByCategory;
	public static String AvailableIUGroup_ViewByName;
	public static String AvailableIUGroup_ViewBySite;
	public static String AvailableIUGroup_ViewByToolTipText;
	public static String AvailableIUsPage_AddSite;
	public static String AvailableIUsPage_Description;
	public static String AvailableIUsPage_IncludeInstalledItems;
	public static String AvailableIUsPage_InstallInfo;
	public static String AvailableIUsPage_InstallInfoTooltip;
	public static String AvailableIUsPage_ManageSites;
	public static String AvailableIUsPage_ManageSitesTooltip;
	public static String AvailableIUsPage_RefreshTooltip;
	public static String AvailableIUsPage_ShowLatestVersions;
	public static String AvailableIUsPage_Title;
	public static String DefaultQueryProvider_ErrorRetrievingProfile;
	public static String DeferredFetchFilteredTree_RetrievingList;
	public static String ElementUtils_UpdateJobTitle;
	public static String Label_Profiles;
	public static String Label_Repositories;
	public static String MetadataRepositoryElement_NotFound;
	public static String MetadataRepositoryElement_RepositoryLoadError;
	public static String UpdateAction_UpdatesAvailableMessage;
	public static String UpdateAction_UpdatesAvailableTitle;
	public static String PlannerResolutionOperation_UnexpectedError;
	public static String PlanStatusHelper_IgnoringImpliedDowngrade;
	public static String PlanStatusHelper_ImpliedUpdate;
	public static String PlanStatusHelper_Items;
	public static String PlanStatusHelper_NothingToDo;
	public static String PlanStatusHelper_AlreadyInstalled;
	public static String PlanStatusHelper_AnotherOperationInProgress;
	public static String PlanStatusHelper_Launch;
	public static String PlanStatusHelper_RequestAltered;
	public static String PlanStatusHelper_RequiresUpdateManager;
	public static String PlanStatusHelper_UnexpectedError;
	public static String PlanStatusHelper_UpdateManagerPromptTitle;
	public static String PlanStatusHelper_PromptForUpdateManagerUI;
	public static String PlatformUpdateTitle;
	public static String PlatformRestartMessage;
	public static String Policy_CannotResetDefaultPolicy;
	public static String ProvUI_ErrorDuringApplyConfig;
	public static String ProvUI_InformationTitle;
	public static String ProvUI_InstallDialogError;
	public static String ProvUI_NameColumnTitle;
	public static String ProvUI_IdColumnTitle;
	public static String ProvUI_VersionColumnTitle;
	public static String ProvUI_WarningTitle;
	public static String ProvUIActivator_ExceptionDuringProfileChange;
	public static String ProvUILicenseManager_ParsingError;
	public static String OptionalPlatformRestartMessage;
	public static String QueryableArtifactRepositoryManager_RepositoryQueryProgress;
	public static String QueryableMetadataRepositoryManager_LoadRepositoryProgress;
	public static String QueryableMetadataRepositoryManager_MultipleRepositoriesNotFound;
	public static String QueryableMetadataRepositoryManager_RepositoryQueryProgress;
	public static String QueryableProfileRegistry_QueryProfileProgress;
	public static String QueryableUpdates_UpdateListProgress;
	public static String SizeComputingWizardPage_SizeJobTitle;
	public static String SizingPhaseSet_PhaseSetName;
	public static String RepositoryPropertyPage_UsernameField;
	public static String RepositoryPropertyPage_PasswordField;
	public static String RepositoryPropertyPage_SavePasswordField;
	public static String RevertDialog_ConfigContentsLabel;
	public static String RevertDialog_ConfigsLabel;
	public static String RevertDialog_ConfirmRestartMessage;
	public static String RevertDialog_Description;
	public static String RevertDialog_PageTitle;
	public static String RevertDialog_RevertError;
	public static String RevertDialog_RevertOperationLabel;
	public static String RevertDialog_Title;
	public static String RevertProfileWizardPage_ErrorRetrievingHistory;
	public static String RollbackProfileElement_InvalidSnapshot;

	public static String TrustCertificateDialog_Details;
	public static String TrustCertificateDialog_Title;
	// Operations
	public static String URLValidator_UnrecognizedURL;
	public static String UpdateManagerCompatibility_ExportSitesTitle;
	public static String UpdateManagerCompatibility_ImportSitesTitle;
	public static String UpdateManagerCompatibility_InvalidSiteFileMessage;
	public static String UpdateManagerCompatibility_InvalidSitesTitle;
	public static String UpdateManagerCompatibility_UnableToOpenFindAndInstall;
	public static String UpdateManagerCompatibility_UnableToOpenManageConfiguration;
	public static String UpdateOperation_NothingToUpdate;
	public static String ServiceUI_Cancel;
	public static String ServiceUI_LoginDetails;
	public static String ServiceUI_LoginRequired;
	public static String ServiceUI_OK;
	public static String UpdateOrInstallWizardPage_Size;
	public static String Updates_Label;
	public static String UpdateWizardPage_Description;
	public static String UpdateWizardPage_Title;

}
