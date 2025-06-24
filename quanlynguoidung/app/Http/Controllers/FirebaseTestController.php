<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;

class FirebaseTestController extends Controller
{
    public function testFirebase()
    {
        $auth = App::make('firebase.auth');

        // Tạo user mới trên Firebase Auth
        $user = $auth->createUser([
            'email' => 'thanhto1409@example.com',
            'password' => 'password22',
            'displayName' => 'user',
        ]);

        return response()->json($user);
    }
}
