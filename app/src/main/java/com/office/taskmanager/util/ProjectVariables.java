package com.office.taskmanager.util;

/**
 * Created by MANJU on 16-06-2016.
 */

public class ProjectVariables {

    public static final String NOVIDEO = "novideo";
    public static final String NOIMAGE = "noimage";
    public static final String NOAUDIO = "noaudio";

    public static final String LOGIN_STATUS = "loginstatus";
    public static final String LOGGED_IN = "loggedin";
    public static final String LOGGED_OUT = "loggedout";
    public static final String STATUS = "Status";
    public static final String PREFERENCES_NAME = "sampleprefs";
    public static final String USERNAME = "userName";
    public static final String PLEASE_CHECK_YOUR_NETWORK_CONNECTION = "please check your network connection";
    public static final String MASTER_URL = "http://makeindiakart.com/Master.svc/";
    // public static final String URL = "http://makeindiakart.com/Master.svc/";
    public static final String PLEASE_ENABLE_BOTH_INTERENT_AND_GPS_IN_YOUR_MOBILE = "Please enable both interent and GPS in your mobile";

    public static final String FTP_HOST = "103.231.101.101";
    public static final String FTP_USER = "myaccoun";
    public static final String FTP_PASS = "ctrls@123";
    //public static final String IMAGE_FOLD = "/OfficeTaskManager/DBImages";

    /*TaskManager_Bombay*/
    public static final String URL = "http://myaccountsonline.co.in/TaskManager_Bombay/TaskManger.svc/";
    public static final String IMAGE_FOLD = "/TaskManager_Bombay/Uploads";
    public static final String IMAGE_PATH = "http://myaccountsonline.co.in/TaskManager_Bombay/Uploads/";

    /* Ofice Task Manage Main URl*/
    //http://myaccountsretail.com/OfficeTaskmanager/TaskManger.svc/
/*    public static final String URL = "http://myaccountsretail.com/OfficeTaskmanager/TaskManger.svc/";
    public static final String IMAGE_FOLD = "/TaskManager_Bombay/Uploads";
    public static final String IMAGE_PATH = "http://myaccountsonline.co.in/OfficeTaskManager/DBImages/";*/

    /*********************
     * Project Base URL
     *********************/
   /*  public static final String URL = "http://makeindiakart.com/TaskManger.svc/";
    public static final String IMAGE_PATH = "http://makeindiakart.com/taskfiles/";
     public static final String IMAGE_PATH = "http://myaccountsonline.co.in/OfficeTaskManager/DBImages/";*/

    /************
     * /**************************
     * Demo Base Url
     ***************************/

    //http://myaccountsonline.co.in/Taskmanager/TaskManger.svc/
    // public static final String URL = "http://myaccountsonline.co.in/taskmanager/TaskManger.svc/";
    //public static final String URL = "http://myaccountsonline.co.in/taskmanager/TaskManger.svc/";
    //public static final String IMAGE_PATH = "http://myaccountsonline.co.in/Taskmanger/taskfiles/";


    //http://myaccountsonline.co.in/taskmanager/Master.svc/
    //http://makeindiakart.com/Master.svc/SendSms/MY10077
    public static String getSendSms(String clientID) {
        return ProjectVariables.MASTER_URL + "SendSms/" + ProjectVariables.CLIENTID;
    }

    //http://myaccountsonline.co.in/taskmanager/TaskManger.svc/Users_ByBranches_Reports/1,Myaccounts
    //public static final String LOGIN = "login";
    public static final String BASE_URL = URL;
    public static final String LOGIN = "Tasklogin_User";
    public static final String REGISTER = "MasterInsert";
    public static final String USERS = "Users/";
    public static final String USERS_BYBRANCHES_REPORTS = "Users_ByBranches_Reports/";
    public static final String BRANCHES_REPORTS = "Branches_Reports/C001";
    //http://myaccountsonline.co.in/taskmanager/TaskManger.svc/priority_Notification

