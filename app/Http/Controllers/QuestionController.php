<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Imports\QuestionsImport;
use Maatwebsite\Excel\Facades\Excel;

class QuestionController extends Controller
{
    public function showImportForm(){

return view('questions.import');

    }
    public function import(Request $request)
    {
        $request->validate([
            'questions_file' => 'required|file|mimes:xlsx,xls,csv',
        ]);

        try {
            Excel::import(new QuestionsImport, $request->file('questions_file'));
            return redirect()->back()->with('success', 'Questions imported successfully.');
        } catch (\Exception $e) {
            return redirect()->back()->with('error', 'Error importing questions: ' . $e->getMessage());
        }
    }
}   