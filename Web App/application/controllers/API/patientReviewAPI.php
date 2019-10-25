<?php defined('BASEPATH') OR exit('No direct script access allowed');

class PatientReviewAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('PatientReview_model');
	}

	function send_review()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->PatientReview_model->send_review($data);
	}

	function has_review()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->PatientReview_model->has_review($data['id_user'], $data['id_doctor']);
	}

	function delete_review()
	{
		$json_data = $this->input->post('json_data');
		$data = json_decode($json_data, TRUE);

		echo $this->PatientReview_model->delete_review($data['id'], $data['id_doctor']);
	}

}

?>