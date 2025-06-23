<?php
namespace App\Http\Controllers;

use App\Services\FirestoreService;
use Illuminate\Http\Request;

class FirestoreController extends Controller
{
    public function addRide(FirestoreService $firestore)
    {
        $firestore->getCollection('rides')->add([
            'driver' => 'Nguyễn Văn A',
            'pickup' => 'Trường ĐH TLU',
            'status' => 'waiting',
            'created_at' => now()
        ]);

        return response()->json(['message' => 'Ride added to Firestore']);
    }

    public function listRides(FirestoreService $firestore)
    {
        $documents = $firestore->getCollection('rides')->documents();
        $results = [];

        foreach ($documents as $doc) {
            if ($doc->exists()) {
                $results[] = $doc->data();
            }
        }

        return response()->json($results);
    }
}
