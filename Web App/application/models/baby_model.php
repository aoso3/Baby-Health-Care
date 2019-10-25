<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Baby_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

    function get_babies_by_user($user_id)
	{
		$this->db->trans_start();
		$this->db->select('child.*')
				 ->from('child_parents')
				 ->join('child', 'child.id = child_parents.id_child')
				 ->where('child_parents.id_parent', $user_id);
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

	function add_baby($baby_info)
	{
		$parent_id = $baby_info['parent_id'];
		unset($baby_info['parent_id']);

		if($this->children_count($parent_id) < 10)
		{
			//convert to SQL Date
			$baby_info['birth_date'] = $this->string_to_date($baby_info['birth_date']);

			$this->db->trans_start();
			$this->db->insert('child', $baby_info);
			$new_baby = $this->db->insert_id();
			$this->db->insert('child_parents', array('id_parent'=>$parent_id, 'id_child' => $new_baby));
			$this->db->trans_complete();

			if ($this->db->trans_status() === FALSE)
			{
				$sec_array = array("success"=>"0", "message" => "Can not add data, please try again later");
			    return json_encode(array($sec_array));
			}
			else
			{
				$sec_array = array("success"=>"1", "message" => "Your data was added successfuly");
				$this->db->select('*')
					 ->from('child')
					 ->where('id', $new_baby);
				$query = $this->db->get()->result_array()[0];
				return json_encode(array($sec_array, $query));
			}
		}
		else
		{
			$sec_array = array("success"=>"0", "message" => "Your can not add more than 10 babies!");
			return json_encode($sec_array);
		}
	}

	function update_baby($id, $baby_info)
	{
		//convert to SQL Date
		$baby_info['birth_date'] = $this->string_to_date($baby_info['birth_date']);

		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('child', $baby_info); 
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not update data, please try again later");
			return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "Your data was updated successfuly");

			$this->db->select('*')
					 ->from('child')
					 ->where('id', $id);
			$query = $this->db->get()->result_array()[0];


			return json_encode(array($sec_array, $query));
		}
	}

	function delete_baby($id_child)
	{
		$this->db->trans_start();
		$this->db->where('id', $id_child)
				 ->delete('child'); 
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

	private function children_count($id_parent)
	{
		$this->db->select('*')
			 	 ->from('child_parents')
			 	 ->where('id_parent', $id_parent);
		return $this->db->count_all_results();
	}	
}
?>