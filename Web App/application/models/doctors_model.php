<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Doctors_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

    function get_ten_doctors($rate, $id, $doctors_count)
    {
    	$this->db->select('id')
    		 	 ->from('user_role')
    		 	 ->where('name', 'doctor');
    	$role_id = $this->db->get()->result_array();

        $count_all = $this->get_doctors_num();

        $doctors = array();
        if($doctors_count < $count_all)
        {
        	$this->db->limit(10, 0)
        			 ->select('id, first_name, last_name,  email, phone, location, location_name, rate, pic, gender')
        			 ->from('users')
        			 ->where('id_role', $role_id[0]['id'])
        			 ->where('id !=', $id)
        			 ->where('rate <=', $rate)
        			 ->order_by('rate', 'desc');
        	$doctors = $this->db->get()->result_array();
        }

    	return json_encode($doctors);
    }

    function search_ten_doctors($word, $rate, $id, $doctors_count)
    {
        if($word == "")
            return $this->get_ten_doctors($rate, $id, $doctors_count);
        else
        {
        	$this->db->select('id')
        		 	 ->from('user_role')
        		 	 ->where('name', 'doctor');
        	$role_id = $this->db->get()->result_array();

            $count_all = $this->get_doctors_num();

            $doctors = array();
            if($doctors_count < $count_all)
            {
            	$this->db->limit(10, 0)
            			 ->select('id, first_name, last_name, email, phone, location, location_name, rate, pic, gender')
            			 ->from('users')
            			 ->where('id_role', $role_id[0]['id'])
            			 ->where('id !=', $id)
            			 ->where('rate <=', $rate)
                         ->where('first_name LIKE',  $word.'%')
                         ->or_where('last_name LIKE', $word.'%')
            			 ->order_by('rate', 'desc');
            	$doctors = $this->db->get()->result_array();
            }

        	return json_encode($doctors);
        }
    }

    function get_linked_doctors($id_child)
    {
        $this->db->trans_start();
        $this->db->select('users.id, first_name, last_name, phone, location, location_name, rate, pic, gender')
                 ->from('child_doctors')
                 ->join('users', 'users.id = child_doctors.id_doctor')
                 ->where('child_doctors.id_child', $id_child);
        $doctors = $this->db->get()->result_array();
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "");
            return json_encode(array($sec_array, $doctors));
        }
    }

    function send_request($request)
    {
        $this->db->trans_start();
    	$this->db->insert('child_doctors', $request);
        $new_id = $this->db->insert_id();
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not register, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "You have registerd successfuly");
            return json_encode(array($sec_array, array('new_id' => $new_id)));
        }
    }

    function request_sent($id_child, $id_doctor)
    {
        $this->db->trans_start();
        $this->db->select('id AS request_id')
                 ->from('child_doctors')
                 ->where('id_child', $id_child)
                 ->where('id_doctor', $id_doctor);
        $query = $this->db->get();
        $count = $query->num_rows();
        $query = $query->result_array();
        if($count > 0)
        $query = $query[0];
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "");

            if($count > 0) $query['is_sent'] = 'true';
            else $query['is_sent'] = 'false';

            return json_encode(array($sec_array, $query));
        }
    }

    function is_linked($id_child, $id_doctor)
    {
        $this->db->trans_start();
        $this->db->select('id AS request_id')
                 ->from('child_doctors')
                 ->where('id_child', $id_child)
                 ->where('id_doctor', $id_doctor)
                 ->where('approved', '1');
        $query = $this->db->get();
        $count = $query->num_rows();
        $query = $query->result_array();
        if($count > 0)
        $query = $query[0];
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "");

            if($count > 0) $query['is_linked'] = 'true';
            else $query['is_linked'] = 'false';

            return json_encode(array($sec_array, $query));
        }
    }

    function delete_request($id)
    {
        $this->db->trans_start();
    	$this->db->where('id', $id);
        $this->db->delete('child_doctors');
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not unregistered you, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "You have unregistered successfuly");
            return json_encode(array($sec_array));
        }
    }

    function re_rate($id_doctor)
    {
        $this->db->trans_start();
    	$this->load->model('PatientReview_model');
        $waiting = $this->PatientReview_model->get_avg_waiting_time($id_doctor);
        $office = $this->PatientReview_model->get_avg_office_cleanliness($id_doctor);
        $explantion = $this->PatientReview_model->get_avg_explination_clarity($id_doctor);
    	$patients = $this->patients_count($id_doctor);

        $rate = ($office + $explantion + $patients) - floor($waiting/10);

    	$this->db->where('id', $id_doctor)
    			 ->update('users', array('rate'=> $rate));
        $this->db->trans_complete();
    }

    function patients_count($id_doctor)
    {
    	$this->db->select('*')
    			 ->from('child_doctors')
    			 ->where('id_doctor', $id_doctor);
    	$query = $this->db->count_all_results();

    	return $query;
    }

    function get_doctor_rating_information($id_doctor)
    {
        $this->db->trans_start();
        $this->load->model('PatientReview_model');
        $rates = $this->PatientReview_model->get_doctor_review_rates($id_doctor);
        $patients_count = $this->patients_count($id_doctor);
        $rates['patients_count'] = $patients_count; 
        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "");
            return json_encode(array($sec_array, $rates));
        }
    }

    function get_doctors_num()
    {
        $this->db->select('*')
                 ->from('users')
                 ->where('id_role', "2");
        $query = $this->db->count_all_results();

        return $query;
    }    
}
?>