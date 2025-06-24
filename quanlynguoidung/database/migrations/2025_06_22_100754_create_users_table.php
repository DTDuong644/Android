<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up(): void
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->string('phone_number')->nullable();
            $table->date('date_of_birth')->nullable();
            $table->string('role')->default('user'); // 'user', 'admin', ...
            $table->string('gender')->nullable(); // Nam / Nữ / Khác
            $table->string('cccd')->nullable()->unique(); // Số căn cước công dân
            $table->enum('status', ['active', 'locked'])->default('active'); // Trạng thái hoạt động
            $table->string('avatar')->nullable(); // Ảnh đại diện (đường dẫn)
            $table->timestamp('email_verified_at')->nullable();
            $table->string('password'); // ✅ Thêm dòng này
            $table->rememberToken();    // ✅ Thêm dòng này
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('users');
    }
};
