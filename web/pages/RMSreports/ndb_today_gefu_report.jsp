<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="DBAutoFillBeans.comboDAO"%>
<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.pdcDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBeans.FillDataComboBean"%>
<%@page import="DBAutoFillBeans.FIllDataTableBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Today GEGU Report</title>
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
        <script src="${pageContext.request.contextPath}/bootstrap/css/buttons.dataTables.min.css"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/css/jquery.dataTables.min.css"></script>
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

            String _system_date = new pdcDAO().getSystemDate();
            String _last_system_date = new pdcDAO().getSystemLastCOBDate();

            comboDAO cmbDAO = new comboDAO();
            String _back_system_date = "";
            MenuDAO menuDAO = new MenuDAO();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            // java.util.Date dt = new java.util.Date();
            // java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // m_Strcw_value_date = sdf.format(m_Strcw_value_date_temp);
            String[] date_spliter = _system_date.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]);
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);
            ced.add(Calendar.DATE, -1);
            _back_system_date = formatter.format(ced.getTime());
        %>

        $(function () {
            window.onload = function () {
                $("#loading-mask").hide();
                $("#div_report_table").hide();
            }

            $('#btn_cancel').click(function () {
                location.reload();
            });

            $('#btn_cancel').click(function () {
                location.reload();
            });
            $('#example1').dataTable({
                "bPaginate": false
            });

//            $("#proccess").click(function (e) {
//                //alert("values");
//                $("#loading-mask").show();
//                if (validateInput()) {
//                    var table = $("#example1").DataTable();
//                    table.clear().draw();
//
//                    var booked_date = $("#booked_date").val();
//
//                    var paramid = "get_today_gefu_data_report";
//                    var values = "&booked_date=" + booked_date + "&paramid=" + paramid;
//                    // alert(values);
//                    $.ajax({
//                        type: "POST",
//                        url: "/NDBRMS/txn_data_retrive",
//                        data: values,
//                        dataType: "json",
//                        //if received a response from the server
//                        success: function (data) {
//
//                            if ((Object.keys(data).length) === 0) {
//                                $("#loading-mask").hide();
//                                swal("No records !", "No records found for the given selection. !");
//                            } else {
//                                $("#div_report_table").show();
//                                $.each(data, function (k, v) {
//                                    table.row.add([v.seller, v.buyer, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_chq_amu, v.credit_debit, v.amount, v.narration]).draw();
//                                });
//                                $("#loading-mask").hide();
//
//                            }
//                        }
//                    });
//
//
//                }
//
//            });

            $("#proccess").click(function (e) {
                //alert("values");
                $("#loading-mask").show();
                if (validateInput()) {
                    var table = $("#example1").DataTable();
                    table.clear().draw();

                    var booked_date = $("#booked_date").val();

                    var paramid = "getGefuReport";
                    var values = "&gefu_date=" + booked_date + "&paramid=" + paramid;
                    // alert(values);
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/report_data_extract",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {

                            if ((Object.keys(data).length) === 0) {
                                $("#loading-mask").hide();
                                swal("No records !", "No records found for the given selection. !");
                            } else {
                                $("#div_report_table").show();
                                $.each(data, function (k, v) {
                                    table.row.add([v.seller, v.buyer, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_chq_amu, v.credit_debit, v.amount, v.narration]).draw();
                                });
                                $("#loading-mask").hide();

                            }
                        }
                    });


                }

            });



            $("#btnExport").click(function (e) {
                window.open('data:application/vnd.ms-excel,' + $('#dvData').html());
                e.preventDefault();
            });








        });


        function validateInput() {

            var hasError = true;
            var booked_date = $("#booked_date").val();


            if (booked_date === '') {

                $('#booked_date').css('border-color', 'red');
                swal("Error!", "GEFU Date cannot be empty. !", "error");
                $("#loading-mask").hide();
                return false;
            }

            return hasError;
        }

        function fnExcelReport() {
            var date = $("#booked_date").val();

            var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
            tab_text = tab_text + '<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>';

            tab_text = tab_text + '<x:Name>Test Sheet</x:Name>';

            tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
            tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';

            tab_text = tab_text + "<table border='1px'>";
            tab_text = tab_text + $('#example1').html();
            tab_text = tab_text + '</table></body></html>';


            var data_type = 'data:application/vnd.ms-excel';

            var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ");

            if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
                if (window.navigator.msSaveBlob) {
                    var blob = new Blob([tab_text], {
                        type: "application/csv;charset=utf-8;"
                    });
                    navigator.msSaveBlob(blob, date + 'GEFU_REPORT.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', date + 'GEFU_REPORT.xls');
            }

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
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Today GEFU Report
                        <small>Today GEFU Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Reports and Charts</a></li>
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
                                            <label class="col-sm-2 control-label">Date</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" id="booked_date" name="booked_date" data-mask style="width: 325px" value="<%=_last_system_date%>">

                                            </div><!-- /.input group -->
                                        </div><!-- /.form group -->

                                    </div><!-- /.box-body -->


                                </form>
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel">Cancel</button>
                                    <button type="button" class="btn btn-info pull-right" id="proccess" name="proccess" style="margin-right: 0.5%;">&nbsp;&nbsp;View&nbsp;&nbsp;</button>
                                </div>
                                <hr>
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div>


                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row" id="div_report_table">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Today GEFU Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" id="dvData" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Seller</th>
                                                <th>Buyer</th>
                                                <th>Chq. Number</th>
                                                <th>Bank Code</th>
                                                <th>Branch Code</th>
                                                <th>Chq Amu.</th>
                                                <th>CR / DR</th>
                                                <th>Amu.</th>
                                                <th>Narration</th>

                                            </tr>
                                        </thead>
                                        <tbody>


                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Seller</th>
                                                <th>Buyer</th>
                                                <th>Chq. Number</th>
                                                <th>Bank Code</th>
                                                <th>Branch Code</th>
                                                <th>Chq Amu.</th>
                                                <th>CR / DR</th>
                                                <th>Amu.</th>
                                                <th>Narration</th>

                                            </tr>
                                        </tfoot>
                                    </table>
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <a href="#" id="test" onClick="javascript:fnExcelReport();">Download Report</a>
                                </div>
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div><!-- /.col -->
                </section><!-- /.content -->
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
