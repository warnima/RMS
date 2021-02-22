<%@page import="DBOops.MenuDAO"%>
<%@page import="DBAutoFillBean.comboDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>NDB BANK | Manual Entry RF</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="wisedth=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
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
            String _system_date = cmbDAO.getSystemDate();
            MenuDAO menuDAO = new MenuDAO();
        %>


        $(function () {


            window.onload = function () {

                $("#sl_has_byr_rms_tenor").prop('readonly', true);
                $("#Tenor").prop('readonly', true);

                $("#bank_form_select").hide();
                $("#branch_form_select").hide();
                $("#liq_proccess").hide();

                var id_seller = $("#id_seller").val();
                var sell_null_checker = 'true';
                //alert(id_seller);
                if (id_seller == 'null') {
                    //alert(id_seller);
                    sell_null_checker = 'false';

                }
                if (sell_null_checker === 'true') {

                    $('#idndb_customer_define_seller_id').val(id_seller);
                    $('#idndb_customer_define_seller_name').val(id_seller);


                    loadBuyersUsingSellers();
                }

            };



            $("#rf_chq_number").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });



            $("#rf_cheque_amu").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/./,]/g, '');

                    if (this.value.indexOf(".") >= 0) {
                        var x = this.value.split('.');
                        var x1 = x[0];
                        var x2 = x[1];



                        if (x2.length > 2) {
                            var res = x2.substring(0, 2);

                            //x2 = x2.replace(this.value.slice(-1), '');
                            this.value = x1 + '.' + res;

                        }

                    }


                    this.value = addCommas(this.value);

                }



            });


            $("#rf_cheque_amu").focusout(function () {
                this.value = this.value.replace(/[^0-9-/./,]/g, '');

                if (this.value.indexOf(".") >= 0) {
                    var x = this.value.split('.');
                    var x1 = x[0];
                    var x2 = x[1];



                    if (x2.length > 2) {
                        var res = x2.substring(0, 2);

                        //x2 = x2.replace(this.value.slice(-1), '');
                        this.value = x1 + '.' + res;

                    }
                    if (x2.length === 1) {
                        var res = x2.substring(0, 1);

                        //x2 = x2.replace(this.value.slice(-1), '');
                        this.value = x1 + '.' + res + '0';

                    }
                    if (x2.length === 0) {


                        //x2 = x2.replace(this.value.slice(-1), '');
                        this.value = x1 + '.' + '00';

                    }

                }


                this.value = addCommas(this.value);


            });


            $("#idndb_customer_define_buyer_id").change(function () {

                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                var paramid = "get_buyer_rel_data_rms";
                var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                        "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $.each(data, function (k, v) {
                            // alert(v.shb_facty_det_tenor);
                            $('#sl_has_byr_rms_tenor').val(v.shb_facty_det_tenor);
                            $('#sl_has_byr_rms_limit').val(v.shb_facty_det_crd_loam_limit);
                            $('#sl_has_byr_rms_otstaning').val(v.shb_facty_det_os);
                            $('#sl_has_byr_rms_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);
                            $('#sl_has_byr_rms_balance').val(v.sl_has_byr_rms_balance);


                        });
                    }
                });

            });

            $('#btn_cancel').click(function () {
                location.reload();
            });


            $("#idndb_customer_define_buyer_name").change(function () {

                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();

                $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_name);

                var idndb_seller_has_buyers = idndb_customer_define_buyer_name;
                var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                var paramid = "get_buyer_rel_data_rms";
                var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                        "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $.each(data, function (k, v) {
                            // alert(v.shb_facty_det_tenor);
                            $('#sl_has_byr_rms_tenor').val(v.shb_facty_det_tenor);
                            $('#sl_has_byr_rms_limit').val(v.shb_facty_det_crd_loam_limit);
                            $('#sl_has_byr_rms_otstaning').val(v.shb_facty_det_os);
                            $('#sl_has_byr_rms_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);
                            $('#sl_has_byr_rms_balance').val(v.sl_has_byr_rms_balance);


                        });
                    }
                });

            });

            $("#idndb_customer_define_seller_id").change(function () {

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


                var paramid = "get_sellers_buyer_data_for_rms_all";
                var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_id +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $('#idndb_customer_define_buyer_id')
                                .empty()
                                .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')

                        $('#idndb_customer_define_buyer_name')
                                .empty()
                                .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')




                        $.each(data, function (k, v) {


                            $('#idndb_customer_define_buyer_id').append($("<option/>", {
                                value: v.idndb_seller_has_buyers,
                                text: v.cust_id
                            }));


                            $('#idndb_customer_define_buyer_name').append($("<option/>", {
                                value: v.idndb_seller_has_buyers,
                                text: v.cust_name
                            }));

                        });
                    }
                });


                var table = $("#example1").DataTable();
                table.clear().draw();

                var paramid = "get_pdc_rf_unauth_cheques";
                var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/report_data_extract",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $("#example1").show();
                        $.each(data, function (k, v) {


                            table.row.add([v.idndb_pdc_txn_master, v.cust_data, v.buy_data, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_value_date, v.pdc_chq_amu, v.pdc_chq_status, v.pdc_chq_status_auth]).draw();

                        });
                    }
                });


            });


            $("#idndb_customer_define_seller_name").change(function () {


                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_name").val();
                $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_id);


                var paramid = "get_sellers_buyer_data_for_rms_all";
                var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_id +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $('#idndb_customer_define_buyer_id')
                                .empty()
                                .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')

                        $('#idndb_customer_define_buyer_name')
                                .empty()
                                .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')


                        $.each(data, function (k, v) {
                            //   alert(v.idndb_seller_has_buyers)


                            $('#idndb_customer_define_buyer_id').append($("<option/>", {
                                value: v.idndb_seller_has_buyers,
                                text: v.cust_id
                            }));


                            $('#idndb_customer_define_buyer_name').append($("<option/>", {
                                value: v.idndb_seller_has_buyers,
                                text: v.cust_name
                            }));

                        });
                    }
                });


                var table = $("#example1").DataTable();
                table.clear().draw();

                var paramid = "get_pdc_rf_unauth_cheques";
                var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/report_data_extract",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $("#example1").show();
                        $.each(data, function (k, v) {


                            table.row.add([v.idndb_pdc_txn_master, v.cust_data, v.buy_data, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_value_date, v.pdc_chq_amu, v.pdc_chq_status, v.pdc_chq_status_auth]).draw();

                        });
                    }
                });


            });


            $("#cust_bank_code").change(function () {
                //  alert("ok");

                var cust_bank_code = $("#cust_bank_code").val();


                $('#cust_bank').val(cust_bank_code);
                var bank_name = $("#cust_bank option:selected").text();
                $('#selected_bank_name').text(bank_name);

                var bnkcodecmb_bank_id = $("#cust_bank").val();

                $('#cust_bank_code').val(bnkcodecmb_bank_id);
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
                                .append('<option selected="selected" value="Select The Branch">Select The Branch</option>')

                        $('#cust_branch_code')
                                .empty()
                                .append('<option selected="selected" value="Select The Branch">Select The Branch</option>')



                        $.each(data, function (k, v) {
                            //    alert(v.branch_name);

                            $('#cust_branch').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_name
                            }));

                            $('#cust_branch_code').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_id
                            }));

                        });
                    }
                });


            });


            $("#cust_branch").change(function () {

                var cust_branch = $("#cust_branch").val();

                $('#cust_branch_code').val(cust_branch);




            });

            $("#cust_branch_code").change(function () {

                var cust_branch_code = $("#cust_branch_code").val();

                $('#cust_branch').val(cust_branch_code);

                var branch_name = $("#cust_branch option:selected").text();
                $('#selected_branch_name').text(branch_name);



            });





            $("#cust_bank").change(function () {

                var bnkcodecmb_bank_id = $("#cust_bank").val();

                $('#cust_bank_code').val(bnkcodecmb_bank_id);
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
                            //  alert(v.branch_name);

                            $('#cust_branch').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_name
                            }));

                            $('#cust_branch_code').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_id
                            }));

                        });
                    }
                });





            });



            $("#cust_branch_code").change(function () {
                if (validateInputRFAE()) {
                    var paramid = "isChqNumAllReadyExist";
                    var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                            "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                            "&cust_bank_code=" + $('#cust_bank_code').val() +
                            "&cust_branch_code=" + $('#cust_branch_code').val() +
                            "&cw_chq_number=" + $("#rf_chq_number").val().trim() +
                            "&paramid=" + paramid;
                    //  alert(values);


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/txn_data_retrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {


                            $.each(data, function (k, v) {
                                var pdc_req_financing = v.pdc_req_financing;
                                var pdc_chq_status_auth = v.pdc_req_financing;
                                var pdc_chq_status = v.pdc_req_financing;
                                if (!(pdc_chq_status_auth === 'AUTHORIZED' && pdc_chq_status === 'ERLYLIQUDED')) {



                                    if (pdc_req_financing === 'RF') {
                                        if (confirm('Cheque allready exist ! Do you want to amend this cheque')) {
                                            $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);
                                            $('#idndb_customer_define_seller_id').val(v.idndb_customer_define_seller_id);
                                            addBuyers(v.idndb_customer_define_seller_id);


                                            $('#idndb_customer_define_seller_name').val(v.idndb_customer_define_seller_id);


                                            // alert(v.idndb_customer_define_buyer_id);
                                            $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer_id);
                                            $('#idndb_customer_define_buyer_name').val(v.idndb_customer_define_buyer_id);

                                            setBuyerDetails(v.idndb_customer_define_buyer_id);


                                            $('#rf_chq_number').val(v.pdc_chq_number);

                                            $('#cust_bank').val(v.idndb_bank_master_file);
                                            $('#cust_bank_code').val(v.idndb_bank_master_file);

                                            addBranches(v.idndb_bank_master_file, v.idndb_branch_master_file);

                                            $('#cust_branch').val(v.idndb_branch_master_file);
                                            $('#cust_branch_code').val(v.idndb_branch_master_file);

                                            $('#rf_value_date').val(v.pdc_value_date);
                                            $('#pdc_booking_date').val(v.pdc_booking_date);
                                            $('#rf_cheque_amu').val(v.pdc_chq_amu);
                                            $("#liq_proccess").show();

                                            if (v.pdc_chq_status_auth === 'AUTHORIZED') {
                                                document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                                document.getElementById("idndb_customer_define_seller_name").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_name").disabled = true;
                                                $("#rf_cheque_amu").prop('readonly', true);
                                                $("#liq_proccess").show();

                                            } else if (v.pdc_chq_status_auth === 'UN-AUTHORIZED' && v.pdc_txn_mod === 'AUTHORIZED') {
                                                document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                                document.getElementById("idndb_customer_define_seller_name").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_name").disabled = true;
                                                document.getElementById("rf_value_date").disabled = true;
                                                $("#rf_cheque_amu").prop('readonly', true);
                                                $("#liq_proccess").hide();

                                            } else if (v.pdc_chq_status_auth === 'UN-AUTHORIZED' && v.pdc_chq_status === 'ERLYLIQUDED') {
                                                alert("Cheque pending authorization for erly lequdation. There for cheque cannot be amend")
                                                document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                                document.getElementById("idndb_customer_define_seller_name").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                                                document.getElementById("idndb_customer_define_buyer_name").disabled = true;
                                                document.getElementById("rf_value_date").disabled = true;
                                                document.getElementById("cust_bank_code").disabled = true;
                                                document.getElementById("cust_branch_code").disabled = true;
                                                $("#rf_cheque_amu").prop('readonly', true);
                                                $("#rf_chq_number").prop('readonly', true);
                                                $("#liq_proccess").hide();

                                            } else {

                                                $("#liq_proccess").hide();
                                            }

                                            var rf_value_date = $("#rf_value_date").val();
                                            var currentDate = $("#_system_date").val();

                                            var DATEVAL = "YES";

                                            if ((rf_value_date === currentDate) && ($("#idndb_pdc_txn_master").val() === '')) {

                                                $('#rf_value_date').css('border-color', 'red');
                                                $('#rf_value_date_lable').text("Value date can't be today.");
                                                $('#rf_value_date').val('');
                                                $('#Tenor').val('');
                                                DATEVAL = 'NO';
                                            }



                                            var entered_date = $("#rf_value_date").val();
                                            var entered_datevalues = entered_date.split('/');
                                            var entered_date_day = entered_datevalues[0];
                                            var entered_date_month = entered_datevalues[1];
                                            var entered_date_year = entered_datevalues[2];
                                            var entered_date_formatted = entered_date_month + '/' + entered_date_day + '/' + entered_date_year;

                                            var date_define = new Date(entered_date_formatted);
                                            //alert("Entered Date" + date_define);

                                            var tennor_ahead = parseInt($("#sl_has_byr_rms_tenor").val().trim());

                                            //alert("tennor" + tennor_ahead + "AHEAD")

                                            var rms_system_date = $("#pdc_booking_date").val();
                                            var rms_system_datevalues = rms_system_date.split('/');
                                            var rms_system_date_day = rms_system_datevalues[0];
                                            var rms_system_date_month = rms_system_datevalues[1];
                                            var rms_system_date_year = rms_system_datevalues[2];
                                            var rms_system_date_formatted = rms_system_date_month + '/' + rms_system_date_day + '/' + rms_system_date_year;

                                            var rms_current_date_set = new Date(rms_system_date_formatted);
                                            var newdate = new Date(rms_system_date_formatted);


                                            newdate.setDate(newdate.getDate() + tennor_ahead);

                                            var dd = newdate.getDate();
                                            var mm = newdate.getMonth() + 1;
                                            var y = newdate.getFullYear();

                                            var someFormattedDate = mm + '/' + dd + '/' + y;
                                            var newformatteddate = new Date(someFormattedDate);
                                            //alert("Tenor Eceed date" + newformatteddate)

                                            if (date_define > newformatteddate) {
                                                $('#rf_value_date').css('border-color', 'red');
                                                $('#rf_value_date_lable').text("Tenor Exceed.");
                                                DATEVAL = 'YES';

                                            }
                                            //alert("RMS Current Date"+rms_current_date_set)

                                            if (date_define < rms_current_date_set) {
                                                $('#rf_value_date').css('border-color', 'red');
                                                $('#rf_value_date_lable').text("Value date can't be back date.");
                                                $('#rf_value_date').val('');
                                                $('#Tenor').val('');
                                                DATEVAL = 'NO';

                                            }
                                            if (DATEVAL === 'YES') {

                                                var oneDay = 24 * 60 * 60 * 1000;
                                                var currentDate = $("#pdc_booking_date").val();
                                                var entered_date = $("#rf_value_date").val();

                                                var entered_datevalues = entered_date.split('/');
                                                var entered_date_day = entered_datevalues[0];
                                                var entered_date_month = entered_datevalues[1];
                                                var entered_date_year = entered_datevalues[2];
                                                var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                                                var entered_currentDate = currentDate.split('/');
                                                var currentDate_day = entered_currentDate[0];
                                                var currentDate_month = entered_currentDate[1];
                                                var currentDate_year = entered_currentDate[2];
                                                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                                                var firstDate = new Date(entered_date_formatted);
                                                var secondDate = new Date(currentDate_formatted);

                                                var diffDays = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
                                                $('#Tenor').val(diffDays);

                                            }


                                        }

                                    }
                                    if (pdc_req_financing === 'CW') {
                                        alert("Cheque allready exist in CW product. you can amend cheque on CW PDC Version");


                                    }
                                }




                            });
                        }
                    });



                }

            });





            $("#btn_rf_pdc_save").click(function (e) {
                //   alert("ok");

                if (validateInput()) {
                    document.getElementById("btn_rf_pdc_save").disabled = true;
                    document.getElementById("btn_cancel").disabled = true;

                    var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();
                    //alert("ok");

                    var rf_cheque_amu = $("#rf_cheque_amu").val();

                    var cust_bank_code = $('#cust_bank_code option:selected').text().trim();
                    var cust_branch_code = $('#cust_branch_code option:selected').text().trim();

                    var cus_buyer_id = $('#idndb_customer_define_buyer_id option:selected').text().trim();
                    var cus_buyer_name = $('#idndb_customer_define_buyer_name option:selected').text().trim();

                    rf_cheque_amu = rf_cheque_amu.replace(/\,/g, '');

                    $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                    var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                    var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                    var paramid = "get_buyer_rel_data_rms";
                    var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                            "&rf_cheque_amu=" + rf_cheque_amu +
                            "&cust_bank_code=" + cust_bank_code +
                            "&cust_branch_code=" + cust_branch_code +
                            "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                            "&paramid=" + paramid;
//                     alert(values);


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {


                            $.each(data, function (k, v) {

                                var message = '';

                                // 1125, but a string, so convert it to number

                                rf_cheque_amu = parseFloat(rf_cheque_amu);



                                if (!(v.sl_has_byr_max_chq_amu === '0') || v.sl_has_byr_max_chq_amu === '0.00' || v.sl_has_byr_max_chq_amu === '0.0') {

                                    if (rf_cheque_amu > v.sl_has_byr_max_chq_amu) {

                                        message = message + 'Buyer Per Cheque amount limit has exceeded.(per cheque amount limit : ' + v.sl_has_byr_max_chq_amu_banner + ')</br>';



                                    }
                                }




//                                if (v.sl_has_byr_rms_balance < rf_cheque_amu) {
//
//                                    message = message + 'Buyer receviable finance Loan Limit has exceeded. (Available balance : ' + v.sl_has_byr_rms_balance_banner + ')</br> ' + v.seller_message + ' . ';
//
//
//
//                                }
                                if (v.sl_has_byr_rms_balance < 0) {

                                    message = message + 'Buyer receviable finance Loan Limit has exceeded. (Available balance : ' + v.sl_has_byr_rms_balance_banner + ')</br> ';



                                }
                                if (v.seller_message !== "") {
                                    message = message + v.seller_message;
                                }



                                if (v.m_buyer_acc_dtails_availability === true && v.m_buyer_acc_dtails_validity === false) {
                                    message = message + '. Cheque bank, branch and account details are not available for the selected buyer (Buyer ID : ' + cus_buyer_id + ', Buyer Name : ' + cus_buyer_name + ').(Bank Code :' + cust_bank_code + ', Branch code : ' + cust_branch_code + '). < /br> ';

                                }


                                var pdc_booking_date = $("#pdc_booking_date").val();
                                var pdc_booking_date_null_checker = 'true';

                                var currentDate = '';
                                var rms_system_date = '';
                                //alert(id_seller);
                                if (pdc_booking_date == '') {
                                    //alert(id_seller);
                                    pdc_booking_date_null_checker = 'false';

                                }
                                if (pdc_booking_date_null_checker === 'true') {
                                    rms_system_date = $("#pdc_booking_date").val();

                                } else {

                                    rms_system_date = $("#_system_date").val();
                                }

                                var entered_date = $("#rf_value_date").val();
                                var entered_datevalues = entered_date.split('/');
                                var entered_date_day = entered_datevalues[0];
                                var entered_date_month = entered_datevalues[1];
                                var entered_date_year = entered_datevalues[2];
                                var entered_date_formatted = entered_date_month + '/' + entered_date_day + '/' + entered_date_year;

                                var date_define = new Date(entered_date_formatted);
                                // alert("Entered Date"+date_define);

                                var tennor_ahead = parseInt($("#sl_has_byr_rms_tenor").val().trim());

                                // alert("tennor" + tennor_ahead + "AHEAD")

                                var rms_system_datevalues = rms_system_date.split('/');
                                var rms_system_date_day = rms_system_datevalues[0];
                                var rms_system_date_month = rms_system_datevalues[1];
                                var rms_system_date_year = rms_system_datevalues[2];
                                var rms_system_date_formatted = rms_system_date_month + '/' + rms_system_date_day + '/' + rms_system_date_year;

                                var rms_current_date_set = new Date(rms_system_date_formatted);
                                var newdate = new Date(rms_system_date_formatted);


                                // var sl_has_byr_rms_tenor = $("#rf_cheque_amu").val();


                                newdate.setDate(newdate.getDate() + tennor_ahead);

                                var dd = newdate.getDate();
                                var mm = newdate.getMonth() + 1;
                                var y = newdate.getFullYear();

                                var someFormattedDate = mm + '/' + dd + '/' + y;
                                var newformatteddate = new Date(someFormattedDate);
                                //alert("Tenor Eceed date" + newformatteddate)

                                if (date_define > newformatteddate) {
                                    var oneDay = 24 * 60 * 60 * 1000;
                                    var currentDate = '';
                                    if (pdc_booking_date_null_checker === 'true') {
                                        currentDate = $("#pdc_booking_date").val();

                                    } else {

                                        currentDate = $("#_system_date").val();
                                    }
                                    var entered_date = $("#rf_value_date").val();

                                    var entered_datevalues = entered_date.split('/');
                                    var entered_date_day = entered_datevalues[0];
                                    var entered_date_month = entered_datevalues[1];
                                    var entered_date_year = entered_datevalues[2];
                                    var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                                    var entered_currentDate = currentDate.split('/');
                                    var currentDate_day = entered_currentDate[0];
                                    var currentDate_month = entered_currentDate[1];
                                    var currentDate_year = entered_currentDate[2];
                                    var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                                    var firstDate = new Date(entered_date_formatted);
                                    var secondDate = new Date(currentDate_formatted);

                                    var diffDays = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
                                    message = message + 'Receivable Finance tenor has exceeded. (Defined Tenor : ' + tennor_ahead + '. Exeeded Tenor :' + diffDays + ')</br>';





                                }

                                if ($("#rf_cheque_liquidation").prop('checked')) {
                                    message = '';
                                }

                                if (!(message === '')) {

                                    var paramid = "save_rf_pdc_manual_entry";
                                    var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                            "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                            "&rf_chq_number=" + $("#rf_chq_number").val().trim() +
                                            "&cust_bank=" + $("#cust_bank").val().trim() +
//                                            "&cust_bank_code=" + $('#cust_bank_code').text().trim() +
                                            "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                            "&cust_branch=" + $("#cust_branch").val().trim() +
//                                            "&cust_branch_code=" + $('#cust_branch_code').text().trim() +
                                            "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                            "&rf_value_date=" + $("#rf_value_date").val().trim() +
                                            "&rf_cheque_amu=" + rf_cheque_amu +
                                            "&rf_cheque_liquidation=" + ($("#rf_cheque_liquidation").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                            "&paramid=" + paramid;
                                    window.location = "modalsWarningRF.jsp?desigURL=ndb_pdc_rf_manual.jsp&message=" + message + "&action=Do you want to override transaction ?&" + values + "";

                                } else {

                                    var paramid = "save_rf_pdc_manual_entry";
                                    var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                            "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                            "&rf_chq_number=" + $("#rf_chq_number").val().trim() +
                                            "&cust_bank=" + $("#cust_bank").val().trim() +
                                            "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                            "&cust_branch=" + $("#cust_branch").val().trim() +
                                            "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                            "&rf_value_date=" + $("#rf_value_date").val().trim() +
                                            "&rf_cheque_amu=" + rf_cheque_amu +
                                            "&message=" +
                                            "&rf_cheque_liquidation=" + ($("#rf_cheque_liquidation").prop('checked') ? "ACTIVE" : "DEACTIVE") +
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



                                }



                            });
                        }
                    });

                }

            });


            $("#rf_value_date").focusout(function () {

                if (!$("#rf_cheque_liquidation").prop('checked')) {
                    var rf_value_date = $("#rf_value_date").val();

                    var pdc_booking_date = $("#pdc_booking_date").val();
                    var pdc_booking_date_null_checker = 'true';

                    var currentDate = '';
                    var rms_system_date = '';
                    //alert(pdc_booking_date);

                    if (pdc_booking_date === '') {

                        pdc_booking_date_null_checker = 'false';

                    }
                    if (pdc_booking_date_null_checker === 'true') {
                        currentDate = $("#_system_date").val();
                        rms_system_date = $("#pdc_booking_date").val();

                    } else {

                        currentDate = $("#_system_date").val();
                        rms_system_date = $("#_system_date").val();
                    }

                    // alert(rms_system_date);


                    var DATEVAL = "YES";

                    if ((rf_value_date === currentDate) && ($("#idndb_pdc_txn_master").val() === '')) {

                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be today.");
                        $('#rf_value_date').val('');
                        $('#Tenor').val('');
                        DATEVAL = 'NO';
                    }



                    var entered_date = $("#rf_value_date").val();
                    var entered_datevalues = entered_date.split('/');
                    var entered_date_day = entered_datevalues[0];
                    var entered_date_month = entered_datevalues[1];
                    var entered_date_year = entered_datevalues[2];
                    var entered_date_formatted = entered_date_month + '/' + entered_date_day + '/' + entered_date_year;

                    var date_define = new Date(entered_date_formatted);
                    //alert("Entered Date" + date_define);

                    var tennor_ahead = parseInt($("#sl_has_byr_rms_tenor").val().trim());

                    // alert("tennor" + tennor_ahead + "AHEAD")


                    var rms_system_datevalues = rms_system_date.split('/');
                    var rms_system_date_day = rms_system_datevalues[0];
                    var rms_system_date_month = rms_system_datevalues[1];
                    var rms_system_date_year = rms_system_datevalues[2];
                    var rms_system_date_formatted = rms_system_date_month + '/' + rms_system_date_day + '/' + rms_system_date_year;

                    var rms_current_date_set = new Date(rms_system_date_formatted);
                    var newdate = new Date(rms_system_date_formatted);


                    newdate.setDate(newdate.getDate() + tennor_ahead);

                    var dd = newdate.getDate();
                    var mm = newdate.getMonth() + 1;
                    var y = newdate.getFullYear();

                    var someFormattedDate = mm + '/' + dd + '/' + y;
                    var newformatteddate = new Date(someFormattedDate);
                    //alert("Tenor Eceed date" + newformatteddate)

                    if (date_define > newformatteddate) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Tenor Exceed.");
                        DATEVAL = 'YES';

                    }
                    //alert("RMS Current Date"+rms_current_date_set)

                    if (date_define < rms_current_date_set) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be back date.");
                        $('#rf_value_date').val('');
                        $('#Tenor').val('');
                        DATEVAL = 'NO';

                    }
                    if (DATEVAL === 'YES') {

                        var oneDay = 24 * 60 * 60 * 1000;
                        var currentDate = '';
                        var entered_date = '';

                        if (pdc_booking_date_null_checker === 'true') {
                            currentDate = $("#pdc_booking_date").val();
                            entered_date = $("#rf_value_date").val();

                        } else {

                            currentDate = $("#_system_date").val();
                            entered_date = $("#rf_value_date").val();
                        }


                        var entered_datevalues = entered_date.split('/');
                        var entered_date_day = entered_datevalues[0];
                        var entered_date_month = entered_datevalues[1];
                        var entered_date_year = entered_datevalues[2];
                        var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                        var entered_currentDate = currentDate.split('/');
                        var currentDate_day = entered_currentDate[0];
                        var currentDate_month = entered_currentDate[1];
                        var currentDate_year = entered_currentDate[2];
                        var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                        var firstDate = new Date(entered_date_formatted);
                        var secondDate = new Date(currentDate_formatted);

                        var diffDays = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
                        $('#Tenor').val(diffDays);

                    }

                }

            });


            $('#rf_value_date').live('keypress', function (e) {
                if (!$("#rf_cheque_liquidation").prop('checked')) {
                    var rf_value_date = $("#rf_value_date").val();

                    var pdc_booking_date = $("#pdc_booking_date").val();
                    var pdc_booking_date_null_checker = 'true';

                    var currentDate = '';
                    var rms_system_date = '';
                    //alert(pdc_booking_date);

                    if (pdc_booking_date === '') {

                        pdc_booking_date_null_checker = 'false';

                    }
                    if (pdc_booking_date_null_checker === 'true') {
                        currentDate = $("#_system_date").val();
                        rms_system_date = $("#pdc_booking_date").val();

                    } else {

                        currentDate = $("#_system_date").val();
                        rms_system_date = $("#_system_date").val();
                    }

                    // alert(rms_system_date);


                    var DATEVAL = "YES";

                    if ((rf_value_date === currentDate) && ($("#idndb_pdc_txn_master").val() === '')) {

                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be today.");
                        $('#rf_value_date').val('');
                        $('#Tenor').val('');
                        DATEVAL = 'NO';
                    }



                    var entered_date = $("#rf_value_date").val();
                    var entered_datevalues = entered_date.split('/');
                    var entered_date_day = entered_datevalues[0];
                    var entered_date_month = entered_datevalues[1];
                    var entered_date_year = entered_datevalues[2];
                    var entered_date_formatted = entered_date_month + '/' + entered_date_day + '/' + entered_date_year;

                    var date_define = new Date(entered_date_formatted);
                    //alert("Entered Date" + date_define);

                    var tennor_ahead = parseInt($("#sl_has_byr_rms_tenor").val().trim());

                    // alert("tennor" + tennor_ahead + "AHEAD")


                    var rms_system_datevalues = rms_system_date.split('/');
                    var rms_system_date_day = rms_system_datevalues[0];
                    var rms_system_date_month = rms_system_datevalues[1];
                    var rms_system_date_year = rms_system_datevalues[2];
                    var rms_system_date_formatted = rms_system_date_month + '/' + rms_system_date_day + '/' + rms_system_date_year;

                    var rms_current_date_set = new Date(rms_system_date_formatted);
                    var newdate = new Date(rms_system_date_formatted);


                    newdate.setDate(newdate.getDate() + tennor_ahead);

                    var dd = newdate.getDate();
                    var mm = newdate.getMonth() + 1;
                    var y = newdate.getFullYear();

                    var someFormattedDate = mm + '/' + dd + '/' + y;
                    var newformatteddate = new Date(someFormattedDate);
                    //alert("Tenor Eceed date" + newformatteddate)

                    if (date_define > newformatteddate) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Tenor Exceed.");
                        DATEVAL = 'YES';

                    }
                    //alert("RMS Current Date"+rms_current_date_set)

                    if (date_define < rms_current_date_set) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be back date.");
                        $('#rf_value_date').val('');
                        $('#Tenor').val('');
                        DATEVAL = 'NO';

                    }
                    if (DATEVAL === 'YES') {

                        var oneDay = 24 * 60 * 60 * 1000;
                        var currentDate = '';
                        var entered_date = '';

                        if (pdc_booking_date_null_checker === 'true') {
                            currentDate = $("#pdc_booking_date").val();
                            entered_date = $("#rf_value_date").val();

                        } else {

                            currentDate = $("#_system_date").val();
                            entered_date = $("#rf_value_date").val();
                        }


                        var entered_datevalues = entered_date.split('/');
                        var entered_date_day = entered_datevalues[0];
                        var entered_date_month = entered_datevalues[1];
                        var entered_date_year = entered_datevalues[2];
                        var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                        var entered_currentDate = currentDate.split('/');
                        var currentDate_day = entered_currentDate[0];
                        var currentDate_month = entered_currentDate[1];
                        var currentDate_year = entered_currentDate[2];
                        var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                        var firstDate = new Date(entered_date_formatted);
                        var secondDate = new Date(currentDate_formatted);

                        var diffDays = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
                        $('#Tenor').val(diffDays);

                    }

                }

            });

            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "#3c8dbc");
                pickedup = $(this);

                var idndb_pdc_txn_master = $(this).find("td").eq(0).html();
                var paramid = "get_PDC_RF_txn_data_to_fill";
                var values = "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                        "&paramid=" + paramid;
                //  alert(values);
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/txn_data_retrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {


                            $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);
                            $('#idndb_customer_define_seller_id').val(v.idndb_customer_define_seller_id);
                            addBuyers(v.idndb_customer_define_seller_id);


                            $('#idndb_customer_define_seller_name').val(v.idndb_customer_define_seller_id);


                            //  alert(v.idndb_customer_define_buyer_id);
                            $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer_id);
                            $('#idndb_customer_define_buyer_name').val(v.idndb_customer_define_buyer_id);

                            setBuyerDetails(v.idndb_customer_define_buyer_id, v.idndb_pdc_txn_master);


                            $('#rf_chq_number').val(v.pdc_chq_number);

                            $('#cust_bank').val(v.idndb_bank_master_file);
                            $('#cust_bank_code').val(v.idndb_bank_master_file);

                            addBranches(v.idndb_bank_master_file, v.idndb_branch_master_file);

                            $('#cust_branch').val(v.idndb_branch_master_file);
                            $('#cust_branch_code').val(v.idndb_branch_master_file);

                            $('#rf_value_date').val(v.pdc_value_date);
                            $('#rf_cheque_amu').val(v.pdc_chq_amu);

                            $("#liq_proccess").show();


                        });
                    }
                });
            });


            function validateInput() {
                var hasError = true;
                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                var rf_chq_number = $("#rf_chq_number").val();
                var cust_bank_code = $("#cust_bank_code").val();
                var cust_branch_code = $("#cust_branch_code").val();
                var rf_value_date = $("#rf_value_date").val();
                var rf_cheque_amu = $("#rf_cheque_amu").val().replace(/\,/g, '');



                if (idndb_customer_define_seller_id === 'Select The Seller') {
                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    hasError = false;
                }

                if (idndb_customer_define_buyer_id === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_chq_number === '') {
                    $('#rf_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_chq_number.length < 6) {
                    $('#rf_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_chq_number.length === '000000') {
                    $('#rf_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (cust_bank_code === 'Select The Bank') {
                    $('#cust_bank_code').css('border-color', 'red');
                    hasError = false;
                }

                if (cust_branch_code === 'Select The Branch') {
                    $('#cust_branch_code').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_value_date === '') {
                    $('#rf_value_date').css('border-color', 'red');
                    hasError = false;
                }


                var currentDate = $("#_system_date").val();
                if (!$("#rf_cheque_liquidation").prop('checked')) {
                    if ((rf_value_date === currentDate) && ($("#idndb_pdc_txn_master").val() === '')) {

                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be today.");
                        hasError = false;
                    }


                    var entered_date = $("#rf_value_date").val();
                    var entered_datevalues = entered_date.split('/');
                    var entered_date_day = entered_datevalues[0];
                    var entered_date_month = entered_datevalues[1];
                    var entered_date_year = entered_datevalues[2];
                    var entered_date_formatted = entered_date_month + '/' + entered_date_day + '/' + entered_date_year;

                    var date_define = new Date(entered_date_formatted);
                    // alert("Entered Date"+date_define);

                    var tennor_ahead = parseInt($("#sl_has_byr_rms_tenor").val().trim());

                    // alert("tennor" + tennor_ahead + "AHEAD")

                    var rms_system_date = $("#_system_date").val();
                    var rms_system_datevalues = rms_system_date.split('/');
                    var rms_system_date_day = rms_system_datevalues[0];
                    var rms_system_date_month = rms_system_datevalues[1];
                    var rms_system_date_year = rms_system_datevalues[2];
                    var rms_system_date_formatted = rms_system_date_month + '/' + rms_system_date_day + '/' + rms_system_date_year;

                    var rms_current_date_set = new Date(rms_system_date_formatted);
                    var newdate = new Date(rms_system_date_formatted);


                    // var sl_has_byr_rms_tenor = $("#rf_cheque_amu").val();


                    newdate.setDate(newdate.getDate() + tennor_ahead);

                    var dd = newdate.getDate();
                    var mm = newdate.getMonth() + 1;
                    var y = newdate.getFullYear();

                    var someFormattedDate = mm + '/' + dd + '/' + y;
                    var newformatteddate = new Date(someFormattedDate);
                    //alert("Tenor Eceed date" + newformatteddate)

                    if (date_define > newformatteddate) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Tenor Exceed.");
                        //  return false;

                    }
                    //alert("RMS Current Date"+rms_current_date_set)

                    if (date_define < rms_current_date_set) {
                        $('#rf_value_date').css('border-color', 'red');
                        $('#rf_value_date_lable').text("Value date can't be back date.");
                        hasError = false;

                    }

                }

                if (rf_cheque_amu === '') {
                    $('#rf_cheque_amu').css('border-color', 'red');
                    hasError = false;
                }
                if (rf_cheque_amu <= 0) {
                    $('#rf_cheque_amu').css('border-color', 'red');
                    hasError = false;
                }


                return hasError;
            }



            function validateInputRFAE() {
                var hasError = true;
                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                var rf_chq_number = $("#rf_chq_number").val();
                var cust_bank_code = $("#cust_bank_code").val();




                if (idndb_customer_define_seller_id === 'Select The Seller') {
                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    hasError = false;
                }

                if (idndb_customer_define_buyer_id === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_chq_number === '') {
                    $('#rf_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (rf_chq_number.length < 6) {
                    $('#rf_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (cust_bank_code === 'Select The Bank') {
                    $('#cust_bank_code').css('border-color', 'red');
                    hasError = false;
                }



                return hasError;
            }



            function parseDate(s) {
                var b = s.split(/\D/);
                return new Date(b[2], --b[0], b[1]);
            }


        });

        function addBuyers(idndb_customer_define_seller_id) {
            // alert("buyer set")


            $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


            var paramid = "get_sellers_buyer_data_for_rms_all";
            var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_id +
                    "&paramid=" + paramid;


            $.ajax({
                type: "POST",
                url: "/NDBRMS/CustomerDataRetrive",
                data: values,
                dataType: "json",
                //if received a response from the server
                success: function (data) {



                    $('#idndb_customer_define_buyer_id')
                            .empty()
                            .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')
                    $('#idndb_customer_define_buyer_name')
                            .empty()
                            .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')




                    $.each(data, function (k, v) {

                        $('#sl_has_byr_rms_tenor').val(v.shb_facty_det_tenor);

                        $('#idndb_customer_define_buyer_id').append($("<option/>", {
                            value: v.idndb_seller_has_buyers,
                            text: v.cust_id
                        }));


                        $('#idndb_customer_define_buyer_name').append($("<option/>", {
                            value: v.idndb_seller_has_buyers,
                            text: v.cust_name
                        }));

                    });
                }
            });
            //  alert("buyer set") 



        }


        function setBuyerDetails(idndb_customer_define_buyer_id, idndb_pdc_txn_master) {


            // $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
            var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
            var idndb_pdc_txn_master = idndb_pdc_txn_master;
            var paramid = "get_buyer_rel_data_rms";
            var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                    "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                    "&paramid=" + paramid;


            $.ajax({
                type: "POST",
                url: "/NDBRMS/CustomerDataRetrive",
                data: values,
                dataType: "json",
                //if received a response from the server
                success: function (data) {


                    $.each(data, function (k, v) {
                        $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_id);
                        $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                        $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_warehs_tenor);
                        $('#sl_has_byr_warehs_limit').val(v.sl_has_byr_warehs_limit);
                        $('#sl_has_byr_warehs_otstaning').val(v.sl_has_byr_warehs_otstaning);
                        $('#sl_has_byr_warehs_fmax_chq_amu').val(v.sl_has_byr_warehs_fmax_chq_amu);
                        $('#sl_has_byr_warehs_balance').val(v.sl_has_byr_warehs_balance);



                    });
                }
            });

        }





        function addBranches(bnkcodecmb_bank_id, idndb_branch_master_file) {



            $('#cust_bank_code').val(bnkcodecmb_bank_id);
            $('#bnkcodecmb_name').val(bnkcodecmb_bank_id);

            var paramid = "get_bank_brnch_data";
            var values = "&bnkcodecmb_bank_id=" + bnkcodecmb_bank_id +
                    "&paramid=" + paramid;


            $.ajax({
                type: "POST",
                url: "/NDBRMS/CustomerDataRetrive",
                data: values,
                dataType: "json", //if received a response from the server
                success: function (data) {


                    $.each(data, function (k, v) {
                        //  alert(v.branch_name);

                        $('#cust_branch').append($("<option/>", {
                            value: v.idndb_branch_master_file,
                            text: v.branch_name
                        }));

                        $('#cust_branch_code').append($("<option/>", {
                            value: v.idndb_branch_master_file,
                            text: v.branch_id
                        }));

                    });

                    $('#cust_branch').val(idndb_branch_master_file);
                    $('#cust_branch_code').val(idndb_branch_master_file);
                }
            });





        }


        function loadBuyersUsingSellers() {

            var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
            $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


            var paramid = "get_sellers_buyer_data_for_rms_all";
            var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_id +
                    "&paramid=" + paramid;


            $.ajax({
                type: "POST",
                url: "/NDBRMS/CustomerDataRetrive",
                data: values,
                dataType: "json",
                //if received a response from the server
                success: function (data) {


                    $('#idndb_customer_define_buyer_id')
                            .empty()
                            .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')

                    $('#idndb_customer_define_buyer_name')
                            .empty()
                            .append('<option selected="selected" value="Select The Buyer">Select The Buyer</option>')




                    $.each(data, function (k, v) {


                        $('#idndb_customer_define_buyer_id').append($("<option/>", {
                            value: v.idndb_seller_has_buyers,
                            text: v.cust_id
                        }));


                        $('#idndb_customer_define_buyer_name').append($("<option/>", {
                            value: v.idndb_seller_has_buyers,
                            text: v.cust_name
                        }));

                    });
                }
            });


            var table = $("#example1").DataTable();
            table.clear().draw();

            var paramid = "get_pdc_rf_unauth_cheques";
            var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                    "&paramid=" + paramid;
            $.ajax({
                type: "POST",
                url: "/NDBRMS/report_data_extract",
                data: values,
                dataType: "json",
                //if received a response from the server
                success: function (data) {
                    $("#example1").show();
                    $.each(data, function (k, v) {


                        table.row.add([v.idndb_pdc_txn_master, v.cust_data, v.buy_data, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_value_date, v.pdc_chq_amu, v.pdc_chq_status, v.pdc_chq_status_auth]).draw();

                    });
                }
            });


        }


        function addCommas(nStr) {
            nStr += '';
            var x = nStr.split('.');
            var x1 = x[0];
            var x2 = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;

        }

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }



        function fnExcelReport() {
            // var idndb_customer_define_seller_id = $('#idndb_customer_define_seller_id').text().trim();

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
                        type: "application/csv;charset=utf -8;"
                    });
                    navigator.msSaveBlob(blob, 'UNAUTHRFCHQS.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'UNAUTHRFCHQS.xls');
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
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Manual Entry RF PDC
                        <small>RF PDC Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Transactions</a></li>
                        <li class="active">Manual Entry RF PDC</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content" id="contid" >

                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box box-danger">
                                <form class="form-horizontal" id="bank_form" name="bank_form" action="/NDBRMS/MasterServletFile"> 

                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <%

                                                        if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Id_ActiveRFSellerDataCmbAll(session.getAttribute("userid").toString()));

                                                        }

                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller Name</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_name" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <%   if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Name_ActiveRFSellerDataCmbAll(session.getAttribute("userid").toString()));

                                                        }


                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Buyer ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_buyer_id" style="width: 350px;">
                                                    <option>Select The Buyer</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Buyer Name</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_buyer_name" style="width: 350px;">
                                                    <option>Select The Buyer</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Number</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="rf_chq_number" name="rf_chq_number" style="width: 350px" maxlength="6">
                                            </div>
                                        </div>
                                        <div class="form-group" id="bank_form_select">
                                            <label class="col-sm-2 control-label" >Bank Name</label>
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
                                            <label class="col-sm-2 control-label">Bank Code</label>

                                            <div class="col-sm-10">
                                                <select id="cust_bank_code" class="form-control" style="width: 350px;">
                                                    <option>Select The Bank</option>
                                                    <%    if (_session_availability) {

                                                            out.print(new FillDataComboBean().getFileBankCodeDataCmb(session.getAttribute("userid").toString()));

                                                        }


                                                    %>
                                                </select> 
                                            </div>
                                            <label class="col-sm-2 control-label" id="selected_bank_name" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                        </div>
                                        <div class="form-group" id="branch_form_select">
                                            <label class="col-sm-2 control-label">Branch Name</label>
                                            <div class="col-sm-10">
                                                <select id="cust_branch" class="form-control" style="width: 350px;">
                                                    <option>Select The Branch</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Branch Code</label>
                                            <div class="col-sm-10">
                                                <select id="cust_branch_code" class="form-control" style="width: 350px;">
                                                    <option>Select The Branch</option>
                                                </select>
                                            </div>
                                            <label class="col-sm-2 control-label" id="selected_branch_name" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>

                                        </div>



                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Value Date</label>

                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask style="width: 350px" id="rf_value_date">
                                            </div><!-- /.input group -->
                                            <label class="col-sm-2 control-label" id="rf_value_date_lable" style="margin-top: -34px;margin-left: 540px;text-align: left;width: 350px"></label>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Amount</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="rf_cheque_amu" name="rf_cheque_amu" placeholder="0.00" style="width: 350px">

                                            </div>
                                            <label class="col-sm-2 control-label" id="rf_cheque_amu_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Tenor</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="Tenor" name="Tenor" style="width: 350px">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Define Tenor</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="sl_has_byr_rms_tenor" name="sl_has_byr_rms_tenor" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_rms_limit" name="sl_has_byr_rms_limit" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_rms_otstaning" name="sl_has_byr_rms_otstaning" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_rms_fmax_chq_amu" name="sl_has_byr_rms_fmax_chq_amu" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_rms_balance" name="sl_has_byr_rms_balance" style="width: 350px">
                                                <input type="hidden" class="form-control" id="pdc_booking_date" name="pdc_booking_date" style="width: 350px">
                                                <input type="hidden" class="form-control" id="idndb_pdc_txn_master" name="idndb_pdc_txn_master" style="width: 350px">
                                                <input type="hidden" class="form-control" id="_system_date" name="_system_date" style="width: 350px" value="<%=_system_date%>">
                                                <input type="hidden" class="form-control" id="id_seller" name="id_seller" value="<%=request.getParameter("id_seller")%>"style="width: 350px">

                                            </div>
                                        </div>

                                        <div class="form-group" id="liq_proccess">
                                            <label class="col-sm-2 control-label">Liquidation </label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="rf_cheque_liquidation" name="rf_cheque_liquidation">Click to Liquidation Cheque
                                            </div>
                                        </div>



                                    </div><!-- /.box-body -->
                                </form>
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel" name="btn_cancel">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_rf_pdc_save" name="btn_holiday_save">Submit</button>
                                </div><!-- /.box-footer -->


                            </div><!-- /.box -->




                        </div><!-- /.col (left) -->
                    </div><!-- /.row -->

                </section></br><!-- /.content -->

                <section class="content">


                    <div class="row">
                        <div class="col-xs-12" style="margin-top: -76px;">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Manual Entry RF PDC Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Seller ID</th>
                                                <th>Buyer ID</th>
                                                <th>Cheque Number</th>
                                                <th>Bank Code</th>
                                                <th>Branch Code</th>
                                                <th>Cheque Value Date</th>
                                                <th>Cheque Amount</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getRFPDCData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Seller ID</th>
                                                <th>Buyer ID</th>
                                                <th>Cheque Number</th>
                                                <th>Bank Code</th>
                                                <th>Branch Code</th>
                                                <th>Cheque Value Date</th>
                                                <th>Cheque Amount</th>
                                                <th>Status</th>
                                                <th>Approval</th>
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
            </aside><!-- /.control-sidebar --><!-- /.control-sidebar -->
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
