<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\DashboardController;
use App\Http\Controllers\LocationController;
use App\Http\Controllers\ViolationController;
use App\Http\Controllers\RatingController;
use App\Http\Controllers\ApplicationController;
use App\Http\Controllers\FirestoreController;


Route::get('/', function () {
    return view('home');
});

Route::get('/users', [UserController::class, 'index'])->name('users.index');
Route::get('/users/{id}', [UserController::class, 'show'])->name('users.show');

// Route để hiển thị form chỉnh sửa (tùy chọn nếu bạn muốn trang edit riêng)
Route::get('/users/{id}/edit', [UserController::class, 'edit'])->name('users.edit');

// Route để xử lý cập nhật thông tin người dùng
// Sử dụng phương thức PUT/PATCH cho cập nhật theo quy ước RESTful
Route::put('/users/{id}', [UserController::class, 'update'])->name('users.update');
Route::patch('/users/{id}', [UserController::class, 'update']); // PATCH cũng trỏ về update

// Route để khóa tài khoản
Route::post('/users/{id}/block', [UserController::class, 'block'])->name('users.block');

// Route để xóa người dùng
Route::delete('/users/{id}', [UserController::class, 'destroy'])->name('users.destroy');

Route::get('/statistics', [DashboardController::class, 'statistics'])->name('statistics');
Route::get('/locations', [LocationController::class, 'index'])->name('locations.index');
Route::get('/locations/create', [LocationController::class, 'create'])->name('locations.create'); // Dòng này là cần thiết!
Route::post('/locations', [LocationController::class, 'store'])->name('locations.store');
Route::get('/locations/{location}/edit', [LocationController::class, 'edit'])->name('locations.edit');
Route::put('/locations/{location}', [LocationController::class, 'update'])->name('locations.update');
Route::delete('/locations/{location}', [LocationController::class, 'destroy'])->name('locations.destroy');
Route::get('/quan-ly-vi-pham', [ViolationController::class, 'index'])->name('violations.index');
Route::get('/violation/{id}', [ViolationController::class, 'show'])->name('violations.show');
Route::delete('/violation/{id}', [ViolationController::class, 'destroy'])->name('violations.destroy');
Route::put('/violation/{id}', [ViolationController::class, 'update'])->name('violations.update');
Route::patch('/users/{user}/lock', [UserController::class, 'lock'])->name('users.lock');
Route::get('/users/{id}/edit', [UserController::class, 'edit'])->name('users.edit');
Route::get('/ratings', [RatingController::class, 'index'])->name('ratings.index');
Route::get('/admin/applications', [ApplicationController::class, 'index'])->name('applications.index');
Route::get('/admin/applications/{id}/edit', [ApplicationController::class, 'edit'])->name('applications.edit');
Route::post('/admin/applications/{id}/status', [ApplicationController::class, 'updateStatus'])->name('applications.update_status');

Route::get('/firestore/add-ride', [FirestoreController::class, 'addRide']);
Route::get('/firestore/rides', [FirestoreController::class, 'listRides']);







