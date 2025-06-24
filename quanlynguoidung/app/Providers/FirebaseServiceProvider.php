<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use Kreait\Firebase\Factory;
use Kreait\Firebase\Auth;
use Kreait\Firebase\Firestore;
use Kreait\Firebase\Messaging;

class FirebaseServiceProvider extends ServiceProvider
{
    public function register()
    {
        $this->app->singleton('firebase.auth', function () {
            return (new Factory)
                ->withServiceAccount(storage_path('app/firebase_credentials.json'))
                ->createAuth();
        });

        $this->app->singleton('firebase.firestore', function () {
            return (new Factory)
                ->withServiceAccount(storage_path('app/firebase_credentials.json'))
                ->createFirestore();
        });

        $this->app->singleton('firebase.messaging', function () {
            return (new Factory)
                ->withServiceAccount(storage_path('app/firebase_credentials.json'))
                ->createMessaging();
        });
    }

    public function boot() {}
}
