@extends('app')

@section('title', 'Quản lý người dùng')

@section('content')
<h2 class="mb-4">Quản lý người dùng</h2>

{{-- Thanh tìm kiếm và lọc --}}
<form method="GET" action="{{ route('users.index') }}">
    <div class="d-flex flex-column flex-md-row flex-wrap align-items-center gap-2 mb-4">
        {{-- Ô tìm kiếm --}}
        <div class="flex-grow-1">
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-search"></i></span>
                <input type="text" name="q" class="form-control" placeholder="Tìm kiếm theo tên, email, số điện thoại..." value="{{ request('q') }}">
            </div>
        </div>

        {{-- Dropdown và nút lọc --}}
        <div class="d-flex align-items-center gap-2 mt-2 mt-md-0">
            <select name="status" class="form-select">
                <option value="">-- Lọc trạng thái --</option>
                <option value="active" {{ request('status') == 'active' ? 'selected' : '' }}>Đang hoạt động</option>
                <option value="locked" {{ request('status') == 'locked' ? 'selected' : '' }}>Bị khoá</option>
            </select>
            <button type="submit" class="btn btn-dark">Lọc</button>
        </div>
    </div>
</form>

{{-- Danh sách người dùng --}}
<div class="card mb-3">
    <div class="card-header bg-light d-flex align-items-center">
        <i class="fas fa-list me-2"></i> Danh sách bạn tìm kiếm
    </div>
    <div class="card-body p-0">
        <ul class="list-group list-group-flush">
            @forelse ($users as $user)
                <li class="list-group-item d-flex flex-column flex-md-row align-items-md-center py-3">
                    <div class="flex-grow-1 mb-2 mb-md-0">
                        <p class="mb-1">
                            <i class="fas fa-user-circle me-2"></i>
                            <strong>Tên người dùng:</strong> {{ $user->name }}
                        </p>
                        <p class="mb-1 small text-muted">
                            <i class="fas fa-envelope me-2"></i>
                            Email: {{ $user->email }}
                        </p>
                        <p class="mb-0 small text-muted">
                            <i class="fas fa-phone me-2"></i>
                            Số điện thoại: {{ $user->phone_number ?? '(Không có)' }}
                        </p>
                    </div>
                    <div class="d-flex align-items-center mt-2 mt-md-0">
                        <span class="badge {{ $user->status == 'active' ? 'bg-success' : 'bg-danger' }} me-3 py-2 px-3">
                            {{ $user->status == 'active' ? 'Đang hoạt động' : 'Bị khóa' }}
                        </span>

                        {{-- Nút chỉnh sửa --}}
                        <a href="{{ route('users.edit', $user->id) }}" class="btn btn-outline-primary btn-sm me-2">
                            <i class="fas fa-pencil-alt"></i>
                        </a>

                        {{-- Nút xoá --}}
                        <form action="{{ route('users.destroy', $user->id) }}" method="POST" onsubmit="return confirm('Bạn có chắc chắn muốn xóa người dùng này?')" class="d-inline">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-outline-danger btn-sm">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </form>
                    </div>
                </li>
            @empty
                <li class="list-group-item text-center text-muted">Không tìm thấy người dùng nào phù hợp.</li>
            @endforelse
        </ul>
    </div>
</div>

{{-- Phân trang giữ filter --}}
@if ($users->hasPages())
    <div class="mt-3 d-flex justify-content-center">
        {{ $users->appends(request()->query())->links() }}
    </div>
@endif
@endsection
