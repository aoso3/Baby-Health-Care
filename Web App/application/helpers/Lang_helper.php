<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

if ( ! function_exists('get_lang_string'))
{

	 function get_lang_string($message_name,$print=true){
	 	$ci=& get_instance();
		$ci->load->library('helper_lib');
		if($print)
			echo $ci->helper_lib->get_lang_string($message_name);
		else
			return $ci->helper_lib->get_lang_string($message_name);
	}
}

if ( ! function_exists('format_msg'))
{

	 function format_msg($msg,$variable,$value,$multiple=false){
	 	$ci=& get_instance();
		$ci->load->library('helper_lib');
		return $ci->helper_lib->format_msg($msg,$variable,$value,$multiple);
	}
}
