

<?php $__env->startSection('title', 'Đánh giá chuyến đi'); ?>

<?php $__env->startSection('content'); ?>
    
    <div class="input-group mb-3">
        <form action="<?php echo e(route('ratings.index')); ?>" method="GET" class="w-100 d-flex">
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo mã đánh giá, ngày đánh giá, ..." value="<?php echo e(request('search')); ?>">
            <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <div class="bg-light p-2 mb-3 rounded"><strong>Danh sách bạn tìm kiếm</strong></div>

    
    <?php $__currentLoopData = $ratings; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $index => $rating): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
        <div class="border p-3 mb-2 bg-white shadow-sm rounded">
            <strong><?php echo e($index + 1); ?>. Mã đánh giá: <?php echo e($rating->id); ?></strong><br>
            - Hành khách: <?php echo e($rating->passenger_name); ?><br>
            - Tài xế: <?php echo e($rating->driver_name); ?><br>
            - Số sao:
            <?php for($i = 1; $i <= $rating->stars; $i++): ?>
                <i class="fas fa-star text-warning"></i>
            <?php endfor; ?>
            <?php for($i = $rating->stars + 1; $i <= 5; $i++): ?>
                <i class="far fa-star text-muted"></i>
            <?php endfor; ?>
            <br>
            - Nội dung đánh giá: <?php echo e($rating->comment); ?>


            
            <div class="mt-2">
                <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#ratingModal<?php echo e($rating->id); ?>">
                    <i class="fas fa-eye"></i> Xem chi tiết
                </button>
            </div>
        </div>

        
        <div class="modal fade" id="ratingModal<?php echo e($rating->id); ?>" tabindex="-1" aria-labelledby="ratingModalLabel<?php echo e($rating->id); ?>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ratingModalLabel<?php echo e($rating->id); ?>">Chi tiết đánh giá #<?php echo e($rating->id); ?></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Mã đánh giá:</strong> <?php echo e($rating->code ?? 'Không có'); ?></p>
                        <p><strong>Hành khách:</strong> <?php echo e($rating->passenger_name); ?></p>
                        <p><strong>Tài xế:</strong> <?php echo e($rating->driver_name); ?></p>
                        <p><strong>Số sao:</strong>
                            <?php for($i = 1; $i <= $rating->stars; $i++): ?>
                                <i class="fas fa-star text-warning"></i>
                            <?php endfor; ?>
                            <?php for($i = $rating->stars + 1; $i <= 5; $i++): ?>
                                <i class="far fa-star text-muted"></i>
                            <?php endfor; ?>
                        </p>
                        <p><strong>Nội dung:</strong> <?php echo e($rating->comment); ?></p>
                        <p><strong>Ngày tạo:</strong> <?php echo e($rating->created_at->format('d/m/Y H:i')); ?></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>

    <?php if($ratings->isEmpty()): ?>
        <div class="text-muted">Không có đánh giá nào.</div>
    <?php endif; ?>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('app', array_diff_key(get_defined_vars(), ['__data' => 1, '__path' => 1]))->render(); ?><?php /**PATH C:\Laravel\quanlynguoidung\resources\views/ratings/index.blade.php ENDPATH**/ ?>