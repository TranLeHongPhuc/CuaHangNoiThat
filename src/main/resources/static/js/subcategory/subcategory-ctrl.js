const app = angular.module("subcategory-app",[])
app.controller("subcategory-ctrl", function($scope, $http){
	$scope.subs =[];
	$scope.form = {};
	$scope.cates = [];
	
	$scope.initialize = function(){
		$http.get('/api/subcategories').then(resp => {
			$scope.subs = resp.data;
		});
		$http.get('/api/categories').then(resp => {
			$scope.cates = resp.data;
		});
	}
	
	
	$scope.initialize();
	
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/save/subcategories', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		}).then(resp => {
			$scope.form.icon = resp.data.name;
		}).catch(error =>{
			alert("Lỗi upload hình ảnh!");
			console.log("Error", error);
		}),
		$http.post('/api/image/subcategories', data, {
			transformRequest: angular.identity,
			headers:{'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
	
	$scope.reset = function(){
		$scope.form = {
			icon : 'default-subcategory.png'
		}
	}
		
	$scope.edit = function(sub){
		$scope.form = angular.copy(sub);
	}
	
	$scope.create = function(){
		let sub  = angular.copy($scope.form);
		let subId = sub['id'];
		let removeSpaceSubId = subId.replaceAll(" ", "");
		let removeSpecialCharacterSubId = removeSpaceSubId.replace(/[^\w\s]/gi, '');
		sub['id'] = removeSpecialCharacterSubId;
		var exist = false;
		$scope.subs.forEach(function (subcategory) {
			let id = subcategory['id'];
			if(id == sub['id']) {
				exist = true;
			}
		});
		if(exist == true) {
			alert("Mã thương hiệu đã tồn tại!");
		}else {
			$http.post("/api/subcategories", sub).then(resp => {
				$scope.subs.push(resp.data);
				alert("Thêm thương hiệu thành công!");
				$scope.reset();
			}).catch(error =>{
				alert("Lỗi thêm mới thương hiệu!");
				console.log("Error", error);
				
			});	
		}
			
				
	}
	
	$scope.update = function(){
		let sub = angular.copy($scope.form);
		$http.put(`/api/subcategories/${sub.id}`, sub).then(resp =>{
			let index = $scope.subs.findIndex(s => s.id == sub.id);
			$scope.subs[index] = sub;
			alert("Cập nhật thương hiệu thành công!");
			$scope.reset();
		}).catch(error =>{
			alert("Lỗi cập nhật thương hiệu");
			console.log("Error", error);
		});
	}
	
	$scope.delete = function(sub){
		$http.delete(`/api/subcategories/${sub.id}`).then(resp =>{
			let index = $scope.subs.findIndex(s => s.id == sub.id);
			$scope.subs.splice(index, 1);
			alert("Xóa thương hiệu thành công!")
		}).catch(error => {
			alert("Lỗi xóa thương hiệu!");
			console.log("Error", error);
		});
	}
	
	$scope.pager = {
		page: 0,
		size: 5,
		get subs() {
			var start = this.page * this.size;
			return $scope.subs.slice(start, start + this.size);
		},
		
		get count() {
			return Math.ceil(1.0 * $scope.subs.length /this.size);
		},
		get totalQuantity(){
			return $scope.subs.length;
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
	
})