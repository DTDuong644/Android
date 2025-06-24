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

    // 🔄 3. Đồng bộ dữ liệu lên Firestore nếu có firebase_uid
    if ($user->firebase_uid) {
        $firestore = app('firebase.firestore');
        $db = $firestore->database();

        $db->collection('users')->document($user->firebase_uid)->set([
            'displayName' => $user->name,
            'email' => $user->email,
            'phone' => $user->phone_number,
            'dateOfBirth' => $user->date_of_birth,
            'gender' => $user->gender,
            'role' => $user->role,
            'status' => $user->status,
            'updatedAt' => now()->toDateTimeString(),
        ], ['merge' => true]);
    }

    // 4. Quay về trang danh sách với thông báo thành công
    return redirect()->route('users.index')
        ->with('success', 'Cập nhật thành công!');
}

    
    public function destroy($id)
    {
       $user = User::findOrFail($id);

    // ✅ Xoá document Firestore nếu có UID
    if ($user->firebase_uid) {
        $firestore = app('firebase.firestore');
        $db = $firestore->database();
        $db->collection('users')->document($user->firebase_uid)->delete();
    }

    // ❌ Xoá trên SQL
    $user->delete(); 

    return redirect()->route('users.index')->with('success', 'Người dùng đã được xóa thành công.');
    }
    public function create()
{
    return view('users.create');
}
public function store(Request $request)
{
    $validated = $request->validate([
        'name' => 'required|string|max:255',
        'email' => 'required|email|max:255|unique:users,email',
        'phone_number' => 'nullable|string|max:20',
        'date_of_birth' => 'nullable|date',
        'gender' => 'nullable|in:Nam,Nữ,Khác',
        'role' => 'nullable|string|max:50',
        'status' => 'required|in:active,locked',
    ]);

    // 1. Lưu vào SQL
    $user = User::create($validated);

    // 2. Ghi lên Firestore
    $firestore = app('firebase.firestore');
    $db = $firestore->database();

    $firebaseId = $db->collection('users')->add([
        'displayName' => $user->name,
        'email' => $user->email,
        'phone' => $user->phone_number,
        'dateOfBirth' => $user->date_of_birth,
        'gender' => $user->gender,
        'role' => $user->role,
        'status' => $user->status,
        'createdAt' => now()->toDateTimeString(),
    ])->id();

    // 3. Lưu UID vào SQL để dùng về sau
    $user->firebase_uid = $firebaseId;
    $user->save();

    return redirect()->route('users.index')->with('success', 'Người dùng đã được tạo!');
}

}