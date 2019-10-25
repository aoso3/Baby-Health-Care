<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Sleep_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

	function get_sleep_chart_data($id_child)
	{
		$this->db->trans_start();
		$this->db->select('*');
		$this->db->from('sleep_chart_data');
		$standard_data = $this->db->get()->result_array();

		$baby_data = array();
		foreach ($standard_data as $key => $data) {
			$this->db->select_avg('amount');
			$this->db->from('sleep_sample');
			$this->db->where('id_child', $id_child);
			$this->db->where('age_months <=',$data['age_to']);
			$this->db->where('age_months >=',$data['age_from']);
			$query = $this->db->get();
			$query = $query->result_array();
			array_push($baby_data, $query[0]);
		}
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
		    return json_encode(array($sec_array));
		}
		else
		{
			$query = array('standard_data' => $standard_data, 'baby_data' => $baby_data);
			$sec_array = array("success"=>"1", "message" => "");
			return json_encode(array($sec_array, $query));
		}
	}

	function get_sleep_samples($id_child)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->where('id_child', $id_child)
				 ->from('sleep_sample');
		$query = $this->db->get()->result_array();
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

	function add_sleep_sample($sample)
	{
		//convert to SQL Date
		$sample['date'] = $this->string_to_date($sample['date']);

		$this->db->trans_start();
		$this->db->insert('sleep_sample', $sample);
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not add data, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "Your data was added successfuly");
			return json_encode(array($sec_array, array('new_id' => $this->db->insert_id())));
		}
	}

	function update_sleep_sample($id, $sample)
	{
		//convert to SQL Date
		$sample['date'] = $this->string_to_date($sample['date']);

		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('sleep_sample', $sample); 
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not update data, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "Your data was updated successfuly");
			return json_encode(array($sec_array));
		}
	}

	function delete_sleep_sample($id)
	{
		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->delete('sleep_sample'); 
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not delete data, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "Your data was deleted successfuly");
			return json_encode(array($sec_array));
		}
	}

	private function string_to_date($string)
	{
    	return date('Y-m-d', strtotime($string));
	}
}
?>