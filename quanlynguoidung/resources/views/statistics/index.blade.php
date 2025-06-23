@extends('app')

@section('title', 'Thống kê')

@section('content')
    <h2 class="mb-4">Thống kê số liệu tháng 10/2024</h2> {{-- Giả sử tháng 10/2024 như ảnh --}}

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <i class="fas fa-list me-2"></i> 1. Tổng quan
        </div>
        <div class="card-body">
            <p>- Số lượng người dùng hiện tại: <strong>1,068</strong> người</p>
            <p>- Số chuyến xe trong tháng: <strong>300</strong> chuyến</p>
            <p>- Tổng doanh thu thu tháng: <strong>300.000.000</strong> vnd</p>
            <p>- Tỷ lệ chuyến đi thành công/hủy: <strong>20/2</strong></p>
            <p>- Số lượng xe đang hoạt động: <strong>200</strong> xe</p>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <i class="fas fa-list me-2"></i> 2. Thống kê chuyến đi
        </div>
        <div class="card-body">
            <p>- Thời gian trung bình mỗi chuyến xe: <strong>45 phút</strong></p>
            <p>- Khoảng cách trung bình mỗi chuyến đi: <strong>10km</strong></p>
            <p>- Lượt đánh giá trung bình: <strong>4.5</strong> <i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star-half-alt text-warning"></i></p>
            <p>- Tỷ lệ chuyến đi thành công/hủy: <strong>20/2</strong></p>
            <p>- Số lượng xe đang hoạt động: <strong>200</strong> xe</p>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <i class="fas fa-list me-2"></i> 3. Thống kê người dùng
        </div>
        <div class="card-body">
            <p>- Số lượng người đăng ký mới trong tháng: <strong>101</strong> người</p>
            <p>- Số người dùng hoạt động thường xuyên: <strong>54</strong> người</p>
            <p>- Số lượng phản hồi sau mỗi chuyến đi: <strong>40</strong></p>
        </div>
    </div>
@endsection