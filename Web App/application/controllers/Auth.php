<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Auth extends CI_Controller {

	function __construct()
	{
		parent::__construct();
		$this->form_validation->set_error_delimiters($this->config->item('error_start_delimiter', 'ion_auth'), $this->config->item('error_end_delimiter', 'ion_auth'));
		$this->lang->load('auth');
		$this->load->library("helper_lib");
		$this->load->library('users_lib');
		$this->load->library('doctor_dashboard_lib');
		if($this->helper_lib->get_current_lang() == "en"){
		 			$this->lang->load('auth_en', 'english');
			}
		else if($this->helper_lib->get_current_lang() == "ar"){
		 			$this->lang->load('auth_ar', 'arabic');
			}

	}

	// redirect if needed, otherwise display the user list
	function index()
	{
		if($this->helper_lib->get_current_lang() == "en"){
					$data['en']  =  1;
			}
		else if($this->helper_lib->get_current_lang() == "ar"){
					$data['ar']  =  1;
			}

		if (!$this->ion_auth->logged_in())
		{
			// redirect them to the login page

			$data['auth'] = 1;
			$data['main_content'] = 'auth';
			$this->load->view('include/main',$data);

		}
		else
		{
			if($this->ion_auth->is_admin()){
				redirect('/Admin_Dashboard','refresh');
				}
			else{
				redirect('/doctor_Dashboard','refresh');
			}
		// 	// set the flash data error message if there is one
		// 	$this->data['message'] = (validation_errors()) ? validation_errors() : $this->session->flashdata('message');
		//
		// 	//list the users
		// 	$this->data['users'] = $this->ion_auth->users()->result();
		// 	foreach ($this->data['users'] as $k => $user)
		// 	{
		// 		$this->data['users'][$k]->groups = $this->ion_auth->get_users_groups($user->id)->result();
		// 	}
		//
		// 	$this->_render_page('auth/index', $this->data);
			}
	}

	// log the user in
	function login()
	{
		$data['auth'] = 1;


		if ($this->ion_auth->logged_in())
		{
			if($this->ion_auth->is_admin()){
				redirect('/Admin_Dashboard','refresh');
				}
			else{
				redirect('/doctor_Dashboard','refresh');
			}
		}
		else
		{
			// check to see if the user is logging in
			// check for "remember me"
			$email  =  $this->input->post('email');
			$password = $this->input->post('password');
			$remember = (bool) $this->input->post('remember');

			if ($this->ion_auth->login($email, $password, $remember))
			{
				//if the login is successful
				//redirect them back to the home page
				$this->session->set_flashdata('message', $this->ion_auth->messages());
				if($this->ion_auth->is_admin()){
			   	redirect('/Admin_Dashboard','refresh');
					}
				else{
					redirect('/doctor_Dashboard','refresh');
				}
			}
			else
			{
				$data['error'] = "the email and password don't match";
				$data['main_content'] = 'auth';
				$this->load->view('include/main',$data);

				// if the login was un-successful
				// redirect them back to the login page
				// $this->session->set_flashdata('message', $this->ion_auth->errors());
				// redirect('','refresh');

				// $password = $this->input->post('password');
				//
				// $arrayToJs = array();
				// $arrayToJs[0] = array();
				// $arrayToJs[1] = array();
				//
				//
				// 	if($password =="karnius"){		// validate??
				// 			$arrayToJs[0][0] = 'password';
				// 			$arrayToJs[0][1] = true;			// RETURN TRUE
				// 			$arrayToJs[0][2] = "This user is available";
				// 					// RETURN ARRAY WITH success
				// 		}else{
				// 			$arrayToJs[0][0] = 'password';
				// 			$arrayToJs[0][1] = false;
				// 			$arrayToJs[0][2] = "This user is already taken";
				// 		}
				//
				// 	echo json_encode($arrayToJs);


				}
			}
	}


	// log the user out
	function logout()
	{

		$this->data['title'] = "Logout";

		// log the user out
		$logout = $this->ion_auth->logout();

		// redirect them to the login page
		$this->session->set_flashdata('message', $this->ion_auth->messages());
		redirect('auth', 'refresh');
	}


	// create a new user
	function create_user()
    {


					// $associational_number = $this->input->post('associational_number');
					// $is_doctor = $this->ion_auth->is_doctor($associational_number);

				// if($is_doctor){


								$email    = strtolower($this->input->post('email'));
								$identity = strtolower($this->input->post('email'));
								$password = $this->input->post('password');

		            $additional_data = array(
										'created_on' =>  now(),
										'pic'        =>'user.png',
										'location'   =>'33.5138073,36.27652790000002',
										'location_name' =>'Damascus, Damascus Governorate, Syria',
										'id_role' =>$this->db->get_where('user_role',array('name'=>'doctor'))->result()[0]->id
		            );

			        if ($this->ion_auth->register($identity, $password, $email,$additional_data))
			        {
							  	$remember = false;
									$this->ion_auth->login($email, $password, $remember);
			            $this->session->set_flashdata('message', $this->ion_auth->messages());
									redirect('/auth/edit_user','refresh');

			        }
			        else
			        {

								$data['error'] = "This email has registerd, try to log in.";
								$data['auth'] = 1;
								$data['main_content'] = 'auth';
								$this->load->view('include/main',$data);
			        }
					// }
					// else{
					// 	$data['error'] = "This associational number is not valid.";
					// 	$data['auth'] = 1;
					// 	$data['main_content'] = 'auth';
					// 	$this->load->view('include/main',$data);
					// }

    }


		// forgot password
		function forgot_password()
		{
			$this->load->helper('email');
			$email =  $this->input->post('identity');
			if(valid_email($email)){


				$identity_column = $this->config->item('identity','ion_auth');
				$identity = $this->ion_auth->where($identity_column, $this->input->post('identity'))->users()->row();

				if(empty($identity)) {
		            		if($this->config->item('identity', 'ion_auth') != 'email')
			            	{
			            		$this->ion_auth->set_error('forgot_password_identity_not_found');
			            	}
			            	else
			            	{
			            	   $this->ion_auth->set_error('forgot_password_email_not_found');
			            	}

			                $this->session->set_flashdata('message', $this->ion_auth->errors());
	                		redirect("auth/forgot_password", 'refresh');
	            		}

				// run the forgotten password method to email an activation code to the user
				$forgotten = $this->ion_auth->forgotten_password($identity->{$this->config->item('identity', 'ion_auth')});
				if ($forgotten)
				{
					// if there were no errors
					$this->session->set_flashdata('message', $this->ion_auth->messages());
					 //redirect("auth/login", 'refresh'); //we should display a confirmation page here instead of the login page
				}
				else
				{
					$this->session->set_flashdata('message', $this->ion_auth->errors());
					//redirect("auth/forgot_password", 'refresh');
				}
			}

		}

		// reset password - final step for forgotten password
		public function reset_password($code = NULL)
		{
			if (!$code)
			{
				show_404();
			}

			$user = $this->ion_auth->forgotten_password_check($code);

			if ($user)
			{
				// if the code is valid then display the password reset form

				$this->form_validation->set_rules('new', $this->lang->line('reset_password_validation_new_password_label'), 'required|min_length[' . $this->config->item('min_password_length', 'ion_auth') . ']|max_length[' . $this->config->item('max_password_length', 'ion_auth') . ']|matches[new_confirm]');
				$this->form_validation->set_rules('new_confirm', $this->lang->line('reset_password_validation_new_password_confirm_label'), 'required');

				if ($this->form_validation->run() == false)
				{
					// display the form

					// set the flash data error message if there is one
					$this->data['message'] = (validation_errors()) ? validation_errors() : $this->session->flashdata('message');

					$this->data['min_password_length'] = $this->config->item('min_password_length', 'ion_auth');
					$this->data['new_password'] = array(
						'name' => 'new',
						'id'   => 'new',
						'type' => 'password',
						'pattern' => '^.{'.$this->data['min_password_length'].'}.*$',
					);
					$this->data['new_password_confirm'] = array(
						'name'    => 'new_confirm',
						'id'      => 'new_confirm',
						'type'    => 'password',
						'pattern' => '^.{'.$this->data['min_password_length'].'}.*$',
					);
					$this->data['user_id'] = array(
						'name'  => 'user_id',
						'id'    => 'user_id',
						'type'  => 'hidden',
						'value' => $user->id,
					);
					$this->data['csrf'] = $this->_get_csrf_nonce();
					$this->data['code'] = $code;

					// render
					$this->_render_page('auth/reset_password', $this->data);
				}
				else
				{
					// do we have a valid request?
					if ($this->_valid_csrf_nonce() === FALSE || $user->id != $this->input->post('user_id'))
					{

						// something fishy might be up
						$this->ion_auth->clear_forgotten_password_code($code);

						show_error($this->lang->line('error_csrf'));

					}
					else
					{
						// finally change the password
						$identity = $user->{$this->config->item('identity', 'ion_auth')};

						$change = $this->ion_auth->reset_password($identity, $this->input->post('new'));

						if ($change)
						{
							// if the password was successfully changed
							$this->session->set_flashdata('message', $this->ion_auth->messages());
							redirect("auth/login", 'refresh');
						}
						else
						{
							$this->session->set_flashdata('message', $this->ion_auth->errors());
							redirect('auth/reset_password/' . $code, 'refresh');
						}
					}
				}
			}
			else
			{
				// if the code is invalid then send them back to the forgot password page
				$this->session->set_flashdata('message', $this->ion_auth->errors());
				redirect("auth/forgot_password", 'refresh');
			}
		}



		function edit_user()
		{
			$current_user_id = $this->ion_auth->get_user_id();
			$u_i = $this->users_lib->user_info($current_user_id);
			$noti = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
			$noti_count = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);

			$data['user_info'] = $u_i;
			$data2['user_info'] = $u_i;
			$data['current_user_id'] = $current_user_id;
			$data2['current_user_id'] = $current_user_id;
			$data['notification'] = $noti;
			$data2['notification'] = $noti;
			$data['notification_count'] = $noti_count;
			$data2['notification_count'] = $noti_count;


			if($this->ion_auth->is_admin()){
					$data['admin_dashboard'] = 1;
					$data2['admin_dashboard'] = 1;
			}
			else{
					$data1['doctor_dashboard'] = 1;
					$data2['doctor_dashboard'] = 1;
			}


			$id = $this->ion_auth->get_user_id();
			$user = $this->ion_auth->user($id)->row();

			if (!$this->ion_auth->logged_in() || (!$this->ion_auth->is_admin() && !($this->ion_auth->user()->row()->id == $id)))
			{
				$data['main_content'] = 'auth';
				$this->load->view('include/main',$data);
			}


		if ($_SERVER['REQUEST_METHOD'] == 'POST'){

					$data = array(
						'username'   => $this->input->post('username'),
						'first_name' => $this->input->post('first_name'),
						'last_name'  => $this->input->post('last_name'),
						'email'      => $this->input->post('email'),
						'birth_date' => $this->input->post('birth_date'),
						'phone'      => $this->input->post('phone'),
						'gender'      => $this->input->post('gender'),
					);
					if($this->input->post('pic')){
						$data['pic'] = $this->input->post('pic');
					}
					if($this->input->post('location')){
						$data['location'] = $this->input->post('location');
					}
					if($this->input->post('location_name')){
						$data['location_name'] = $this->input->post('location_name');
					}
					if($this->input->post('location_name')){
						$data['location_name'] = $this->input->post('location_name');
					}
					if ($this->input->post('password')){
						$data['password'] = $this->input->post('password');
					}

							$this->ion_auth->update($user->id, $data);
							redirect('/auth/edit_user','refresh');
					}

							$data2['username'] =  $user->{'username'};
							$data2['first_name'] =  $user->{'first_name'};
							$data2['last_name'] =  $user->{'last_name'};
							$data2['email'] = $user->{'email'};
							$data2['birth_date'] =  $user->{'birth_date'};
							$data2['phone'] =  $user->{'phone'};
							$data2['gender'] =  $user->{'gender'};
							$data2['location'] =  $user->{'location'};
							$data2['pic'] =  $user->{'pic'};
							$data2['location_name'] =  $user->{'location_name'};
							$data2['main_content'] = 'users/profile';
							$data2['user_profile'] = 1;
							$this->load->view('include/main',$data2);

		}




			function _get_csrf_nonce()
			{
				$this->load->helper('string');
				$key   = random_string('alnum', 8);
				$value = random_string('alnum', 20);
				$this->session->set_flashdata('csrfkey', $key);
				$this->session->set_flashdata('csrfvalue', $value);

				return array($key => $value);
			}

			function _valid_csrf_nonce()
			{
				if ($this->input->post($this->session->flashdata('csrfkey')) !== FALSE &&
					$this->input->post($this->session->flashdata('csrfkey')) == $this->session->flashdata('csrfvalue'))
				{
					return TRUE;
				}
				else
				{
					return FALSE;
				}
			}

			function _render_page($view, $data=null, $returnhtml=false)//I think this makes more sense
			{

				$this->viewdata = (empty($data)) ? $this->data: $data;

				$view_html = $this->load->view($view, $this->viewdata, $returnhtml);

				if ($returnhtml) return $view_html;//This will return html on 3rd argument being true
			}


}
