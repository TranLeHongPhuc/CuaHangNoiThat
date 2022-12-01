const app = angular.module("subcategory-app",[])
app.controller("subcategory-ctrl", function($scope, $http){
	
	$scope.users =[];
	$scope.form = {};
	
	
	$scope.initialize = function(){
		$http.get('/api/accounts').then(resp => {
			$scope.users = resp.data;
		});
		
	}
	
	
	$scope.initialize();
	
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/save/users', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		}).then(resp => {
			$scope.form.photo = resp.data.name;
		}).catch(error =>{
			alert("Lỗi upload hình ảnh!");
			console.log("Error", error);
		}),
		$http.post('/api/image/users', data, {
			transformRequest: angular.identity,
			headers:{'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
	
	$scope.reset = function(){
		
	}
		
	$scope.edit = function(user){
		$scope.form = angular.copy(user);
		document.getElementById('username').readOnly = true;
		document.getElementById('email').readOnly = true;
	}
	
	$scope.update = function() {
		var sub = angular.copy($scope.form);
		$http.put(`/api/accounts/${sub.username}`, sub).then(resp => {
			let index = $scope.users.findIndex(u => u.username == sub.username);
			$scope.users[index] = sub;
			alert("Cập nhật thành công!");
		}).catch(error => {
			alert("Lỗi cập nhật !");
			console.log("Error", error);
		});
	}
	
	$scope.sortProperty = 'username';
	
	$scope.sort = function(){
		
	}
	
	$scope.currentPage = 0;
	$scope.pageSize = "3";
	
	$scope.totalQuantity = function(){
		return $scope.users.length;
	}
	
	$scope.numberOfPages = function(){
		return Math.ceil($scope.users.length / $scope.pageSize);
	}
	for(let i = 0; i < 45; i++){
		$scope.users.push("Item " + i);
	}
	
	$scope.pagination = function(){
		$scope.currentPage = 0;
	}
	
})
app.filter('startFrom', function(){
		return function(input, start){
			start = +start;
			return input.slice(start);
		}
});
