package server_side;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helpers.VolleyMultipartRequest;
import application_controller.ApplicationController;
import custom_interfaces.CallBack;
import model.Baby;
import model.ChartData;
import model.ChildSkill;
import model.Doctor;
import model.DoctorProfileInfo;
import model.FoodType;
import model.FoodTypeSample;
import model.MainSection;
import model.Notification;
import model.Quantity;
import model.QuantitySample;
import model.SecodarySection;
import model.SleepChartData;
import model.SleepSample;
import model.User;
import model.VaccenationRecord;
import model.Vaccination;
import model.VaccinationAdv;
import model.VaccinationConfirmationReq;

/**
 * Created by Amal on 4/8/2016.
 */
public class APIManager {

    private final APITypes TYPE;
    private String URL;
    private static final String TAG                           = APIManager.class.getName();
    private static final String BASE_URL                      = AppConfig.HOST_URL + "baby_health_care/API/loginAPI/";//"androidTest/index.php/API/loginAPI/";
    private static final String GET_BASE_VACCINATIONS_URL     = BASE_URL + "get_base_vaccinations";
    private static final String ADD_DEVICE_URL                = BASE_URL + "register_device";
    private static final String DELETE_DEVICE_URL             = BASE_URL + "unregister_device";

    public Boolean get_babies_status              = null;
    public Boolean get_main_sections_status       = null;
    public Boolean get_secondary_sections_status  = null;
    public Boolean get_food_type_status           = null;
    public Boolean get_food_type_samples_status   = null;
    public Boolean get_base_vaccinations_status   = null;
    public Boolean add_device_status              = null;

    public User loggedin_user                            = null;
    public ArrayList<Baby> babies                        = null;
    public ArrayList<MainSection> main_sections          = null;
    public ArrayList<SecodarySection> secondary_sections = null;
    public String error_message                          = null;
    public ArrayList<FoodType> foodTypes                 = null;
    public ArrayList<FoodTypeSample> foodTypeSamples     = null;
    public ArrayList<ChartData> chart_data               = null;
    public ArrayList<Quantity> quantities                = null;
    public ArrayList<QuantitySample> quantity_samples    = null;
    public ArrayList<SleepChartData> standard_sleep_data = null;
    public ArrayList<Double> baby_sleep_data             = null;
    public ArrayList<ChildSkill> baby_Child_skills = null;
    public ArrayList<SleepSample> sleep_samples          = null;
    public ArrayList<Vaccination> base_vaccinations      = null;
    public String token_id                              = null;
    public ArrayList<Doctor> doctors                       = null;
    public ArrayList<VaccenationRecord> record           = null;
    public VaccinationAdv adv = null;
    public Boolean is_registered = null;
    public Integer registeration_id = null;
    public VaccinationConfirmationReq vacc_req = null;
    public ArrayList<Notification> notifications = null;
    public JSONObject notification_message = null;
    public Boolean request_sent = null;
    public Boolean is_linked = null;
    public Integer link_id = null;
    public Boolean has_reviewed = null;
    public Integer review_id = null;
    public DoctorProfileInfo doctorProfile = null;

    public String message;
    public Boolean query_status = null;
    public String new_id;

    public APIManager(APITypes type) {
        TYPE = type;
        setURL();
    }

