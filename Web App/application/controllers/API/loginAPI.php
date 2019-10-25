<?php defined('BASEPATH') OR exit('No direct script access allowed');

class LoginAPI extends CI_Controller {

	var $upload_error;

	public function __construct()
	{
		parent::__construct();

		$this->load->database();
		$this->load->library('ion_auth');
		$this->load->model('Login_model');
	}

	function login()
	{
		$json = $this->input->post('json_data');
		$user_info = json_decode($json, true);

		echo $this->Login_model->login($user_info['email'], $user_info['password']);
	}

	function register()
	{
		$json = $this->input->post('json_data');
		$new_user = json_decode($json, true);

		$email = $new_user['email'];
		unset($new_user['email']);
		$password = $new_user['password'];
		unset($new_user['password']);

		echo $this->Login_model->register($email, $password, $new_user);
	}

	function update_user()
	{
		$json = $this->input->post('json_data');
		$user_info = json_decode($json, true);

		//upload new image
		$file_name = "";
		if(isset($user_info['img_key']))
		{
			if(!$this->upload($user_info['img_key'], $file_name))
			{
				$sec_array = array("success"=>"0", "message" => $this->upload_error);
				echo json_encode(array($sec_array));
				return;
			}

			unset($user_info['img_key']);
			$user_info['pic'] = $file_name;
		}

		$user_id = $user_info['id'];
		unset($user_info['id']);
		echo $this->Login_model->update($user_id, $user_info);
	}

	private function upload($image_key, &$file_name)
	{
    	$config['upload_path'] = './assets/images/users/';
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

	function change_password()
	{
		$json = $this->input->post('json_data');
		$data = json_decode($json, true);

		echo $this->Login_model->change_password($data['email'], $data['old'], $data['new']);
	}

	function get_base_vaccinations()
	{
		$this->db->select('*');
		$this->db->from('vaccination');
		$vaccinations = $this->db->get()->result_array();

		echo json_encode($vaccinations);
	}

	function register_device()
	{
		$json_data = $this->input->post('json_data');
		$device_info = json_decode($json_data, true);

		$this->db->insert('gcm_registeredid',
			array('id_user' => $device_info['id_user'],
			'token' => $device_info['token']));
		$new_id = $this->db->insert_id();

		echo json_encode(array('id' => $new_id));
	}

	function unregister_device()
	{
		$json_data = $this->input->post('json_data');
		$device_info = json_decode($json_data, true);

		$this->db->where('id', $device_info['id'])
				 ->delete('gcm_registeredid');
	}

public function send_gcm()
{
    // simple loading
    // note: you have to specify API key in config before
        $this->load->library('gcm');

    // simple adding message. You can also add message in the data,
    // but if you specified it with setMesage() already
    // then setMessage's messages will have bigger priority
        //$this->gcm->setMessage('Test message '.date('d.m.Y H:s:i'));

    // add recepient or few
        //$this->gcm->addRecepient('fyeSFXF3hIk:APA91bERRpdduT8q47h-R3ctAJcsa-G6cdeNTH_p193TrmOCx389Lw9nuN0PDPdIAt3ZieoaNOP38NecQ8TACbltXIUYWaV96LS29hnZVDCCB5oiuTK4UAEID7ST20vgwkeuXWnPPxht');
       // $this->gcm->addRecepient('cozgWcM8eVA:APA91bF-baUGntOobdhcoUvjFhu_85UsncXJCL1V91qW3cvziDXK9PapAFtvzv3l4KDNUzFwscMb5uy5tW6MC59hRK3nXs6wegWGpIjUUX8vZlGp-3p893iPEaCsG6TsWW8-fKc1N_F-');
        $this->gcm->addRecepient('cozgWcM8eVA:APA91bF-baUGntOobdhcoUvjFhu_85UsncXJCL1V91qW3cvziDXK9PapAFtvzv3l4KDNUzFwscMb5uy5tW6MC59hRK3nXs6wegWGpIjUUX8vZlGp-3p893iPEaCsG6TsWW8-fKc1N_F-');

        // $message = array(
        // 	'id' => '1',
        // 	'id_child' => '11',
        // 	'title' => 'vaccination advertisement',
        // 	'text' => ' Doctor Ahmad has added a new vaccination vaccination_name.',
        // 	'img' => '',
        // 	'additional_data' => '1',
        // 	'is_seen' => 'false',
        // 	'creation_date' => '2016-22-5',
        // 	'creator_user_id' => '2',
        // 	'type' => 'new vaccination adv'
       	// );

       	$message = array(
        	'id' => '193',
        	'id_child' => '5',
        	'title' => 'Vaccination advertisement',
        	'text' => 'Ahmad Ahmad has added a new vaccination advertisement.',
        	'img' => 'notification2.png',
        	'additional_data' => '9',
        	'is_seen' => '0',
        	'creation_date' => '2016-29-5',
        	'creator_user_id' => '43',
        	'type' => 'new vaccination adv'
       	);

    // set additional data
        $this->gcm->setData(array(
            'message' => json_encode($message)
        ));

    // also you can add time to live
        //$this->gcm->setTtl(500);
    // and unset in further
        //$this->gcm->setTtl(false);

    // set group for messages if needed
        //$this->gcm->setGroup('Test');
    // or set to default
        //$this->gcm->setGroup(false);

    // then send
        if ($this->gcm->send())
            echo 'Success for all messages';
        else
            echo 'Some messages have errors';

    // and see responses for more info
        print_r($this->gcm->status);
        print_r($this->gcm->messagesStatuses);

    die(' Worked.');
}

    function test()
    {
    	// $this->load->model('vaccinations_model');
    	// $res = $this->vaccinations_model->get_vaccination_confirmation_request(11);

    }
}

?>
