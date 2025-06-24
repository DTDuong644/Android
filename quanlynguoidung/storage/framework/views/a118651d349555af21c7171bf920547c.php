

<?php $__env->startSection('title', 'Thông tin cá nhân'); ?>

<?php $__env->startSection('content'); ?>
<div class="container-fluid py-4" style="max-width: 800px; margin: auto;">
    <div class="card border">
        <div class="card-header bg-primary text-white text-center fw-bold">
            Thông tin cá nhân
        </div>
        <div class="card-body">
            <div class="row">
                
                <div class="col-md-4 text-center">
                    <img src="https://via.placeholder.com/120x120.png?text=Avatar" alt="Avatar" class="rounded-circle img-fluid mb-2 border border-dark" style="width: 120px; height: 120px;">
                </div>

                
                <div class="col-md-8">
                    <div class="row mb-2">
                        <div class="col-6"><strong>Họ và tên:</strong> <?php echo e($user->name); ?></div>
                        <div class="col-6"><strong>Ngày sinh:</strong> <?php echo e($user->date_of_birth); ?></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Vai trò:</strong> <?php echo e($user->role); ?></div>
                        <div class="col-6"><strong>Số điện thoại:</strong> <?php echo e($user->phone_number); ?></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Email:</strong> <?php echo e($user->email); ?></div>
                        <div class="col-6"><strong>Giới tính:</strong> <?php echo e($user->gender); ?></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>CCCD:</strong> <?php echo e($user->cccd); ?></div>
                        <div class="col-6"><strong>Ngày tạo tài khoản:</strong> <?php echo e($user->created_at->format('d/m/Y')); ?></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6"><strong>Trạng thái:</strong>
                            <span class="badge <?php echo e($user->status === 'active' ? 'bg-success' : 'bg-danger'); ?>">
                                <?php echo e($user->status === 'active' ? 'Đang hoạt động' : 'Bị khóa'); ?>

                            </span>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4"><strong>Số chuyến hoàn thành:</strong> <?php echo e($user->completed_trips); ?></div>
                        <div class="col-4"><strong>Hủy chuyến:</strong> <?php echo e($user->canceled_trips); ?></div>
                        <div class="col-4"><strong>Lượt đánh giá:</strong> <?php echo e($user->ratings_count); ?></div>
                    </div>
                </div>
            </div>

            <hr>

            
            <div class="d-flex justify-content-center gap-3 mt-3">
                <form method="POST" action="<?php echo e(route('users.lock', $user->id)); ?>">
                    <?php echo csrf_field(); ?>
                    <?php echo method_field('PATCH'); ?>
                    <button type="submit" class="btn btn-danger">Khóa tài khoản</button>
                </form>

                <a href="<?php echo e(route('users.edit', $user->id)); ?>" class="btn btn-success">Cập nhật</a>
            </div>
        </div>
    </div>
</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/users/show.blade.php ENDPATH**/ ?>