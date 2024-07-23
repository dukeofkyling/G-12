<?php

namespace App\Imports;

use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;
class QuestionsImport implements ToModel,  WithHeadingRow
{
    
    public function model(array $row)
    {

        
        return new Question([
            'question' => $row['question'] ?? null,
            'answer' => $row['answer'] ?? null,
            'marks' => $row['marks'] ?? '0', 


        ]);
    }
}
