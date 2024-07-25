<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Schools;

class SchoolsController extends Controller
{
    public function datasubmit(Request $request)
    {
        // Validate the input data
        $validateData=$request->validate([
            'sname' => 'required',
            'district' => 'required',
            'regnumber' => 'required',
            'email' => 'required|email',
            'rname' => 'required',
        ]);

        // Create a new Representative instance and save it to the database
        Schools::create([
            'sname' => $request->input('sname'),
            'district' => $request->input('district'),
            'regnumber' => $request->input('regnumber'),
            'email' => $request->input('email'),
            'rname' => $request->input('rname'),
        ]);

        // Redirect back or show a success message
        return redirect()->back()->with('success', ' added successfully!');
    }
}
