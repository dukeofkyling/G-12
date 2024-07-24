<?php

namespace App\Imports;

use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;
class QuestionsImport implements ToModel, WithHeadingRow
{
    /**
    * @param array $row
    *
    * @return \Illuminate\Database\Eloquent\Model|null
    */
    public function model(array $row)
    {
        return new Question([

'question' => $row['question'], // Adjust the key according to your Excel column names
                 // Adjust the key according to your Excel column names
                 // Adjust the key according to your Excel column names



            //
        ]);
    }
}
