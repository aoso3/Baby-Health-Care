<?php defined('BASEPATH') OR exit('No direct script access allowed');

class PatientReview_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

    function send_review($review)
    {
        $this->db->trans_start();
        $this->db->insert('patient_review', $review);
        $new_id = $this->db->insert_id();

        $this->load->model('Doctors_model');
        $this->Doctors_model->re_rate($review['id_doctor']);
        $this->db->trans_start();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not send a review, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "You have reviwed this doctor successfuly");
            return json_encode(array($sec_array, array('new_id' => $new_id)));
        }
    }

    function has_review($id_user, $id_doctor)
    {
        $this->db->trans_start();
        $this->db->select('id AS review_id')
                 ->from('patient_review')
                 ->where('id_patient', $id_user)
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

            if($count > 0) $query['has_reviewed'] = 'true';
            else $query['has_reviewed'] = 'false';

            return json_encode(array($sec_array, $query));
        }
    }

    function delete_review($id, $id_doctor)
    {
        $this->db->trans_start();

        $this->db->where('id', $id)
                 ->delete('patient_review');

        $this->load->model('Doctors_model');
        $this->Doctors_model->re_rate($id_doctor);

        $this->db->trans_complete();

        if ($this->db->trans_status() === FALSE)
        {
            $sec_array = array("success"=>"0", "message" => "Can not delete review, please try again later");
            return json_encode(array($sec_array));
        }
        else
        {
            $sec_array = array("success"=>"1", "message" => "Review was deleted successfuly");
            return json_encode(array($sec_array));
        }
    }

    function get_avg_waiting_time($id_doctor)
    {
        $this->db->trans_start();
        $this->db->select_avg('waiting_time')
                 ->from('patient_review')
                 ->where('id_doctor', $id_doctor);
        $result = $this->db->get()->result_array()[0]['waiting_time'];
        $this->db->trans_complete();

        if(empty($result))
            return 0;

        return $result;
    }

    function get_avg_office_cleanliness($id_doctor)
    {
        $this->db->trans_start();
        $this->db->select_avg('office_cleanliness')
                 ->from('patient_review')
                 ->where('id_doctor', $id_doctor);
        $result = $this->db->get()->result_array()[0]['office_cleanliness'];
        $this->db->trans_complete();

        if(empty($result))
            return 0;

        return $result;
    }

    function get_avg_explination_clarity($id_doctor)
    {
        $this->db->trans_start();
        $this->db->select_avg('explination_clarity')
                 ->from('patient_review')
                 ->where('id_doctor', $id_doctor);
        $result = $this->db->get()->result_array()[0]['explination_clarity'];
        $this->db->trans_complete();

        if(empty($result))
            return 0;
        
        return $result;
    }

    function get_doctor_reviews_count($id_doctor)
    {
        $this->db->select('*')
                 ->from('patient_review')
                 ->where('id_doctor', $id_doctor);
        $query = $this->db->count_all_results();

        return $query;
    }

    function get_doctor_review_rates($id_doctor)
    {
        $this->db->trans_start();
        $waiting_time = $this->get_avg_waiting_time($id_doctor);
        $office_cleanliness = $this->get_avg_office_cleanliness($id_doctor);
        $explination_clarity = $this->get_avg_explination_clarity($id_doctor);
        $reviews_count = $this->get_doctor_reviews_count($id_doctor);
        $this->db->trans_complete();

        $rates = array('waiting_time'=>$waiting_time, 
                       'office_cleanliness' => $office_cleanliness,
                       'explination_clarity'=>$explination_clarity,
                       'reviews_count' => $reviews_count);

        return $rates;
    }
}
?>