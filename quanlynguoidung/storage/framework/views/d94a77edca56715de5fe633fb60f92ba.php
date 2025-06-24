

 

<?php $__env->startSection('title', 'Quản lý địa điểm'); ?> 

<?php $__env->startSection('content'); ?>
    <div class="container-fluid"> 
        
        
        

        <div class="row justify-content-center">
            <div class="col-12"> 
                <div class="card shadow-sm mb-4">
                    <div class="card-body">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <form action="<?php echo e(route('locations.index')); ?>" method="GET" class="flex-grow-1 me-3">
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-search text-muted"></i>
                                    </span>
                                    <input type="text" name="search" class="form-control border-start-0" placeholder="Tìm kiếm theo địa điểm" value="<?php echo e($searchTerm ?? ''); ?>">
                                </div>
                            </form>
                            <a href="<?php echo e(route('locations.create')); ?>" class="btn btn-primary" type="button">
                                <i class="fas fa-plus"></i>
                            </a>
                        </div>

                        <div class="list-group">
                            <div class="list-group-item list-group-item-action bg-light text-dark fw-bold border-bottom-0 rounded-0 py-2">
                                <i class="fas fa-list"></i> Danh sách bạn tìm kiếm
                            </div>

                            <?php $__empty_1 = true; $__currentLoopData = $locations; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $index => $location): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); $__empty_1 = false; ?>
                                <div class="list-group-item list-group-item-action border-top-0 <?php echo e($index == 0 ? 'rounded-top-0' : ''); ?> <?php echo e($index == count($locations) - 1 ? 'rounded-bottom-0' : ''); ?> py-3">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <strong><?php echo e($index + 1); ?>. <?php echo e($location->name); ?></strong><br>
                                            <small>- Số chuyến đi: <?php echo e($location->so_chuyen_di); ?> chuyến</small><br>
                                            <small>- Số chuyến đến: <?php echo e($location->so_chuyen_den); ?> chuyến</small><br>
                                            <small>- Tài xế đang hoạt động: <?php echo e($location->tai_xe_dang_hoat_dong); ?> tài xế</small>
                                        </div>
                                        <div>
                                            <a href="<?php echo e(route('locations.edit', $location->id)); ?>" class="btn btn-outline-secondary btn-sm">
                                                <i class="fas fa-pencil-alt"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); if ($__empty_1): ?>
                                <div class="list-group-item text-center text-muted py-4">
                                    Không tìm thấy địa điểm nào.
                                </div>
                            <?php endif; ?>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<?php $__env->stopSection(); ?>

<?php $__env->startPush('styles'); ?>
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
<?php $__env->stopPush(); ?>
<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/locations/index.blade.php ENDPATH**/ ?>