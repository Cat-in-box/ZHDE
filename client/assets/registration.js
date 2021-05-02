$(document).ready(function(){

	$("#b-registration").click(async function () {
        let varData = {
			"email": document.getElementById('email').value,
            "password": document.getElementById('password').value,
            "passport": document.getElementById('passport').value,
            "lastName": document.getElementById('last-name').value,
			"firstName": document.getElementById('first-name').value,
			"patronymic": document.getElementById('patronymic').value,
			"dateOfBirth": document.getElementById('date-of-birth').value,
			"phoneNumber": document.getElementById('phone-number').value
        };
        let response = await fetch("http://localhost:8080/clients/create", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(varData)
        }, {mode: 'cors'}).then(function (response) {

            if (response.status === 409) {
				alert("Пользователь с таким логином или паспортом уже существует!")
			} else if (response.status === 400) {
                alert("Введены некорректные данные")
            } else if (response.status === 200) {
                alert("Успешно!")

				window.location.replace("index.html");
                
            } else {
                alert("Пролучили код " + response.status)
            }

        }).catch(function (error) {
            console.log("Что-то пошло не так", error);
        });
    });
	
});
