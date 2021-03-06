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
        <title>NDB BANK | Manual Entry CW</title>
        <!-- Tell the browser to be responsive to screen width -->
        <link rel="stylesheet" href="../../bootstrap/css/style.css">
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

            String _system_date = new comboDAO().getSystemDate();
        %>

        $(function () {
            window.onload = function () {

                document.getElementById("pdc_branch_ac_officer").disabled = true;

                $("#sl_has_byr_warehs_tenor").prop('readonly', true);
                $("#Tenor").prop('readonly', true);
                $("#bank_form_select").hide();
                $("#branch_form_select").hide();
                $("#liq_proccess").hide();
                $("#loading-mask").hide();
                $("#dev_unauthdata").hide();

                var id_seller = $("#id_seller").val();
                var sell_null_checker = 'true';
                if (id_seller === 'null') {
                    sell_null_checker = 'false';

                }
                if (sell_null_checker === 'true') {
                    $('#idndb_customer_define_seller_id').val(id_seller);
                    $('#idndb_customer_define_seller_name').val(id_seller);
                    getSellerBranchAcOfficer(id_seller);

                    loadBuyersUsingSellers();
                }



            };


            $("#cw_chq_number").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#cw_chq_number").keyup(function () {
                if (!(this.value === '')) {
                    $('#cw_chq_number').css('border-color', '');
                }
            });

            $("#cw_chq_number").focusout(function () {

                if (this.value === '') {
                    $('#cw_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Cheque number cannot be empty. !", "error");
                }

                if ((this.value.length < 6)) {
                    $('#cw_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid cheque number length . !", "error");
                }

                if (this.value === '000000') {
                    $('#cw_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid cheque number. !", "error");
                }

            });


            $("#cw_cheque_amu").keyup(function () {

                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/./,]/g, '');
                }

                if (!(this.value === '')) {
                    $('#cw_cheque_amu').css('border-color', '');
                }
            });


            $("#cw_cheque_amu").focusout(function () {

                if (this.value === '') {
                    $('#cw_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "Cheque amount cannot be empty. !", "error");
                } else if (this.value === '0.00') {
                    $('#cw_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid Cheque amount. !", "error");
                } else if (this.value === '0') {
                    $('#cw_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid Cheque amount. !", "error");
                } else {
                    this.value = parseFloat(Math.round(this.value * 100) / 100).toFixed(2)
                    this.value = addCommas(this.value);
                }


            });

            $("#cw_value_date").focusout(function () {

                $('#cw_value_date').css('border-color', '');
                $('#cw_value_date_lable').text('');


                var oneDay = 24 * 60 * 60 * 1000;

                var obj_curr_date = new Date();
                var month = obj_curr_date.getMonth() + 1;
                var day = obj_curr_date.getDate();


                var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                var entered_date = $("#cw_value_date").val();


                var entered_datevalues = entered_date.split('/');
                var entered_date_day = entered_datevalues[0];
                var entered_date_month = entered_datevalues[1];
                var entered_date_year = entered_datevalues[2];
                var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                var entered_currentDate = current_date.split('/');
                var currentDate_day = entered_currentDate[0];
                var currentDate_month = entered_currentDate[1];
                var currentDate_year = entered_currentDate[2];
                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                var obj_entered_date = new Date(entered_date_formatted);
                var obj_current_date = new Date(currentDate_formatted);


                var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                var defined_pdc_tenor = $("#sl_has_byr_warehs_tenor").val();

                $('#Tenor').val(diffDays);

                if (obj_current_date.getTime() >= obj_entered_date.getTime()) {
                    $('#Tenor').val('0');
                    $('#cw_value_date').css('border-color', 'red');
                    $('#cw_value_date_lable').text('Value date cannot be today or backdated date');
                    swal("Invalid Input !", "Value date cannot be today or backdated date. !", "error");
                } else if (diffDays > defined_pdc_tenor) {
                    var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
                    $('#cw_value_date').css('border-color', 'red');
                    $('#cw_value_date_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
                }


            });

            $('#btn_cancel').click(function () {

                location.reload();
            });
            $("#btn_cw_pdc_save").click(function () {
                $("#loading-mask").show();

                if (validateInput()) {

                    document.getElementById("btn_cw_pdc_save").disabled = true;
                    document.getElementById("btn_cancel").disabled = true;


                    var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();
                    var cw_cheque_amu = $("#cw_cheque_amu").val();
                    cw_cheque_amu = cw_cheque_amu.replace(/\,/g, '');

                    var cust_bank_code = $('#cust_bank_code option:selected').text().trim();
                    var cust_branch_code = $('#cust_branch_code option:selected').text().trim();

                    var cus_buyer_id = $('#idndb_customer_define_buyer_id option:selected').text().trim();
                    var cus_buyer_name = $('#idndb_customer_define_buyer_name option:selected').text().trim();



                    $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                    var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                    var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                    var paramid = "get_buyer_rel_data";
                    var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                            "&cw_cheque_amu=" + cw_cheque_amu +
                            "&cust_bank_code=" + cust_bank_code +
                            "&cust_branch_code=" + cust_branch_code +
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

                                var message = '';



                                // 1125, but a string, so convert it to number
                                cw_cheque_amu = parseFloat(cw_cheque_amu);


                                if (!(v.sl_has_byr_warehs_fmax_chq_amu === '0' || v.sl_has_byr_warehs_fmax_chq_amu === '0.00' || v.sl_has_byr_warehs_fmax_chq_amu === '0.0')) {

                                    if (cw_cheque_amu > v.sl_has_byr_warehs_fmax_chq_amu) {


                                        message = message + 'Per Cheque amount limit has exceeded.(per cheque amount limit : ' + v.sl_has_byr_warehs_fmax_chq_amu_banner + ')</br>';



                                    }
                                }



//                                if (v.sl_has_byr_warehs_balance < cw_cheque_amu) {
//                                    // alert("ok");
//
//                                    message = message + 'Cheque warehousing limit has exceeded. (Available balance : ' + v.sl_has_byr_warehs_balance_banner + ')</br> ' + v.seller_message + ' . ';
//
//
//
//                                }

                                if (v.sl_has_byr_warehs_balance < 0) {
                                    // alert("ok");

                                    message = message + 'Cheque warehousing limit has exceeded. (Available balance : ' + v.sl_has_byr_warehs_balance_banner + ')</br> ';



                                }
                                if (v.seller_message !== "") {
                                    message = message + v.seller_message;
                                }

                                if (v.m_buyer_acc_dtails_availability === true && v.m_buyer_acc_dtails_validity === false) {
                                    var buyer_bank_branch_error = '. Cheque bank, branch and account details are not available for the selected buyer (Buyer ID : ' + cus_buyer_id + ', Buyer Name : ' + cus_buyer_name + ').(Bank Code :' + cust_bank_code + ', Branch code : ' + cust_branch_code + ').';

                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=" + buyer_bank_branch_error + "&id_seller=" + $("#idndb_customer_define_seller_id").val().trim() + "";


                                } else {

                                    var oneDay = 24 * 60 * 60 * 1000;

                                    var obj_curr_date = new Date();
                                    var month = obj_curr_date.getMonth() + 1;
                                    var day = obj_curr_date.getDate();


                                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                                    var entered_date = $("#cw_value_date").val();


                                    var entered_datevalues = entered_date.split('/');
                                    var entered_date_day = entered_datevalues[0];
                                    var entered_date_month = entered_datevalues[1];
                                    var entered_date_year = entered_datevalues[2];
                                    var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                                    var entered_currentDate = current_date.split('/');
                                    var currentDate_day = entered_currentDate[0];
                                    var currentDate_month = entered_currentDate[1];
                                    var currentDate_year = entered_currentDate[2];
                                    var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                                    var obj_entered_date = new Date(entered_date_formatted);
                                    var obj_current_date = new Date(currentDate_formatted);


                                    var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                                    var defined_pdc_tenor = $("#sl_has_byr_warehs_tenor").val();

                                    if (diffDays > defined_pdc_tenor) {
                                        message = message + 'Receivable Finance tenor has exceeded. ( Defined Tenor : ' + defined_pdc_tenor + '. Exeeded Tenor :' + diffDays + ')</br>';
                                    }


                                    if (!(message === '')) {


                                        var paramid = "save_cw_pdc_manual_entry";
                                        var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                "&cw_chq_number=" + $("#cw_chq_number").val().trim() +
                                                "&cust_bank=" + $("#cust_bank").val().trim() +
                                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                                "&cust_branch=" + $("#cust_branch").val().trim() +
                                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                                "&cw_value_date=" + $("#cw_value_date").val().trim() +
                                                "&cw_cheque_modification=NEW&cw_cheque_amu=" + cw_cheque_amu +
                                                "&chq_wh_dr_to_cr_spe_narr=" + $("#chq_wh_dr_to_cr_spe_narr").val().trim() +
                                                "&paramid=" + paramid;

                                        window.location = "modalsWarningCW.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=" + message + "&action=Do you want to override transaction ?&" + values + ""

                                    } else {

                                        var paramid = "save_cw_pdc_manual_entry";
                                        var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                "&cw_chq_number=" + $("#cw_chq_number").val().trim() +
                                                "&cust_bank=" + $("#cust_bank").val().trim() +
                                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                                "&cust_branch=" + $("#cust_branch").val().trim() +
                                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                                "&cw_value_date=" + $("#cw_value_date").val().trim() +
                                                "&message=" +
                                                "&cw_cheque_modification=NEW&cw_cheque_amu=" + cw_cheque_amu +
                                                "&chq_wh_dr_to_cr_spe_narr=" + $("#chq_wh_dr_to_cr_spe_narr").val().trim() +
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
                                                    window.location = "modalsSucess.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=PDC Cheque warehousing entry data saved to System successfuly and sent for authorization.&id_seller=" + id_seller + "";

                                                } else if (data.systemna) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=" + data.systemna + "";

                                                } else if (data.cn_error) {
                                                    var rec_data = data.cn_error.split('/');
                                                    var id_seller = rec_data[1];
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=" + rec_data[0] + "!&id_seller=" + id_seller + "";

                                                } else if (data.error) {
                                                    var rec_data = data.error.split('/');
                                                    var id_seller = rec_data[1];
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_cw_manual.jsp&message=Error occured in saving PDC cheque warehousing entry !&id_seller=" + id_seller + "";

                                                }
                                            }
                                        });

                                    }
                                }
                                $("#loading-mask").hide();

                            });
                        }
                    });

                }

            });

            $("#btn_pdc_unauth_view").click(function (e) {

                var idndb_customer_define_seller_id = $('#idndb_customer_define_seller_id').val();

                if (!(idndb_customer_define_seller_id === 'Select The Seller')) {

                    $("#loading-mask").show();
                    var table = $("#example1").DataTable();
                    table.clear().draw();

                    var paramid = "getPdcVdeElqUnauthData";
                    var values = "idndb_customer_define_seller_id=" + idndb_customer_define_seller_id + "\
                        &selection_param=CW_NEW&paramid=" + paramid;

                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/txn_data_retrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            if ((Object.keys(data).length) === 0) {
                                $("#loading-mask").hide();
                                swal("No data found !", "Un-Authorized CW new transactions not available. !");
//                        swal("Invalid Input !", "Shareholder NIC not found !", "error");
                            } else {

                                $("#dev_unauthdata").show();
                                $.each(data, function (k, v) {

                                    table.row.add([v.idndb_pdc_txn_master, v.seller_details, v.buyer_details, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_value_date, v.pdc_chq_amu, v.pdc_latest_modification, v.pdc_chq_status_auth]).draw();

                                });

                                $("#loading-mask").hide();

                            }

                        }
                    });

                } else {

                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the seller. !", "error");
                }




            });


            $("#idndb_customer_define_seller_id").change(function () {

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);

                if (!(idndb_customer_define_seller_id === 'Select The Seller')) {
                    $('#idndb_customer_define_seller_id').css('border-color', '');
                    $('#idndb_customer_define_seller_name').css('border-color', '');


                    var paramid = "get_sellers_buyer_data";
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

                                $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc)



                            });
                        }
                    });

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


                    var paramid = "get_sellers_buyer_data";
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

                                $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc)



                            });
                        }
                    });

                } else {

                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the seller. !", "error");
                }


            });

            $("#idndb_customer_define_buyer_id").change(function () {

                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                if (!(idndb_customer_define_buyer_id === 'Select The Buyer')) {

                    $('#idndb_customer_define_buyer_id').css('border-color', '');
                    $('#idndb_customer_define_buyer_name').css('border-color', '');

                    var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                    var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                    var paramid = "get_buyer_rel_data";
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
                                $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_warehs_tenor);
                                $('#sl_has_byr_warehs_limit').val(v.sl_has_byr_warehs_limit);
                                $('#sl_has_byr_warehs_otstaning').val(v.sl_has_byr_warehs_otstaning);
                                $('#sl_has_byr_warehs_fmax_chq_amu').val(v.sl_has_byr_warehs_fmax_chq_amu);
                                $('#sl_has_byr_warehs_balance').val(v.sl_has_byr_warehs_balance);
                                $('#chq_wh_dr_to_cr_spe_narr_status').val(v.chq_wh_dr_to_cr_spe_narr);


                            });
                        }
                    });

                } else {

                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the buyer. !", "error");
                }

            });


            $("#idndb_customer_define_buyer_name").change(function () {

                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();
                $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_name);

                if (!(idndb_customer_define_buyer_name === 'Select The Buyer')) {
                    $('#idndb_customer_define_buyer_id').css('border-color', '');
                    $('#idndb_customer_define_buyer_name').css('border-color', '');


                    var idndb_seller_has_buyers = idndb_customer_define_buyer_name;
                    var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();
                    var paramid = "get_buyer_rel_data";
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
                                $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_warehs_tenor);
                                $('#sl_has_byr_warehs_limit').val(v.sl_has_byr_warehs_limit);
                                $('#sl_has_byr_warehs_otstaning').val(v.sl_has_byr_warehs_otstaning);
                                $('#sl_has_byr_warehs_fmax_chq_amu').val(v.sl_has_byr_warehs_fmax_chq_amu);
                                $('#sl_has_byr_warehs_balance').val(v.sl_has_byr_warehs_balance);
                                $('#chq_wh_dr_to_cr_spe_narr_status').val(v.chq_wh_dr_to_cr_spe_narr);


                            });
                        }
                    });
                } else {

                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the buyer. !", "error");
                }

            });


            $("#cust_bank_code").change(function () {

                var cust_bank_code = $("#cust_bank_code").val();

                if (!(cust_bank_code === 'Select The Bank')) {
                    $('#cust_bank_code').css('border-color', '');


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
                } else {
                    $('#selected_bank_name').text('');
                    $('#cust_bank_code').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the bank. !", "error");
                }





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

            $("#cust_branch").change(function () {
                var cust_branch = $("#cust_branch").val();
                $('#cust_branch_code').val(cust_branch);

            });



            $("#cust_branch_code").change(function () {

                var cust_branch_code = $("#cust_branch_code").val();

                if (!(cust_branch_code === 'Select The Branch')) {
                    $("#loading-mask").show();
                    $('#cust_branch_code').css('border-color', '');

                    $('#cust_branch').val($("#cust_branch_code").val());
                    $('#selected_branch_name').text($("#cust_branch option:selected").text());

                    if (validateInputCWAE()) {
                        var paramid = "isChqNumAllReadyExist";
                        var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                "&cust_bank_code=" + $('#cust_bank_code').val() +
                                "&cust_branch_code=" + $('#cust_branch_code').val() +
                                "&cw_chq_number=" + $("#cw_chq_number").val().trim() +
                                "&paramid=" + paramid;

                        $.ajax({
                            type: "POST",
                            url: "/NDBRMS/txn_data_retrive",
                            data: values,
                            dataType: "json",
                            //if received a response from the server
                            success: function (data) {
                                $.each(data, function (k, v) {
                                    var pdc_req_financing = v.pdc_req_financing;
                                    if (pdc_req_financing === 'RF') {
                                        swal("Invalid Input !", "Cheque allready exist in the RF product. You can amend the cheque on PDC liqidatuins & Extentsions funtion !", "error");
                                        $('#idndb_customer_define_seller_id').css('border-color', 'red');
                                        $('#idndb_customer_define_seller_name').css('border-color', 'red');
                                        $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                                        $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                                        $('#cw_chq_number').css('border-color', 'red');
                                        $('#cust_bank_code').css('border-color', 'red');
                                        $('#cust_branch_code').css('border-color', 'red');
                                    }
                                    if (pdc_req_financing === 'CW') {
                                        swal("Invalid Input !", "Cheque allready exist in the CW product. You can amend the cheque on PDC liqidatuins & Extentsions funtion !", "error");
                                        $('#idndb_customer_define_seller_id').css('border-color', 'red');
                                        $('#idndb_customer_define_seller_name').css('border-color', 'red');
                                        $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                                        $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                                        $('#cw_chq_number').css('border-color', 'red');
                                        $('#cust_bank_code').css('border-color', 'red');
                                        $('#cust_branch_code').css('border-color', 'red');
                                    }
                                });
                                $("#loading-mask").hide();
                            }
                        });


                    }
                } else {
                    $('#selected_branch_name').text('');
                    $('#cust_branch_code').css('border-color', 'red');
                    swal("Invalid Input !", "Please select the branch code. !", "error");
                }

            });




            function validateInput() {

                var hasError = true;
                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                var cw_chq_number = $("#cw_chq_number").val();
                var cust_bank_code = $("#cust_bank_code").val();
                var cust_branch_code = $("#cust_branch_code").val();
                var cw_value_date = $("#cw_value_date").val();
                var cw_cheque_amu = $("#cw_cheque_amu").val().replace(/\,/g, '');

                var chq_wh_dr_to_cr_spe_narr_status = $("#chq_wh_dr_to_cr_spe_narr_status").val();
                var chq_wh_dr_to_cr_spe_narr = $("#chq_wh_dr_to_cr_spe_narr").val();





                if (idndb_customer_define_seller_id === 'Select The Seller') {
                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    hasError = false;

                }

                if (idndb_customer_define_buyer_id === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                    hasError = false;
                }

                if (cw_chq_number === '') {
                    $('#cw_chq_number').css('border-color', 'red');
                    hasError = false;
                }



                if (cw_chq_number.length < 6) {
                    $('#cw_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (cw_chq_number === '000000') {
                    $('#cw_chq_number').css('border-color', 'red');
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

                if (cw_value_date === '') {
                    $('#cw_value_date').css('border-color', 'red');
                    hasError = false;
                }

                if (chq_wh_dr_to_cr_spe_narr_status === '1' && chq_wh_dr_to_cr_spe_narr === '') {
                    $('#chq_wh_dr_to_cr_spe_narr').css('border-color', 'red');
                    hasError = false;
                }

                if (!(cw_value_date === '')) {
                    var oneDay = 24 * 60 * 60 * 1000;

                    var obj_curr_date = new Date();
                    var month = obj_curr_date.getMonth() + 1;
                    var day = obj_curr_date.getDate();


                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                    var entered_date = $("#cw_value_date").val();


                    var entered_datevalues = entered_date.split('/');
                    var entered_date_day = entered_datevalues[0];
                    var entered_date_month = entered_datevalues[1];
                    var entered_date_year = entered_datevalues[2];
                    var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                    var entered_currentDate = current_date.split('/');
                    var currentDate_day = entered_currentDate[0];
                    var currentDate_month = entered_currentDate[1];
                    var currentDate_year = entered_currentDate[2];
                    var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                    var obj_entered_date = new Date(entered_date_formatted);
                    var obj_current_date = new Date(currentDate_formatted);


                    var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                    var defined_pdc_tenor = $("#sl_has_byr_warehs_tenor").val();

                    $('#Tenor').val(diffDays);

                    if (obj_current_date.getTime() >= obj_entered_date.getTime()) {
                        $('#cw_value_date').css('border-color', 'red');
                        $('#cw_value_date_lable').text('Value date cannot be today or backdated date');
                        hasError = false;
                    } else if (diffDays > defined_pdc_tenor) {
                        var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)

                        $('#cw_value_date').css('border-color', 'red');
                        $('#cw_value_date_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
                    }
                }

                if (cw_cheque_amu === '') {
                    $('#cw_cheque_amu').css('border-color', 'red');
                    hasError = false;
                }
                if (cw_cheque_amu <= 0) {
                    $('#cw_cheque_amu').css('border-color', 'red');
                    hasError = false;
                }

                if (!hasError) {
                    $("#loading-mask").hide();
                    swal("Invalid Inputs !", "please look into highlighted error inputs.. !", "error");

                }

                return hasError;
            }



            function validateInputCWAE() {

                var hasError = true;
                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                var cw_chq_number = $("#cw_chq_number").val();
                var cust_bank_code = $("#cust_bank_code").val();



                if (idndb_customer_define_seller_id === 'Select The Seller') {
                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    hasError = false;
                }

                if (idndb_customer_define_buyer_id === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                    hasError = false;
                }

                if (cw_chq_number === '') {
                    $('#cw_chq_number').css('border-color', 'red');
                    hasError = false;
                }
                if (cw_chq_number.length < 6) {
                    $('#cw_chq_number').css('border-color', 'red');
                    hasError = false;
                }
                if (cw_chq_number.length === '000000') {
                    $('#cw_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (cust_bank_code === 'Select The Bank') {
                    $('#cust_bank_code').css('border-color', 'red');
                    hasError = false;
                }

                if (!hasError) {

                    $("#loading-mask").hide();
                }


                return hasError;
            }


            function parseDate(s) {
                var b = s.split(/\D/);
                return new Date(b[2], --b[0], b[1]);
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


        function addBuyers(idndb_customer_define_seller_id) {
            //("buyer set")


            $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


            var paramid = "get_sellers_buyer_data";
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

                        $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_warehs_tenor);


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
            var paramid = "get_buyer_rel_data";
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
                        $('#chq_wh_dr_to_cr_spe_narr_status').val(v.chq_wh_dr_to_cr_spe_narr);



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

                    $('#cust_branch').val(idndb_branch_master_file);
                    $('#cust_branch_code').val(idndb_branch_master_file);
                }
            });





        }


        function loadBuyersUsingSellers() {


            var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
            $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


            var paramid = "get_sellers_buyer_data";
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

            var paramid = "get_pdc_cw_unauth_cheques";
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
                        type: "application/csv;charset=utf-8;"
                    });
                    navigator.msSaveBlob(blob, 'UNAUTHCWCHQS.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'UNAUTHCWCHQS.xls');
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
                        Manual Entry CW PDC
                        <small>CW PDC Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Transactions</a></li>
                        <li class="active">Manual Entry CW PDC</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content" id="contid" >

                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box box-default">
                                <form class="form-horizontal"> 

                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <% if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Id_ActiveCWSellerDataCmb(session.getAttribute("userid").toString()));

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

                                                            out.print(new FillDataComboBean().getCust_Name_ActiveCWSellerDataCmb(session.getAttribute("userid").toString()));

                                                        }


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
                                                <input type="text" class="form-control" id="cw_chq_number" name="pdc_chq_number" style="width: 350px" maxlength="6">
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
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask style="width: 350px" id="cw_value_date">
                                            </div><!-- /.input group -->
                                            <label class="col-sm-2 control-label" id="cw_value_date_lable" style="margin-top: -34px;margin-left: 540px;text-align: left;width: 350px"></label>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Amount</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="cw_cheque_amu" name="cw_cheque_amu" placeholder="0.00" style="width: 350px" >

                                            </div>
                                            <label class="col-sm-2 control-label" id="cw_cheque_amu_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">DR/CR Transfer (Special Narration)</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="chq_wh_dr_to_cr_spe_narr" name="chq_wh_dr_to_cr_spe_narr" style="width: 350px" maxlength="35">
                                            </div>
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
                                                <input type="text" class="form-control" id="sl_has_byr_warehs_tenor" name="sl_has_byr_warehs_tenor" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_warehs_limit" name="sl_has_byr_warehs_limit" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_warehs_otstaning" name="sl_has_byr_warehs_otstaning" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_warehs_fmax_chq_amu" name="sl_has_byr_warehs_fmax_chq_amu" style="width: 350px">
                                                <input type="hidden" class="form-control" id="sl_has_byr_warehs_balance" name="sl_has_byr_warehs_balance" style="width: 350px">
                                                <input type="hidden" class="form-control" id="idndb_pdc_txn_master" name="idndb_pdc_txn_master" style="width: 350px">
                                                <input type="hidden" class="form-control" id="pdc_booking_date" name="pdc_booking_date" style="width: 350px">
                                                <input type="hidden" class="form-control" id="_system_date" name="_system_date" value="<%=_system_date%>"style="width: 350px">
                                                <input type="hidden" class="form-control" id="id_seller" name="id_seller" value="<%=request.getParameter("id_seller")%>"style="width: 350px">
                                                <input type="hidden" class="form-control" id="chq_wh_dr_to_cr_spe_narr_status" name="chq_wh_dr_to_cr_spe_narr_status" style="width: 350px">
                                            </div>
                                        </div>

                                        <div class="form-group" id="liq_proccess">
                                            <label class="col-sm-2 control-label">Liquidation </label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="cw_cheque_liquidation" name="cw_cheque_liquidation">Click to Liquidation Cheque
                                            </div>
                                        </div>



                                    </div><!-- /.box-body -->
                                </form>


                                <div class="box-footer" style="padding-top: 3%;">
                                    <button type="button" class="btn btn-info pull-right" id="btn_cancel" name="btn_holiday_clear">Cancel</button>
                                    <button type="button" class="btn btn-info pull-right" id="btn_pdc_unauth_view" name="btn_pdc_unauth_view" style="margin-right: 0.5%;">&nbsp;&nbsp;View&nbsp;&nbsp;</button>
                                    <button type="button" class="btn btn-info pull-right" id="btn_cw_pdc_save" name="btn_cw_pdc_save" style="margin-right: 0.5%;">Submit</button>
                                </div><!-- /.box-footer -->
                                <hr>

                            </div><!-- /.box -->



                        </div><!-- /.col (left) -->
                    </div><!-- /.row -->

                </section><!-- /.content --></br>

                <section class="content">


                    <div class="row" id="dev_unauthdata">
                        <div class="col-xs-12" style="margin-top: -76px;">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Manual Entry CW PDC Data</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th style="hi">ID</th>
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
                                            <%    if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getCWPDCData(session.getAttribute("userid").toString()));
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
