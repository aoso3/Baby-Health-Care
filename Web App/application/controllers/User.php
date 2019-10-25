<?php defined('BASEPATH') OR exit('No direct script access allowed');

include(APPPATH.'third_party/PictureCut/core/PictureCut.php');
class User extends CI_Controller {

	public function index()
	{
  //  echo "soso";


		$data['main_content'] = 'users/test2';
		$this->load->view('include/main',$data);
	}
	public function profile()
	{
		$user_name = $this->input->post('');
		$first_name = $this->input->post('');
		$last_name = $this->input->post('');
		$email = $this->input->post('');
		$birth_date = $this->input->post('');
		$phone = $this->input->post('');
		$password = $this->input->post('');
		$location = $this->input->post('');
		$pic = $this->input->post('');



		$data['user_profile'] = 1;
		$data['main_content'] = 'users/profile';
		$this->load->view('include/main',$data);
	}



	public function test()
	{
    echo "soso";


		// $data['main_content'] = 'users/test';
		// $this->load->view('include/main',$data);
	}


}
