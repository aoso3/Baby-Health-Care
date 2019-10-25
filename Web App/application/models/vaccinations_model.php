<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Vaccinations_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

    function get_child_vaccinations_record($id_child)
    {
    	$this->db->trans_start();
    	$this->db->select('id, id_doctor, id_vaccination, date')
    			 ->from('child_vaccination')
    			 ->where('id_child', $id_child)
    			 ->where('state', 'taken');
    	$records = $this->db->get()->result_array();

    	$query = array();
    	foreach ($records as $key => $record) {
	    	$this->db->select('id, first_name, last_name, phone, location, location_name, rate, pic, gender')
	    			 ->from('users')
	    			 ->where('id', $record['id_doctor']);
	    	$doctor = $this->db->get()->result_array();

	    	$this->db->select('id, name, description, age_from, age_to')
	    			 ->from('vaccination')
	    			 ->where('id', $record['id_vaccination']);
	    	$vec = $this->db->get()->result_array();

	    	unset($record['id_doctor']);
	    	unset($record['id_vaccination']);
	    	$record['doctor'] = $doctor[0];
	    	$record['vaccination'] = $vec[0];
	    	array_push($query, $record);
    	}

    	$this->db->trans_complete();


		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
		    return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "");
			return json_encode(array($sec_array, $query));
		}
    }

    function register_for_vaccination($registration)
    {
    	$this->db->trans_start();
    	$this->db->insert('register_for_vaccination', $registration);
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

    function delete_register_for_vaccination($id)
    {
    	$this->db->trans_start();
    	$this->db->where('id', $id);
    	$this->db->delete('register_for_vaccination');
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

    function get_adv($id)
    {
    	$this->db->trans_start();
    	$this->db->select('id, id_doctor, id_vaccination, age_from, age_to, text, status, price, activation_date, close_date')
    			 ->from('vaccination_adv')
    			 ->where('id', $id);
    	$adv = $this->db->get()->result_array()[0];

    	$this->db->select('id, first_name, last_name, phone, location, location_name, rate, pic, gender')
    			 ->from('users')
    			 ->where('id', $adv['id_doctor']);
    	$adv['doctor'] = $this->db->get()->result_array()[0];
    	unset($adv['id_doctor']);

    	$this->db->select('*')
    			 ->from('vaccination')
    			 ->where('id', $adv['id_vaccination']);
    	$adv['vaccination'] = $this->db->get()->result_array()[0];
    	unset($adv['id_vaccination']);
    	$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
		    return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "");
			return json_encode(array($sec_array, $adv));
		}
    }

    function is_registered_for_vaccination($id_child, $id_adv)
    {
    	$this->db->trans_start();
    	$this->db->select('id AS registration_id')
    			 ->from('register_for_vaccination')
    			 ->where('id_adv', $id_adv)
    			 ->where('id_child', $id_child);
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

			if($count > 0) $query['is_registered'] = 'true';
			else $query['is_registered'] = 'false';

			return json_encode(array($sec_array, $query));
		}
    }

    function get_vaccination_confirmation_request($id)
    {
    	$this->db->trans_start();
    	$this->db->select('id, id_doctor, id_vaccination, id_child, date')
    			 ->from('child_vaccination')
    			 ->where('id', $id);
    	$request = $this->db->get()->result_array()[0];

    	$this->db->select('id, first_name, last_name, phone, location, location_name, rate, pic, gender')
    			 ->from('users')
    			 ->where('id',  $request['id_doctor']);
    	$request['doctor'] = $this->db->get()->result_array()[0];
    	unset($request['id_doctor']);

    	$this->db->select('*')
    			 ->from('vaccination')
    			 ->where('id',  $request['id_vaccination']);
    	$request['vaccination'] = $this->db->get()->result_array()[0];
    	unset($request['id_vaccination']);
    	$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
		    return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "");
			return json_encode(array($sec_array, $request));
		}
    }	

    function Confirm_vaccination($id)
    {
    	$this->db->trans_start();
    	$this->db->where('id', $id)
    			 ->update('child_vaccination', array('state' => 'taken'));
    	$this->db->trans_complete();

    	if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can confirm, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "This vaccination was confrmed successfuly");
			return json_encode(array($sec_array));
		}
    }

    function refuse_vaccination($id)
    {
    	$this->db->trans_start();
    	$this->db->where('id', $id)
    			 ->delete('child_vaccination');
    	$this->db->trans_complete();

    	if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not refuse vaccination, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "This vaccination was refused successfuly");
			return json_encode(array($sec_array));
		}
    }
}
?>