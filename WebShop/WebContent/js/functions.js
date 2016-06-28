function register(isIndex,user){
	if(isIndex){
		user = {
			username: $("#registerForm input[name='username']").val(),
			password: $("#registerForm input[name='password']").val(),
			email: $("#registerForm input[name='email']").val(),
			name: $("#registerForm input[name='name']").val(),
			surname: $("#registerForm input[name='surname']").val(),
			address: $("#registerForm input[name='address']").val(),
			telephone: $("#registerForm input[name='telephone']").val(),
			country: $("#countrySelect").val(),
			type: 'buyer'
		};
	}
	$.ajax({
		type: 'POST',
		url: '/WebShop/rest/user/register',
			//contentType: 'application/json',
			data: user,
			dataType: 'text'
		})
	.done(function(data){
		if(data == "OK"){
			alert('User \'' + user.username + '\' has been successfuly regitered.')
		}else{
			alert('Registration has failed.');
		}
	})
	.fail(function(data, status){
		alert(status);
	});
	return false;
}

function login(){
	var user = {
		username: $("#loginForm input[name='username']").val(),
		password: $("#loginForm input[name='password']").val()
	};
	$.ajax({
		type: 'POST',
		url: '/WebShop/rest/user/login',
		data: user,
		dataType: 'text'
	})
	.done(function(data){
		if(data == "OK"){
			window.location = "home.html";
		}else{
				// Prikazi poruku o gresci
			}
		})
	.fail(function(data, status){
		alert(status);
	});
	return false;
}
