$(async function () {
    await getTableWithUsers();
    await addNewUser();
    await editUser();
    await deleteUser();
})

const usersFetchService = {
    head:  {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

findAllUsers: async () => await fetch('api/restUsers'),
addUsers: async (user) => await fetch('api/restUsers', {method: 'POST', headers: usersFetchService.head, body: JSON.stringify(user)}),
findOneUser: async (id) => await fetch('api/restUsers/{id}'),
editUsers: async (user) => await fetch('api/restUsers', {method: 'PUT', headers: usersFetchService.head, body: JSON.stringify(user)}),
deleteUsers: async (id) => await fetch('api/restUsers/{id}', {method: 'DELETE', headers: usersFetchService.head}),

}
async function getTableWithUsers() {
    let table = $('#tableWithUsers tbody');
    table.empty();

    await usersFetchService.findAllUsers()
        .then(res => res.json())
        .then(users =>{
            users.forEach(user => {
                let tableFill = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.userName}</td>
                    <td>${user.password}</td>
                    <td>${user.rolesForTable}</td>
                    <td>
                        <a type="button" onmouseover="editUser("${user.id}")" data-action="edit" class="btn btn-info"
                        data-toggle="modal" data-target="#someDefaultModal">Edit</a>
                    </td>
                    <td>
                         <a type="button" onmouseover="deleteUser("${user.id}")" data-action="delete" class="btn btn-danger"
                        data-toggle="modal" data-target="someDefaultModal">Delete</a>
                    </td>
                </tr>
                    
                )`;
                table.append(tableFill);
            })
        })
    $('#tableWithUsers').find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');
        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-userid');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-userid', buttonAction);
        defaultModal.modal('show');
    })
}

async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action){
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function addNewUser() {
    $('#addNewUser').click(async () => {
        let addUserForm = $('#defaultSomeForm')
        let name = addUserForm.find('#AddNewUserName').val().trim();
        let lastName = addUserForm.find('#AddNewLastName').val().trim();
        let userName = addUserForm.find('#AddNewUserUserName').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let roles = addUserForm.find('#AddNewUserRoles').val().trim();
        let data = {
            id: id,
            name: name,
            lastName : lastName ,
            userName: userName,
            password: password,
            roles: roles
        }
        const response = await userFetchService.addNewUser(data);
        if (response.ok) {
            getTableWithUsers();
            addUserForm.find('#AddNewUserName').val('');
            addUserForm.find('#AddNewLastName').val('');
            addUserForm.find('#AddNewUserUserName').val('');
            addUserForm.find('#AddNewUserPassword').val('');
            addUserForm.find('#AddNewUserRoles').val('');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }

    })
}

async function editUser(modal, id) {
    let preuser = await usersFetchService.findOneUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button class="btn btn-outline-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`;
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
        <form class="form-group" id="editUser">
            <input type="text" class="form-control" id="id1" name="id" value="${user.id}" disabled><br>
            <input class="form-control" type="text" id="name1" value="${user.name}"><br>
            <input class="form-control" type="text" id="lastName1" value="${user.lastName}"><br>
            <input class="form-control" type="text" id="userName1" value="${user.userName}"><br>
            <input class="form-control" type="text" id="password1" value="${user.password}"><br>
            <input class="form-control" type="text" id="roles1"  value="${user.rolesForTable}"><br>
        </form>`;
        modal.find('.modal-body').append(bodyForm);
    })
    $("#editButton").on('click', async () => {
        let id = modal.find("#id1").val().trim();
        let name = modal.find("#name1").val().trim();
        let lastName = modal.find("#lastName1").val().trim();
        let userName = modal.find("#userName1").val().trim();
        let password = modal.find("#password1").val().trim();
        let roles = modal.find("#roles1").val().trim();
        let data = {
            id: id,
            name: name,
            lastName : lastName ,
            userName: userName,
            password: password,
            roles: roles
        }
        const response = await usersFetchService.editUsers(data, id);

        if(response.ok) {
        await getTableWithUsers();
        modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

async function deleteUser(modal, id) {
    await usersFetchService.deleteUsers(id);
    await getTableWithUsers();
    modal.find('.modal-title').html('');
    modal.find('.modal-body').html('User was deleted');
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal"> Close</button>`
    modal.find('.modal-footer').append(closeButton);

}


