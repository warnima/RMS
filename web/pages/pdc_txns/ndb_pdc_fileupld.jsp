<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | File Upload</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="../../bootstrap/css/style.css">
        <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="../../plugins/datatables/dataTables.bootstrap.css">
        <link rel="stylesheet" href="../../plugins/daterangepicker/daterangepicker-bs3.css">
        <link rel="stylesheet" href="../../plugins/iCheck/all.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="../../dist/css/AdminLTE.min.css">
        <link rel="shortcut icon" href="../../dist/img/NDBBANK.png" alt="..." class="img-circle"/>

        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="../../dist/css/skins/_all-skins.min.css">
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
        <script src="${pageContext.request.contextPath}/bootstrap/js/sweetalert.min.js"></script>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <script type="text/javascript">

        <%

            boolean _session_availability = true;
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }

            MenuDAO menuDAO = new MenuDAO();
        %>
        $(function () {
            window.onload = function () {

                $("#loading-mask").hide();
                document.getElementById("pdc_branch_ac_officer").disabled = true;

            }

            $('#btn_cancel').click(function () {
                location.reload();
            });

            $("#PDCBulkUploadFileRFData").change(function () {
                $("#loading-mask").show();
                if (this.checked) {
                    $("#PDCBulkUploadFileCWData").prop('checked', false);
                    $('#pdc_branch_ac_officer').val("");

                    var paramid = "get_rf_sellers";
                    var values = "&programe_type=RF&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {


                            $('#idndb_customer_define_seller_id')
                                    .empty()
                                    .append('<option selected="selected" value="Select The Seller">Select The Seller</option>')

                            $('#idndb_customer_define_seller_name')
                                    .empty()
                                    .append('<option selected="selected" value="Select The Seller">Select The Seller</option>')




                            $.each(data, function (k, v) {




                                $('#idndb_customer_define_seller_id').append($("<option/>", {
                                    value: v.idndb_cust_prod_map,
                                    text: v.cust_id
                                }));


                                $('#idndb_customer_define_seller_name').append($("<option/>", {
                                    value: v.idndb_cust_prod_map,
                                    text: v.cust_name
                                }));

                            });
                            $("#loading-mask").hide();
                        }
                    });
                }
            });


            $("#PDCBulkUploadFileCWData").change(function () {
                if (this.checked) {
                    $('#pdc_branch_ac_officer').val("");
                    $("#loading-mask").show();
                    $("#PDCBulkUploadFileRFData").prop('checked', false);
                    var paramid = "get_rf_sellers";
                    var values = "&programe_type=CW&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {


                            $('#idndb_customer_define_seller_id')
                                    .empty()
                                    .append('<option selected="selected" value="Select The Seller">Select The Seller</option>')

                            $('#idndb_customer_define_seller_name')
                                    .empty()
                                    .append('<option selected="selected" value="Select The Seller">Select The Seller</option>')




                            $.each(data, function (k, v) {




                                $('#idndb_customer_define_seller_id').append($("<option/>", {
                                    value: v.idndb_cust_prod_map,
                                    text: v.cust_id
                                }));


                                $('#idndb_customer_define_seller_name').append($("<option/>", {
                                    value: v.idndb_cust_prod_map,
                                    text: v.cust_name
                                }));

                            });
                            $("#loading-mask").hide();
                        }
                    });
                }
            });




            $("#idndb_customer_define_seller_id").change(function () {

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);

                if (!(idndb_customer_define_seller_id === 'Select The Seller')) {
                    getSellerBranchAcOfficer(idndb_customer_define_seller_id);
                    $('#idndb_customer_define_seller_id').css('border-color', '');
                    $('#idndb_customer_define_seller_name').css('border-color', '');

                } else {

                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the seller. !", "error");
                }



            });


            $("#idndb_customer_define_seller_name").change(function () {

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_name").val();
                $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_id);

                if (!(idndb_customer_define_seller_id === 'Select The Seller')) {

                    $('#idndb_customer_define_seller_id').css('border-color', '');
                    $('#idndb_customer_define_seller_name').css('border-color', '');

                    getSellerBranchAcOfficer(idndb_customer_define_seller_id);

                } else {

                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the seller. !", "error");
                }


            });

            function getSellerBranchAcOfficer(idndb_cust_prod_map_seller) {
                var paramid = "getSellerBranchAcOfficer";
                var values = "&idndb_cust_prod_map_seller=" + idndb_cust_prod_map_seller +
                        "&paramid=" + paramid;

                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/txn_data_retrive",
                    data: values,
                    dataType: "json",
                    success: function (data) {


                        $.each(data, function (k, v) {

                            $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc);

                        });

                    }
                });
            }
            ;


        });

        function validateInput() {


            var hasError = true;
            var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
            var PDCBulkUploadFileData = $("#PDCBulkUploadFileData").val();


            var req_for_financing = ''
            if ($("#PDCBulkUploadFileRFData").prop('checked')) {
                req_for_financing = "RF"
            }

            if ($("#PDCBulkUploadFileCWData").prop('checked')) {
                req_for_financing = "CW"
            }

            if (req_for_financing === '') {

                swal("Error!", "Please select the product type !", "error");
                $("#loading-mask").hide();
                return false;

            }


            if (idndb_customer_define_seller_id === 'Select The Seller') {
                $('#idndb_customer_define_seller_id').css('border-color', 'red');
                $('#idndb_customer_define_seller_name').css('border-color', 'red');
                $("#loading-mask").hide();
                return false;
            }

            if (PDCBulkUploadFileData === '') {

                swal("Error!", "Please choose a file for upload !", "error");
                $("#loading-mask").hide();
                return false;
            }
            $("#loading-mask").show();
            $("#btn_upld").prop("disabled", true);
            return hasError;
        }

        function ValidateExtension() {
            var allowedFiles = [".xlsx"];
            var fileUpload = document.getElementById("form-field-file-input");
            var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");
            if (!regex.test(fileUpload.value.toLowerCase())) {
                swal("Invalid Input!", "Invalid input file format. .xlsx only allowed !", "error");
                return false;
            }
            return true;
        }



    </script>
    <% UserBean user = (UserBean) (session.getAttribute("user"));%> 

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
                                String user_type = user.getCode();

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
            <div class="loader" id="loading-mask">                            
                <div class="loader_logo centered"  >
                </div>
            </div>             
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        PDC File Upload
                        <small>PDC Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Transactions</a></li>
                        <li class="active">PDC File Upload</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal" method="POST" id="form1"  action="/NDBRMS/PDCBukUpload" enctype="multipart/form-data" style="padding-left: 20px" onsubmit="return validateInput()">
                                    <div class="box-body">
                                        <input type="hidden" class="form-control" id="paramid" name="paramid" value="pdc_file_upld">

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Product</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="PDCBulkUploadFileRFData" name="PDCBulkUploadFileRFData" value="ACTIVE">Receivable Finance
                                                <input type="checkbox" id="PDCBulkUploadFileCWData" name="PDCBulkUploadFileCWData" value="ACTIVE">Cheque Warehousing
                                            </div>
                                        </div> 

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_id" name="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <%
                                                        //  out.print(new FillDataComboBean().getCust_Id_ActiveSellerDataCmb(session.getAttribute("userid").toString()));

                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller Name</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_name" name="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <%                                                    //out.print(new FillDataComboBean().getCust_Name_ActiveSellerDataCmb(session.getAttribute("userid").toString()));
//
%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Branch / A/C Officer</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="pdc_branch_ac_officer" name="pdc_branch_ac_officer" style="width: 350px" >
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">PDC Bulk File input</label>

                                            <div class="col-sm-10">
                                                <input type="file" id="PDCBulkUploadFileData" name="PDCBulkUploadFileData">
                                                <p class="help-block">Pick the PDC Bulk file for upload</p>
                                            </div>
                                        </div>



                                        <!-- /.box-body -->
                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-info pull-right" id="btn_cancel" style="width: 100px">Cancel</button>
                                            <button type="submit" class="btn btn-info pull-right" id="btn_upld" style="width: 100px;margin-right: 0.5%;">Upload</button>
                                        </div><!-- /.box-footer -->
                                    </div>
                                </form>
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
        </aside><!-- /.control-sidebar --><!-- /.control-sidebar -->
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
