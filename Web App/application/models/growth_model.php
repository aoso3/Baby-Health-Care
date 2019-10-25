<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Growth_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

	function get_chart_data($sex, $id_quantity)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('growth_chart_data')
				 ->where('sex', $sex)
				 ->where('id_quantity', $id_quantity);
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

	function get_quantities()
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('quantities');
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

	function get_baby_quantities($id_child, $id_quantities)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('child_quantities')
				 ->where('id_child', $id_child)
				 ->where('id_quantities', $id_quantities);
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

	function add_quantity_sample($sample)
	{
		//convert to SQL Date
		//$sample['date'] = $this->string_to_date($sample['date']);

		$this->db->trans_start();
		$this->db->insert('child_quantities', $sample);
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

	function update_quantity_sample($id, $sample)
	{
		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('child_quantities', $sample); 
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

	function delete_quantity_sample($id_sample)
	{
		$this->db->trans_start();
		$this->db->where('id', $id_sample)
				 ->delete('child_quantities'); 
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
		$time = strtotime($string);
    	$date = date("d-m-Y", $time);

    	return $date;
	}
}
?>