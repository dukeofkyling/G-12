<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Schools extends Model
{
    use HasFactory;

    protected $fillable = ['sname', 'district', 'regnumber','email','rname'];
    public $timestamps = false;
}
