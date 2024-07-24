@extends('welcome', ['activePage' => 'typography', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Typography', 'activeButton' => 'laravel'])

@section('content')
<div class="full-page section-image" data-image="{{ asset('light-bootstrap/img/bg5.jpg') }}">
    <div class="content">
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-10">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h4 class="card-title"><b>The Details Of The Representatives</b></h4>
                        </div>
                        <div class="card-body">
                            @if (session('success'))
                                <div class="alert alert-success">
                                    {{ session('success') }}
                                </div>
                            @endif
                            <form method="post" action="{{ route('representatives.store') }}">
                                @csrf <!-- Add the CSRF token -->
                                <div class="form-group">
                                    <label for="repName" class="text-dark">Representative Name</label>
                                    <input type="text" name="repName" class="form-control" id="repName" placeholder="Representative Name" required>
                                </div>
                                <div class="form-group">
                                    <label for="repEmail" class="text-dark">Representative Email</label>
                                    <input type="email" name="repEmail" class="form-control" id="repEmail" placeholder="Representative Email" required>
                                </div>
                                <div class="form-group">
                                    <label for="schoolRegNo" class="text-dark">School Registration Number</label>
                                    <input type="text" name="schoolRegNo" class="form-control" id="schoolRegNo" placeholder="School Registration Number" required>
                                </div>
                                <!-- Other form fields if needed -->
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>        
@endsection

<style>
    .full-page {
        background-color: #f8f9fa; /* Light gray background */
    }

    .card {
        border: none; /* Remove card border */
        border-radius: 8px; /* Rounded corners */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Light shadow */
    }

    .card-header {
        border-bottom: 2px solid #007bff; /* Blue bottom border */
        background-color: #007bff; /* Blue background */
        color: white; /* White text */
    }

    .card-body {
        background-color: white; /* White background */
        padding: 20px;
        color: #333; /* Dark grey text */
    }

    .form-group label {
        color: #333; /* Dark grey label text */
    }

    .btn-primary {
        background-color: #007bff; /* Blue button */
        border-color: #007bff; /* Blue border */
    }

    .btn-primary:hover {
        background-color: #0056b3; /* Darker blue on hover */
        border-color: #0056b3; /* Darker blue border on hover */
    }

    .alert-success {
        color: #155724;
        background-color: #d4edda;
        border-color: #c3e6cb;
    }
</style>
