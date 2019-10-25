
      <div class="col-md-6 col-md-offset-3">
          <fieldset>
            <div class="form-group">
              <div class="col-md-9">
                <input id="name" type="text" placeholder="Your name" class="form-control" autofocus>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-12 text-right">
                <button type="button" id="submit" class="btn btn-primary">Submit</button>
              </div>
            </div>
          </fieldset>
      </div>



	<script src="<?php echo base_url('node_modules/socket.io/node_modules/socket.io-client/socket.io.js');?>"></script>
	<script>
  $(document).ready(function(){

    $("#submit").click(function(){

            var socket = io.connect( 'http://'+window.location.hostname+':3000' );

            socket.emit('new_message2', {
              creator_user_id: 11,
              additional_data: 1,
              type_id: 1,
              creation_date: '2016-05-11 00:00:00',
              title: 'vaccination request',
              text: 'You have a new request for samaria vaccination.',
              img: 'notification1.png',
              recipient_user_id: 43,
              child_id: 1,
              is_seen: 0,

            });

    });

  });
	</script>
