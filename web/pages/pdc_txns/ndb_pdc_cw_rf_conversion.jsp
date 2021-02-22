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
        <title>NDB BANK | CW to RF Conversion</title>
        <link rel="shortcut icon" href="../../dist/img/NDBBANK.png" alt="..." class="img-circle"/>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">>
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
            MenuDAO menuDAO = new MenuDAO();

            comboDAO cmbDAO = new comboDAO();
            String _system_date = cmbDAO.getSystemDate();
        %>

        $(function () {
            window.onload = function () {


                $("#bank_form_select").hide();
                $("#old_buyer_data").hide();
                $("#branch_form_select").hide();
                $("#dev_butn").hide();
                $("#liq_proccess").hide();



                $("#sl_has_byr_rms_tenor").prop('readonly', true);
                $("#Tenor").prop('readonly', true);

                $("#cw_buyer_id").prop('readonly', true);
                $("#cw_buyer_name").prop('readonly', true);

                document.getElementById("pdc_branch_ac_officer").disabled = true;

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




            $('#btn_cancel').click(function () {
                location.reload();
            });


            $("#rf_cheque_amu").keyup(function () {
                $(this).format({format: "#,###.00", locale: "us"});

            });


            $("#idndb_customer_define_buyer_id").change(function () {

                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                var paramid = "get_buyer_rel_data_cw_to_rf";
                var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $.each(data, function (k, v) {
                            $('#sl_has_byr_rms_tenor').val(v.shb_facty_det_tenor);
                            $('#sl_has_byr_rms_limit').val(v.shb_facty_det_crd_loam_limit);
                            $('#sl_has_byr_rms_otstaning').val(v.shb_facty_det_os);
                            $('#sl_has_byr_rms_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);
                            $('#sl_has_byr_rms_balance').val(v.sl_has_byr_rms_balance);


                        });
                    }
                });

            });


            $("#idndb_customer_define_buyer_name").change(function () {

                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();

                $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_name);

                var idndb_seller_has_buyers = idndb_customer_define_buyer_name;
                var paramid = "get_buyer_rel_data_cw_to_rf";
                var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                        "&paramid=" + paramid;


                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {


                        $.each(data, function (k, v) {
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





            });


            $("#idndb_customer_define_seller_name").change(function () {

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_name").val();
                $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_id);

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
                                text: v.cust_id}));


                            $('#idndb_customer_define_buyer_name').append($("<option/>", {
                                value: v.idndb_seller_has_buyers, text: v.cust_name
                            }));

                            $('#pdc_branch_ac_officer').val(v.branch_name + " | " + v.geo_market_desc)

                        });
                    }
                });


            });


            $("#cust_bank_code").change(function () {

                var cust_bank_code = $("#cust_bank_code").val();


                $('#cust_bank').val(cust_bank_code);
                var bank_name = $("#cust_bank option:selected").text();
                $('#selected_bank_name').text(bank_name);

                var bnkcodecmb_bank_id = $("#cust_bank").val();

                $('#cust_bank_code').val(bnkcodecmb_bank_id);
                $('#bnkcodecmb_name').val(bnkcodecmb_bank_id);

                var paramid = "get_bank_brnch_data";
                var values = "&bnkcodecmb_bank_id=" + bnkcodecmb_bank_id + "&paramid=" + paramid;


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


            $("#cust_branch_code").change(function () {
                if (validateInputRFAE()) {
                    var paramid = "isChqNumAllReadyExistForCwToRF";
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
                                var cw_to_rf_availability = v.cw_to_rf_availability;
                                if (pdc_req_financing === 'CW' && cw_to_rf_availability === 'true') {
                                    if (confirm('Cheque allready exist and available for CW to RF conversion ! Do you want to convertr this cheque')) {
                                        $('#idndb_pdc_txn_master').val(v.idndb_pdc_txn_master);
                                        addSellers(v.idndb_customer_define_seller_id);
                                        //alert(v.idndb_customer_define_buyer_id);
                                        addBuyers(v.idndb_customer_define_seller_id, v.idndb_customer_define_buyer_id);


                                        // alert(v.idndb_customer_define_buyer_id);
                                        //  $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer_id);
                                        //   $('#idndb_customer_define_buyer_name').val(v.idndb_customer_define_buyer_id);

                                        //setBuyerDetails(v.idndb_customer_define_buyer_id);

                                        $("#old_buyer_data").show();
                                        $('#rf_chq_number').val(v.pdc_chq_number);
                                        $('#cw_buyer_name').val(v.cust_name);
                                        $('#cw_buyer_id').val(v.cust_id);

                                        $('#cust_bank').val(v.idndb_bank_master_file);
                                        $('#cust_bank_code').val(v.idndb_bank_master_file);

                                        addBranches(v.idndb_bank_master_file, v.idndb_branch_master_file);

                                        $('#cust_branch').val(v.idndb_branch_master_file);
                                        $('#cust_branch_code').val(v.idndb_branch_master_file);

                                        $('#rf_value_date').val(v.pdc_value_date);
                                        $('#rf_cheque_amu').val(v.pdc_chq_amu);
                                        $('#sl_has_byr_rms_tenor').val(v.cw_tenor);
                                        $("#dev_butn").show();

                                        var rf_value_date = $("#rf_value_date").val();
                                        var currentDate = $("#_system_date").val();

                                        var DATEVAL = "YES";

                                        if (rf_value_date === currentDate) {

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

                                        var rms_system_date = $("#_system_date").val();
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

                                        //alert("Tenor" + tennor_ahead)
                                        //alert("Tenor Exceed date" + newformatteddate)
                                        //alert("Entered date" + date_define)

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
                                            var currentDate = $("#_system_date").val();
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


                                        $("#rf_chq_number").prop('readonly', true);
                                        $("#rf_value_date").prop('readonly', true);
                                        $("#rf_cheque_amu").prop('readonly', true);
                                        $("#Tenor").prop('readonly', true);
                                        $("#sl_has_byr_rms_tenor").prop('readonly', true);

                                        document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                        document.getElementById("idndb_customer_define_seller_name").disabled = true;
                                        // document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                                        // document.getElementById("idndb_customer_define_buyer_name").disabled = true;
                                        document.getElementById("cust_bank_code").disabled = true;
                                        document.getElementById("cust_branch_code").disabled = true;





                                    }

                                }
                                if (pdc_req_financing === 'CW' && cw_to_rf_availability === 'false') {
                                    alert("Cheque allready exist in CW product. Cheque not available for converion because buyer not available in RF product");
                                    $("#dev_butn").hide();

                                }




                            });
                        }
                    });



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















            $("#btn_cd_rf_pdc_cover_save").click(function (e) {


                if (validateInput()) {

                    document.getElementById("btn_cd_rf_pdc_cover_save").disabled = true;
                    document.getElementById("btn_cancel").disabled = true;


                    var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();
                    var rf_cheque_amu = $("#rf_cheque_amu").val();

                    var cust_bank_code = $('#cust_bank_code option:selected').text().trim();
                    var cust_branch_code = $('#cust_branch_code option:selected').text().trim();

                    var cus_buyer_id = $('#idndb_customer_define_buyer_id option:selected').text().trim();
                    var cus_buyer_name = $('#idndb_customer_define_buyer_name option:selected').text().trim();


                    rf_cheque_amu = rf_cheque_amu.replace(/\,/g, '');
                    $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
                    var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
                    var paramid = "get_buyer_rel_data_cw_to_rf";
                    var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
                            "&rf_cheque_amu=" + rf_cheque_amu +
                            "&cust_bank_code=" + cust_bank_code +
                            "&cust_branch_code=" + cust_branch_code +
                            "&paramid=" + paramid;



                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {


                            $.each(data, function (k, v) {

                                var message = ' CW to RF COversion entry .';




                                // 1125, but a string, so convert it to number
                                rf_cheque_amu = parseFloat(rf_cheque_amu);


                                if (!(v.sl_has_byr_max_chq_amu === '0')) {

                                    if (rf_cheque_amu > v.sl_has_byr_max_chq_amu) {

                                        message = message + 'Buyer per cheque amount limit has exceeded.(per cheque amount limit : ' + v.sl_has_byr_max_chq_amu_banner + ')</br>';



                                    }
                                }




//                                if (v.sl_has_byr_rms_balance < rf_cheque_amu) {
//                                    message = message + 'Buyer receviable finance Loan Limit has exceeded. (Available balance : ' + v.sl_has_byr_rms_balance_banner + ' )</br> ' + v.seller_message + '.';
//
//
//
//                                }
                                if (v.sl_has_byr_rms_balance < 0) {
                                    message = message + 'Buyer receviable finance Loan Limit has exceeded. (Available balance : ' + v.sl_has_byr_rms_balance_banner + ' )</br> ' + v.seller_message + '.';



                                }

                                if (v.m_buyer_acc_dtails_availability === true && v.m_buyer_acc_dtails_validity === false) {
                                    message = message + '. Cheque bank, branch and account details are not available for the selected buyer (Buyer ID : ' + cus_buyer_id + ', Buyer Name : ' + cus_buyer_name + ').(Bank Code :' + cust_bank_code + ', Branch code : ' + cust_branch_code + '). < /br> ';

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
                                    var oneDay = 24 * 60 * 60 * 1000;
                                    var currentDate = $("#_system_date").val();
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
                                    message = message + 'RF Cheque warehousing tenor has exceeded. (Defined Tenor : ' + tennor_ahead + '. Exeeded Tenor :' + diffDays + ')</br>';




                                }

                                //alert(message);
                                if (!(message === '')) {


                                    var paramid = "save_cw_to_rf_pdc_conversion_entry";
                                    var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                            "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                            "&rf_chq_number=" + $("#rf_chq_number").val().trim() +
                                            "&cust_bank=" + $("#cust_bank").val().trim() +
                                            "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                            "&cust_branch=" + $("#cust_branch").val().trim() +
                                            "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                            "&rf_value_date=" + $("#rf_value_date").val().trim() +
                                            "&rf_cheque_liquidation=" + ($("#rf_cheque_liquidation").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                            "&rf_cheque_amu=" + rf_cheque_amu +
                                            "&paramid=" + paramid;

                                    window.location = "modalsWarningCWRF.jsp?desigURL=ndb_pdc_cw_rf_conversion.jsp&message=" + message + "&action=Do you want to conversion with override transaction ?&" + values + ""

                                } else {

                                    var paramid = "save_cw_to_rf_pdc_conversion_entry";
                                    var values = "&idndb_customer_define_seller_id=" + $("#idndb_customer_define_seller_id").val().trim() +
                                            "&idndb_customer_define_buyer_id=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                            "&idndb_pdc_txn_master=" + $("#idndb_pdc_txn_master").val().trim() +
                                            "&rf_chq_number=" + $("#rf_chq_number").val().trim() +
                                            "&cust_bank=" + $("#cust_bank").val().trim() +
                                            "&cust_bank_code=" + $('#cust_bank_code option:selected').text().trim() +
                                            "&cust_branch=" + $("#cust_branch").val().trim() +
                                            "&cust_branch_code=" + $('#cust_branch_code option:selected').text().trim() +
                                            "&rf_value_date=" + $("#rf_value_date").val().trim() +
                                            "&rf_cheque_liquidation=" + ($("#rf_cheque_liquidation").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                            "&rf_cheque_amu=" + rf_cheque_amu +
                                            "&paramid=" + paramid;

                                    window.location = "modalsWarningCWRF.jsp?desigURL=ndb_pdc_cw_rf_conversion.jsp&message=" + message + "&action=Do you want to conversion transaction ?&" + values + ""


                                }



                            });
                        }
                    });

                }

            });

            $("#rf_value_date").focusout(function () {

                var rf_value_date = $("#rf_value_date").val();
                var currentDate = $("#_system_date").val();

                var DATEVAL = "YES";

                if (rf_value_date === currentDate) {

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

                var rms_system_date = $("#_system_date").val();
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
                    var currentDate = $("#_system_date").val();
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



            });


            $('#rf_value_date').live('keypress', function (e) {

                var rf_value_date = $("#rf_value_date").val();
                var currentDate = $("#_system_date").val();

                var DATEVAL = "YES";

                if (rf_value_date === currentDate) {

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

                var rms_system_date = $("#_system_date").val();
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
                    var currentDate = $("#_system_date").val();
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



            });

            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "#3c8dbc");
                pickedup = $(this);

                var idndb_pdc_txn_master = $(this).find("td").eq(0).html();
                var paramid = "get_PDC_CW_RF_CONV_txn_data_to_fill";
                var values = "&idndb_pdc_txn_master=" + idndb_pdc_txn_master +
                        "&paramid=" + paramid;

                // alert(values);
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
                            $('#rf_cheque_amu').val(v.pdc_chq_amu);





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
                var rf_cheque_amu = $("#rf_cheque_amu").val();



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

                if (rf_value_date === currentDate) {

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


                if (rf_cheque_amu === '') {
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


        function addBuyers(idndb_customer_define_seller_id, idndb_customer_define_buyer_id) {
            //  alert("buyer set")


            $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);


            var paramid = "get_sellers_buyer_data_for_rms";
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

                    // $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_id);
                    //  $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);


                }
            });
            // alert("buyer set")




        }

        function addSellers(idndb_customer_define_seller_id) {

            var paramid = "get_sellers_rms_sellers";
            var values = "&paramid=" + paramid;


            $.ajax({
                type: "POST",
                url: "/NDBRMS/CustomerDataRetrive",
                data: values,
                dataType: "json",
                //if received a response from the server
                success: function (data) {



                    $('#idndb_customer_define_seller_id')
                            .empty()
                            .append('<option selected="selected" value="Select The Seller">Select The Buyer</option>');

                    $('#idndb_customer_define_seller_name')
                            .empty()
                            .append('<option selected="selected" value="Select The Seller">Select The Buyer</option>');



                    $.each(data, function (k, v) {


                        $('#idndb_customer_define_seller_id').append($("<option/>", {
                            value: v.idndb_cust_prod_map,
                            text: v.cust_id
                        }));


                        $('#idndb_customer_define_seller_name').append($("<option/>", {
                            value: v.idndb_cust_prod_map,
                            text: v.cust_name
                        }));

                    });


                    $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_id);
                    $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);
                }
            });
            // alert("buyer set")




        }




        function setBuyerDetails(idndb_customer_define_buyer_id) {




            // $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);
            var idndb_seller_has_buyers = idndb_customer_define_buyer_id;
            var paramid = "get_buyer_rel_data";
            var values = "&idndb_seller_has_buyers=" + idndb_seller_has_buyers +
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
                        Manual Entry PDC CW TO RF Conversion Entry
                        <small>PDC PDC CW to RF Conversion Data</small>
                    </h1>

                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Transactions</a></li>
                        <li class="active">Manual Entry PDC CW TO RF Conversion Entry</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content" id="contid" >

                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box box-default">
                                <form class="form-horizontal" id="bank_form" name="bank_form" > 

                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <%
                                                        if (_session_availability) {

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
                                                    <% if (_session_availability) {

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
                                        <div id="old_buyer_data">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">CW Buyer ID</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cw_buyer_id" name="Tenor" style="width: 350px">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">CW Buyer Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="cw_buyer_name" name="Tenor" style="width: 350px">
                                                </div>
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
                                                    <%                                                        if (_session_availability) {
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
                                                <input type="text" class="form-control" id="rf_cheque_amu" name="rf_cheque_amu" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">

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
                                                <input type="hidden" class="form-control" id="idndb_pdc_txn_master" name="idndb_pdc_txn_master" style="width: 350px">
                                                <input type="hidden" class="form-control" id="_system_date" name="_system_date" style="width: 350px" value="<%=_system_date%>">
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
                                <div class="box-footer" id="dev_butn">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel" name="btn_holiday_clear">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cd_rf_pdc_cover_save" name="btn_holiday_save">Submit</button>
                                </div><!-- /.box-footer -->


                            </div><!-- /.box -->



                        </div><!-- /.col (left) -->
                    </div><!-- /.row -->

                </section>
            </div>
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
