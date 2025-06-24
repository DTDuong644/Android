

<?php $__env->startSection('title', 'Duyệt hồ sơ'); ?>

<?php $__env->startSection('content'); ?>
<div class="container mt-3">
    <div class="text-center mb-3">
        <h4 class="bg-primary text-white py-2 rounded">Thông tin duyệt hồ sơ</h4>
    </div>

    <div class="border p-4 bg-white rounded">
        <div class="row mb-3">
            <div class="col-md-6">
                <label>Họ và tên:</label>
                <input type="text" class="form-control" value="<?php echo e($application->user_name ?? ''); ?>" disabled>
            </div>
            <div class="col-md-3">
                <label>Ngày sinh:</label>
                <input type="text" class="form-control" value="<?php echo e($application->date_of_birth); ?>" disabled>
            </div>
            <div class="col-md-3">
                <label>CCCD:</label>
                <input type="text" class="form-control" value="<?php echo e($application->cccd); ?>" disabled>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6 text-center">
                <label>Ảnh chụp CCCD (mặt trước):</label><br>
                <img src="<?php echo e(asset('storage/' . $application->front_image)); ?>" class="img-thumbnail" width="200">
            </div>
            <div class="col-md-6 text-center">
                <label>Ảnh chụp CCCD (mặt sau):</label><br>
                <img src="<?php echo e(asset('storage/' . $application->back_image)); ?>" class="img-thumbnail" width="200">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>Số tài khoản ngân hàng:</label>
                <input type="text" class="form-control" value="<?php echo e($application->bank_account); ?>" disabled>
            </div>
            <div class="col-md-3">
                <label>Ngày gửi:</label>
                <input type="text" class="form-control" value="<?php echo e($application->submitted_at); ?>" disabled>
            </div>
            <div class="col-md-3">
                <label>Vai trò:</label>
                <input type="text" class="form-control" value="<?php echo e($application->role); ?>" disabled>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>Trạng thái:</label>
                <input type="text" class="form-control" value="<?php echo e($application->status); ?>" disabled>
            </div>
            <div class="col-md-6">
                <label>Email:</label>
                <input type="text" class="form-control" value="<?php echo e($application->email); ?>" disabled>
            </div>
        </div>

        <div class="text-center mt-4">
            <form action="<?php echo e(route('applications.update_status', $application->id)); ?>" method="POST" class="d-inline">
                <?php echo csrf_field(); ?>
                <button name="status" value="Chưa duyệt" class="btn btn-danger">Từ chối</button>
<button name="status" value="Đã duyệt" class="btn btn-success">Duyệt hồ sơ</button>
<button name="status" value="Chờ duyệt" class="btn btn-primary">Yêu cầu bổ sung</button>
            </form>
        </div>
    </div>
</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/applications/edit.blade.php ENDPATH**/ ?>