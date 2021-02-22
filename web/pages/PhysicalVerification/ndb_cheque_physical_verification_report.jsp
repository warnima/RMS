<%@page import="DBAutoFillBean.TableDAO"%>
<%@page import="DBOops.MenuDAO"%>
<%@page import="DBAutoFillBeans.comboDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | physical Verification Report</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
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
            comboDAO cmbDAO = new comboDAO();
            TableDAO tbldao = new TableDAO();
            String _system_date = cmbDAO.getSystemDate();

            MenuDAO menuDAO = new MenuDAO();
        %>


        $(function () {
            window.onload = function () {
                $("#verification_date").prop('readonly', true);
                $("#physical_verification_date").prop('readonly', true);
                $("#portfolio_balance_as_at").prop('readonly', true);
                $("#no_of_cheques").prop('readonly', true);
                $("#total_cheque_amount").prop('readonly', true);
                $("#verified_no_of_cheques").prop('readonly', true);
                $("#not_verified_no_of_cheques").prop('readonly', true);
            };

            $('#example2').dataTable({
                "aLengthMenu": [10, 25, 50, 100, 500, 20000]
            });

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
                        Physical Verification Dashboard & Reports
                        <small>Physical Verification Dashboard & Reports</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Physical Verification</a></li>
                        <li class="active">Physical Verification Dashboard & Reports</li>
                    </ol>
                </section>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal" id="bank_form" name="bank_form"> 
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Physical Verification Date</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="physical_verification_date" name="physical_verification_date" style="width: 350px" value="<%=_system_date%>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Portfolio Balance as at</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="portfolio_balance_as_at" name="portfolio_balance_as_at" style="width: 350px" value="<%=_system_date%>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">No of Cheques</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="no_of_cheques" name="no_of_cheques" style="width: 350px" value="<%=tbldao.noOfChequesPortfolioReport(_system_date)%>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Total Cheque Amount</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="total_cheque_amount" name="total_cheque_amount" style="width: 350px" value="<%=tbldao.chequeAmountChequesPortfolioReport(_system_date)%>">
                                            </div>
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Verified No of Cheques</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="verified_no_of_cheques" name="verified_no_of_cheques" style="width: 350px" value="<%=tbldao.noOfChequesVerified(_system_date)%>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Not Verified No of Cheques</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="not_verified_no_of_cheques" name="not_verified_no_of_cheques" style="width: 350px" value="<%=tbldao.noOfChequesNotVerified(_system_date)%>">
                                            </div>
                                        </div>    
                                    </div><!-- /.box-body -->

                                </form>
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div>


                </section>                             


                <!-- Main content -->
                <section class="content">


                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Physical Verification Summary</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>User Name</th>
                                                <th>No of Cheques Verified</th>
                                                <th>Amount</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%                                                if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getPDCVerificationSummeryData(_system_date));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>#</th>
                                                <th>User Name</th>
                                                <th>No of Cheques Verified</th>
                                                <th>Amount</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div><!-- /.col -->

                </section>
                <section class="content">

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Physical Verification Summary Report</h3>
                                </div>
                                <form class="form-horizontal" action="/NDBRMS/downloadChequeVerficationReport" onsubmit="return validateInput()">
                                    <div class="box-body">


                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Physical Verification Date</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" id="verification_date" name="verification_date" data-mask style="width: 325px" value="<%=_system_date%>">

                                            </div><!-- /.input group -->
                                        </div><!-- /.form group -->

                                    </div><!-- /.box-body -->
                                    <div class="box-footer">
                                        <button type="submit" class="btn btn-info pull-right" id="down_slips" onclick="validateInput()">Download Report</button>
                                        <button type="button" class="btn btn-info pull-right" id="btn_cancel">Cancel</button>

                                    </div>
                                    <!-- /.box-footer -->

                                </form>

                            </div><!-- /.box -->



                        </div><!-- /.box -->
                    </div>


                </section>                        
                <!-- /.content -->
                <!--                <section class="content">
                
                
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="box">
                                                <div class="box-header">
                                                    <h3 class="box-title">Not Verified Cheque details </h3>
                                                </div> /.box-header 
                                                <div class="box-body" style="overflow-y: auto;">
                                                    <table id="example2" class="table table-bordered table-striped" style="cursor: pointer;">
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Seller Name</th>
                                                                <th>Cheque No</th>
                                                                <th>Bank Code</th>
                                                                <th>Branch Code</th>
                                                                <th>Value Date</th>
                                                                <th>Amount</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                <%                                                if (_session_availability) {

//                                                    out.print(new FIllDataTableBean().getPDCNotVerifiedChequeSummeryData(_system_date));
                    }
                %>
            </tbody>
            <tfoot>
                <tr>
                     <th>#</th>
                    <th>Seller Name</th>
                    <th>Cheque No</th>
                    <th>Bank Code</th>
                    <th>Branch Code</th>
                    <th>Value Date</th>
                    <th>Amount</th>
                </tr>
            </tfoot>
        </table>
    </div> /.box-body 
</div> /.box 


</div> /.box 
</div> /.col 

</section>-->

            </div>
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
                                                $('#example2').DataTable();
                                            });
    </script>
</body>
</html>
