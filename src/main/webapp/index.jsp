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

    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">JEE7 Sandbox</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Link 1</a></li>
            <li><a href="#">Link 2</a></li>
            <li><a href="#">Link 3</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Link 4</a></li>
            <li><a href="#">Link 5</a></li>
            <li><a href="#">Link 6</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <div class="container">
      <div class="jumbotron">
        <h1>SANDBOX</h1>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing
          elit. Fusce pulvinar libero id nibh tincidunt, ut adipiscing
          diam sagittis. Aliquam accumsan id purus eget
          malesuada. Suspendisse potenti. Cras iaculis mollis
          augue. Phasellus sapien turpis, consectetur viverra rutrum
          vel, eleifend at nunc. Vivamus mattis et urna at
          bibendum. Etiam eleifend, nunc a pulvinar mattis, nisi dui
          scelerisque eros, nec porttitor elit sapien quis
          massa.
        </p>
        <p>
          Aenean laoreet scelerisque urna tincidunt tristique. Mauris
          aliquet erat a lobortis bibendum. Morbi vulputate viverra
          felis, id porttitor nulla. Nam at orci tempus, placerat
          felis ut, rhoncus est. Nunc sed sem in tellus scelerisque
          posuere. Aenean vestibulum, dui vel tristique viverra, dolor
          lectus auctor tortor, a dapibus augue neque eget
          enim. Nullam viverra, mi sit amet blandit semper, urna velit
          pellentesque velit, id dictum nunc nulla quis ante. Mauris
          eget tortor pulvinar, dignissim nulla a, elementum
          purus. Vestibulum sed dapibus erat.
        </p>
        <p>
          <a class="btn btn-lg btn-primary" href="#" role="button">Sample button</a>
        </p>
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
