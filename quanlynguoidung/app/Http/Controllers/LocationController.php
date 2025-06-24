<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class LocationController extends Controller
{
   
   public function index(Request $request)
    {
        
        $allLocations = [
            (object)['id' => 1, 'name' => 'Trường đại học Thủy Lợi', 'so_chuyen_di' => 40, 'so_chuyen_den' => 35, 'tai_xe_dang_hoat_dong' => 15],
            (object)['id' => 2, 'name' => 'Thị xã Sơn Tây', 'so_chuyen_di' => 20, 'so_chuyen_den' => 15, 'tai_xe_dang_hoat_dong' => 15],
            (object)['id' => 3, 'name' => 'Thành phố Thái Nguyên', 'so_chuyen_di' => 15, 'so_chuyen_den' => 20, 'tai_xe_dang_hoat_dong' => 7],
            (object)['id' => 4, 'name' => 'Huyện Thường Tín', 'so_chuyen_di' => 25, 'so_chuyen_den' => 16, 'tai_xe_dang_hoat_dong' => 10],
            (object)['id' => 5, 'name' => 'Thành phố Phú Thọ', 'so_chuyen_di' => 18, 'so_chuyen_den' => 12, 'tai_xe_dang_hoat_dong' => 8],
            (object)['id' => 6, 'name' => 'Quận Hai Bà Trưng', 'so_chuyen_di' => 30, 'so_chuyen_den' => 25, 'tai_xe_dang_hoat_dong' => 12],
            (object)['id' => 7, 'name' => 'Huyện Ba Vì', 'so_chuyen_di' => 10, 'so_chuyen_den' => 8, 'tai_xe_dang_hoat_dong' => 5],
        ];

        $searchTerm = $request->query('search'); // Lấy từ khóa tìm kiếm từ query string

        $locations = collect($allLocations)->filter(function ($location) use ($searchTerm) {
            if ($searchTerm) {
                return str_contains(mb_strtolower($location->name, 'UTF-8'), mb_strtolower($searchTerm, 'UTF-8'));
            }
            return true;
        })->values()->all(); // Lọc và reset keys

        return view('locations.index', compact('locations', 'searchTerm'));
    }

    // Bạn có thể thêm các phương thức khác ở đây cho Thêm, Sửa, Xóa
    // Ví dụ:
    public function create()
    {
        // Hiển thị form thêm địa điểm mới
        return view('locations.create');
    }

    public function store(Request $request)
    {
        // Xử lý lưu địa điểm mới vào CSDL
        // return redirect()->route('locations.index')->with('success', 'Thêm địa điểm thành công!');
    }

    public function edit($id)
    {
        // Lấy thông tin địa điểm theo ID và hiển thị form chỉnh sửa
        // $location = Location::findOrFail($id);
        // return view('locations.edit', compact('location'));
    }

    public function update(Request $request, $id)
    {
        // Xử lý cập nhật thông tin địa điểm
        // return redirect()->route('locations.index')->with('success', 'Cập nhật địa điểm thành công!');
    }

    public function destroy($id)
    {
        // Xử lý xóa địa điểm
        // return redirect()->route('locations.index')->with('success', 'Xóa địa điểm thành công!');
    }
}
