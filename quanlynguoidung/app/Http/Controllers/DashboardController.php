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

        // === 3. Tính toán các số liệu cho "Thống kê người dùng" ===
        $newUsersThisMonth = 101; // Số lượng người đăng ký mới trong tháng
        $activeUsersRegularly = 54; // Số người dùng hoạt động thường xuyên
        $feedbackCount = 40; // Số lượng phản hồi sau mỗi chuyến đi


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