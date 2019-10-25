<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller {

	public function index()
	{
		$data['main_content'] = 'auth';
		$this->load->view('include/main',$data);
	}
	public function auth(){
		$data['main_content'] = 'auth';
		$this->load->view('include/main',$data);
	}

	public function test1(){
		$data['current_user_id'] = 40;
		$data['notification'] = 4;
		$data['main_content'] = 'send';
		$this->load->view('include/main',$data);
	}
	public function submit(){

	   	$arr['name'] = $this->input->post('name');
			$this->db->insert('message',$arr);
			$detail = $this->db->select('*')->from('message')->where('id',$this->db->insert_id())->get()->row();
			$arr['id'] = $detail->id;
			$arr['success'] = true;

	  	echo json_encode($arr);
	}
	public function test2(){
		$data['message'] = $this->db->select('*')->from('message')->order_by('id','desc')->get();
		$data['main_content'] = 'message';
		$this->load->view('include/main',$data);
	}





}
