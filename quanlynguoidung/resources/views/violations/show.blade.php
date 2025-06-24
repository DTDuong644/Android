@extends('app')

@section('title', 'Chi tiết vi phạm')

@section('content')
<div class="p-4">
    <h3 class="text-center bg-primary text-white py-2 rounded">Chi tiết vi phạm</h3>

    <div class="border p-4 bg-white mt-3 rounded">
        <form action="{{ route('violations.update', $violation->id) }}" method="POST">
            @csrf
            @method('PUT')

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label fw-bold">Mã vi phạm:</label>
                    <input type="text" class="form-control" value="{{ $violation->code }}" readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Loại vi phạm:</label>
                    <input type="text" class="form-control" value="{{ $violation->type }}" readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Trạng thái xử lý:</label>
                    <select name="status" class="form-control">
                        <option value="Chưa xử lý" {{ $violation->status == 'Chưa xử lý' ? 'selected' : '' }}>Chưa xử lý</option>
                        <option value="Đã xử lý" {{ $violation->status == 'Đã xử lý' ? 'selected' : '' }}>Đã xử lý</option>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Họ tên người vi phạm:</label>
                    <input type="text" class="form-control" value="{{ $violation->violator_name }}" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold">Vai trò:</label>
                    <input type="text" class="form-control" value="Tài xế" readonly>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Địa điểm xảy ra:</label>
                    <input type="text" class="form-control" value="{{ $violation->location ?? 'Đại học Thủy Lợi' }}" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold">Ngày vi phạm:</label>
                    <input type="text" class="form-control" value="{{ \Carbon\Carbon::parse($violation->violation_date)->format('d/m/Y') }}" readonly>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Ảnh/Video, bằng chứng (nếu có):</label><br>
                @if ($violation->evidence_url)
                    <img src="{{ asset('storage/' . $violation->evidence_url) }}" style="max-width: 200px;" class="img-thumbnail">
                @else
                    <p>(Chưa có)</p>
                @endif
            </div>

            <div class="text-center mt-4">
                <a href="{{ route('violations.index') }}" class="btn btn-danger me-2">Hủy</a>
                <button type="submit" class="btn btn-success me-2">Lưu thay đổi</button>
            </div>
        </form>

        {{-- Form XÓA tách riêng --}}
        <div class="text-center mt-2">
            <form action="{{ route('violations.destroy', $violation->id) }}" method="POST" onsubmit="return confirm('Bạn có chắc muốn xoá vi phạm này?')" class="d-inline">
                @csrf
                @method('DELETE')
                <button type="submit" class="btn btn-primary">Xóa</button>
            </form>
        </div>
    </div>
</div>
@endsection
