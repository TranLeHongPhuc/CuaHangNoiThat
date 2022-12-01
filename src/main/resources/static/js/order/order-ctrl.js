const app = angular.module("order-app", [])
app.controller("order-ctrl", function($scope, $http){
	$scope.items = [];
	$scope.form = {};
	
	$scope.initialize = function(){
		$http.get("/api/orders").then(resp => {
			$scope.items = resp.data;
			console.log(this.items)
		});
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
		get totalQuantity(){
			return $scope.items.length;
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