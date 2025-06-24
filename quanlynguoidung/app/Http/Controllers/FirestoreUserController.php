<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;

class FirestoreUserController extends Controller
{
    public function updateUser(Request $request, $uid)
    {
        $firestore = App::make('firebase.firestore');
        $db = $firestore->database();

        // Tham chiếu đến collection "users", document có ID là uid
        $userRef = $db->collection('users')->document($uid);

        // Dữ liệu cần update
        $data = [
            'displayName' => $request->input('displayName', 'Không tên'),
            'phone' => $request->input('phone', null),
            'updatedAt' => now()->toDateTimeString(),
        ];

        // Ghi đè hoặc tạo nếu chưa tồn tại
        $userRef->set($data, ['merge' => true]);

        return response()->json(['status' => 'updated', 'uid' => $uid]);
    }
}
