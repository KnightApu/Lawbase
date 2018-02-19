package util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ConformationMessage
{

	public static final String CLIENT_UPDATE="CLIENT_UPDATE";
	public static final String CLIENT_CREDIT_UPDATE="CLIENT_CREDIT_UPDATE";
	public static final String CLIENT_DELETE="CLIENT_DELETE";

	public static final String USER_ADD="USER_ADD";
	public static final String USER_EDIT="USER_EDIT";
	public static final String USER_DELETE="USER_DELETE";

	public static final String LANGUAGE_ADD="LANGUAGE_ADD";
	public static final String LANGUAGE_EDIT="LANGUAGE_EDIT";
	public static final String LANGUAGE_DELETE="LANGUAGE_DELETE";

	public static final String ROLE_ADD="ROLE_ADD";
	public static final String ROLE_EDIT="ROLE_EDIT";
	public static final String ROLE_DELETE="ROLE_DELETE";

	public static final String PREFIX_TRANSLATION_ADD = "PREFIX_TRANSLATION_ADD";
	public static final String PREFIX_TRANSLATION_UPDATE = "PREFIX_TRANSLATION_UPDATE";  


	public static final String RESTRICTED_IP_ADDED="RESTRICTED_IP_ADDED";



	//.......................Disaster Management..........................

	//.......................Office Type..........................

	public static final String OFFICE_TYPE_ADD = "OFFICE_TYPE_ADD";
	public static final String OFFICE_TYPE_EDIT = "OFFICE_TYPE_EDIT";
	public static final String OFFICE_TYPE_DELETE = "OFFICE_TYPE_DELETE";


	//.........................................................


	public static final String DESIGNATION_ADD="DESIGNATION_ADD";
	public static final String DESIGNATION_EDIT="DESIGNATION_EDIT";
	public static final String DESIGNATION_DELETE="DESIGNATION_DELETE";


	//.......................OFFICE_LEVEL..........................
	public static final String OFFICE_LEVEL_ADD = "OFFICE_LEVEL_ADD";
	public static final String OFFICE_LEVEL_EDIT = "OFFICE_LEVEL_EDIT";
	public static final String OFFICE_LEVEL_DELETE = "OFFICE_LEVEL_DELETE";

	//.......................TEMPLATE_OFFICE..........................
	public static final String TEMPLATE_OFFICE_ADD="TEMPLATE_OFFICE_ADD";
	public static final String TEMPLATE_OFFICE_EDIT="TEMPLATE_OFFICE_EDIT";
	public static final String TEMPLATE_OFFICE_DELETE = "TEMPLATE_OFFICE_DELETE"; 

	//.......................OFFICE..........................
	public static final String OFFICE_ADD = "OFFICE_ADD";
	public static final String OFFICE_EDIT = "OFFICE_EDIT";
	public static final String OFFICE_DELETE = "OFFICE_DELETE";

	//..........................ASSOCIATE OFFICE.....................
	public static final String ASSOCIATE_OFFICE_ADD = "ASSOCIATE_OFFICE_ADD";
	public static final String ASSOCIATE_OFFICE_EDIT = "ASSOCIATE_OFFICE_EDIT";
	public static final String ASSOCIATE_OFFICE_DELETE = "ASSOCIATE_OFFICE_DELETE";


	//..........................OFFICE DESIGNATION .....................
	public static final String OFFICE_DESIGNATION_ADD = "OFFICE_DESIGNATION_ADD";
	public static final String OFFICE_DESIGNATION_EDIT = "OFFICE_DESIGNATION_EDIT";
	public static final String OFFICE_DESIGNATION_DELETE = "OFFICE_DESIGNATION_DELETE";
	public static final String OFFICE_BRANCH_ADD = "OFFICE_BRANCH_ADD";
	public static final String OFFICE_BRANCH_DELETE = "OFFICE_BRANCH_DELETE";



	//..........................OFFICER OFFICE DESIGNATION .....................
	public static final String OFFICER_OFFICE_DESIGNATION_ADD = "OFFICER_OFFICE_DESIGNATION_ADD";
	public static final String OFFICER_OFFICE_DESIGNATION_EDIT = "OFFICER_OFFICE_DESIGNATION_EDIT";
	public static final String OFFICER_OFFICE_DESIGNATION_DELETE = "OFFICER_OFFICE_DESIGNATION_DELETE";


	//.......................OFFICER..........................
	public static final String OFFICER_ADD = "OFFICER_ADD";
	public static final String OFFICER_EDIT = "OFFICER_EDIT";
	public static final String OFFICER_DELETE = "OFFICER_DELETE";

	//.......................FILE..........................
	public static final String FILE_ADD = "FILE_ADD";
	public static final String FILE_EDIT = "FILE_EDIT";
	public static final String FILE_DELETE = "FILE_DELETE";

	//.......................FILE..........................
	public static final String MESSAGE_ADD = "MESSAGE_ADD";
	public static final String MESSAGE_EDIT = "MESSAGE_EDIT";
	public static final String MESSAGE_DELETE = "MESSAGE_DELETE";

	public static final String CMS_LANGUAGE_ADD="CMS_LANGUAGE_ADD";
	public static final String CMS_LANGUAGE_EDIT="CMS_LANGUAGE_EDIT";
	public static final String CMS_LANGUAGE_DELETE="CMS_LANGUAGE_DELETE";

	//.......................Disaster Management..........................

	public static final String RESEARCH_CONTENT_UPLOAD="RESEARCH_CONTENT_UPLOAD";

	//.....................Incident..........................
	public static final String INCIDENT_ADD = "INCIDENT_ADD";


	//..................SIpUser......................

	public static final int sipUser = 1;

	public static final int AddSipUser = 1;
	public static final int UpdateSipUser = 2;
	public static final int DeleteSipUser = 3;
	public static final int SearchAallSipUser = 4;

	//...................Voicemail..........................

	public static final int VoiceMail=2;
	public static final int AddVoiceMail=3;
	public static final int DeleteVoicemail=4;
	public static final int UpdateVoiceMail=5;
	//..........................................................
	//.......................Extension..........................
	public static final int Extension = 3;
	public static final int AddExtension = 1;
	public static final int DeleteExtension = 2;
	public static final int UpdateExtension = 3;
	public static final int SearchExtension = 4;

	public static final String CMS_MENU_GROUPS_ADD="CMS_MENU_GROUPS_ADD";
	public static final String CMS_MENU_GROUPS_EDIT="CMS_MENU_GROUPS_EDIT";
	public static final String CMS_MENU_GROUPS_DELETE="CMS_MENU_GROUPS_DELETE";

	public static final String CMS_MENU_ITEMS_ADD="CMS_MENU_ITEMS_ADD";
	public static final String CMS_MENU_ITEMS_EDIT="CMS_MENU_ITEMS_EDIT";
	public static final String CMS_MENU_ITEMS_DELETE="CMS_MENU_ITEMS_DELETE";

	public static final String CMS_TAB_ITEMS_ADD="CMS_TAB_ITEMS_ADD";
	public static final String CMS_TAB_ITEMS_EDIT="CMS_TAB_ITEMS_EDIT";
	public static final String CMS_TAB_ITEMS_DELETE="CMS_TAB_ITEMS_DELETE";

	public static final String CMS_CONTENT_CATEGORY_ADD="CMS_CONTENT_CATEGORY_ADD";
	public static final String CMS_CONTENT_CATEGORY_EDIT="CMS_CONTENT_CATEGORY_EDIT";
	public static final String CMS_CONTENT_CATEGORY_DELETE="CMS_CONTENT_CATEGORY_DELETE";

	public static final String CMS_CONTENT_DETAILS_ADD="CMS_CONTENT_DETAILS_ADD";
	public static final String CMS_CONTENT_DETAILS_EDIT="CMS_CONTENT_DETAILS_EDIT";
	public static final String CMS_CONTENT_DETAILS_DELETE="CMS_CONTENT_DETAILS_DELETE";

	public static final String CMS_MODULES_ADD="CMS_MODULES_ADD";
	public static final String CMS_MODULES_EDIT="CMS_MODULES_EDIT";
	public static final String CMS_MODULES_DELETE="CMS_MODULES_DELETE";

	public static final String CMS_GALLERY_ADD="CMS_GALLERY_ADD";
	public static final String CMS_GALLERY_EDIT="CMS_GALLERY_EDIT";
	public static final String CMS_GALLERY_DELETE="CMS_GALLERY_DELETE";

	public static final String NEWS_ADD="NEWS_ADD";
	public static final String NEWS_EDIT="NEWS_EDIT";
	public static final String NEWS_DELETE="NEWS_DELETE";


	//-------------Category-------
	public static final String CATEGORY_ADDED = "category_added";
	public static final String RESOURCE_ADDED = "resource_added";
	public static final String RESOURCE_ADD_FAILURE = "resource_add_failure";
	
	public static final String ORGANIZATION_ADDED = "organization_added";
	public static final String ORGANIZATION_ADD_FAILURE = "organization_add_failure";
	
	public static final String HUMAN_RESOURCE_ADDED = "human_resource_added";
	public static final String HUMAN_RESOURCE_ADD_FAILURE = "human_resource_add_failure";
	
	public static final String REQUISITION_ADDED = "requisition_added";
	public static final String REQUISITION_ACCEPTED = "requisition_accepted";


	public static final String PERMANENT_ENDPOINT_DELETED="PERMANENT_ENDPOINT_DELETED";
	
	public static final String MAIL_SENT = "mail_sent";
	public static final String CATEGORY_SET = "category_set";
	public static final String FOCAL_POINT_ADDED = "focal_point_added";
	public ConformationMessage()
	{
	}
}

