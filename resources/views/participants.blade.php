<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Comaptible" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mathematic challenge</title>
        <link rel="stylesheet" type="text/css" href="{{ asset('css/participantsview.css') }}">
        <style>
          
                body {
                    background-image: url('{{('images/mathematics 3.jpg')}}');
                    background-size: cover;
                    height:100vh;
                    width: 100vw;
                    display: flex;
                    justify-content: center;
                    background-repeat: no-repeat;
                    position: fixed;
                }
            
                
        </style>
    </head>
    <body>
        <main class="main">
            <header class="header">
                <a href="#" class="logo">MATH 2+3=5</a>

                <nav class="navbar">
                    <a href="#" class="active">Home</a>
                    <a href="#">About</a>
                    <a href="#">Contact</a>
                </nav>
            </header>
            <div class="container">
                <section class="quiz-section"></section>
            <section class="home">
                <div class="home-content">
                    <h1>inter-school math challenge</h1>
                    <p style="font-size:21px;">Mathematics challenge, welcome to the this challenge. make sure you attempt the questions
                        they all carry equal marks and marks are to be deducted incase you skip a qestion.
                         Competitions help identify students with higher abilities in mathematics. <br><b>sucess in your attempts<b><br></p>
                    <button class="start-btn">Atempt challenge </button>
                </div>
            </section>
            </div>
        </main> 

        <div class="popup-info">
            <h2>Quiz Guide</h2>
            <span class="info">1.Lorem ipsum dolor sit anet consectetur.</span>
            <span class="info">2.Lorem ipsum dolor sit anet consectetur adipisicing.</span>
            <span class="info">3.Lorem ipsum dolor sit anet cons.</span>
            <span class="info">4.Lorem ipsum dolor sit anet consectetur adipisicing.</span>
            <span class="info">5.Lorem ipsum dolor sit anet consectetur adipisicing.</span>

            <div class="btn-group">
                <button class="info-btn exit-btn">Exit Quiz</button>
                <a href="#" class="info-btn continue-btn">Continue</a>
            </div>
        </div>
        <script src="{{ asset('js/homepage.js') }}"></script>
    </body>
</html>