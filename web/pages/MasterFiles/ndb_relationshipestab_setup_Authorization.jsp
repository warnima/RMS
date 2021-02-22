<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>NDB BANK | Relationship Establishment Auth</title>
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
        $(function () {
        <%
            boolean _session_availability = true;
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }
            MenuDAO menuDAO = new MenuDAO();

        %>
            window.onload = function () {

                document.getElementById("idndb_customer_define_seller_id").disabled = true;
                document.getElementById("idndb_customer_define_seller_name").disabled = true;
                document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                document.getElementById("idndb_customer_define_buyer_name").disabled = true;
//                document.getElementById("sl_has_byr_rf").disabled = true;
//                document.getElementById("sl_has_byr_cw").disabled = true;
                document.getElementById("chx_chqware_tran").disabled = true;
                document.getElementById("chx_chqware_fixed").disabled = true;
                document.getElementById("chx_recfin_tran").disabled = true;
                document.getElementById("chx_recfin_fixed").disabled = true;
                document.getElementById("rf_fixed_rate_daily").disabled = true;
                document.getElementById("rf_fixed_rate_weekly").disabled = true;
                document.getElementById("rf_fixed_rate_monthly").disabled = true;
                document.getElementById("rf_fixed_rate_yearly").disabled = true;
                document.getElementById("cw_fixed_rate_daily").disabled = true;
                document.getElementById("cw_fixed_rate_weekly").disabled = true;
                document.getElementById("cw_fixed_rate_monthly").disabled = true;
                document.getElementById("cw_fixed_rate_yearly").disabled = true;


                $("#sl_has_byr_warehs_limit").prop('readonly', true);
                $("#sl_has_byr_warehs_limit_lable").prop('readonly', true);
                $("#sl_has_byr_warehs_otstaning").prop('readonly', true);
                $("#sl_has_byr_warehs_tenor").prop('readonly', true);
                $("#sl_has_byr_warehs_fmax_chq_amu").prop('readonly', true);
                $("#cw_tran_base_falt_fee").prop('readonly', true);
                $("#cw_tran_base_from_tran").prop('readonly', true);
                $("#cw_fixed_rate_amount").prop('readonly', true);
                $("#sl_has_byr_cdt_loan_amu").prop('readonly', true);
                $("#sl_has_byr_fmax_chq_amu").prop('readonly', true);
                $("#sl_has_byr_otstaning").prop('readonly', true);
                $("#sl_has_byr_tenor").prop('readonly', true);
                $("#sl_has_byr_itst_trsry").prop('readonly', true);
                $("#rf_tran_base_falt_fee").prop('readonly', true);
                $("#rf_tran_base_from_tran").prop('readonly', true);
                $("#rf_fixed_rate_amount").prop('readonly', true);
                $("#sl_has_byr_advnce_rate_prstage").prop('readonly', true);
                $("#sl_has_byr_remarks").prop('readonly', true);
                $("#sl_has_byr_active").prop('readonly', true);
                $("#sl_has_byr_active").prop('disabled', true);
                $("#statusReason").prop('readonly', true);

                $('#sl_has_byr_rf').attr('disabled', false);
                $('#sl_has_byr_cw').attr('disabled', false);

                $("#div_chequ_dis_prestage").hide();
                $("#div_chequ_dis_flatfee").hide();
                $("#div_cheque_dis").hide();
                $("#div_cw_details").hide();

                $("#div_rec_fin_trn_base").hide();
                $("#div_rec_fin_fixed_base").hide();
                $("#div_chqweare_trn_base").hide();
                $("#div_chqweare_fixed_base").hide();
                $("#loading-mask").hide();

            };

            $('#btn_cancel').click(function () {
                location.reload();
            });
            $('#mactive').click(function (e) {
                $(this).closest('table').find('td input:checkbox').prop('checked', this.checked);
            });




            $("#sl_has_byr_warehs_limit").focusout(function () {
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


            $("#sl_has_byr_warehs_otstaning").focusout(function () {
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


            $("#sl_has_byr_warehs_fmax_chq_amu").focusout(function () {
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


            $("#sl_has_byr_cdt_loan_amu").focusout(function () {
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


            $("#sl_has_byr_fmax_chq_amu").focusout(function () {
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


            $("#sl_has_byr_otstaning").focusout(function () {
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


            $("#cw_tran_base_from_tran").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });


            $("#cw_tran_base_from_tran").focusout(function () {
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


            $("#rf_tran_base_from_tran").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#rf_tran_base_from_tran").focusout(function () {
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



            $("#cw_fixed_rate_amount").focusout(function () {
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


            $("#rf_fixed_rate_amount").focusout(function () {
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


            $("#sl_has_byr_warehs_tenor").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#sl_has_byr_tenor").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }
            });

            $("#sl_has_byr_itst_trsry").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#sl_has_byr_advnce_rate_prstage").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#chx_recfin_tran").change(function () {
                if (this.checked) {
                    $("#div_rec_fin_trn_base").show();
                    $("#chx_recfin_fixed").prop('checked', false);
                    $("#div_rec_fin_fixed_base").hide();

                } else {
                    $("#div_rec_fin_trn_base").hide();
                }
            });

            $("#chx_recfin_fixed").change(function () {
                if (this.checked) {
                    $("#div_rec_fin_fixed_base").show();
                    $("#chx_recfin_tran").prop('checked', false);
                    $("#div_rec_fin_trn_base").hide();
                } else {
                    $("#div_rec_fin_fixed_base").hide();
                }
            });


            $("#sl_has_byr_rf").change(function () {
                if (this.checked) {
                    $("#div_cheque_dis").show();
                    $("#div_cw_details").hide();
                    $("#sl_has_byr_cw").prop('checked', false);
                } else {
                    $("#div_cheque_dis").hide();
                    $("#div_cw_details").hide();
                }
            });

            $("#sl_has_byr_cw").change(function () {
                if (this.checked) {
                    $("#div_cw_details").show();
                    $("#div_cheque_dis").hide();
                    $("#sl_has_byr_rf").prop('checked', false);
                } else {
                    $("#div_cheque_dis").hide();
                    $("#div_cw_details").hide();

                }
            });


            $("#ch_faci_details").change(function () {
                if (this.checked) {
                    $("#div_facility_details").show();
                } else {
                    $("#div_facility_details").hide();
                }
            });


            $("#chx_chqware_tran").change(function () {
                if (this.checked) {
                    $("#div_chqweare_trn_base").show();
                    $("#chx_chqware_fixed").prop('checked', false);
                    $("#div_chqweare_fixed_base").hide();
                } else {
                    $("#div_chqweare_trn_base").hide();
                }
            });

            $("#chx_chqware_fixed").change(function () {
                if (this.checked) {
                    $("#div_chqweare_fixed_base").show();
                    $("#chx_chqware_tran").prop('checked', false);
                    $("#div_chqweare_trn_base").hide();
                } else {
                    $("#div_chqweare_fixed_base").hide();
                }
            });




            $("#ch_chequ_dis_prestage").change(function () {
                if (this.checked) {
                    $("#div_chequ_dis_prestage").show();
                    $("#div_chequ_dis_flatfee").hide();
                    $("#ch_chequ_dis_flatfee").prop('checked', false);
                } else {
                    $("#div_chequ_dis_prestage").hide();
                }
            });

            $("#ch_chequ_dis_flatfee").change(function () {
                if (this.checked) {
                    $("#div_chequ_dis_flatfee").show();
                    $("#div_chequ_dis_prestage").hide();
                    $("#ch_chequ_dis_prestage").prop('checked', false);


                } else {
                    $("#div_chequ_dis_flatfee").hide();
                }
            });


            $("#idndb_customer_define_seller_id").change(function () {

                $("#div_cw_details").hide();
                $("#div_cheque_dis").hide();
                $("#sl_has_byr_cw").prop('checked', false);
                $("#sl_has_byr_rf").prop('checked', false);
                $('#sl_has_byr_rf').attr('disabled', true);
                $('#sl_has_byr_cw').attr('disabled', true);

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();

                $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);

                var paramid = "get_customer_products"
                var values = "&idndb_cust_prod_map=" + $("#idndb_customer_define_seller_id").val().trim() +
                        "&paramid=" + paramid;

                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {

                            if (v.prod_relationship_res_fin === 'ACTIVE') {
                                $("#sl_has_byr_rf").attr('disabled', false);
                            }
                            if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                $("#sl_has_byr_cw").attr('disabled', false);
                            }


                        });
                    }
                });



            });


            $("#idndb_customer_define_seller_name").change(function () {

                $("#div_cw_details").hide();
                $("#div_cheque_dis").hide();
                $("#sl_has_byr_cw").prop('checked', false);
                $("#sl_has_byr_rf").prop('checked', false);
                $('#sl_has_byr_rf').attr('disabled', true);
                $('#sl_has_byr_cw').attr('disabled', true);

                var idndb_customer_define_seller_name = $("#idndb_customer_define_seller_name").val();

                $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_name);
                var paramid = "get_customer_products"
                var values = "&idndb_cust_prod_map=" + $("#idndb_customer_define_seller_name").val().trim() +
                        "&paramid=" + paramid;

                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {

                            if (v.prod_relationship_res_fin === 'ACTIVE') {
                                $('#sl_has_byr_rf').attr('disabled', false);
                            }
                            if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                $('#sl_has_byr_cw').attr('disabled', false);
                            }


                        });
                    }
                });



            });


            $("#idndb_customer_define_buyer_id").change(function () {

                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);



            });


            $("#idndb_customer_define_buyer_name").change(function () {

                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();

                $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_name);



            });

            $("#btn_sller_has_buyer_auth").click(function (e) {

                $("#loading-mask").show();
                //    if (validateInput()) {


                var paramid = "sl_has_byr_auth_data_save";
                $('input[type="submit"]').prop('disabled', false);

                var checkValues = $('input[name=active]:checked').map(function ()
                {
                    return $(this).val();
                }).get();


                $("#btn_sller_has_buyer_auth").prop("disabled", true);
                var values = "&idndb_seller_has_buyers=" + checkValues +
                        "&paramid=" + paramid;



                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerServletDate",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {
                        if (data.success) {
                            window.location = "modalsSucess.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=Relationship estabilishment data authorized successfuly !";

                        } else if (data.systemna) {
                            window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=" + data.systemna + "";

                        } else if (data.error) {
                            window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=Error occured in authorizing relationship estabilishment data.";

                        }
                    }
                });
                //   }

            });

            $("#btn_sller_has_buyer_reject").click(function (e) {

                $("#loading-mask").show();
                //  if (validateInput()) {
                $('input[type="submit"]').prop('disabled', false);

                var checkValues = $('input[name=active]:checked').map(function ()
                {
                    return $(this).val();
                }).get();

                $("#btn_sller_has_buyer_reject").prop("disabled", true);
                var paramid = "sl_has_byr_reject_data_save";
                var values = "&idndb_seller_has_buyers=" + checkValues +
                        "&paramid=" + paramid;



                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerServletDate",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {
                        if (data.success) {
                            window.location = "modalsSucess.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=Bank customer relationship estabilishment data rejected successfuly !";

                        } else if (data.systemna) {
                            window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=" + data.systemna + "";

                        } else if (data.error) {
                            window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup_Authorization.jsp&message=Error occured in rejecting customer Relationship estabilishment data.";

                        }
                    }
                });
                //   }

            });


            $("#example1 tbody tr").on("click", function (event) {

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }

                $(this).css("background-color", "#3c8dbc");
                pickedup = $(this);

                var idndb_seller_has_buyers = $(this).find("td").eq(0).html();
                var paramid = "get_rel_mestb_data_tofill";
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
                            $("#div_chequ_dis_prestage").hide();
                            $("#div_chequ_dis_flatfee").hide();
                            $("#div_cheque_dis").hide();
                            $("#div_cw_details").hide();

                            $("#div_rec_fin_trn_base").hide();
                            $("#div_rec_fin_fixed_base").hide();
                            $("#div_chqweare_trn_base").hide();
                            $("#div_chqweare_fixed_base").hide();
                            $("#sl_has_byr_rf").prop('checked', false);
                            $("#sl_has_byr_cw").prop('checked', false);

                            $('#idndb_seller_has_buyers').val(v.idndb_seller_has_buyers);
                            $('#idndb_customer_define_seller_id').val(v.idndb_customer_define_seller);
                            $('#idndb_customer_define_seller_name').val(v.idndb_customer_define_seller);
                            $('#idndb_customer_define_buyer_id').val(v.idndb_customer_define_buyer);
                            $('#idndb_customer_define_buyer_name').val(v.idndb_customer_define_buyer);


                            if (v.sl_has_byr_prorm_type === 'RF') {
                                $("#sl_has_byr_rf").prop('checked', true);
                                $("#div_cheque_dis").show();
                                $("#div_cw_details").hide();

                            } else if (v.sl_has_byr_prorm_type === 'CW') {
                                $("#sl_has_byr_cw").prop('checked', true);
                                $("#div_cheque_dis").hide();
                                $("#div_cw_details").show();
                            }



                            //    $('#sl_has_byr_fcilty_acc_no').val(v.sl_has_byr_facilty_ac_no);
                            $('#sl_has_byr_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);////getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977                                
                            $('#sl_has_byr_cdt_loan_amu').val(v.buyers_loan_percentage);
                            //$('#sl_has_byr_cdt_loan_amu').val(v.shb_facty_det_crd_loam_limit);
                            $('#sl_has_byr_otstaning').val(v.shb_facty_det_os);
                            $('#sl_has_byr_tenor').val(v.shb_facty_det_tenor);
                            $('#sl_has_byr_itst_trsry').val(v.shb_facty_det_irest_trry);
                            //   $('#sl_has_byr_min_comm_limt').val(v.shb_facty_det_main_cmiss_limit);
                            //   $('#sl_has_byr_chq_disc_intst').val(v.shb_chq_dis_ricing_intrest);


                            if (v.rec_finance_commision_crg === 'TRANSACTION BASED') {
                                $("#chx_recfin_tran").prop('checked', true);
                                $("#div_rec_fin_trn_base").show();
                            } else if (v.rec_finance_commision_crg === 'FIXED CHARGE BASED') {
                                $("#chx_recfin_fixed").prop('checked', true);
                                $("#div_rec_fin_fixed_base").show();
                            }

                            $("#rf_tran_base_falt_fee").val(v.rf_tran_base_falt_fee);
                            $("#rf_tran_base_from_tran").val(v.rf_tran_base_from_tran);
                            $("#rf_fixed_rate_amount").val(v.rf_fixed_rate_amount);

                            if (v.rf_fixed_frequency === 'DAILY') {
                                $("#rf_fixed_rate_daily").prop('checked', true);
                            }
                            if (v.rf_fixed_frequency === 'WEEKLY') {
                                $("#rf_fixed_rate_weekly").prop('checked', true);
                            }
                            if (v.rf_fixed_frequency === 'MONTHLY') {
                                $("#rf_fixed_rate_monthly").prop('checked', true);
                            }
                            if (v.rf_fixed_frequency === 'YEARLY') {
                                $("#rf_fixed_rate_yearly").prop('checked', true);
                            }

                            if (v.chq_wh_commision_crg === 'TRANSACTION BASED') {
                                $("#chx_chqware_tran").prop('checked', true);
                                $("#div_chqweare_trn_base").show();
                            } else if (v.chq_wh_commision_crg === 'FIXED CHARGE BASED') {
                                $("#chx_chqware_fixed").prop('checked', true);
                                $("#div_chqweare_fixed_base").show();
                            }

                            $("#cw_tran_base_falt_fee").val(v.cw_tran_base_falt_fee);
                            $("#cw_tran_base_from_tran").val(v.cw_tran_base_from_tran);
                            $("#cw_fixed_rate_amount").val(v.cw_fixed_rate_amount);

                            if (v.cw_fixed_frequency === 'DAILY') {
                                $("#cw_fixed_rate_daily").prop('checked', true);
                            }
                            if (v.cw_fixed_frequency === 'WEEKLY') {
                                $("#cw_fixed_rate_weekly").prop('checked', true);
                            }
                            if (v.cw_fixed_frequency === 'MONTHLY') {
                                $("#cw_fixed_rate_monthly").prop('checked', true);
                            }
                            if (v.cw_fixed_frequency === 'YEARLY') {
                                $("#cw_fixed_rate_yearly").prop('checked', true);
                            }

                            // alert(v.shb_chq_dis_adv_rate_prectange);
                            $("#sl_has_byr_advnce_rate_prstage").val(v.shb_chq_dis_adv_rate_prectange);


                            $("#sl_has_byr_remarks").val(v.sl_has_byr_remarks);

                            if (v.sl_has_byr_status === 'ACTIVE') {
                                $("#sl_has_byr_active").prop('checked', true);
                            } else {
                                $("#sl_has_byr_active").prop('checked', false);
                            }

                            $('#statusReason').val(v.statusReason);


                            // cw facility etails
                            $("#sl_has_byr_warehs_limit").val(v.sl_has_byr_warehs_limit);
                            $("#sl_has_byr_warehs_otstaning").val(v.sl_has_byr_warehs_otstaning);
                            $("#sl_has_byr_warehs_tenor").val(v.sl_has_byr_warehs_tenor);
                            $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);



                        });
                    }
                });
            });





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
                    navigator.msSaveBlob(blob, 'CUST REL ESTB UNAUTH DATA.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'CUST REL ESTB UNAUTH DATA.xls');
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
            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        Relationship Establishment Authorization
                        <small>Linking Buyer To The Seller</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Relationship Establishment Authorization</li>
                    </ol>
                </section>

                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box box-info">
                                <form class="form-horizontal">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Seller ID</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_seller_id" style="width: 350px;">
                                                    <option>Select The Seller</option>
                                                    <% if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Id_ActiveSellerDataCmb(session.getAttribute("userid").toString()));

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

                                                            out.print(new FillDataComboBean().getCust_Name_ActiveSellerDataCmb(session.getAttribute("userid").toString()));

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
                                                    <%   if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Id_ActiveBuyerDataCmb(session.getAttribute("userid").toString()));

                                                        }


                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Buyer Name</label>
                                            <div class="col-sm-10">
                                                <select class="form-control" id="idndb_customer_define_buyer_name" style="width: 350px;">
                                                    <option>Select The Buyer</option>
                                                    <%  if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Name_ActiveBuyerDataCmb(session.getAttribute("userid").toString()));

                                                        }


                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Program Type</label>
                                            <div class="col-sm-10">
                                                <input type="hidden" class="form-control" id="idndb_seller_has_buyers" name="idndb_seller_has_buyers" style="width: 350px">
                                                <input type="checkbox" id="sl_has_byr_rf">Receivable Finance
                                                <input type="checkbox" id="sl_has_byr_cw">Cheque Warehousing
                                            </div>
                                        </div>

                                        <!--       <div class="form-group">
                                                   <label class="col-sm-2 control-label">Program Type</label>
                                                   <div class="col-sm-10">
                                                       <select class="form-control" id="sl_has_byr_prorm_type" style="width: 350px;">
                                                           <option value="1">Cheque Discounting</option>
                                                       </select>
                                                   </div>
                                               </div>
                                        
                                               <div class="form-group">
                                                   <label class="col-sm-2 control-label">Facility A/C No.</label>
                                                   <div class="col-sm-10">
                                                       <input type="text" class="form-control" id="sl_has_byr_fcilty_acc_no" name="sl_has_byr_fcilty_acc_no" style="width: 350px">
                                                      
                                                   </div>
                                               </div>
                                        
                                        -->


                                    </div><!-- /.box-body -->
                                </form>
                            </div><!-- /.box -->


                            <div class="box box-info" id="div_cw_details">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Cheque Warehousing</h3>
                                </div><!-- /.box-header -->
                                <form class="form-horizontal">

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Warehousing limit Percentage(%)</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_limit" name="sl_has_byr_warehs_limit" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                        <label class="col-sm-2 control-label" id="sl_has_byr_warehs_limit_lable" style="margin-top: -34px;margin-left: 580px;text-align: left;width: 573px;"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Maximum Per Cheque Amount</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_fmax_chq_amu" name="sl_has_byr_warehs_fmax_chq_amu" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_otstaning" name="sl_has_byr_warehs_otstaning" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_tenor" name="sl_has_byr_warehs_tenor" value="120" placeholder="Days" style="width: 350px">
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Commission Charge</label>
                                        <div class="col-sm-10">
                                            <input type="checkbox" id="chx_chqware_tran"> Transaction Based
                                            <input type="checkbox" id="chx_chqware_fixed"> Fixed Charge Based
                                        </div>
                                    </div>

                                    <div id="div_chqweare_trn_base">
                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Transaction Based</h6>
                                        </div><!-- /.box-header -->
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Flat Fee</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_tran_base_falt_fee" name="cw_tran_base_falt_fee" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">From Transaction (%)</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_tran_base_from_tran" name="cw_tran_base_from_tran" placeholder="0.00" style="width: 350px">
                                            </div>
                                        </div>


                                    </div>

                                    <div id="div_chqweare_fixed_base">

                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Fixed Charge Based</h6>
                                        </div><!-- /.box-header -->



                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_fixed_rate_amount" name="cw_fixed_rate_amount" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Frequency</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="cw_fixed_rate_daily">Daily
                                                <input type="checkbox" id="cw_fixed_rate_weekly">Weekly
                                                <input type="checkbox" id="cw_fixed_rate_monthly">Monthly
                                                <input type="checkbox" id="cw_fixed_rate_yearly">Yearly
                                            </div>
                                        </div>

                                    </div>

                                </form>
                            </div>

                            <div class="box box-info" id="div_cheque_dis">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Receivable Financing</h3>
                                </div><!-- /.box-header -->
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Credit Loan Percentage(%)</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_cdt_loan_amu" name="sl_has_byr_cdt_loan_amu" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                        <label class="col-sm-2 control-label" id="sl_has_byr_cdt_loan_amu_lable" style="margin-top: -34px;margin-left: 580px;text-align: left;width: 573px;"></label>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Maximum Per Cheque Amount</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_fmax_chq_amu" name="sl_has_byr_fmax_chq_amu" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_otstaning" name="sl_has_byr_otstaning" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_tenor" name="sl_has_byr_tenor" placeholder="Days" value="120" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Interest (Treasury)</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_itst_trsry" name="sl_has_byr_itst_trsry" placeholder="0.00 %" style="width: 350px">
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Commission Charge</label>
                                        <div class="col-sm-10">
                                            <input type="checkbox" id="chx_recfin_tran"> Transaction Based
                                            <input type="checkbox" id="chx_recfin_fixed"> Fixed Charge Based
                                        </div>
                                    </div>

                                    <div id="div_rec_fin_trn_base">
                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Transaction Based</h6>
                                        </div><!-- /.box-header -->
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Flat Fee</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_tran_base_falt_fee" name="rf_tran_base_falt_fee" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">From Transaction (%)</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_tran_base_from_tran" name="rf_tran_base_from_tran" placeholder="0.00" style="width: 350px">
                                            </div>
                                        </div>


                                    </div>

                                    <div id="div_rec_fin_fixed_base">

                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Fixed Charge Based</h6>
                                        </div><!-- /.box-header -->

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_fixed_rate_amount" name="rf_fixed_rate_amount" placeholder="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Frequency</label>
                                            <div class="col-sm-10">
                                                <input type="checkbox" id="rf_fixed_rate_daily">Daily
                                                <input type="checkbox" id="rf_fixed_rate_weekly">Weekly
                                                <input type="checkbox" id="rf_fixed_rate_monthly">Monthly
                                                <input type="checkbox" id="rf_fixed_rate_yearly">Yearly
                                            </div>
                                        </div>

                                    </div>

                                    <div class="box-header with-border">
                                        <h6 class="box-title" style="font-size: 17px;">Advance Rate</h6>
                                    </div><!-- /.box-header -->
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Percentage</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_advnce_rate_prstage" name="sl_has_byr_advnce_rate_prstage" placeholder="0.00 %" style="width: 350px">
                                        </div>
                                    </div>

                                </form>
                            </div>

                            <div class="box box-info">

                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"></label>

                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Remarks</label>
                                        <div class="col-sm-10">
                                            <textarea type="" class="form-control" id="sl_has_byr_remarks" name="sl_has_byr_remarks" style="width: 350px;height: 150px"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Buyer To Seller Link</label>
                                        <div class="col-sm-1">
                                            <input type="checkbox" id="sl_has_byr_active"> Active
                                        </div>

                                        <div class="col-sm-4">
                                            <input type="text" id="statusReason" class="form-control" placeholder="Reason for Activating/De-activating"/>
                                        </div>
                                        <div class="col-sm-5"></div>
                                    </div>


                                </form>
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right" id="btn_sller_has_buyer_reject">Rejected</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_sller_has_buyer_auth" style="margin-right: 5px">Authorized</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_cancel" style="margin-right: 5px">Cancel</button>
                                </div><!-- /.box-footer -->
                            </div>
                        </div><!-- /.box -->
                    </div>


                </section>
                <!-- Main content -->
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Customer Relationship Establishment Un-Authorized Data</h3>
                                    <a href="#" id="test" onClick="javascript:fnExcelReport();">Click Here To Download</a>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Seller</th>
                                                <th>Buyer</th>
                                                <th>Program Type</th>
                                                <th>Changes</th>
                                                <th>Approval</th>
                                                <th>Modify by</th>
                                                <th>Modify Date</th>
                                                <th><div class="controls"><input class="check_boxes optional" type="checkbox" name="mactive" id="mactive" value="Y"/></div></th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getCustomerRelationEstblishUnAuthData(session.getAttribute("userid").toString()));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Seller</th>
                                                <th>Buyer</th>
                                                <th>Program Type</th>
                                                <th>Changes</th>
                                                <th>Approval</th>
                                                <th>Modify by</th>
                                                <th>Modify Date</th>
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