

<?php $__env->startSection('title', 'Chi tiết vi phạm'); ?>

<?php $__env->startSection('content'); ?>
<div class="p-4">
    <h3 class="text-center bg-primary text-white py-2 rounded">Chi tiết vi phạm</h3>

    <div class="border p-4 bg-white mt-3 rounded">
        <form action="<?php echo e(route('violations.update', $violation->id)); ?>" method="POST">
            <?php echo csrf_field(); ?>
            <?php echo method_field('PUT'); ?>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label fw-bold">Mã vi phạm:</label>
                    <input type="text" class="form-control" value="<?php echo e($violation->code); ?>" readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Loại vi phạm:</label>
                    <input type="text" class="form-control" value="<?php echo e($violation->type); ?>" readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Trạng thái xử lý:</label>
                    <select name="status" class="form-control">
                        <option value="Chưa xử lý" <?php echo e($violation->status == 'Chưa xử lý' ? 'selected' : ''); ?>>Chưa xử lý</option>
                        <option value="Đã xử lý" <?php echo e($violation->status == 'Đã xử lý' ? 'selected' : ''); ?>>Đã xử lý</option>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Họ tên người vi phạm:</label>
                    <input type="text" class="form-control" value="<?php echo e($violation->violator_name); ?>" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold">Vai trò:</label>
                    <input type="text" class="form-control" value="Tài xế" readonly>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Địa điểm xảy ra:</label>
                    <input type="text" class="form-control" value="<?php echo e($violation->location ?? 'Đại học Thủy Lợi'); ?>" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold">Ngày vi phạm:</label>
                    <input type="text" class="form-control" value="<?php echo e(\Carbon\Carbon::parse($violation->violation_date)->format('d/m/Y')); ?>" readonly>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Ảnh/Video, bằng chứng (nếu có):</label><br>
                <?php if($violation->evidence_url): ?>
                    <img src="<?php echo e(asset('storage/' . $violation->evidence_url)); ?>" style="max-width: 200px;" class="img-thumbnail">
                <?php else: ?>
                    <p>(Chưa có)</p>
                <?php endif; ?>
            </div>

            <div class="text-center mt-4">
                <a href="<?php echo e(route('violations.index')); ?>" class="btn btn-danger me-2">Hủy</a>
                <button type="submit" class="btn btn-success me-2">Lưu thay đổi</button>
            </div>
        </form>

        
        <div class="text-center mt-2">
            <form action="<?php echo e(route('violations.destroy', $violation->id)); ?>" method="POST" onsubmit="return confirm('Bạn có chắc muốn xoá vi phạm này?')" class="d-inline">
                <?php echo csrf_field(); ?>
                <?php echo method_field('DELETE'); ?>
                <button type="submit" class="btn btn-primary">Xóa</button>
            </form>
        </div>
    </div>
</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/violations/show.blade.php ENDPATH**/ ?>