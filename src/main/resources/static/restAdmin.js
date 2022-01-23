function fillUsersTable() {
    fetch('http://localhost:8080/api/restUsers').then(
        response => {
            response.json().then(
                data => {
                    let temp ="";
                    data.forEach((u) => {
                        temp += "<tr >";
                        temp += "<td >"+ u.id + "</td>";
                        temp += "<td >"+ u.name + "</td>";
                        temp += "<td >"+ u.lastName + "</td>";
                        temp += "<td >"+ u.userName + "</td>";
                        temp += "<td >"+ u.password + "</td>";
                        temp += "<td >"+ u.rolesForTable + "</td>";
                        temp += "<td >" +
                            "    <a class='btn btn-info' role='button' onmouseover='fillEditModal(" + u.id + ")' data-toggle='modal' data-target='#editUser'>Edit</a>" +
                            "    </td>"
                        temp += "<td >" +
                            "    <a class='btn btn-danger' role='button' onmouseover='fillDeleteModal(" + u.id + ")' data-toggle='modal' data-target='#deleteUser'>Delete</a>" +
                            "    </td>"
                        temp += "</tr>";
                    })
                    $("#usersTableHere").empty();
                    $("#usersTableHere").append(temp);
                }
            )
        }
    )
}
function fillEditModal(userId) {
    $.get("http://localhost:8080/api/restUsers" + userId, function (userJSON) {
        $('#idToEdit').val(userJSON.id);
        $('#nameToEdit').val(userJSON.name);
        $('#lastNameToEdit').val(userJSON.lastName);
        $('#userNameToEdit').val(userJSON.userName);
        $('#passwordToEdit').val(userJSON.password);
        $('#roleToEdit').val(userJSON.rolesForTable);

    });
}
function fillDeleteModal(userId) {
    $.get("http://localhost:8080/api/restUsers/{id}" + userId, function (userJSON) {
        $('#idToDelete').val(userJSON.id);
        $('#nameToDelete').val(userJSON.name);
        $('#lastNameToDelete').val(userJSON.lastName);
        $('#userNameToDelete').val(userJSON.userName);
        $('#passwordToDelete').val(userJSON.password);
        $('#roleToDelete').val(userJSON.rolesForTable);
    });
}
function reloadNewUserTable(){

    $('#newName').val('');
    $('#newLastName').val('');
    $('#newUserName').val('');
    $('#newPassword').val('');
    $('#newRole').val('').map(role => role.value);

}


$(function () {
    $("#logout").append("<a class='custom-a' href='/logout'>Logout</a>");
    $('#addSubmit').on("click", function () {
        let userRoles = []
        console.log($('#addFormRoles').val())
        for(let i = 0; i < $('#addFormRoles').val().length; i++){
            userRoles.push({role:$('#addFormRoles').val()[i]})
        }
        let user = {
            name : $("#newName").val(),
            lastName : $("#newLastName").val(),
            userName : $("#newUserName").val(),
            password : $("#newPassword").val(),
            roles : userRoles
        };
        fetch('http://localhost:8080/api/restUsers', {
            method: "POST",
            credentials: 'same-origin',
            body: JSON.stringify(user),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        fillUsersTable();
        alert("Новый юзер добавлен")
        reloadNewUserTable();
        fillUsersTable()
    });
    $('#modalDeleteBtn').on("click", function () {
        fetch('http://localhost:8080/api/restUsers/{id}'+ $('#idToDelete').val(), {
            method: "DELETE",
            credentials: 'same-origin',
        });
        fillUsersTable();
        alert("Удален")
        fillUsersTable();
    });
    $('#modalEditBtn').on("click", function () {
        let checked = [];
        $('input:checkbox:checked').each(function () {
            checked.push($(this).val());
        });

        let user = {
            id : $('#idToEdit').val(),
            name : $("#nameToEdit").val(),
            lastName : $("#lastNameToEdit").val(),
            userName : $("#userNameToEdit").val(),
            password : $("#passwordToEdit").val(),
            roles : checked
        };
        fetch('http://localhost:8080/api/restUsers', {
            method: "PUT",
            body: JSON.stringify(user),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        fillUsersTable();
        alert("Отредактирован")
        fillUsersTable();
        reloadNewUserTable();
    });
});
fillUsersTable();