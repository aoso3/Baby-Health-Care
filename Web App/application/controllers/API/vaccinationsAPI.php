<?php defined('BASEPATH') OR exit('No direct script access allowed');

class VaccinationsAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Vaccinations_model');
	}

	public function get_child_vaccinations_record()
	{
		$json_data = $this->input->post('json_data');
		$child_id = json_decode($json_data, true);

		echo $this->Vaccinations_model->get_child_vaccinations_record($child_id['id_child']);
	}

	public function register_for_vaccination()
	{
		$json_data = $this->input->post('json_data');
		$registration = json_decode($json_data, true);

		echo $this->Vaccinations_model->register_for_vaccination($registration);
	}

	public function delete_register_for_vaccination()
	{
		$json_data = $this->input->post('json_data');
		$registration_id = json_decode($json_data, true);

		echo $this->Vaccinations_model->delete_register_for_vaccination($registration_id['id']);
	}

	public function get_adv()
	{
		$json_data = $this->input->post('json_data');
		$adv_id = json_decode($json_data, true);
		
		echo $this->Vaccinations_model->get_adv($adv_id['id']);
	}

	public function is_registered_for_vaccination()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Vaccinations_model->is_registered_for_vaccination($info['id_child'], $info['id_adv']);
	}

	function get_vaccination_confirmation_request()
	{
		$json_data = $this->input->post('json_data');
		$request_id = json_decode($json_data, true);
		
		echo $this->Vaccinations_model->get_vaccination_confirmation_request($request_id['id']);
	}

	public function Confirm_vaccination()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Vaccinations_model->Confirm_vaccination($info['id']);
	}

	public function refuse_vaccination()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Vaccinations_model->refuse_vaccination($info['id']);
	}
}

?>