
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="{{ asset('css/participants.css') }}">
         <meta charset="utf-8" />
         <link rel="apple-touch-icon" sizes="76x76" href="{{ asset('light-bootstrap/img/apple-icon.png') }}">
         <link rel="icon" type="image/png" href="{{ asset('light-bootstrap/img/favicon.ico') }}">
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
         <title>User management</title>
         <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
         <!--     Fonts and icons     -->
         <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />
         <!-- CSS Files -->
         <link href="{{ asset('light-bootstrap/css/bootstrap.min.css') }}" rel="stylesheet" />
         <link href="{{ asset('light-bootstrap/css/light-bootstrap-dashboard.css?v=2.0.0') }} " rel="stylesheet" />
         <!-- CSS Just for demo purpose, don't include it in your project -->
         <link href="{{ asset('light-bootstrap/css/demo.css') }}" rel="stylesheet" />
 
         <!-- Canonical SEO -->
         <link rel="canonical" href="https://www.creative-tim.com/product/light-bootstrap-dashboard-laravel" />        <!--  Social tags      -->
         <meta name="keywords" content="design system, dashboard, bootstrap 4 dashboard, bootstrap 4 design, bootstrap 4 system, bootstrap 4, bootstrap 4 uit kit, bootstrap 4 kit, light bootstrap, light bootstrap dashboard, creative tim,updivision, html dashboard, html css template, web template, bootstrap, bootstrap 4, css3 template, frontend, responsive bootstrap template, bootstrap dashboard, responsive dashboard, laravel, laravel php, laravel php framework, free laravel admin template, free laravel admin, free laravel admin template + Front End + CRUD, crud laravel php, crud laravel, laravel backend admin dashboard">
         <meta name="description" content="Start your development with a Bootstrap 4 Admin Dashboard built for Laravel Framework 5.5 and Up.">
 
 
         <!-- Schema.org markup for Google+ -->
         <meta itemprop="name" content="Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION">
         <meta itemprop="description" content="Start your development with a Bootstrap 4 Admin Dashboard built for Laravel Framework 5.5 and Up.">
 
         <meta itemprop="image" content="https://s3.amazonaws.com/creativetim_bucket/products/213/opt_lbd_laravel_thumbnail.jpg">
 
         <!-- Twitter Card data -->
         <meta name="twitter:card" content="product">
         <meta name="twitter:site" content="@creativetim">
         <meta name="twitter:title" content="Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION">
 
         <meta name="twitter:description" content="Start your development with a Bootstrap 4 Admin Dashboard built for Laravel Framework 5.5 and Up.">
         <meta name="twitter:creator" content="@creativetim">
         <meta name="twitter:image" content="https://s3.amazonaws.com/creativetim_bucket/products/213/opt_lbd_laravel_thumbnail.jpg">
 
         <!-- Open Graph data -->
         <meta property="fb:app_id" content="655968634437471">
         <meta property="og:title" content="Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION" />
         <meta property="og:type" content="article" />
         <meta property="og:url" content="https://www.creative-tim.com/live/light-bootstrap-dashboard-laravel" />
         <meta property="og:image" content="https://s3.amazonaws.com/creativetim_bucket/products/213/opt_lbd_laravel_thumbnail.jpg"/>
         <meta property="og:description" content="Start your development with a Bootstrap 4 Admin Dashboard built for Laravel Framework 5.5 and Up." />
         <meta property="og:site_name" content="Creative Tim & UPDIVISION" />
           <!-- Google Tag Manager -->
         <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
         new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
         j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
         'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
         })(window,document,'script','dataLayer','GTM-NKDMSK6');</script>
         <!-- End Google Tag Manager -->
     
         <script>
             // Facebook Pixel Code Don't Delete
               ! function(f, b, e, v, n, t, s) {
                 if (f.fbq) return;
                 n = f.fbq = function() {
                   n.callMethod ?
                     n.callMethod.apply(n, arguments) : n.queue.push(arguments)
                 };
                 if (!f._fbq) f._fbq = n;
                 n.push = n;
                 n.loaded = !0;
                 n.version = '2.0';
                 n.queue = [];
                 t = b.createElement(e);
                 t.async = !0;
                 t.src = v;
                 s = b.getElementsByTagName(e)[0];
                 s.parentNode.insertBefore(t, s)
               }(window,
                 document, 'script', '//connect.facebook.net/en_US/fbevents.js');
               try {
                 fbq('init', '111649226022273');
                 fbq('track', "PageView");
               } catch (err) {
                 console.log('Facebook Track Error:', err);
               }
         </script>
    
    </head>
<body>
<div class="wrapper ">

<div class="sidebar" data-image="{{ asset('light-bootstrap/img/sidebar-5.jpg') }}">
<div class="sidebar-wrapper">
                
                <ul class="nav">
                    <li class="nav-item ">
                        <a class="nav-link" href="{{route('dashboard')}}">
                            <i class="nc-icon nc-chart-pie-35"></i>
                            <p>{{ __("Dashboard") }}</p>
                        </a>
                    </li>
                   
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="collapse" href="#laravelExamples" aria-expanded="true">
                            <i>
                                <img src="{{ asset('light-bootstrap/img/laravel.svg') }}" style="width:25px">
                            </i>
                            <p>
                                {{ __('Dashboard') }}
                                <b class="caret"></b>
                            </p>
                        </a>
                        <div class="collapse  show " id="laravelExamples">
                            <ul class="nav">
                                <li class="nav-item ">
                                    <a class="nav-link" href="{{route('profile.edit')}}">
                                        <i class="nc-icon nc-single-02"></i>
                                        <p>{{ __("Questions") }}</p>
                                    </a>
                                </li>
                                <li class="nav-item  active">
                                    <a class="nav-link" href="{{route('user.index')}}">
                                        <i class="nc-icon nc-circle-09"></i>
                                        <p>{{ __("Answers") }}</p>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
        
                    <li class="nav-item ">
                        <a class="nav-link" href="{{route('page.index', 'schools')}}">
                            <i class="nc-icon nc-notes"></i>
                            <p>{{ __("Upload Schools") }}</p>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="{{route('page.index', 'representatives')}}">
                            <i class="nc-icon nc-paper-2"></i>
                            <p>{{ __("Add Representatives") }}</p>
                        </a>
                    </li>
                    
                </ul>
            </div>
              </div>

<section class="container">
<form action="{{ route('schools.datasubmit') }}" method="POST">
<h2>Upload School Information</h2>
@csrf 
  <label for="sname">School Name:</label><br>
  <input type="text" id="sname" name="sname"><br>
  <label for="district">District:</label><br>
  <input type="text" id="district" name="district" ><br>
  <label for="regnumber">Registration Number:</label><br>
  <input type="text" id="regnumber" name="regnumber" ><br>
  <label for="email">Email:</label><br>
  <input type="text" id="email" name="email"><br>
  <label for="rname">Representative Name:</label><br>
  <input type="text" id="rname" name="rname"><br><br>
  <input type="submit" value="Submit">
</form> 

</section>

</body>
</html>

