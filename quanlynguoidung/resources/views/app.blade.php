<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>@yield('title', 'Ứng dụng Quản trị')</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .navbar-top {
            background-color: #e9ecef;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #dee2e6;
        }
        .navbar-top .logo img {
            height: 40px; /* Adjust as needed */
        }
        .navbar-top .admin-info {
            font-weight: bold;
            display: flex;
            align-items: center;
        }
        .navbar-top .admin-info i {
            margin-right: 8px;
            font-size: 1.2rem;
        }

        .sidebar {
            width: 250px;
            background-color: #343a40;
            color: white;
            position: fixed; /* Fixed sidebar */
            height: 100%;
            padding-top: 20px;
            overflow-y: auto; /* Enable scrolling if content exceeds height */
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }
        .sidebar .sidebar-header {
            text-align: center;
            padding-bottom: 20px;
            color: white;
            font-size: 1.5rem;
            border-bottom: 1px solid #495057;
            margin-bottom: 15px;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            padding: 12px 20px;
            display: block;
            font-size: 1rem;
            transition: background-color 0.2s ease;
        }
        .sidebar a:hover {
            background-color: #007bff; /* Primary blue on hover */
            color: white;
        }
        .sidebar a i {
            margin-right: 10px;
            width: 20px; /* Consistent icon width */
        }

        .content-wrapper {
            margin-left: 250px; /* Cân bằng với chiều rộng sidebar */
            padding-top: 60px; /* Để tránh navbar top */
            padding: 20px;
        }

        /* Styles cho các card menu */
        .card-menu {
            background-color: #007bff; /* Mặc định màu xanh dương */
            color: white;
            padding: 25px;
            text-align: center;
            border-radius: 8px;
            margin-bottom: 25px; /* Khoảng cách giữa các card */
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            min-height: 150px; /* Chiều cao tối thiểu cho card */
            display: flex;
            flex-direction: column ;
            justify-content: center;
            align-items: center;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* Bóng nhẹ */
        }
        .card-menu:hover {
            background-color: #0056b3; /* Xanh đậm hơn khi hover */
            transform: translateY(-5px); /* Hiệu ứng nổi lên */
        }
        .card-menu i {
            font-size: 3.5rem; /* Kích thước icon lớn hơn */
            margin-bottom: 15px;
        }
        .card-menu span {
            font-size: 1.1rem; /* Kích thước chữ cho tiêu đề */
            font-weight: bold;
        }

        /* Điều chỉnh responsive (tùy chọn) */
        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                height: auto;
                position: relative;
                box-shadow: none;
            }
            .content-wrapper {
                margin-left: 0;
                padding-top: 10px; /* Điều chỉnh lại padding */
            }
        }
     

    </style>
</head>
<body>
    <nav class="navbar-top">
        <div class="logo">
            <img src="{{ asset('images/logo.png') }}" alt="Logo"> </div>
        <div class="admin-info">
            <i class="fas fa-user-circle"></i> Admin
        </div>
    </nav>

    <div class="d-flex">
        <div class="sidebar">
            <div class="sidebar-header">
                Hệ thống Quản lý
            </div>
            <a href="/"><i class="fas fa-home"></i> Trang chủ</a>
            <a href="/users"><i class="fas fa-users"></i> Quản lý người dùng</a>
            <a href="{{ route('locations.index') }}"><i class="fas fa-map-marker-alt"></i> Quản lý địa điểm</a>
            <a href="{{ route('violations.index') }}"><i class="fas fa-exclamation-triangle"></i> Quản lý vi phạm</a>
            <a href="{{ route('ratings.index') }}"><i class="fas fa-pencil-alt"></i> Đánh giá chuyến đi</a>
            <ul class="nav flex-column">
    <li class="nav-item">
        <a class="nav-link" href="{{ route('statistics') }}">
            <i class="fas fa-chart-bar me-2"></i> Thống kê
        </a>
    </li>
    </ul>
            <a href={{ route('applications.index') }}><i class="fas fa-file-alt"></i> Duyệt hồ sơ</a>
        </div>

        <div class="content-wrapper">
            @yield('content')
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    @yield('scripts')
</body>
</html>