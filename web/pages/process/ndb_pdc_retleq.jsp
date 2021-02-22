<%@page import="DBOops.MenuDAO"%>
<%@page import="DBAutoFillBeans.comboDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Return Liq</title>
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

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
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

    <script>
        <%
            boolean _session_availability = true;
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }

            MenuDAO menuDAO = new MenuDAO();

            comboDAO cmbDAO = new comboDAO();
            String _system_date = cmbDAO.getSystemDate();
            String m_next_system_date = "";
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
            ced.add(Calendar.DATE, 1);
            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));


        %>

        $(function () {

            window.onload = function () {
                $("#today").prop('readonly', true);
                $("#loading-mask").hide();

            }


            $('#btn_cancel').click(function () {
                location.reload();
            });

            $("#btn_rtn_liqd_process").click(function (e) {
                $("#loading-mask").show();
                $("#btn_rtn_liqd_process").prop("disabled", true);

                //alert("ok");
                var paramid = "mstr_run_rtn_liqd_process";
                var values = "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/DayEndProcess",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        if (data.success) {
                            window.location = "modalsSucess.jsp?desigURL=ndb_pdc_retleq.jsp&message=Return / Additional day process proceed successfully !.";

                        } else if (data.error) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=Error occurred in return lequidation process. !";

                        } else if (data.errorret) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.errorret + "";

                        } else if (data.erroradd) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.erroradd + "";

                        } else if (data.errorfcbc) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.errorfcbc + "";

                        } else if (data.errorhm) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.errorhm + "";

                        } else if (data.errorret) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + errorret + "";

                        } else if (data.errorad) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.errorad + "";

                        } else if (data.erroruath) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.erroruath + "";

                        } else if (data.errordbbackup) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_retleq.jsp&message=" + data.errordbbackup + "";

                        }
                    }
                });

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
            <div class="loader" id="loading-mask">                            
                <div class="loader_logo centered"  >
                </div>
            </div> 
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        Return / Liquidation Process 
                        <small>Return / Liquidation Process </small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Tables</a></li>
                        <li class="active">Data tables</li>
                    </ol>
                </section>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal">
                                    <div class="box-body">

                                        <div class="form-group" style="margin-left: 50px;">
                                            <label class="col-sm-2 control-label" style="margin-left: -80px;   ">Today</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask id="today" style="width: 325px" value="<%=_system_date%>">
                                            </div><!-- /.input group -->
                                        </div><!-- /.form group -->


                                        <!--   <p><code>in .progress</code></p>
                                         <div class="progress">
                                             <div class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                                 <span class="sr-only">50% Complete (success)</span>
                                             </div>
                                         </div> -->


                                    </div><!-- /.box-body -->


                                </form>
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_rtn_liqd_process" style="margin-right: 5px">Process</button>
                                </div><!-- /.box-footer -->
                            </div><!-- /.box -->


                        </div><!-- /.box -->

                    </div>


                </section>

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
