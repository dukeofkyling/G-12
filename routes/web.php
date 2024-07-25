<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\RepresentativeController;
use App\Http\Controllers\AnswerController;
use App\Http\Controllers\QuestionController;
use App\Http\Controllers\SchoolsController;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/admin/import-questions', 'Admin\ImportController@showImportForm')->name('admin.import.questions.form');
Route::post('/admin/import-questions', 'Admin\ImportController@importQuestions')->name('admin.import.questions');



Route::get('questions/import', [QuestionController::class, 'showImportForm'])->name('questions.import');
Route::post('questions/import', [QuestionController::class, 'import'])->name('questions.import.post');


Route::get('/admin/answers/import', [AnswerController::class, 'showImportForm'])->name('answers.import');
Route::post('/admin/answers/import', [AnswerController::class, 'import'])->name('answers.import.post');




Route::get('answers/import', [AnswerController::class, 'showImportForm'])->name('answers.import.form');
Route::post('answers/import', [AnswerController::class, 'import'])->name('answers.import.post');




Route::get('/', function () {
    return view('welcome');
});

Route::get('/participants', function () {
    return view('participants');
});

Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Auth::routes();

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('dashboard');

Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::patch('profile', ['as' => 'profile.update', 'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::patch('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});

Route::group(['middleware' => 'auth'], function () {
	Route::get('{page}', ['as' => 'page.index', 'uses' => 'App\Http\Controllers\PageController@index']);
});

Route::post('/representatives', [RepresentativeController::class, 'store'])->name('representatives.store');

Route::get('/schools', function () {
    return view('pages.schools'); // Make sure 'schools' matches your blade file name correctly
});

Route::post('/datasubmit', [SchoolsController::class, 'datasubmit'])->name('schools.datasubmit');


