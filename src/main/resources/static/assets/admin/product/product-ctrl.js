app.controller("product-ctrl", function($scope, $http){
	
	$scope.items = []; /* Hiển thị sản phẩm trên form */
	$scope.cates = []; /* hiển thị category trên form */ 
	$scope.form = {}; /* đối tượng trong scope để hiển thị lên form */
	
	/* Tải thông tin sản phẩm từ CSDL về */
	$scope.initialize = function() {
		$http.get("/rest/products").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
		});
		/*Đổ dữ liệu vào combobox - tải category về*/
		$http.get("/rest/categories").then(resp => {
			$scope.cates = resp.data;
		});
	}
	
	/* Thực hiện khởi động load form */
	$scope.initialize();
	
		/* Hiển thị lên Form */
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
	}
	
		/* Làm mới Form */
	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
			image:"cloud-upload.jpg",
			available: true
		};
	}
	
		/* Thêm sản phẩm */
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/products`, item).then(resp => {
			resp.data.createDate = new Date(resp.data.createDate)
			$scope.items.push(resp.data); /* Thêm mới thì lưu vô danh sách */
			$scope.reset();				/* sau khi post xóa sạch form */
			alert("Thêm sản phẩm mới thành công");
		}).catch(error => {
			alert("Thêm sản phẩm mới thất bại !!");
			console.log("Error", error);
		});
	}
	
		/* Cập nhật sản phẩm */
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id ==item.id);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công");
		}).catch(error => {
			alert("Cập nhật sản phẩm thất bại !!");
			console.log("Error", error);
		});
	}
		/* Xóa sản phẩm */
	$scope.delete = function(item) {
		$http.delete(`/rest/products/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id ==item.id);
			// xóa phần tử trong mảng trong js dùng splice
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa sản phẩm thành công");
		}).catch(error => {
			alert("Xóa sản phẩm thất bại !!");
			console.log("Error", error);
		});
	}
		/* Tải hình ảnh mình họa của sản phẩm */
	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Upload lỗi không thành công !")
			console.log("Error", error)
		})
	}
	
	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		
		get count() {
			return Math.ceil(1.0 * $scope.items.length /this.size);
		},
		
		first(){
			this.page=0;
		},
		prev(){
			this.page --;
			if(this.page<0){
				this.last();
			}
		},
		next() {
			this.page ++;
			if(this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}
});