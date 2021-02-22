<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | physical Verification </title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="../../plugins/datatables/dataTables.bootstrap.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="../../dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="../../dist/css/skins/_all-skins.min.css">
        <link rel="shortcut icon" href="../../dist/img/NDBBANK.png" alt="..." class="img-circle"/>

        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery-1.11.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery-migrate-1.2.1.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery-ui.min.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.validate.min.js"></script> 
        <script src="${pageContext.request.contextPath}/bootstrap/js/respond.src.js"></script> 
        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.jqGrid.min.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/i18n/grid.locale-en.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/i18n/defaults-en_US.min.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.popupwindow.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.validate.min.js"></script> 
        <script src="${pageContext.request.contextPath}/bootstrap/js/myscript.js"></script>


    </head>
    <script type="text/javascript">
        <%
            boolean _session_availability = true;
            UserBean user = null;
            MenuDAO menuDAO = null;
            String user_type = "";
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }
            if (_session_availability) {
                user = (UserBean) (session.getAttribute("user"));
                user_type = user.getCode();
                menuDAO = new MenuDAO();
            }

        %>
        $(function () {
            window.onload = function () {
                $("#cheque_number_input").focus();
                $("#seller_name").prop('readonly', true);
                $("#buyer_name").prop('readonly', true);
                $("#cheque_number").prop('readonly', true);
                $("#bank_code").prop('readonly', true);
                $("#branch_code").prop('readonly', true);
                $("#cheque_amu").prop('readonly', true);
                $("#value_date").prop('readonly', true);
                $("#div_reenter_data").hide();
                $("#div_ver_chq_data").hide();
                $("#div_btn_vrify").hide();


            };

            $(document).keypress(function (e) {
                if (e.which == 13) {
                    $("#btn_verify").focus();
                }
            });



            $("#cheque_number_input").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });
            $("#bank_code_input").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });
            $("#branch_code_input").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cheque_number_input").keyup(function () {
                if (this.value.length === 6) {

                    if (validateInput()) {


                        var paramid = "msrt_verify_data";
                        var values = "&cheque_number_input=" + $("#cheque_number_input").val().trim() +
                                "&bank_code_input=" + $("#bank_code_input").val().trim() +
                                "&branch_code_input=" + $("#branch_code_input").val().trim() +
                                "&paramid=" + paramid;


                        $.ajax({
                            type: "POST",
                            url: "/NDBRMS/report_data_extract",
                            data: values,
                            dataType: "json",
                            //if received a response from the server
                            success: function (data) {

                                $.each(data, function (k, v) {

                                    if (v.pdc_chq_verification === 'Available') {
                                        $("#div_ver_chq_data").show();
                                        $("#div_btn_vrify").show();
                                        $('#seller_name').val(v.seller_id + '|' + v.seller_name);
                                        $('#buyer_name').val(v.buyer_id + '|' + v.buyer_name);
                                        $('#cheque_number').val(v.pdc_chq_number);
                                        $('#bank_code').val(v.pdc_bank_code);
                                        $('#branch_code').val(v.pdc_branch_code);
                                        $('#cheque_amu').val(v.pdc_chq_amu);
                                        $('#value_date').val(v.value_date);
                                        $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);

                                        $("#pdc_chq_checking").hide();
                                    } else if (v.pdc_chq_verification === 'Duplicate') {
                                        alert("Duplicate of cheques. Please enter bank code and branch code for relevent cheque.")
                                        $("#div_reenter_data").show();

                                        $("#bank_code_input").focus();



                                    } else if (v.pdc_chq_verification === 'Notavailable') {


                                        $('#cheque_number_input').css('border-color', 'red');
                                        $('#cheque_error_message').text("Cheque not available in the system...");
                                        alert("Cheque not available in the system...");
                                        location.reload();
                                        $("#bank_code").focus();
                                    }


                                });
                            }
                        });
                    }
                }

            });

            $("#bank_code_input").keyup(function () {
                if (this.value.length === 4) {
                    $("#branch_code_input").focus();
                }
            });


            $("#branch_code_input").keyup(function () {
                if (this.value.length === 3) {

                    if (validateInput()) {


                        var paramid = "msrt_verify_data";
                        var values = "&cheque_number_input=" + $("#cheque_number_input").val().trim() +
                                "&bank_code_input=" + $("#bank_code_input").val().trim() +
                                "&branch_code_input=" + $("#branch_code_input").val().trim() +
                                "&paramid=" + paramid;


                        $.ajax({
                            type: "POST",
                            url: "/NDBRMS/report_data_extract",
                            data: values,
                            dataType: "json",
                            //if received a response from the server
                            success: function (data) {

                                $.each(data, function (k, v) {

                                    if (v.pdc_chq_verification === 'Available') {
                                        $("#div_ver_chq_data").show();
                                        $("#div_btn_vrify").show();
                                        $('#seller_name').val(v.seller_id + '|' + v.seller_name);
                                        $('#buyer_name').val(v.buyer_id + '|' + v.buyer_name);
                                        $('#cheque_number').val(v.pdc_chq_number);
                                        $('#bank_code').val(v.pdc_bank_code);
                                        $('#branch_code').val(v.pdc_branch_code);
                                        $('#cheque_amu').val(v.pdc_chq_amu);
                                        $('#value_date').val(v.value_date);
                                        $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);

                                        $("#pdc_chq_checking").hide();
                                    } else if (v.pdc_chq_verification === 'Duplicate') {
                                        alert("Duplicate of cheques. Please enter bank code and branch code for relevent cheque.")
                                        $("#div_reenter_data").show();

                                        $("#bank_code_input").focus();



                                    } else if (v.pdc_chq_verification === 'Notavailable') {


                                        $('#cheque_number_input').css('border-color', 'red');
                                        $('#cheque_error_message').text("Cheque not available in the system...");
                                        alert("Cheque not available in the system...");
                                        location.reload();
                                        $("#bank_code").focus();
                                    }


                                });
                            }
                        });
                    }
                }

            });


            $("#btn_check").click(function () {

                if (validateInput()) {


                    var paramid = "msrt_verify_data";
                    var values = "&cheque_number_input=" + $("#cheque_number_input").val().trim() +
                            "&bank_code_input=" + $("#bank_code_input").val().trim() +
                            "&branch_code_input=" + $("#branch_code_input").val().trim() +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/report_data_extract",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {

                            $.each(data, function (k, v) {

                                if (v.pdc_chq_verification === 'Available') {
                                    $("#div_ver_chq_data").show();
                                    $("#div_btn_vrify").show();
                                    $('#seller_name').val(v.seller_id + '|' + v.seller_name);
                                    $('#buyer_name').val(v.buyer_id + '|' + v.buyer_name);
                                    $('#cheque_number').val(v.pdc_chq_number);
                                    $('#bank_code').val(v.pdc_bank_code);
                                    $('#branch_code').val(v.pdc_branch_code);
                                    $('#cheque_amu').val(v.pdc_chq_amu);
                                    $('#value_date').val(v.value_date);
                                    $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);

                                    $("#pdc_chq_checking").hide();
                                } else if (v.pdc_chq_verification === 'Duplicate') {
                                    alert("Duplicate of cheques. Please enter bank code and branch code for relevent cheque.")
                                    $("#div_reenter_data").show();

                                    $("#bank_code_input").focus();



                                } else if (v.pdc_chq_verification === 'Notavailable') {


                                    $('#cheque_number_input').css('border-color', 'red');
                                    $('#cheque_error_message').text("Cheque not available in the system...");
                                    alert("Cheque not available in the system...");
                                    location.reload();
                                    $("#bank_code").focus();
                                }


                            });
                        }
                    });
                }

            });




            $("#btn_verify").click(function (e) {
                if (validateInputForVerify()) {


                    var paramid = "msrt_save_chq_verify_data";
                    var values = "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/MasterServletData",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            if (data.success) {
                                alert("Cheque verified successfully...");
                                location.reload();
                            } else if (data.alreadyexdata) {
                                alert("Cheque already verified..");
                                $('#cheque_number_input').css('border-color', 'red');
                                $('#bank_code_input').css('border-color', 'red');
                                $('#branch_code_input').css('border-color', 'red');
                                $('#cheque_error_message').text("Cheque already verified..");

                            } else if (data.systemna) {
                                alert("Error occured in accessing database for cheque verification..");
                                $('#cheque_number_input').css('border-color', 'red');
                                $('#bank_code_input').css('border-color', 'red');
                                $('#branch_code_input').css('border-color', 'red');
                                $('#cheque_error_message').text("Error occured in accessing database for cheque verification..");
//                                location.reload();
                            } else if (data.error) {
                                alert("Error occured in accessing database for cheque verification..");
                                $('#cheque_number_input').css('border-color', 'red');
                                $('#bank_code_input').css('border-color', 'red');
                                $('#branch_code_input').css('border-color', 'red');
                                $('#cheque_error_message').text("Error occured in accessing database for cheque verification..");
//                                
//                                location.reload();
                            }
                        }
                    });
                }

            });



            $('#btn_cancel').click(function () {
                location.reload();
            });



            function validateInput() {


                var hasError = true;
                var cheque_number_input = $("#cheque_number_input").val();

                if (cheque_number_input === '') {
                    $('#cheque_number_input').css('border-color', 'red');
                    return false;
                }


                return hasError;
            }

            function validateInputForVerify() {


                var hasError = true;
                var cheque_number_input = $("#cheque_number_input").val();
                var seller_name = $("#seller_name").val();
                var buyer_name = $("#buyer_name").val();
                var cheque_number = $("#cheque_number").val();
                var bank_code = $("#bank_code").val();
                var branch_code = $("#branch_code").val();
                var cheque_amu = $("#cheque_amu").val();
                var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();

                if (cheque_number_input === '') {
                    $('#cheque_number_input').css('border-color', 'red');
                    return false;
                }
                if (seller_name === '') {
                    $('#seller_name').css('border-color', 'red');
                    return false;
                }
                if (buyer_name === '') {
                    $('#buyer_name').css('border-color', 'red');
                    return false;
                }
                if (cheque_number === '') {
                    $('#cheque_number').css('border-color', 'red');
                    return false;
                }
                if (bank_code === '') {
                    $('#bank_code').css('border-color', 'red');
                    return false;
                }
                if (branch_code === '') {
                    $('#branch_code').css('border-color', 'red');
                    return false;
                }
                if (cheque_amu === '') {
                    $('#cheque_amu').css('border-color', 'red');
                    return false;
                }

                if (idndb_pdc_txn_master === '') {
                    alert("Invalid input for pdc cheque verification..")
                    return false;
                }


                return hasError;
            }

            function test(filter) {
                //alert(filter);
            }


        });
    </script>




    <body class="hold-transition skin-blue sidebar-mini" >
        <div class="wrapper">
            <header class="main-header">
                <!-- Logo -->
                <a href="/NDBRMS/indexl.jsp" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini"><b>A</b>LT</span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg"><b>NDB </b>BANK RMS</span>
                </a>
                <!-- Header Navbar: style can be found in header.less -->
                <nav class="navbar navbar-static-top" role="navigation">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- Messages: style can be found in dropdown.less-->


                            <!-- User Account: style can be found in dropdown.less -->
                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="btn btn-default btn-flat" style="width: 600px;">
                                    <%   if (_session_availability) {%>
                                    <span class="hidden-xs" style="float: right">   <%=user.getStrUserName()%> (<%= menuDAO.getDate()%>)</span>
                                    <%  } %>
                                    <img src="../../dist/img/man.png" class="user-image" alt="User Image" style="float: right">
                                </a>
                            </li>
                            <li class="dropdown messages-menu">
                                <a href="/NDBRMS/LogoutServlet" >
                                    <i class="fa fa-power-off"></i>
                                </a>                                
                            </li>
                            <li>
                                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>

            <aside class="main-sidebar">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <form action="#" method="get" class="sidebar-form">
                        <div class="user-panel" style="height: 48px;">
                            <div class="pull-left image">
                                <img src="../../dist/img/man.png" class="img-circle" alt="User Image">
                            </div>
                            <div class="pull-left info">
                                <%   if (_session_availability) {%>
                                <p><i class="fa fa-circle text-success"></i>    <%=user.getStrUserName()%><a href="#"></a></p>
                                    <%  } %>
                            </div>
                        </div>
                    </form>
                    <ul class="sidebar-menu">

                        <%
                            String HTML = "";
                            if (_session_availability) {

                                if (user_type.equals("3")) {
                                    HTML = menuDAO.getSubSupperUserMenues();
                                }
                                if (user_type.equals("6")) {
                                    HTML = menuDAO.getSubPDCRFMasterFileSMSInputter();
                                }
                                if (user_type.equals("7")) {
                                    HTML = menuDAO.getSubPDCRFMasterFileSMSAuthorizor();
                                }
                                if (user_type.equals("8")) {
                                    HTML = menuDAO.getSubPDCRFMasterFileInputter();
                                }
                                if (user_type.equals("9")) {
                                    HTML = menuDAO.getSubPDCRFMasterFileAuthorizor();
                                }
                                if (user_type.equals("10")) {
                                    HTML = menuDAO.getSubPDCRFTransactionInputter();
                                }
                                if (user_type.equals("11")) {
                                    HTML = menuDAO.getSubPDCRFTransactionAuthorizor();
                                }
                                if (user_type.equals("12")) {
                                    HTML = menuDAO.getSubPDCBussinessUser();
                                }

                            }
                        %>

                        <%=HTML%>


                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        Physical Verification Of Cheques.
                        <small>Physical Verification Input</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Physical Verification</a></li>
                        <li class="active">Physical Verification Input</li>
                    </ol>
                </section>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal" id="bank_form" name="bank_form" action="/NDBRMS/MasterServletFile"> 
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Number</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="cheque_number_input" name="cheque_number_input" style="width: 350px" maxlength="6">
                                                <input type="hidden" class="form-control" id="idndb_pdc_txn_master" name="idndb_pdc_txn_master" style="width: 350px">
                                                <input type="hidden" class="form-control" id="idndb_pdc_chq_verification" name="idndb_pdc_chq_verification" style="width: 350px">
                                                <label class="col-sm-2 control-label" id="cheque_error_message" style="margin-top: -34px;margin-left: 360px;text-align: left;width: 573px;"></label>
                                            </div>
                                        </div>

                                        <div id="div_reenter_data">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Bank Code</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="bank_code_input" name="bank_code_input" maxlength="4" style="width: 350px" maxlength="4">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Branch Code</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="branch_code_input" name="branch_code_input" maxlength="4" style="width: 350px" maxlength="3">
                                                </div>
                                            </div>
                                        </div>
                                        <div id="div_ver_chq_data">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Seller Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="seller_name" name="seller_name" maxlength="4" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Buyer Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="buyer_name" name="buyer_name" maxlength="4" style="width: 350px">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Cheque Number</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cheque_number" name="cheque_number" maxlength="4" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Bank Code</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="bank_code" name="bank_code" maxlength="4" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Branch Code</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="branch_code" name="branch_code" maxlength="4" style="width: 350px">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Cheque Amount</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cheque_amu" name="cheque_amu" placeholder="0.00" style="width: 350px">

                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Value Date</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="value_date" name="value_date" placeholder="dd/mm/yyyy" style="width: 350px">

                                                </div>
                                            </div>

                                        </div>


                                    </div><!-- /.box-body -->

                                </form>

                                <div class="box-footer" id="pdc_chq_checking">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_check" name="btn_check" style="width: 100px;">Check</button>
                                </div>

                                <div id="div_btn_vrify">
                                    <div class="box-footer">
                                        <button type="submit" class="btn btn-info pull-right" id="btn_cancel" name="btn_cancel" style="width: 100px;">Cancel</button>
                                        <button type="submit" class="btn btn-info pull-left" id="btn_verify" name="btn_verify" style="margin-left: 169px;width: 100px;">Verify</button>
                                    </div>
                                </div>

                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div>


                </section>

                <!-- Main content -->

            </div><!-- /.row -->

        </div><!-- /.content-wrapper -->
        <footer class="main-footer">
            <div class="pull-right hidden-xs">
                <b>Version</b> 2.1.0
            </div>
            <strong>Copyright &copy; 2016 <a href="http://www.ndbbank.com">NDB IT SS Department</a>.</strong> All rights reserved.
        </footer>

        <!-- Control Sidebar -->
        <aside class="control-sidebar control-sidebar-dark">
            <!-- Create the tabs -->
            <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
                <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
                <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <!-- Home tab content -->
                <div class="tab-pane" id="control-sidebar-home-tab">


                </div><!-- /.tab-pane -->

            </div>
        </aside><!-- /.control-sidebar -->
        <!-- Add the sidebar's background. This div must be placed
             immediately after the control sidebar -->
        <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    <!-- jQuery 2.1.4 -->
    <script src="../../plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
    <!-- DataTables -->
    <script src="../../plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="../../plugins/datatables/dataTables.bootstrap.min.js"></script>
    <!-- SlimScroll -->
    <script src="../../plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <!-- FastClick -->
    <script src="../../plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="../../dist/js/app.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="../../dist/js/demo.js"></script>
    <!-- page script -->
    <script>
        $(function () {
            $("#example1").DataTable();
            $('#example2').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
        });
    </script>
</body>
</html>
