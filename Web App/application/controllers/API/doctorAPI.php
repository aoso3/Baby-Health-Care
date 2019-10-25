<?php defined('BASEPATH') OR exit('No direct script access allowed');

class DoctorAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Doctors_model');
	}

	function get_ten_doctors()
	{
		$json_data = $this->input->post('json_data');
		$last_doctor = json_decode($json_data, TRUE);

		echo $this->Doctors_model->get_ten_doctors($last_doctor['id'], $last_doctor['rate'], $last_doctor['count']);
	}

	function search_ten_doctors()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->search_ten_doctors($data['word'], $data['rate'], $data['id'],$data['count']);
	}

	function request_sent()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->request_sent($data['id_child'], $data['id_doctor']);
	}

	function is_linked()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->is_linked($data['id_child'], $data['id_doctor']);
	}

	function send_request()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);
		$data['approved'] = '0';

		echo $this->Doctors_model->send_request($data);
	}

	function delete_request()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->delete_request($data['id']);
	}

	function get_doctor_rating_information()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->get_doctor_rating_information($data['id']);
	}

	function get_linked_doctors()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->Doctors_model->get_linked_doctors($data['id_child']);
	}
}

?>