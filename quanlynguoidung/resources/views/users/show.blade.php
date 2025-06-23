@extends('app')

@section('title', 'Thông tin cá nhân')

@section('content')
<div class="container-fluid py-4" style="max-width: 800px; margin: auto;">
    <div class="card border">
        <div class="card-header bg-primary text-white text-center fw-bold">
            Thông tin cá nhân
        </div>
        <div class="card-body">
            <div class="row">
                {{-- Avatar --}}
                <div class="col-md-4 text-center">
                    <img src="https://via.placeholder.com/120x120.png?text=Avatar" alt="Avatar" class="rounded-circle img-fluid mb-2 border border-dark" style="width: 120px; height: 120px;">
                </div>

                {{-- Thông tin cá nhân --}}
                <div class="col-md-8">
                    <div class="row mb-2">
                        <div class="col-6"><strong>Họ và tên:</strong> {{ $user->name }}</div>
                        <div class="col-6"><strong>Ngày sinh:</strong> {{ $user->date_of_birth }}</div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Vai trò:</strong> {{ $user->role }}</div>
                        <div class="col-6"><strong>Số điện thoại:</strong> {{ $user->phone_number }}</div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Email:</strong> {{ $user->email }}</div>
                        <div class="col-6"><strong>Giới tính:</strong> {{ $user->gender }}</div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>CCCD:</strong> {{ $user->cccd }}</div>
                        <div class="col-6"><strong>Ngày tạo tài khoản:</strong> {{ $user->created_at->format('d/m/Y') }}</div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Trạng thái:</strong>
                            <span class="badge {{ $user->status === 'active' ? 'bg-success' : 'bg-danger' }}">
                                {{ $user->status === 'active' ? 'Đang hoạt động' : 'Bị khóa' }}
                            </span>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4"><strong>Số chuyến hoàn thành:</strong> {{ $user->completed_trips }}</div>
                        <div class="col-4"><strong>Hủy chuyến:</strong> {{ $user->canceled_trips }}</div>
                        <div class="col-4"><strong>Lượt đánh giá:</strong> {{ $user->ratings_count }}</div>
                    </div>
                </div>
            </div>

            <hr>

            {{-- Nút hành động --}}
            <div class="d-flex justify-content-center gap-3 mt-3">
                <form method="POST" action="{{ route('users.lock', $user->id) }}">
                    @csrf
                    @method('PATCH')
                    <button type="submit" class="btn btn-danger">Khóa tài khoản</button>
                </form>

                <a href="{{ route('users.edit', $user->id) }}" class="btn btn-success">Cập nhật</a>
            </div>
        </div>
    </div>
</div>
@endsection
