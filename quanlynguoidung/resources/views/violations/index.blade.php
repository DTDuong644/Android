@extends('app')

@section('title', 'Quản lý vi phạm')

@section('content')
<div class="p-4">

    {{-- Thanh tìm kiếm --}}
    <form action="{{ route('violations.index') }}" method="GET" class="mb-3">
        <div class="input-group">
            <span class="input-group-text"><i class="fas fa-search"></i></span>
            <input type="text" name="q" class="form-control" placeholder="Tìm kiếm theo mã vi phạm, ngày vi phạm, ....." value="{{ request('q') }}">
        </div>
    </form>

    {{-- Tiêu đề --}}
    <div class="bg-light p-2 mb-3 rounded d-flex align-items-center">
        <i class="fas fa-list-ul me-2"></i>
        <strong>Danh sách bạn tìm kiếm</strong>
    </div>

    {{-- Danh sách vi phạm --}}
    @forelse($violations as $index => $vp)
        <div class="border rounded p-3 mb-3 shadow-sm bg-white d-flex justify-content-between align-items-center">
            <div>
                <p class="fw-bold">{{ $index + 1 }}. Mã vi phạm: {{ $vp->code }}</p>
                <p>- Người vi phạm: {{ $vp->violator_name }}</p>
                <p>- Loại vi phạm: {{ $vp->type }}</p>
                <p>- Ngày vi phạm: {{ \Carbon\Carbon::parse($vp->violation_date)->format('d-m-Y/H:i') }}</p>
                <p>- Tình trạng: {{ $vp->status }}</p>
            </div>
            <a href="{{ route('violations.show', $vp->id) }}" class="text-dark">
    <i class="fas fa-eye fa-2x"></i>
</a>
        </div>
    @empty
        <p class="text-muted">Không tìm thấy vi phạm nào phù hợp.</p>
    @endforelse

</div>
@endsection
