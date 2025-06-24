<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('applications', function (Blueprint $table) {
        $table->id();
        $table->string('code')->unique(); // Mã hồ sơ như HS001
        $table->string('user_name');      // Tên người dùng
        $table->date('submitted_at');     // Ngày gửi
        $table->enum('status', ['Chờ duyệt', 'Đã duyệt', 'Chưa duyệt']);
        $table->enum('type', ['Tài xế', 'Khách hàng']);
        $table->timestamps();
    });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('applications');
    }
};
