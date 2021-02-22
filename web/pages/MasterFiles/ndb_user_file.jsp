<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | User Master</title>
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
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }
            MenuDAO menuDAO = new MenuDAO();
        %>
        $(function() {


            $("#btn_user_save").click(function(e) {


                if (validateInput()) {

                    var paramid = "msrt_save_user_data";
                    var values = "&idndb_user_master=" + $("#idndb_user_master").val().trim() +
                            "&ndb_user_user_id=" + $("#ndb_user_user_id").val().trim() +
                            "&ndb_user_name=" + $("#ndb_user_name").val().trim() +
                            "&ndb_user_department=" + $("#ndb_user_department").val().trim() +
                            "&idndb_user_levels=" + $("#idndb_user_levels").val().trim() +
                            "&ndb_user_status=" + ($("#ndb_user_status").prop('checked') ? "ACTIVE" : "DEACTIVE") + 
                            "&ndb_user_rms=" + ($("#ndb_user_rms").prop('checked') ? "ACTIVE" : "DEACTIVE") + 
                            "&ndb_user_poms=" + ($("#ndb_user_poms").prop('checked') ? "ACTIVE" : "DEACTIVE") + 
                            "&ndb_user_um=" + ($("#ndb_user_um").prop('checked') ? "ACTIVE" : "DEACTIVE") + 
                            "&paramid=" + paramid;

                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/MasterServletData",
                        data: values,
                        dataType: "json", //if received a response from the server
                        success: function(data) {
                            if (data.success) {
                                window.location = "modalsSucess.jsp?desigURL=ndb_user_file.jsp&message=User data saved to DB successfuly .";

                            } else if (data.error) {
                                window.location = "modalsError.jsp?desigURL=ndb_user_file.jsp&message=Error occured in saving user data."

                            }
                        }
                    });
                }

            });




            $("#example1 tbody tr").on("click", function(event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "rgb(154, 154, 168)");
                pickedup = $(this);

                var idndb_user_master = $(this).find("td").eq(0).html();
                var paramid = "get_user_data_tofill";
                var values = "&idndb_user_master=" + idndb_user_master +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function(data) {
                        $.each(data, function(k, v) {
                            $('#idndb_user_master').val(v.idndb_user_master);
                            $('#ndb_user_user_id').val(v.ndb_user_user_id);
                            $('#ndb_user_name').val(v.ndb_user_name);
                            $("#ndb_user_department").val(v.ndb_user_department);
                            $("#idndb_user_levels").val(v.idndb_user_levels);

                            if (v.ndb_user_status === 'ACTIVE') {
                                $("#ndb_user_status").prop('checked', true);
                            }
                            if (v.ndb_user_rms === 'ACTIVE') {
                                $("#ndb_user_rms").prop('checked', true);
                            }
                            if (v.ndb_user_poms === 'ACTIVE') {
                                $("#ndb_user_poms").prop('checked', true);
                            }
                            if (v.ndb_user_um === 'ACTIVE') {
                                $("#ndb_user_um").prop('checked', true);
                            }
                            
                            
                            
                        });
                    }
                });
            });







            function validateInput() {

                var hasError = true;
                var ndb_user_user_id = $("#ndb_user_user_id").val();
                var ndb_user_name = $("#ndb_user_name").val();
                var ndb_user_department = $("#ndb_user_department").val();
                var idndb_user_levels = $("#idndb_user_levels").val();

                if (ndb_user_name === '') {
                    $('#ndb_user_name').css('border-color', 'red');
                    return false;
                }
                if (ndb_user_user_id === '') {
                    $('#ndb_user_user_id').css('border-color', 'red');
                    return false;
                }
                if (ndb_user_department === '') {
                    $('#ndb_user_department').css('border-color', 'red');
                    return false;
                }
                if (idndb_user_levels === 'Select User Level') {
                    $('#idndb_user_levels').css('border-color', 'red');
                    return false;
                }

                return hasError;
            }


        });
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
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        User Master File
                        <small>User Data</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">User Management</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">User Master File</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal" id="branch_data" name="branch_data">
                                    <div class="box-body">
                                       
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User ID</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="ndb_user_user_id" name="ndb_user_user_id" style="width: 350px">
                                                <input type="hidden" class="form-control" id="idndb_user_master" name="idndb_user_master" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="ndb_user_name" name="ndb_user_name" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User Department</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="ndb_user_department" name="ndb_user_department" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User Privileged System</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="ndb_user_rms">RMS System 
                                                <input type="checkbox" id="ndb_user_poms">POMS System
                                                <input type="checkbox" id="ndb_user_um">RMS & POMS User Management
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-2 control-label">User Level</label>
                                            <div class="col-sm-10">

                                                <select class="form-control" id="idndb_user_levels" style="width: 350px;">
                                                    <option>Select User Level</option>    
                                                    <%                                                        out.print(new FillDataComboBean().getFileUserLevelsDataCmb(session.getAttribute("userid").toString()));

                                                    %>


                                                </select>
                                            </div>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">User Status</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="ndb_user_status">Click to Active
                                            </div>
                                        </div>
                                        

                                    </div><!-- /.box -->
                                </form>

                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_user_save" name="btn_branch_save">Submit</button>
                                </div><!-- /.box-footer -->
                            </div>
                        </div>
                    </div>


                </section>

                <!-- Main content -->
                <section class="content">


                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">User Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" style="cursor: pointer;" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>User ID</th>
                                                <th>User Name</th>
                                                <th>User Department</th>
                                                <th>Privileged Systems</th>
                                                <th>User Level</th>
                                                <th>User Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%                                                            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                                                    response.sendRedirect("/NDBRMS/pages/MasterFiles/lockscreen.jsp");
                                                } else {

                                                    out.print(new FIllDataTableBean().getUserData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>User ID</th>
                                                <th>User Name</th>
                                                <th>User Department</th>
                                                <th>User Level</th>
                                                <th>User Status</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div><!-- /.col -->
                </section><!-- /.content -->
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
        $(function() {
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
