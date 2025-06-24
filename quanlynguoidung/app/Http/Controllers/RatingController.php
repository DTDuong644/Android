<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Rating;

class RatingController extends Controller
{
    public function index(Request $request)
    {
        $query = Rating::query();

        // Lọc theo từ khóa tìm kiếm nếu có
        if ($request->filled('search')) {
            $search = $request->input('search');
            $query->where('id', 'like', "%$search%")
                  ->orWhere('passenger_name', 'like', "%$search%")
                  ->orWhere('driver_name', 'like', "%$search%")
                  ->orWhere('comment', 'like', "%$search%");
        }

        $ratings = $query->latest()->get();

        return view('ratings.index', compact('ratings'));
    }
}
