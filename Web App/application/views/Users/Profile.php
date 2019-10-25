<style>
  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
  }
  #map {
    height: 100%;
  }
  .controls {
    margin-top: 10px;
    border: 1px solid transparent;
    border-radius: 2px 0 0 2px;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    height: 32px;
    outline: none;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  }

  #pac-input {
    background-color: #fff;
    font-family: Roboto;
    font-size: 15px;
    font-weight: 300;
    margin-left: 12px;
    padding: 0 11px 0 13px;
    text-overflow: ellipsis;
    width: 300px;
  }

  #pac-input:focus {
    border-color: #4d90fe;
  }

  .pac-container {
    font-family: Roboto;
  }

  #type-selector {
    color: #fff;
    background-color: #4d90fe;
    padding: 5px 11px 0px 11px;
  }

  #type-selector label {
    font-family: Roboto;
    font-size: 13px;
    font-weight: 300;
  }
</style>


<script type="text/javascript">
$(document).ready(function(){
    $(".form").validationEngine();
   });
</script>

<div lang="en" dir="ltr" class="no-js">
<!--main content start-->
  <section id="main-content"><section class="wrapper">
    <div class=" view ">
      <div class=" form row title">
          <h3 style="margin-bottom:40px;">Edit Profile</h3>
      <form accept-charset="utf-8" action="<?php echo site_url('auth/edit_user') ?>" method="post">
      <!--Picture & Maps-->
        <div class="col-xs-4 col-xs-offset-2 col-centered">
          <div class="avatar img-circle img-thumbnail center-block " style="margin-top:-30px;" id="container_photo"></div>
          <input style="direction:ltr;width:50%" value="<?php echo $location_name ?>" name="location_name" id="pac-input" class="controls" type="text"
                        placeholder="Enter a location" autocomplete="on" runat="server" required >
          <input type="hidden" id="city2" name="city2" />
          <input type="hidden" id="cityLat" name="cityLat" />
          <input type="hidden" id="cityLng" name="cityLng" />
          <input type="hidden" id="location" name="location"  />
          <div  style="height:250px; margin-top:20px" id="map"></div>

        </div>

      <!--Infos-->
        <div class="  col-xs-6 ">
          <!--Username-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Username:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input class=" form-control round-form" name="username" value="<?php echo $username ?>" type="text" data-validation-engine="validate[required,minSize[3],mamdize[50]]"/>
                </div>
            </div></div>
          <!--First Name-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">First Name:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input class=" form-control round-form" name="first_name" value="<?php echo $first_name ?>"  type="text" data-validation-engine="validate[required,minSize[3],mamdize[50]]"/>
                </div>
              </div></div>
          <!--Last Name-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Last Name:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                    <input class="form-control round-form" name="last_name" value="<?php echo $last_name ?>"  type="text" data-validation-engine="validate[required,minSize[3],mamdize[50]]"/>
                </div>
            </div></div>
          <!--Email-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">E-mail:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input class="form-control round-form" name="email" value="<?php echo $email ?>" type="text" data-validation-engine="validate[required,custom[email]]"/>
                </div>
            </div></div>
          <!--Birthday-->
            <div class="form-group" ><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Birthday:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input type="date" class="form-control round-form" name="birth_date" value="<?php if($birth_date !='0000-00-00') echo $birth_date ?>" />
                </div>
            </div>
          </div>

                    <!--div class="form-group"><div class="row">
                      </div></div></div-->
          <!--Phone-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Phone:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input class="form-control round-form" name="phone" value="<?php echo $phone ?>" type="number" />
                </div>
            </div></div>
          <!--Password-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Password:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <input type="hidden" name="name" value="">
                  <input class="form-control round-form" onchange="parseNumberHead()" name="password"  type="password" />
                </div>
            </div></div>
          <!--Gender-->
            <div class="form-group"><div class="row">
              <!--Label-->
                <div class="col-md-4"><label class="control-label">Gender:</label></div>
              <!--Input-->
                <div class="col-md-8 ">
                  <div class="radio3 radio-female">
                    <input type="radio" id="radio1" name="radio1" value="option1" <?php if($user_info->gender !== 'male'){echo "checked";} ?>>
                      <label for="radio1">
                      Female
                      </label>
                  </div>
                  <div class="radio3 radio-male">
                    <input type="radio" id="radio2" name="radio1" value="option2" <?php if($user_info->gender !== 'female'){echo "checked";} ?>>
                      <label for="radio2">
                      male
                      </label>
                  </div>
                </div>
            </div></div>
        </div>

      <!-- bouttons -->
        <div class="row"><div class="col-md-4 pull-right" >
          <input type="submit" class="btn btn-primary" value="Save Changes">
          <input type="reset" class="btn btn8default" value="Cancel">
        </div>

          <input type="hidden" id="pic" name="pic" />


      </form>
            </div>
          </div>
        </div>
      </div>
    </section>