    public static final String PRIORITY_NOTIFICATION = "priority_Notification";
    public static final String TASKREPORT = "TaskReport";
    public static final String TASK_CREAT = "Task_Creation";
    public static final String BRANCHES = "Branches/C001";
    public static final String USER_ROLES = "TaskUserRoles";
    public static final String TASK_UPDATE = "Task_Update";
    public static final String TASK_DELETED = "Task_Deleted";
    public static final String TASK_COMMENTS = "TaskComments";
    public static final String getTasksByUserId = "TaskUserTasksById";
    public static final String POST_ANDROIDREGID = "Post_AndroidRegId";
    public static final String TASK_RESEND = "Task_Resend";
    public static final String TASK_ATTENDENCE = "Task_Attendence";
    public static final String TASK_AUTORESEND = "Task_AutoResend";

    public static final String MANAGERDAILYFEEDBACK = "ManagerDailyFeedBack";
    public static final String FEEDINGPAPER = "FeedingPaper";
    public static final String DAILYREPORT = "DailyReport";
    public static final String GETMANAGERDAILYFEEDBACK = "GetManagerDailyFeedBack";
    public static final String GETFEEDINGPAPER = "GetFeedingPaper";
    public static final String GETDAILYREPORT = "GetDailyReport";
    public static final String INWARDPROFORMA = "InwardProforma";
    public static final String GetInwardProforma = "GetInwardProforma";
    public static final String OutwardProforma = "OutwardProforma";
    public static final String GetOutwardProforma = "GetOutwardProforma";



    /*  string xmlf = "<ROOT>";

                xmlf += "<INSERTXMLDATA ProformaType='C' ProformaValue='2' ROLE='7' USERID='8' BRANCHID='9' COUNT='1' />";
                xmlf += "<INSERTXMLDATA ProformaType='G' ProformaValue='2' ROLE='7' USERID='8' BRANCHID='9' COUNT='2' />";
                xmlf += "<INSERTXMLDATA ProformaType='S' ProformaValue='2' ROLE='7' USERID='8' BRANCHID='9' COUNT='3' />";
                xmlf += "<INSERTXMLDATA ProformaType='2nd' ProformaValue='2' ROLE='7' USERID='8' BRANCHID='9' COUNT='4' />";
                xmlf += "</ROOT>";
                Result
GetOutwardProforma

BranchId

DATE,XmlFile,Branchid*/

    // http://myaccountsonline.co.in/taskmanager/TaskManger.svc/DailyReport

    public static final int APK_CONNECTION_TIMEOUT = 15000;
    public static final int APK_WAIT_DATA_TIMEOUT = 30000;
    public static String DAILY_SALES_SUMMARY = "DailySalesSummary";
    public static String PARTYWISE_SALES = "PartywiseSales";
    public static final String USER_DELETED = "User_Deleted";
    public static String PHOTP = "";
    public static String CLIENTID = "";
    public static String TASKMANGER = "TaskManger";
    public static String AAPPTYPE = "AappType";
    public static String ADDRESS1 = "Address1";
    public static String ADDRESS2 = "Address2";
    public static String ADDRESS3 = "Address3";
    public static String ANDROIDNO = "AndroidNo";
    public static String ANDROIDNO1 = "AndroidNo";
    public static String BRANCHNAME = "BranchName";
    public static String CITYID = "CityId";
    public static String CLIENTNAME = "ClientName";
    // public static String COMPNAME = "CompName";
    public static String COMPANYID = "CompId";
    public static String EMAILID = "EmailId";
    public static final String PHONENUBER = "PhoneNo";
    public static String PHONENO1 = "PhoneNo1";
    public static String REMARKS = "Remarks";

    public static String PASSWORD = "Password";
    public static String JPASSWORD = "Password";
    public static String JEMAILID = "EmailId";

    public static String JREMARKS = "";
    public static String JRESULT = "";
    public static String ImAGES = "";


    public static final String USER_PROFILE = "Task_UserMaster";
    public static String RESULT = "Result";
    public static String FIRSTNAME = "FirstName";
    public static String LASTNAME = "LastName";

