<!-- resources/views/questions/import.blade.php -->

<!DOCTYPE html>
<html>
<head>
    <title>Import Questions</title>
</head>
<body>
    <h1>Import Questions</h1>
    @if (session('success'))
        <div>
            {{ session('success') }}
        </div>
    @endif
    <form action="{{ route('questions.import.post') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <label for="questions_file">Select questions file:</label>
        <input type="file" name="questions_file" id="questions_file" required>
        <button type="submit">Import</button>
    </form>
</body>
</html>
