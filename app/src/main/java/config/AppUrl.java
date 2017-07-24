package config;

/**
 * Created by Hp on 9/26/2016.
 */
class AppUrl {
    //static final String BASE_URL ="http://41.186.53.35:8080/";//IP of MTN server
    static final String BASE_URL ="http://41.74.172.131:8080/";// IP to AOS server
    static final String LOGIN_URL ="EngenPayFuel/android/adminLogin";
    //static final String DIPPING_URL ="SpPayFuel/TankManagementService/diping";//dipping/create
    static final String DIPPING_URL ="EngenEquipment/dipping/create";
    //static final String TANKING_URL ="SpTanking/TankingManagementService/tanking/create";//tanking/create
    static final String TANKING_URL ="EngenEquipment/tanking/create";
    //static final String TANK_LIST_URL ="SpPayFuel/TankManagementService/tanks";//tank/list
    static final String TANK_LIST_URL ="EngenEquipment/tank/list";
    static final String PUMP_URL ="EngenEquipment/pump/pumpDash";
    static final String SET_INDEX_URL ="EngenEquipment/NozzleManagementService/nozzle/adjust";
    static final String REPORT_MONEY_URL ="EngenPayFuel/UserManagementService/user/shift";
    static final String PAYMENT_MODE_URL="EngenPayFuel/PaymentModeManagementService/paymentmodes/";
    static final String GET_USER_URL="EngenPayFuel/UserManagementService/users/";
    static final String GET_USER_REPORT_NOZZLE="EngenEquipment/ReportWebService/getNozzleReport";
    static final String GET_USER_LIST="EngenEquipment/ReportWebService/users";
    static final String POST_SHIFT_REPORT="EngenEquipment/ReportWebService/report/input";
    static final String GET_BRANCH_ORDERS="EngenEquipment/order/list";
}
