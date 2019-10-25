<?php defined('BASEPATH') OR exit('No direct script access allowed');

class BabyAPI extends CI_Controller {

	var $upload_error;

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->model('Baby_model');
	}

	function get_babies_by_user()
	{
		$json_data = $this->input->post('json_data');
		$user_info = json_decode($json_data, true);

		echo $this->Baby_model->get_babies_by_user($user_info['id']);
	}

	function add_baby()
	{
		$json_data = $this->input->post('json_data');
		$baby_info = json_decode($json_data, true);

		//upload new image
		$file_name = "";
		if(isset($baby_info['img_key'])){
			if(!$this->upload($baby_info['img_key'], $file_name))
			{
				$sec_array = array("success"=>"0", "message" => $this->upload_error);
				echo json_encode(array($sec_array));
				return;
			}

		unset($baby_info['img_key']);
		$baby_info['pic'] = $file_name;
	}
		echo $this->Baby_model->add_baby($baby_info);
	}

	function delete_baby()
	{
		$json_data = $this->input->post('json_data');
		$baby_info = json_decode($json_data, true);

		echo $this->Baby_model->delete_baby($baby_info['id']);
	}

	function update_baby()
	{
		$json_data = $this->input->post('json_data');
		$baby_info = json_decode($json_data, true);
		$baby_id = $baby_info['id'];
		unset($baby_info['id']);

		//upload new image
		$file_name = "";
		if(isset($baby_info['img_key']))
			if(!$this->upload($baby_info['img_key'], $file_name))
			{
				$sec_array = array("success"=>"0", "message" => $this->upload_error);
				echo json_encode(array($sec_array));
				return;
			}

		unset($baby_info['img_key']);
		$baby_info['pic'] = $file_name;

		echo $this->Baby_model->update_baby($baby_id, $baby_info);
	}

	private function upload($image_key, &$file_name)
	{
    	$config['upload_path'] = './assets/images/baby_profile/';
		$config['allowed_types'] = 'jpg|png';

		//set file name
		$this->load->helper('string');
		$file_name =  random_string('alnum', 16);
		$config['file_name'] = $file_name;

		$this->load->library('upload', $config);

		if ( ! $this->upload->do_upload($image_key))
		{
			$upload_error = $this->upload->display_errors();
			return false;
		}
		else
		{
			$data = $this->upload->data();
			$file_name = $file_name.$data['file_ext'];
			return true;
		}
	}
}

?>
