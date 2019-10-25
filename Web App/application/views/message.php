

    <?php
          foreach($message->result() as $row){
    ?>
          <div id="msg">
            <?php echo $row->name."<br>";?>
          </div>
    <?php

          }
    ?>


	<script src="<?php echo base_url('node_modules/socket.io/node_modules/socket.io-client/socket.io.js');?>"></script>
	<script>


  var socket = io.connect( 'http://'+window.location.hostname+':3000' );

  socket.on( 'new_message2', function( data ) {
      $( "#msg" ).prepend(data.text+"<br>");
  });

</script>
