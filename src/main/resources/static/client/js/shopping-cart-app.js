const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http, $window, $log) {
	$scope.cart = {
		items: [],
		add(id) {
			var item = this.items.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToSessionStorage();
			} else {
				$http.get(`/api/products/${id}`).then(resp => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToSessionStorage();
				})
			}
		},
		
		remove(id){
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index,1);
			this.saveToSessionStorage();
		},
		
		clear(){
			this.items = [];
			this.saveToSessionStorage();
		},

		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},

		get amount() {
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},

		saveToSessionStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			sessionStorage.setItem("cart-tco", json);
		},
		
		loadFromSessionStorage(){
			var json = sessionStorage.getItem("cart-tco");
			this.items = json ? JSON.parse(json) : [];
		},

		// format giá tiền
		formatNumber(value) {
			return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		},

		// tính phí ship
		vat(){
			return 30000;
		},
	
	}

	$scope.cart.loadFromSessionStorage();
	$scope.order = {
		createDate: new Date(),
		address: "",
		account: { username: $("#username").val() },
		description: "",
		phone: "",
		
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			if ($scope.cart.count > 0) {
				var order = angular.copy(this);
				$http.post("/api/orders", order).then(resp => {
					alert("Đặt hàng thành công");
					$scope.cart.clear();
				}).catch(error => {
					console.log({ username: $("#username").val() })
					alert("Đặt hàng lỗi!")
					console.log(error)

				})
			}else{
				alert("Bạn chưa có sản phẩm trong giỏ hàng")
			}
		}
		
	};
	
	
	/*dang ky user*/////////////////////////////////////////////
	$scope.checkcode=null;
	$scope.checkUser;
	$scope.authority;
	$scope.user ={
		add(item){
			if(item.password == item.confirmpassword){
				item.fullname = item.firstname +" "+ item.lastname
				$http.post(`/api/accounts/register/${item.email}`,item).then(resp => {
					alert("Đăng ký tài khoản thành công ! Vui lòng kiểm tra mã code tại email "+item.email+"!")
					$scope.checkcode="yes"
					$scope.checkUser=resp.data;
					$scope.authority={
						id:null,
						account:$scope.checkUser,
						role:{id:'USER',name:'Users'}
					}
					$http.post(`/api/authority`,$scope.authority).then(resp => {
						
					}).catch(error => {
						alert("Thêm authority lỗi")
						console.log(error)
					})
				}).catch(error => {
					alert("dang ky lỗi!")
					console.log(error)
				})
			}else{
				alert("Xác nhận mật khẩu không đúng!")
			}
		},
		confirm(item){
			if(item!=null){
				if(item==$scope.checkUser.verificationCode){
					$http.put(`/api/accounts/${$scope.checkUser.email}`, $scope.checkUser).then(resp => {
				            
				            alert('Email kích hoạt thành công!');
				            var url = "http://" + $window.location.host + "/security/login";
					        $log.log(url);
					        $window.location.href = url;
									          
				        }).catch(error => {
				            alert('Email kích hoạt thất bại!')
				            console.log("Error", error)
				        })
				}else{
					alert("Mã verification Code không chính xác!")
				}
			}else{
				alert("Vui lòng nhập mã verification Code!")
			}
		}
	}
})