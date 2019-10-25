<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/angular_table.css')?>">

    <section class="color-8 tabs-row" ng-init="growth('Height')" style="padding-top:30px;padding-bottom:10px">

      <p class="perspective">
        <button class="btn_new btn-success btn-8 round-form" ng-click="growth('Height')">Height</button>
      </p>
      <p class="perspective">
        <button class="btn_new btn-success btn-8 round-form" ng-click="growth('Head')">Head Size</button>
      </p>
      <p class="perspective">
        <button class="btn_new btn-success btn-8 round-form" ng-click="growth('Weight')">Weight</button>
      </p>
    </section>

      <div style="padding-bottom:10px">
        <h2>Growth Chart</h2>
      </div>

<!--Section Content-->
  <div class="content">
    <!--Chart-->
      <div class="col-centered" style="margin-top: -10%;">
        <div id="linechart_material"></div>
      </div>
    <!--Add new Sample-->
      <div class="row">
          <div class="col-md-8 col-centered">
            <div class=" col-xs-12 col-md-5 col-centered">
              <input type="number" name="week"  id="week" required class="form-control round-form inputs" placeholder="week" pattern="[0-9]+" step="1">
            </div>
            <div class=" col-xs-12 col-md-5 col-centered">
              <input type="number" name="value" id="value" required class="form-control round-form inputs" placeholder="Value" pattern="[0-9]*[.,]?[0-9]+?" step="0.0000001">
              <p id="warning" ></p>
            </div>
          </div>
        </div>
      <div class="row">
        <div class="col-md-2 col-centered ">
          <button ng-click="addItem(row)"class="btn round-form add-samples-btns" id="add">ADD</button>
        </div>
        </div>

    <!--Table Samples-->
      <div class="col-centered col-xs-10" ng-if="quantities_array.length != 0">
        <table st-table="quantities_array" st-safe-src="row" class="table table-striped" >
          <thead>
          <tr>
            <input id="search-input" style="margin-top:10px;" ng-model="search" class="form-control round-form" placeholder="global search ..." type="text"/>
          </tr>
          <tr>
            <th st-ratio="40" st-sort="lastName">Week</th>
            <th st-ratio="40" st-sort="firstName">Value</th>
          </tr>
          </thead>
          <tbody >
          <tr   ng-repeat="row in quantities_array | filter:search | orderBy: order">
            <td st-ratio="40" >{{row.age_weeks}}</td>
            <td st-ratio="40" id="{{row.id}}" contentEditable="true" >{{row.measurement}}</td>
            <td >
            <button st-ratio="10" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger"
                    data-toggle="tooltip" data-placement="top" title="Remove">
              <i class="glyphicon glyphicon-remove-circle">
              </i>
            </button>
            <button st-ratio="10" type="button" ng-click="editItem(row)" class="btn btn-sm btn-primary"
              data-toggle="tooltip" data-placement="top" title="Edit">
              <i class="glyphicon glyphicon-edit">
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
</div>


<div class="content" ng-if="quantities_array.length == 0" >
      <div class="col-md-7 col-md-offset-5 ">
        <div class="row ">
          <div class="col-md-4 col-sm-2 ">
            <div class="box1" style="background-Color:#EA4B47;color:#D2F2F0;padding: 50px 50px;margin:50px 0px">
              <h1>Empty</h1>
            </div>
          </div>
      </div>
     </div>
</div>
