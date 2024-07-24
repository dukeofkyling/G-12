<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsImport;

class QuestionController extends Controller
{
    public function showImportForm()
    {
        return view('questions.import');
    }

    public function import(Request $request)
    {
        $request->validate([
            'questions_file' => 'required|file|mimes:xlsx,xls,csv',
        ]);

        Excel::import(new QuestionsImport, $request->file('questions_file'));

        return redirect()->back()->with('success', 'Questions imported successfully.');
    }
}

