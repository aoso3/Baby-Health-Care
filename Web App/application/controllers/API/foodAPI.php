<?php defined('BASEPATH') OR exit('No direct script access allowed');

class FoodAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Food_model');
	}

	function get_food_types()
	{
		$json_data = $this->input->post('json_data');
		$baby_id = json_decode($json_data, true);

		echo $this->Food_model->get_food_types($baby_id['id']);
	}

	function get_food_type_samples()
	{
		$json_data = $this->input->post('json_data');
		$food_id = json_decode($json_data, true);

		echo $this->Food_model->get_food_type_samples($food_id['id_food']);
	}

	function add_food_type()
	{
		$json_data = $this->input->post('json_data');
		$type = json_decode($json_data, true);

		echo $this->Food_model->add_food_type($type);
	}

	function add_food_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);

		echo $this->Food_model->add_food_sample($sample);
	}

	function update_food_type()
	{
		$json_data = $this->input->post('json_data');
		$type = json_decode($json_data, true);
		$type_id = $type['id'];
		unset($type['id']);

		echo $this->Food_model->update_food_type($type_id, $type);
	}

	function update_food_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);
		$sample_id = $sample['id'];
		unset($sample['id']);

		echo $this->Food_model->update_food_sample($sample_id, $sample);
	}

	function delete_food_type()
	{
		$json_data = $this->input->post('json_data');
		$type_id = json_decode($json_data, true);

		echo $this->Food_model->delete_food_type($type_id['id']);
	}

	function delete_food_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample_id = json_decode($json_data, true);

		echo $this->Food_model->delete_food_sample($sample_id['id']);
	}
}

?>