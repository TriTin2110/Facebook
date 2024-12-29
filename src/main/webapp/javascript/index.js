// MỞ THÔNG TIN THÊM KHI NHẤN VÀO ẢNH AVT
		var open = document.getElementById("open-profile");
		var moreProfile = document.querySelector(".acc_more");
		open.onclick = function(e) {
			e.preventDefault();
			if (moreProfile.style.display == 'none') {
				moreProfile.style.display = 'block';
			} else {
				moreProfile.style.display = 'none';
			}
		}

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
	              imageList.style.transform = 'translateX(-${currentIndex * imageWidth}px)';
	          }
	      });
	    
	        moveRightButton.addEventListener('click', function() {
	            if (currentIndex < images.length - 4) { // Số lượng ảnh hiển thị trong khung là 4
	                currentIndex++;
	                imageList.style.transform = 'translateX(-${currentIndex * imageWidth}px)';
	            }
	        });
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
	              imageList.style.transform = "translateX(-" + (currentIndex * imageWidth) + "px)";
	          }
	      });
	    
	        moveRightButton.addEventListener('click', function() {
	            if (currentIndex < images.length - 4) { // Số lượng ảnh hiển thị trong khung là 4
	                currentIndex++;
	                imageList.style.transform = "translateX(-" + (currentIndex * imageWidth) + "px)";
	            }
	        });
	    });
	    // RÊ CHUỘT VÀO NÚT LIKE SẼ HIỂN THỊ THÊM CÁC CẢM XÚC KHÁC
	  document.addEventListener('DOMContentLoaded', function() {
	    const postContents = document.querySelectorAll('.post_content');
	    postContents.forEach(function(postContent) {
	      const likeButton = postContent.querySelector('.like-button');
	      const iconLike = postContent.querySelector('.icon_like');
	      const emotion = postContent.querySelector('.emotions');
	      const emotionIcons = emotion.querySelectorAll('.emotion');
	      const numberReact = postContent.querySelector('.number_react a span');
	      let isBlue = false;
	      let selectedEmotion = null;

	      likeButton.addEventListener('click', function() {
	        if (selectedEmotion) {
	          // Nếu đã chọn cảm xúc khác, hủy bỏ cảm xúc đó
	          selectedEmotion = null;
	          iconLike.style.display = 'inline-block';
	          likeButton.innerHTML = 'Thích';
	          likeButton.style.color = '#65676b';
	          iconLike.style.color = '#65676b';
	          numberReact.innerText = parseInt(numberReact.innerText) - 1;
	          isBlue = false;
	        } else if (isBlue) {
	          // Nếu đang thích, hủy thích
	          // likeButton.innerHTML = '<i class="fa-solid fa-thumbs-up"></i> Thích';
	          iconLike.style.display = 'inline-block';
	          likeButton.style.color = '#65676b';
	          iconLike.style.color = '#65676b';
	          numberReact.innerText = parseInt(numberReact.innerText) - 1;
	          isBlue = false;
	        } else {
	          // Thích
	          // likeButton.innerHTML = '<i class="fa-solid fa-thumbs-up"></i> Thích';
	          // iconLike.style.display = 'inline-block';
	          likeButton.style.color = '#0866ff';
	          iconLike.style.color = '#0866ff';
	          numberReact.innerText = parseInt(numberReact.innerText) + 1;
	          isBlue = true;
	        }
	      });

	      likeButton.addEventListener('mouseover', function() {
	        emotion.style.display = 'flex';
	      });

	      emotion.addEventListener('mouseover', function() {
	        emotion.style.display = 'flex';
	      });

	      likeButton.addEventListener('mouseout', function() {
	        emotion.style.display = 'none';
	      });

	      emotion.addEventListener('mouseout', function() {
	        emotion.style.display = 'none';
	      });

	      emotionIcons.forEach(emotionIcon => {
	        emotionIcon.addEventListener('click', function() {
	          const getData = this.getAttribute('data-emotion');
	          const getEmotionIcon = this.outerHTML;

	          if (selectedEmotion) {
	            // Nếu đã chọn cảm xúc khác, hủy bỏ cảm xúc đó
	            selectedEmotion = null;
	            numberReact.innerText = parseInt(numberReact.innerText) - 1;
	          } else if (isBlue) {
	            // Nếu đang thích, hủy thích
	            isBlue = false;
	            numberReact.innerText = parseInt(numberReact.innerText) - 1;
	          }

	          // Chọn cảm xúc mới
	          likeButton.innerHTML = getEmotionIcon + ' ' + getData;
	          numberReact.innerText = parseInt(numberReact.innerText) + 1;
	          selectedEmotion = getData;
	          iconLike.style.display = 'none';
	          emotion.style.display = 'none';
	        });
	      });
	    });
	  });
	    // ĐĂNG BÀI VIẾT
	    document.getElementById("postButton").addEventListener("click", function() {
	      var content = document.getElementById("content_area").value;
	      var fileInput = document.querySelector('input[type="file"]');
	      var formData = new FormData();
	      formData.append("content", content);
	      formData.append("image", fileInput.files[0]);
	  
	      fetch("/SummerProject/post", { // Đường dẫn đầy đủ tới servlet
	          method: "POST",
	          body: formData
	      })
	      .then(response => response.json())
	      .then(data => {
	          if (data && data.postContent && data.postImage) {
	              var postHtml = `
	                  <div class="post_content">
	                      <div class="header_post">
	                          <img src="../img/avt.jpg" alt="">
	                          <div class="name-acc_post">
	                              <h4>Chí Nguyên</h4>
	                              <span>Just now</span>
	                          </div>
	                      </div>
	                      <div class="caption_post">
	                          <span>${data.postContent}</span>
	                      </div>
	                      <div class="content_post">
	                          <img src="${data.postImage}" alt="">
	                      </div>
	                      <div class="interact_post">
	                          <div class="number_react">
	                              <i class="fa-solid fa-thumbs-up color_like"></i>
	                              <i class="fa-solid fa-heart color_heart"></i>
	                              <a href="#"> <span>0</span> </a>
	                          </div>
	                          <div class="cmt-share">
	                              <a href="#">
	                                  <span>0</span>
	                                  <i class="fa-solid fa-comment color_cmt"></i>
	                                  <span>0</span>
	                                  <i class="fa-solid fa-share color_cmt"></i>
	                              </a>
	                          </div>
	                      </div>
	                      <div class="interact_btn">
	                          <ul>
	                              <li>
	                                  <i class="fa-solid fa-thumbs-up"></i>
	                                  <h4>Thích</h4>
	                              </li>
	                              <li>
	                                  <i class="fa-solid fa-comment"></i>
	                                  <h4>Bình luận</h4>
	                              </li>
	                              <li>
	                                  <i class="fa-solid fa-paper-plane"></i>
	                                  <h4>Gửi</h4>
	                              </li>
	                              <li>
	                                  <i class="fa-solid fa-share"></i>
	                                  <h4>Chia sẻ</h4>
	                              </li>
	                          </ul>
	                      </div>
	                  </div>
	              `;
	  
	              var postContainer = document.querySelector(".post");
	              postContainer.insertAdjacentHTML('afterbegin', postHtml);
	  
	              document.getElementById("content_area").value = '';
	              fileInput.value = '';
	              $('#myModal').modal('hide');
	          } else {
	              console.error("Invalid response data", data);
	          }
	      })
	      .catch(error => {
	          console.error("Error:", error);
	      });
	  });
console.log('Form chưa được submit');
