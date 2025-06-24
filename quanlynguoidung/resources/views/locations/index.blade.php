{{-- resources/views/locations/index.blade.php --}}

@extends('app') {{-- Kế thừa từ tệp layout chính của bạn --}}

@section('title', 'Quản lý địa điểm') {{-- Đặt tiêu đề cho trang cụ thể này --}}

@section('content')
    <div class="container-fluid"> {{-- Sử dụng container-fluid cho chiều rộng đầy đủ --}}
        {{-- Tiêu đề Dashboard từ hình ảnh đầu tiên của bạn không có ở đây, vì vậy nó bị bỏ qua. --}}
        {{-- Nếu bạn muốn có tiêu đề, hãy bỏ ghi chú dòng bên dưới: --}}
        {{-- <h1 class="mb-4">Quản lý địa điểm</h1> --}}

        <div class="row justify-content-center">
            <div class="col-12"> {{-- Sử dụng col-12 để lấp đầy toàn bộ chiều rộng nội dung --}}
                <div class="card shadow-sm mb-4">
                    <div class="card-body">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <form action="{{ route('locations.index') }}" method="GET" class="flex-grow-1 me-3">
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-search text-muted"></i>
                                    </span>
                                    <input type="text" name="search" class="form-control border-start-0" placeholder="Tìm kiếm theo địa điểm" value="{{ $searchTerm ?? '' }}">
                                </div>
                            </form>
                            <a href="{{ route('locations.create') }}" class="btn btn-primary" type="button">
                                <i class="fas fa-plus"></i>
                            </a>
                        </div>

                        <div class="list-group">
                            <div class="list-group-item list-group-item-action bg-light text-dark fw-bold border-bottom-0 rounded-0 py-2">
                                <i class="fas fa-list"></i> Danh sách bạn tìm kiếm
                            </div>

                            @forelse($locations as $index => $location)
                                <div class="list-group-item list-group-item-action border-top-0 {{ $index == 0 ? 'rounded-top-0' : '' }} {{ $index == count($locations) - 1 ? 'rounded-bottom-0' : '' }} py-3">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <strong>{{ $index + 1 }}. {{ $location->name }}</strong><br>
                                            <small>- Số chuyến đi: {{ $location->so_chuyen_di }} chuyến</small><br>
                                            <small>- Số chuyến đến: {{ $location->so_chuyen_den }} chuyến</small><br>
                                            <small>- Tài xế đang hoạt động: {{ $location->tai_xe_dang_hoat_dong }} tài xế</small>
                                        </div>
                                        <div>
                                            <a href="{{ route('locations.edit', $location->id) }}" class="btn btn-outline-secondary btn-sm">
                                                <i class="fas fa-pencil-alt"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            @empty
                                <div class="list-group-item text-center text-muted py-4">
                                    Không tìm thấy địa điểm nào.
                                </div>
                            @endforelse
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection

@push('styles')
<style>
    /* Các style tùy chỉnh để khớp với hình ảnh */
    body {
        background-color: #f0f2f5; /* Nền màu xám nhạt */
    }
    .card {
        border: none;
        border-radius: .5rem;
    }
    .input-group-text {
        border-right: none;
    }
    .form-control {
        border-left: none;
    }
    .btn-primary {
        background-color: #007bff; /* Màu xanh dương */
        border-color: #007bff;
    }
    .btn-primary:hover {
        background-color: #0056b3;
        border-color: #0056b3;
    }
    .list-group-item {
        margin-bottom: 0px; /* Xóa khoảng trống giữa các mục danh sách */
        border: 1px solid #dee2e6; /* Thêm viền cho các mục danh sách */
        background-color: #fff;
    }
    .list-group-item:first-child {
        border-top-left-radius: 0.25rem;
        border-top-right-radius: 0.25rem;
    }
    .list-group-item:last-child {
        border-bottom-left-radius: 0.25rem;
        border-bottom-right-radius: 0.25rem;
    }
    .list-group-item strong {
        color: #343a40;
    }
    .list-group-item small {
        color: #6c757d;
    }
    .btn-outline-secondary {
        color: #6c757d;
        border-color: #6c757d;
    }
    .btn-outline-secondary:hover {
        background-color: #6c757d;
        color: #fff;
    }
    /* Kích thước biểu tượng */
    .fas {
        font-size: 1.1em;
    }
</style>
@endpush