    public static String ImAGE = "Image";
    public static String PHONENO = "PhoneNo";
    public static String IMEID = "IMEID";
    public static String MAILID = "MailID";
    public static String MACID = "Macid";
    public static String ANDROID = "android";
    public static String APPNAME = "AppName";

    public static String STAT = "";
    public static String FNAME = "FirstName";
    public static String UID = "Uid";
    public static String USERLEVEL = "UserLevel";
    public static String USERROLE = "UserRole";
    public static String IMAGE_AllUSER = "Image";
    public static String EMA_ILID = "Emailid";
    public static String PH_ONE = "Phone";
    public static String CI_TY = "City";

    public static String COMPNAME = "Compname";
    public static String BRANCHID = "BranchId";
    public static String BNAME = "BranchName";
    public static String TASKFROMID = "TaskFromId";
    public static String TASKOID = "TaskToId";
    public static String EXPSTARTDAE = "ExpStartDate";
    public static String EXPENDDATE = "ExpEndDate";
    public static String ACTSDATE = "ActStartDate";
    public static String ACTENDDATE = "ActEndDate";
    public static String TASKSTAT = "TaskStatus";
    public static String TASKHEAD = "TaskHeading";
    public static String TASKDES = "TaskDes";
    public static String PRIORITY = "priority";
    public static String LOGIN_PHONE = "Phone";
    public static String USER_ROLE = "UserRole";
    public static String CITY = "City";
    public static String COUNTRY = "Country";
    public static String CREATEDDATE = "CreatedDate";
    public static String ENDDATE = "EndDate";
    public static String FLAG = "Flag";
    public static String IMAGE = "IMage";
    public static String ID = "Id";
    public static String TASK_id = "Cid";
    public static String USERLOGINID = "uuid";
    public static String ROLENAME = "RoleName";
    public static String ROID = "RoId";
    public static String STARTTIME = "StartTime";
    public static String ENDTIME = "EndTime";
    public static String Uname = "Uname";
    public static String UImage = "UImage";
    public static String video = "video";

    public static String _ACTENDDATE = "ActEndDate";
    public static String _ACTSTARTDATE = "ActStartDate";
    public static String _TASKHEADING = "TaskHeading";
    public static String _TASKSTATUS = "TaskStatus";
    public static String _CID = "cid";
    public static String PRIORITYS = "Priority";
    public static String REPEATEDDAYS = "RepeatedDays";

    //  public static String STATUS="Status";

    public static String EMPID = "EmpId";
    public static String NAME = "Name";
    public static String DATE = "Date";
    public static String TYPE = "Type";
    public static String COMMENTS = "Comments";
    public static String LEAVEFROM = "LeaveFrom";
    public static String LEAVETO = "LeaveTo";
    public static String LATTITUDE = "Lattitude";
    public static String LONGITUDE = "Longitude";
    public static String ADDRESS = "Address";
    public static String MANAGERID = "ManagerId";

    /*Task Details Variables*/
    public static final String TASK_HEADING = "task_heading";
    public static final String TASK = "task";
    public static final String ASIGNBY = "asignby";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STARTTIMES = "starttime";
    public static final String ENDTIMES = "endtime";
    public static final String UNAME = "uname";
    public static final String PROFILE = "profile";
    public static final String STATUSS = "status";
    public static final String TASK_ID = "task_id";
    public static final String TASK_FROMID = "task_fromid";
    public static final String TASK_IMAGE = "task_image";
    public static final String TASK_VIDEO = "task_video";

    /*Section Incharges;*/
    public static final String UserId = "UserId";
    public static final String ClrAdvPayment = "ClrAdvPayment";
    public static final String TravelCharges = "TravelCharges";
    public static final String PendingOrder = "PendingOrder";
    public static final String PedingLRValue = "PedingLRValue";
    public static final String NoOfVisitsSDPT = "NoOfVisitsSDPT";
    public static final String NoOfVisitsJGTL = "NoOfVisitsJGTL";
    public static final String DiscBillList = "DiscBillList";
    public static final String CounterCheckMrng = "CounterCheckMrng";
    public static final String StockReport = "StockReport";
    public static final String EveryMonthDiscount = "EveryMonthDiscount";
    public static final String SlowStockReport = "SlowStockReport";
    public static final String MinStockQty = "MinStockQty";
    public static final String ImprovementInSales = "ImprovementInSales";
    public static final String MarketSurvey = "MarketSurvey";
    public static final String MonthlyReport = "MonthlyReport";
    public static final String Incentive = "Incentive";
    public static final String StartingFoldingAndSetting = "StartingFoldingAndSetting";
    public static final String Role = "Role";
    public static final String Flag = "Flag";