</section>

    <script>
        $(document).ready(function(){


            $("#container_photo").PictureCut({
                Extensions                  : ["jpg","png","gif","ico","jpeg","tiff"],
                InputOfImageDirectory       : "image",
                ActionToSubmitUpload        : "<?php echo base_url('assets/js/PictureCut/src/php/upload.php')?>",
                ActionToSubmitCrop          : "<?php echo base_url('assets/js/PictureCut/src/php/crop.php')?>",
                PluginFolderOnServer        : "<?php echo base_url('assets/js/PictureCut/')?>",
                FolderOnServer              : "/baby_health_care/assets/images/users/",
                DefaultImageButton          : '<?php echo base_url("assets/images/users/$user_info->pic")?>',
                MinimumWidthToResize        : 1024,
                MinimumHeightToResize       : 630,
                MaximumSize                 : 4096,
                EnableCrop                  : true,
                ImageNameRandom             : true,
                CropWindowStyle             : "Bootstrap",
                CropModes                   :{
                                                widescreen: true,
                                                letterbox: true,
                                                free   : true
                                              },
                CropOrientation             : true,
                UploadedCallback            : function(data){
                  document.getElementById('pic').value = data.currentFileName;
                }
            });

        })
    </script>

    <script>
    $(document).ready(function() {
        $(window).keydown(function(event){
          if(event.keyCode == 13) {
            event.preventDefault();
            return false;
          }
        });
        $("#datepicker").datepicker({ dateFormat: "dd/mm/yy" }).datepicker("setDate", new Date());

});
      // This example requires the Places library. Include the libraries=places
      // parameter when you first load the API. For example:
      // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">

      function initMap() {
        var location = "<?php echo $location; ?>";
        var loc_lat = location.substr(0, location.indexOf(','));
        var loc_lng = location.substr(location.indexOf(',')+1,location.length);
        var t = parseFloat(loc_lat);
        var g = parseFloat(loc_lng);

        // var url = 'http://maps.googleapis.com/maps/api/geocode/json?latlng='+location+'&sensor=true';
        // $.getJSON(url, function (json) {
        //     if (json.status == "OK") {
        //         //Check result 0
        //         var result = json.results[0];
        //         //look for locality tag and administrative_area_level_1
        //         var city = "";
        //         var state = "";
        //         for (var i = 0, len = result.address_components.length; i < len; i++) {
        //             var ac = result.address_components[i];
        //             if (ac.types.indexOf("administrative_area_level_1") >= 0) state = ac.short_name;
        //         }
        //         if (state != '') {
        //             if(city != "" && state != ""){
        //             document.getElementById('pac-input').value = city + ", " + state;
        //           }
        //           else if(city == ""){
        //             document.getElementById('pac-input').value = state;
        //           }
        //           else{
        //             document.getElementById('pac-input').value = city;
        //
        //           }
        //         }
        //
        //     }
        //
        // });


        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: t,lng: g},
          zoom: 14,
          mapTypeId: google.maps.MapTypeId.HYBRID

        });


        var input = /** @type {!HTMLInputElement} */(
            document.getElementById('pac-input'));

        var types = document.getElementById('type-selector');
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(types);

        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);

        var infowindow = new google.maps.InfoWindow();
        var marker = new google.maps.Marker({
          map: map,
          anchorPoint: new google.maps.Point(0, -29)
        });

        autocomplete.addListener('place_changed', function() {
          infowindow.close();
          marker.setVisible(false);
          var place = autocomplete.getPlace();
          if (!place.geometry) {
            //window.alert("Autocomplete's returned place contains no geometry");
          //  return;
          }

          // If the place has a geometry, then present it on a map.
          if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
          } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);  // Why 17? Because it looks good.
          }
          marker.setIcon(/** @type {google.maps.Icon} */({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
          }));
          marker.setPosition(place.geometry.location);
          marker.setVisible(true);

          var address = '';
          if (place.address_components) {
            address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
          }

          infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
          infowindow.open(map, marker);
        });
        google.maps.event.addListener(autocomplete, 'place_changed', function () {
            var place = autocomplete.getPlace();
            // document.getElementById('pac-input').value = place.name;
            document.getElementById('cityLat').value = place.geometry.location.lat();
            document.getElementById('cityLng').value = place.geometry.location.lng();
            document.getElementById('location').value = place.geometry.location.lat() + ',' + place.geometry.location.lng();

        });

      }

    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBUG9tIY6ZYRi5aNq7Jtpniu1jsJ6pQJkg&libraries=places&callback=initMap"
        async defer>
        </script>
