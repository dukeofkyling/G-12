<div class="sidebar" data-image="{{ asset('light-bootstrap/img/sidebar-5.jpg') }}">
    <!--
Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

Tip 2: you can also add an image using data-image tag
-->
    <div class="sidebar-wrapper" style="background: transparent;,color:none">
       
        <ul class="nav" >
            <li class="nav-item ">
                        <a class="nav-link" href="{{route('dashboard')}}">
                            <i class="nc-icon nc-chart-pie-35"></i>
                            <p>{{ __("Dashboard") }}</p>
                        </a>
                    </li>
           
            <!-- resources/views/admin/dashboard.blade.php -->
<ul class="nav flex-column">
    <!-- other menu items -->
    <li class="nav-item">
        <a class="nav-link d-flex align-items-center" href="{{ route('answers.import') }}">
            <i class="fas fa-file-import me-2"></i> Import Answers
        </a>
    </li>
</ul>
          <!-- resources/views/admin/dashboard.blade.php -->
          <ul class="nav flex-column">
    <!-- other menu items -->
    <li class="nav-item">
        <a class="nav-link d-flex align-items-center" href="{{ route('questions.import') }}">
            <i class="fas fa-file-import me-2"></i> Import Questions
        </a>
    </li>
</ul>







<!-- Include Bootstrap JS for proper rendering of icons -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>







            <li class="nav-item @if($activePage == 'table') active @endif">
                <a class="nav-link" href="{{route('page.index', 'schools')}}">
                    <i class="nc-icon nc-notes"></i>
                    <p>{{ __("Upload Schools") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'representatives') active @endif">
                <a class="nav-link" href="{{route('page.index', 'representatives')}}">
                    <i class="nc-icon nc-paper-2"></i>
                    <p>{{ __("Add Representatives") }}</p>
                </a>
            </li>
           
        </ul>
    </div>
</div>
