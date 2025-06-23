<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class RatingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('ratings')->insert([
            [
                'code' => 'DG001',
                'passenger_name' => 'Nguyễn Thị Linh',
                'driver_name' => 'Nguyễn Tiến Bình',
                'stars' => 4,
                'comment' => 'Tài xế thân thiện',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'code' => 'DG002',
                'passenger_name' => 'Nguyễn Khánh Hưng',
                'driver_name' => 'Tô Đức Thành',
                'stars' => 5,
                'comment' => 'Tài xế chở an toàn',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'code' => 'DG003',
                'passenger_name' => 'Nguyễn Khánh Linh',
                'driver_name' => 'Nguyễn Tiến Bình',
                'stars' => 1,
                'comment' => 'Tài xế vượt đèn đỏ',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'code' => 'DG004',
                'passenger_name' => 'Phạm Thanh Dương',
                'driver_name' => 'Đỗ Tùng Dương',
                'stars' => 4,
                'comment' => 'Tài xế đến muộn',
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ]);
    }
}
