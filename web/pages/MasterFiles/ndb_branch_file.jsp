<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Branch Master</title>
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
        <script src="${pageContext.request.contextPath}/bootstrap/js/sweetalert.min.js"></script>


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
        <% if (user_type.equals("12") || user_type.equals("9") || user_type.equals("11")) { %>
                $("#branch_id").prop('readonly', true);
                $("#branch_name").prop('readonly', true);
                document.getElementById("bnkcodecmb").disabled = true;
                document.getElementById("bnkcodecmb_name").disabled = true;
                document.getElementById("ch_branch_status").disabled = true;
                document.getElementById("btn_branch_save").disabled = true;


        <%}  %>

                $("#ch_branch_status").prop('checked', true);
                $("#loading-mask").hide();

            };



            $("#branch_id").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }

            });

            $("#branch_name").keyup(function () {
                if (this.value.match(/[^a-zA-Z0-9 ]/g)) {
                    this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
                }
            });



            $("#btn_branch_save").click(function (e) {


                if (validateInput()) {
                    $("#loading-mask").show();
                    $("#btn_branch_save").prop("disabled", true);

                    var paramid = "msrt_save_branch_data";
                    var values = "&bnkcodecmb=" + $("#bnkcodecmb").val().trim() +
                            "&branch_id=" + $("#branch_id").val().trim() +
                            "&branch_name=" + $("#branch_name").val().trim() +
                            "&idndb_branch_master_file=" + $("#idndb_branch_master_file").val().trim() +
                            "&ch_branch_status=" + ($("#ch_branch_status").prop('checked') ? "ACTIVE" : "DEACTIVE") + "&paramid=" + paramid;

                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/MasterServletData",
                        data: values,
                        dataType: "json", //if received a response from the server
                        success: function (data) {
                            if (data.success) {
                                window.location = "modalsSucess.jsp?desigURL=ndb_branch_file.jsp&message=Branch data saved to DB successfuly and sent for authorization.";

                            } else if (data.alreadyexdata) {
                                window.location = "modalsError.jsp?desigURL=ndb_branch_file.jsp&message=Error occured in saving branch data. Branch Code allready exist.";

                            } else if (data.systemna) {
                                window.location = "modalsError.jsp?desigURL=ndb_branch_file.jsp&message=" + data.systemna + "";

                            } else if (data.error) {
                                window.location = "modalsError.jsp?desigURL=ndb_branch_file.jsp&message=Error occured in saving branch data.";

                            }
                        }
                    });
                }

            });

            $('#btn_branch_clear').click(function () {
                location.reload();
            });


            $("#bnkcodecmb").change(function () {

                var bnkcodecmb_bank_id = $("#bnkcodecmb").val();
                $('#bnkcodecmb_name').val(bnkcodecmb_bank_id);



            });


            $("#bnkcodecmb_name").change(function () {

                var bnkcodecmb_bank_id = $("#bnkcodecmb_name").val();
                $('#bnkcodecmb').val(bnkcodecmb_bank_id);



            });


            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "rgb(154, 154, 168)");
                pickedup = $(this);

                var idndb_branch_master_file = $(this).find("td").eq(0).html();
                var paramid = "get_branch_data_tofill";
                var values = "&idndb_branch_master_file=" + idndb_branch_master_file +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {
                            $('#bnkcodecmb').val(v.idndb_bank_master_file);
                            $('#bnkcodecmb_name').val(v.idndb_bank_master_file);
                            $("#idndb_branch_master_file").val(v.idndb_branch_master_file);
                            $("#branch_id").val(v.branch_id);
                            $("#branch_name").val(v.branch_name);

                            if (v.branch_status === 'ACTIVE') {
                                $("#ch_branch_status").prop('checked', true);
                            } else {

                                $("#ch_branch_status").prop('checked', false);
                            }
                        });
                    }
                });
            });


            $('#example1').dataTable({
                "aLengthMenu": [10, 25, 50, 100, 500, 20000]
            });




            function validateInput() {

                var hasError = true;
                var bnkcodecmb = $("#bnkcodecmb").val();
                var bnkcodecmb_name = $("#bnkcodecmb_name").val();
                var branch_id = $("#branch_id").val();
                var branch_name = $("#branch_name").val();

                if (bnkcodecmb_name === 'Select The Bank Name') {
                    $('#bnkcodecmb_name').css('border-color', 'red');
                    return false;
                }
                if (bnkcodecmb === 'Select The Bank Code') {
                    $('#bnkcodecmb').css('border-color', 'red');
                    return false;
                }
                if (branch_id === '') {
                    $('#branch_id').css('border-color', 'red');
                    return false;
                }
                if (branch_name === '') {
                    $('#branch_name').css('border-color', 'red');
                    return false;
                }

                return hasError;
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
            <div class="loader" id="loading-mask">                            
                <div class="loader_logo centered"  >
                </div>
            </div> 
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        Branch Master File
                        <small>Branch Data</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Branch File</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal" id="branch_data" name="branch_data">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Bank Code</label>
                                            <div class="col-sm-10">

                                                <select id="bnkcodecmb" class="form-control" style="width: 350px;" >
                                                    <option>Select The Bank Code</option>
                                                    <% if (_session_availability) {

                                                            out.print(new FillDataComboBean().getFileBankCodeDataCmb(session.getAttribute("userid").toString()));

                                                        }


                                                    %>

                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Bank Name</label>
                                            <div class="col-sm-10">

                                                <select class="form-control" id="bnkcodecmb_name" style="width: 350px;">
                                                    <option>Select The Bank Name</option>    
                                                    <%  if (_session_availability) {

                                                            out.print(new FillDataComboBean().getFileBankNameDataCmb(session.getAttribute("userid").toString()));

                                                        }

                                                    %>


                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Branch ID</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="branch_id" name="branch_id" style="width: 350px" maxlength="3">
                                                <input type="hidden" class="form-control" id="idndb_branch_master_file" name="idndb_branch_master_file" maxlength="4" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Branch Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="branch_name" name="branch_name" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Branch Status</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="ch_branch_status">Click to Active
                                            </div>
                                        </div>

                                    </div><!-- /.box -->
                                </form>

                            </div>
                            <div class="box box-info">                   
                                <div class="box-footer">
                                    <a href="../MasterFiles/ndb_branch_file.jsp?table_plus=true" class="btn btn-default btn-flat">View Branch Data</a>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_branch_clear">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_branch_save" name="btn_branch_save" style="margin-right: 5px">Submit</button>
                                </div><!-- /.box-footer -->
                            </div>
                        </div>
                    </div>


                </section>

                <!-- Main content -->
                <%                     String id_table_plus = request.getParameter("table_plus");
                    if (!(id_table_plus == null)) {


                %>
                <section class="content">


                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Branch Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" style="cursor: pointer;" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Bank Name</th>
                                                <th>Branch ID</th>
                                                <th>Branch Name</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getuBranchData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Bank Name</th>
                                                <th>Branch ID</th>
                                                <th>Branch Name</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div><!-- /.col -->
                </section><!-- /.content -->
                <%
                    }
                %>
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
        </aside>
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
