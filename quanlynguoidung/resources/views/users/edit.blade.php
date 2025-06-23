@extends('app')

@section('title', 'Chỉnh sửa thông tin')

@section('content')
<div class="container py-4" style="max-width: 800px; margin: auto;">
    <h3 class="text-center mb-4">Cập nhật thông tin cá nhân</h3>

    {{-- Hiển thị lỗi validate --}}
    @if ($errors->any())
        <div class="alert alert-danger">
            <ul class="mb-0">
                @foreach ($errors->all() as $error)
                    <li>{{ $error }}</li>
                @endforeach
            </ul>
        </div>
    @endif

    <form method="POST" action="{{ route('users.update', $user->id) }}">
        @csrf
        @method('PUT')

        <div class="mb-3">
            <label class="form-label fw-bold">Họ và tên</label>
            <input type="text" name="name" class="form-control" value="{{ old('name', $user->name) }}">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Email</label>
            <input type="email" name="email" class="form-control" value="{{ old('email', $user->email) }}">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Số điện thoại</label>
            <input type="text" name="phone_number" class="form-control" value="{{ old('phone_number', $user->phone_number) }}">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Ngày sinh</label>
            <input type="date" name="date_of_birth" class="form-control"
                   value="{{ old('date_of_birth', optional($user->date_of_birth)->format('Y-m-d')) }}">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Giới tính</label>
            <select name="gender" class="form-select">
                @php $gender = old('gender', $user->gender); @endphp
                <option value="Nam" {{ $gender == 'Nam' ? 'selected' : '' }}>Nam</option>
                <option value="Nữ" {{ $gender == 'Nữ' ? 'selected' : '' }}>Nữ</option>
                <option value="Khác" {{ $gender == 'Khác' ? 'selected' : '' }}>Khác</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Vai trò</label>
            <input type="text" name="role" class="form-control" value="{{ old('role', $user->role) }}">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Trạng thái</label>
            @php $status = old('status', $user->status); @endphp
            <select name="status" class="form-select">
                <option value="active" {{ $status == 'active' ? 'selected' : '' }}>Đang hoạt động</option>
                <option value="locked" {{ $status == 'locked' ? 'selected' : '' }}>Bị khóa</option>
            </select>
        </div>

        <div class="d-flex justify-content-end">
            <a href="{{ route('users.show', $user->id) }}" class="btn btn-secondary me-2">Hủy</a>
            <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        </div>
    </form>
</div>
@endsection
