

<?php $__env->startSection('title', 'Chỉnh sửa thông tin'); ?>

<?php $__env->startSection('content'); ?>
<div class="container py-4" style="max-width: 800px; margin: auto;">
    <h3 class="text-center mb-4">Cập nhật thông tin cá nhân</h3>

    
    <?php if($errors->any()): ?>
        <div class="alert alert-danger">
            <ul class="mb-0">
                <?php $__currentLoopData = $errors->all(); $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $error): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                    <li><?php echo e($error); ?></li>
                <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
            </ul>
        </div>
    <?php endif; ?>

    <form method="POST" action="<?php echo e(route('users.update', $user->id)); ?>">
        <?php echo csrf_field(); ?>
        <?php echo method_field('PUT'); ?>

        <div class="mb-3">
            <label class="form-label fw-bold">Họ và tên</label>
            <input type="text" name="name" class="form-control" value="<?php echo e(old('name', $user->name)); ?>">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Email</label>
            <input type="email" name="email" class="form-control" value="<?php echo e(old('email', $user->email)); ?>">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Số điện thoại</label>
            <input type="text" name="phone_number" class="form-control" value="<?php echo e(old('phone_number', $user->phone_number)); ?>">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Ngày sinh</label>
            <input type="date" name="date_of_birth" class="form-control"
                   value="<?php echo e(old('date_of_birth', optional($user->date_of_birth)->format('Y-m-d'))); ?>">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Giới tính</label>
            <select name="gender" class="form-select">
                <?php $gender = old('gender', $user->gender); ?>
                <option value="Nam" <?php echo e($gender == 'Nam' ? 'selected' : ''); ?>>Nam</option>
                <option value="Nữ" <?php echo e($gender == 'Nữ' ? 'selected' : ''); ?>>Nữ</option>
                <option value="Khác" <?php echo e($gender == 'Khác' ? 'selected' : ''); ?>>Khác</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Vai trò</label>
            <input type="text" name="role" class="form-control" value="<?php echo e(old('role', $user->role)); ?>">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Trạng thái</label>
            <?php $status = old('status', $user->status); ?>
            <select name="status" class="form-select">
                <option value="active" <?php echo e($status == 'active' ? 'selected' : ''); ?>>Đang hoạt động</option>
                <option value="locked" <?php echo e($status == 'locked' ? 'selected' : ''); ?>>Bị khóa</option>
            </select>
        </div>

        <div class="d-flex justify-content-end">
            <a href="<?php echo e(route('users.show', $user->id)); ?>" class="btn btn-secondary me-2">Hủy</a>
            <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        </div>
    </form>
</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/users/edit.blade.php ENDPATH**/ ?>