/**
 * 
 */
// MỞ THÔNG TIN THÊM KHI NHẤN VÀO ẢNH AVT
      var openAvt = document.getElementById("open-profile");
      var moreProfile = document.querySelector(".acc_more");
      openAvt.addEventListener("click", function(e) {
		  console.log("Đã click")
		  
        if (moreProfile.style.display == 'none') {
			moreProfile.style.display = 'none';
         	moreNotify.style.display = 'none';
        } else {
          moreProfile.style.display = 'block';
          moreNotify.style.display = 'none';
        }
        e.preventDefault();
      });
      
      // MỞ THÔNG BÁO
      var openNotify = document.getElementById("open-notify");
      var moreNotify = document.querySelector(".notify_more");
      openNotify.addEventListener("click", function(e) {
        e.preventDefault();
        if (moreNotify.style.display == 'none') {
          moreNotify.style.display = 'block';
          moreProfile.style.display = 'none';
        } else {
          moreProfile.style.display = 'none';
          moreNotify.style.display = 'none';
        }
      });
      
      // CHUYỂN ẢNH GIỮA CÁC STORY
      document.addEventListener('DOMContentLoaded', function() {
        const moveLeftButton = document.getElementById('moveLeftButton');
        const moveRightButton = document.getElementById('moveRightButton');
        const imageList = document.querySelector('.story');
        const images = document.querySelectorAll('.story img');
        const imageWidth = images[0].offsetWidth + 10; // Kích thước ảnh + margin
        let currentIndex = 0;

        moveLeftButton.addEventListener('click', function() {
          if (currentIndex > 0) {
              currentIndex--;
              imageList.style.transform = `translateX(-${currentIndex * imageWidth}px)`;
          }
      });
    
        moveRightButton.addEventListener('click', function() {
            if (currentIndex < images.length - 4) { // Số lượng ảnh hiển thị trong khung là 4
                currentIndex++;
                imageList.style.transform = `translateX(-${currentIndex * imageWidth}px)`;
            }
        });
    });

      