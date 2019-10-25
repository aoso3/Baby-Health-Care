package server_side;

/**
 * Created by Amal on 4/8/2016.
 */
public class AppConfig {
    //10.0.2.2
    public static final String HOST_URL           = "http://192.168.43.120/";//"http://192.168.1.6/";
    private static final String API_URL           = HOST_URL + "baby_health_care/API/";//"androidTest/index.php/API/";
    private static final String URL_UPLOAD_FOLDER = HOST_URL + "baby_health_care/assets/images/";
    public static final String SOCKET_URL         = "http://192.168.43.120:3000";//"http://192.168.1.6:3000";

    public static String URL_MAIN_SECTIONS_ICONS         = URL_UPLOAD_FOLDER + "main_sections_icons/";
    public static String URL_MAIN_SECTIONS_DETAILS_ICONS = URL_UPLOAD_FOLDER + "main_sections_details_icons/";
    public static String URL_CHILDREN_AVATAR             = URL_UPLOAD_FOLDER + "baby_profile/";
    public static String URL_USER_AVATAR                 = URL_UPLOAD_FOLDER + "users/";
    public static String URL_NOTIFICATION_ICO            = URL_UPLOAD_FOLDER + "notification/";

    public static final String LOGIN_API                            = API_URL + "loginAPI/";
    private static final String BABY_API                            = API_URL + "babyAPI/";
    private static final String FOOD_API                            = API_URL + "foodAPI/";
    private static final String SLEEP_API                           = API_URL + "sleepAPI/";
    private static final String GROWTH_API                          = API_URL + "growthAPI/";
    private static final String SECTIONS_API                        = API_URL + "sectionsAPI/";
    private static final String SKILLS_API                          = API_URL + "skillsAPI/";
    private static final String DOCTOR_API                          = API_URL + "doctorAPI/";
    private static final String PATIENT_REVIEW_API                  = API_URL + "patientReviewAPI/";
    private static final String VECCATION_API                       = API_URL + "vaccinationsAPI/";
    private static final String NOTIFICATIONS_API                   = API_URL + "notificationsAPI/";

    public static final String LOGIN_URL                            = LOGIN_API + "login";
    public static final String REGISTER_URL                         = LOGIN_API + "register";
    public static final String UPDATE_PARENT_URL                    = LOGIN_API + "update_user";
    public static final String CHANGE_PASSWORD_URL                  = LOGIN_API + "change_password";

    public static final String UPDATE_BABY_URL                      = BABY_API + "update_baby";
    public static final String ADD_BABY_URL                         = BABY_API + "add_baby";
    public static final String GET_BABIES_URL                       = BABY_API + "get_babies_by_user";
    public static final String DELETE_BABY_URL                      = BABY_API + "delete_baby";

    public static final String GET_MAIN_SECTIONS_URL                = SECTIONS_API + "get_main_sections";
    public static final String GET_SECONDARY_SECTIONS_URL           = SECTIONS_API + "get_main_section_details";

    public static final String GET_FOOD_TYPES_URL                   = FOOD_API + "get_food_types";
    public static final String GET_FOOD_TYPE_SAMPLES_URL            = FOOD_API + "get_food_type_samples";
    public static final String ADD_FOOD_TYPE_URL                    = FOOD_API + "add_food_type";
    public static final String ADD_FOOD_TYPE_SAMPLE_URL             = FOOD_API + "add_food_sample";
    public static final String DELETE_FOOD_TYPE_URL                 = FOOD_API + "delete_food_type";
    public static final String UPDATE_FOOD_TYPE_URL                 = FOOD_API + "update_food_type";
    public static final String UPDATE_FOOD_TYPE_SAMPLE_URL          = FOOD_API + "update_food_sample";
    public static final String DELETE_FOOD_SAMPLE_URL               = FOOD_API + "delete_food_sample";

    public static final String GET_CHART_DATA_URL                   = GROWTH_API + "get_chart_data";
    public static final String GET_QUANTITIES_URL                   = GROWTH_API + "get_quantities";
    public static final String GET_QUANTITY_SAMPLES_URL             = GROWTH_API + "get_baby_quantities";
    public static final String ADD_QUANTITY_SAMPLES_URL             = GROWTH_API + "add_quantity_sample";
    public static final String UPDATE_QUANTITY_SAMPLES_URL          = GROWTH_API + "update_quantity_sample";
    public static final String DELETE_QUANTITY_SAMPLES_URL          = GROWTH_API + "delete_quantity_sample";

