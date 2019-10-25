<body>

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          	<!--h3><class="fa fa-angle-right"></i> Baby's Name</h3-->
          	<div class="row mt">
              <div >
          		<div class="col-lg-2 col-md-2 col-sm-6 col-xs-6">
          		  <img src="assets/img/friends/fr-05.jpg" class="img-circle img-responsive" >
          		</div>
              <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4>اسم الطفل</h4>
                        <h4 class="subheading">شهر سنة</h4>
                        <i class="fa fa-mars fa-3x"></i>
                        <i class="fa fa-venus fa-3x"></i>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">
                        </p>
                    </div>
                </div>
              </div>
              </div>
            </div>
            <div class="row mt">
              <div class="panel-body ">
                <div class="row">
                  <ul class=" nav nav-tabs doctor-sections-tabs " style="text-align:center">
                  <!--ul class="nav nav-tabs pull-right nav-stacked"-->
                  <div class="col-lg-2 col-lg-push-4 col-xs-3 col-xs-push-3 h" style="text-align:center"><li>
                    <span class="fa-stack fa-4x" href="#feeding" data-toggle="tab" aria-expanded="true" >
                      <i class="fa fa-circle fa-stack-2x thumb "></i>
                      <i class="fa fa-spoon fa-stack-1x fa-inverse fa-rotate-270" ></i>
                    </span>
                  </li></div>
                  <div class="col-lg-2 col-lg-push-4 col-xs-3 col-xs-push-3 h"><li>
                    <span class="fa-stack fa-4x" href="#height" data-toggle="tab" aria-expanded="true" >
                      <i class="fa fa-circle fa-stack-2x thumb" ></i>
                      <i class="fa fa-child fa-stack-1x fa-inverse" ></i>
                    </span>
                  </li></div>
                  </ul>
                </div>
                <div class="row">
                  <div class="tab-content text-center col-lg-6 col-lg-push-3">
                  <div class="tab-pane fade active in" id="height" >
                      <div class="row">
                        <div class="col-md-12">
                          <div class="panel panel-default">
                            <div class="panel-heading">نمو الطفل</div>
                            <div class="panel-body">
                              <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="collapsed" aria-expanded="false">الطول</a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse" style="height: 0px;" aria-expanded="false">
                                        <div class="panel-body">
                                          /*Chart*/
                                            <form name="addSample" onsubmit="return validateForm()">

                                                <div class="inner-addon left-addon " >
                                                <i class="glyphicon glyphicon-calendar"></i>
                                                  <input type="date" required class="form-control" autofocus/>
                                              </div>
                                              <div class="inner-addon left-addon">
                                                <i class="glyphicon glyphicon-user"></i>
                                                  <!--input type="number" class="form-control" pattern="[0-9]+([\.,][0-9]+)?" step="0.01"/-->
                                                  <input type="text" class ="form-control" id="mesurement-height" required onchange="parseNumberHeight()"/>
                                              </div>
                                              <p id="warning-height"> <p>
                                              <button  class="btn btn-default">إضافة</button>
                                            </form>
                                    </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" class="collapsed">الوزن</a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse" style="height: 0px;" aria-expanded="false">
                                        <div class="panel-body">
                                            /*Chart*/
                                            <form name="addSample" onsubmit="return validateForm()">

                                                <div class="inner-addon left-addon " >
                                                <i class="glyphicon glyphicon-calendar"></i>
                                                  <input type="date" required class="form-control" autofocus/>
                                              </div>
                                              <div class="inner-addon left-addon">
                                                <i class="glyphicon glyphicon-user"></i>
                                                  <!--input type="number" class="form-control" pattern="[0-9]+([\.,][0-9]+)?" step="0.01"/-->
                                                  <input type="text" class ="form-control" id="mesurement-weight" required onchange="parseNumberWeight()"/>
                                              </div>
                                              <p id="warning-weight"> <p>
                                              <button  class="btn btn-default">إضافة</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" class="collapsed">محيط الرأس</a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree" class="panel-collapse collapse" style="height: 0px;" aria-expanded="false">
                                        <div class="panel-body">
                                            /*Chart*/
                                            <form name="addSample" onsubmit="return validateForm()">

                                                <div class="inner-addon left-addon " >
                                                <i class="glyphicon glyphicon-calendar"></i>
                                                  <input type="date" required class="form-control" autofocus/>
                                              </div>
                                              <div class="inner-addon left-addon">
                                                <i class="glyphicon glyphicon-user"></i>
                                                  <!--input type="number" class="form-control" pattern="[0-9]+([\.,][0-9]+)?" step="0.01"/-->
                                                  <input type="text" class ="form-control" id="mesurement-head" required onchange="parseNumberHead()"/>
                                              </div>
                                              <p id="warning-head"> <p>
                                              <button  class="btn btn-default">إضافة</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                  </div>
                </div>
                  <div class="tab-pane fade" id="feeding" >
                    <div class="row">
                      <div class="col-md-12">
                        <div class="panel panel-default">
                          <div class="panel-heading">غذاء الطفل</div>
                          <div class="panel-body">
                            /*Table*/
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>









              </div>
      </div>

		</section><!--/wrapper -->
      </section><!-- /MAIN CONTENT -->

      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
              2014 - Alvarez.is
              <a href="blank.html#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->

    </body>
