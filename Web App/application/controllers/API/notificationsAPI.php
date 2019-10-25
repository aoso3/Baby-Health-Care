<?php defined('BASEPATH') OR exit('No direct script access allowed');

class NotificationsAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Notifications_model');
	}

	function see_notification()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Notifications_model->see_notification($info['id_notification'], $info['id_user']);
	}

	function get_all_adv_notifications()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Notifications_model->get_all_adv_notifications($info['id_user'], $info['id_child']);
	}

	function get_all_not_seen_confirmation_requests()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Notifications_model->get_all_not_seen_confirmation_requests($info['id_user'], $info['id_child']);
	}

	function add_vaccination_request_notification()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Notifications_model->add_vaccination_request_notification($info['id_user'], $info['id_child'], $info['id_adv']);
	}

	function add_register_at_doctor_notification()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		echo $this->Notifications_model->add_register_at_doctor_notification($info['id_user'], $info['id_child'], $info['id_req']);
	}

	function delete_notification()
	{
		$json_data = $this->input->post('json_data');
		$info = json_decode($json_data, true);

		$this->Notifications_model->delete_notification($info['id_child'], $info['id_doctor']);	
	}
}

?>