@extends('welcome', ['activePage' => 'typography', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Typography', 'activeButton' => 'laravel'])

@section('content')
<div class="full-page section-image" data-image="{{asset('light-bootstrap/img/bg5.jpg')}}">
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title" style="border:none;"><b>The Details Of The Representaives<b></h4>
                            
                        </div>
                    </div>   
                    <form method="post" action="{{ route('representatives.store') }}">
    @csrf <!-- Add the CSRF token -->
    <input type="text" name="repName" placeholder="Representative Name">
    <input type="email" name="repEmail" placeholder="Representative Email">
    <input type="text" name="schoolRegNo" placeholder="School Registration Number">
    <!-- Other form fields if needed -->
    <button type="submit">Submit</button>
</form>
                </div>
            </div>
        </div>
    </div>
</div>        
@endsection