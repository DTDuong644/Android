

<?php $__env->startSection('title', 'Quản lý người dùng'); ?>

<?php $__env->startSection('content'); ?>
<h2 class="mb-4">Quản lý người dùng</h2>


<form method="GET" action="<?php echo e(route('users.index')); ?>">
    <div class="d-flex flex-column flex-md-row flex-wrap align-items-center gap-2 mb-4">
        
        <div class="flex-grow-1">
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-search"></i></span>
                <input type="text" name="q" class="form-control" placeholder="Tìm kiếm theo tên, email, số điện thoại..." value="<?php echo e(request('q')); ?>">
            </div>
        </div>

        
        <div class="d-flex align-items-center gap-2 mt-2 mt-md-0">
            <select name="status" class="form-select">
                <option value="">-- Lọc trạng thái --</option>
                <option value="active" <?php echo e(request('status') == 'active' ? 'selected' : ''); ?>>Đang hoạt động</option>
                <option value="locked" <?php echo e(request('status') == 'locked' ? 'selected' : ''); ?>>Bị khoá</option>
            </select>
            <button type="submit" class="btn btn-dark">Lọc</button>
        </div>
    </div>
</form>


<div class="card mb-3">
    <div class="card-header bg-light d-flex align-items-center">
        <i class="fas fa-list me-2"></i> Danh sách bạn tìm kiếm
    </div>
    <div class="card-body p-0">
        <ul class="list-group list-group-flush">
            <?php $__empty_1 = true; $__currentLoopData = $users; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $user): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); $__empty_1 = false; ?>
                <li class="list-group-item d-flex flex-column flex-md-row align-items-md-center py-3">
                    <div class="flex-grow-1 mb-2 mb-md-0">
                        <p class="mb-1">
                            <i class="fas fa-user-circle me-2"></i>
                            <strong>Tên người dùng:</strong> <?php echo e($user->name); ?>

                        </p>
                        <p class="mb-1 small text-muted">
                            <i class="fas fa-envelope me-2"></i>
                            Email: <?php echo e($user->email); ?>

                        </p>
                        <p class="mb-0 small text-muted">
                            <i class="fas fa-phone me-2"></i>
                            Số điện thoại: <?php echo e($user->phone_number ?? '(Không có)'); ?>

                        </p>
                    </div>
                    <div class="d-flex align-items-center mt-2 mt-md-0">
                        <span class="badge <?php echo e($user->status == 'active' ? 'bg-success' : 'bg-danger'); ?> me-3 py-2 px-3">
                            <?php echo e($user->status == 'active' ? 'Đang hoạt động' : 'Bị khóa'); ?>

                        </span>

                        
                        <a href="<?php echo e(route('users.edit', $user->id)); ?>" class="btn btn-outline-primary btn-sm me-2">
                            <i class="fas fa-pencil-alt"></i>
                        </a>

                        
                        <form action="<?php echo e(route('users.destroy', $user->id)); ?>" method="POST" onsubmit="return confirm('Bạn có chắc chắn muốn xóa người dùng này?')" class="d-inline">
                            <?php echo csrf_field(); ?>
                            <?php echo method_field('DELETE'); ?>
                            <button type="submit" class="btn btn-outline-danger btn-sm">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </form>
                    </div>
                </li>
            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); if ($__empty_1): ?>
                <li class="list-group-item text-center text-muted">Không tìm thấy người dùng nào phù hợp.</li>
            <?php endif; ?>
        </ul>
    </div>
</div>


<?php if($users->hasPages()): ?>
    <div class="mt-3 d-flex justify-content-center">
        <?php echo e($users->appends(request()->query())->links()); ?>

    </div>
<?php endif; ?>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/users/index.blade.php ENDPATH**/ ?>