<?php
namespace App\Services;

use Google\Cloud\Firestore\FirestoreClient;

class FirestoreService
{
    protected $db;

    public function __construct()
    {
        $this->db = new FirestoreClient([
            'keyFilePath' => storage_path('app/firebase/firebase_credentials.json'),
            'projectId' => 'tlurideshare',
        ]);
    }

    public function getCollection($collection)
    {
        return $this->db->collection($collection);
    }
}
