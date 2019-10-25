<?php defined('BASEPATH') OR exit('No direct script access allowed');

class SkillsAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Skill_model');
	}

	function get_all_skills()
	{
		$json_data = $this->input->post('json_data');
		$child_info = json_decode($json_data, true);

		echo $this->Skill_model->get_all_skills($child_info['id_child']);
	}

	function add_skill()
	{
		$json_data = $this->input->post('json_data');
		$skill = json_decode($json_data, true);

		echo $this->Skill_model->add_skill($skill);
	}

	function update_skill()
	{
		$json_data = $this->input->post('json_data');
		$skill = json_decode($json_data, true);
		$skill_id = $skill['id'];
		unset($skill['id']);

		echo $this->Skill_model->update_skill($skill_id, $skill);
	}

	function delete_skill()
	{
		$json_data = $this->input->post('json_data');
		$skill_id = json_decode($json_data, true);

		echo $this->Skill_model->delete_skill($skill_id['id']);
	}

	function baby_true_skills()
	{
		$json_data = $this->input->post('json_data');
		$child_info = json_decode($json_data, true);
		
		echo $this->Skill_model->baby_true_skills($child_info['id_child']);
	}
}

?>