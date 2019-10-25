<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Sections_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }


	function get_main_sections()
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('main_section');
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

	function get_main_section_details($main_section_id)
	{
		$this->db->trans_start();
		$this->db->select('*')
				 ->from('main_section_details')
				 ->where('id_main_section', $main_section_id);
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
}
?>