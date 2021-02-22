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

//                document.getElementById("pdc_product_rf").disabled = true;
//                document.getElementById("pdc_product_cw").disabled = true;
//                document.getElementById("cust_bank_code").disabled = true;
//                document.getElementById("cust_branch_code").disabled = true;
//                $("#pdc_chq_number").prop('readonly', true);

                $("#pdc_seller_id_name").prop('readonly', true);
                $("#pdc_buyer_id_name").prop('readonly', true);
                $("#pdc_value_date").prop('readonly', true);
                $("#pdc_cheque_amu").prop('readonly', true);
                $("#pdc_tenor").prop('readonly', true);
                $("#new_pdc_tenor").prop('readonly', true);
                $("#pdc_chq_wh_dr_to_cr_spe_narr").prop('readonly', true);

                $("#cust_cheque_details_div").hide();
                $("#bank_form_select").hide();
                $("#branch_form_select").hide();
                $("#liq_proccess").hide();
                $("#div_pdc_chq_dtl_updt").hide();

                $("#div_new_cust_bank_name").hide();
                $("#div_new_cust_branch_name").hide();

                $("#shareholder-data-model-button").hide();
                $("#new_chq_val_date_div").hide();
                $("#div_pdc_chq_wh_dr_to_cr_spe_narr").hide();
                $("#div_pdc_update_chq_wh_dr_to_cr_spe_narr").hide();
                $("#dev_unauthdata").hide();

                $("#loading-mask").hide();

                document.getElementById("pdc_branch_ac_officer").disabled = true;



            };

            $("#pdc_chq_number").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }

            });

            $("#new_pdc_chq_number").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }

            });

            $("#pdc_product_rf").change(function () {
                if (this.checked) {
                    $("#pdc_product_cw").prop('checked', false);
                }
            });

            $("#pdc_product_cw").change(function () {
                if (this.checked) {
                    $("#pdc_product_rf").prop('checked', false);
                }
            });

            $("#pdc_cheque_vde").change(function () {
                if (this.checked) {
                    $("#pdc_cheque_elq").prop('checked', false);
                    $("#pdc_cheque_dtl_updt").prop('checked', false);
                    $("#new_chq_val_date_div").show();
                    $("#div_pdc_chq_dtl_updt").hide();
                } else {
                    $("#new_chq_val_date_div").hide();
                }
            });

            $("#pdc_cheque_elq").change(function () {
                if (this.checked) {
                    $("#pdc_cheque_vde").prop('checked', false);
                    $("#pdc_cheque_dtl_updt").prop('pdc_cheque_dtl_updt', false);
                    $("#new_chq_val_date_div").hide();
                    $("#div_pdc_chq_dtl_updt").hide();
                }
            });

            $("#pdc_cheque_dtl_updt").change(function () {
                if (this.checked) {
                    $("#pdc_cheque_vde").prop('checked', false);
                    $("#pdc_cheque_elq").prop('checked', false);
                    $("#div_pdc_chq_dtl_updt").show();
                    $("#new_chq_val_date_div").hide();
                } else {
                    $("#div_pdc_chq_dtl_updt").hide();
                }
            });




            $('#btn_cancel').click(function () {
                location.reload();
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



                    swal("Invalid Input !", "Select the bank code for the selection. !", "error");
                }




            });

            $("#new_cust_bank_code").change(function () {
                var new_cust_bank_code = $("#new_cust_bank_code").val();
                $('#new_cust_bank_name').val(new_cust_bank_code);
                var new_cust_bank_name = $("#new_cust_bank_name option:selected").text();
                $('#new_cust_bank_name_lable').text(new_cust_bank_name);


                var paramid = "get_bank_brnch_data";
                var values = "&bnkcodecmb_bank_id=" + new_cust_bank_code +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $('#new_cust_branch_name')
                                .empty()
                                .append('<option selected="selected" value="Select The Branch">Select The Branch</option>')

                        $('#new_cust_branch_code')
                                .empty()
                                .append('<option selected="selected" value="Select The Branch">Select The Branch</option>')



                        $.each(data, function (k, v) {

                            $('#new_cust_branch_name').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_name
                            }));

                            $('#new_cust_branch_code').append($("<option/>", {
                                value: v.idndb_branch_master_file,
                                text: v.branch_id
                            }));

                        });
                    }
                });


            });

            $("#new_pdc_chq_number").keyup(function () {
                if (!(this.value === '')) {
                    $('#new_pdc_chq_number').css('border-color', '');
                }
            });

            $("#pdc_chq_number").keyup(function () {
                if (!(this.value === '')) {
                    $('#pdc_chq_number').css('border-color', '');
                }
            });

            $("#pdc_update_chq_wh_dr_to_cr_spe_narr").keyup(function () {
                if (!(this.value === '')) {
                    $('#pdc_update_chq_wh_dr_to_cr_spe_narr').css('border-color', '');
                }
            });

            $("#new_pdc_cheque_amu").keyup(function () {

                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/./,]/g, '');

                }

                if (!(this.value === '')) {
                    $('#new_pdc_cheque_amu').css('border-color', '');
                }
            });

            $("#pdc_chq_number").focusout(function () {

                if (this.value === '') {
                    $('#pdc_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Cheque number cannot be empty for the selection. !", "error");
                }

                if (this.value === '000000') {
                    $('#pdc_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid cheque number for the selction. !", "error");
                }

            });



            $("#new_pdc_chq_number").focusout(function () {

                if (this.value === '') {
                    $('#new_pdc_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "New cheque number cannot be empty. !", "error");
                }

                if (this.value === '000000') {
                    $('#new_pdc_chq_number').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid new cheque number. !", "error");
                }

            });




            $("#new_pdc_cheque_amu").focusout(function () {

                if (this.value === '') {
                    $('#new_pdc_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "New cheque amount cannot be empty. !", "error");
                } else if (this.value === '0.00') {
                    $('#new_pdc_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid new cheque amount. !", "error");
                } else if (this.value === '0') {
                    $('#new_pdc_cheque_amu').css('border-color', 'red');
                    swal("Invalid Input !", "Invalid new cheque amount. !", "error");
                } else {
                    if ($("#_system_date").val() === $("#pdc_booking_date").val()) {
                        this.value = parseFloat(Math.round((this.value).replace(",", "") * 100) / 100).toFixed(2)
                        this.value = addCommas(this.value);

                    }
                }


            });


            $("#cust_branch_code").change(function () {


                var cust_branch_code = $("#cust_branch_code").val();

                if (!(cust_branch_code === 'Select The Branch')) {
                    $("#loading-mask").show();

                    $('#cust_branch_code').css('border-color', '');

                    $('#cust_branch').val(cust_branch_code);
                    var branch_name = $("#cust_branch option:selected").text();
                    $('#selected_branch_name').text(branch_name);
                    if (validateInputForActiveCheques()) {
                        getActiveChequeDetails();
                    } else {

                        $("#loading-mask").hide();
                    }


                } else {
                    $('#selected_branch_name').text('');
                    $('#cust_branch_code').css('border-color', 'red');
                    swal("Invalid Input !", "Select the branch code for the selection. !", "error");
                }
            });

            $("#new_cust_branch_code").change(function () {
                var new_cust_branch_code = $("#new_cust_branch_code").val();
                $('#new_cust_branch_name').val(new_cust_branch_code);
                var new_cust_branch_name = $("#new_cust_branch_name option:selected").text();
                $('#new_cust_branch_name_lable').text(new_cust_branch_name);
            });





            $("#btn_pdc_ve_elq_update").click(function (e) {


                if (validateUpdateDetails()) {
                    var fun_title = '';
                    var fun_text = '';

                    if ($("#pdc_cheque_dtl_updt").prop('checked')) {
                        fun_title = 'Do you really want to update the cheque details ?';
                        fun_text = 'Save'

                    }

                    if ($("#pdc_cheque_vde").prop('checked')) {
                        fun_title = 'Do you really want to extend value date of this cheque ?';
                        fun_text = 'Save'

                    }
                    if ($("#pdc_cheque_elq").prop('checked')) {
                        fun_title = 'Do you really want to ealry liquidate this cheque ?';
                        fun_text = 'Save';

                    }




                    swal({
                        title: fun_title,
                        icon: "warning",
                        buttons: {
                            cancel: "Cancel",
                            catch : {
                                text: fun_text,
                                value: "catch",
                            },
                        }
                    }).then((value) => {
                        switch (value) {

                            case "catch":
                                savePdcLequdationsAndExtentions();
                                break;

                            default:

                        }
                    });

                }


            });


            function savePdcLequdationsAndExtentions() {
//                if (validateUpdateDetails()) {
                $("#loading-mask").show();
                var message = '';
                var entered_date = $("#new_value_date").val();

                var pdc_cheque_amu = $("#pdc_cheque_amu").val();
                pdc_cheque_amu = pdc_cheque_amu.replace(/\,/g, '');

                var new_pdc_cheque_amu = $("#new_pdc_cheque_amu").val();
                new_pdc_cheque_amu = new_pdc_cheque_amu.replace(/\,/g, '');

                var pdc_product = 'NA';

                if ($("#pdc_product_rf").prop('checked')) {
                    pdc_product = 'RF';
                }
                if ($("#pdc_product_cw").prop('checked')) {
                    pdc_product = 'CW';
                }

                var pdc_change = 'NA';
                if ($("#pdc_cheque_vde").prop('checked')) {
                    pdc_change = 'VDE';
                }
                if ($("#pdc_cheque_elq").prop('checked')) {
                    pdc_change = 'ELQ';
                }

                if ($("#pdc_cheque_dtl_updt").prop('checked')) {
                    pdc_change = 'UPD';
                }

                if (pdc_change === 'VDE') {

                    var oneDay = 24 * 60 * 60 * 1000;

                    var obj_curr_date = new Date();
                    var month = obj_curr_date.getMonth() + 1;
                    var day = obj_curr_date.getDate();


                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();


                    var entered_datevalues = entered_date.split('/');
                    var entered_date_day = entered_datevalues[0];
                    var entered_date_month = entered_datevalues[1];
                    var entered_date_year = entered_datevalues[2];
                    var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;

                    var pdc_booking_date = $("#pdc_booking_date").val();
                    var pdc_booking_date_arr = pdc_booking_date.split('/');
                    var pdc_booking_date_day = pdc_booking_date_arr[0];
                    var pdc_booking_date_moth = pdc_booking_date_arr[1];
                    var pdc_booking_date_year = pdc_booking_date_arr[2];
                    var pdc_booking_date_formatted = pdc_booking_date_year + ',' + pdc_booking_date_moth + ',' + pdc_booking_date_day;

                    var obj_entered_date = new Date(entered_date_formatted);
                    var obj_pdc_booking_date = new Date(pdc_booking_date_formatted);

                    var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_pdc_booking_date.getTime()) / (oneDay)));
                    var defined_pdc_tenor = $("#pdc_tenor").val();

                    if (diffDays > defined_pdc_tenor) {
                        message = message + 'PDC Cheque tenor has exceeded. (Defined Tenor : ' + defined_pdc_tenor + '. Exeeded Tenor :' + diffDays + ')</br>';

                    }



                }

                if (pdc_change === 'UPD') {

                    var oneDay = 24 * 60 * 60 * 1000;

                    var obj_curr_date = new Date();
                    var month = obj_curr_date.getMonth() + 1;
                    var day = obj_curr_date.getDate();


                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                    var updated_value_date = $("#updated_value_date").val();

                    var existing_value_date = $("#pdc_value_date").val();

                    var existing_value_date_array = existing_value_date.split('/');
                    var existing_value_date_day = existing_value_date_array[0];
                    var existing_value_date_month = existing_value_date_array[1];
                    var existing_value_date_year = existing_value_date_array[2];
                    var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;



                    var updated_value_date_arr = updated_value_date.split('/');
                    var updated_value_date_day = updated_value_date_arr[0];
                    var updated_value_date_month = updated_value_date_arr[1];
                    var updated_value_date_year = updated_value_date_arr[2];
                    var updated_value_date_formatted = updated_value_date_year + ',' + updated_value_date_month + ',' + updated_value_date_day;

                    var entered_currentDate = current_date.split('/');
                    var currentDate_day = entered_currentDate[0];
                    var currentDate_month = entered_currentDate[1];
                    var currentDate_year = entered_currentDate[2];
                    var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                    var obj_updated_date = new Date(updated_value_date_formatted);
                    var obj_current_date = new Date(currentDate_formatted);
                    var obj_existing_value_date = new Date(existing_value_date_formatted);


                    var diffDays = Math.round(Math.abs((obj_updated_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                    var defined_pdc_tenor = $("#pdc_tenor").val();


                    if (diffDays > defined_pdc_tenor) {
                        message = message + 'PDC Cheque tenor has exceeded. (Defined Tenor : ' + defined_pdc_tenor + '. Exeeded Tenor :' + diffDays + ')</br>';

                    }

                }





                if (pdc_product === 'RF') {

                    var modification = '';

                    if (pdc_change === 'ELQ') {
                        modification = "&rf_cheque_amu=" + pdc_cheque_amu +
                                "&rf_chq_number=" + $("#pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                "&rf_value_date=" + $("#new_value_date").val().trim() +
                                "&rf_cheque_modification=ELQ";
                    }

                    if (pdc_change === 'VDE') {
                        modification = "&rf_cheque_amu=" + pdc_cheque_amu +
                                "&rf_chq_number=" + $("#pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                "&rf_value_date=" + $("#new_value_date").val().trim() +
                                "&rf_cheque_modification=VDE";

                    }

                    if (pdc_change === 'UPD') {
                        modification = "&rf_cheque_amu=" + new_pdc_cheque_amu +
                                "&rf_chq_number=" + $("#new_pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#new_cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#new_cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#new_cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#new_cust_branch_code option:selected').text().trim() +
                                "&rf_value_date=" + $("#updated_value_date").val().trim() +
                                "&rf_cheque_modification=UPD";
                    }

                    //-------------------------------------------------------------------------------

                    var pdc_buyer_id_name = $("#pdc_buyer_id_name").val();
                    var pdc_seller_id_name = $("#pdc_seller_id_name").val();


                    var paramid = "get_buyer_rel_data_rms";
                    var values = "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val() +
                            "&rf_cheque_amu=" + new_pdc_cheque_amu +
                            "&cust_bank_code=" + $('#new_cust_bank_code option:selected').text().trim() +
                            "&cust_branch_code=" + $('#new_cust_branch_code option:selected').text().trim() +
                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val() +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {

                            $.each(data, function (k, v) {

                                new_pdc_cheque_amu = parseFloat(new_pdc_cheque_amu);
                                pdc_cheque_amu = parseFloat(pdc_cheque_amu);

                                var spec_validation = false;
                                if (pdc_change === 'UPD') {
                                    spec_validation = true;
                                }

                                if (spec_validation === true && v.m_buyer_acc_dtails_availability === true && v.m_buyer_acc_dtails_validity === false) {
                                    var buyer_bank_branch_error = 'Cheque bank, branch and account details are not available for the selected buyer (Buyer ID | Buyer Name: ' + pdc_buyer_id_name + ').(Bank Code :' + $('#new_cust_bank_code option:selected').text().trim() + ', Branch code : ' + $('#new_cust_branch_code option:selected').text().trim() + ').';
                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + buyer_bank_branch_error + "&id_seller=" + $("#idndb_customer_define_seller_id").val().trim() + "";

                                } else {

                                    if (!(new_pdc_cheque_amu === pdc_cheque_amu)) {

                                        if (!(v.sl_has_byr_max_chq_amu === '0') || v.sl_has_byr_max_chq_amu === '0.00' || v.sl_has_byr_max_chq_amu === '0.0') {

                                            if (new_pdc_cheque_amu > v.sl_has_byr_max_chq_amu) {

                                                message = message + 'Buyer Per Cheque amount limit has exceeded.(per cheque amount limit : ' + v.sl_has_byr_max_chq_amu_banner + ')</br>';

                                            }
                                        }

                                        if (v.sl_has_byr_rms_balance < 0) {

                                            message = message + 'Buyer receviable finance Loan Limit has exceeded. (Available balance : ' + v.sl_has_byr_rms_balance_banner + ')</br> ';



                                        }
                                        if (v.seller_message !== "") {
                                            message = message + v.seller_message;
                                        }




                                    }

                                    if (!(message === '')) {

                                        var paramid = "save_rf_pdc_manual_entry";
                                        var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                modification +
                                                "&paramid=" + paramid;

                                        window.location = "modalsWarningRF.jsp?desigURL=ndb_pdc_updates.jsp&message=" + message + "&action=Do you want to override transaction ?&" + values + "";

                                    } else {

                                        var paramid = "save_rf_pdc_manual_entry";
                                        var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                "&message=" +
                                                modification +
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
                                                    window.location = "modalsSucess.jsp?desigURL=ndb_pdc_updates.jsp&message=PDC receviable finance entry data saved to System successfuly and sent for authorization.&id_seller=" + id_seller + "";

                                                } else if (data.systemna) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + data.systemna + "";

                                                } else if (data.cn_error) {
                                                    var rec_data = data.cn_error.split('/');
                                                    var id_seller = rec_data[1];
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + rec_data[0] + "&id_seller=" + id_seller + "";

                                                } else if (data.error) {
                                                    var rec_data = data.error.split('/');
                                                    var id_seller = rec_data[1];
                                                    window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=Error occured in saving PDC receviable finance entry !&id_seller=" + id_seller + "";

                                                }
                                            }
                                        });

                                    }
                                }

                            });
                        }
                    });



                    //-------------------------------------------------------------------------------


                }

                if (pdc_product === 'CW') {

                    var modification = '';

                    if (pdc_change === 'ELQ') {
                        modification = "&cw_cheque_amu=" + pdc_cheque_amu +
                                "&cw_chq_number=" + $("#pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                "&cw_value_date=" + $("#new_value_date").val().trim() +
                                "&cw_cheque_modification=ELQ" +
                                "&chq_wh_dr_to_cr_spe_narr=" + $("#pdc_chq_wh_dr_to_cr_spe_narr").val().trim();
                    }

                    if (pdc_change === 'VDE') {
                        modification = "&cw_cheque_amu=" + pdc_cheque_amu +
                                "&cw_chq_number=" + $("#pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                "&cw_value_date=" + $("#new_value_date").val().trim() +
                                "&cw_cheque_modification=VDE" +
                                "&chq_wh_dr_to_cr_spe_narr=" + $("#pdc_chq_wh_dr_to_cr_spe_narr").val().trim();
                    }

                    if (pdc_change === 'UPD') {
                        modification = "&cw_cheque_amu=" + new_pdc_cheque_amu +
                                "&cw_chq_number=" + $("#new_pdc_chq_number").val().trim() +
                                "&cust_bank=" + $("#new_cust_bank_code").val().trim() +
                                "&cust_bank_code=" + $('#new_cust_bank_code option:selected').text().trim() +
                                "&cust_branch=" + $("#new_cust_branch_code").val().trim() +
                                "&cust_branch_code=" + $('#new_cust_branch_code option:selected').text().trim() +
                                "&cw_value_date=" + $("#updated_value_date").val().trim() +
                                "&cw_cheque_modification=UPD" +
                                "&chq_wh_dr_to_cr_spe_narr=" + $("#pdc_update_chq_wh_dr_to_cr_spe_narr").val().trim();
                    }

                    //-----------------------------------------------------------------------------------------------------------
                    var pdc_buyer_id_name = $("#pdc_buyer_id_name").val();
                    var pdc_seller_id_name = $("#pdc_seller_id_name").val();

                    var paramid = "get_buyer_rel_data";
                    var values = "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val() +
                            "&cw_cheque_amu=" + new_pdc_cheque_amu +
                            "&cust_bank_code=" + $('#new_cust_bank_code option:selected').text().trim() +
                            "&cust_branch_code=" + $('#new_cust_branch_code option:selected').text().trim() +
                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val() +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {

                            $.each(data, function (k, v) {
                                var spec_validation = false;
                                if (pdc_change === 'UPD') {
                                    spec_validation = true;
                                }

                                if (spec_validation === true && v.chq_wh_dr_to_cr_spe_narr === '1' && $("#pdc_update_chq_wh_dr_to_cr_spe_narr").val() === '') {

                                    $('#pdc_update_chq_wh_dr_to_cr_spe_narr').css('border-color', 'red');
                                    $("#loading-mask").hide();

                                } else {

                                    new_pdc_cheque_amu = parseFloat(new_pdc_cheque_amu);
                                    pdc_cheque_amu = parseFloat(pdc_cheque_amu);

                                    if (!(v.sl_has_byr_warehs_fmax_chq_amu === '0' || v.sl_has_byr_warehs_fmax_chq_amu === '0.00' || v.sl_has_byr_warehs_fmax_chq_amu === '0.0')) {

                                        if (new_pdc_cheque_amu > v.sl_has_byr_warehs_fmax_chq_amu) {


                                            message = message + 'Per Cheque amount limit has exceeded.(per cheque amount limit : ' + v.sl_has_byr_warehs_fmax_chq_amu_banner + ')</br>';



                                        }
                                    }

                                    if (v.sl_has_byr_warehs_balance < 0) {

                                        message = message + 'Cheque warehousing limit has exceeded. (Available balance : ' + v.sl_has_byr_warehs_balance_banner + ')</br> ';



                                    }
                                    if (v.seller_message !== "") {
                                        message = message + v.seller_message;
                                    }

                                    if (spec_validation === true && v.m_buyer_acc_dtails_availability === true && v.m_buyer_acc_dtails_validity === false) {

                                        var buyer_bank_branch_error = 'Cheque bank, branch and account details are not available for the (Buyer ID | Buyer Name: ' + pdc_buyer_id_name + ').(Bank Code :' + $('#new_cust_bank_code option:selected').text().trim() + ', Branch code : ' + $('#new_cust_branch_code option:selected').text().trim() + ').';
                                        window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + buyer_bank_branch_error + "&id_seller=" + $("#idndb_customer_define_seller_id").val().trim() + "";

                                    } else {

                                        if (!(message === '')) {


                                            var paramid = "save_cw_pdc_manual_entry";
                                            var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                    "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                    "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                    modification +
                                                    "&chq_wh_dr_to_cr_spe_narr=" +
                                                    "&paramid=" + paramid;

                                            window.location = "modalsWarningCW.jsp?desigURL=ndb_pdc_updates.jsp&message=" + message + "&action=Do you want to override transaction ?&" + values + ""

                                        } else {

                                            var paramid = "save_cw_pdc_manual_entry";
                                            var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                    "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                    "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                                    "&message=" +
                                                    modification +
                                                    "&chq_wh_dr_to_cr_spe_narr=" +
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
                                                        window.location = "modalsSucess.jsp?desigURL=ndb_pdc_updates.jsp&message=PDC Cheque warehousing entry data saved to System successfuly and sent for authorization.&id_seller=" + id_seller + "";

                                                    } else if (data.systemna) {
                                                        window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + data.systemna + "";

                                                    } else if (data.cn_error) {
                                                        var rec_data = data.cn_error.split('/');
                                                        var id_seller = rec_data[1];
                                                        window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=" + rec_data[0] + "!&id_seller=" + id_seller + "";

                                                    } else if (data.error) {
                                                        var rec_data = data.error.split('/');
                                                        var id_seller = rec_data[1];
                                                        window.location = "modalsError.jsp?desigURL=ndb_pdc_updates.jsp&message=Error occured in saving PDC cheque warehousing entry !&id_seller=" + id_seller + "";

                                                    }
                                                }
                                            });

                                        }
                                    }
                                }

                            });
                        }
                    });

                    //-----------------------------------------------------------------------------------------------------------

                }

