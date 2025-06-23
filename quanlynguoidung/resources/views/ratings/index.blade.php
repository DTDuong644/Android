@extends('app')

@section('title', 'Đánh giá chuyến đi')

@section('content')
    {{-- Thanh tìm kiếm --}}
    <div class="input-group mb-3">
        <form action="{{ route('ratings.index') }}" method="GET" class="w-100 d-flex">
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo mã đánh giá, ngày đánh giá, ..." value="{{ request('search') }}">
            <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <div class="bg-light p-2 mb-3 rounded"><strong>Danh sách bạn tìm kiếm</strong></div>

    {{-- Danh sách đánh giá --}}
    @foreach ($ratings as $index => $rating)
        <div class="border p-3 mb-2 bg-white shadow-sm rounded">
            <strong>{{ $index + 1 }}. Mã đánh giá: {{ $rating->id }}</strong><br>
            - Hành khách: {{ $rating->passenger_name }}<br>
            - Tài xế: {{ $rating->driver_name }}<br>
            - Số sao:
            @for ($i = 1; $i <= $rating->stars; $i++)
                <i class="fas fa-star text-warning"></i>
            @endfor
            @for ($i = $rating->stars + 1; $i <= 5; $i++)
                <i class="far fa-star text-muted"></i>
            @endfor
            <br>
            - Nội dung đánh giá: {{ $rating->comment }}

            {{-- Nút xem chi tiết --}}
            <div class="mt-2">
                <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#ratingModal{{ $rating->id }}">
                    <i class="fas fa-eye"></i> Xem chi tiết
                </button>
            </div>
        </div>

        {{-- Modal chi tiết --}}
        <div class="modal fade" id="ratingModal{{ $rating->id }}" tabindex="-1" aria-labelledby="ratingModalLabel{{ $rating->id }}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ratingModalLabel{{ $rating->id }}">Chi tiết đánh giá #{{ $rating->id }}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Mã đánh giá:</strong> {{ $rating->code ?? 'Không có' }}</p>
                        <p><strong>Hành khách:</strong> {{ $rating->passenger_name }}</p>
                        <p><strong>Tài xế:</strong> {{ $rating->driver_name }}</p>
                        <p><strong>Số sao:</strong>
                            @for ($i = 1; $i <= $rating->stars; $i++)
                                <i class="fas fa-star text-warning"></i>
                            @endfor
                            @for ($i = $rating->stars + 1; $i <= 5; $i++)
                                <i class="far fa-star text-muted"></i>
                            @endfor
                        </p>
                        <p><strong>Nội dung:</strong> {{ $rating->comment }}</p>
                        <p><strong>Ngày tạo:</strong> {{ $rating->created_at->format('d/m/Y H:i') }}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
    @endforeach

    @if ($ratings->isEmpty())
        <div class="text-muted">Không có đánh giá nào.</div>
    @endif
@endsection
