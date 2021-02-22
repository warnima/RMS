<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>NDB BANK | Relationship Establishment</title>
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
            UserBean user = null;
            MenuDAO menuDAO = null;
            String user_type = "";
            if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
                _session_availability = false;
                response.sendRedirect("/NDBRMS/index.jsp");
            }
            if (_session_availability) {
                user = (UserBean) (session.getAttribute("user"));
                user_type = user.getCode();
                menuDAO = new MenuDAO();
            }

        %>
            window.onload = function () {

        <% if (user_type.equals("12") || user_type.equals("9") || user_type.equals("10") || user_type.equals("11")) { %>
                document.getElementById("idndb_customer_define_seller_id").disabled = true;
                document.getElementById("idndb_customer_define_seller_name").disabled = true;
                document.getElementById("idndb_customer_define_buyer_id").disabled = true;
                document.getElementById("idndb_customer_define_buyer_name").disabled = true;
                document.getElementById("sl_has_byr_rf").disabled = true;
                document.getElementById("sl_has_byr_cw").disabled = true;
                document.getElementById("chx_chqware_tran").disabled = true;
                document.getElementById("chx_chqware_fixed").disabled = true;
                document.getElementById("chx_recfin_tran").disabled = true;
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
                $("#statusReason").prop('readonly', true);
                document.getElementById("save_sl_has_byr_data").disabled = true;


        <%}  %>


//                $('#sl_has_byr_rf').attr('disabled', true);
//                $('#sl_has_byr_cw').attr('disabled', true);

                $("#div_chequ_dis_prestage").hide();
                $("#div_chequ_dis_flatfee").hide();
                $("#div_cheque_dis").hide();
                $("#div_cw_details").hide();
                $("#tre_int_rate").hide();

                $("#div_rec_fin_trn_base").hide();
                $("#div_rec_fin_fixed_base").hide();
                $("#div_chqweare_trn_base").hide();
                $("#div_chqweare_fixed_base").hide();

                $("#sl_has_byr_active").prop('checked', true);
                $("#loading-mask").hide();

            };


            $("#sl_has_byr_warehs_limit").keyup(function () {
                if (this.value.match(/[^0-9]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $('#sl_has_byr_warehs_limit').on('keydown keyup', function (e) {
                if ($(this).val() > 100
                        && e.keyCode !== 46 // keycode for delete
                        && e.keyCode !== 8 // keycode for backspace
                        ) {
                    e.preventDefault();
                    $(this).val(100);
                }
            });

            $("#sl_has_byr_warehs_limit").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#sl_has_byr_warehs_otstaning").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#sl_has_byr_warehs_otstaning").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#sl_has_byr_warehs_fmax_chq_amu").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#sl_has_byr_warehs_fmax_chq_amu").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#sl_has_byr_cdt_loan_amu").keyup(function () {
                if (this.value.match(/[^0-9]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $('#sl_has_byr_cdt_loan_amu').on('keydown keyup', function (e) {
                if ($(this).val() > 100
                        && e.keyCode !== 46 // keycode for delete
                        && e.keyCode !== 8 // keycode for backspace
                        ) {
                    e.preventDefault();
                    $(this).val(100);
                }
            });

            $("#sl_has_byr_cdt_loan_amu").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#sl_has_byr_fmax_chq_amu").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#sl_has_byr_fmax_chq_amu").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#sl_has_byr_otstaning").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#sl_has_byr_otstaning").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#cw_tran_base_falt_fee").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#cw_tran_base_falt_fee").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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
                    this.value = this.value.replace(/[^0-9/.]/g, '');

                    if (this.value.indexOf(".") >= 0) {
                        var x = this.value.split('.');
                        var x1 = x[0];
                        var x2 = x[1];



                        if (x2.length > 4) {
                            var res = x2.substring(0, 4);

                            //x2 = x2.replace(this.value.slice(-1), '');
                            this.value = x1 + '.' + res;

                        }
                    }


                    this.value = addCommas(this.value);

                }



            });

            $("#cw_tran_base_from_tran").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#rf_tran_base_falt_fee").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#rf_tran_base_falt_fee").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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
                    this.value = this.value.replace(/[^0-9/.]/g, '');

                    if (this.value.indexOf(".") >= 0) {
                        var x = this.value.split('.');
                        var x1 = x[0];
                        var x2 = x[1];



                        if (x2.length > 4) {
                            var res = x2.substring(0, 4);

                            //x2 = x2.replace(this.value.slice(-1), '');
                            this.value = x1 + '.' + res;

                        }
                    }


                    this.value = addCommas(this.value);

                }



            });
            $("#rf_tran_base_from_tran").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9-/.]/g, '');
                }

            });

            $("#cw_fixed_rate_amount").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#cw_fixed_rate_amount").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#rf_fixed_rate_amount").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');

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
            $("#rf_fixed_rate_amount").focusout(function () {
                this.value = this.value.replace(/[^0-9/.]/g, '');

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

            $("#rf_fixed_rate_daily").change(function () {
                if (this.checked) {
                    $("#rf_fixed_rate_weekly").prop('checked', false);
                    $("#rf_fixed_rate_monthly").prop('checked', false);
                    $("#rf_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#rf_fixed_rate_weekly").change(function () {
                if (this.checked) {
                    $("#rf_fixed_rate_daily").prop('checked', false);
                    $("#rf_fixed_rate_monthly").prop('checked', false);
                    $("#rf_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#rf_fixed_rate_monthly").change(function () {
                if (this.checked) {
                    $("#rf_fixed_rate_daily").prop('checked', false);
                    $("#rf_fixed_rate_weekly").prop('checked', false);
                    $("#rf_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#rf_fixed_rate_yearly").change(function () {
                if (this.checked) {
                    $("#rf_fixed_rate_daily").prop('checked', false);
                    $("#rf_fixed_rate_weekly").prop('checked', false);
                    $("#rf_fixed_rate_monthly").prop('checked', false);
                }
            });




            $("#cw_fixed_rate_daily").change(function () {
                if (this.checked) {
                    $("#cw_fixed_rate_weekly").prop('checked', false);
                    $("#cw_fixed_rate_monthly").prop('checked', false);
                    $("#cw_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#cw_fixed_rate_weekly").change(function () {
                if (this.checked) {
                    $("#cw_fixed_rate_daily").prop('checked', false);
                    $("#cw_fixed_rate_monthly").prop('checked', false);
                    $("#cw_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#cw_fixed_rate_monthly").change(function () {
                if (this.checked) {
                    $("#cw_fixed_rate_daily").prop('checked', false);
                    $("#cw_fixed_rate_weekly").prop('checked', false);
                    $("#cw_fixed_rate_yearly").prop('checked', false);
                }
            });
            $("#cw_fixed_rate_yearly").change(function () {
                if (this.checked) {
                    $("#cw_fixed_rate_daily").prop('checked', false);
                    $("#cw_fixed_rate_weekly").prop('checked', false);
                    $("#cw_fixed_rate_monthly").prop('checked', false);
                }
            });




            $("#idndb_customer_define_seller_id").change(function () {

                $("#loading-mask").show();

                $("#div_cw_details").hide();
                $("#div_cheque_dis").hide();
                $("#sl_has_byr_cw").prop('checked', false);
                $("#sl_has_byr_rf").prop('checked', false);
                $('#sl_has_byr_rf').attr('disabled', true);
                $('#sl_has_byr_cw').attr('disabled', true);

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();

                $('#idndb_customer_define_seller_name').val(idndb_customer_define_seller_id);

                var paramid = "get_customer_products"
                var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_id +
                        "&paramid=" + paramid;
                //  alert(values);
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json", //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {

                            if (v.prod_relationship_res_fin === 'ACTIVE') {
                                $('#sl_has_byr_rf').attr('disabled', false);
                                $('#sl_has_byr_advnce_rate_prstage').val(v.rec_finance_financing);
                                $('#setup_rf_seller_advance_rate').val(v.rec_finance_financing);
                                $('#sl_has_byr_tenor').val(v.rec_finance_tenor);
                                $('#setup_rf_seller_tenor').val(v.rec_finance_tenor);
                                seller_rec_fin_limit = parseFloat(v.seller_rec_fin_limit);


                            }
                            if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                $('#sl_has_byr_cw').attr('disabled', false);
                                $('#sl_has_byr_warehs_fmax_chq_amu').val(v.sl_has_byr_fmax_chq_amu);
                                $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_tenor);
                                $('#setup_cw_seller_tenor').val(v.sl_has_byr_tenor);
                                seller_chq_wh_limit = parseFloat(v.seller_chq_wh_limit);
                            }

                            $("#loading-mask").hide();


                        });
                    }
                });



            });


            $("#idndb_customer_define_seller_name").change(function () {
                $("#loading-mask").show();
                $("#div_cw_details").hide();
                $("#div_cheque_dis").hide();
                $("#sl_has_byr_cw").prop('checked', false);
                $("#sl_has_byr_rf").prop('checked', false);
                $('#sl_has_byr_rf').attr('disabled', true);
                $('#sl_has_byr_cw').attr('disabled', true);


                var idndb_customer_define_seller_name = $("#idndb_customer_define_seller_name").val();

                $('#idndb_customer_define_seller_id').val(idndb_customer_define_seller_name);
                var paramid = "get_customer_products"
                var values = "&idndb_cust_prod_map=" + idndb_customer_define_seller_name +
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
                                $('#sl_has_byr_advnce_rate_prstage').val(v.rec_finance_financing);
                                $('#setup_rf_seller_advance_rate').val(v.rec_finance_financing);
                                $('#sl_has_byr_tenor').val(v.rec_finance_tenor);
                                $('#setup_rf_seller_tenor').val(v.rec_finance_tenor);
                                seller_rec_fin_limit = parseFloat(v.seller_rec_fin_limit);

                            }
                            if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                $('#sl_has_byr_cw').attr('disabled', false);
                                $('#sl_has_byr_warehs_fmax_chq_amu').val(v.sl_has_byr_fmax_chq_amu);
                                $('#sl_has_byr_warehs_tenor').val(v.sl_has_byr_tenor);
                                $('#setup_cw_seller_tenor').val(v.sl_has_byr_tenor);
                                seller_chq_wh_limit = parseFloat(v.seller_chq_wh_limit);
                            }

                            $("#loading-mask").hide();

                        });
                    }
                });



            });


            $("#idndb_customer_define_buyer_id").change(function () {
                $("#loading-mask").show();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();

                $('#idndb_customer_define_buyer_name').val(idndb_customer_define_buyer_id);

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var paramid = "get_rel_mestb_data_tofillSelected";
                var values = "&idndb_customer_define_seller_id=" + idndb_customer_define_seller_id +
                        "&idndb_customer_define_buyer_id=" + idndb_customer_define_buyer_id +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        if ((Object.keys(data).length) === 0) {
                            $("#loading-mask").hide();
                        } else {
                            $.each(data, function (k, v) {
                                if (v.data_availablity === 'ACTIVE') {
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
                                    $('#sl_has_byr_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);

                                    ////getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977                                
                                    $('#sl_has_byr_cdt_loan_amu').val(v.buyers_loan_percentage);
//                                seller_rec_fin_limit=parseFloat(v.seller_rec_fin_limit);
//                                seller_chq_wh_limit=parseFloat(v.seller_chq_wh_limit);
                                    //$('#sl_has_byr_cdt_loan_amu').val(v.shb_facty_det_crd_loam_limit);


                                    $('#sl_has_byr_otstaning').val(v.shb_facty_det_os);
                                    $('#sl_has_byr_tenor').val(v.shb_facty_det_tenor);
                                    $('#setup_rf_seller_tenor').val(v.seller_tenor);
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

                                    //  alert(v.shb_chq_dis_adv_rate_prectange);
                                    $("#sl_has_byr_advnce_rate_prstage").val(v.shb_chq_dis_adv_rate_prectange);
                                    $("#setup_rf_seller_advance_rate").val(v.seller_advnc_prtage);


                                    $("#sl_has_byr_remarks").val(v.sl_has_byr_remarks);

                                    if (v.sl_has_byr_status === 'ACTIVE') {
                                        $("#sl_has_byr_active").prop('checked', true);
                                    } else {
                                        $("#sl_has_byr_active").prop('checked', false);
                                    }
                                    $("#statusReason").val(v.statusReason);



                                    // cw facility etails
                                    $("#sl_has_byr_warehs_limit").val(v.sl_has_byr_warehs_limit);
//                                seller_chq_wh_limit=v.sl_has_byr_warehs_limit;
                                    $("#sl_has_byr_warehs_otstaning").val(v.sl_has_byr_warehs_otstaning);
                                    $("#sl_has_byr_warehs_tenor").val(v.sl_has_byr_warehs_tenor);
                                    $("#setup_cw_seller_tenor").val(v.cw_seller_tenor);
                                    $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);
                                    $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);
                                    document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                    document.getElementById("idndb_customer_define_seller_name").disabled = true;

                                }

                                $("#loading-mask").hide();

                            });


                        }


                    }
                });




            });


            $("#idndb_customer_define_buyer_name").change(function () {
                $("#loading-mask").show();
                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();

                $('#idndb_customer_define_buyer_id').val(idndb_customer_define_buyer_name);

                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var paramid = "get_rel_mestb_data_tofillSelected";
                var values = "&idndb_customer_define_seller_id=" + idndb_customer_define_seller_id +
                        "&idndb_customer_define_buyer_id=" + idndb_customer_define_buyer_name +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        if ((Object.keys(data).length) === 0) {
                            $("#loading-mask").hide();
                        } else {
                            $.each(data, function (k, v) {
                                if (v.data_availablity === 'ACTIVE') {
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
                                    $('#sl_has_byr_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);
                                    ////getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977                                
                                    $('#sl_has_byr_cdt_loan_amu').val(v.buyers_loan_percentage);
//                                seller_rec_fin_limit=parseFloat(v.seller_rec_fin_limit);
//                                seller_chq_wh_limit=parseFloat(v.seller_chq_wh_limit);
                                    //$('#sl_has_byr_cdt_loan_amu').val(v.shb_facty_det_crd_loam_limit);

                                    $('#sl_has_byr_otstaning').val(v.shb_facty_det_os);
                                    $('#sl_has_byr_tenor').val(v.shb_facty_det_tenor);
                                    $('#setup_rf_seller_tenor').val(v.seller_tenor);
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

                                    //  alert(v.shb_chq_dis_adv_rate_prectange);
                                    $("#sl_has_byr_advnce_rate_prstage").val(v.shb_chq_dis_adv_rate_prectange);
                                    $("#setup_rf_seller_advance_rate").val(v.seller_advnc_prtage);


                                    $("#sl_has_byr_remarks").val(v.sl_has_byr_remarks);

                                    if (v.sl_has_byr_status === 'ACTIVE') {
                                        $("#sl_has_byr_active").prop('checked', true);
                                    } else {
                                        $("#sl_has_byr_active").prop('checked', false);
                                    }

                                    $("#statusReason").val(v.statusReason);


                                    // cw facility etails
                                    $("#sl_has_byr_warehs_limit").val(v.sl_has_byr_warehs_limit);
                                    $("#sl_has_byr_warehs_otstaning").val(v.sl_has_byr_warehs_otstaning);
                                    $("#sl_has_byr_warehs_tenor").val(v.sl_has_byr_warehs_tenor);
                                    $("#setup_cw_seller_tenor").val(v.cw_seller_tenor);
                                    $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);
                                    $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);
                                    document.getElementById("idndb_customer_define_seller_id").disabled = true;
                                    document.getElementById("idndb_customer_define_seller_name").disabled = true;

                                }
                                $("#loading-mask").hide();
                            });


                        }


                    }
                });



            });

            $("#save_sl_has_byr_data").click(function (e) {


                if (validateInput()) {
                    $("#loading-mask").show();

                    $("#save_sl_has_byr_data").prop("disabled", true);


                    if ($("#sl_has_byr_cw").prop('checked')) {
                        var paramid = "validateSellerCWLimt";
                        var values = "&idndb_customer_define_seller=" + $("#idndb_customer_define_seller_id").val().trim() +
                                "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val().trim() +
                                "&paramid=" + paramid;
                        // alert(values);

                        $.ajax({
                            type: "POST",
                            url: "/NDBRMS/CustomerDataRetrive",
                            data: values,
                            dataType: "json", //if received a response from the server
                            success: function (data) {
                                $.each(data, function (k, v) {
                                    //  alert(v.available_sub_limt);
                                    ///   alert( $("#sl_has_byr_warehs_limit").val().trim().replace(/\,/g, ''));

                                    //---------------------------------------------------//
                                    //Commented on 10.04.2018 - not required to check the limit since percentage is using - CFU-BRD-4 -- Janaka_5977
//                                    if (v.available_sub_limt < $("#sl_has_byr_warehs_limit").val().trim().replace(/\,/g, '')) {
//                                        //   alert(v.available_sub_limt)
//                                        $('#sl_has_byr_warehs_limit_lable').text("Seller Limit Exceeded . Sub Limit Available for :" + v.available_sub_limt_formtted + "");
//
//                                    } else {
                                    //---------------------------------------------------//
                                    {
                                        var statusReason = ($("#statusReason").val() !== '') ? $("#statusReason").val().trim() : "";
                                        var paramid = "save_sl_has_byr_data";
                                        var values = "&idndb_customer_define_seller=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val().trim() +
                                                "&sl_has_byr_prorm_type_rf=" + ($("#sl_has_byr_rf").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&sl_has_byr_prorm_type_cw=" + ($("#sl_has_byr_cw").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                //cw facility details    

                                                "&sl_has_byr_warehs_limit=" + $("#sl_has_byr_warehs_limit").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_warehs_otstaning=" + $("#sl_has_byr_warehs_otstaning").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_warehs_tenor=" + $("#sl_has_byr_warehs_tenor").val().trim() +
                                                "&sl_has_byr_warehs_fmax_chq_amu=" + $("#sl_has_byr_warehs_fmax_chq_amu").val().trim().replace(/\,/g, '') +
                                                "&chx_recfin_tran=" + ($("#chx_recfin_tran").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&chx_recfin_fixed=" + ($("#chx_recfin_fixed").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_tran_base_falt_fee=" + $("#rf_tran_base_falt_fee").val().trim().replace(/\,/g, '') +
                                                "&rf_tran_base_from_tran=" + $("#rf_tran_base_from_tran").val().trim().replace(/\,/g, '') +
                                                "&rf_fixed_rate_amount=" + $("#rf_fixed_rate_amount").val().trim().replace(/\,/g, '') +
                                                "&rf_fixed_rate_daily=" + ($("#rf_fixed_rate_daily").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_weekly=" + ($("#rf_fixed_rate_weekly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_monthly=" + ($("#rf_fixed_rate_monthly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_yearly=" + ($("#rf_fixed_rate_yearly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                //rf facility details buyer


                                                "&sl_has_byr_fmax_chq_amu=" + $("#sl_has_byr_fmax_chq_amu").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_cdt_loan_amu=" + $("#sl_has_byr_cdt_loan_amu").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_otstaning=" + $("#sl_has_byr_otstaning").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_tenor=" + $("#sl_has_byr_tenor").val().trim() +
                                                "&sl_has_byr_itst_trsry=" + $("#sl_has_byr_itst_trsry").val().trim() +
                                                "&chx_chqware_tran=" + ($("#chx_chqware_tran").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&chx_chqware_fixed=" + ($("#chx_chqware_fixed").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_tran_base_falt_fee=" + $("#cw_tran_base_falt_fee").val().trim().replace(/\,/g, '') +
                                                "&cw_tran_base_from_tran=" + $("#cw_tran_base_from_tran").val().trim().replace(/\,/g, '') +
                                                "&cw_fixed_rate_amount=" + $("#cw_fixed_rate_amount").val().trim().replace(/\,/g, '') +
                                                "&cw_fixed_rate_daily=" + ($("#cw_fixed_rate_daily").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_weekly=" + ($("#cw_fixed_rate_weekly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_monthly=" + ($("#cw_fixed_rate_monthly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_yearly=" + ($("#cw_fixed_rate_yearly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&sl_has_byr_advnce_rate_prstage=" + $("#sl_has_byr_advnce_rate_prstage").val().trim() +
                                                "&sl_has_byr_remarks=" + $("#sl_has_byr_remarks").val().trim() +
                                                "&sl_has_byr_active=" + ($("#sl_has_byr_active").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&statusReason=" + statusReason +
                                                "&paramid=" + paramid;


                                        $.ajax({
                                            type: "POST",
                                            url: "/NDBRMS/CustomerServletDate",
                                            data: values,
                                            dataType: "json", //if received a response from the server
                                            success: function (data) {
                                                // alert(data.samesellernuyer);
                                                if (data.success) {
                                                    window.location = "modalsSucess.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Relationship estabilishment data saved to DB successfuly and sent for authorization.";

                                                } else if (data.systemna) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=" + data.systemna + "";

                                                } else if (data.alreadyexdatarf) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data. The Buyer already linking with the seller in the system receivable finance product.";

                                                } else if (data.alreadyexdatacw) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data. The Buyer already linking with the seller in the system cheque warehousing product.";

                                                } else if (data.samesellernuyer) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=" + data.samesellernuyer + "";

                                                } else if (data.error) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data.";

                                                }
                                            }
                                        });
                                    }


                                });
                            }
                        });
                    }

                    if ($("#sl_has_byr_rf").prop('checked')) {
                        //alert();

                        var paramid = "validateSellerLimt";
                        var values = "&idndb_customer_define_seller=" + $("#idndb_customer_define_seller_id").val().trim() +
                                "&sl_has_byr_cdt_loan_amu=" + $("#sl_has_byr_cdt_loan_amu").val().trim() +
                                "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val().trim() +
                                "&paramid=" + paramid;
                        //alert(values);

                        $.ajax({
                            type: "POST",
                            url: "/NDBRMS/CustomerDataRetrive",
                            data: values,
                            dataType: "json", //if received a response from the server
                            success: function (data) {
                                $.each(data, function (k, v) {
                                    //alert(v.available_sub_limt)

                                    //---------------------------------------------------//
                                    //Commented on 10.04.2018 - not required to check the limit since percentage is using - CFU-BRD-4 -- Janaka_5977
//                                    if (v.available_sub_limt < $("#sl_has_byr_cdt_loan_amu").val().trim().replace(/\,/g, '')) {
//
//                                        $('#sl_has_byr_cdt_loan_amu_lable').text("Seller Limit Exceeded . Sub Limit Available for :" + v.available_sub_limt_formatted + "");
//                                        $('#sl_has_byr_cdt_loan_amu').css('border-color', 'red');
//                                    } else {
//                                    //---------------------------------------------------//
                                    // alert("ok");
                                    {
                                        var statusReason = ($("#statusReason").val() !== '') ? $("#statusReason").val().trim() : "";
                                        var rec_finance_limit = v.rec_finance_limit;
//                                            var buyers_percentage=$("#sl_has_byr_cdt_loan_amu").val().trim().replace(/\,/g, '');
//                                            var buyrs_limit=(rec_finance_limit*buyers_percentage)/100;

                                        var buyrs_limit = $("#sl_has_byr_cdt_loan_amu").val().trim().replace(/\,/g, '');
                                        var paramid = "save_sl_has_byr_data";
                                        var values = "&idndb_customer_define_seller=" + $("#idndb_customer_define_seller_id").val().trim() +
                                                "&idndb_customer_define_buyer=" + $("#idndb_customer_define_buyer_id").val().trim() +
                                                "&idndb_seller_has_buyers=" + $("#idndb_seller_has_buyers").val().trim() +
                                                "&sl_has_byr_prorm_type_rf=" + ($("#sl_has_byr_rf").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&sl_has_byr_prorm_type_cw=" + ($("#sl_has_byr_cw").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                //cw facility details    

                                                "&sl_has_byr_warehs_limit=" + $("#sl_has_byr_warehs_limit").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_warehs_otstaning=" + $("#sl_has_byr_warehs_otstaning").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_warehs_tenor=" + $("#sl_has_byr_warehs_tenor").val().trim() +
                                                "&sl_has_byr_warehs_fmax_chq_amu=" + $("#sl_has_byr_warehs_fmax_chq_amu").val().trim().replace(/\,/g, '') +
                                                "&chx_recfin_tran=" + ($("#chx_recfin_tran").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&chx_recfin_fixed=" + ($("#chx_recfin_fixed").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_tran_base_falt_fee=" + $("#rf_tran_base_falt_fee").val().trim().replace(/\,/g, '') +
                                                "&rf_tran_base_from_tran=" + $("#rf_tran_base_from_tran").val().trim() +
                                                "&rf_fixed_rate_amount=" + $("#rf_fixed_rate_amount").val().trim().replace(/\,/g, '') +
                                                "&rf_fixed_rate_daily=" + ($("#rf_fixed_rate_daily").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_weekly=" + ($("#rf_fixed_rate_weekly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_monthly=" + ($("#rf_fixed_rate_monthly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&rf_fixed_rate_yearly=" + ($("#rf_fixed_rate_yearly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                //rf facility details buyer


                                                "&sl_has_byr_fmax_chq_amu=" + $("#sl_has_byr_fmax_chq_amu").val().trim().replace(/\,/g, '') +
                                                // percentage is using for buyers limit - CFU-BRD-4 -- Janaka_5977       
                                                // "&sl_has_byr_cdt_loan_amu=" + $("#sl_has_byr_cdt_loan_amu").val().trim().replace(/\,/g, '') +

                                                "&sl_has_byr_cdt_loan_amu=" + buyrs_limit +
                                                "&sl_has_byr_otstaning=" + $("#sl_has_byr_otstaning").val().trim().replace(/\,/g, '') +
                                                "&sl_has_byr_tenor=" + $("#sl_has_byr_tenor").val().trim() +
                                                "&sl_has_byr_itst_trsry=" + $("#sl_has_byr_itst_trsry").val().trim() +
                                                "&chx_chqware_tran=" + ($("#chx_chqware_tran").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&chx_chqware_fixed=" + ($("#chx_chqware_fixed").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_tran_base_falt_fee=" + $("#cw_tran_base_falt_fee").val().trim().replace(/\,/g, '') +
                                                "&cw_tran_base_from_tran=" + $("#cw_tran_base_from_tran").val().trim() +
                                                "&cw_fixed_rate_amount=" + $("#cw_fixed_rate_amount").val().trim().replace(/\,/g, '') +
                                                "&cw_fixed_rate_daily=" + ($("#cw_fixed_rate_daily").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_weekly=" + ($("#cw_fixed_rate_weekly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_monthly=" + ($("#cw_fixed_rate_monthly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&cw_fixed_rate_yearly=" + ($("#cw_fixed_rate_yearly").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&sl_has_byr_advnce_rate_prstage=" + $("#sl_has_byr_advnce_rate_prstage").val().trim() +
                                                "&sl_has_byr_remarks=" + $("#sl_has_byr_remarks").val().trim() +
                                                "&sl_has_byr_active=" + ($("#sl_has_byr_active").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                                                "&statusReason=" + statusReason +
                                                "&paramid=" + paramid;
                                        // alert(paramid);

                                        $.ajax({
                                            type: "POST",
                                            url: "/NDBRMS/CustomerServletDate",
                                            data: values,
                                            dataType: "json", //if received a response from the server
                                            success: function (data) {
                                                if (data.success) {
                                                    window.location = "modalsSucess.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Relationship estabilishment data saved to DB successfuly and sent for authorization.";

                                                } else if (data.systemna) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=" + data.systemna + "";

                                                } else if (data.alreadyexdatarf) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data. The Buyer already linking with the seller in the system receivable finance product.";

                                                } else if (data.alreadyexdatacw) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data. The Buyer already linking with the seller in the system cheque warehousing product.";

                                                } else if (data.samesellernuyer) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=" + data.samesellernuyer + "";

                                                } else if (data.error) {
                                                    window.location = "modalsError.jsp?desigURL=ndb_relationshipestab_setup.jsp&message=Error occured in saving relationship estabilishment data.";

                                                }
                                            }
                                        });

                                    }


                                });
                            }
                        });
                    }





                }

            });

            $('#clear_sl_has_byr_data').click(function () {
                location.reload();
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
                            $('#sl_has_byr_fmax_chq_amu').val(v.sl_has_byr_max_chq_amu);
                            ////getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977                                
                            $('#sl_has_byr_cdt_loan_amu').val(v.buyers_loan_percentage);
                            seller_rec_fin_limit = parseFloat(v.seller_rec_fin_limit);
                            seller_chq_wh_limit = parseFloat(v.seller_chq_wh_limit);
                            //$('#sl_has_byr_cdt_loan_amu').val(v.shb_facty_det_crd_loam_limit);                           
                            $('#sl_has_byr_otstaning').val(v.shb_facty_det_os);
                            $('#sl_has_byr_tenor').val(v.shb_facty_det_tenor);
                            $('#setup_rf_seller_tenor').val(v.seller_tenor);
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

                            //  alert(v.shb_chq_dis_adv_rate_prectange);
                            $("#sl_has_byr_advnce_rate_prstage").val(v.shb_chq_dis_adv_rate_prectange);
                            $("#setup_rf_seller_advance_rate").val(v.seller_advnc_prtage);


                            $("#sl_has_byr_remarks").val(v.sl_has_byr_remarks);

                            if (v.sl_has_byr_status === 'ACTIVE') {
                                $("#sl_has_byr_active").prop('checked', true);
                            } else {
                                $("#sl_has_byr_active").prop('checked', false);
                            }
                            $("#statusReason").val(v.statusReason);


                            // cw facility etails
                            $("#sl_has_byr_warehs_limit").val(v.sl_has_byr_warehs_limit);
                            $("#sl_has_byr_warehs_otstaning").val(v.sl_has_byr_warehs_otstaning);
                            $("#sl_has_byr_warehs_tenor").val(v.sl_has_byr_warehs_tenor);
                            $("#setup_cw_seller_tenor").val(v.cw_seller_tenor);
                            $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);
                            $("#sl_has_byr_warehs_fmax_chq_amu").val(v.sl_has_byr_warehs_fmax_chq_amu);

                            document.getElementById("idndb_customer_define_seller_id").disabled = true;
                            document.getElementById("idndb_customer_define_seller_name").disabled = true;

                        });
                    }
                });
            });

            $("#btn_view_estb_data").click(function (e) {
                var search_criteria = $("#search_criteria").val();
                window.location = '/NDBRMS/pages/MasterFiles/ndb_relationshipestab_setup.jsp?table_plus=true&search_criteria=' + search_criteria + '';



            });

            $('#example1').dataTable({
                "aLengthMenu": [10, 25, 50, 100, 500, 20000]
            });


            //     $("#sl_has_byr_warehs_limit").focusout(function () {
