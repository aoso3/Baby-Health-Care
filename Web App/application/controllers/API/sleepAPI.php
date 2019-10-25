<?php defined('BASEPATH') OR exit('No direct script access allowed');

class SleepAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Sleep_model');
	}

	function get_sleep_chart_data()
	{
		$json_data = $this->input->post('json_data');
		$child_info = json_decode($json_data, true);

		echo $this->Sleep_model->get_sleep_chart_data($child_info['id_child']);
	}

	function get_sleep_samples()
	{
		$json_data = $this->input->post('json_data');
		$child_info = json_decode($json_data, true);

		echo $this->Sleep_model->get_sleep_samples($child_info['id_child']);
	}

	function add_sleep_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);

		echo $this->Sleep_model->add_sleep_sample($sample);
	}

	function update_sleep_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample = json_decode($json_data, true);
		$sample_id = $sample['id'];
		unset($sample['id']);

		echo $this->Sleep_model->update_sleep_sample($sample_id, $sample);
	}

	function delete_sleep_sample()
	{
		$json_data = $this->input->post('json_data');
		$sample_id = json_decode($json_data, true);

		echo $this->Sleep_model->delete_sleep_sample($sample_id['id']);
	}
}

?>