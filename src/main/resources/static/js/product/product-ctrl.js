const app = angular.module("product-app", [])
app.controller("product-ctrl", function($scope, $http){
	$scope.items = [];
	$scope.cates = [];
	$scope.subs = [];
	$scope.form = {};
	
	$scope.initialize = function(){
		$http.get("/api/products").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
		});
		
		$http.get("/api/categories").then(resp =>{
			$scope.cates = resp.data;
		});
		
		$http.get("/api/subcategories").then(resp =>{
			$scope.subs = resp.data;
		})
	}
	$scope.initialize();
	
	$scope.imageChanged = function(files){
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/products', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype:'multipart/form-data'
		})
	}
	
	$scope.productProperty = 'id';
	$scope.sort = function(){
		
	}
	
	$scope.totalQuantity = function(){
		return $scope.items.length;
	}
	
	$scope.currentPage = 0;
	$scope.pageSize = "10";
	
	$scope.numberOfPages = function(){
		return Math.ceil($scope.items.length / $scope.pageSize);
	}
	for(i = 0; i < 45; i++){
		$scope.items.push("Item " + i);
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
})