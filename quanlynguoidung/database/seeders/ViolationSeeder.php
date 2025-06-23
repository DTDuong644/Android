<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Violation;

class ViolationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        Violation::create([
        'code' => 'VP001',
        'violator_name' => 'Nguyễn Tiến Bình',
        'type' => 'Trễ chuyến',
        'violation_date' => '2025-03-15 08:30:00',
        'status' => 'Đã xử lý',
    ]);

     Violation::create([
        'code' => 'VP002 ',
        'violator_name' => 'Phạm Vũ Ngọc Trọng',
        'type' => 'Gây mất an toàn ',
        'violation_date' => '2025-03-15 08:30:00',
        'status' => 'Chưa xử lý',
    ]);
    Violation::create([
        'code' => 'VP003 ',
        'violator_name' => 'Tô Đức Thành',
        'type' => 'Gây mất an toàn ',
        'violation_date' => '2025-03-25 08:30:00',
        'status' => 'Chưa xử lý',
    ]);
    Violation::create([
        'code' => 'VP004 ',
        'violator_name' => 'Phạm Vũ Ngọc Bình',
        'type' => 'Hủy chuyến không lý do ',
        'violation_date' => '2025-03-24 08:20:00',
        'status' => 'Đã xử lý',
    ]);
    Violation::create([
        'code' => 'VP005 ',
        'violator_name' => 'Nguyễn Thành An',
        'type' => 'Vượt đèn đỏ ',
        'violation_date' => '2025-03-15 15:15:00',
        'status' => 'Chưa xử lý',
    ]);
    Violation::create([
        'code' => 'VP006 ',
        'violator_name' => 'Nguyễn Quốc Trọng',
        'type' => 'Hủy chuyến không lý do ',
        'violation_date' => '2025-04-26 19:15:00',
        'status' => 'Chưa xử lý',
    ]);
    }
    
}
