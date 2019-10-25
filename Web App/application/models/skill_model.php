<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Skill_model extends CI_Model 
{	
	 function __construct()
    {
        parent::__construct();

        $this->db->trans_strict(FALSE);
    }

    function get_all_skills($id_child)
	{
		$this->db->trans_start();
		$this->db->select('id, age_months AS age, date, id_skills AS id_skill, rate')
				 ->from('child_skills')
				 ->where('id_child', $id_child);
		$true_child_skills = $this->db->get()->result_array();

		$false_skills = array();
		$false_child_skills = array();
		$true_skills_ids = array();
		if(count($true_child_skills)!=0)
		{
			for($i = 0; $i < count($true_child_skills); $i++) {
				$this->db->select('*')
						 ->from('skills')
						 ->where('id', $true_child_skills[$i]['id_skill']);
				$skill = $this->db->get()->result_array()[0];
				array_push($true_skills_ids, $skill['id']);
				$true_child_skills[$i]['skill'] = $skill;
				unset($true_child_skills[$i]['id_skill']);
			}

			$this->db->select('*')
					 ->from('skills')
					 ->where_not_in('id', $true_skills_ids);
			$false_skills = $this->db->get()->result_array();
		}
		else
		{
			$this->db->select('*')
					 ->from('skills');
			$false_skills = $this->db->get()->result_array();
		}

		foreach ($false_skills as $key => $false_skill) {
			$child_skill = array();
			$child_skill['id'] = 'none';
			$child_skill['age'] = 'none';
			$child_skill['date'] = 'none';
			$child_skill['rate'] = 'none';
			$child_skill['skill'] = $false_skill;

			array_push($false_child_skills, $child_skill);
		}		

		$skills = array_merge($true_child_skills, $false_child_skills);
		$this->db->trans_complete();

		if ($this->db->trans_status() === FALSE)
		{
			$sec_array = array("success"=>"0", "message" => "Can not load data, please try again later");
		    return json_encode(array($sec_array));
		}
		else
		{
			$sec_array = array("success"=>"1", "message" => "");
			$json =  json_encode(array($sec_array, $skills));
			$json = str_replace("\u00a0", "", $json);
			return $json;
		}
	}

	private function flatten(array $array) {
	    $return = array();
	    array_walk_recursive($array, function($a) use (&$return) { $return[] = $a; });
	    return $return;
	}	

	function add_skill($skill)
	{
		//convert to SQL Date
		$skill['date'] = $this->string_to_date($skill['date']);

		$this->db->trans_start();
		$this->db->insert('child_skills', $skill);
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

	function update_skill($id, $skill)
	{
		//convert to SQL Date
		$skill['date'] = $this->string_to_date($skill['date']);

		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->update('child_skills', $skill); 
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

	function delete_skill($id)
	{
		$this->db->trans_start();
		$this->db->where('id', $id)
				 ->delete('child_skills'); 
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

	function rate_skill($skill_info, $child_age)
	{
		if($skill_info['age_from'] <= $child_age && $child_age < $skill_info['age_to'])
		{
			if($skill_info['kind'] == 'Achieved')
				return 'Good';
			else if($skill_info['kind'] == 'Emerging')
					return 'Very Good';
				else
					return 'Excellent';
		}

		if($child_age < $skill_info['age_from'])
		{
			if($skill_info['kind'] == 'Achieved')
				return 'Excellent';
			else
				return 'Great';
		}

		if($child_age > $skill_info['age_to'])
		{
			if($skill_info['kind'] == 'Achieved')
				return 'Ok';
			else if($skill_info['kind'] == 'Emerging')
			{
				$period = floor(($skill_info['age_from'] + $skill_info['age_to'])/4);
				if($child_age > $period)
					return 'Ok';
				else return 'Good';
			} 
			else
			{
				$period = floor(($skill_info['age_from'] + $skill_info['age_to'])/2);
				if($child_age > $period)
					return 'Ok';
				else return 'Good';
			}
		}
	}

	function rate_false_skill($skill_info, $child_age)
	{
		if($skill_info['age_from'] <= $child_age && $child_age < $skill_info['age_to'] || $child_age < $skill_info['age_from'])
			return 'Not bad';

		if($child_age > $skill_info['age_to'])
		{
			if($skill_info['kind'] == 'Achieved')
				return 'Very bad';
			else if($skill_info['kind'] == 'Emerging')
			{
				$period = floor(($skill_info['age_from'] + $skill_info['age_to'])/4);
				if($child_age > $period)
					return 'Very bad';
				else return 'Bad';
			} 
			else
			{
				$period = floor(($skill_info['age_from'] + $skill_info['age_to'])/2);
				if($child_age > $period)
					return 'Very bad';
				else return 'Bad';
			}
		}
	}	
}
?>