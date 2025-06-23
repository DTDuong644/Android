<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Application;

class ApplicationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {

        Application::insert([
        [
            'code' => 'HS001',
            'user_name' => 'Nguyễn Văn Linh',
            'submitted_at' => '2025-05-29',
            'status' => 'Chờ duyệt',
            'type' => 'Tài xế',
            'created_at' => now(),
            'updated_at' => now(),
        ],
        [
            'code' => 'HS002',
            'user_name' => 'Nguyễn Thị An',
            'submitted_at' => '2025-05-29',
            'status' => 'Chờ duyệt',
            'type' => 'Khách hàng',
            'created_at' => now(),
            'updated_at' => now(),
        ],
        [
            'code' => 'HS003',
            'user_name' => 'Nguyễn Văn Lâm',
            'submitted_at' => '2025-05-24',
            'status' => 'Đã duyệt',
            'type' => 'Tài xế',
            'created_at' => now(),
            'updated_at' => now(),
        ],[
            'code' => 'HS004',
            'user_name' => 'Phùng Thị Lan',
            'submitted_at' => '2025-05-15',
            'status' => 'Chờ duyệt',
            'type' => 'Khách hàng',
            'created_at' => now(),
            'updated_at' => now(),
        ],[
            'code' => 'HS005',
            'user_name' => 'Đỗ Tùng Dương',
            'submitted_at' => '2025-05-20',
            'status' => 'Chờ duyệt',
            'type' => 'Tài xế',
            'created_at' => now(),
            'updated_at' => now(),
        ],[
            'code' => 'HS006',
            'user_name' => 'Nguyễn Thị Minh',
            'submitted_at' => '2025-05-30',
            'status' => 'Đã duyệt',
            'type' => 'Khách hàng',
            'created_at' => now(),
            'updated_at' => now(),
        ],[
            'code' => 'HS007',
            'user_name' => 'Nguyễn Văn Thanh',
            'submitted_at' => '2025-05-11',
            'status' => 'Chờ duyệt',
            'type' => 'Khách hàng',
            'created_at' => now(),
            'updated_at' => now(),
        ],
       
    ]);
    }
}
