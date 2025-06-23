<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\User; 
use Illuminate\Validation\Rule;

class UserController extends Controller
{
    /**
     * Display a listing of the users.
     */
    public function index(Request $request)
    {
        $query = User::query();

    // Tìm theo từ khóa
    if ($request->filled('q')) {
        $q = $request->input('q');
        $query->where(function ($subQuery) use ($q) {
            $subQuery->where('name', 'like', "%$q%")
                     ->orWhere('email', 'like', "%$q%")
                     ->orWhere('phone_number', 'like', "%$q%");
        });
    }

    // Lọc theo trạng thái
    if ($request->filled('status')) {
        $query->where('status', $request->input('status'));
    }

    $users = $query->orderBy('created_at', 'desc')->paginate(10);

    return view('users.index', compact('users'));
    }

    /**
     * Display the specified user.
     */
    public function show($id)
    {
        $user = User::findOrFail($id);
        return view('users.show', compact('user'));
    }

    /**
     * Show the form for editing the specified user.
     */
    public function edit($id)
    {
        $user = User::findOrFail($id);
    return view('users.edit', compact('user'));
    }

    /**
     * Update the specified user in storage.
     */
    public function update(Request $request, $id)
    {
       // 1. Validate dữ liệu
    $validated = $request->validate([
        'name' => 'required|string|max:255',
        'email' => 'required|email|max:255',
        'phone_number' => 'nullable|string|max:20',
        'date_of_birth' => 'nullable|date',
        'gender' => 'nullable|in:Nam,Nữ,Khác',
        'role' => 'nullable|string|max:50',
        'status' => 'required|in:active,locked',
    ]);

    // 2. Tìm user và cập nhật
    $user = User::findOrFail($id);
    $user->update($validated);

    // 3. Quay về trang chi tiết với thông báo thành công
    return redirect()->route('users.index')  // hoặc 'users.show', tùy bạn
        ->with('success', 'Cập nhật thành công!');
    }

    // Phương thức để xử lý khóa tài khoản (nút "Khóa tài khoản")
    public function block($id)
    {
        $user = User::findOrFail($id);
        $user->status = 'blocked'; // Đặt trạng thái là 'blocked'
        $user->save();
        return redirect()->route('users.show', $user->id)->with('success', 'Tài khoản đã được khóa.');
    }

    
    public function destroy($id)
    {
        $user = User::findOrFail($id);
        $user->delete(); 
        return redirect()->route('users.index')->with('success', 'Người dùng đã được xóa thành công.');
    }
}