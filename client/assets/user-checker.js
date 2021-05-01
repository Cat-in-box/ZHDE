function UserCheck(){
	try {
		let user = getCookie("user-id");
		alert(user);
		if (user == null) {
			alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
			window.location.replace("index.html");
		}
	} catch {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ 1");
		window.location.replace("index.html");
	}
}