const app = angular.module("category-ctrl", [])
app.controller("category-ctrl", function($scope, $http){
	$scope.imageChanged = function(files){
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/categories', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype:'multipart/form-data'
		})
	}
})