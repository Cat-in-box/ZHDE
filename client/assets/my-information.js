$(document).ready(function(){

	let user = getCookie("user-id");
	if (user == null) {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
		window.location.replace("index.html");
	}
	
	informationFill()
	function informationFill() {
		$.ajax({
			url: "http://localhost:8080/clients/info/" + user,
			type: "GET",
			dataType: "json",
			success: function (response){
				document.getElementById('email').textContent = response["email"];
				document.getElementById('password').textContent = response["userPassword"];
				document.getElementById('passport').placeholder = response["passport"];
				document.getElementById('last-name').placeholder = response["lastName"];
				document.getElementById('first-name').placeholder = response["firstName"];
				document.getElementById('patronymic').placeholder = response["patronymic"];
				document.getElementById('date-of-birth').placeholder = response["dateOfBirth"];
				document.getElementById('phone-number').placeholder = response["phoneNumber"];
			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	$("#b-change").click(async function () {
        let varData = {
            "passport": document.getElementById('passport').value,
            "lastName": document.getElementById('last-name').value,
			"firstName": document.getElementById('first-name').value,
			"patronymic": document.getElementById('patronymic').value,
			"dateOfBirth": document.getElementById('date-of-birth').value,
			"phoneNumber": document.getElementById('phone-number').value
        };
        let response = await fetch("http://localhost:8080/clients/update/" + user, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(varData)
        }, {mode: 'cors'}).then(function (response) {

            if (response.status === 400) {
                alert("Введены некорректные данные")
            } else if (response.status === 200) {
                alert("Ваши данные изменены!")
				document.getElementById('passport').value = "";
            	document.getElementById('last-name').value = "";
				document.getElementById('first-name').value = "";
				document.getElementById('patronymic').value = "";
				document.getElementById('date-of-birth').value = "";
				document.getElementById('phone-number').value = "";

				informationFill()
                
            } else {
                alert("Пролучили код " + response.status)
            }

        }).catch(function (error) {
            console.log("Что-то пошло не так", error);
        });
    });

	$("#b-exit").click(function () {
		eraseCookie("user-id");
		window.location.replace("index.html");
	});
	
});
