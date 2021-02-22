<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Modals Warning</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">
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

        <style>
            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }
            .example-modal .modal {
                background: transparent !important;
            }
        </style>
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


            $("#button_close").click(function (e) {
                var desigURL = $("#desig_url").val();

                window.location = "" + desigURL + ""

            });



            $("#fr_pdc_save_changes").click(function (e) {

                document.getElementById("fr_pdc_save_changes").disabled = true;

                var paramid = "save_rf_pdc_manual_entry";
                var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                        "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                        "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                        "&rf_chq_number=" + $("#rf_chq_number").val().trim() +
                        "&cust_bank=" + $("#cust_bank").val().trim() +
                        "&cust_bank_code=" + $('#cust_bank_code').text().trim() +
                        "&cust_branch=" + $("#cust_branch").val().trim() +
                        "&cust_branch_code=" + $('#cust_branch_code').text().trim() +
                        "&rf_value_date=" + $("#rf_value_date").val().trim() +
                        "&rf_cheque_amu=" + $("#rf_cheque_amu").val().trim() +
                        "&message=" + $("#message").val().trim() +
                        "&rf_cheque_modification=" + $("#rf_cheque_modification").val().trim() +
                        "&paramid=" + paramid;


                $.ajax({

                    type: "POST",
                    url: "/NDBRMS/PDCentries",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {

                        if (data.success) {
                            var rec_data = data.success.split('/');
                            var id_seller = rec_data[1];
                            window.location = "modalsSucess.jsp?desigURL=ndb_pdc_rf_manual.jsp&message=PDC receviable finance entry data saved to System successfuly and sent for authorization.&id_seller=" + id_seller + "";

                        } else if (data.systemna) {
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_rf_manual.jsp&message=" + data.systemna + "";

                        } else if (data.cn_error) {
                            var rec_data = data.cn_error.split('/');
                            var id_seller = rec_data[1];
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_rf_manual.jsp&message=" + rec_data[0] + "&id_seller=" + id_seller + "";

                        } else if (data.error) {
                            var rec_data = data.error.split('/');
                            var id_seller = rec_data[1];
                            window.location = "modalsError.jsp?desigURL=ndb_pdc_rf_manual.jsp&message=Error occured in saving PDC receviable finance entry !&id_seller=" + id_seller + "";

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
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Message
                        <small>new</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">UI</a></li>
                        <li class="active">Modals</li>
                    </ol>
                </section>

                <!-- Main content -->

                <section class="content">

                    <div class="example-modal">
                        <div class="modal modal-warning">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title">Override message</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p><%= request.getParameter("message")%></br><%= request.getParameter("action")%></p>
                                    </div>
                                    <div class="modal-footer"><input type="hidden" name="desig_url" id="desig_url" value="<%= request.getParameter("desigURL")%>" />
                                        <input type="hidden" name="desig_url" id="idndb_customer_define_seller_id" value="<%= request.getParameter("idndb_customer_define_seller_id")%>" />
                                        <input type="hidden" name="desig_url" id="idndb_customer_define_buyer_id" value="<%= request.getParameter("idndb_customer_define_buyer_id")%>" />
                                        <input type="hidden" name="desig_url" id="idndb_pdc_txn_master" value="<%= request.getParameter("idndb_pdc_txn_master")%>" />
                                        <input type="hidden" name="desig_url" id="rf_chq_number" value="<%= request.getParameter("rf_chq_number")%>" />
                                        <input type="hidden" name="desig_url" id="cust_bank" value="<%= request.getParameter("cust_bank")%>" />
                                        <input type="hidden" name="desig_url" id="cust_bank_code" value="<%= request.getParameter("cust_bank_code")%>" />
                                        <input type="hidden" name="desig_url" id="cust_branch" value="<%= request.getParameter("cust_branch")%>" />
                                        <input type="hidden" name="desig_url" id="cust_branch_code" value="<%= request.getParameter("cust_branch_code")%>" />
                                        <input type="hidden" name="desig_url" id="rf_value_date" value="<%= request.getParameter("rf_value_date")%>" />
                                        <input type="hidden" name="desig_url" id="rf_cheque_amu" value="<%= request.getParameter("rf_cheque_amu")%>" />
                                        <input type="hidden" name="desig_url" id="rf_cheque_modification" value="<%= request.getParameter("rf_cheque_modification")%>" />
                                        <input type="hidden" name="desig_url" id="paramid" value="<%= request.getParameter("paramid")%>" />
                                        <input type="hidden" name="desig_url" id="message" value="<%= request.getParameter("message")%>" />
                                        <button type="button" class="btn btn-outline pull-left" data-dismiss="modal" id="button_close" >Close</button>
                                        <button type="button" class="btn btn-outline" id="fr_pdc_save_changes">Save changes</button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                    </div><!-- /.example-modal -->

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
            </aside><!-- /.control-sidebar --><!-- /.control-sidebar -->
            <!-- Add the sidebar's background. This div must be placed
                 immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
        </div><!-- ./wrapper -->

        <!-- jQuery 2.1.4 -->
        <script src="../../plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="../../bootstrap/js/bootstrap.min.js"></script>
        <!-- FastClick -->
        <script src="../../plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="../../dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="../../dist/js/demo.js"></script>
    </body>
</html>
