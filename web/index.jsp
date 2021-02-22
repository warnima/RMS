<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<%

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    boolean monday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    boolean tuesdat = cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    boolean wednesday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    boolean thersday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    boolean friday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;

%>
<%if (monday) {%>
<html style=" background-image:url('./dist/img/mday.jpg');
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;">
    <% } %>
    <%if (tuesdat) {%>
    <html style=" background-image:url('./dist/img/tuday.jpg');
          -webkit-background-size: cover;
          -moz-background-size: cover;
          -o-background-size: cover;
          background-size: cover;">
        <% } %>
        <%if (wednesday) {%>
        <html style=" background-image:url('./dist/img/wday.jpg');
              -webkit-background-size: cover;
              -moz-background-size: cover;
              -o-background-size: cover;
              background-size: cover;">
            <% } %>
            <%if (thersday) {%>
            <html style=" background-image:url('./dist/img/thday.png');
                  -webkit-background-size: cover;
                  -moz-background-size: cover;
                  -o-background-size: cover;
                  background-size: cover;">
                <% } %>
                <%if (friday) {%>
                <html style=" background-image:url('./dist/img/fday.jpg');
                      -webkit-background-size: cover;
                      -moz-background-size: cover;
                      -o-background-size: cover;
                      background-size: cover;">
                    <% }%>


                    <head>
                        <meta charset="utf-8">
                        <meta http-equiv="X-UA-Compatible" content="IE=edge">
                        <title>NDB BANK | RMS LOG-ON</title>
                        <!-- Tell the browser to be responsive to screen width -->
                        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
                        <!-- Bootstrap 3.3.5 -->
                        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
                        <!-- Font Awesome -->

                        <link rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">

                        <!-- Ionicons -->

                        <link rel="stylesheet" href="../../bootstrap/css/ionicons.min.css">
                        <!-- Theme style -->


                        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
                        <link rel="stylesheet" href="dist/css/font-awesome.min.css">
                        <link rel="stylesheet" href="dist/css/ionicons.min.css">
                        <!-- AdminLTE Skins. Choose a skin from the css/skins
                             folder instead of downloading all of them to reduce the load. -->
                        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
                        <!-- iCheck -->
                        <link rel="stylesheet" href="plugins/iCheck/flat/blue.css">
                        <!-- Morris chart -->
                        <link rel="stylesheet" href="plugins/morris/morris.css">
                        <!-- jvectormap -->
                        <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">
                        <!-- Date Picker -->
                        <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
                        <!-- Daterange picker -->
                        <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
                        <!-- bootstrap wysihtml5 - text editor -->
                        <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
                        <link rel="shortcut icon" href="./dist/img/NDBBANK.png" alt="..." class="img-circle"/>

                        
                    </head>
                    <SCRIPT type="text/javascript">
                        window.history.forward();                       
                       

                    </SCRIPT>
                    <body class="hold-transition login-page" style="
                          opacity: 1.0px;
                          opacity: 0.8;
                          height: 500px;
                          ">

                        <div class="login-box">
                            <div class="login-logo">
                                <a href=""><b>NDB RMS</b> System</a>
                            </div><!-- /.login-logo -->
                            <div class="login-box-body">
                                <p class="login-box-msg">Sign in to start your session</p>
                                <p class="login-box-msg">Please Use Windows Credentials to Sign in</p>
                                <form action="/NDBRMS/LoginServlet" method="post" onsubmit="saveCredentials()">
                                    <div class="form-group has-feedback">
                                        <input type="text" class="form-control" name="username" placeholder="User Name" id="username">
                                        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <input type="password" class="form-control" name="password" placeholder="Password" id="password">
                                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-8">
                                            <input type="checkbox" id="rememberMe" name="rememberMe" checked="checked"> Remember Me
                                        </div><!-- /.col -->
                                        
                                        <div class="col-xs-4">
                                            <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                                        </div><!-- /.col -->
                                    </div>
                                    <div class="text-center">
                                        <b>Version</b> 2.1.0
                                    </div>
                                    <div class="text-center"><strong>Copyright &copy; 2016 <a href="http://www.ndbbank.com">NDB IT SS Department</a>.</br></strong> All rights reserved.
                                    </div>
                                </form>


                            </div><!-- /.login-box-body -->
                        </div><!-- /.login-box -->

                        <!-- jQuery 2.1.4 -->
                        <script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.1.4.min.js"></script>
                        <!-- Bootstrap 3.3.5 -->
                        <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
                        <!-- iCheck -->
                        <script src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
                        <script>
                          window.onload = function () {
                            var username = localStorage.getItem('username');
                            var password = localStorage.getItem('password');
                            if (username != undefined && username != null && username.length > 0) {                               
                                $('#username').val(username);
                            }
                            if (password != undefined && password != null && password.length > 0) {
                                $('#password').val(password);
                            }
                        }

                        function saveCredentials() {
                            var username = $('#username').val();
                            var password = $('#password').val();

                            if ($("#rememberMe").prop('checked')) {
                                localStorage.setItem('username', username);
                                localStorage.setItem('password', password);
                            }
                            else{
                                localStorage.setItem('username','');
                                localStorage.setItem('password', '');
                            }
                        }                               
                    
                        </script>
                    </body>
                </html>
