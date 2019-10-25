<?php defined('BASEPATH') OR exit('No direct script access allowed');

class GrowthAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Growth_model');
	}

	function get_chart_data()
	{
		$json_data = $this->input->post('json_data');
		$chart_info = json_decode($json_data, true);

		echo $this->Growth_model->get_chart_data($chart_info['sex'], $chart_info['id_quantity']);
	}

	function get_quantities()
	{
		echo $this->Growth_model->get_quantities();
	}

	function get_baby_quantities()
	{
		$json_data = $this->input->post('json_data');
		$quantity_info = json_decode($json_data, true);

		echo $this->Growth_model->get_baby_quantities($quantity_info['id_child'], $quantity_info['id_quantities']);
	}

	function add_quantity_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);

		echo $this->Growth_model->add_quantity_sample($sample);
	}

	function update_quantity_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);
		$sample_id = $sample['id'];
		unset($sample['id']);

		echo $this->Growth_model->update_quantity_sample($sample_id, $sample);
	}

	function delete_quantity_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample_id = json_decode($json_data, true);
		echo $this->Growth_model->delete_quantity_sample($sample_id['id']);
	}
}

?>