    /*FeedingPaper*/

    public static final String DressMaterial = "DressMaterial";
    public static final String HalfSarees = "HalfSarees";
    public static final String FreeSize_SS = "FreeSize_SS";
    public static final String Kurthies = "Kurthies";
    public static final String Tops = "Tops";
    public static final String ShortTops = "ShortTops";
    public static final String JeansL = "JeansL";
    public static final String S_SGirls_32_40 = "S_SGirls_32_40";
    public static final String Western_32_36 = "Western_32_36";
    public static final String TopsG_32_40 = "TopsG_32_40";
    public static final String JeansG_32_40 = "JeansG_32_40";
    public static final String LeggingG_32_40 = "LeggingG_32_40";
    public static final String HalfSareesG = "HalfSareesG";
    public static final String Frocks_16_30 = "Frocks_16_30";
    public static final String LeggingG_16_30 = "LeggingG_16_30";
    public static final String Western_16_30 = "Western_16_30";
    public static final String JeansG_16_30 = "JeansG_16_30";
    public static final String TopsG_16_30 = "TopsG_16_30";
    public static final String F_Nightie = "F_Nightie";
    public static final String F_Nighties = "F_Nighties";
    public static final String Nighties_L = "Nighties_L";
    public static final String NightSuit = "NightSuit";
    public static final String N_PL = "N_PL";
    public static final String N_PBoys = "N_PBoys";
    public static final String Bra = "Bra";
    public static final String Panties = "Panties";
    public static final String K_BabyBed = "K_BabyBed";
    public static final String K_BabyBlanket = "K_BabyBlanket";
    public static final String K_BabyCarryBag = "K_BabyCarryBag";
    public static final String K_BabyTowel = "K_BabyTowel";
    public static final String K_BabySocks_Glous = "K_BabySocks_Glouse";
    public static final String Shorts_3 = "Shorts_3_4";

    public static final String GFLRBanians = "GFLRBanians";
    public static final String GFLRUnderwear = "GFLRUnderwear";
    public static final String GFLRTowelsHL = "GFLRTowelsHL";
    public static final String GFLRTowelsTurkish = "GFLRTowelsTurkish";
    public static final String GFLRDoorCurtain = "GFLRDoorCurtain";
    public static final String GFLRBlankets = "GFLRBlankets";
    public static final String GFLRPurses = "GFLRPurses";
    public static final String GFLRBedSheets = "GFLRBedSheets";
    public static final String GFLRChaddars = "GFLRChaddars";
    public static final String MGLRTJeans = "MGLRTJeans";
    public static final String MGLRTCottonPants = "MGLRTCottonPants";
    public static final String MGLRTShirts_Cargo = "MGLRTShirts_Cargo";
    public static final String MGLRTFormalShirts = "MGLRTFormalShirts";
    public static final String MGLRTTShirts = "MGLRTTShirts";
    public static final String MGLRTBrandedJeans = "MGLRTBrandedJeans";
    public static final String MGLRTBrandedShirts = "MGLRTBrandedShirts";
    public static final String MGLRTKP_Partywear_Suits = "MGLRTKP_Partywear_Suits";
    public static final String MGLRTBurmadas = "MGLRTBurmadas";
    public static final String MGLRTShirts_Panamerica_Scapes = "MGLRTShirts_Panamerica_Scapes";


