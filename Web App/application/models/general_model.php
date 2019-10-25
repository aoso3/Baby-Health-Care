<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class General_model extends MY_Model {
	
    public $before_create = array();
    public $before_update = array();

	public function __construct()
	{
		parent::__construct();
	}

	public function set_table($table)
	{
		$this->_table = $table;

		return $this;
	}

	public function get_table()
	{
		return $this->_table;
	}

	public function get_all()
   	{
   		// $this->db->order_by($this->_table.'.updated_at desc');
   		$this->db->order_by($this->_table.'.id desc');
   		return parent::get_all();
   	}

}

/* End of file general_model.php */
/* Location: ./application/models/general_model.php */