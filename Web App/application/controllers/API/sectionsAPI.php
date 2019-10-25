<?php defined('BASEPATH') OR exit('No direct script access allowed');

class SectionsAPI extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Sections_model');
	}

	function get_main_sections()
	{
		echo $this->Sections_model->get_main_sections();
	}

	function get_main_section_details()
	{
		$json_data = $this->input->post('json_data');
		$main_section_id = json_decode($json_data, true);

		echo $this->Sections_model->get_main_section_details($main_section_id['id']);
	}
}

?>