    public static final String GET_SLEEP_CHART_DATA_URL             = SLEEP_API + "get_sleep_chart_data";
    public static final String GET_SLEEP_SAMPLES_URL                = SLEEP_API + "get_sleep_samples";
    public static final String ADD_SLEEP_SAMPLES_URL                = SLEEP_API + "add_sleep_sample";
    public static final String UPDATE_SLEEP_SAMPLES_URL             = SLEEP_API + "update_sleep_sample";
    public static final String DELETE_SLEEP_SAMPLES_URL             = SLEEP_API + "delete_sleep_sample";

    public static final String GET_ALL_SKILLS_URL                   = SKILLS_API + "get_all_skills";
    public static final String ADD_SKILL_URL                        = SKILLS_API + "add_skill";
    public static final String UPDATE_SKILL_URL                     = SKILLS_API + "update_skill";
    public static final String DELETE_SKILL_URL                     = SKILLS_API + "delete_skill";

    public static final String SERCH_DOCTOR_URL                     = DOCTOR_API + "search_ten_doctors";
    public static final String LINK_TO_DOCTOR_URL                   = DOCTOR_API + "send_request";
    public static final String REQUEST_WAS_SENT_URL                 = DOCTOR_API + "request_sent";
    public static final String IS_LINKED_TO_DOCTOR                  = DOCTOR_API + "is_linked";
    public static final String UNLINK_TO_DOCTOR_URL                 = DOCTOR_API + "delete_request";
    public static final String GET_DOCTOR_PROFILE_INFO_URL          = DOCTOR_API + "get_doctor_rating_information";
    public static final String GET_LINKED_DOCTORS_URL               = DOCTOR_API + "get_linked_doctors";

    public static final String GET_VEC_RECORD_URL                   = VECCATION_API + "get_child_vaccinations_record";
    public static final String GET_VAC_ADV_URL                      = VECCATION_API + "get_adv";
    public static final String REGISTER_FOR_VAC_ADV_URL             = VECCATION_API + "register_for_vaccination";
    public static final String DELETE_REG_FOR_VAC_ADV_URL           = VECCATION_API + "delete_register_for_vaccination";
    public static final String IS_REGISTERED_FOR_ADV_URL            = VECCATION_API + "is_registered_for_vaccination";
    public static final String GET_VAC_CONFIRM_REQ_URL              = VECCATION_API + "get_vaccination_confirmation_request";
    public static final String CONFIRM_VAC_REQ_URL                  = VECCATION_API + "Confirm_vaccination";
    public static final String REFUSE_VAC_REQUEST_URL               = VECCATION_API + "refuse_vaccination";

    public static final String GET_ALL_VAC_ADV_URL                  = NOTIFICATIONS_API + "get_all_adv_notifications";
    public static final String GET_ALL_CONFIRM_VAC_REQ_URL          = NOTIFICATIONS_API + "get_all_not_seen_confirmation_requests";
    public static final String SEND_NEW_VAC_REQ_NOTIFICATION_URL    = NOTIFICATIONS_API + "add_vaccination_request_notification";
    public static final String SEND_NEW_LINK_REQ_NOTIFICATION_URL   = NOTIFICATIONS_API + "add_register_at_doctor_notification";
    public static final String SEE_NOTIFICATION_URL                 = NOTIFICATIONS_API + "see_notification";
    public static final String DELETE_NOTIFICATION_URL                 = NOTIFICATIONS_API + "delete_notification";

    public static final String REVIEW_DOCTOR_URL                    = PATIENT_REVIEW_API + "send_review";
    public static final String DELETE_REVIEW_URL                    = PATIENT_REVIEW_API + "delete_review";
    public static final String HAS_REVIEWED_URL                     = PATIENT_REVIEW_API + "has_review";

    //private static final String GET_BASE_VACCINATIONS_URL     = BASE_URL + "get_base_vaccinations";
    //private static final String ADD_DEVICE_URL                = BASE_URL + "register_device";

}
