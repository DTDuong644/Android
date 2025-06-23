<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

class UserSeeder extends Seeder
{
    public function run(): void
    {
        $users = [
            [
                'name' => 'Nguyễn Tiến Bình',
                'email' => 'tienbinh@gmail.com',
                'phone_number' => '0351892719',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789012',
                'status' => 'active',
            ],
            [
                'name' => 'Phạm Vũ Ngọc Trọng',
                'email' => 'ngoctrong@gmail.com',
                'phone_number' => '0351892720',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789034',
                'status' => 'active',
            ],
            [
                'name' => 'Tô Đức Thành',
                'email' => 'thanhto1409@gmail.com',
                'phone_number' => '0351892730',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789022',
                'status' => 'locked',
            ],
            [
                'name' => 'Đỗ Tùng Dương',
                'email' => 'tungduong222@gmail.com',
                'phone_number' => '0352672719',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789490',
                'status' => 'locked',
            ],
            [
                'name' => 'Nguyễn Phú An',
                'email' => 'anpb22@gmail.com',
                'phone_number' => '0351922719',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123422789012',
                'status' => 'locked',
            ],
            [
                'name' => 'Phạm Quốc Trọng',
                'email' => 'trongquoc99@gmail.com',
                'phone_number' => '0351112719',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '1234567892122',
                'status' => 'locked',
            ],
            [
                'name' => 'Nguyễn Năng Anh',
                'email' => 'nanganh43@gmail.com',
                'phone_number' => '0351892219',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789033',
                'status' => 'active',
            ],
            [
                'name' => 'Nguyễn Thành Nam',
                'email' => 'nampb11@gmail.com',
                'phone_number' => '0351892423',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789441',
                'status' => 'locked',
            ],
            [
                'name' => 'Phạm Bình Minh',
                'email' => 'binhminh11@gmail.com',
                'phone_number' => '0351892334',
                'role' => 'tài xế',
                'gender' => 'Nam',
                'cccd' => '123456789342',
                'status' => 'active',
            ],
        ];

        foreach ($users as $user) {
            User::updateOrCreate(
                ['email' => $user['email']],
                array_merge($user, [
                    'email_verified_at' => now(),
                    'password' => Hash::make('12345678'),
                    'remember_token' => Str::random(10),
                    'created_at' => now(),
                    'updated_at' => now(),
                ])
            );
        }
    }
}
