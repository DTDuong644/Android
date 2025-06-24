@extends('app')

@section('content')
<div class="container">
    <h3>Thêm người dùng mới</h3>

    <form action="{{ route('users.store') }}" method="POST">
        @csrf

        <div class="mb-3">
            <label for="name">Họ tên</label>
            <input type="text" name="name" class="form-control" required value="{{ old('name') }}">
        </div>

        <div class="mb-3">
            <label for="email">Email</label>
            <input type="email" name="email" class="form-control" required value="{{ old('email') }}">
        </div>

        <div class="mb-3">
            <label for="phone_number">Số điện thoại</label>
            <input type="text" name="phone_number" class="form-control" value="{{ old('phone_number') }}">
        </div>

        <div class="mb-3">
            <label for="date_of_birth">Ngày sinh</label>
            <input type="date" name="date_of_birth" class="form-control" value="{{ old('date_of_birth') }}">
        </div>

        <div class="mb-3">
            <label for="gender">Giới tính</label>
            <select name="gender" class="form-control">
                <option value="">-- Chọn --</option>
                <option value="Nam">Nam</option>
                <option value="Nữ">Nữ</option>
                <option value="Khác">Khác</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="role">Vai trò</label>
            <input type="text" name="role" class="form-control" value="{{ old('role') }}">
        </div>

        <div class="mb-3">
            <label for="status">Trạng thái</label>
            <select name="status" class="form-control" required>
                <option value="active">Active</option>
                <option value="locked">Locked</option>
            </select>
        </div>

        <button class="btn btn-primary">Lưu</button>
    </form>
</div>
@endsection
