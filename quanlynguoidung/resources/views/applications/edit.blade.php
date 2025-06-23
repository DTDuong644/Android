@extends('app')

@section('title', 'Duyệt hồ sơ')

@section('content')
<div class="container mt-3">
    <div class="text-center mb-3">
        <h4 class="bg-primary text-white py-2 rounded">Thông tin duyệt hồ sơ</h4>
    </div>

    <div class="border p-4 bg-white rounded">
        <div class="row mb-3">
            <div class="col-md-6">
                <label>Họ và tên:</label>
                <input type="text" class="form-control" value="{{ $application->user_name ?? '' }}" disabled>
            </div>
            <div class="col-md-3">
                <label>Ngày sinh:</label>
                <input type="text" class="form-control" value="{{ $application->date_of_birth }}" disabled>
            </div>
            <div class="col-md-3">
                <label>CCCD:</label>
                <input type="text" class="form-control" value="{{ $application->cccd }}" disabled>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6 text-center">
                <label>Ảnh chụp CCCD (mặt trước):</label><br>
                <img src="{{ asset('storage/' . $application->front_image) }}" class="img-thumbnail" width="200">
            </div>
            <div class="col-md-6 text-center">
                <label>Ảnh chụp CCCD (mặt sau):</label><br>
                <img src="{{ asset('storage/' . $application->back_image) }}" class="img-thumbnail" width="200">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>Số tài khoản ngân hàng:</label>
                <input type="text" class="form-control" value="{{ $application->bank_account }}" disabled>
            </div>
            <div class="col-md-3">
                <label>Ngày gửi:</label>
                <input type="text" class="form-control" value="{{ $application->submitted_at }}" disabled>
            </div>
            <div class="col-md-3">
                <label>Vai trò:</label>
                <input type="text" class="form-control" value="{{ $application->role }}" disabled>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>Trạng thái:</label>
                <input type="text" class="form-control" value="{{ $application->status }}" disabled>
            </div>
            <div class="col-md-6">
                <label>Email:</label>
                <input type="text" class="form-control" value="{{ $application->email }}" disabled>
            </div>
        </div>

        <div class="text-center mt-4">
            <form action="{{ route('applications.update_status', $application->id) }}" method="POST" class="d-inline">
                @csrf
                <button name="status" value="Chưa duyệt" class="btn btn-danger">Từ chối</button>
<button name="status" value="Đã duyệt" class="btn btn-success">Duyệt hồ sơ</button>
<button name="status" value="Chờ duyệt" class="btn btn-primary">Yêu cầu bổ sung</button>
            </form>
        </div>
    </div>
</div>
@endsection
