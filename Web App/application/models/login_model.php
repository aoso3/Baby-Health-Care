<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Login_model extends CI_Model 
{	
    public $tables = array();
    public $error_message = "";

	 function __construct()
    {
        parent::__construct();

        //initialize hash method options (Bcrypt)
        $this->hash_method = $this->config->item('hash_method', 'ion_auth');
        $this->default_rounds = $this->config->item('default_rounds', 'ion_auth');
        $this->random_rounds = $this->config->item('random_rounds', 'ion_auth');
        $this->min_rounds = $this->config->item('min_rounds', 'ion_auth');
        $this->max_rounds = $this->config->item('max_rounds', 'ion_auth');

        //initialize db tables data
        $this->tables  = $this->config->item('tables', 'ion_auth');
        $this->store_salt      = $this->config->item('store_salt', 'ion_auth');

        //load the bcrypt class if needed
        if ($this->hash_method == 'bcrypt') {
            if ($this->random_rounds)
            {
                $rand = rand($this->min_rounds,$this->max_rounds);
                $params = array('rounds' => $rand);
            }
            else
            {
                $params = array('rounds' => $this->default_rounds);
            }

            $params['salt_prefix'] = $this->config->item('salt_prefix', 'ion_auth');
            $this->load->library('bcrypt',$params);
        }
    }

    public function login($email, $password)
    {
        if (empty($email) || empty($password))
        {
            $sec_array = array("success"=>"0", "message" => "Empty fields are not available");
            return json_encode(array($sec_array));
        }

        $this->db->select('users.id, user_role.name AS role, first_name, last_name, email, birth_date, gender, pic')
                 ->from('users')
                 ->join('user_role', 'user_role.id = users.id_role')
                 ->where(array('email'=>$email));
        $query = $this->db->get()->result_array();

        if (sizeof($query) === 1)
        {
            if($this->hash_password_db($query[0]['id'], $password))
            {
                $sec_array = array("success"=>"1", "message" => "");
                return json_encode(array($sec_array, $query[0]));
            }
            else
            {
                $sec_array = array("success"=>"0", "message" => "Incorrect password");
                return json_encode(array($sec_array));
            }
        }
        else
        {
            $sec_array = array("success"=>"0", "message" => "Incorrect email or password");
            return json_encode(array($sec_array));
        }
    }

    /**
     * register
     *
     * @return bool
     * @author Mathew
     **/
    public function register($email, $password, $additional_data = array())
    {
        $manual_activation = $this->config->item('manual_activation', 'ion_auth');

        if ($this->email_check($email))
        {
            $this->error_message = 'This email is already exists';
            $sec_array = array("success"=>"0", "message" => $this->error_message);
            return json_encode(array($sec_array));
        }

        // IP Address
        $ip_address = $this->_prepare_ip($this->input->ip_address());
        $salt       = $this->store_salt ? $this->salt() : FALSE;
        $password   = $this->hash_password($password, $salt);

        // Users table.
        $data = array(
            'username'   => 'username',
            'password'   => $password,
            'email'      => $email,
            'ip_address' => $ip_address,
            'created_on' => time(),
            'active'     => ($manual_activation === false ? 1 : 0)
        );

        if ($this->store_salt)
        {
            $data['salt'] = $salt;
        }

        //filter out any data passed that doesnt have a matching column in the users table
        //and merge the set user data and the additional data
        $user_data = array_merge($this->_filter_data($this->tables['users'], $additional_data), $data);

        $this->db->insert($this->tables['users'], $user_data);

        $id = $this->db->insert_id();

        if(isset($id))
        {
            $this->db->select('id, email, first_name, last_name, birth_date, gender, pic')
                     ->from('users')
                     ->where('id', $id);
            $user_data = $this->db->get()->result_array()[0];

            $sec_array = array("success"=>"1", "message" => "Your register was completed successfuly");
            return json_encode(array($sec_array, $user_data));
        }
        else
        {
            $sec_array = array("success"=>"0", "message" => $this->error_message);
            return json_encode(array($sec_array));
        }
    }

    /**
     * Checks email
     *
     * @return bool
     * @author Mathew
     **/
    public function email_check($email = '')
    {
        if (empty($email))
        {
            $this->error_message = "Email cannot be empty";
            return FALSE;
        }

        return $this->db->where('email', $email)
                ->order_by("id", "ASC")
                ->limit(1)
                ->count_all_results($this->tables['users']) > 0;
    }

    /**
     * This function takes a password and validates it
     * against an entry in the users table.
     *
     * @return void
     * @author Mathew
     **/
    public function hash_password_db($id, $password, $use_sha1_override=FALSE)
    {
        if (empty($id) || empty($password))
        {
            return FALSE;
        }

        $this->db->select('password, salt')
                 ->from('users')
                 ->where('id', $id);
        $query = $this->db->get();

        $hash_password_db = $query->row();

        if ($query->num_rows() !== 1)
        {
            return FALSE;
        }

        // bcrypt
        if ($use_sha1_override === FALSE && $this->hash_method == 'bcrypt')
        {
            if ($this->bcrypt->verify($password,$hash_password_db->password))
            {
                return TRUE;
            }

            return FALSE;
        }

        // sha1
        if ($this->store_salt)
        {
            $db_password = sha1($password . $hash_password_db->salt);
        }
        else
        {
            $salt = substr($hash_password_db->password, 0, $this->salt_length);

            $db_password =  $salt . substr(sha1($salt . $password), 0, -$this->salt_length);
        }

        if($db_password == $hash_password_db->password)
        {
            return TRUE;
        }
        else
        {
            return FALSE;
        }
    }

    /**
     * Hashes the password to be stored in the database.
     *
     * @return void
     * @author Mathew
     **/
    public function hash_password($password, $salt=false, $use_sha1_override=FALSE)
    {
        if (empty($password))
        {
            $this->error_message = "Password cannot be empty";
            return FALSE;
        }

        //bcrypt
        if ($use_sha1_override === FALSE && $this->hash_method == 'bcrypt')
        {
            return $this->bcrypt->hash($password);
        }


        if ($this->store_salt && $salt)
        {
            return  sha1($password . $salt);
        }
        else
        {
            $salt = $this->salt();
            return  $salt . substr(sha1($salt . $password), 0, -$this->salt_length);
        }
    }

    /**
     * change password
     *
     * @return bool
     * @author Mathew
     **/
    public function change_password($identity, $old, $new)
    {
        $query = $this->db->select('id, password, salt')
                          ->where('email', $identity)
                          ->limit(1)
                          ->order_by('id', 'desc')
                          ->get($this->tables['users']);

        if ($query->num_rows() !== 1)
        {
            $this->error_message = 'Error changing passowrd';
            $sec_array = array("success"=>"0", "message" => $this->error_message);
            return json_encode(array($sec_array));
        }

        $user = $query->row();

        $old_password_matches = $this->hash_password_db($user->id, $old);

        if ($old_password_matches === TRUE)
        {
            //store the new password and reset the remember code so all remembered instances have to re-login
            $hashed_new_password  = $this->hash_password($new, $user->salt);
            $data = array(
                'password' => $hashed_new_password,
                'remember_code' => NULL,
            );

            $successfully_changed_password_in_db = $this->db->update($this->tables['users'], $data, array('email' => $identity));
            if ($successfully_changed_password_in_db)
            {
                $this->error_message = 'Your passord was updated successfully';
                $sec_array = array("success"=>"1", "message" => $this->error_message);
                return json_encode(array($sec_array));
            }
            else
            {
                $this->error_message = 'Error changing passowrd';
                $sec_array = array("success"=>"0", "message" => $this->error_message);
                return json_encode(array($sec_array));
            }

            return $successfully_changed_password_in_db;
        }

        $this->error_message = 'Password does not match';
        $sec_array = array("success"=>"0", "message" => $this->error_message);
        return json_encode(array($sec_array));
    }        

    /**
     * update
     *
     * @return bool
     * @author Phil Sturgeon
     **/
    public function update($id, array $data)
    {
        $this->db->select('*')
                 ->from('users')
                 ->where('id', $id);
        $user = $this->db->get()->row();

        $this->db->trans_begin();

        if (array_key_exists('email', $data) && $this->identity_check($data['email']) && $user->{'email'} !== $data['email'])
        {
            $this->db->trans_rollback();

            $sec_array = array("success"=>"0", "message" => 'Your account was not updated successfully');
            return json_encode(array($sec_array));           
        }

        // Filter the data passed
        $data = $this->_filter_data($this->tables['users'], $data);

        if (array_key_exists('username', $data) || array_key_exists('password', $data) || array_key_exists('email', $data))
        {
            if (array_key_exists('password', $data))
            {
                if( ! empty($data['password']))
                {
                    $data['password'] = $this->hash_password($data['password'], $user->salt);
                }
                else
                {
                    // unset password so it doesn't effect database entry if no password passed
                    unset($data['password']);
                }
            }
        }

        $this->db->update($this->tables['users'], $data, array('id' => $user->id));

        if ($this->db->trans_status() === FALSE)
        {
            $this->db->trans_rollback();
            $sec_array = array("success"=>"0", "message" => 'Your account was not updated successfully');
            return json_encode(array($sec_array));     
        }

        $this->db->trans_commit();

        $sec_array = array("success"=>"1", "message" => 'Your account was updated successfully');
            $this->db->select('id, email, first_name, last_name, birth_date, gender, pic')
                     ->from('users')
                     ->where('id', $id);
            $user_data = $this->db->get()->result_array()[0];
        return json_encode(array($sec_array, $user_data));     
    }    

    protected function _prepare_ip($ip_address) {
        //just return the string IP address now for better compatibility
        return $ip_address;
    }

    protected function _filter_data($table, $data)
    {
        $filtered_data = array();
        $columns = $this->db->list_fields($table);

        if (is_array($data))
        {
            foreach ($columns as $column)
            {
                if (array_key_exists($column, $data))
                    $filtered_data[$column] = $data[$column];
            }
        }

        return $filtered_data;
    }
}
?>