    private void setURL()
    {
        switch (TYPE){
            case LOGIN: URL = AppConfig.LOGIN_URL;
                break;
            case REGISTER: URL = AppConfig.REGISTER_URL;
                break;
            case UPDATE_PARENT: URL = AppConfig.UPDATE_PARENT_URL;
                break;
            case UPDATE_BABY: URL = AppConfig.UPDATE_BABY_URL;
                break;
            case ADD_BABY: URL = AppConfig.ADD_BABY_URL;
                break;
            case GET_BABIES: URL = AppConfig.GET_BABIES_URL;
                break;
            case CHANGE_PASSWORD: URL = AppConfig.CHANGE_PASSWORD_URL;
                break;
            case GET_MAIN_SECTIONS: URL = AppConfig.GET_MAIN_SECTIONS_URL;
                break;
            case GET_SECONDARY_SECTIONS: URL = AppConfig.GET_SECONDARY_SECTIONS_URL;
                break;
            case GET_FOOD_TYPES: URL = AppConfig.GET_FOOD_TYPES_URL;
                break;
            case GET_FOOD_TYPE_SAMPLES: URL = AppConfig.GET_FOOD_TYPE_SAMPLES_URL;
                break;
            case UPDATE_FOOD_TYPE: URL = AppConfig.UPDATE_FOOD_TYPE_URL;
                break;
            case UPDATE_FOOD_TYPE_SAMPLE: URL = AppConfig.UPDATE_FOOD_TYPE_SAMPLE_URL;
                break;
            case ADD_FOOD_TYPE: URL = AppConfig.ADD_FOOD_TYPE_URL;
                break;
            case ADD_FOOD_TYPE_SAMPLE: URL = AppConfig.ADD_FOOD_TYPE_SAMPLE_URL;
                break;
            case GET_CHART_DATA: URL = AppConfig.GET_CHART_DATA_URL;
                break;
            case GET_QUANTITIES: URL = AppConfig.GET_QUANTITIES_URL;
                break;
            case GET_QUANTITY_SAMPLES: URL = AppConfig.GET_QUANTITY_SAMPLES_URL;
                break;
            case GET_SLEEP_CHART_DATA: URL = AppConfig.GET_SLEEP_CHART_DATA_URL;
                break;
            case GET_ALL_SKILLS: URL = AppConfig.GET_ALL_SKILLS_URL;
                break;
            case GET_SLEEP_SAMPLES: URL = AppConfig.GET_SLEEP_SAMPLES_URL;
                break;
            case ADD_SLEEP_SAMPLE: URL = AppConfig.ADD_SLEEP_SAMPLES_URL;
                break;
            case UPDATE_SLEEP_SAMPLE: URL = AppConfig.UPDATE_SLEEP_SAMPLES_URL;
                break;
            case ADD_QUANTITY_SAMPLE: URL =  AppConfig.ADD_QUANTITY_SAMPLES_URL;
                break;
            case UPDATE_QUANTITY_SAMPLE: URL =  AppConfig.UPDATE_QUANTITY_SAMPLES_URL;
                break;
            case ADD_SKILL: URL = AppConfig.ADD_SKILL_URL;
                break;
            case GET_BASE_VACCINATIONS: URL = GET_BASE_VACCINATIONS_URL;
                break;
            case ADD_DEVICE: URL = ADD_DEVICE_URL;
                break;
            case SEARCH_DOCTOR: URL = AppConfig.SERCH_DOCTOR_URL;
                break;
            case GET_VEC_RECORD: URL = AppConfig.GET_VEC_RECORD_URL;
                break;
            case GET_VAC_ADV: URL = AppConfig.GET_VAC_ADV_URL;
                break;
            case REGISTER_FOR_VAC_ADV: URL = AppConfig.REGISTER_FOR_VAC_ADV_URL;
                break;
            case DELETE_REG_FOR_VAC_ADV: URL = AppConfig.DELETE_REG_FOR_VAC_ADV_URL;
                break;
            case IS_REGISTERED_FOR_ADV: URL = AppConfig.IS_REGISTERED_FOR_ADV_URL;
                break;
            case GET_VAC_CONFIRM_REQ: URL = AppConfig.GET_VAC_CONFIRM_REQ_URL;
                break;
            case CONFIRM_VAC_REQ: URL = AppConfig.CONFIRM_VAC_REQ_URL;
                break;
            case REFUSE_VAC_REQUEST: URL = AppConfig.REFUSE_VAC_REQUEST_URL;
                break;
            case GET_ALL_VAC_ADV: URL = AppConfig.GET_ALL_VAC_ADV_URL;
                break;
            case GET_ALL_CONFIRM_VAC_REQ: URL = AppConfig.GET_ALL_CONFIRM_VAC_REQ_URL;
                break;
            case SEND_NEW_VAC_REQ_NOTIFICATION: URL = AppConfig.SEND_NEW_VAC_REQ_NOTIFICATION_URL;
                break;
            case LINK_TO_DOCTOR: URL = AppConfig.LINK_TO_DOCTOR_URL;
                break;
            case UNLINK_TO_DOCTOR: URL = AppConfig.UNLINK_TO_DOCTOR_URL;
                break;
            case REQUEST_WAS_SENT: URL = AppConfig.REQUEST_WAS_SENT_URL;
                break;
            case REVIEW_DOCTOR: URL = AppConfig.REVIEW_DOCTOR_URL;
                break;
            case DELETE_REVIEW: URL = AppConfig.DELETE_REVIEW_URL;
                break;
            case HAS_REVIEWED: URL = AppConfig.HAS_REVIEWED_URL;
                break;
            case GET_DOCTOR_PROFILE_INFO: URL = AppConfig.GET_DOCTOR_PROFILE_INFO_URL;
                break;
            case SEND_NEW_LINK_REQ_NOTIFICATION: URL = AppConfig.SEND_NEW_LINK_REQ_NOTIFICATION_URL;
                break;
            case DELETE_BABY: URL = AppConfig.DELETE_BABY_URL;
                break;
            case DELETE_QUANTITY_SAMPLE: URL = AppConfig.DELETE_QUANTITY_SAMPLES_URL;
                break;
            case DELETE_SLEEP_SAMPLE: URL = AppConfig.DELETE_SLEEP_SAMPLES_URL;
                break;
            case DELETE_FOOD_TYPE: URL = AppConfig.DELETE_FOOD_TYPE_URL;
                break;
            case UPDATE_SKILL: URL = AppConfig.UPDATE_SKILL_URL;
                break;
            case DELETE_SKILL: URL = AppConfig.DELETE_SKILL_URL;
                break;
            case SEE_NOTIFICATION: URL = AppConfig.SEE_NOTIFICATION_URL;
                break;
            case GET_LINKED_DOCTORS: URL = AppConfig.GET_LINKED_DOCTORS_URL;
                break;
            case DELETE_DEVICE: URL = DELETE_DEVICE_URL;
                break;
            case DELETE_NOTIFICATION: URL = AppConfig.DELETE_NOTIFICATION_URL;
                break;
            case DELETE_FOOD_SAMPLE: URL = AppConfig.DELETE_FOOD_SAMPLE_URL;
                break;
            case IS_LINKED_TO_DOCTOR: URL = AppConfig.IS_LINKED_TO_DOCTOR;
                break;
        }
    }

