<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JEE7 Sandbox</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/common.css">
  </head>
  <body>
    <!--[if lt IE 8]>
    <p class="browsehappy">
      You are using an <strong>outdated</strong>
      browser. Please <a href="http://browsehappy.com/">upgrade your
      browser</a> to improve your experience.
    </p>
    <![endif]-->

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="centering text-center">
          <div id="content" class="col-md-offset-4 col-md-4">
            <div class="form-group">
              <input type="text" class="form-control" id="username" placeholder="Username">
            </div>
            <div class="form-group">
              <input type="password" class="form-control" id="password" placeholder="Password">
            </div>
            <button type="submit" class="btn btn-default btn-block">Login</button>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/common.js"></script>

    <!-- TODO: Add the actual Google Analytics Tracking Code -->
    <script>
      (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
      function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
      e=o.createElement(i);r=o.getElementsByTagName(i)[0];
      e.src='//www.google-analytics.com/analytics.js';
      r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
      ga('create','UA-XXXXX-X');ga('send','pageview');
    </script>
  </body>
</html>
