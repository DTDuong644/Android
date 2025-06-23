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
        Schema::create('violations', function (Blueprint $table) {
        $table->id();
        $table->string('code'); // mã vi phạm: VP001
        $table->string('violator_name');
        $table->string('type'); // loại vi phạm
        $table->dateTime('violation_date');
        $table->string('status'); // đã xử lý / chưa xử lý
        $table->timestamps();
    });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('violations');
    }
};
