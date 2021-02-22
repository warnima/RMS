<%@page import="DBOops.MenuDAO"%>
<%@page import="DBOops.UserBean"%>
<%@page import="DBAutoFillBean.FIllDataTableBean"%>
<%@page import="DBAutoFillBean.FillDataComboBean"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>NDB BANK | Customer Product Mapping</title>
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
                document.getElementById("idndb_customer_define_id").disabled = true;
                document.getElementById("idndb_customer_define_name").disabled = true;
                document.getElementById("cust_as_seller").disabled = true;
                document.getElementById("cust_as_buyer").disabled = true;
                document.getElementById("ch_rev_fin").disabled = true;
                document.getElementById("ch_ch_we").disabled = true;
                document.getElementById("prod_rel_status").disabled = true;
                document.getElementById("statusReason").disabled = true;
                document.getElementById("ch_rec_fin_b_c_yes").disabled = true;
                document.getElementById("ch_rec_fin_b_c_no").disabled = true;

                document.getElementById("ch_rec_fin_balance_transfer_yes").disabled = true;
                document.getElementById("ch_rec_fin_balance_transfer_no").disabled = true;


                document.getElementById("chq_wh_dr_to_cr_spe_narr_yes").disabled = true;
                document.getElementById("chq_wh_dr_to_cr_spe_narr_no").disabled = true;

                $("#rec_finance_curr").prop('readonly', true);
                $("#rec_finance_limit").prop('readonly', true);

                //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                $(".rf_temporary_limit_value").prop('readonly', true);
                $(".rf_temporary_limit_exp_date").prop('readonly', true);

                $("#rec_finance_Outstanding").prop('readonly', true);
                $("#rec_finance_tenor").prop('readonly', true);
                $("#rec_finance_inerest_rate").prop('readonly', true);
                $("#rec_finance_financing").prop('readonly', true);
                $("#rec_finance_acc_num").prop('readonly', true);
                $("#rec_finance_cr_dsc_proc_acc_num").prop('readonly', true);
                $("#rec_finance_current_ac").prop('readonly', true);
                $("#rec_finance_margin_ac").prop('readonly', true);
                $("#rec_finance_margin").prop('readonly', true);
                $("#cust_credit_rate").prop('readonly', true);
                $("#rec_finance_erly_wdr_chg").prop('readonly', true);
                $("#rec_finance_vale_dte_extr_chg").prop('readonly', true);
                $("#rec_finance_erly_stlemnt_chg").prop('readonly', true);
                $("#chq_wh_limit").prop('readonly', true);
                //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                $(".cw_temporary_limit_value").prop('readonly', true);
                //addedd for the CW Temporary Limit Exp Date -- CFU-BRD-4 -- Janaka_5977
                $(".cw_temporary_limit_exp_date").prop('readonly', true);
                $("#sl_has_byr_otstaning").prop('readonly', true);
                $("#sl_has_byr_tenor").prop('readonly', true);
                $("#chq_wh_erly_wdr_chg").prop('readonly', true);
                $("#chq_wh_vale_dte_extr_chg").prop('readonly', true);
                $("#chq_wh_erly_stlemnt_chg").prop('readonly', true);
                $("#cms_curr_acc_number").prop('readonly', true);
                $("#cms_collection_acc_number").prop('readonly', true);
                document.getElementById("cust_prod_map_save").disabled = true;



        <%}  %>

                var today = new Date().toISOString().split('T')[0];
                $('.rf_temporary_limit_exp_date').attr('min', today);
                $('.cw_temporary_limit_exp_date').attr('min', today)
                $("#div_rec_fin").hide();
                $("#dev_chq_wear").hide();
                $("#dev_rel_only").hide();
                $("#interest_rate").hide();

                $("#div-buyer-acc-details").hide();


                $("#div_crelonly_trn_base").hide();
                $("#div_crelonly_fixed_base").hide();

                $("#prod_rel_map").hide();
                $("#prod_rel_map2").hide();

                $("#rec_finance_acc_num").prop('readonly', true);
                $("#rec_finance_curr").prop('readonly', true);
                $("#rec_finance_cr_dsc_proc_acc_num").prop('readonly', true);
                $("#rec_finance_current_ac").prop('readonly', true);
                $("#rec_finance_margin_ac").prop('readonly', true);
                $("#rec_finance_margin").prop('readonly', true);
                $("#cust_credit_rate").prop('readonly', true);
                $("#cms_curr_acc_number").prop('readonly', true);
                $("#cms_collection_acc_number").prop('readonly', true);


                $("#prod_rel_status").prop('checked', true);
                $("#loading-mask").hide();

            };





            $("#rec_finance_limit").keyup(function () {
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


            $("#rec_finance_limit").focusout(function () {
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

            rfTempLimitValuesFormatter();
            cwTempLimitValuesFormatter();


            $("#rec_finance_Outstanding").keyup(function () {
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
            $("#rec_finance_Outstanding").focusout(function () {
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

            $("#rec_finance_erly_wdr_chg").keyup(function () {
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
            $("#rec_finance_erly_wdr_chg").focusout(function () {
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
            $("#rec_finance_vale_dte_extr_chg").keyup(function () {
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
            $("#rec_finance_vale_dte_extr_chg").focusout(function () {
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
            $("#rec_finance_erly_stlemnt_chg").keyup(function () {
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
            $("#rec_finance_erly_stlemnt_chg").focusout(function () {
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

            $("#chq_wh_limit").keyup(function () {
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
            $("#chq_wh_limit").focusout(function () {
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

            $("#chq_wh_erly_wdr_chg").keyup(function () {
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
            $("#chq_wh_erly_wdr_chg").focusout(function () {
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
            $("#chq_wh_vale_dte_extr_chg").keyup(function () {
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
            $("#chq_wh_vale_dte_extr_chg").focusout(function () {
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
            $("#chq_wh_erly_stlemnt_chg").keyup(function () {
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
            $("#chq_wh_erly_stlemnt_chg").focusout(function () {
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



            $("#rec_finance_tenor").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }

            });


            $("#rec_finance_inerest_rate").keyup(function () {
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

            $("#rec_finance_inerest_rate").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');
                }

            });

            $("#rec_finance_financing").keyup(function () {
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

            $("#rec_finance_financing").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9/.]/g, '');
                }

            });

            $("#sl_has_byr_tenor").keyup(function () {
                if (this.value.match(/[^0-9 ]/g)) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                }

            });


            $("#ch_rev_fin").change(function () {
                if (this.checked) {
                    $("#div_rec_fin").show();
                } else {
                    $("#div_rec_fin").hide();
                }
            });

            $("#ch_ch_we").change(function () {
                if (this.checked) {
                    $("#dev_chq_wear").show();
                } else {
                    $("#dev_chq_wear").hide();
                }
            });

            $("#cust_as_seller").change(function () {
                if (this.checked) {

                    $("#loading-mask").show();
                    $("#cust_as_buyer").prop('checked', false);
                    $("#prod_rel_map").show();
                    $("#div-buyer-acc-details").hide();
                    $("#div-buyer-acc-details").hide();

                    var idndb_customer_define_id = $("#idndb_customer_define_id").val().trim();
                    var cust_as_seller = 'ACTIVE';
                    var paramid = "get_prod_map_data_tofillAsSeller";
                    var values = "&idndb_customer_define_id=" + idndb_customer_define_id +
                            "&cust_as_seller=" + cust_as_seller +
                            "&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            $.each(data, function (k, v) {



                                if (v.data_availablity === 'ACTIVE') {

                                    $("#div_rec_fin").hide();
                                    $("#dev_chq_wear").hide();


                                    $("#div_crelonly_trn_base").hide();
                                    $("#div_crelonly_fixed_base").hide();

                                    $("#prod_rel_map").hide();
                                    $("#cust_as_seller").prop('checked', false);
                                    $("#cust_as_buyer").prop('checked', false);
                                    $("#ch_rev_fin").prop('checked', false);
                                    $("#ch_ch_we").prop('checked', false);


                                    $('#idndb_cust_prod_map').val(v.idndb_cust_prod_map);
                                    $('#idndb_customer_define_id').val(v.idndb_customer_define);
                                    $('#idndb_customer_define_name').val(v.idndb_customer_define);

                                    if (v.prod_relationship_key_seller === 'ACTIVE') {
                                        $("#cust_as_seller").prop('checked', true);
                                        $("#prod_rel_map").show();
                                    }

                                    if (v.prod_relationship_key_buyer === 'ACTIVE') {
                                        $("#cust_as_buyer").prop('checked', true);
                                    }

                                    if (v.prod_relationship_res_fin === 'ACTIVE') {
                                        $("#ch_rev_fin").prop('checked', true);
                                        $("#div_rec_fin").show();
                                    }

                                    if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                        $("#ch_ch_we").prop('checked', true);
                                        $("#dev_chq_wear").show();
                                    }



                                    if (v.prod_relationship_status === 'ACTIVE') {
                                        $("#prod_rel_status").prop('checked', true);
                                    } else {

                                        $("#prod_rel_status").prop('checked', false);
                                    }

                                    $('#statusReason').val(v.statusReason);




                                    //$('#rec_finance_curr').val(v.rec_finance_curr);



                                    $('#rec_finance_limit').val(v.rec_finance_limit);
                                    //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                                    var rf_temporary_limits_string = v.rf_temporary_limits + "";
                                    if (rf_temporary_limits_string !== "") {
                                        var rf_temporary_limits_arr = rf_temporary_limits_string.split(",");
                                        var firstRecord = rf_temporary_limits_arr[0] + "";

                                        var firstRecordValues_arr = firstRecord.split("##");

                                        $("#rf_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                        $("#rf_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                        $("#rf_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                        var from1 = firstRecordValues_arr[2].split("-");
                                        var expDate1 = new Date(from1[0], from1[1] - 1, from1[2]);
                                        var today = new Date();
                                        var thisYear = today.getFullYear();
                                        var thisMonth = (today.getMonth() + 1) < 10 ? "0" + (today.getMonth() + 1) : (today.getMonth() + 1);
                                        var thisDate = today.getDate() < 10 ? "0" + today.getDate() : today.getDate();
                                        var todayString = thisYear + "-" + thisMonth + "-" + thisDate;

                                        if (expDate1.getFullYear() === today.getFullYear() && expDate1.getMonth() === today.getMonth() && expDate1.getDate() === today.getDate()) {
                                        } else {

                                            if ((today.getTime() - expDate1.getTime()) > 0) {
                                                $("#rf_temporary_limit0").css('background-color', 'rgba(221, 75, 57, 0.74)');
                                                $("#rf_temporary_limit_exp_date0").css('background-color', 'rgba(221, 75, 57, 0.74)');
                                            }
                                        }

                                        for (var row_counter = 1; row_counter < rf_temporary_limits_arr.length; row_counter++) {
                                            var record = rf_temporary_limits_arr[row_counter] + "";
                                            var recordValues_arr = record.split("##");
                                            count = $('table.rf tr').length;

                                            var data = "<tr><td><input type='checkbox' class='rf_case'/><input type='hidden' id='rf_temporary_limit_record_no" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                            data += "";

                                            var dataRed = "<td><input type='text' class='form-control rf_temporary_limit_value' style='background-color: rgba(221, 75, 57, 0.74)' id='rf_temporary_limit" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' min='" + todayString + "' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "' style='background-color: rgba(221, 75, 57, 0.74)'/></td></tr>";
                                            var dataNotRed = "<td><input type='text' class='form-control rf_temporary_limit_value' id='rf_temporary_limit" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' min='" + todayString + "' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                            var from = recordValues_arr[2].split("-");
                                            var expDate = new Date(from[0], from[1] - 1, from[2]);


                                            if (expDate.getFullYear() === today.getFullYear() && expDate.getMonth() === today.getMonth() && expDate.getDate() === today.getDate()) {
                                                data += dataNotRed;
                                            } else {
                                                if ((today.getTime() - expDate.getTime()) > 0) {
                                                    data += dataRed;
                                                } else {
                                                    data += dataNotRed;
                                                }
                                            }
                                            $('table.rf').append(data);
                                            rf_tempory_limit_row_count++;
                                            rf_tempory_limit_record_no++;

                                            rfTempLimitValuesFormatter();
                                        }
                                    }
                                    $('#rec_finance_Outstanding').val(v.rec_finance_Outstanding);
                                    $('#rec_finance_tenor').val(v.rec_finance_tenor);
                                    $("#rec_finance_inerest_rate").val(v.rec_finance_inerest_rate);
                                    $("#rec_finance_financing").val(v.rec_finance_financing);
                                    $("#rec_finance_acc_num").val(v.rec_finance_acc_num);
                                    $("#rec_finance_cr_dsc_proc_acc_num").val(v.rec_finance_cr_dsc_proc_acc_num);
                                    $("#rec_finance_current_ac").val(v.rec_finance_current_ac);
                                    $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                                    $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                                    $("#rec_finance_margin_ac").val(v.rec_finance_margin_ac);
                                    $("#rec_finance_margin").val(v.rec_finance_margin);
                                    $("#cust_credit_rate").val(v.cust_credit_rate);


                                    if (v.rec_finance_bulk_credit === 'BULK CREDIT ACTIVE') {
                                        $("#ch_rec_fin_b_c_yes").prop('checked', true);
                                        $("#ch_rec_fin_b_c_no").prop('checked', false);
                                    } else {

                                        $("#ch_rec_fin_b_c_no").prop('checked', true);
                                        $("#ch_rec_fin_b_c_yes").prop('checked', false);
                                    }

                                    if (v.rec_finance_balance_transfer === 'BALANCE TRANSFER ACTIVE') {
                                        $("#ch_rec_fin_balance_transfer_yes").prop('checked', true);
                                        $("#ch_rec_fin_balance_transfer_no").prop('checked', false);
                                    } else {

                                        $("#ch_rec_fin_balance_transfer_no").prop('checked', true);
                                        $("#ch_rec_fin_balance_transfer_yes").prop('checked', false);
                                    }





                                    $("#rec_finance_erly_wdr_chg").val(v.rec_finance_erly_wdr_chg);
                                    $("#rec_finance_vale_dte_extr_chg").val(v.rec_finance_vale_dte_extr_chg);
                                    $("#rec_finance_erly_stlemnt_chg").val(v.rec_finance_erly_stlemnt_chg);

                                    // chque ware housing  

                                    $("#chq_wh_limit").val(v.chq_wh_limit);

                                    //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                                    var cw_temporary_limits_string = v.cw_temporary_limits + "";
                                    if (cw_temporary_limits_string !== "") {
                                        var cw_temporary_limits_arr = cw_temporary_limits_string.split(",");
                                        var firstRecord = cw_temporary_limits_arr[0] + "";

                                        var firstRecordValues_arr = firstRecord.split("##");

                                        $("#cw_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                        $("#cw_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                        $("#cw_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                        var from1 = firstRecordValues_arr[2].split("-");
                                        var expDate1 = new Date(from1[0], from1[1] - 1, from1[2]);
                                        var today = new Date();
                                        var thisYear = today.getFullYear();
                                        var thisMonth = (today.getMonth() + 1) < 10 ? "0" + (today.getMonth() + 1) : (today.getMonth() + 1);
                                        var thisDate = today.getDate() < 10 ? "0" + today.getDate() : today.getDate();
                                        var todayString = thisYear + "-" + thisMonth + "-" + thisDate;

                                        if (expDate1.getFullYear() === today.getFullYear() && expDate1.getMonth() === today.getMonth() && expDate1.getDate() === today.getDate()) {
                                        } else {

                                            if ((today.getTime() - expDate1.getTime()) > 0) {
                                                $("#cw_temporary_limit0").css('background-color', 'rgba(221, 75, 57, 0.74)');
                                                $("#cw_temporary_limit_exp_date0").css('background-color', 'rgba(221, 75, 57, 0.74)');
                                            }
                                        }

                                        for (var row_counter = 1; row_counter < cw_temporary_limits_arr.length; row_counter++) {
                                            var record = cw_temporary_limits_arr[row_counter] + "";
                                            var recordValues_arr = record.split("##");
                                            count = $('table.cw tr').length;

                                            var data = "<tr><td><input type='checkbox' class='cw_case'/><input type='hidden' id='cw_temporary_limit_record_no" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                            data += "";

                                            var dataRed = "<td><input type='text' class='form-control cw_temporary_limit_value' style='background-color: rgba(221, 75, 57, 0.74)' id='cw_temporary_limit" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' min='" + todayString + "' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "' style='background-color: rgba(221, 75, 57, 0.74)'/></td></tr>";
                                            var dataNotRed = "<td><input type='text' class='form-control cw_temporary_limit_value' id='cw_temporary_limit" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' min='" + todayString + "' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                            var from = recordValues_arr[2].split("-");
                                            var expDate = new Date(from[0], from[1] - 1, from[2]);


                                            if (expDate.getFullYear() === today.getFullYear() && expDate.getMonth() === today.getMonth() && expDate.getDate() === today.getDate()) {
                                                data += dataNotRed;
                                            } else {
                                                if ((today.getTime() - expDate.getTime()) > 0) {
                                                    data += dataRed;
                                                } else {
                                                    data += dataNotRed;
                                                }
                                            }



                                            $('table.cw').append(data);
                                            cw_tempory_limit_row_count++;
                                            cw_tempory_limit_record_no++;

                                            cwTempLimitValuesFormatter();
                                        }
                                    }

                                    $("#sl_has_byr_otstaning").val(v.sl_has_byr_otstaning);
                                    $("#sl_has_byr_tenor").val(v.sl_has_byr_tenor);

                                    if (v.chq_wh_dr_to_cr_spe_narr === '1') {
                                        $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', true);
                                        $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', false);
                                    } else {

                                        $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', true);
                                        $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', false);
                                    }

                                    $("#chq_wh_erly_wdr_chg").val(v.chq_wh_erly_wdr_chg);
                                    $("#chq_wh_vale_dte_extr_chg").val(v.chq_wh_vale_dte_extr_chg);
                                    $("#chq_wh_erly_stlemnt_chg").val(v.chq_wh_erly_stlemnt_chg);
                                    document.getElementById("idndb_customer_define_id").disabled = true;
                                    document.getElementById("idndb_customer_define_name").disabled = true;
                                    document.getElementById("cust_as_seller").disabled = true;
                                    document.getElementById("cust_as_buyer").disabled = true;
                                }

                            });


                        }
                    });


                    var idndb_customer_define_id = $("#idndb_customer_define_id").val().trim();
                    var cust_as_buyer = 'ACTIVE';
                    var paramid = "get_prod_map_data_tofillAsBuyer";
                    var values = "&idndb_customer_define_id=" + idndb_customer_define_id +
                            "&cust_as_buyer=" + cust_as_buyer +
                            "&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            $.each(data, function (k, v) {
                                if (v.data_availablity === 'ACTIVE') {
                                    document.getElementById("idndb_customer_define_id").disabled = true;
                                    document.getElementById("idndb_customer_define_name").disabled = true;
                                    document.getElementById("cust_as_seller").disabled = true;
                                    document.getElementById("cust_as_buyer").disabled = true;
                                    $("#cust_as_seller").prop('checked', false);
                                    alert("Customer already exists as a buyer !");
                                    document.getElementById("cust_as_buyer").disabled = false;
                                    document.getElementById("ch_rev_fin").disabled = true;
                                    document.getElementById("ch_ch_we").disabled = true;

                                }

                            });
                            $("#loading-mask").hide();
                        }
                    });




                } else {
                    $("#prod_rel_map").hide();
                }
            });

            $("#cust_as_buyer").change(function () {
                if (this.checked) {

                    $("#loading-mask").show();
                    $("#cust_as_seller").prop('checked', false);
                    $("#ch_rev_fin").prop('checked', false);
                    $("#ch_ch_we").prop('checked', false);
                    $("#div_rec_fin").hide();
                    $("#dev_chq_wear").hide();
                    $("#prod_rel_map").hide();
                    $("#div-buyer-acc-details").show();

                    var idndb_customer_define_id = $("#idndb_customer_define_id").val().trim();
                    var cust_as_buyer = 'ACTIVE';
                    var paramid = "get_prod_map_data_tofillAsBuyer";
                    var values = "&idndb_customer_define_id=" + idndb_customer_define_id +
                            "&cust_as_buyer=" + cust_as_buyer +
                            "&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            $.each(data, function (k, v) {



                                if (v.data_availablity === 'ACTIVE') {


                                    $("#div_rec_fin").hide();
                                    $("#dev_chq_wear").hide();


                                    $("#div_crelonly_trn_base").hide();
                                    $("#div_crelonly_fixed_base").hide();

                                    $("#prod_rel_map").hide();
                                    $("#cust_as_seller").prop('checked', false);
                                    $("#cust_as_buyer").prop('checked', false);
                                    $("#ch_rev_fin").prop('checked', false);
                                    $("#ch_ch_we").prop('checked', false);


                                    $('#idndb_cust_prod_map').val(v.idndb_cust_prod_map);
                                    $('#idndb_customer_define_id').val(v.idndb_customer_define);
                                    $('#idndb_customer_define_name').val(v.idndb_customer_define);

                                    if (v.prod_relationship_key_seller === 'ACTIVE') {
                                        $("#cust_as_seller").prop('checked', true);
                                        $("#prod_rel_map").show();
                                        $("#div-buyer-acc-details").hide();
                                    }

                                    if (v.prod_relationship_key_buyer === 'ACTIVE') {
                                        $("#cust_as_buyer").prop('checked', true);
                                        $("#div-buyer-acc-details").show();
                                    }

                                    if (v.prod_relationship_res_fin === 'ACTIVE') {
                                        $("#ch_rev_fin").prop('checked', true);
                                        $("#div_rec_fin").show();
                                    }

                                    if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                        $("#ch_ch_we").prop('checked', true);
                                        $("#dev_chq_wear").show();
                                    }



                                    if (v.prod_relationship_status === 'ACTIVE') {
                                        $("#prod_rel_status").prop('checked', true);
                                    } else {

                                        $("#prod_rel_status").prop('checked', false);
                                    }

                                    $('#statusReason').val(v.statusReason);



                                    //$('#rec_finance_curr').val(v.rec_finance_curr);
                                    $('#rec_finance_limit').val(v.rec_finance_limit);

                                    //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                                    var rf_temporary_limits_string = v.rf_temporary_limits + "";
                                    if (rf_temporary_limits_string !== "") {
                                        var rf_temporary_limits_arr = rf_temporary_limits_string.split(",");
                                        var firstRecord = rf_temporary_limits_arr[0] + "";

                                        var firstRecordValues_arr = firstRecord.split("##");

                                        $("#rf_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                        $("#rf_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                        $("#rf_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                        for (var row_counter = 1; row_counter < rf_temporary_limits_arr.length; row_counter++) {
                                            var record = rf_temporary_limits_arr[row_counter] + "";
                                            var recordValues_arr = record.split("##");
                                            count = $('table.rf tr').length;
                                            var data = "<tr><td><input type='checkbox' class='rf_case'/><input type='hidden' id='rf_temporary_limit_record_no" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                            data += "<td><input type='text' class='form-control rf_temporary_limit_value' id='rf_temporary_limit" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                            $('table.rf').append(data);
                                            rf_tempory_limit_row_count++;
                                            rf_tempory_limit_record_no++;

                                            rfTempLimitValuesFormatter();
                                        }
                                    }


                                    //---------------------------------------------------------


                                    var buyer_accs_string = v.buyer_accs + "";

                                    if (buyer_accs_string !== "") {
                                        var buyer_accs_arr = buyer_accs_string.split(",");
                                        var firstRecord = buyer_accs_arr[0] + "";

                                        var firstRecordValues_arr = firstRecord.split("##");

                                        $("#rf_buyer_accs_record_no0").val(firstRecordValues_arr[0]);
                                        $("#rf_buyer_accs_bank_code0").val(firstRecordValues_arr[1]);
                                        $("#rf_buyer_accs_branch_code0").val(firstRecordValues_arr[2]);
                                        $("#rf_buyer_accs_acc_no0").val(firstRecordValues_arr[3]);

                                        for (var row_counter = 1; row_counter < buyer_accs_arr.length; row_counter++) {
                                            var record = buyer_accs_arr[row_counter] + "";
                                            var recordValues_arr = record.split("##");
                                            count = $('table.rf2 tr').length;
                                            var data = "<tr><td><input type='checkbox' class='rf_case_buyer_accs'/><input type='hidden' id='rf_buyer_accs_record_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                            data += "<td><input type='text' class='form-control rf_buyer_accs_bank_code_value ' id='rf_buyer_accs_bank_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_bank_code[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[1] + "'/></td> \n\
                                                     <td><input type='text' class='form-control rf_buyer_accs_branch_code_value' id='rf_buyer_accs_branch_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_branch_code[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[2] + "'/></td> \n\
                                                     <td><input type='text' class='form-control rf_buyer_accs_acc_no_value' id='rf_buyer_accs_acc_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_acc_no[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[3] + "'/></td></tr>";

                                            $('table.rf2').append(data);
                                            rf_buyer_accs_row_count++;
                                            rf_buyer_accs_record_no++;

                                        }
                                    }


                                    $('#rec_finance_Outstanding').val(v.rec_finance_Outstanding);
                                    $('#rec_finance_tenor').val(v.rec_finance_tenor);
                                    $("#rec_finance_inerest_rate").val(v.rec_finance_inerest_rate);
                                    $("#rec_finance_financing").val(v.rec_finance_financing);
                                    $("#rec_finance_acc_num").val(v.rec_finance_acc_num);
                                    $("#rec_finance_cr_dsc_proc_acc_num").val(v.rec_finance_cr_dsc_proc_acc_num);
                                    $("#rec_finance_current_ac").val(v.rec_finance_current_ac);
                                    $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                                    $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                                    $("#rec_finance_margin_ac").val(v.rec_finance_margin_ac);
                                    $("#rec_finance_margin").val(v.rec_finance_margin);
                                    $("#cust_credit_rate").val(v.cust_credit_rate);


                                    if (v.rec_finance_bulk_credit === 'BULK CREDIT ACTIVE') {
                                        $("#ch_rec_fin_b_c_yes").prop('checked', true);
                                        $("#ch_rec_fin_b_c_no").prop('checked', false);
                                    } else {

                                        $("#ch_rec_fin_b_c_no").prop('checked', true);
                                        $("#ch_rec_fin_b_c_yes").prop('checked', false);
                                    }
                                    if (v.rec_finance_balance_transfer === 'BALANCE TRANSFER ACTIVE') {
                                        $("#ch_rec_fin_balance_transfer_yes").prop('checked', true);
                                        $("#ch_rec_fin_balance_transfer_no").prop('checked', false);
                                    } else {

                                        $("#ch_rec_fin_balance_transfer_no").prop('checked', true);
                                        $("#ch_rec_fin_balance_transfer_yes").prop('checked', false);
                                    }



                                    $("#rec_finance_erly_wdr_chg").val(v.rec_finance_erly_wdr_chg);
                                    $("#rec_finance_vale_dte_extr_chg").val(v.rec_finance_vale_dte_extr_chg);
                                    $("#rec_finance_erly_stlemnt_chg").val(v.rec_finance_erly_stlemnt_chg);

                                    // chque ware housing  

                                    $("#chq_wh_limit").val(v.chq_wh_limit);

                                    //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                                    var cw_temporary_limits_string = v.cw_temporary_limits + "";
                                    if (cw_temporary_limits_string !== "") {
                                        var cw_temporary_limits_arr = cw_temporary_limits_string.split(",");
                                        var firstRecord = cw_temporary_limits_arr[0] + "";

                                        var firstRecordValues_arr = firstRecord.split("##");

                                        $("#cw_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                        $("#cw_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                        $("#cw_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                        for (var row_counter = 1; row_counter < cw_temporary_limits_arr.length; row_counter++) {
                                            var record = cw_temporary_limits_arr[row_counter] + "";
                                            var recordValues_arr = record.split("##");
                                            count = $('table.cw tr').length;
                                            var data = "<tr><td><input type='checkbox' class='cw_case'/><input type='hidden' id='cw_temporary_limit_record_no" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                            data += "<td><input type='text' class='form-control cw_temporary_limit_value' id='cw_temporary_limit" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                            $('table.cw').append(data);
                                            cw_tempory_limit_row_count++;
                                            cw_tempory_limit_record_no++;

                                            cwTempLimitValuesFormatter();
                                        }
                                    }
                                    $("#sl_has_byr_otstaning").val(v.sl_has_byr_otstaning);
                                    $("#sl_has_byr_tenor").val(v.sl_has_byr_tenor);

                                    if (v.chq_wh_dr_to_cr_spe_narr === '1') {
                                        $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', true);
                                        $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', false);
                                    } else {

                                        $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', true);
                                        $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', false);
                                    }

                                    $("#chq_wh_erly_wdr_chg").val(v.chq_wh_erly_wdr_chg);
                                    $("#chq_wh_vale_dte_extr_chg").val(v.chq_wh_vale_dte_extr_chg);
                                    $("#chq_wh_erly_stlemnt_chg").val(v.chq_wh_erly_stlemnt_chg);
                                    document.getElementById("idndb_customer_define_id").disabled = true;
                                    document.getElementById("idndb_customer_define_name").disabled = true;
                                    document.getElementById("cust_as_seller").disabled = true;
                                    document.getElementById("cust_as_buyer").disabled = true;
                                }

                            });
                        }
                    });


                    var idndb_customer_define_id = $("#idndb_customer_define_id").val().trim();
                    var cust_as_seller = 'ACTIVE';
                    var paramid = "get_prod_map_data_tofillAsSeller";
                    var values = "&idndb_customer_define_id=" + idndb_customer_define_id +
                            "&cust_as_seller=" + cust_as_seller +
                            "&paramid=" + paramid;
                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerDataRetrive",
                        data: values,
                        dataType: "json",
                        //if received a response from the server
                        success: function (data) {
                            $.each(data, function (k, v) {



                                if (v.data_availablity === 'ACTIVE') {

                                    document.getElementById("idndb_customer_define_id").disabled = true;
                                    document.getElementById("idndb_customer_define_name").disabled = true;
                                    document.getElementById("cust_as_seller").disabled = true;
                                    document.getElementById("cust_as_buyer").disabled = true;
                                    $("#cust_as_buyer").prop('checked', false);

                                    document.getElementById("cust_as_seller").disabled = false;
                                }

                            });
                        }
                    });

                    $("#loading-mask").hide();


                }
            });


            $("#ch_rec_fin_b_c_yes").change(function () {
                if (this.checked) {
                    $("#ch_rec_fin_b_c_no").prop('checked', false);
                }
            });
            $("#ch_rec_fin_b_c_no").change(function () {
                if (this.checked) {
                    $("#ch_rec_fin_b_c_yes").prop('checked', false);
                }
            });

            $("#ch_rec_fin_balance_transfer_yes").change(function () {
                if (this.checked) {
                    $("#ch_rec_fin_balance_transfer_no").prop('checked', false);
                }
            });
            $("#ch_rec_fin_balance_transfer_no").change(function () {
                if (this.checked) {
                    $("#ch_rec_fin_balance_transfer_yes").prop('checked', false);
                }
            });


            $("#chq_wh_dr_to_cr_spe_narr_yes").change(function () {
                if (this.checked) {
                    $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', false);
                }
            });
            $("#chq_wh_dr_to_cr_spe_narr_no").change(function () {
                if (this.checked) {
                    $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', false);
                }
            });





            $("#idndb_customer_define_id").change(function () {

                var idndb_customer_define_id = $("#idndb_customer_define_id").val();
                var paramid = "idndb_customer_define_data_read";
                var values = "&idndb_customer_define=" + idndb_customer_define_id + "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    success: function (data) {
                        $.each(data, function (k, v) {

                            $('#idndb_customer_define_name').val(v.idndb_customer_define);
                            $("#rec_finance_acc_num").val(v.rec_finance_acc_num);
                            $("#rec_finance_cr_dsc_proc_acc_num").val(v.rec_finance_cr_dsc_proc_acc_num);
                            $("#rec_finance_current_ac").val(v.rec_finance_curr_ac);
                            $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                            $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                            $("#rec_finance_margin_ac").val(v.rec_finance_margin_ac);
                            $("#rec_finance_margin").val(v.rec_finance_margin);
                            $("#cust_credit_rate").val(v.cust_credit_rate);

                        });
                    }
                });

            });

            $("#idndb_customer_define_name").change(function () {

                var idndb_customer_define_id = $("#idndb_customer_define_name").val();
                var paramid = "idndb_customer_define_data_read";
                var values = "&idndb_customer_define=" + idndb_customer_define_id + "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    success: function (data) {
                        $.each(data, function (k, v) {

                            $('#idndb_customer_define_id').val(v.idndb_customer_define);
                            $("#rec_finance_acc_num").val(v.rec_finance_acc_num);
                            $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                            $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                            $("#rec_finance_cr_dsc_proc_acc_num").val(v.rec_finance_cr_dsc_proc_acc_num);
                            $("#rec_finance_current_ac").val(v.rec_finance_curr_ac);
                            $("#rec_finance_margin_ac").val(v.rec_finance_margin_ac);
                            $("#rec_finance_margin").val(v.rec_finance_margin);
                            $("#cust_credit_rate").val(v.cust_credit_rate);

                        });
                    }
                });

            });

            $("#cust_prod_map_save").click(function (e) {


                if (validateInput()) {
                    $("#loading-mask").show();
                    $("#cust_prod_map_save").prop("disabled", true);
                    var paramid = "cust_prod_maping_data_save";


                    var rf_buyer_accs = "";


                    for (var counter = 0; counter < rf_buyer_accs_row_count; counter++) {
                        var rf_buyer_accs_record_no = $('#rf_buyer_accs_record_no' + counter + '').val();
                        if (rf_buyer_accs_record_no !== undefined) {
                            var rf_buyer_accs_bank_code = $('#rf_buyer_accs_bank_code' + counter + '').val().trim();
                            var rf_buyer_accs_branch_code = $('#rf_buyer_accs_branch_code' + counter + '').val().trim();
                            var rf_buyer_accs_acc_no = $('#rf_buyer_accs_acc_no' + counter + '').val().trim();
                            rf_buyer_accs += rf_buyer_accs_record_no + "##" + rf_buyer_accs_bank_code + "##" + rf_buyer_accs_branch_code + "##" + rf_buyer_accs_acc_no + ","
                        }

                    }








                    if (rf_buyer_accs.charAt(rf_buyer_accs.length - 1) === ",") {
                        rf_buyer_accs = rf_buyer_accs.substring(0, rf_buyer_accs.length - 1);
                    }



                    var rf_temporary_limits = "";
                    for (var counter = 0; counter < rf_tempory_limit_row_count; counter++) {
                        var rf_temporary_limit_record_no = $('#rf_temporary_limit_record_no' + counter + '').val();
                        if (rf_temporary_limit_record_no != undefined) {
                            var rf_temporary_limit = $('#rf_temporary_limit' + counter + '').val().trim().replace(/\,/g, '');
                            var rf_temporary_limit_exp_date = $('#rf_temporary_limit_exp_date' + counter + '').val().trim();
                            rf_temporary_limits += rf_temporary_limit_record_no + "##" + rf_temporary_limit + "##" + rf_temporary_limit_exp_date + ","
                        }

                    }


                    if (rf_temporary_limits.charAt(rf_temporary_limits.length - 1) === ",") {
                        rf_temporary_limits = rf_temporary_limits.substring(0, rf_temporary_limits.length - 1);
                    }

                    var cw_temporary_limits = "";
                    for (var counter = 0; counter < cw_tempory_limit_row_count; counter++) {
                        var cw_temporary_limit_record_no = $('#cw_temporary_limit_record_no' + counter + '').val();
                        if (cw_temporary_limit_record_no != undefined) {
                            var cw_temporary_limit = $('#cw_temporary_limit' + counter + '').val().trim().replace(/\,/g, '');
                            var cw_temporary_limit_exp_date = $('#cw_temporary_limit_exp_date' + counter + '').val().trim();
                            cw_temporary_limits += cw_temporary_limit_record_no + "##" + cw_temporary_limit + "##" + cw_temporary_limit_exp_date + ","
                        }

                    }


                    if (cw_temporary_limits.charAt(cw_temporary_limits.length - 1) === ",") {
                        cw_temporary_limits = cw_temporary_limits.substring(0, cw_temporary_limits.length - 1);
                    }
                    var statusReason = ($("#statusReason").val() !== '') ? $("#statusReason").val().trim() : "";

                    var values = "&idndb_customer_define_id=" + $("#idndb_customer_define_id").val().trim() +
                            "&cust_as_seller=" + ($("#cust_as_seller").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&cust_as_buyer=" + ($("#cust_as_buyer").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&ch_rev_fin=" + ($("#ch_rev_fin").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&ch_ch_we=" + ($("#ch_ch_we").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&prod_rel_status=" + ($("#prod_rel_status").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&statusReason=" + statusReason +
                            // recivable finance 
                            "&idndb_cust_prod_map=" + $("#idndb_cust_prod_map").val().trim() +
                            "&rec_finance_curr=" + $("#rec_finance_curr").val().trim() +
                            "&rec_finance_limit=" + $("#rec_finance_limit").val().trim().replace(/\,/g, '') +
                            //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                            "&rf_temporary_limits=" + rf_temporary_limits +
                            //added for buyer accounts -- Madhawa_4799        
                            "&rf_buyer_accs=" + rf_buyer_accs +
                            "&rec_finance_Outstanding=" + $("#rec_finance_Outstanding").val().trim().replace(/\,/g, '') +
                            "&rec_finance_tenor=" + $("#rec_finance_tenor").val().trim() +
                            "&rec_finance_inerest_rate=" + $("#rec_finance_inerest_rate").val().trim() +
                            "&rec_finance_financing=" + $("#rec_finance_financing").val().trim() +
                            "&rec_finance_acc_num=" + $("#rec_finance_acc_num").val().trim() +
                            "&rec_finance_cr_dsc_proc_acc_num=" + $("#rec_finance_cr_dsc_proc_acc_num").val().trim() +
                            "&rec_finance_current_ac=" + $("#rec_finance_current_ac").val().trim() +
                            "&rec_finance_margin_ac=" + $("#rec_finance_margin_ac").val().trim() +
                            "&rec_finance_margin=" + $("#rec_finance_margin").val().trim() +
                            "&cust_credit_rate=" + $("#cust_credit_rate").val().trim() +
                            "&ch_rec_fin_b_c_yes=" + ($("#ch_rec_fin_b_c_yes").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&ch_rec_fin_b_c_no=" + ($("#ch_rec_fin_b_c_no").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&ch_rec_fin_balance_transfer_yes=" + ($("#ch_rec_fin_balance_transfer_yes").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&ch_rec_fin_balance_transfer_no=" + ($("#ch_rec_fin_balance_transfer_no").prop('checked') ? "ACTIVE" : "DEACTIVE") +
                            "&rec_finance_erly_wdr_chg=" + $("#rec_finance_erly_wdr_chg").val().trim().replace(/\,/g, '') +
                            "&rec_finance_vale_dte_extr_chg=" + $("#rec_finance_vale_dte_extr_chg").val().trim().replace(/\,/g, '') +
                            "&rec_finance_erly_stlemnt_chg=" + $("#rec_finance_erly_stlemnt_chg").val().trim().replace(/\,/g, '') +
                            "&chq_wh_limit=" + $("#chq_wh_limit").val().trim().replace(/\,/g, '') +
                            //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                            "&cw_temporary_limits=" + cw_temporary_limits +
                            "&sl_has_byr_otstaning=" + $("#sl_has_byr_otstaning").val().trim().replace(/\,/g, '') +
                            "&sl_has_byr_tenor=" + $("#sl_has_byr_tenor").val().trim() +
                            "&chq_wh_dr_to_cr_spe_narr_yes=" + ($("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked') ? "1" : "0") +
                            "&chq_wh_dr_to_cr_spe_narr_no=" + ($("#chq_wh_dr_to_cr_spe_narr_no").prop('checked') ? "1" : "0") +
                            "&chq_wh_erly_wdr_chg=" + $("#chq_wh_erly_wdr_chg").val().trim().replace(/\,/g, '') +
                            "&chq_wh_vale_dte_extr_chg=" + $("#chq_wh_vale_dte_extr_chg").val().trim().replace(/\,/g, '') +
                            "&chq_wh_erly_stlemnt_chg=" + $("#chq_wh_erly_stlemnt_chg").val().trim().replace(/\,/g, '') +
                            "&paramid=" + paramid;


                    $.ajax({
                        type: "POST",
                        url: "/NDBRMS/CustomerServletDate",
                        data: values,
                        dataType: "json", //if received a response from the server
                        success: function (data) {
                            if (data.success) {
                                window.location = "modalsSucess.jsp?desigURL=ndb_prorelationship_setup.jsp&message=Customer product mapping data saved to DB successfuly and sent for authorization.";

                            } else if (data.systemna) {
                                window.location = "modalsError.jsp?desigURL=ndb_prorelationship_setup.jsp&message=" + data.systemna + "";

                            } else if (data.alreadyexdatasel) {
                                window.location = "modalsError.jsp?desigURL=ndb_prorelationship_setup.jsp&message=Error occured in saving seller product mapping data.Customer allready existing in the system as a seller.";

                            } else if (data.alreadyexdatabuy) {
                                window.location = "modalsError.jsp?desigURL=ndb_prorelationship_setup.jsp&message=Error occured in saving seller product mapping data.Customer allready existing in the system as a Buyer.";

                            } else if (data.unauthdataamend) {
                                window.location = "modalsError.jsp?desigURL=ndb_prorelationship_setup.jsp&message=" + data.unauthdataamend + "";

                            } else if (data.error) {
                                window.location = "modalsError.jsp?desigURL=ndb_prorelationship_setup.jsp&message=Error occured in saving seller product mapping data.";

                            }
                        }
                    });
                }

            });

            $("#btn_view_customer_prod_data").click(function (e) {
                var search_criteria = $("#search_criteria").val();
                window.location = '/NDBRMS/pages/MasterFiles/ndb_prorelationship_setup.jsp?table_plus=true&search_criteria=' + search_criteria + '';



            });

            $('#cust_prod_map_clear').click(function () {
                location.reload();
            });



            $("#example1 tbody tr").on("click", function (event) {
                $("#buyerAccDetlTable").remove();
                addBuyerAccDtlTable();

                var pickedup;
                if (pickedup != null) {
                    pickedup.css("background-color", "#ffccff");
                }



                $(this).css("background-color", "#3c8dbc");
                pickedup = $(this);

                var idndb_cust_prod_map = $(this).find("td").eq(0).html();
                var paramid = "get_prod_map_data_tofill";
                var values = "&idndb_cust_prod_map=" + idndb_cust_prod_map +
                        "&paramid=" + paramid;
                $.ajax({
                    type: "POST",
                    url: "/NDBRMS/CustomerDataRetrive",
                    data: values,
                    dataType: "json",
                    //if received a response from the server
                    success: function (data) {
                        $.each(data, function (k, v) {

                            $("#div_rec_fin").hide();
                            $("#dev_chq_wear").hide();


                            $("#div_crelonly_trn_base").hide();
                            $("#div_crelonly_fixed_base").hide();

                            $("#prod_rel_map").hide();
                            $("#cust_as_seller").prop('checked', false);
                            $("#cust_as_buyer").prop('checked', false);
                            $("#ch_rev_fin").prop('checked', false);
                            $("#ch_ch_we").prop('checked', false);


                            $('#idndb_cust_prod_map').val(v.idndb_cust_prod_map);
                            $('#idndb_customer_define_id').val(v.idndb_customer_define);
                            $('#idndb_customer_define_name').val(v.idndb_customer_define);

                            if (v.prod_relationship_key_seller === 'ACTIVE') {
                                $("#cust_as_seller").prop('checked', true);
                                $("#prod_rel_map").show();
                            }

                            if (v.prod_relationship_key_buyer === 'ACTIVE') {
                                $("#cust_as_buyer").prop('checked', true);
                                $("#div-buyer-acc-details").show();
                            }

                            if (v.prod_relationship_res_fin === 'ACTIVE') {
                                $("#ch_rev_fin").prop('checked', true);
                                $("#div_rec_fin").show();
                            }

                            if (v.prod_relationship_chq_ware === 'ACTIVE') {
                                $("#ch_ch_we").prop('checked', true);
                                $("#dev_chq_wear").show();
                            }



                            if (v.prod_relationship_status === 'ACTIVE') {
                                $("#prod_rel_status").prop('checked', true);
                            } else {

                                $("#prod_rel_status").prop('checked', false);
                            }


                            $('#statusReason').val(v.statusReason);



                            var buyer_accs_string = v.buyer_accs + "";

                            if (buyer_accs_string !== "") {
                                var buyer_accs_arr = buyer_accs_string.split(",");
                                var firstRecord = buyer_accs_arr[0] + "";

                                var firstRecordValues_arr = firstRecord.split("##");

                                $("#rf_buyer_accs_record_no0").val(firstRecordValues_arr[0]);
                                $("#rf_buyer_accs_bank_code0").val(firstRecordValues_arr[1]);
                                $("#rf_buyer_accs_branch_code0").val(firstRecordValues_arr[2]);
                                $("#rf_buyer_accs_acc_no0").val(firstRecordValues_arr[3]);



                                for (var row_counter = 1; row_counter < buyer_accs_arr.length; row_counter++) {
                                    var record = buyer_accs_arr[row_counter] + "";
                                    var recordValues_arr = record.split("##");
                                    count = $('table.rf2 tr').length;
                                    var data = "<tr><td><input type='checkbox' class='rf_case_buyer_accs'/><input type='hidden' id='rf_buyer_accs_record_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                    data += "<td><input type='text' class='form-control rf_buyer_accs_bank_code_value ' id='rf_buyer_accs_bank_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_bank_code[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[1] + "'/></td> \n\
                                                     <td><input type='text' class='form-control rf_buyer_accs_branch_code_value' id='rf_buyer_accs_branch_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_branch_code[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[2] + "'/></td> \n\
                                                     <td><input type='text' class='form-control rf_buyer_accs_acc_no_value' id='rf_buyer_accs_acc_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_acc_no[" + rf_buyer_accs_row_count + "]' value='" + recordValues_arr[3] + "'/></td></tr>";

                                    $('table.rf2').append(data);
                                    rf_buyer_accs_row_count++;
                                    rf_buyer_accs_record_no++;

                                }
                            }


                            //$('#rec_finance_curr').val(v.rec_finance_curr);
                            $('#rec_finance_limit').val(v.rec_finance_limit);

                            //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                            var rf_temporary_limits_string = v.rf_temporary_limits + "";
                            if (rf_temporary_limits_string !== "") {
                                var rf_temporary_limits_arr = rf_temporary_limits_string.split(",");
                                var firstRecord = rf_temporary_limits_arr[0] + "";

                                var firstRecordValues_arr = firstRecord.split("##");

                                $("#rf_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                $("#rf_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                $("#rf_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                for (var row_counter = 1; row_counter < rf_temporary_limits_arr.length; row_counter++) {
                                    var record = rf_temporary_limits_arr[row_counter] + "";
                                    var recordValues_arr = record.split("##");
                                    count = $('table.rf tr').length;
                                    var data = "<tr><td><input type='checkbox' class='rf_case'/><input type='hidden' id='rf_temporary_limit_record_no" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                    data += "<td><input type='text' class='form-control rf_temporary_limit_value' id='rf_temporary_limit" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                    $('table.rf').append(data);
                                    rf_tempory_limit_row_count++;
                                    rf_tempory_limit_record_no++;

                                    rfTempLimitValuesFormatter();
                                }
                            }

                            $('#rec_finance_Outstanding').val(v.rec_finance_Outstanding);
                            $('#rec_finance_tenor').val(v.rec_finance_tenor);
                            $("#rec_finance_inerest_rate").val(v.rec_finance_inerest_rate);
                            $("#rec_finance_financing").val(v.rec_finance_financing);
                            $("#rec_finance_acc_num").val(v.rec_finance_acc_num);
                            $("#rec_finance_cr_dsc_proc_acc_num").val(v.rec_finance_cr_dsc_proc_acc_num);
                            $("#rec_finance_current_ac").val(v.rec_finance_current_ac);
                            $("#cms_curr_acc_number").val(v.cms_curr_acc_number);
                            $("#cms_collection_acc_number").val(v.cms_collection_acc_number);
                            $("#rec_finance_margin_ac").val(v.rec_finance_margin_ac);
                            $("#rec_finance_margin").val(v.rec_finance_margin);
                            $("#cust_credit_rate").val(v.cust_credit_rate);


                            if (v.rec_finance_bulk_credit === 'BULK CREDIT ACTIVE') {
                                $("#ch_rec_fin_b_c_yes").prop('checked', true);
                                $("#ch_rec_fin_b_c_no").prop('checked', false);
                            } else {

                                $("#ch_rec_fin_b_c_no").prop('checked', true);
                                $("#ch_rec_fin_b_c_yes").prop('checked', false);
                            }


                            if (v.rec_finance_balance_transfer === 'BALANCE TRANSFER ACTIVE') {
                                $("#ch_rec_fin_balance_transfer_yes").prop('checked', true);
                                $("#ch_rec_fin_balance_transfer_no").prop('checked', false);
                            } else {

                                $("#ch_rec_fin_balance_transfer_no").prop('checked', true);
                                $("#ch_rec_fin_balance_transfer_yes").prop('checked', false);
                            }



                            $("#rec_finance_erly_wdr_chg").val(v.rec_finance_erly_wdr_chg);
                            $("#rec_finance_vale_dte_extr_chg").val(v.rec_finance_vale_dte_extr_chg);
                            $("#rec_finance_erly_stlemnt_chg").val(v.rec_finance_erly_stlemnt_chg);

                            // chque ware housing  

                            $("#chq_wh_limit").val(v.chq_wh_limit);

                            //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
                            var cw_temporary_limits_string = v.cw_temporary_limits + "";
                            if (cw_temporary_limits_string !== "") {
                                var cw_temporary_limits_arr = cw_temporary_limits_string.split(",");
                                var firstRecord = cw_temporary_limits_arr[0] + "";

                                var firstRecordValues_arr = firstRecord.split("##");

                                $("#cw_temporary_limit_record_no0").val(firstRecordValues_arr[0]);
                                $("#cw_temporary_limit0").val(addCommas(firstRecordValues_arr[1]));
                                $("#cw_temporary_limit_exp_date0").val(firstRecordValues_arr[2]);

                                for (var row_counter = 1; row_counter < cw_temporary_limits_arr.length; row_counter++) {
                                    var record = cw_temporary_limits_arr[row_counter] + "";
                                    var recordValues_arr = record.split("##");
                                    count = $('table.cw tr').length;
                                    var data = "<tr><td><input type='checkbox' class='cw_case'/><input type='hidden' id='cw_temporary_limit_record_no" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[0] + "'/></td>";
                                    data += "<td><input type='text' class='form-control cw_temporary_limit_value' id='cw_temporary_limit" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + addCommas(recordValues_arr[1]) + "'/></td> <td><input type='date' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + recordValues_arr[2] + "'/></td></tr>";
                                    $('table.cw').append(data);
                                    cw_tempory_limit_row_count++;
                                    cw_tempory_limit_record_no++;

                                    cwTempLimitValuesFormatter();
                                }
                            }
                            $("#sl_has_byr_otstaning").val(v.sl_has_byr_otstaning);
                            $("#sl_has_byr_tenor").val(v.sl_has_byr_tenor);

                            if (v.chq_wh_dr_to_cr_spe_narr === '1') {
                                $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', true);
                                $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', false);
                            } else {

                                $("#chq_wh_dr_to_cr_spe_narr_no").prop('checked', true);
                                $("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked', false);
                            }

                            $("#chq_wh_erly_wdr_chg").val(v.chq_wh_erly_wdr_chg);
                            $("#chq_wh_vale_dte_extr_chg").val(v.chq_wh_vale_dte_extr_chg);
                            $("#chq_wh_erly_stlemnt_chg").val(v.chq_wh_erly_stlemnt_chg);
                            document.getElementById("idndb_customer_define_id").disabled = true;
                            document.getElementById("idndb_customer_define_name").disabled = true;
                            document.getElementById("cust_as_seller").disabled = true;
                            document.getElementById("cust_as_buyer").disabled = true;

                        });
                    }
                });
            });

            $('#example1').dataTable({
                "aLengthMenu": [10, 25, 50, 100, 500, 20000]
            });


            function validateInput() {

                var hasError = true;
                var idndb_customer_define_id = $("#idndb_customer_define_id").val();
                var idndb_customer_define_name = $("#idndb_customer_define_name").val();


                if (idndb_customer_define_id === 'Select The Customer ID') {
                    $('#idndb_customer_define_id').css('border-color', 'red');
                    return false;
                }
                if (idndb_customer_define_name === 'Select The Customer Name') {
                    $('#idndb_customer_define_name').css('border-color', 'red');
                    return false;
                }

                if (!($("#prod_rel_status").prop('checked'))) {
                    if ($("#statusReason").val() === '') {
                        $('#statusReason').css('border-color', 'red');
                        return false;
                    }
                }


                var seller_or_buyer = "NOT DEFINE";
                if ($("#cust_as_seller").prop('checked')) {
                    seller_or_buyer = "SELLER ACTIVE"
                }
                if ($("#cust_as_buyer").prop('checked')) {
                    seller_or_buyer = "BUYER ACTIVE"
                }

                if (seller_or_buyer === 'NOT DEFINE') {
                    alert("Invalid Relationship Key")
                    return false;
                }

                if (seller_or_buyer === 'BUYER ACTIVE') {
                    var noBuyAccEmptyVal = true;
                    for (var i = 0; i < rf_buyer_accs_row_count; i++) {
                        var rf_buyer_accs_record_no = $('#rf_buyer_accs_record_no' + i + '').val();
                        if (rf_buyer_accs_record_no !== undefined) {
                            var rf_buyer_accs_bank_code_val = $('#rf_buyer_accs_bank_code' + i + '').val();
                            var rf_buyer_accs_branch_code_val = $('#rf_buyer_accs_branch_code' + i + '').val();
                            var rf_buyer_accs_acc_no_val = $('#rf_buyer_accs_acc_no' + i + '').val();

                            if (rf_buyer_accs_bank_code_val === undefined) {
                                noBuyAccEmptyVal = false;
                            }
                            if (rf_buyer_accs_bank_code_val === '' || !(rf_buyer_accs_bank_code_val.length ===4)) {
                                $('#rf_buyer_accs_bank_code' + i).css('border-color', 'red');
                                noBuyAccEmptyVal = false;
                            } else {
                                $('#rf_buyer_accs_bank_code' + i).css('border-color', '#ddd');
                            }

                            if (rf_buyer_accs_branch_code_val === '' || !(rf_buyer_accs_branch_code_val.length ===3)) {
                                $('#rf_buyer_accs_branch_code' + i).css('border-color', 'red');
                                noBuyAccEmptyVal = false;
                            } else {
                                $('#rf_buyer_accs_branch_code' + i).css('border-color', '#ddd');
                            }

                            if (rf_buyer_accs_acc_no_val === '') {
                                $('#rf_buyer_accs_acc_no' + i).css('border-color', 'red');
                                noBuyAccEmptyVal = false;
                            } else {
                                $('#rf_buyer_accs_acc_no' + i).css('border-color', '#ddd');
                            }



                        }

                    }
                    if (!noBuyAccEmptyVal) {
                        return false;
                    }

                }



                if (seller_or_buyer === 'SELLER ACTIVE') {
                    var seller_product = "NOT DEFINE";
                    if ($("#ch_rev_fin").prop('checked')) {
                        seller_product = "RF";
                    }


                    if (seller_product === 'RF') {
                        var rec_finance_curr = $("#rec_finance_curr").val();
                        var rec_finance_limit = $("#rec_finance_limit").val();
                        var rec_finance_inerest_rate = $("#rec_finance_inerest_rate").val();
                        var rec_finance_financing = $("#rec_finance_financing").val();
                        var rec_finance_acc_num = $("#rec_finance_acc_num").val();
                        var rec_finance_cr_dsc_proc_acc_num = $("#rec_finance_cr_dsc_proc_acc_num").val();
                        var rec_finance_margin_ac = $("#rec_finance_margin_ac").val();
                        var rec_finance_margin = $("#rec_finance_margin").val();
                        var cust_credit_rate = $("#cust_credit_rate").val();

                        var rec_finance_erly_wdr_chg = $("#rec_finance_erly_wdr_chg").val();
                        var rec_finance_vale_dte_extr_chg = $("#rec_finance_vale_dte_extr_chg").val();
                        var rec_finance_erly_stlemnt_chg = $("#rec_finance_erly_stlemnt_chg").val();

                        var noRfEmptyVal = true;
                        for (var i = 0; i < rf_tempory_limit_row_count; i++) {
                            var rf_temporary_limit_record_no = $('#rf_temporary_limit_record_no' + i + '').val();
                            if (rf_temporary_limit_record_no !== undefined) {
                                var rf_temporary_limit_val = $('#rf_temporary_limit' + i + '').val();
                                var rf_temporary_limit_exp_date_val = $('#rf_temporary_limit_exp_date' + i + '').val();
                                if (rf_temporary_limit_val === undefined) {
                                    noRfEmptyVal = false;
                                }
                                if (rf_temporary_limit_val === '') {
                                    $('#rf_temporary_limit' + i).css('border-color', 'red');
                                    noRfEmptyVal = false;
                                    if (rf_temporary_limit_exp_date_val === '') {
                                        $('#rf_temporary_limit_exp_date' + i).css('border-color', 'red');
                                        noRfEmptyVal = false;
                                    } else {
                                        $('#rf_temporary_limit_exp_date' + i).css('border-color', '#ddd');
                                    }
                                } else {
                                    $('#rf_temporary_limit' + i).css('border-color', '#ddd');
                                }

                            }
                            if (rf_temporary_limit_val !== '') {
                                if (rf_temporary_limit_exp_date_val === '') {
                                    $('#rf_temporary_limit_exp_date' + i).css('border-color', 'red');
                                    noRfEmptyVal = false;
                                } else {
                                    $('#rf_temporary_limit_exp_date' + i).css('border-color', '#ddd');
                                }
                            }
                        }
                        if (!noRfEmptyVal) {
                            return false;
                        }

                        if (rec_finance_curr === '') {
                            $('#rec_finance_curr').css('border-color', 'red');
                            return false;
                        }
                        if (rec_finance_limit === '') {
                            $('#rec_finance_limit').css('border-color', 'red');
                            return false;
                        }
                        if (rec_finance_financing === '') {
                            $('#rec_finance_financing').css('border-color', 'red');
                            return false;
                        }
                        if (rec_finance_acc_num === '') {
                            $('#rec_finance_acc_num').css('border-color', 'red');
                            return false;
                        }
                        if (rec_finance_cr_dsc_proc_acc_num === '') {
                            $('#rec_finance_cr_dsc_proc_acc_num').css('border-color', 'red');
                            return false;
                        }
                        if (cust_credit_rate === '') {
                            $('#cust_credit_rate').css('border-color', 'red');
                            return false;
                        }
                        var bulk_credit = "NOT DEFINE";
                        if ($("#ch_rec_fin_b_c_yes").prop('checked')) {
                            bulk_credit = "YES"
                        }
                        if ($("#ch_rec_fin_b_c_no").prop('checked')) {
                            bulk_credit = "NO"
                        }

                        var balance_transfer = "NOT DEFINE";
                        if ($("#ch_rec_fin_balance_transfer_yes").prop('checked')) {
                            balance_transfer = "YES"
                        }
                        if ($("#ch_rec_fin_balance_transfer_no").prop('checked')) {
                            balance_transfer = "NO"
                        }

                        if (bulk_credit === 'NOT DEFINE') {
                            alert("Invalid bulk credit ")
                            return false;
                        }

                        if (balance_transfer === 'NOT DEFINE') {
                            alert("Invalid Balance Transfer ")
                            return false;
                        }

                        if (rec_finance_erly_wdr_chg === '') {
                            $('#rec_finance_erly_wdr_chg').css('border-color', 'red');
                            return false;
                        }

                        if (rec_finance_vale_dte_extr_chg === '') {
                            $('#rec_finance_vale_dte_extr_chg').css('border-color', 'red');
                            return false;
                        }

                        if (rec_finance_erly_stlemnt_chg === '') {
                            $('#rec_finance_erly_stlemnt_chg').css('border-color', 'red');
                            return false;
                        }



                    }

                    if ($("#ch_ch_we").prop('checked')) {
                        seller_product = "CW";
                    }

                    if (seller_product === 'CW') {

                        var chq_wh_limit = $("#chq_wh_limit").val();

                        var noCwEmptyVal = true;
                        for (var i = 0; i < cw_tempory_limit_row_count; i++) {
                            var cw_temporary_limit_record_no = $('#cw_temporary_limit_record_no' + i + '').val();
                            if (cw_temporary_limit_record_no !== undefined) {
                                var cw_temporary_limit_val = $('#cw_temporary_limit' + i + '').val();
                                var cw_temporary_limit_exp_date_val = $('#cw_temporary_limit_exp_date' + i + '').val();
                                if (cw_temporary_limit_val === undefined) {
                                    noCwEmptyVal = false;
                                }
                                if (cw_temporary_limit_val === '') {
                                    $('#cw_temporary_limit' + i).css('border-color', 'red');
                                    noCwEmptyVal = false;
                                    if (cw_temporary_limit_exp_date_val === '') {
                                        $('#cw_temporary_limit_exp_date' + i).css('border-color', 'red');
                                        noCwEmptyVal = false;
                                    } else {
                                        $('#cw_temporary_limit_exp_date' + i).css('border-color', '#ddd');
                                    }
                                } else {
                                    $('#cw_temporary_limit' + i).css('border-color', '#ddd');
                                }

                            }
                            if (cw_temporary_limit_val !== '') {
                                if (cw_temporary_limit_exp_date_val === '') {
                                    $('#cw_temporary_limit_exp_date' + i).css('border-color', 'red');
                                    noCwEmptyVal = false;
                                } else {
                                    $('#cw_temporary_limit_exp_date' + i).css('border-color', '#ddd');
                                }
                            }
                        }
                        if (!noCwEmptyVal) {
                            return false;
                        }

                        var chq_wh_dr_to_cr_spe_narr = "NOT DEFINE";
                        if ($("#chq_wh_dr_to_cr_spe_narr_yes").prop('checked')) {
                            chq_wh_dr_to_cr_spe_narr = "YES"
                        }
                        if ($("#chq_wh_dr_to_cr_spe_narr_no").prop('checked')) {
                            chq_wh_dr_to_cr_spe_narr = "NO"
                        }

                        if (chq_wh_dr_to_cr_spe_narr === 'NOT DEFINE') {
                            alert("Please active or de-active DR/CR Transfer (Special Narration).")
                            return false;
                        }



                        var chq_wh_erly_wdr_chg = $("#chq_wh_erly_wdr_chg").val();
                        var chq_wh_vale_dte_extr_chg = $("#chq_wh_vale_dte_extr_chg").val();
                        var chq_wh_erly_stlemnt_chg = $("#chq_wh_erly_stlemnt_chg").val();
                        var cms_curr_acc_number = $("#cms_curr_acc_number").val();
                        var cms_collection_acc_number = $("#cms_collection_acc_number").val();

                        if (chq_wh_limit === '') {
                            $('#chq_wh_limit').css('border-color', 'red');
                            return false;
                        }
                        if (cms_curr_acc_number === '') {
                            $('#cms_curr_acc_number').css('border-color', 'red');
                            return false;
                        }
                        if (cms_collection_acc_number === '') {
                            $('#cms_collection_acc_number').css('border-color', 'red');
                            return false;
                        }

                        if (chq_wh_erly_wdr_chg === '') {
                            $('#chq_wh_erly_wdr_chg').css('border-color', 'red');
                            return false;
                        }

                        if (chq_wh_vale_dte_extr_chg === '') {
                            $('#chq_wh_vale_dte_extr_chg').css('border-color', 'red');
                            return false;
                        }

                        if (chq_wh_erly_stlemnt_chg === '') {
                            $('#chq_wh_erly_stlemnt_chg').css('border-color', 'red');
                            return false;
                        }


                    }
                    if (seller_product === 'NOT DEFINE') {
                        alert("Invalid Product Key")
                        return false;
                    }



                }



                return hasError;
            }


        });

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
                    navigator.msSaveBlob(blob, 'CUST PROD REL DATA.xls');
                }
            } else {
                $('#test').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
                $('#test').attr('download', 'CUST PROD REL DATA.xls');
            }

        }


        $(document).on('click', '#rf_delete', function () {
            $('.rf_case:checkbox:checked').parents("tr").remove();
            $('.rf_check_all').prop("checked", false);
            rf_check();

        });

        $(document).on('click', '#rf_delete_buyer_accs', function () {
            $('.rf_case_buyer_accs:checkbox:checked').parents("tr").remove();
            $('.rf_check_all_buyer_accs').prop("checked", false);
            rf_check();

        });


        var rf_tempory_limit_row_count = 1;

        $(document).on('click', '#rf_addmore', function () {
            count = $('table.rf tr').length;
            var data = "<tr><td><input type='checkbox' class='rf_case'/><input type='hidden' id='rf_temporary_limit_record_no" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]' value='" + rf_tempory_limit_record_no + "'/></td>";
            data += "<td><input type='text' class='form-control rf_temporary_limit_value' id='rf_temporary_limit" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]'/></td> \n\
                    <td><input type='date' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date" + rf_tempory_limit_row_count + "' name='rf_temporary_limit[" + rf_tempory_limit_row_count + "]'/></td></tr>";
            $('table.rf').append(data);
            var today = new Date().toISOString().split('T')[0];
            $('.rf_temporary_limit_exp_date').attr('min', today);
            rf_tempory_limit_row_count++;
            rf_tempory_limit_record_no++;
            rfTempLimitValuesFormatter();


        });

        var rf_buyer_accs_row_count = 1;
        $(document).on('click', '#rf_addmore_buyer_accs', function () {
            count = $('table.rf2 tr').length;
            var data = "<tr><td><input type='checkbox' class='rf_case_buyer_accs'/><input type='hidden' id='rf_buyer_accs_record_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs[" + rf_buyer_accs_row_count + "]' value='" + rf_buyer_accs_record_no + "'/></td>";
            data += "<td><input type='text' class='form-control f_buyer_accs_bank_code_value' id='rf_buyer_accs_bank_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_bank_code[" + rf_buyer_accs_row_count + "]'/></td> \n\
                     <td><input type='text' class='form-control rf_buyer_accs_branch_code_value' id='rf_buyer_accs_branch_code" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_branch_code[" + rf_buyer_accs_row_count + "]'/></td> \n\
                     <td><input type='text' class='form-control rf_buyer_accs_acc_no_value' id='rf_buyer_accs_acc_no" + rf_buyer_accs_row_count + "' name='rf_buyer_accs_acc_no[" + rf_buyer_accs_row_count + "]'/></td></tr>";
            $('table.rf2').append(data);
            rf_buyer_accs_row_count++;
            rf_buyer_accs_record_no++;


        });


        function rf_select_all() {
            $('input[class=rf_case]:checkbox').each(function () {
                if ($('input[class=rf_check_all]:checkbox:checked').length == 0) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            });
        }

        function rf_select_all_buyer_accs() {
            $('input[class=rf_case_buyer_accs]:checkbox').each(function () {
                if ($('input[class=rf_check_all_buyer_accs]:checkbox:checked').length == 0) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            });
        }

        function rf_check() {
            obj = $('.table.rf tr').find('span');
            $.each(obj, function (key, value) {

                id = value.id;
                $('#' + id).html(key + 1);
            });
        }

        var rf_tempory_limit_record_no = 1;
        var rf_buyer_accs_record_no = 1;

        function rfTempLimitValuesFormatter() {

            //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
            $(".rf_temporary_limit_value").keyup(function () {
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

            //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977

            $(".rf_temporary_limit_value").focusout(function () {
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

        }

        $(document).on('click', '#cw_delete', function () {
            $('.cw_case:checkbox:checked').parents("tr").remove();
            $('.cw_check_all').prop("checked", false);
            cw_check();

        });


        var cw_tempory_limit_row_count = 1;

        $(document).on('click', '#cw_addmore', function () {
            count = $('table.cw tr').length;
            var data = "<tr><td><input type='checkbox' class='cw_case'/><input type='hidden' id='cw_temporary_limit_record_no" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]' value='" + cw_tempory_limit_record_no + "'/></td>";
            data += "<td><input type='text' class='form-control cw_temporary_limit_value' id='cw_temporary_limit" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]'/></td> <td><input type='date' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date" + cw_tempory_limit_row_count + "' name='cw_temporary_limit[" + cw_tempory_limit_row_count + "]'/></td></tr>";
            $('table.cw').append(data);
            var today = new Date().toISOString().split('T')[0];
            $('.cw_temporary_limit_exp_date').attr('min', today)
            cw_tempory_limit_row_count++;
            cw_tempory_limit_record_no++;
            cwTempLimitValuesFormatter();


        });
        function cw_select_all() {
            $('input[class=cw_case]:checkbox').each(function () {
                if ($('input[class=cw_check_all]:checkbox:checked').length == 0) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            });
        }

        function cw_check() {
            obj = $('.table.cw tr').find('span');
            $.each(obj, function (key, value) {

                id = value.id;
                $('#' + id).html(key + 1);
            });
        }

        var cw_tempory_limit_record_no = 1;

        function addBuyerAccDtlTable() {
            $('#buyerAccDtlDiv').append('<table class="table rf2" id="buyerAccDetlTable"><tr><th><input class="rf_check_all_buyer_accs" type="checkbox" onclick="rf_select_all_buyer_accs()"/></th><th>Bank Code</th><th>Branch Code</th><th>Account</th></tr><tr><div class="col-sm-1"><td><input type="checkbox" class="rf_case_buyer_accs"/><input type="hidden" id="rf_buyer_accs_record_no0" name="rf_buyer_accs[0]" value="0"/></td></div><div class="col-sm-2"><td><input type="text" class="form-control rf_buyer_accs_bank_code_value" id="rf_buyer_accs_bank_code0" name="rf_buyer_accs_bank_code[0]"/></td></div><div class="col-sm-2"><td><input type="text" class="form-control rf_buyer_accs_branch_code_value" id="rf_buyer_accs_branch_code0" name="rf_buyer_accs_branch_code[0]"/></td></div><div class="col-sm-2"><td><input type="text" class="form-control rf_buyer_accs_acc_no_value" id="rf_buyer_accs_acc_no0" name="rf_buyer_accs_acc_no[0]"/></td></div></tr></table>');
        }

        function cwTempLimitValuesFormatter() {

            //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
            $(".cw_temporary_limit_value").keyup(function () {
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

            //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977

            $(".cw_temporary_limit_value").focusout(function () {
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



        }
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
                        Customer Product Mapping
                        <small>Product Data</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Master Files</a></li>
                        <li class="active">Customer Product Mapping</li>
                    </ol>
                </section>
                <section class="content">



                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box box-info">
                                <form class="form-horizontal">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Customer ID</label>
                                            <div class="col-sm-10">
                                                <input type="hidden" class="form-control" id="idndb_cust_prod_map" name="idndb_cust_prod_map" style="width: 350px">

                                                <select id="idndb_customer_define_id" class="form-control" style="width: 350px;">
                                                    <option>Select The Customer ID</option>
                                                    <% if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_Id_ActiveDataCmb(session.getAttribute("userid").toString()));

                                                        }

                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Customer Name</label>
                                            <div class="col-sm-10">
                                                <select id="idndb_customer_define_name" class="form-control" style="width: 350px;">
                                                    <option>Select The Customer Name</option>
                                                    <%  if (_session_availability) {

                                                            out.print(new FillDataComboBean().getCust_name_ActiveDataCmb(session.getAttribute("userid").toString()));

                                                        }


                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Relationship Key</label>

                                            <div class="col-sm-10">

                                                <input type="checkbox" id="cust_as_seller"> Seller
                                                <input type="checkbox" id="cust_as_buyer"> Buyer

                                            </div>

                                        </div>

                                        <div id="prod_rel_map">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">Product Relationship</label>
                                                <div class="col-sm-10">
                                                    <input type="checkbox" id="ch_rev_fin"> Receivable Finance
                                                    <input type="checkbox" id="ch_ch_we"> Cheque Warehousing
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="div-buyer-acc-details">
                                            <label class="col-sm-2 control-label">Buyer A/C Details</label>
                                            <div class="col-sm-5" id="buyerAccDtlDiv">
                                                <table class="table rf2" id="buyerAccDetlTable">
                                                    <tr>
                                                        <th><input class='rf_check_all_buyer_accs' type='checkbox' onclick="rf_select_all_buyer_accs()"/></th>
                                                        <th>Bank Code</th>
                                                        <th>Branch Code</th>
                                                        <th>Account</th>
                                                    </tr>
                                                    <tr>
                                                    <div class="col-sm-1">
                                                        <td><input type='checkbox' class='rf_case_buyer_accs'/><input type='hidden' id='rf_buyer_accs_record_no0' name='rf_buyer_accs[0]' value='0'/></td>
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <td><input type='text' class='form-control rf_buyer_accs_bank_code_value' id='rf_buyer_accs_bank_code0' name='rf_buyer_accs_bank_code[0]'/></td>
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <td><input type='text' class='form-control rf_buyer_accs_branch_code_value' id='rf_buyer_accs_branch_code0' name='rf_buyer_accs_branch_code[0]'/></td>    
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <td><input type='text' class='form-control rf_buyer_accs_acc_no_value' id='rf_buyer_accs_acc_no0' name='rf_buyer_accs_acc_no[0]'/></td>    
                                                    </div>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="col-sm-5">
                                                <button type="button" id='rf_delete_buyer_accs' class='btn btn-default'>- Delete</button>
                                                <button type="button" id='rf_addmore_buyer_accs' class='btn btn-default'>+ Add More</button>
                                            </div>
                                        </div>         


                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Product Relationship Status</label>
                                            <div class="col-sm-1">
                                                <input type="checkbox" id="prod_rel_status">Click to active
                                            </div>
                                            <div class="col-sm-4">
                                                <input type="text" id="statusReason" class="form-control" placeholder="Reason for Activating/De-activating"/>
                                            </div>
                                            <div class="col-sm-5"></div>
                                        </div>




                                    </div><!-- /.box-body -->
                                </form>
                            </div><!-- /.box -->


                            <div class="box box-info" id="div_rec_fin">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Receivable Finance</h3>
                                </div><!-- /.box-header -->
                                <form class="form-horizontal">

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Currency</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_curr" name="rec_finance_curr"  value="LKR" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Receivable Finance Limit</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_limit" name="rec_finance_limit" placeholder="0.00" style="width: 350px">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">RF Temporary Limit</label>
                                        <div class="col-sm-5">
                                            <table class="table rf">
                                                <tr>
                                                    <th><input class='rf_check_all' type='checkbox' onclick="rf_select_all()"/></th>
                                                    <th>Limit</th>
                                                    <th>Expiry Date</th>
                                                </tr>
                                                <tr>
                                                <div class="col-sm-1">
                                                    <td><input type='checkbox' class='rf_case'/><input type='hidden' id='rf_temporary_limit_record_no0' name='rf_temporary_limit[0]' value='0'/></td>
                                                </div>
                                                <div class="col-sm-2">
                                                    <td><input type='text' class='form-control rf_temporary_limit_value' id='rf_temporary_limit0' name='rf_temporary_limit[0]'/></td>
                                                </div>
                                                <div class="col-sm-2">
                                                    <td><input type='date' class='form-control rf_temporary_limit_exp_date' id='rf_temporary_limit_exp_date0' name='rf_temporary_limit[0]'/></td>    
                                                </div>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="col-sm-5">
                                            <button type="button" id='rf_delete' class='btn btn-default'>- Delete</button>
                                            <button type="button" id='rf_addmore' class='btn btn-default'>+ Add More</button>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_Outstanding" name="rec_finance_Outstanding" placeholder="0.00" value="0.00" style="width: 350px" onkeyup="this.value = addCommas(this.value);">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_tenor" name="rec_finance_tenor" placeholder="0" value="120" style="width: 350px">
                                        </div>
                                    </div>


                                    <div id="interest_rate">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Interest Rate(%)</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="rec_finance_inerest_rate" name="rec_finance_inerest_rate" placeholder="0.00" value="0.00" style="width: 350px" onchange="handleChange(this);">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Financing (%)</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_financing" name="rec_finance_financing" style="width: 350px" onchange="handleChange(this);">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Receivable Account No:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_acc_num" name="rec_finance_acc_num"  style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Cre. Dsc. Proceeds to A/C No:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_cr_dsc_proc_acc_num" name="rec_finance_cr_dsc_proc_acc_num"  style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Current A/C No:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_current_ac" name="rec_finance_current_ac"  style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Margin A/C No:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_margin_ac" name="rec_finance_margin_ac"  style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Margin (%)</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_margin" name="rec_finance_margin" placeholder="0.00"  style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Credit Rating</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="cust_credit_rate" name="cust_credit_rate" placeholder="0.00"  style="width: 350px">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Bulk Credit</label>
                                        <div class="col-sm-10">
                                            <input type="checkbox" id="ch_rec_fin_b_c_yes">Yes
                                            <input type="checkbox" id="ch_rec_fin_b_c_no">No
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Balance Transfer</label>
                                        <div class="col-sm-10">
                                            <input type="checkbox" id="ch_rec_fin_balance_transfer_yes">Yes
                                            <input type="checkbox" id="ch_rec_fin_balance_transfer_no">No
                                        </div>
                                    </div>



                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Early Withdrawal Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_erly_wdr_chg" name="rec_finance_erly_wdr_chg" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Value Date Extenstion Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_vale_dte_extr_chg" name="rec_finance_vale_dte_extr_chg" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Early Settlement Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="rec_finance_erly_stlemnt_chg" name="rec_finance_erly_stlemnt_chg" placeholder="0.00" value="0.00" style="width: 350px" >
                                        </div>
                                    </div>
                                    <hr>
                                </form>
                            </div>

                            <div class="box box-info" id="dev_chq_wear">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Cheque Warehousing</h3>
                                </div><!-- /.box-header -->
                                <form class="form-horizontal">
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
                                        <label class="col-sm-2 control-label">Cheque Warehousing Limit</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="chq_wh_limit" name="chq_wh_limit" placeholder="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">CW Temporary Limit</label>
                                        <div class="col-sm-5">
                                            <table class="table cw">
                                                <tr>
                                                    <th><input class='cw_check_all' type='checkbox' onclick="cw_select_all()"/></th>
                                                    <th>Limit</th>
                                                    <th>Expiry Date</th>
                                                </tr>
                                                <tr>
                                                <div class="col-sm-1">
                                                    <td><input type='checkbox' class='cw_case'/><input type='hidden' id='cw_temporary_limit_record_no0' name='cw_temporary_limit[0]' value='0'/></td>
                                                </div>
                                                <div class="col-sm-2">
                                                    <td><input type='text' class='form-control cw_temporary_limit_value' id='cw_temporary_limit0' name='cw_temporary_limit[0]'/></td>
                                                </div>
                                                <div class="col-sm-2">
                                                    <td><input type='date' class='form-control cw_temporary_limit_exp_date' id='cw_temporary_limit_exp_date0' name='cw_temporary_limit[0]'/></td>    
                                                </div>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="col-sm-5">
                                            <button type="button" id='cw_delete' class='btn btn-default'>- Delete</button>
                                            <button type="button" id='cw_addmore' class='btn btn-default'>+ Add More</button>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Outstanding</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_otstaning" name="sl_has_byr_otstaning" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">DR/CR Transfer (Special Narration) </label>
                                        <div class="col-sm-10">
                                            <input type="checkbox" id="chq_wh_dr_to_cr_spe_narr_yes">Active
                                            <input type="checkbox" id="chq_wh_dr_to_cr_spe_narr_no">De-active
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Tenor</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="sl_has_byr_tenor" name="sl_has_byr_tenor" placeholder="Days" value="120" style="width: 350px">
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Early Withdrawal Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="chq_wh_erly_wdr_chg" name="chq_wh_erly_wdr_chg" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Value Date Extenstion Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="chq_wh_vale_dte_extr_chg" name="chq_wh_vale_dte_extr_chg" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Early Settlement Chg:</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="chq_wh_erly_stlemnt_chg" name="chq_wh_erly_stlemnt_chg" placeholder="0.00" value="0.00" style="width: 350px">
                                        </div>
                                    </div>
                                    <hr>
                                </form>
                            </div>
                            <div class="box box-info">


                                <div class="box-footer">
                                    <input type="text" class="form-control" id="search_criteria" name="search_criteria" style="width: 350px;">
                                    <button type="submit" class="btn btn-info pull-right" id="cust_prod_map_clear" style="margin-top: -33px;">Cancel</button>
                                    <button type="submit" class="btn btn-info pull-right" id="cust_prod_map_save" style="margin-right: 5px;margin-top: -33px;">Submit</button>
                                    <button type="submit" class="btn btn-info pull-right" id="btn_view_customer_prod_data" style="margin-top: -34px;margin-right: 314px;">View Customer Product Mapping Data</button>
                                </div><!-- /.box-footer -->

                            </div>
                        </div><!-- /.box -->
                    </div>


                </section>
                <!-- Main content -->
                <%                    String id_table_plus = request.getParameter("table_plus");
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
                                    <h3 class="box-title">Customer Product Mapping Data :</h3>
                                    <a href="#" id="test" onClick="javascript:fnExcelReport();">Click Here To Download</a>
                                </div><!-- /.box-header -->
                                <div class="box-body" style="overflow-y: auto;">
                                    <table id="example1" class="table table-bordered table-striped" style="cursor: pointer;">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Customer ID</th>
                                                <th>Customer Name</th>
                                                <th>Relationship Key</th>
                                                <th>Product Relationship</th>
                                                <th>Status</th>
                                                <th>Approval</th>
                                                <th>Latest Changes</th>
                                                <th>Mod. By/Date</th>
                                                <th>Auth. By/Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%   if (_session_availability) {
                                                    out.print(new FIllDataTableBean().getCustomerProdMapData(search_criteria));
                                                }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Customer ID</th>
                                                <th>Customer Name</th>
                                                <th>Relationship Key</th>
                                                <th>Product Relationship</th>
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
