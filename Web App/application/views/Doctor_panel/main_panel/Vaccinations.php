<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/angular_table.css')?>">

    <section class="color-8 tabs-row" ng-init="growth('Height')"  style="padding-top: 30px;margin-bottom:-40px ">

      <p class="perspective">
        <button class="btn_new btn-success btn-8 round-form" ng-click="compleated()">Compleated</button>
      </p>
      <p class="perspective">
        <button class="btn_new btn-warning btn-8 round-form" ng-click="requasted()">Requasted</button>
      </p>
      <p class="perspective">
        <button class="btn_new btn-danger btn-8 round-form" ng-click="not_taken()">Not Taken</button>
      </p>
    </section>
    <!--Section Content-->
    <div class="content" ng-if="vac.length != 0" >

      <table st-table="vac_not_taken" st-safe-src="row" class="table table-striped" >
        <thead>
          <tr>
            <input id="search-input" style="margin-top:10px;" ng-model="search" class="form-control round-form" placeholder="global search ..." type="text"/>
          </tr>
          <tr>
            <th  st-sort="lastName">Name</th>
            <th  st-sort="firstName">Description</th>
            <th st-sort="firstName">Age From</th>
            <th  st-sort="firstName">Age To</th>
            <th ng-if="section=='not_taken'"  st-sort="firstName">Take</th>
          </tr>
        </thead>
        <tbody >
          <tr ng-repeat="row in vac | filter:search " >
            <td  >{{row.name}}</td>
            <td  >{{row.description}}</td>
            <td  >{{row.age_from}}</td>
            <td id="{{row.id}}" >{{row.age_to}}</td>
            <td ng-if="section=='not_taken'" >
            <button st-ratio="10" type="button" ng-click="add_vac(row)" class="btn btn-sm btn-success"
                    data-toggle="tooltip" data-placement="top" title="Remove">
              <i class="glyphicon glyphicon-ok">
              </i>
            </button>
            </td>
          </tr>
        </tbody>
        <tfoot>
      			<tr>
      				<td colspan="5" class="text-center">
      					<div st-items-by-page="2" st-pagination=""></div>
      				</td>
      			</tr>
      	</tfoot>
      </table>
    </div>
  </div>
</div>


    <div class="content" ng-if="vac.length == 0" >
          <div class="col-md-8 col-md-offset-4 ">
            <div class="row ">
              <div class="col-md-4 col-sm-2 ">
                <div class="box1" style="background-Color:#EA4B47;color:#D2F2F0;padding: 50px 50px;margin:50px 0px">
                  <h1>Empty</h1>
                </div>
              </div>
          </div>
         </div>
    </div>
