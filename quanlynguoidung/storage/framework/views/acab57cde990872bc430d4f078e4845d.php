 <?php $__env->startSection('title', 'Trang chủ Quản trị'); ?> <?php $__env->startSection('content'); ?>
    <h2 class="mb-4">Dashboard</h2>
    <div class="row">
        <div class="col-md-4 col-lg-3">
    <a href="/users" class="card-menu d-block text-decoration-none">
        <i class="fas fa-users"></i>
        <span>Quản lý người dùng</span>
    </a>
</div>
        <div class="col-md-4 col-lg-3">
            <div class="card-menu">
                <a href="<?php echo e(route('statistics')); ?>" >
                    <i class="fas fa-chart-bar text-white"></i>
                <span class="text-white">Thống kê</span></a>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card-menu">
                <a href ="<?php echo e(route('locations.index')); ?>">
                <i class="fas fa-chart-bar text-white"></i>
                <span class="text-white">Quản lý địa điểm</span></a>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card-menu">
                <a href ="<?php echo e(route('violations.index')); ?>">
                <i class="fas fa-chart-bar text-white"></i>
                <span class="text-white">Quản lý vi phạm</span></a>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card-menu">
                <a href ="<?php echo e(route('ratings.index')); ?>">
                <i class="fas fa-pencil-alt text-white"></i>
                <span class="text-white">Đánh giá chuyến đi</span></a>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card-menu">
                <a href="<?php echo e(route('applications.index')); ?>" class="nav-link <?php echo e(request()->is('admin/applications') ? 'active' : ''); ?>">
                <i class="fas fa-file-alt"></i>
                <span>Duyệt hồ sơ</span></a>
            </div>
        </div>
    </div>
<?php $__env->stopSection(); ?>

<?php $__env->startSection('scripts'); ?>
    <script>
        // console.log('Trang chủ đã được tải.');
    </script>
<?php $__env->stopSection(); ?>
<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/home.blade.php ENDPATH**/ ?>