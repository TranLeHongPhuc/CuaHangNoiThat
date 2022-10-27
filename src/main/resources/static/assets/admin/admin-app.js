app = angular.module("admin-app",["ngRoute"])

app.config(function($routeProvider){
	$routeProvider
	.when("/product", {
		templateUrl: "/assets/admin/product/index.html",
		controller: "product-ctrl"
	})
	.when("/users", {
		templateUrl: "/assets/admin/users/index.html",
		controller: "users-ctrl"
	})
	.when("/orders", {
		templateUrl: "/assets/admin/orders/index.html",
		controller: "orders-ctrl"
	})
	.when("/categories", {
		templateUrl: "/assets/admin/categories/index.html",
		controller: "categories-ctrl"
	})
	.when("/subcategories", {
		templateUrl: "/assets/admin/subcategories/index.html",
		controller: "subcategories-ctrl"
	})
	.when("/authority", {
		templateUrl: "/assets/admin/authority/index.html",
		controller: "authority-ctrl"
	})
	.when("/unauthorized",{
		templateUrl:"/assets/admin/authority/unauthorized.html",
		controller:"authority-ctrl"
	})
	.when("/unauthorized",{
		templateUrl: "/assets/admin/authority/unauthorized.html",
		controller: "authority-ctrl"
	})
	.otherwise({
		//template: "<h1 class='text center'> FPT Polytechnic Administration </h1>"
	})
})