///
            //        var sl_has_byr_warehs_limit = $('#sl_has_byr_warehs_limit').val();
            //        $('#sl_has_byr_warehs_fmax_chq_amu').val(sl_has_byr_warehs_limit);

            //    });




            //     $("#sl_has_byr_cdt_loan_amu").focusout(function () {
            //         var sl_has_byr_cdt_loan_amu = $('#sl_has_byr_cdt_loan_amu').val();
            //         $('#sl_has_byr_fmax_chq_amu').val(sl_has_byr_cdt_loan_amu);
//
            //     });







            function validateInput() {

                var hasError = true;
                var idndb_customer_define_seller_id = $("#idndb_customer_define_seller_id").val();
                var idndb_customer_define_seller_name = $("#idndb_customer_define_seller_name").val();
                var idndb_customer_define_buyer_id = $("#idndb_customer_define_buyer_id").val();
                var idndb_customer_define_buyer_name = $("#idndb_customer_define_buyer_name").val();


                if (idndb_customer_define_seller_id === 'Select The Seller') {
                    $('#idndb_customer_define_seller_id').css('border-color', 'red');
                    return false;
                }
                if (idndb_customer_define_seller_name === 'Select The Seller') {
                    $('#idndb_customer_define_seller_name').css('border-color', 'red');
                    return false;
                }


                if (idndb_customer_define_buyer_id === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_id').css('border-color', 'red');
                    return false;
                }
                if (idndb_customer_define_buyer_name === 'Select The Buyer') {
                    $('#idndb_customer_define_buyer_name').css('border-color', 'red');
                    return false;
                }


                var rf_cw = "NOT DEFINE";
                if ($("#sl_has_byr_rf").prop('checked')) {
                    rf_cw = "RF";
                }
                if ($("#sl_has_byr_cw").prop('checked')) {
                    rf_cw = "CW";
                }

                if (rf_cw === 'NOT DEFINE') {
                    alert("Invalid Relationship Programe Type");
                    return false;
                }

                if (!($("#sl_has_byr_active").prop('checked'))) {
                    if ($("#statusReason").val() === '') {
                        $('#statusReason').css('border-color', 'red');
                        return false;
                    }
                }



                if (rf_cw === 'CW') {
                    var sl_has_byr_warehs_limit = parseFloat($("#sl_has_byr_warehs_limit").val().replace(/\,/g, '').trim());

                    var sl_has_byr_warehs_otstaning = $("#sl_has_byr_warehs_otstaning").val();
                    var sl_has_byr_warehs_tenor = $("#sl_has_byr_warehs_tenor").val();
                    var sl_has_byr_warehs_fmax_chq_amu = parseFloat($("#sl_has_byr_warehs_fmax_chq_amu").val().replace(/\,/g, '').trim());


                    if (sl_has_byr_warehs_limit === '') {
                        $('#sl_has_byr_warehs_limit').css('border-color', 'red');
                        return false;
                    }
                    //change CFU-BRD-4 getting chq precentage wise Janaka_5977

                    if (sl_has_byr_warehs_fmax_chq_amu > ((sl_has_byr_warehs_limit / 100) * seller_chq_wh_limit)) {
                        // alert("ok");

                        $('#sl_has_byr_warehs_fmax_chq_amu').css('border-color', 'red');
                        return false;

                    }

                    if (sl_has_byr_warehs_tenor === '') {
                        $('#sl_has_byr_warehs_tenor').css('border-color', 'red');
                        return false;
                    }


                    if (sl_has_byr_warehs_fmax_chq_amu === '') {
                        $('#sl_has_byr_warehs_fmax_chq_amu').css('border-color', 'red');
                        return false;
                    }



                    var chq_comm = "NOT DEFINE";
                    if ($("#chx_chqware_tran").prop('checked')) {
                        chq_comm = "TB";
                    }
                    if ($("#chx_chqware_fixed").prop('checked')) {
                        chq_comm = "FC";
                    }

                    if (chq_comm === 'NOT DEFINE') {
                        alert("Invalid Commision Type")
                        return false;
                    }


                    if (chq_comm === 'TB') {

                        var cw_tran_base_falt_fee = $("#cw_tran_base_falt_fee").val();
                        var cw_tran_base_falt_fee_without_commas = cw_tran_base_falt_fee.replace(",", "");
                        var cw_tran_base_from_tran = $("#cw_tran_base_from_tran").val();
                        if (cw_tran_base_falt_fee === '' && cw_tran_base_from_tran === '') {
                            $('#cw_tran_base_falt_fee').css('border-color', 'red');
                            $('#cw_tran_base_from_tran').css('border-color', 'red');
                            return false;
                        }

                        if ((cw_tran_base_from_tran !== '') && (parseFloat(cw_tran_base_from_tran) > 1)) {
                            $('#cw_tran_base_from_tran').css('border-color', 'red');
                            return false;
                        }
                        if ((cw_tran_base_falt_fee !== '') && (parseFloat(cw_tran_base_falt_fee_without_commas) > 1000)) {
                            $('#cw_tran_base_falt_fee').css('border-color', 'red');
                            return false;
                        }


                    }

                    if (chq_comm === 'FC') {

                        var cw_fixed_rate_amount = $("#cw_fixed_rate_amount").val();
                        if (cw_fixed_rate_amount === '') {
                            $('#cw_fixed_rate_amount').css('border-color', 'red');
                            return false;
                        }



                        var cw_fixed_rate_freq = "NOT DEFINE";
                        if ($("#cw_fixed_rate_daily").prop('checked')) {
                            cw_fixed_rate_freq = "CW";
                        }
                        if ($("#cw_fixed_rate_weekly").prop('checked')) {
                            cw_fixed_rate_freq = "CW";
                        }
                        if ($("#cw_fixed_rate_monthly").prop('checked')) {
                            cw_fixed_rate_freq = "CW";
                        }
                        if ($("#cw_fixed_rate_yearly").prop('checked')) {
                            cw_fixed_rate_freq = "CW";
                        }
                        if (cw_fixed_rate_freq === 'NOT DEFINE') {

                            alert("Invalid Fixed Charge Base Frequancy")
                            return false;
                        }



                    }



                }



                if (rf_cw === 'RF') {
                    var sl_has_byr_cdt_loan_amu = parseFloat($("#sl_has_byr_cdt_loan_amu").val().replace(/\,/g, '').trim());


                    var sl_has_byr_fmax_chq_amu = parseFloat($("#sl_has_byr_fmax_chq_amu").val().replace(/\,/g, '').trim());
                    var sl_has_byr_tenor = $("#sl_has_byr_tenor").val();
                    
                    //change CFU-BRD-4 Janaka_5977

                    if (sl_has_byr_fmax_chq_amu > ((sl_has_byr_cdt_loan_amu / 100) * seller_rec_fin_limit)) {
                        //if (sl_has_byr_fmax_chq_amu > (sl_has_byr_cdt_loan_amu)) {
                        // alert(sl_has_byr_fmax_chq_amu + "y" + sl_has_byr_cdt_loan_amu)
                        $('#sl_has_byr_fmax_chq_amu').css('border-color', 'red');
                        return false;

                    }



                    if (sl_has_byr_cdt_loan_amu === '') {
                        $('#sl_has_byr_cdt_loan_amu').css('border-color', 'red');
                        return false;
                    }

                    if (sl_has_byr_fmax_chq_amu === '') {
                        $('#sl_has_byr_fmax_chq_amu').css('border-color', 'red');
                        return false;
                    }



                    if (sl_has_byr_tenor === '') {
                        $('#sl_has_byr_tenor').css('border-color', 'red');
                        return false;
                    }





                    var chq_comm = "NOT DEFINE";
                    if ($("#chx_recfin_tran").prop('checked')) {
                        chq_comm = "TB";
                    }
                    if ($("#chx_recfin_fixed").prop('checked')) {
                        chq_comm = "FC";
                    }

                    if (chq_comm === 'NOT DEFINE') {
                        alert("Invalid Commision Type")
                        return false;
                    }


                    if (chq_comm === 'TB') {

                        var rf_tran_base_falt_fee = $("#rf_tran_base_falt_fee").val();
                        var rf_tran_base_falt_fee_without_commas = rf_tran_base_falt_fee.replace(",", "");
                        var rf_tran_base_from_tran = $("#rf_tran_base_from_tran").val();
                        if (rf_tran_base_falt_fee === '' && rf_tran_base_from_tran === '') {
                            $('#rf_tran_base_falt_fee').css('border-color', 'red');
                            $('#rf_tran_base_from_tran').css('border-color', 'red');
                            return false;
                        }

                        if ((rf_tran_base_from_tran !== '') && (parseFloat(rf_tran_base_from_tran) > 1)) {
                            $('#rf_tran_base_from_tran').css('border-color', 'red');
                            return false;
                        }
                        if ((rf_tran_base_falt_fee !== '') && (parseFloat(rf_tran_base_falt_fee_without_commas) > 1000)) {
                            $('#rf_tran_base_falt_fee').css('border-color', 'red');
                            return false;
                        }
                    }

                    if (chq_comm === 'FC') {

                        var rf_fixed_rate_amount = $("#rf_fixed_rate_amount").val();
                        if (rf_fixed_rate_amount === '') {
                            $('#rf_fixed_rate_amount').css('border-color', 'red');
                            return false;
                        }


                        var rf_fixed_rate_freq = "NOT DEFINE";
                        if ($("#rf_fixed_rate_daily").prop('checked')) {
                            rf_fixed_rate_freq = "RF";
                        }
                        if ($("#rf_fixed_rate_weekly").prop('checked')) {
                            rf_fixed_rate_freq = "RF";
                        }
                        if ($("#rf_fixed_rate_monthly").prop('checked')) {
                            rf_fixed_rate_freq = "RF";
                        }
                        if ($("#rf_fixed_rate_yearly").prop('checked')) {
                            rf_fixed_rate_freq = "RF";
                        }
                        if (rf_fixed_rate_freq === 'NOT DEFINE') {

                            alert("Invalid Fixed Charge Base Frequancy")
                        }



                    }



                }


                return hasError;
            }





        });


        function handleRfTenorChange(input) {
            var setup_rf_seller_tenor = parseInt($("#setup_rf_seller_tenor").val().trim());

            if (input.value > setup_rf_seller_tenor) {

                input.value = setup_rf_seller_tenor;
            }
        }


        function handleCWTenorChange(input) {
            var setup_cw_seller_tenor = parseInt($("#setup_cw_seller_tenor").val().trim());
            if (input.value > setup_cw_seller_tenor) {
                input.value = setup_cw_seller_tenor;
            }
        }

        function handleRfAdvRateChange(input) {
            var setup_rf_seller_advance_rate = parseFloat($("#setup_rf_seller_advance_rate").val().trim());

            if (input.value > setup_rf_seller_advance_rate) {

                input.value = setup_rf_seller_advance_rate;
            }
        }








        function handleChange(input) {
            if (input.value < 0)
                input.value = 0;
            if (input.value > 100)
                input.value = 100;
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
                    navigator.msSaveBlob(blob, 'CUST REL ESTB DATA.xls.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'CUST REL ESTB DATA.xls');
            }

        }

        var seller_rec_fin_limit = 0;
        var seller_chq_wh_limit = 0;

    </script>

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
                        Relationship Establishment
                        <small>Linking Buyer To The Seller</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Relationship Establishment</li>
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
                                                    <%  if (_session_availability) {

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
                                                    <%   if (_session_availability) {

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
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_limit" name="sl_has_byr_warehs_limit" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                        <label class="col-sm-2 control-label" id="sl_has_byr_warehs_limit_lable" style="margin-top: -34px;margin-left: 580px;text-align: left;width: 573px;"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Maximum Per Cheque Amount</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_fmax_chq_amu" name="sl_has_byr_warehs_fmax_chq_amu" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_otstaning" name="sl_has_byr_warehs_otstaning" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_warehs_tenor" name="sl_has_byr_warehs_tenor" value="0" placeholder="Days" style="width: 350px" onchange="handleCWTenorChange(this);">
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
                                        <br>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Flat Fee</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_tran_base_falt_fee" name="cw_tran_base_falt_fee" placeholder="0.00" value="0.00" style="width: 350px;background-color: rgba(57, 221, 159, 0.38);" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">From Transaction (%)</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_tran_base_from_tran" name="cw_tran_base_from_tran" placeholder="0.00" value="0" style="width: 350px;background-color: rgba(221, 57, 57, 0.38);" onchange="handleChange(this);">
                                            </div>
                                        </div>


                                    </div>

                                    <div id="div_chqweare_fixed_base">

                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Fixed Charge Based</h6>
                                        </div><!-- /.box-header -->
                                        <br>


                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="cw_fixed_rate_amount" name="cw_fixed_rate_amount" placeholder="0.00" value="0.00" style="width: 350px;background-color: rgba(57, 221, 159, 0.38);" >
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
                                            <input type="text" class="form-control" id="sl_has_byr_cdt_loan_amu" name="sl_has_byr_cdt_loan_amu" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                        <label class="col-sm-2 control-label" id="sl_has_byr_cdt_loan_amu_lable" style="margin-top: -34px;margin-left: 580px;text-align: left;width: 573px;"></label>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Maximum Per Cheque Amount</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_fmax_chq_amu" name="sl_has_byr_fmax_chq_amu" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_otstaning" name="sl_has_byr_otstaning" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_tenor" name="sl_has_byr_tenor" placeholder="Days" value="0" style="width: 350px" onchange="handleRfTenorChange(this);">
                                        </div>
                                    </div>
                                    <div id="tre_int_rate">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Interest (Treasury)</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="sl_has_byr_itst_trsry" name="sl_has_byr_itst_trsry" placeholder="0.00 %" style="width: 350px">
                                            </div>
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
                                        <br>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Flat Fee</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_tran_base_falt_fee" name="rf_tran_base_falt_fee" placeholder="0.00" value="0.00" style="width: 350px;background-color: rgba(57, 221, 159, 0.38);">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">From Transaction (%)</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_tran_base_from_tran" name="rf_tran_base_from_tran" placeholder="0.00" style="width: 350px;background-color: rgba(221, 57, 57, 0.38);" value="0" onchange="handleChange(this);">
                                            </div>
                                        </div>


                                    </div>

                                    <div id="div_rec_fin_fixed_base">

                                        <div class="box-header with-border">
                                            <h6 class="box-title" style="font-size: 17px;">Fixed Charge Based</h6>
                                        </div><!-- /.box-header -->
                                        <br>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control" id="rf_fixed_rate_amount" name="rf_fixed_rate_amount" placeholder="0.00" style="width: 350px;background-color: rgba(57, 221, 159, 0.38);" value="0.00">
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
                                    <br>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Percentage</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_advnce_rate_prstage" name="sl_has_byr_advnce_rate_prstage" placeholder="0.00 %" style="width: 350px" onchange="handleRfAdvRateChange(this);">
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

                                    <input type="hidden" class="form-control" id="setup_rf_seller_tenor" name="setup_rf_seller_tenor" style="width: 350px">
                                    <input type="hidden" class="form-control" id="setup_rf_seller_advance_rate" name="setup_rf_seller_advance_rate" style="width: 350px">
                                    <input type="hidden" class="form-control" id="setup_cw_seller_tenor" name="setup_cw_seller_tenor" style="width: 350px">



                                </form>

                            </div>
                            <div class="box box-info">

                                <div class="box-footer">
                                    <input type="text" class="form-control" id="search_criteria" name="search_criteria" style="width: 350px;">
                                    <button type="submit" class="btn btn-info pull-right" id="clear_sl_has_byr_data" style="margin-top: -33px;">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="save_sl_has_byr_data" style="margin-right: 5px;margin-top: -33px;">Submit</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_view_estb_data" style="margin-top: -34px;margin-right: 314px;">View Relationship Establishment Data</button>
                                </div><!-- /.box-footer -->

                            </div>
                        </div><!-- /.box -->
                    </div>


                </section>
                <!-- Main content -->


                <%                     String id_table_plus = request.getParameter("table_plus");
                    String search_criteria = "";
                    if ((request.getParameter("search_criteria") == null)) {
                        search_criteria = "";
                    } else {
                        search_criteria = request.getParameter("search_criteria").toString();
                    }
                    if (!(id_table_plus == null)) {


                %>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Customer Relationship Establishment Data :</h3>
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
                                                <th>Status</th>
                                                <th>Approval</th>
                                                <th>Latest Changes</th>
                                                <th>Mod. By/Date</th>
                                                <th>Auth. By/Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {

                                                    out.print(new FIllDataTableBean().getCustomerRelationEstblishData(search_criteria));

                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Seller</th>
                                                <th>Buyer</th>
                                                <th>Program Type</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                                <th>Latest Changes</th>
                                                <th>Mod. By/Date</th>
                                                <th>Auth. By/Date</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div><!-- /.box-body -->

                            </div><!-- /.box -->


                        </div><!-- /.box -->
                    </div><!-- /.col -->
                </section><!-- /.content -->
                <%
                    }
                %>
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