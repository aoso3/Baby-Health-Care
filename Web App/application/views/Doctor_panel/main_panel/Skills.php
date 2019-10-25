
<div class="col-xs-12 col-centered">


      			<section class="color-8"  style="padding-top:30px;padding-bottom:10px">

      				<p class="perspective">
      					<button class="btn_new btn-success btn-8 round-form" ng-click="done=true">Done</button>
      				</p>
      				<p class="perspective">
      					<button class="btn_new btn-danger btn-8 round-form" ng-click="done=false">Not Yet</button>
      				</p>
      			</section>


	<!-- <ul class="cd-pricing col-xs-8" ng-if="done" >
		<li ng-repeat="skills in done_skills" ng-if="skills.length">
			<header class="cd-pricing-header">
				<h3>{{skills[0].age_from}} to {{skills[0].age_to}} Month</h3>

			</header>

			<div  class="cd-pricing-features">
				<ul ng-repeat="s in skills">
          <h3>{{$index+1}}</h3>
          <li  aria-hidden="true"><li class="fa fa-check"><em>Skill: {{s.skill}}</em></li></li>
          <li aria-hidden="true"><em>Level: {{s.kind}}</em></li>
          <li aria-hidden="true"><em>Kid Age: {{s.age_months}} Months</em></li>
          <br>
				</ul>

			</div>

			<footer class="cd-pricing-footer">

			</footer>
		</li>
	</ul>

  <ul class="cd-pricing col-xs-8" ng-if="!done" >
    <li ng-repeat="skills in not_done_skills" ng-if="skills.length">
      <header class="cd-pricing-header">
        <h3>{{skills[0].age_from}} to {{skills[0].age_to}} Month</h3>
      </header>

      <div  class="cd-pricing-features">
          <h1 class="fa fa-times" ><em>{{skills.length}}</em></h1>
          <br>
        <ul ng-repeat="s in skills">
          <li aria-hidden="true"> <em>{{s.skill}}</em></li>
          <br>
        </ul>
      </div>

    </li>
  </ul>  -->







  <div class="p col-xs-12"  ng-if="done">

    <div class="fancy-collapse-panel" >
      <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

        <div class="col-xs-6" ng-repeat="skills in done_skills" ng-if="skills.length" >
            <!--Panel -1- -->
              <div class="panel panel-default" >
                <!--Panel -1- Heading-->
                  <div class="panel-heading" role="tab" id="{{$index}}headingOne">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#accordion" data-target="#{{$index}}done" aria-expanded="false" aria-controls="collapseOne">
                              <p class="skill-title">{{skills[0].age_from}} to {{skills[0].age_to}} Month</p>
                      </a>
                    </h4>
                  </div>
                <!--Panel -1- Content-->
                  <div id="{{$index}}done" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{$index}}headingOne" >
                    <div class="panel-body" ng-repeat="s in skills">
                      <label class="label-skill">Number: {{$index+1}}</label>
                        <p class="p-skill"></p>
                      <label class="label-skill">Name: {{s.skill}}</label>
                        <p class="p-skill"></p>
                      <label class="label-skill">Level: {{s.kind}}</label>
                        <p class="p-skill"></p>
                        <label class="label-skill">Date: {{s.date}} </label>
                          <p class="p-skill"></p>
                        <label class="label-skill">At Age: {{s.age_months}} Months</label>
                          <p class="p-skill"></p>
                          <label style="color:#FFFFFF"

                          ng-style="s.rate=='Ok' && {'background-color':'#b63023'}  || s.rate=='Good' && {'background-color':'#c8cb56'}
                                  || s.rate=='Very good' && {'background-color':'#1d92bd'} || s.rate=='Excellent' && {'background-color':'#287e0f'}
                                  || s.rate=='Great' && {'background-color':'#e38920'}"

                          class="label-skill">Rate: {{s.rate}} </label>
                            <p class="p-skill"></p>
                    </div>
                  </div>
                </div>
              </div>


        </div>
      </div>
    </div>


    <div class="p col-xs-12"  ng-if="!done">
      <div class="fancy-collapse-panel" >
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

          <div class="col-xs-6" ng-repeat="skills in not_done_skills" ng-if="skills.length">
              <!--Panel -1- -->
                <div class="panel panel-default" >
                  <!--Panel -1- Heading-->
                    <div class="panel-heading" role="tab" id="{{$index}}headingtow">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" data-target="#{{$index}}notdone" aria-expanded="false" aria-controls="collapseOne">
                                <p class="skill-title">{{skills[0].age_from}} to {{skills[0].age_to}} Month</p>
                        </a>
                      </h4>
                    </div>
                  <!--Panel -1- Content-->
                    <div id="{{$index}}notdone" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{$index}}headingtow" >
                      <div class="panel-body" ng-repeat="s in skills">
                        <label class="label-skill">Number: {{$index+1}}</label>
                          <p class="p-skill"></p>
                        <label class="label-skill">Name: {{s.skill}}</label>
                          <p class="p-skill"></p>
                        <label class="label-skill">Level: {{s.kind}}</label>
                          <p class="p-skill"></p>
                      </div>
                    </div>
                  </div>
                </div>


          </div>
        </div>
      </div>



</div>
