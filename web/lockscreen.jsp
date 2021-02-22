<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="DBOops.UserBean"%>
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
                        <title>NDBbank | Home</title>
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

                        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
                        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
                        <!--[if lt IE 9]>
                            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
                            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
                        
                        <![endif]-->
                        <link rel="shortcut icon" href="./dist/img/NDBBANK.png" alt="..." class="img-circle"/>
                    </head>
                    <body class="hold-transition lockscreen" style="height: 322px;opacity: 0.8;">
                        <!-- Automatic element centering -->
                        <div class="lockscreen-wrapper">

                            <div class="lockscreen-logo">
                                <a href="../../index.jsp"><b>NDB RMS</b> System</a>
                            </div>
                            <!-- User name -->
                            <div class="lockscreen-name"><%= request.getParameter("user_name")%></div>

                            <!-- START LOCK SCREEN ITEM -->
                            <div class="lockscreen-item">
                                <!-- lockscreen image -->
                                <div class="lockscreen-image">
                                    <img src="dist/img/man.png" alt="User Image">
                                </div>
                                <!-- /.lockscreen-image -->

                                <!-- lockscreen credentials (contains the form) -->
                                <form class="lockscreen-credentials" action="/NDBRMS/LoginServlet" method="post">
                                    <div class="input-group">
                                        <input type="hidden" class="form-control" name="username" value="<%= request.getParameter("userid")%>" placeholder="User Name">
                                        <input type="password" class="form-control" placeholder="password" name="password">
                                        <div class="input-group-btn">
                                            <button  type="submit" class="btn btn-primary btn-block btn-flat"><i class="fa fa-arrow-right text-muted"></i></button>
                                        </div>
                                    </div>
                                </form><!-- /.lockscreen credentials -->

                            </div><!-- /.lockscreen-item -->
                            <div class="help-block text-center">
                                Enter your password to retrieve your session
                            </div>
                            <div class="text-center">
                                <a href="index.jsp">Or sign in as a different user</a>
                            </div>

                            <div class="text-center">
                                <b>Version</b> 2.1.0
                            </div>
                            <div class="text-center"><strong>Copyright &copy; 2016 <a href="http://www.ndbbank.com">NDB IT SS Department</a>.</strong> All rights reserved.
                            </div>
                        </div><!-- /.center -->

                        <!-- jQuery 2.1.4 -->
                        <script src="../../plugins/jQuery/jQuery-2.1.4.min.js"></script>
                        <!-- Bootstrap 3.3.5 -->
                        <script src="../../bootstrap/js/bootstrap.min.js"></script>
                    </body>
                </html>
