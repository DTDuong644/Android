package com.example.tlu_rideshare; // Đảm bảo đúng tên package của bạn

import android.app.Application;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.appcheck.FirebaseAppCheck;
//import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
// import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory; // Bỏ comment này nếu bạn muốn dùng Play Integrity cho bản chính thức


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Firebase App Check cho debug builds
        FirebaseApp.initializeApp(this); // Dòng này đảm bảo Firebase được khởi tạo

        // Lấy instance của FirebaseAppCheck và sau đó gọi installProviderFactory
//        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
//        firebaseAppCheck.installAppCheckProviderFactory(
//                DebugAppCheckProviderFactory.getInstance());

        // Nếu bạn muốn dùng Play Integrity (cho bản release), bạn sẽ dùng dòng này thay thế:
        // firebaseAppCheck.installProviderFactory(
        //         PlayIntegrityAppCheckProviderFactory.getInstance());
    }
}