    public void sendRequest(final JSONObject json_obj, final CallBack onResponse)
    {
        reset();

        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.v("amal", response);
                        handleResponse(response);
                        onResponse.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> json_data = new HashMap<>();
                json_data.put("json_data", json_obj.toString());
                return json_data;
            }
        };

        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    public void sendMultiPartsRequest(final JSONObject json_obj, final Map<String,
            byte[]> byte_data, final CallBack onResponse)
    {
        reset();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String response_string = new String(response.data);
                //Log.v("amal", response_string);
                handleResponse(response_string);
                onResponse.onResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> json_data = new HashMap<>();
                json_data.put("json_data", json_obj.toString());
                return json_data;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for (Map.Entry<String, byte[]> entry : byte_data.entrySet())
                {
                    params.put(entry.getKey(), new DataPart("file_avatar.jpg", entry.getValue()));
                }

                return params;
            }
        };

        // add the request object to the queue to be executed
        ApplicationController.getInstance().addToRequestQueue(multipartRequest);
    }

    private void handleResponse(String response)
    {
        switch (TYPE) {
            case LOGIN: handleLoginResponse(response);
                break;
            case REGISTER: handleRegisterResponse(response);
                break;
            case UPDATE_PARENT: handleLoginResponse(response);//handleUpdateQuery(response);
                break;
            case UPDATE_BABY: handleUpdateBaby(response);//handleUpdateQuery(response);
                break;
            case ADD_BABY: handleUpdateBaby(response);//handleAddQuery(response);
                break;
            case GET_BABIES: handleGetBabiesResponse(response);
                break;
            case CHANGE_PASSWORD: handleUpdateQuery(response);//handleChangePassword(response);
                break;
            case GET_MAIN_SECTIONS: handleGetMainSectionsResponse(response);
                break;
            case GET_SECONDARY_SECTIONS: handleGetSecondarySectionsResponse(response);
                break;
            case GET_FOOD_TYPES: handleGetFoodTypes(response);
                break;
            case GET_FOOD_TYPE_SAMPLES: handleGetFoodTypeSamples(response);
                break;
            case ADD_FOOD_TYPE: handleAddQuery(response);
                break;
            case ADD_FOOD_TYPE_SAMPLE: handleAddQuery(response);
                break;
            case UPDATE_FOOD_TYPE: handleUpdateQuery(response);
                break;
            case UPDATE_FOOD_TYPE_SAMPLE: handleUpdateQuery(response);
                break;
            case GET_CHART_DATA: handleGetChartData(response);
                break;
            case GET_QUANTITIES: handleGetQuantities(response);
                break;
            case GET_QUANTITY_SAMPLES: handleGetQuantitySamples(response);
                break;
            case ADD_QUANTITY_SAMPLE: handleAddQuery(response);
                break;
            case UPDATE_QUANTITY_SAMPLE: handleUpdateQuery(response);
                break;
            case GET_SLEEP_CHART_DATA: handleSleepChartData(response);
                break;
            case GET_ALL_SKILLS: handleAllSkills(response);
                break;
            case GET_SLEEP_SAMPLES: handleSleepSamples(response);
                break;
            case ADD_SLEEP_SAMPLE: handleAddQuery(response);
                break;
            case UPDATE_SLEEP_SAMPLE: handleUpdateQuery(response);
                break;
            case ADD_SKILL: handleAddQuery(response);
                break;
            case GET_BASE_VACCINATIONS: handleGetBaseVaccinations(response);
                break;
            case ADD_DEVICE: handleAddDevice(response);
                break;
            case SEARCH_DOCTOR: handleSearchDoctor(response);
                break;
            case GET_VEC_RECORD: handleGetVecRecord(response);
                break;
            case GET_VAC_ADV: handleGetAdv(response);
                break;
            case REGISTER_FOR_VAC_ADV: handleAddQuery(response);
                break;
            case DELETE_REG_FOR_VAC_ADV: handleDeleteQuery(response);
                break;
            case IS_REGISTERED_FOR_ADV: handleIsRegisteredInAdv(response);
                break;
            case GET_VAC_CONFIRM_REQ: handleGetVacConfirmationReq(response);
                break;
            case CONFIRM_VAC_REQ: handleUpdateQuery(response);
                break;
            case REFUSE_VAC_REQUEST: handleDeleteQuery(response);
                break;
            case GET_ALL_VAC_ADV: handleGetAllVaccAdv(response);
                break;
            case GET_ALL_CONFIRM_VAC_REQ: handleGetALLVaccConfirmReq(response);
                break;
            case SEND_NEW_VAC_REQ_NOTIFICATION: handleSendNotification(response);
                break;
            case LINK_TO_DOCTOR: handleAddQuery(response);
                break;
            case UNLINK_TO_DOCTOR: handleDeleteQuery(response);
                break;
            case REQUEST_WAS_SENT: handleRequestSent(response);
                break;
            case IS_LINKED_TO_DOCTOR: handleIsLinked(response);
                break;
            case REVIEW_DOCTOR: handleAddQuery(response);
                break;
            case DELETE_REVIEW: handleDeleteQuery(response);
                break;
            case HAS_REVIEWED: handleHasReviewed(response);
                break;
            case GET_DOCTOR_PROFILE_INFO: handleDoctorProfileInfo(response);
                break;
            case SEND_NEW_LINK_REQ_NOTIFICATION: handleSendNotification(response);
                break;
            case DELETE_BABY: handleDeleteQuery(response);
                break;
            case DELETE_QUANTITY_SAMPLE: handleDeleteQuery(response);
                break;
            case DELETE_SLEEP_SAMPLE: handleDeleteQuery(response);
                break;
            case DELETE_FOOD_TYPE: handleDeleteQuery(response);
                break;
            case UPDATE_SKILL: handleUpdateQuery(response);
                break;
            case DELETE_SKILL: handleDeleteQuery(response);
                break;
            case SEE_NOTIFICATION: handleUpdateQuery(response);
                break;
            case GET_LINKED_DOCTORS: handleGetLinkedDoctors(response);
                break;
            case DELETE_FOOD_SAMPLE: handleDeleteQuery(response);
                break;
        }
    }

    private void handleLoginResponse(String response)
    {
        try {
            JSONArray responseArray = new JSONArray(response);
            JSONObject seccuss_obj = responseArray.getJSONObject(0);

            if(seccuss_obj.getString("success").equals("1")) {
                query_status = true;
                ObjectMapper objMap = new ObjectMapper();
                loggedin_user = objMap.readValue(responseArray.getString(1), User.class);
            }
            else
            {
                query_status = false;
            }

            message = seccuss_obj.getString("message");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateUserResponse(String response)
    {
        try {
            JSONArray responseArray = new JSONArray(response);
            JSONObject seccuss_obj = responseArray.getJSONObject(0);

            if(seccuss_obj.getString("success").equals("1")) {
                query_status = true;
                ObjectMapper objMap = new ObjectMapper();
                loggedin_user = objMap.readValue(responseArray.getString(1), User.class);
            }
            else
            {
                query_status = false;
                message = seccuss_obj.getString("message");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Baby new_baby = null;
    private void handleUpdateBaby(String response)
    {
        try {
            JSONArray responseArray = new JSONArray(response);
            JSONObject seccuss_obj = responseArray.getJSONObject(0);

            if(seccuss_obj.getString("success").equals("1")) {
                query_status = true;
                ObjectMapper objMap = new ObjectMapper();
                new_baby = objMap.readValue(responseArray.getString(1), Baby.class);
            }
            else
            {
                query_status = false;
            }
            message = seccuss_obj.getString("message");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRegisterResponse(String response)
    {
        try {
            JSONArray responseArray = new JSONArray(response);

            JSONObject seccuss_obj = responseArray.getJSONObject(0);

            if(seccuss_obj.getString("success").equals("1")) {
                query_status = true;
                ObjectMapper objMap = new ObjectMapper();
                loggedin_user = objMap.readValue(responseArray.getString(1), User.class);
            }
            else
            {
                query_status = false;
                message = seccuss_obj.getString("message");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetBabiesResponse(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                babies = objMap.readValue(String.valueOf(data.getJSONArray(1)), new TypeReference<ArrayList<Baby>>() {});
                get_babies_status = true;
            }
            else get_babies_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetMainSectionsResponse(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                main_sections = objMap.readValue(String.valueOf(data.getJSONArray(1)), new TypeReference<ArrayList<MainSection>>() {});
                get_main_sections_status = true;
            }
            else get_main_sections_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetSecondarySectionsResponse(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                secondary_sections = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<SecodarySection>>() {});
                get_secondary_sections_status = true;
            }
            else get_secondary_sections_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddQuery(String response)
    {
        error_message = response;
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                new_id = data.getJSONObject(1).getString("new_id");
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateQuery(String response)
    {
        error_message = response;
        try {
            JSONArray data = new JSONArray(response);

            query_status = data.getJSONObject(0).getInt("success") == 1;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteQuery(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            query_status = data.getJSONObject(0).getInt("success") == 1;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleGetFoodTypes(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                foodTypes = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<FoodType>>() {});
                get_food_type_status = true;
            }
            else get_food_type_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetFoodTypeSamples(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                foodTypeSamples = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<FoodTypeSample>>() {});
                get_food_type_samples_status = true;
            }
            else get_food_type_samples_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetChartData(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                chart_data = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<ChartData>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetQuantities(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                quantities = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<Quantity>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetQuantitySamples(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                quantity_samples = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<QuantitySample>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSleepChartData(String response)
    {
        error_message = response;
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                JSONArray standard_data = data.getJSONObject(1).getJSONArray("standard_data");

                ObjectMapper objMap = new ObjectMapper();
                standard_sleep_data = objMap.readValue(standard_data.toString(),
                        new TypeReference<ArrayList<SleepChartData>>(){});

                baby_sleep_data = new ArrayList<>();
                JSONArray baby_data = data.getJSONObject(1).getJSONArray("baby_data");
                for (int i = 0; i < baby_data.length(); i++)
                {
                    if(baby_data.getJSONObject(i).isNull("amount"))
                        baby_sleep_data.add(0.0);
                    else
                        baby_sleep_data.add(baby_data.getJSONObject(i).getDouble("amount"));
                }

                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAllSkills(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                baby_Child_skills = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<ChildSkill>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddDevice(String response)
    {
        try {
            JSONObject json  = new JSONObject(response);
            token_id = json.getString("id");
            add_device_status = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleSleepSamples(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                sleep_samples = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<SleepSample>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetBaseVaccinations(String response)
    {
        try {
            ObjectMapper objMap = new ObjectMapper();
            base_vaccinations = objMap.readValue(response, new TypeReference<ArrayList<Vaccination>>(){});
            get_base_vaccinations_status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSearchDoctor(String response)
    {
        try {
            ObjectMapper objMap = new ObjectMapper();
            doctors = objMap.readValue(response, new TypeReference<ArrayList<Doctor>>(){});
            query_status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetLinkedDoctors(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                doctors = objMap.readValue(data.getJSONArray(1).toString(), new TypeReference<ArrayList<Doctor>>(){});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetVecRecord(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                record = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<VaccenationRecord>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetAdv(String response)
    {
        Log.v("amal", response);

        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                adv = objMap.readValue(data.getString(1), VaccinationAdv.class);
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetVacConfirmationReq(String response)
    {
        Log.v("amaaal", response);
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                vacc_req = objMap.readValue(data.getString(1), VaccinationConfirmationReq.class);
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIsRegisteredInAdv(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                JSONObject obj = data.getJSONObject(1);
                is_registered = obj.getBoolean("is_registered");
                if(is_registered)
                    registeration_id = obj.getInt("registration_id");
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleGetAllVaccAdv(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                notifications = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<Notification>>() {
                        });
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetALLVaccConfirmReq(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                notifications = objMap.readValue(String.valueOf(data.getJSONArray(1)),
                        new TypeReference<ArrayList<Notification>>() {});
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSendNotification(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                notification_message = data.getJSONObject(1);
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleRequestSent(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                JSONObject obj = data.getJSONObject(1);
                request_sent = obj.getBoolean("is_sent");
                if(request_sent)
                    link_id = obj.getInt("request_id");
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleIsLinked(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                JSONObject obj = data.getJSONObject(1);
                is_linked = obj.getBoolean("is_linked");
                if(is_linked)
                    link_id = obj.getInt("request_id");
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleHasReviewed(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                JSONObject obj = data.getJSONObject(1);
                has_reviewed = obj.getBoolean("has_reviewed");
                if(has_reviewed)
                    review_id = obj.getInt("review_id");
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleDoctorProfileInfo(String response)
    {
        try {
            JSONArray data = new JSONArray(response);

            if(data.getJSONObject(0).getInt("success") == 1) {
                ObjectMapper objMap = new ObjectMapper();
                doctorProfile = objMap.readValue(data.getString(1), DoctorProfileInfo.class);
                query_status = true;
            }
            else query_status = false;

            message = data.getJSONObject(0).getString("message");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void reset()
    {
//        loggedin_user = null;
//        //login_status = null;
//        register_status = null;
//        update_user_status = null;
//        update_baby_status = null;
//        add_baby_status = null;
//        babies = null;
//        get_babies_status = null;
//        get_main_sections_status = null;
//        main_sections = null;
//        secondary_sections = null;
//        get_secondary_sections_status = null;
//        food_samples = null;
//        get_food_samples_status = null;
//        add_food_samples_status = null;
//        added_sample = null;
//        update_food_samples_status = null;
//        updated_sample = null;
//        error_message = null;
    }
}
