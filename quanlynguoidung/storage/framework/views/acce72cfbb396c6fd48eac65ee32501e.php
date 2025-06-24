

<?php $__env->startSection('title', 'Quản lý vi phạm'); ?>

<?php $__env->startSection('content'); ?>
<div class="p-4">

    
    <form action="<?php echo e(route('violations.index')); ?>" method="GET" class="mb-3">
        <div class="input-group">
            <span class="input-group-text"><i class="fas fa-search"></i></span>
            <input type="text" name="q" class="form-control" placeholder="Tìm kiếm theo mã vi phạm, ngày vi phạm, ....." value="<?php echo e(request('q')); ?>">
        </div>
    </form>

    
    <div class="bg-light p-2 mb-3 rounded d-flex align-items-center">
        <i class="fas fa-list-ul me-2"></i>
        <strong>Danh sách bạn tìm kiếm</strong>
    </div>

    
    <?php $__empty_1 = true; $__currentLoopData = $violations; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $index => $vp): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); $__empty_1 = false; ?>
        <div class="border rounded p-3 mb-3 shadow-sm bg-white d-flex justify-content-between align-items-center">
            <div>
                <p class="fw-bold"><?php echo e($index + 1); ?>. Mã vi phạm: <?php echo e($vp->code); ?></p>
                <p>- Người vi phạm: <?php echo e($vp->violator_name); ?></p>
                <p>- Loại vi phạm: <?php echo e($vp->type); ?></p>
                <p>- Ngày vi phạm: <?php echo e(\Carbon\Carbon::parse($vp->violation_date)->format('d-m-Y/H:i')); ?></p>
                <p>- Tình trạng: <?php echo e($vp->status); ?></p>
            </div>
            <a href="<?php echo e(route('violations.show', $vp->id)); ?>" class="text-dark">
    <i class="fas fa-eye fa-2x"></i>
</a>
        </div>
    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); if ($__empty_1): ?>
        <p class="text-muted">Không tìm thấy vi phạm nào phù hợp.</p>
    <?php endif; ?>

</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/violations/index.blade.php ENDPATH**/ ?>