//                }

            }



            $("#btn_pdc_unauth_view").click(function (e) {
                $("#loading-mask").show();

                var table = $("#example1").DataTable();
                table.clear().draw();

                var paramid = "getPdcVdeElqUnauthData";
                var values = "selection_param=RF_CW_VDE_ELQ_UPD&paramid=" + paramid;

                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/txn_data_retrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        if ((Object.keys(data).length) === 0) {
                            $("#loading-mask").hide();
                            swal("No data found !", "Un-Authorized RF & CW liquidations and value date extensions data not available. !");
//                        swal("Invalid Input !", "Shareholder NIC not found !", "error");
                        } else {

                            $("#dev_unauthdata").show();
                            $.each(data, function (k, v) {

                                table.row.add([v.idndb_pdc_txn_master, v.pdc_req_financing, v.seller_details, v.buyer_details, v.pdc_chq_number, v.pdc_bank_code, v.pdc_branch_code, v.pdc_value_date, v.pdc_chq_amu, v.pdc_latest_modification, v.pdc_chq_status_auth]).draw();

                            });

                            $("#loading-mask").hide();

                        }

                    }
                });


            });





            function getActiveChequeDetails() {

                var pdc_product = 'NA';
                if ($("#pdc_product_rf").prop('checked')) {

                    pdc_product = 'RF';
                }
                if ($("#pdc_product_cw").prop('checked')) {

                    pdc_product = 'CW';
                }
                var pdc_chq_number = $("#pdc_chq_number").val();
                ;
                var cust_bank_code = $('#cust_bank_code option:selected').text().trim();
                var cust_branch_code = $('#cust_branch_code option:selected').text().trim();

                var paramid = "getActiveChequeDetails";
                var values = "&pdc_product=" + pdc_product +
                        "&pdc_chq_number=" + pdc_chq_number +
                        "&cust_bank_code=" + cust_bank_code +
                        "&cust_branch_code=" + cust_branch_code +
                        "&paramid=" + paramid;



                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/txn_data_retrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {




                        if ((Object.keys(data).length) === 0) {
                            $("#loading-mask").hide();
                            swal("Invalid Input !", "Cannot found a PDC cheque for the given selection. !", "error");
                            $('#pdc_chq_number').val('');
                            $('#cust_bank_code').val('Select The Bank');
                            $('#cust_branch_code').val('Select The Branch');
                            $('#selected_bank_name').text('');
                            $('#selected_branch_name').text('');
                            $("#pdc_product_cw").prop('checked', false);
                            $("#pdc_product_rf").prop('checked', false);
                        }

                        if (!((Object.keys(data).length === 1) || (Object.keys(data).length === 0))) {
                            swal({
                                title: " Duplicated PDC Cheque.",
                                text: Object.keys(data).length + " PDC Cheques found for PDC cheque number : " + pdc_chq_number + ", Bank code :" + cust_bank_code + " and Branch code : " + cust_branch_code + ".",
                                icon: "warning",
                                buttons: {
                                    cancel: "Cancel",
                                    catch : {
                                        text: "View PDC Cheques.",
                                        value: "catch",
                                    },
                                }
                            }).then((value) => {
                                switch (value) {

                                    case "catch":
                                        createPdcChequesModelGrid(pdc_product, pdc_chq_number, cust_bank_code, cust_branch_code);
                                        break;

                                    default:

                                }
                            });
                        } else {
                            $.each(data, function (k, v) {
                                $("#loading-mask").hide();

                                $("#cust_cheque_details_div").show();


                                document.getElementById("pdc_product_rf").disabled = true;
                                document.getElementById("pdc_product_cw").disabled = true;
                                document.getElementById("cust_bank_code").disabled = true;
                                document.getElementById("cust_branch_code").disabled = true;
                                $("#pdc_chq_number").prop('readonly', true);
                                if (v.pdc_chq_status_auth === 'UN-AUTHORIZED') {
                                    document.getElementById("pdc_cheque_vde").disabled = true;
                                    document.getElementById("pdc_cheque_elq").disabled = true;

                                }

                                $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);
                                $('#idndb_seller_has_buyers').val(v.idndb_seller_has_buyers);
                                $('#idndb_customer_define_seller_id').val(v.idndb_customer_define_seller_id);
                                $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer_id);
                                $('#pdc_seller_id_name').val(v.seller_cust_id + ' | ' + v.seller_cust_name);
                                $('#pdc_buyer_id_name').val(v.buyer_cust_id + ' | ' + v.buyer_cust_name);
                                $('#pdc_value_date').val(v.pdc_value_date);
                                $('#new_value_date').val(v.pdc_value_date);
                                $('#pdc_cheque_amu').val(v.pdc_chq_amu);
                                $('#new_pdc_cheque_amu').val(v.pdc_chq_amu);
                                $('#pdc_booking_date').val(v.pdc_booking_date);


                                if ($("#pdc_product_rf").prop('checked')) {
                                    $('#pdc_tenor').val(v.shb_facty_det_tenor);
                                    $('#new_pdc_tenor').val(v.shb_facty_det_tenor);
                                    $("#div_pdc_chq_wh_dr_to_cr_spe_narr").hide();
                                    $("#div_pdc_update_chq_wh_dr_to_cr_spe_narr").hide();

                                    if (!($("#_system_date").val() === v.pdc_booking_date)) {
                                        $("#new_pdc_cheque_amu").prop('readonly', true);

                                    }
                                }
                                if ($("#pdc_product_cw").prop('checked')) {

                                    $('#pdc_tenor').val(v.sl_has_byr_warehs_tenor);
                                    $('#new_pdc_tenor').val(v.sl_has_byr_warehs_tenor);
                                    $('#pdc_chq_wh_dr_to_cr_spe_narr').val(v.pdc_chq_wh_dr_to_cr_spe_narr);
                                    $('#pdc_update_chq_wh_dr_to_cr_spe_narr').val(v.pdc_chq_wh_dr_to_cr_spe_narr);

                                    $("#div_pdc_chq_wh_dr_to_cr_spe_narr").show();
                                    $("#div_pdc_update_chq_wh_dr_to_cr_spe_narr").show();
                                    if (!($("#_system_date").val() === v.pdc_booking_date)) {
                                        $("#new_pdc_cheque_amu").prop('readonly', true);
                                    }


                                }
                                $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc)


                                $('#new_pdc_chq_number').val(v.pdc_chq_number);
                                $('#new_cust_bank_code').val(v.idndb_bank_master_file);

                                $('#new_cust_bank_name').val(v.idndb_bank_master_file);
                                $('#new_cust_bank_name_lable').text($("#new_cust_bank_name option:selected").text());


                                addBranches(v.idndb_bank_master_file, v.idndb_branch_master_file)
                                $('#new_cust_branch_code').val(v.idndb_branch_master_file);
                                $('#updated_value_date').val(v.pdc_value_date);
                            });

                        }

                    }
                });

            }



            function createPdcChequesModelGrid(pdc_product, pdc_chq_number, cust_bank_code, cust_branch_code) {
                $("#loading-mask").hide();
                var paramid = "getActiveChequeDetails";
                var values = "&pdc_product=" + pdc_product +
                        "&pdc_chq_number=" + pdc_chq_number +
                        "&cust_bank_code=" + cust_bank_code +
                        "&cust_branch_code=" + cust_branch_code +
                        "&paramid=" + paramid;

                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/txn_data_retrive",
                    data: values,
                    dataType: "json",
                    success: function (data) {

                        var gridrow = '';

                        $.each(data, function (k, v) {


                            gridrow += '<th style="font-weight: 500;500;display:none;">' + v.idndb_pdc_txn_master + '</th>' +
                                    '<th style="font-weight: 500;">' + v.pdc_chq_number + '</th>' +
                                    '<th style="font-weight: 500;">' + cust_bank_code + ' / ' + cust_branch_code + '</th>' +
                                    '<th style="font-weight: 500;">' + v.seller_cust_id + ' / ' + v.seller_cust_name + '</th>' +
                                    '<th style="font-weight: 500;">' + v.buyer_cust_id + ' / ' + v.buyer_cust_id + '</th>' +
                                    '<th style="font-weight: 500;">' + v.pdc_value_date + '</th>' +
                                    '<th style="font-weight: 500;">' + v.pdc_chq_amu + '</th>' +
                                    '<th style="font-weight: 500;"><button class="btn-primary btn" type="button" style="width: 70px;" onclick="getChequeDetails(\'' + v.idndb_pdc_txn_master + '\')">Select</button></th>' + '</tr>';

                        });

                        document.getElementById('certificate-shareholder-model-data-table-body').innerHTML = gridrow;
                        $('#shareholder-data-model-button').click();
                    }
                });
            }
            ;








            $("#new_value_date").focusout(function () {

                $('#new_value_date').css('border-color', '');
                $('#pdc_tenor').css('border-color', '');
                $('#pdc_tenor_lable').text("");

                var oneDay = 24 * 60 * 60 * 1000;

                var obj_curr_date = new Date();
                var month = obj_curr_date.getMonth() + 1;
                var day = obj_curr_date.getDate();


                var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                var entered_date = $("#new_value_date").val();
                var existing_value_date = $("#pdc_value_date").val();
                var pdc_booking_date = $("#pdc_booking_date").val();

                var existing_value_date_array = existing_value_date.split('/');
                var existing_value_date_day = existing_value_date_array[0];
                var existing_value_date_month = existing_value_date_array[1];
                var existing_value_date_year = existing_value_date_array[2];
                var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;




                var entered_datevalues = entered_date.split('/');
                var entered_date_day = entered_datevalues[0];
                var entered_date_month = entered_datevalues[1];
                var entered_date_year = entered_datevalues[2];
                var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;


                var pdc_booking_date_arr = pdc_booking_date.split('/');
                var pdc_booking_date_day = pdc_booking_date_arr[0];
                var pdc_booking_date_moth = pdc_booking_date_arr[1];
                var pdc_booking_date_year = pdc_booking_date_arr[2];
                var pdc_booking_date_formatted = pdc_booking_date_year + ',' + pdc_booking_date_moth + ',' + pdc_booking_date_day;

                var entered_currentDate = current_date.split('/');
                var currentDate_day = entered_currentDate[0];
                var currentDate_month = entered_currentDate[1];
                var currentDate_year = entered_currentDate[2];
                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                var obj_entered_date = new Date(entered_date_formatted);
                var obj_pdc_booking_date = new Date(pdc_booking_date_formatted);
                var obj_current_date = new Date(currentDate_formatted);
                var obj_existing_value_date = new Date(existing_value_date_formatted);


                var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_pdc_booking_date.getTime()) / (oneDay)));
                var defined_pdc_tenor = $("#pdc_tenor").val();


                if (obj_current_date.getTime() >= obj_entered_date.getTime()) {
                    $('#new_value_date').css('border-color', 'red');

                    swal("Invalid Input !", "New value date cannot be today or backdated date. !", "error");
                }

                if (obj_existing_value_date.getTime() >= obj_entered_date.getTime()) {
                    $('#new_value_date').css('border-color', 'red');
                    swal("Invalid Input !", "New value date cannot be existing value date or backdated date to existing value date. !", "error");
                }




                if (diffDays > defined_pdc_tenor) {
                    var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
                    $('#pdc_tenor').css('border-color', 'red');
                    $('#pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');

//                    swal("Invalid Input !", "Tenor exceeded by " + pdc_tenor_diff + " days. !", "warning");
                }



            });


