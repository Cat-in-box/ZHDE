function servseCheck() {
	$.ajax({
		url: "http://localhost:8080/stations/getAllNames",
		type: "GET",
		dataType: "json",
		success: function (response){
			select = document.getElementById('stations-to');

			for (let i = 0; i<response.length; i++){
				let opt = document.createElement('option');
				opt.value = i;
				opt.innerHTML = response[i];
				select.appendChild(opt);

			}

		},
		error: function(error) {
			console.log("Что-то пошло не так", error);
		}
	});
}