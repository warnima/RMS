<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Holiday Manual Marker Master</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="../../bootstrap/css/style.css">
        <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">
        <!-- daterange picker -->
        <link rel="stylesheet" href="../../plugins/daterangepicker/daterangepicker-bs3.css">
        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="../../plugins/iCheck/all.css">
        <link rel="stylesheet" href="../../plugins/datatables/dataTables.bootstrap.css">
        <!-- Bootstrap Color Picker -->
        <link rel="stylesheet" href="../../plugins/colorpicker/bootstrap-colorpicker.min.css">
        <!-- Bootstrap time Picker -->
        <link rel="stylesheet" href="../../plugins/timepicker/bootstrap-timepicker.min.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="../../plugins/select2/select2.min.css">
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
        <% if (user_type.equals("12") || user_type.equals("9")) { %>
                document.getElementById("ndb_holiday").disabled = true;
                document.getElementById("ndb_holiday_status").disabled = true;
                document.getElementById("btn_holiday_save").disabled = true;


        <%}  %>

                $("#ndb_holiday_status").prop('checked', true);
                $("#loading-mask").hide();

            };
            $("#btn_holiday_save").click(function (e) {


                if (validateInput()) {
                    
                    $("#loading-mask").show();

                    $("#btn_holiday_save").prop("disabled", true);


                    var paramid = "msrt_save_holiday_data";
                    var values = "&idndb_holiday_marker=" + $("#idndb_holiday_marker").val().trim() +
                            "&ndb_holiday=" + $("#ndb_holiday").val().trim() +
                            "&ndb_holiday_status=" + ($("#ndb_holiday_status").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/MasterServletData",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            if (data.success) {
                                window.location = "modalsSucess.jsp?desigURL=ndb_holiday_marker.jsp&message=Holiday marked data saved to DB successfuly and sent for authorization.";

                            } else if (data.systemna) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=" + data.systemna + "";

                            } else if (data.alreadyexdata) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=Error occured in saving holiday marked data. Holiday already marked for this date.";

                            } else if (data.backdatedata) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=Error occured in saving holiday marked data. Holiday is a marked as back date.";

                            } else if (data.holidayToday) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=Error occured in saving holiday marked data. Holiday cannot be marked as today.";

                            } else if (data.holidayWeekend) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=Error occured in saving holiday marked data. Holiday cannot be marked as Weekend.";

                            } else if (data.error) {
                                window.location = "modalsError.jsp?desigURL=ndb_holiday_marker.jsp&message=Error occured in saving holiday marked data.";

                            }
                        }
                    });
                }

            });

            $('#btn_holiday_clear').click(function () {
                location.reload();
            });

            $("#bankTable").click(function () {
                var serviceID = $(this).attr('id');
                //  alert("serviceID :: " + serviceID);

            });

            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "rgb(154, 154, 168)");
                pickedup = $(this);
                var idndb_holiday_marker = $(this).find("td").eq(0).html();


                var paramid = "get_holiday_data_tofill";
                var values = "&idndb_holiday_marker=" + idndb_holiday_marker +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {
                            document.getElementById("ndb_holiday").disabled = true;

                            $("#idndb_holiday_marker").val(v.idndb_holiday_marker);
                            $("#ndb_holiday").val(v.ndb_holiday);

                            if (v.ndb_holiday_status === 'ACTIVE') {
                                $("#ndb_holiday_status").prop('checked', true);
                            } else {
                                $("#ndb_holiday_status").prop('checked', false);

                            }
                        });
                    }
                });
            });





            function validateInput() {


                var hasError = true;
                var ndb_holiday = $("#ndb_holiday").val();
                if (ndb_holiday === '') {
                    $('#ndb_holiday').css('border-color', 'red');
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
            <div class="loader" id="loading-mask">                            
                <div class="loader_logo centered"  >
                </div>
            </div> 
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Holiday Marker
                        <small>Holiday Data</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Holiday Marker</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">

                                <form class="form-horizontal">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Holiday</label>
                                            <input type="hidden" class="form-control" id="idndb_holiday_marker" name="idndb_holiday_marker" style="width: 350px">

                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask style="width: 350px" id="ndb_holiday">
                                            </div><!-- /.input group -->
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Holiday Status</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="ndb_holiday_status" name="ndb_holiday_status">Click to Active
                                            </div>
                                        </div>
                                    </div><!-- /.box-body -->
                                </form>

                            </div><!-- /.box -->
                            <div class="box box-info">
                                <div class="box-footer">
                                    <a href="../MasterFiles/ndb_holiday_marker.jsp?table_plus=true" class="btn btn-default btn-flat">View Holiday Data</a>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_holiday_clear" name="btn_holiday_clear">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_holiday_save" name="btn_holiday_save" style="margin-right: 5px">Submit</button>
                                </div><!-- /.box-footer -->
                            </div>
                        </div><!-- /.col (left) -->
                    </div><!-- /.row -->

                </section><!-- /.content -->
                <%                     String id_table_plus = request.getParameter("table_plus");
                    if (!(id_table_plus == null)) {


                %>
                <section class="content">


                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Holiday Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Holiday</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getHolidayData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Holiday</th>
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
        <!-- Select2 -->
        <script src="../../plugins/select2/select2.full.min.js"></script>
        <!-- InputMask -->
        <script src="../../plugins/input-mask/jquery.inputmask.js"></script>
        <script src="../../plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
        <script src="../../plugins/input-mask/jquery.inputmask.extensions.js"></script>
        <!-- date-range-picker -->
        <script src="../../bootstrap/js/moment.min.js"></script>
        <script src="../../plugins/daterangepicker/daterangepicker.js"></script>
        <!-- bootstrap color picker -->
        <script src="../../plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
        <!-- bootstrap time picker -->
        <script src="../../plugins/timepicker/bootstrap-timepicker.min.js"></script>
        <script src="../../plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="../../plugins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="../../plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- iCheck 1.0.1 -->
        <script src="../../plugins/iCheck/icheck.min.js"></script>
        <!-- FastClick -->
        <script src="../../plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="../../dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="../../dist/js/demo.js"></script>
        <!-- Page script -->
        <script>
        $(function () {
            //Initialize Select2 Elements
            $(".select2").select2();

            //Datemask dd/mm/yyyy
            $("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
            //Datemask2 mm/dd/yyyy
            $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
            //Money Euro
            $("[data-mask]").inputmask();

            //Date range picker
            $('#reservation').daterangepicker();
            //Date range picker with time picker
            $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
            //Date range as a button
            $('#daterange-btn').daterangepicker(
                    {
                        ranges: {
                            'Today': [moment(), moment()],
                            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                            'This Month': [moment().startOf('month'), moment().endOf('month')],
                            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                        },
                        startDate: moment().subtract(29, 'days'),
                        endDate: moment()
                    },
                    function (start, end) {
                        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                    }
            );

            //iCheck for checkbox and radio inputs
            $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            //Red color scheme for iCheck
            $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
                checkboxClass: 'icheckbox_minimal-red',
                radioClass: 'iradio_minimal-red'
            });
            //Flat red color scheme for iCheck
            $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
                checkboxClass: 'icheckbox_flat-green',
                radioClass: 'iradio_flat-green'
            });

            //Colorpicker
            $(".my-colorpicker1").colorpicker();
            //color picker with addon
            $(".my-colorpicker2").colorpicker();

            //Timepicker
            $(".timepicker").timepicker({
                showInputs: false
            });
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