//            $('#new_value_date').live('keypress', function (e) {
//
//                var oneDay = 24 * 60 * 60 * 1000;
//
//                var obj_curr_date = new Date();
//                var month = obj_curr_date.getMonth() + 1;
//                var day = obj_curr_date.getDate();
//
//
//                var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
//                var entered_date = $("#new_value_date").val();
//                var existing_value_date = $("#pdc_value_date").val();
//
//                var existing_value_date_array = existing_value_date.split('/');
//                var existing_value_date_day = existing_value_date_array[0];
//                var existing_value_date_month = existing_value_date_array[1];
//                var existing_value_date_year = existing_value_date_array[2];
//                var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;
//
//
//
//
//                var entered_datevalues = entered_date.split('/');
//                var entered_date_day = entered_datevalues[0];
//                var entered_date_month = entered_datevalues[1];
//                var entered_date_year = entered_datevalues[2];
//                var entered_date_formatted = entered_date_year + ',' + entered_date_month + ',' + entered_date_day;
//
//                var entered_currentDate = current_date.split('/');
//                var currentDate_day = entered_currentDate[0];
//                var currentDate_month = entered_currentDate[1];
//                var currentDate_year = entered_currentDate[2];
//                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;
//
//                var obj_entered_date = new Date(entered_date_formatted);
//                var obj_current_date = new Date(currentDate_formatted);
//                var obj_existing_value_date = new Date(existing_value_date_formatted);
//
//
//                var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_current_date.getTime()) / (oneDay)));
//                var defined_pdc_tenor = $("#pdc_tenor").val();
//
//
//                if (obj_current_date.getTime() >= obj_entered_date.getTime()) {
//                    $('#new_value_date').css('border-color', 'red');
//
//                    swal("Invalid Input !", "New value date cannot be today or backdated date. !", "error");
//                }
//
//                if (obj_existing_value_date.getTime() >= obj_entered_date.getTime()) {
//                    $('#new_value_date').css('border-color', 'red');
//                    swal("Invalid Input !", "New value date cannot be existing value date or backdated date to existing value date. !", "error");
//                }
//
//
//
//
//                if (diffDays > defined_pdc_tenor) {
//                    var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor);
//                    $('#pdc_tenor').css('border-color', 'red');
//                    $('#pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
//
//                    swal("Invalid Input !", "Tenor exceeded by " + pdc_tenor_diff + " days. !", "warning");
//                }
//
//            });






            $("#updated_value_date").focusout(function () {
                $('#updated_value_date').css('border-color', '');
                $('#new_pdc_tenor').css('border-color', '');
                $('#new_pdc_tenor_lable').text('');

                var oneDay = 24 * 60 * 60 * 1000;

                var obj_curr_date = new Date();
                var month = obj_curr_date.getMonth() + 1;
                var day = obj_curr_date.getDate();


                var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                var updated_value_date = $("#updated_value_date").val();

                if (updated_value_date === '') {
                    swal("Error!", "Please enter the new value date !", "error");
                    hasError = false;
                    return hasError;

                }

                var existing_value_date = $("#pdc_value_date").val();

                var existing_value_date_array = existing_value_date.split('/');
                var existing_value_date_day = existing_value_date_array[0];
                var existing_value_date_month = existing_value_date_array[1];
                var existing_value_date_year = existing_value_date_array[2];
                var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;



                var updated_value_date_arr = updated_value_date.split('/');
                var updated_value_date_day = updated_value_date_arr[0];
                var updated_value_date_month = updated_value_date_arr[1];
                var updated_value_date_year = updated_value_date_arr[2];
                var updated_value_date_formatted = updated_value_date_year + ',' + updated_value_date_month + ',' + updated_value_date_day;

                var entered_currentDate = current_date.split('/');
                var currentDate_day = entered_currentDate[0];
                var currentDate_month = entered_currentDate[1];
                var currentDate_year = entered_currentDate[2];
                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                var obj_updated_date = new Date(updated_value_date_formatted);
                var obj_current_date = new Date(currentDate_formatted);
                var obj_existing_value_date = new Date(existing_value_date_formatted);


                var diffDays = Math.round(Math.abs((obj_updated_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                var defined_pdc_tenor = $("#pdc_tenor").val();


                if (obj_current_date.getTime() > obj_updated_date.getTime()) {
                    $('#updated_value_date').css('border-color', 'red');

                    swal("Invalid Input !", "New value date cannot be backdated date. !", "error");
                    hasError = false;
                    return hasError;
                }

                if (diffDays > defined_pdc_tenor) {
                    var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
                    $('#new_pdc_tenor').css('border-color', 'red');
                    $('#new_pdc_tenor_lable').text('');
                    $('#new_pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
                }



            });

//            $('#updated_value_date').live('keypress', function (e) {
//
//                var oneDay = 24 * 60 * 60 * 1000;
//
//                var obj_curr_date = new Date();
//                var month = obj_curr_date.getMonth() + 1;
//                var day = obj_curr_date.getDate();
//
//
//                var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
//                var updated_value_date = $("#updated_value_date").val();
//
//                if (updated_value_date === '') {
//                    swal("Error!", "Please enter the new value date !", "error");
//                    hasError = false;
//                    return hasError;
//
//                }
//
//                var existing_value_date = $("#pdc_value_date").val();
//
//                var existing_value_date_array = existing_value_date.split('/');
//                var existing_value_date_day = existing_value_date_array[0];
//                var existing_value_date_month = existing_value_date_array[1];
//                var existing_value_date_year = existing_value_date_array[2];
//                var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;
//
//
//
//                var updated_value_date_arr = updated_value_date.split('/');
//                var updated_value_date_day = updated_value_date_arr[0];
//                var updated_value_date_month = updated_value_date_arr[1];
//                var updated_value_date_year = updated_value_date_arr[2];
//                var updated_value_date_formatted = updated_value_date_year + ',' + updated_value_date_month + ',' + updated_value_date_day;
//
//                var entered_currentDate = current_date.split('/');
//                var currentDate_day = entered_currentDate[0];
//                var currentDate_month = entered_currentDate[1];
//                var currentDate_year = entered_currentDate[2];
//                var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;
//
//                var obj_updated_date = new Date(updated_value_date_formatted);
//                var obj_current_date = new Date(currentDate_formatted);
//                var obj_existing_value_date = new Date(existing_value_date_formatted);
//
//
//                var diffDays = Math.round(Math.abs((obj_updated_date.getTime() - obj_current_date.getTime()) / (oneDay)));
//                var defined_pdc_tenor = $("#pdc_tenor").val();
//
//
//                if (obj_current_date.getTime() > obj_updated_date.getTime()) {
//                    $('#new_value_date').css('border-color', 'red');
//
//                    swal("Invalid Input !", "New value date cannot be backdated date. !", "error");
//                    hasError = false;
//                    return hasError;
//                }
//
//                if (diffDays > defined_pdc_tenor) {
//                    var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
//                    $('#new_pdc_tenor').css('border-color', 'red');
//                    $('#new_pdc_tenor_lable').text('');
//                    $('#new_pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
//                }
//
//
//
//            });

            function validateInputForActiveCheques() {
                var hasError = true;
                var pdc_product = 'NA';
                var pdc_chq_number = $("#pdc_chq_number").val();
                var cust_bank_code = $("#cust_bank_code").val();

                if ($("#pdc_product_rf").prop('checked')) {
                    pdc_product = 'RF';
                }
                if ($("#pdc_product_cw").prop('checked')) {
                    pdc_product = 'CW';
                }

                if (pdc_product === 'NA') {

                    swal("Error!", "Please select the product type !", "error");
                    hasError = false;
                }

                if (pdc_chq_number === '') {
                    $('#pdc_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (pdc_chq_number === '000000') {
                    $('#pdc_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (pdc_chq_number.length < 6) {
                    $('#pdc_chq_number').css('border-color', 'red');
                    hasError = false;
                }

                if (cust_bank_code === 'Select The Bank') {
                    $('#cust_bank_code').css('border-color', 'red');
                    hasError = false;
                }

                return hasError;
            }


            function validateUpdateDetails() {
                var hasError = true;

                var idndb_pdc_txn_master = $("#idndb_pdc_txn_master").val();

                var pdc_change = 'NA';



                if ($("#pdc_cheque_dtl_updt").prop('checked')) {
                    pdc_change = 'ELQ';
                }

                if ($("#pdc_cheque_vde").prop('checked')) {
                    pdc_change = 'VDE';
                }
                if ($("#pdc_cheque_elq").prop('checked')) {
                    pdc_change = 'ELQ';
                }

                if (idndb_pdc_txn_master === '') {
                    swal("Error!", "Please select a cheque for the update process. !", "error");
                    $('#pdc_chq_number').css('border-color', 'red');
                    $('#cust_bank_code').css('border-color', 'red');
                    $('#cust_branch_code').css('border-color', 'red');
                    hasError = false;
                    return hasError;
                }

                if (pdc_change === 'NA') {

                    swal("Error!", "Please select amendment type !", "error");
                    hasError = false;
                    return hasError;
                }





                if (pdc_change === 'VDE') {


                    var oneDay = 24 * 60 * 60 * 1000;

                    var obj_curr_date = new Date();
                    var month = obj_curr_date.getMonth() + 1;
                    var day = obj_curr_date.getDate();


                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                    var entered_date = $("#new_value_date").val();

                    if (entered_date === '') {
                        swal("Error!", "Please enter the new value date !", "error");
                        hasError = false;
                        return hasError;

                    }

                    var existing_value_date = $("#pdc_value_date").val();

                    var existing_value_date_array = existing_value_date.split('/');
                    var existing_value_date_day = existing_value_date_array[0];
                    var existing_value_date_month = existing_value_date_array[1];
                    var existing_value_date_year = existing_value_date_array[2];
                    var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;



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
                    var obj_existing_value_date = new Date(existing_value_date_formatted);



                    var diffDays = Math.round(Math.abs((obj_entered_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                    var defined_pdc_tenor = $("#pdc_tenor").val();


                    if (obj_current_date.getTime() >= obj_entered_date.getTime()) {
                        $('#new_value_date').css('border-color', 'red');

                        swal("Invalid Input !", "New value date cannot be today or backdated date. !", "error");
                        hasError = false;
                        return hasError;
                    }

                    if (obj_existing_value_date.getTime() >= obj_entered_date.getTime()) {
                        $('#new_value_date').css('border-color', 'red');
                        swal("Invalid Input !", "New value date cannot be existing value date or backdated date to existing value date. !", "error");
                        hasError = false;
                        return hasError;
                    }



                    if (diffDays > defined_pdc_tenor) {
                        var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
                        $('#pdc_tenor').css('border-color', 'red');
                        $('#pdc_tenor_lable').text('');
                        $('#pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');

//                        swal("Invalid Input !", "Tenor exceeded by " + pdc_tenor_diff + " days. !", "warning");
                    }




                }

                if (pdc_change === 'UPD') {


                    var oneDay = 24 * 60 * 60 * 1000;

                    var obj_curr_date = new Date();
                    var month = obj_curr_date.getMonth() + 1;
                    var day = obj_curr_date.getDate();


                    var current_date = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + obj_curr_date.getFullYear();
                    var updated_value_date = $("#updated_value_date").val();

                    if (updated_value_date === '') {
                        swal("Error!", "Please enter the new value date !", "error");
                        hasError = false;
                        return hasError;

                    }

                    var existing_value_date = $("#pdc_value_date").val();

                    var existing_value_date_array = existing_value_date.split('/');
                    var existing_value_date_day = existing_value_date_array[0];
                    var existing_value_date_month = existing_value_date_array[1];
                    var existing_value_date_year = existing_value_date_array[2];
                    var existing_value_date_formatted = existing_value_date_year + ',' + existing_value_date_month + ',' + existing_value_date_day;



                    var updated_value_date_arr = updated_value_date.split('/');
                    var updated_value_date_day = updated_value_date_arr[0];
                    var updated_value_date_month = updated_value_date_arr[1];
                    var updated_value_date_year = updated_value_date_arr[2];
                    var updated_value_date_formatted = updated_value_date_year + ',' + updated_value_date_month + ',' + updated_value_date_day;

                    var entered_currentDate = current_date.split('/');
                    var currentDate_day = entered_currentDate[0];
                    var currentDate_month = entered_currentDate[1];
                    var currentDate_year = entered_currentDate[2];
                    var currentDate_formatted = currentDate_year + ',' + currentDate_month + ',' + currentDate_day;

                    var obj_updated_date = new Date(updated_value_date_formatted);
                    var obj_current_date = new Date(currentDate_formatted);
                    var obj_existing_value_date = new Date(existing_value_date_formatted);


                    var diffDays = Math.round(Math.abs((obj_updated_date.getTime() - obj_current_date.getTime()) / (oneDay)));
                    var defined_pdc_tenor = $("#pdc_tenor").val();


                    if ($("#new_pdc_chq_number").val() === '') {
                        $('#new_pdc_chq_number').css('border-color', 'red');

                        swal("Invalid Input !", "New cheque number cannot be empty. !", "error");
                        hasError = false;
                        return hasError;
                    }

                    if ($("#new_pdc_chq_number").val() === '000000') {
                        $('#new_pdc_chq_number').css('border-color', 'red');

                        swal("Invalid Input !", "Invalid new cheque number. !", "error");
                        hasError = false;
                        return hasError;
                    }

                    if ($("#new_pdc_cheque_amu").val() === '0.00') {
                        $('#new_pdc_cheque_amu').css('border-color', 'red');

                        swal("Invalid Input !", "Invalid new cheque amount. !", "error");
                        hasError = false;
                        return hasError;
                    }

                    if ($("#new_pdc_cheque_amu").val() === '') {
                        $('#new_pdc_cheque_amu').css('border-color', 'red');

                        swal("Invalid Input !", "Invalid new cheque amount. !", "error");
                        hasError = false;
                        return hasError
                    }


                    if (obj_current_date.getTime() > obj_updated_date.getTime()) {
                        $('#pdc_value_date').css('border-color', 'red');

                        swal("Invalid Input !", "New value date cannot be backdated date. !", "error");
                        hasError = false;
                        return hasError;
                    }

                    if (diffDays > defined_pdc_tenor) {
                        var pdc_tenor_diff = parseInt(diffDays) - parseInt(defined_pdc_tenor)
                        $('#new_pdc_tenor').css('border-color', 'red');
                        $('#new_pdc_tenor_lable').text('');
                        $('#new_pdc_tenor_lable').text('Tenor exceeded by ' + pdc_tenor_diff + ' days.');
                    }

                }

                return hasError;
            }

        });


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
                        type: "application/csv;charset=utf -8;"
                    });
                    navigator.msSaveBlob(blob, 'UNAUTHRFCHQS.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'UN_AUTH_VDE_ELQ_DATA.xls');
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
            <div class="loader" id="loading-mask">                            
                <div class="loader_logo centered"  >

                </div>
            </div> 
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        RF & CW Liquidations and Value Date Extensions
                        <small>RF & CW  PDC Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Transactions</a></li>
                        <li class="active"> RF & CW Liquidations and Value Date Extensions</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content" id="contid" >

                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box box-default">
                                <form class="form-horizontal" >
                                    <input type="hidden" class="form-control" id="idndb_pdc_txn_master" name="idndb_pdc_txn_master">
                                    <input type="hidden" class="form-control" id="idndb_customer_define_seller_id" name="idndb_customer_define_seller_id">
                                    <input type="hidden" class="form-control" id="idndb_customer_define_buyer_id" name="idndb_customer_define_buyer_id">
                                    <input type="hidden" class="form-control" id="idndb_seller_has_buyers" name="idndb_seller_has_buyers">
                                    <input type="hidden" class="form-control" id="_system_date" name="_system_date" style="width: 350px" value="<%=_system_date%>">
                                    <input type="hidden" class="form-control" id="pdc_booking_date" name="pdc_booking_date" style="width: 350px">
                                    <input type="hidden" class="form-control" id="chq_wh_dr_to_cr_spe_narr_status" name="chq_wh_dr_to_cr_spe_narr_status" style="width: 350px">


                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Product</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="pdc_product_rf" name="pdc_product_rf" value="ACTIVE">Receivable Finance (RF) &nbsp;&nbsp;
                                                <input type="checkbox" id="pdc_product_cw" name="pdc_product_cw" value="ACTIVE">Cheque Warehousing (CW) &nbsp;
                                            </div>
                                        </div> 

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Cheque Number</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="pdc_chq_number" name="pdc_chq_number" style="width: 350px" maxlength="6">
                                            </div>
                                        </div>

                                        <div class="form-group" id="bank_form_select">
                                            <label class="col-sm-2 control-label" >Bank Name</label>
                                            <div class="col-sm-10">
                                                <select id="cust_bank" class="form-control" style="width: 350px;">
                                                    <option value="Select The Bank">Select The Bank</option>
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
                                                    <option id="Select The Bank" value="Select The Bank">Select The Bank</option>
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

                                        <div id="cust_cheque_details_div">

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Seller ID/Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_seller_id_name" name="pdc_seller_id_name" style="width: 350px">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Buyer ID/Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_buyer_id_name" name="pdc_buyer_id_name" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Branch / A/C Officer</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_branch_ac_officer" name="pdc_branch_ac_officer" style="width: 350px" >
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Cheque Value Date</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_value_date" name="pdc_value_date" placeholder="0.00" style="width: 350px">
                                                </div>
                                                <label class="col-sm-2 control-label" id="pdc_value_date_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                            </div>         

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Cheque Amount</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_cheque_amu" name="rf_cheque_amu" placeholder="0.00" style="width: 350px">
                                                </div>
                                                <label class="col-sm-2 control-label" id="pdc_cheque_amu_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                            </div>
                                            <div class="form-group" id="div_pdc_chq_wh_dr_to_cr_spe_narr">
                                                <label class="col-sm-2 control-label">DR/CR Transfer (Special Narration)</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="pdc_chq_wh_dr_to_cr_spe_narr" name="pdc_chq_wh_dr_to_cr_spe_narr" style="width: 350px" maxlength="35">
                                                </div>
                                            </div>   
                                            <hr>
                                            <div class="form-group" style="
                                                 color: red;
                                                 font-size: 15px;
                                                 ">
                                                <label class="col-sm-2 control-label">Amendment</label>
                                                <div class="col-sm-10">
                                                    <input type="checkbox" id="pdc_cheque_dtl_updt" name="pdc_cheque_dtl_updt" value="ACTIVE">Detail Update &nbsp;&nbsp;
                                                    <input type="checkbox" id="pdc_cheque_vde" name="pdc_cheque_vde" value="ACTIVE">Value date Extention &nbsp;&nbsp;
                                                    <input type="checkbox" id="pdc_cheque_elq" name="pdc_cheque_elq" value="ACTIVE">Early Lequidation&nbsp;
                                                </div>
                                            </div> 
                                            <div id="new_chq_val_date_div">
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">Defined Tenor</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="pdc_tenor" name="pdc_tenor" style="width: 350px">
                                                    </div>
                                                    <label class="col-sm-2 control-label" id="pdc_tenor_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>

                                                </div>
                                                <div class="form-group" >
                                                    <label class="col-sm-2 control-label">New Cheque Value Date</label>

                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask style="width: 350px" id="new_value_date">
                                                    </div><!-- /.input group -->
                                                    <label class="col-sm-2 control-label" id="new_value_date_lable" style="margin-top: -34px;margin-left: 540px;text-align: left;width: 350px"></label>
                                                </div>

                                            </div>
                                            <div id="div_pdc_chq_dtl_updt">
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">New Cheque Number</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="new_pdc_chq_number" name="pdc_chq_number" style="width: 350px" maxlength="6">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">New Cheque Amount</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="new_pdc_cheque_amu" name="new_rf_cheque_amu" placeholder="0.00" style="width: 350px">
                                                    </div>
                                                    <label class="col-sm-2 control-label" id="new_pdc_cheque_amu_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">New Bank Code</label>

                                                    <div class="col-sm-10">
                                                        <select id="new_cust_bank_code" class="form-control" style="width: 350px;">
                                                            <%    if (_session_availability) {

                                                                    out.print(new FillDataComboBean().getFileBankCodeDataCmb(session.getAttribute("userid").toString()));

                                                                }


                                                            %>
                                                        </select> 
                                                    </div>
                                                    <label class="col-sm-2 control-label" id="new_cust_bank_name_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>
                                                </div>
                                                <div class="form-group" id="div_new_cust_bank_name">
                                                    <label class="col-sm-2 control-label" >New Bank Name</label>
                                                    <div class="col-sm-10">
                                                        <select id="new_cust_bank_name" class="form-control" style="width: 350px;">
                                                            <%    if (_session_availability) {

                                                                    out.print(new FillDataComboBean().getFileBankNameDataCmb(session.getAttribute("userid").toString()));

                                                                }


                                                            %>
                                                        </select> </div>
                                                </div>       

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">New Branch Code</label>
                                                    <div class="col-sm-10">
                                                        <select id="new_cust_branch_code" class="form-control" style="width: 350px;">
                                                        </select>
                                                    </div>
                                                    <label class="col-sm-2 control-label" id="new_cust_branch_name_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>

                                                </div>
                                                <div class="form-group" id="div_new_cust_branch_name">
                                                    <label class="col-sm-2 control-label">New Branch Name</label>
                                                    <div class="col-sm-10">
                                                        <select id="new_cust_branch_name" class="form-control" style="width: 350px;">
                                                        </select>
                                                    </div>
                                                </div>    
                                                <div class="form-group" id="div_pdc_update_chq_wh_dr_to_cr_spe_narr">
                                                    <label class="col-sm-2 control-label">DR/CR Transfer (Special Narration)</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="pdc_update_chq_wh_dr_to_cr_spe_narr" name="pdc_update_chq_wh_dr_to_cr_spe_narr" style="width: 350px" maxlength="35">
                                                    </div>
                                                </div>         

                                                <div class="form-group" >
                                                    <label class="col-sm-2 control-label">New Cheque Value Date</label>

                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask style="width: 350px" id="updated_value_date">
                                                    </div><!-- /.input group -->
                                                    <label class="col-sm-2 control-label" id="updated_value_date_lable" style="margin-top: -34px;margin-left: 540px;text-align: left;width: 350px"></label>
                                                </div> 
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">Defined Tenor</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="new_pdc_tenor" name="new_pdc_tenor" style="width: 350px">
                                                    </div>
                                                    <label class="col-sm-2 control-label" id="new_pdc_tenor_lable" style="margin-top: -34px;margin-left: 540px;text-align: left"></label>

                                                </div>



                                            </div>


                                        </div><!-- /.box-body -->
                                </form>
                                <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#shareholder-data-model" id="shareholder-data-model-button">Open Duplicate Certificate Modal</button>                            
                                <div id="shareholder-data-model" class="modal fade" role="dialog">
                                    <div class="modal-dialog" style="width: 80%;height: 80%;">                                        <!-- Modal content-->
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">PDC Cheque Details</h4>
                                            </div>
                                            <div class="modal-body">
                                                <p>Duplicate PDC Cheque Details.</p>
                                                <table  class="table table-bordered" id="certificate-shareholder-model-data-table" >
                                                    <thead>
                                                        <tr>
                                                            <th style="display:none;">#UID</th>
                                                            <th>Cheque Number</th>
                                                            <th>Bank / Branch Code</th>
                                                            <th>Seller ID / Seller Name</th>
                                                            <th>Buyer ID / Buyer Name</th>                                                            
                                                            <th>Value Date</th>
                                                            <th>Amount</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="certificate-shareholder-model-data-table-body">

                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal" id="shareholder-data-model-close-button">Close</button>
                                            </div>

                                        </div>

                                    </div>
                                </div>
                                <!--<hr>-->
                                <div class="box-footer" style="padding-top: 3%;">
                                    <button type="button" class="btn btn-info pull-right" id="btn_cancel" name="btn_cancel">Cancel</button>
                                    <button type="button" class="btn btn-info pull-right" id="btn_pdc_unauth_view" name="btn_pdc_view_unauth_data" style="margin-right: 0.5%;">&nbsp;&nbsp;View&nbsp;&nbsp;</button>
                                    <button type="button" class="btn btn-info pull-right" id="btn_pdc_ve_elq_update" name="btn_pdc_ve_elq_update" style="margin-right: 0.5%;">Submit</button>


                                </div><!-- /.box-footer -->
                                <hr>


                            </div><!-- /.box -->




                        </div><!-- /.col (left) -->
                    </div><!-- /.row -->

                </section></br><!-- /.content -->

                <section class="content">


                    <div class="row" id="dev_unauthdata">
                        <div class="col-xs-12" style="margin-top: -76px;">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Un-Authorized RF & CW Liquidations and Value Date Extensions</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Product</th>
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
                                            <%  // if (_session_availability) {
//                                                    out.print(new FIllDataTableBean().getPdcVdeElqUnauthData(session.getAttribute("userid").toString()));
                                                // }
%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Product</th>
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
                                        function getChequeDetails(idndb_pdc_txn_master) {

                                            var paramid = "getChequeDetails";
                                            var values = "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                                                    "&paramid=" + paramid;


                                            $.ajax({
                                                type: "POST",
                                                url: "/NDBRMS/txn_data_retrive",
                                                data: values,
                                                dataType: "json",
                                                //if received a response from the server
                                                success: function (data) {

                                                    if ((Object.keys(data).length) === 0) {
                                                        swal("Invalid Input !", "Cannot found the selected PDC cheque. !", "error");
                                                    } else {
                                                        $.each(data, function (k, v) {

                                                            $("#cust_cheque_details_div").show();
                                                            document.getElementById("pdc_product_rf").disabled = true;
                                                            document.getElementById("pdc_product_cw").disabled = true;
                                                            document.getElementById("cust_bank_code").disabled = true;
                                                            document.getElementById("cust_branch_code").disabled = true;
                                                            $("#pdc_chq_number").prop('readonly', true);
                                                            if (v.pdc_chq_status_auth === 'UN-AUTHORIZED') {
                                                                document.getElementById("pdc_cheque_vde").disabled = true;
                                                                document.getElementById("pdc_cheque_elq").disabled = true;

                                                            }

                                                            $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);
                                                            $('#idndb_seller_has_buyers').val(v.idndb_seller_has_buyers);
                                                            $('#idndb_customer_define_seller_id').val(v.idndb_customer_define_seller_id);
                                                            $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer_id);
                                                            $('#pdc_seller_id_name').val(v.seller_cust_id + ' | ' + v.seller_cust_name);
                                                            $('#pdc_buyer_id_name').val(v.buyer_cust_id + ' | ' + v.buyer_cust_name);
                                                            $('#pdc_value_date').val(v.pdc_value_date);
                                                            $('#new_value_date').val(v.pdc_value_date);
                                                            $('#pdc_cheque_amu').val(v.pdc_chq_amu);
                                                            $('#new_pdc_cheque_amu').val(v.pdc_chq_amu);
                                                            $('#pdc_booking_date').val(v.pdc_booking_date);

                                                            if ($("#pdc_product_rf").prop('checked')) {
                                                                $('#pdc_tenor').val(v.shb_facty_det_tenor);
                                                                $('#new_pdc_tenor').val(v.shb_facty_det_tenor);
                                                                $("#div_pdc_chq_wh_dr_to_cr_spe_narr").hide();
                                                                $("#div_pdc_update_chq_wh_dr_to_cr_spe_narr").hide();


                                                                if (!$("#_system_date").val() === v.pdc_booking_date) {
                                                                    $("#new_pdc_cheque_amu").prop('readonly', true);
                                                                }
                                                            }
                                                            if ($("#pdc_product_cw").prop('checked')) {
                                                                $('#pdc_tenor').val(v.sl_has_byr_warehs_tenor);
                                                                $('#new_pdc_tenor').val(v.sl_has_byr_warehs_tenor);
                                                                $('#pdc_chq_wh_dr_to_cr_spe_narr').val(v.pdc_chq_wh_dr_to_cr_spe_narr);
                                                                $('#pdc_update_chq_wh_dr_to_cr_spe_narr').val(v.pdc_chq_wh_dr_to_cr_spe_narr);

                                                                $("#div_pdc_chq_wh_dr_to_cr_spe_narr").show();
                                                                $("#div_pdc_update_chq_wh_dr_to_cr_spe_narr").show();
                                                                if (!($("#_system_date").val() === v.pdc_booking_date)) {
                                                                    $("#new_pdc_cheque_amu").prop('readonly', true);
                                                                }

                                                            }

                                                            $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc)


                                                            $('#new_pdc_chq_number').val(v.pdc_chq_number);
                                                            $('#new_cust_bank_code').val(v.idndb_bank_master_file);

                                                            $('#new_cust_bank_name').val(v.idndb_bank_master_file);
                                                            $('#new_cust_bank_name_lable').text($("#new_cust_bank_name option:selected").text());


                                                            addBranches(v.idndb_bank_master_file, v.idndb_branch_master_file)
                                                            $('#new_cust_branch_code').val(v.idndb_branch_master_file);
                                                            $('#updated_value_date').val(v.pdc_value_date);
//                                                            $('#shareholder-data-model-close-button').click();

                                                        });

                                                    }

                                                }
                                            });

                                        }

                                        function addBranches(idndb_bank_master_file, idndb_branch_master_file) {


                                            var paramid = "get_bank_brnch_data";
                                            var values = "&bnkcodecmb_bank_id=" + idndb_bank_master_file +
                                                    "&paramid=" + paramid;


                                            $.ajax({
                                                type: "POST",
                                                url: "/NDBRMS/CustomerDataRetrive",
                                                data: values,
                                                dataType: "json", //if received a response from the server
                                                success: function (data) {


                                                    $.each(data, function (k, v) {

                                                        $('#new_cust_branch_name').append($("<option/>", {
                                                            value: v.idndb_branch_master_file,
                                                            text: v.branch_name
                                                        }));

                                                        $('#new_cust_branch_code').append($("<option/>", {
                                                            value: v.idndb_branch_master_file,
                                                            text: v.branch_id
                                                        }));

                                                    });

                                                    $('#new_cust_branch_name').val(idndb_branch_master_file);
                                                    $('#new_cust_branch_code').val(idndb_branch_master_file);

                                                    $('#new_cust_branch_name_lable').text($("#new_cust_branch_name option:selected").text());
                                                }
                                            });





                                        }

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
