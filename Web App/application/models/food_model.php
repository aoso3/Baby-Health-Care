<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Food_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

	function get_food_types($id_child)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('food_type')
				 ->where('id_child', $id_child);
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

	function add_food_type($type_info)
	{
		$this->db->trans_start();
		$this->db->insert('food_type', $type_info);
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

	function update_food_type($id, $type_info)
	{
		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('food_type', $type_info); 
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

	function delete_food_type($id_type)
	{
		$this->db->trans_start();
		$this->db->where('id', $id_type);
		$this->db->delete('food_type'); 
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

	function get_food_type_samples($id_type)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('food_sample')
				 ->where('id_food', $id_type);
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

	function update_food_sample($id, $sample_info)
	{
		//convert to SQL Date
		$sample_info['date'] = $this->string_to_date($sample_info['date']);
		$sample_info['note_date'] = $this->string_to_date($sample_info['note_date']);

		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('food_sample', $sample_info); 
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

	function add_food_sample($sample_info)
	{
		//convert to SQL Date
		$sample_info['date'] = $this->string_to_date($sample_info['date']);
		$sample_info['note_date'] = $this->string_to_date($sample_info['note_date']);

		$this->db->trans_start();
		$this->db->insert('food_sample', $sample_info);
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

	function delete_food_sample($id_sample)
	{
		$this->db->trans_start();
		$this->db->where('id', $id_sample);
		$this->db->delete('food_sample'); 
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