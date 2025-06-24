<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\User; 
// use App\Models\Trip; 
// use App\Models\Rating; 

class DashboardController extends Controller
{
    public function statistics()
    {
        
        $totalUsers = User::count(); 

        
        $totalTripsInMonth = 300; 
        $totalRevenueInMonth = 300000000; 
        $tripCompletionRatio = '20/2'; 
        $activeVehicles = 200; 

       
        $avgTripDuration = '45 phút'; 
        $avgTripDistance = '10km'; 
        $avgRating = 4.5; 

        
        $newUsersThisMonth = 101; 
        $activeUsersRegularly = 54; 
        $feedbackCount = 40; 


        // Truyền tất cả dữ liệu sang view
        return view('statistics.index', compact(
            'totalUsers',
            'totalTripsInMonth',
            'totalRevenueInMonth',
            'tripCompletionRatio',
            'activeVehicles',
            'avgTripDuration',
            'avgTripDistance',
            'avgRating',
            'newUsersThisMonth',
            'activeUsersRegularly',
            'feedbackCount'
        ));
    }
}