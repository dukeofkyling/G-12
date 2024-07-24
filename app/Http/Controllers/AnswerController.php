<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Imports\AnswersImport;
use Maatwebsite\Excel\Facades\Excel;

class AnswerController extends Controller
{
    public function showImportForm()
    {
        return view('answers.import');
    }

    public function import(Request $request)
    {
        $request->validate([
            'answers_file' => 'required|file|mimes:xlsx,xls,csv',
        ]);

        Excel::import(new AnswersImport, $request->file('answers_file'));

        return redirect()->back()->with('success', 'Answers imported successfully.');
    }
}
