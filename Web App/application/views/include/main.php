<?php
if(!isset($header)){
        $header = "include/header";
}
if(!isset($footer)){
        $footer = "include/footer";
}
if(!isset($load_header) || $load_header)
	$this->load->view($header);
  if(isset($main_content)){
    $this->load->view($main_content);
    }
if(!isset($load_footer) || $load_footer)
$this->load->view($footer);
