@extends('app')

@section('title', 'Duyệt hồ sơ')

@section('content')
    {{-- Thanh tìm kiếm --}}
    <div class="input-group mb-3">
        <form action="{{ route('applications.index') }}" method="GET" class="w-100 d-flex">
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo địa điểm" value="{{ request('search') }}">
            <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <div class="bg-light p-2 mb-3 rounded"><strong>Danh sách bạn tìm kiếm</strong></div>

    @foreach ($applications as $index => $app)
        <div class="border p-2 mb-2 bg-white rounded">
            <strong>{{ $index + 1 }}. Mã hồ sơ {{ $app->code }}:</strong><br>
            - Tên người dùng: {{ $app->user_name }}<br>
            - Ngày gửi: {{ \Carbon\Carbon::parse($app->submitted_at)->format('d-m-Y') }}<br>
            - Trạng thái: {{ $app->status }}<br>
            - Loại hồ sơ: {{ $app->type }}<br>

            <div class="text-end mt-1">
                <a href="{{ route('applications.edit', $app->id) }}">
    <i class="fas fa-edit"></i>
</a>

                <form action="#" method="POST" class="d-inline">
                    @csrf
                    @method('DELETE')
                    <button onclick="return confirm('Xoá hồ sơ này?')" class="btn btn-sm btn-outline-danger"><i class="fas fa-trash"></i></button>
                </form>
            </div>
        </div>
    @endforeach

    @if ($applications->isEmpty())
        <div class="text-muted">Không có hồ sơ nào.</div>
    @endif
@endsection
