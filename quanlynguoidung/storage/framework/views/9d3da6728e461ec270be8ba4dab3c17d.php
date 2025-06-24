

<?php $__env->startSection('title', 'Duyệt hồ sơ'); ?>

<?php $__env->startSection('content'); ?>
    
    <div class="input-group mb-3">
        <form action="<?php echo e(route('applications.index')); ?>" method="GET" class="w-100 d-flex">
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo địa điểm" value="<?php echo e(request('search')); ?>">
            <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <div class="bg-light p-2 mb-3 rounded"><strong>Danh sách bạn tìm kiếm</strong></div>

    <?php $__currentLoopData = $applications; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $index => $app): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
        <div class="border p-2 mb-2 bg-white rounded">
            <strong><?php echo e($index + 1); ?>. Mã hồ sơ <?php echo e($app->code); ?>:</strong><br>
            - Tên người dùng: <?php echo e($app->user_name); ?><br>
            - Ngày gửi: <?php echo e(\Carbon\Carbon::parse($app->submitted_at)->format('d-m-Y')); ?><br>
            - Trạng thái: <?php echo e($app->status); ?><br>
            - Loại hồ sơ: <?php echo e($app->type); ?><br>

            <div class="text-end mt-1">
                <a href="<?php echo e(route('applications.edit', $app->id)); ?>">
    <i class="fas fa-edit"></i>
</a>

                <form action="#" method="POST" class="d-inline">
                    <?php echo csrf_field(); ?>
                    <?php echo method_field('DELETE'); ?>
                    <button onclick="return confirm('Xoá hồ sơ này?')" class="btn btn-sm btn-outline-danger"><i class="fas fa-trash"></i></button>
                </form>
            </div>
        </div>
    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>

    <?php if($applications->isEmpty()): ?>
        <div class="text-muted">Không có hồ sơ nào.</div>
    <?php endif; ?>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/applications/index.blade.php ENDPATH**/ ?>