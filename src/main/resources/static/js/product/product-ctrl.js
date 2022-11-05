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