    /*Daily Reports*/
    public static final String BranchId = "BranchId";
    public static final String Sale = "Sale";
    public static final String Checking_Counter_Amount_Pcs = "Checking_Counter_Amount_Pcs";
    public static final String Unpaid = "Unpaid";
    public static final String Rg = "Rg";
    public static final String Axis = "Axis";
    public static final String Hdfc = "Hdfc";
    public static final String Cash_On_Hand = "Cash_On_Hand";
    public static final String Opening_Cash = "Opening_Cash";
    public static final String Bank_Deposit = "Bank_Deposit";
    public static final String Cash_Short = "Cash_Short";
    public static final String Synd_Odh = "Synd_Odh";
    public static final String Unpaid_Parcels_Amount_Type = "Unpaid_Parcels_Amount_Type";
    public static final String Unpaid_Parcels_Amount = "Unpaid_Parcels_Amount";
    public static final String Night_Cash_or_Opening_Cash = "Night_Cash_or_Opening_Cash";
    public static final String Daily_Units_Power_or_Fuel = "Daily_Units_Power_or_Fuel";
    public static final String Daily_Extra_Staff = "Daily_Extra_Staff";
    public static final String Week_Off_Holiday = "Week_Off_Holiday";
    public static final String Daily_Absent_Staff = "Daily_Absent_Staff";
    public static final String Daily_Present_or_Tota = "Daily_Present_or_Total";
    public static final String Tea_x_Present_Staff_Custo = "Tea_x_Present_Staff_Custo";
    public static final String Spl_Less = "Spl_Less";
    public static final String Daily_Water_Jars = "Daily_Water_Jars";
    public static final String Plastic_Covers_or_Jute_Bags_Amt = "Plastic_Covers_or_Jute_Bags_Amt";
    public static final String How_Many_Staff_Shop_Open_or_Dvlp = "How_Many_Staff_Shop_Open_or_Dvlp";
    public static final String Prayer = "Prayer";
    public static final String Compounding_or_Late_Coming = "Compounding_or_Late_Coming";
    public static final String Tiffin_Box = "Tiffin_Box";
    public static final String Staff_Low_Sale_Remainder = "Staff_Low_Sale_Remainder";
    public static final String Gate_Pass_with_Receipt_Number = "Gate_Pass_with_Receipt_Number";
    public static final String Cancel_Bills_Sign_with_Photos = "Cancel_Bills_Sign_with_Photos";
    public static final String Alterness_Check_Meterage_SS = "Alterness_Check_Meterage_SS";
    public static final String Alterness_Check_inward_qty = "Alterness_Check_inward_qty";
    public static final String Alterness_Check_secu_Alert = "Alterness_Check_secu_Alert";
    public static final String Maintaince_Sheet = "Maintaince_Sheet";
    public static final String Night_Watchman_Call_Alert = "Night_Watchman_Call_Alert";
    public static final String PhCall_Shop_Open_or_Dvlp = "PhCall_Shop_Open_or_Dvlp";
    public static final String DB_and_SB_Entry = "DB_and_SB_Entry";
    public static final String Noof_Parcel_or_LRs = "Noof_Parcel_or_LRs";
    public static final String Visiting_with_report = "Visiting_with_report";
    public static final String Eye_Hospital_OP_nd_Surgeries_OP = "Eye_Hospital_OP_nd_Surgeries_OP";
    public static final String Eye_Hospital_OP_nd_Surgeries_Surgery_Free = "Eye_Hospital_OP_nd_Surgeries_Surgery_Free";
    public static final String Eye_Hospital_OP_nd_Surgeries_Pay = "Eye_Hospital_OP_nd_Surgeries_Pay";
    public static final String Blood_Bank_Stock = "Blood_Bank_Stock";
    public static final String Dental_Hospital = "Dental_Hospital";
    public static final String Roles = "Role";

    /*Proform Fragments*/
    public static final String BF = "BF";
    public static final String Balance = "Balance";
    public static final String Branchid = "Branchid";
    public static final String FLR = "FLR";
    public static final String OpenBal = "OpenBal";
    public static final String RCD = "RCD";
    public static final String RoleS = "Role";
    public static final String StatusS = "Status";
    public static final String Total = "Total";
    public static final String Userid = "Userid";


}
