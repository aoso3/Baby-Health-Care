var socket  = require( 'socket.io' );
var express = require('express');
var app     = express();
var server  = require('http').createServer(app);
var io      = socket.listen( server );
var port    = process.env.PORT || 3000;

server.listen(port, function () {
  console.log('Server listening at port %d', port);
});


io.on('connection', function (socket) {
  socket.on( 'new_message2', function( data ) {
      console.log(data);
    io.sockets.emit( 'new_message2', {
    	creator_user_id: data.creator_user_id,
      additional_data: data.additional_data,
      type_id: data.type_id,
      creation_date: data.creation_date,
      title: data.title,
      text: data.text,
      img: data.img,
      recipient_user_id: data.recipient_user_id,
      child_id: data.child_id,
      is_seen: data.is_seen,
    });
  });

  
});