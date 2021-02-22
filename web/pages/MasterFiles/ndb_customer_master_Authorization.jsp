<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Customer Master Auth</title>
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
            $('#btn_cancel').click(function () {
                location.reload();
            });

            window.onload = function () {


                $("#_ndb_customer").hide();
                $("#cust_ch_active").prop('checked', true);


                $("#cust_id").prop('readonly', true);
                $("#cust_name").prop('readonly', true);
                $("#cust_short_name").prop('readonly', true);
                $("#cust_contct_no").prop('readonly', true);
                $("#cust_fx_number").prop('readonly', true);
                $("#cust_contct_per1").prop('readonly', true);
                $("#cust_contct_per2").prop('readonly', true);
                $("#cust_email").prop('readonly', true);
                $("#cust_address").prop('readonly', true);
                $("#cust_rec_acc_no").prop('readonly', true);
                $("#cust_cre_des_pros_to_acc_no").prop('readonly', true);
                $("#cust_curr_ac_no").prop('readonly', true);
                $("#cms_curr_acc_number").prop('readonly', true);
                $("#cms_collection_acc_number").prop('readonly', true);
                $("#cust_margin_ac_no").prop('readonly', true);
                $("#cust_margin").prop('readonly', true);
                $("#cust_credt_rate").prop('readonly', true);
                $("#cust_other_bank_ac_no").prop('readonly', true);
                $("#statusReason").prop('readonly', true);
                document.getElementById("cust_ndb_customer_active").disabled = true;
                document.getElementById("cust_indutry_id").disabled = true;
                document.getElementById("cust_bank").disabled = true;
                document.getElementById("cust_branch").disabled = true;
                document.getElementById("cust_geo_market").disabled = true;
                document.getElementById("cust_ch_active").disabled = true;
                document.getElementById("statusReason").disabled = true;

                $("#loading-mask").hide();


            };
            $('#mactive').click(function (e) {
                $(this).closest('table').find('td input:checkbox').prop('checked', this.checked);
            });

            $("#cust_ndb_customer_active").change(function () {
                if (this.checked) {
                    $("#_ndb_customer").show();
                } else {
                    $("#_ndb_customer").hide();
                }
            });

            $("#cust_rec_acc_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cust_cre_des_pros_to_acc_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cust_curr_ac_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cust_margin_ac_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cust_margin").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#cust_other_bank_ac_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cust_contct_no").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/ /+]/g, '');
                }
            });
            $("#cust_fx_number").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/ /+]/g, '');
                }
            });


            $("#btn_cutomerdfine_auth").click(function (e) {

                $("#loading-mask").show();

                $('input[type="submit"]').prop('disabled', false);

                var checkValues = $('input[name=active]:checked').map(function ()
                {
                    return $(this).val();
                }).get();

                $("#btn_cutomerdfine_auth").prop("disabled", true);
                var paramid = "msrt_save_cust_define_auth_data";
                var values = "&idndb_customer_define=" + checkValues +
                        "&paramid=" + paramid;




                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerServletDate",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {

                        if (data.success) {
                            window.location = "modalsSucess.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=Customer define data authorized successfuly !";

                        } else if (data.systemna) {
                            window.location = "modalsError.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=" + data.systemna + "";

                        } else if (data.error) {
                            window.location = "modalsError.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=Error occured in authorizing customer define data.";

                        }
                    }
                });


            });


            $("#btn_cutomerdfine_rejected").click(function (e) {

                $("#loading-mask").show();

                $('input[type="submit"]').prop('disabled', false);

                var checkValues = $('input[name=active]:checked').map(function ()
                {
                    return $(this).val();
                }).get();

                $("#btn_cutomerdfine_rejected").prop("disabled", true);
                var paramid = "msrt_save_cust_define_reject_data";
                var values = "&idndb_customer_define=" + checkValues +
                        "&paramid=" + paramid;




                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerServletDate",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {

                        if (data.success) {
                            window.location = "modalsSucess.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=Customer defined data rejected successfuly !";

                        } else if (data.systemna) {
                            window.location = "modalsError.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=" + data.systemna + "";

                        } else if (data.error) {
                            window.location = "modalsError.jsp?desigURL=ndb_customer_master_Authorization.jsp&message=Error occured in rejecting customer defined data.";

                        }
                    }
                });


            });

            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "#3c8dbc");
                pickedup = $(this);

                var idndb_customer_define = $(this).find("td").eq(0).html();
                var paramid = "get_customer_data_tofill";
                var values = "&idndb_customer_define=" + idndb_customer_define +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {
                            $('#idndb_customer_define').val(v.idndb_customer_define);
                            $('#cust_id').val(v.cust_id);
                            $('#cust_name').val(v.cust_name);
                            $('#cust_short_name').val(v.cust_short_name);
                            $("#cust_indutry_id").val(v.cust_industry_id);
                            $("#cust_contct_no").val(v.cust_contact_number);
                            $("#cust_fx_number").val(v.cust_fax_number);
                            $("#cust_contct_per1").val(v.cust_contact_per1);
                            $("#cust_contct_per2").val(v.cust_contact_per2);
                            $("#cust_email").val(v.cust_email);
                            $("#cust_address").val(v.cust_address);
                            $("#cust_rec_acc_no").val(v.rec_finance_acc_num);
                            $("#cust_cre_des_pros_to_acc_no").val(v.rec_finance_cr_dsc_proc_acc_num);
                            $("#cust_curr_ac_no").val(v.rec_finance_curr_ac);
                            $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                            $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                            $("#cust_margin_ac_no").val(v.rec_finance_margin_ac);
                            $("#cust_margin").val(v.rec_finance_margin);
                            $("#cust_credt_rate").val(v.cust_credit_rate);
                            $("#cust_bank").val(v.idndb_bank_master_file);
                            $("#statusReason").val(v.statusReason);
                            loadBranches(v.idndb_branch_master_file);

                            $("#cust_branch").val(v.idndb_branch_master_file);
                            $("#cust_other_bank_ac_no").val(v.cust_other_bank_ac_no);
                            $("#cust_geo_market").val(v.idndb_geo_market_master_file);

                            if (v.cust_status === 'ACTIVE') {
                                $("#cust_ch_active").prop('checked', true);
                            } else {
                                $("#cust_ch_active").prop('checked', false);
                            }
                            if (v.cust_is_ndb_cust === 'ACTIVE') {
                                $("#cust_ndb_customer_active").prop('checked', true);
                                $("#cust_indutry_id").val(v.cust_industry_id);
                                $("#cust_geo_market").val(v.idndb_geo_market_master_file);
                                $("#_ndb_customer").show();
                            } else {
                                $("#cust_ndb_customer_active").prop('checked', false);
                                $("#_ndb_customer").hide();

                            }
                        });
                    }
                });
            });

            $("#edit_cust_data").click(function (e) {

                // alert("ok");
            });

            $("#bankTable").click(function () {
                var serviceID = $(this).attr('id');
                // alert("serviceID :: " + serviceID);

            });

            $("#cust_bank").change(function () {

                var bnkcodecmb_bank_id = $("#cust_bank").val();
                $('#bnkcodecmb_name').val(bnkcodecmb_bank_id);

                var paramid = "get_bank_brnch_data";
                var values = "&bnkcodecmb_bank_id=" + bnkcodecmb_bank_id +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $('#cust_branch')
                                .empty()
                                .append('<option selected="selected" value="whatever">Select The Branch</option>')



                        $.each(data, function (k, v) {


                            $('#cust_branch').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_name
                            }));

                        });
                    }
                });





            });


            function loadBranches(idndb_branch_master_file) {


                var bnkcodecmb_bank_id = $("#cust_bank").val();
                $('#bnkcodecmb_name').val(bnkcodecmb_bank_id);

                var paramid = "get_bank_brnch_data";
                var values = "&bnkcodecmb_bank_id=" + bnkcodecmb_bank_id +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {


                            $('#cust_branch').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_name
                            }));

                        });
                        $("#cust_branch").val(idndb_branch_master_file);
                    }
                });





            }


            function validateInput() {


                var hasError = true;
                var idndb_customer_define = $("#idndb_customer_define").val();
                var cust_id = $("#cust_id").val();
                var cust_name = $("#cust_name").val();
                var cust_indutry_id = $("#cust_indutry_id").val();
                var cust_contct_no = $("#cust_contct_no").val();
                var cust_contct_per1 = $("#cust_contct_per1").val();
                var cust_address = $("#cust_address").val();
                var cust_credt_rate = $("#cust_credt_rate").val();
                var cust_bank = $("#cust_bank").val();
                var cust_branch = $("#cust_branch").val();
                var cust_geo_market = $("#cust_geo_market").val();

                if (idndb_customer_define === '') {
                    return false;
                }

                if (cust_id === '') {
                    $('#cust_id').css('border-color', 'red');
                    return false;
                }
                if (cust_name === '') {
                    $('#cust_name').css('border-color', 'red');
                    return false;
                }
                if (cust_indutry_id === 'Select The Industry') {
                    $('#cust_indutry_id').css('border-color', 'red');
                    return false;
                }
                if (cust_contct_no === '') {
                    $('#cust_contct_no').css('border-color', 'red');
                    return false;
                }
                if (cust_contct_per1 === '') {
                    $('#cust_contct_per1').css('border-color', 'red');
                    return false;
                }
                if (cust_address === '') {
                    $('#cust_address').css('border-color', 'red');
                    return false;
                }
                if (cust_credt_rate === '') {
                    $('#cust_credt_rate').css('border-color', 'red');
                    return false;
                }
                if (cust_bank === 'Select The Bank') {
                    $('#cust_bank').css('border-color', 'red');
                    return false;
                }
                if (cust_branch === 'Select The Branch') {
                    $('#cust_branch').css('border-color', 'red');
                    return false;
                }
                if (cust_geo_market === 'Select The Geographical Market') {
                    $('#cust_geo_market').css('border-color', 'red');
                    return false;
                }

                return hasError;
            }


        });


        function fnExcelReport() {



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
                    navigator.msSaveBlob(blob, 'UNAUTH CUSTOMER DATA.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'UNAUTH CUSTOMER DATA.xls');
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

                <section class="content-header">
                    <h1>
                        Customer Master File Authorization
                        <small>Customer Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Define Customer Authorization</li>
                    </ol>
                </section>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <form class="form-horizontal">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Customer Type</label>
                                            <div class="col-sm-10">
                                                <label>
                                                    <input type="checkbox" id="cust_ndb_customer_active"> NDB Customer
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Customer ID</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="cust_id" name="cust_id" style="width: 350px">
                                                <input type="hidden" class="form-control" id="idndb_customer_define" name="idndb_customer_define" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Customer Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="cust_name" name="cust_name" style="width: 350px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Short Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="cust_short_name" name="cust_short_name" style="width: 350px">
                                            </div>
                                        </div>
                                        <div id="_ndb_customer">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label" >Industry</label>
                                                <div class="col-sm-10">
                                                    <select id="cust_indutry_id" class="form-control" style="width: 350px;">
                                                        <option>Select The Industry</option>
                                                        <% if (_session_availability) {

                                                                out.print(new FillDataComboBean().getFileIndustryActiveDataCmb(session.getAttribute("userid").toString()));

                                                            }

                                                        %>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Contact number</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_contct_no" name="cust_contct_no" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Fax Number</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_fx_number" name="cust_fx_number" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Contact Person 1</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_contct_per1" name="cust_contct_per1" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Contact Person 2</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_contct_per2" name="cust_contct_per2" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">E-mail</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_email" name="cust_email" style="width: 350px">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Customer Address</label>

                                                <div class="col-sm-10">
                                                    <textarea id="cust_address" name="cust_address" class="form-control" style="width: 350px;height: 150px"></textarea>
                                                </div>

                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Receivable Account No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_rec_acc_no" name="cust_rec_acc_no"  style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Cre. Dsc. Proceeds to A/C No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_cre_des_pros_to_acc_no" name="cust_cre_des_pros_to_acc_no"  style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">RF / Current Operative A/C No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_curr_ac_no" name="cust_curr_ac_no"  style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">CW Collection A/C No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cms_collection_acc_number" name="cms_curr_acc_number"  style="width: 350px">
                                                </div>
                                            </div>        
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">CW /Current Operative A/C No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cms_curr_acc_number" name="cms_curr_acc_number"  style="width: 350px">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Margin A/C No:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_margin_ac_no" name="cust_margin_ac_no"  style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Margin (%)</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_margin" name="cust_margin" placeholder="0.00"  style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Credit Rating</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cust_credt_rate" name="cust_credt_rate" style="width: 350px">
                                                </div>
                                            </div>


                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Bank</label>
                                                <div class="col-sm-10">
                                                    <select id="cust_bank" class="form-control" style="width: 350px;">
                                                        <option>Select The Bank</option>
                                                        <%    if (_session_availability) {

                                                                out.print(new FillDataComboBean().getFileBankNameDataCmb(session.getAttribute("userid").toString()));

                                                            }


                                                        %>
                                                    </select> </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Branch</label>
                                                <div class="col-sm-10">
                                                    <select id="cust_branch" class="form-control" style="width: 350px;">
                                                        <option>Select The Branch</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Other Bank A/C No. </label>
                                                <div class="col-sm-10">
                                                    <input type="text"  class="form-control" id="cust_other_bank_ac_no" name="cust_other_bank_ac_no" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Account Officer</label>
                                                <div class="col-sm-10">
                                                    <select id="cust_geo_market" class="form-control" style="width: 350px;">
                                                        <option>Select The Geographical Market</option>
                                                        <%   if (_session_availability) {

                                                                out.print(new FillDataComboBean().getFileGeoMrketActiveDataCmb(session.getAttribute("userid").toString()));

                                                            }


                                                        %>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-1">
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" id="cust_ch_active"> Active
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <input type="text" id="statusReason" class="form-control" placeholder="Reason for Activating/De-activating"/>
                                            </div>
                                            <div class="col-sm-5"></div>
                                        </div>
                                    </div><!-- /.box-body -->
                                    <!-- /.box-footer -->
                                </form>
                                <div class="box-footer">

                                    <button type="submit" class="btn btn-info pull-right" id="btn_cutomerdfine_rejected">Rejected</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cutomerdfine_auth" style="margin-right: 5px">Authorized</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel" style="margin-right: 5px">Cancel</button>

                                </div>
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
                                    <h3 class="box-title">Customer Un-Authorized Data :</h3>
                                    <a href="#" id="test" onClick="javascript:fnExcelReport();">Click Here To Download</a>

                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" style="cursor: pointer;" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Customer ID</th>
                                                <th>Customer Name</th>
                                                <th>Changes</th>
                                                <th>Approval</th>
                                                <th>Modify By</th>
                                                <th>Modify date</th>
                                                <th><div class="controls"><input class="check_boxes optional" type="checkbox" name="mactive" id="mactive" value="Y"/></div></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getCustomerDifineUnAuthData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Customer ID</th>
                                                <th>Customer Name</th>
                                                <th>Changes</th>
                                                <th>Approval</th>
                                                <th>Modify By</th>
                                                <th>Modify date</th>
                                                <th></th>
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
