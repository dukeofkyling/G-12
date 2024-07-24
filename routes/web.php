<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolsController;
use App\Http\Controllers\RepresentativeController;


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
// Route::post('/schools', [SchoolsController::class, 'datasubmit'])->name('schools.datasubmit');
// // Route::get('/schools',[SchoolsController::class, 'index']);
// // Route::view('uploadschools','Schools');
// Route::POST('datasubmit','SchoolsController@datasubmit');
// Route::post('/datasubmit',function(){
// 	return view('pages.Schools');
// });
// Route::post('/schools',function(){
// 	$Schools = new Schools();
// 	$Schools->sname = request('sname');
// 	$Schools->district = request('district');
// 	$Schools->regnumber = request('regnumber');
// 	$Schools->email = request('email');
// 	$Schools->rname = request('rname');
// 	$Schools->save();
// });
Route::get('/schools', function () {
    return view('pages.Schools'); // This route should return your form view
});

Route::post('/datasubmit', [SchoolsController::class, 'datasubmit'])->name('schools.datasubmit');
