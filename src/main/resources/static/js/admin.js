const editModal = new bootstrap.Modal(document.getElementById('editModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const successToast = new bootstrap.Toast(document.getElementById('successToast'));

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

document.addEventListener('DOMContentLoaded', function () {
    loadUsers();

    document.getElementById('editUserForm')
        .addEventListener('submit', function (e) {
            e.preventDefault();
            updateUser();
        });

    document.getElementById('deleteUserForm')
        .addEventListener('submit', function (e) {
            e.preventDefault();
            deleteUser();
        });

    document.getElementById('addUserForm')
        .addEventListener('submit', function (e) {
            e.preventDefault();
            addUser();
        });

    document.getElementById('add-user-tab')
        .addEventListener('click', function (e) {
            const roleSelect = document.getElementById('beingAddedUserRoles');
            fillRoleSelect(roleSelect);
            e.preventDefault();
        })

})

function loadUsers() {
    fetch('/api/admin/user_list')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(user_data_list => {
            const tableBody = document.getElementById('userListTableBody');
            tableBody.innerHTML = '';

            user_data_list.forEach(user => {
                const userRow = tableBody.appendChild(document.createElement('tr'));
                userRow.dataset.userId = user.id;

                userRow.appendChild(document.createElement('td')).textContent = user.id;
                userRow.appendChild(document.createElement('td')).textContent = user.firstname;
                userRow.appendChild(document.createElement('td')).textContent = user.lastname;
                userRow.appendChild(document.createElement('td')).textContent = user.age;
                userRow.appendChild(document.createElement('td')).textContent = user.username;
                userRow.appendChild(document.createElement('td')).textContent = user.roles.map(role => role.name).join(', ');

                const editBtn = userRow
                    .appendChild(document.createElement('td'))
                    .appendChild(document.createElement('button'));
                editBtn.className = "btn btn-success";
                editBtn.textContent = "Edit";
                editBtn.dataset.bsToggle = "modal";
                editBtn.dataset.bsTarget = "#editModal";
                editBtn.addEventListener('click', () => getEditModalWindow(user.id));

                const deleteBtn = userRow
                    .appendChild(document.createElement('td'))
                    .appendChild(document.createElement('button'));
                deleteBtn.className = "btn btn-danger";
                deleteBtn.textContent = "Delete";
                deleteBtn.dataset.bsToggle = "modal";
                deleteBtn.dataset.bsTarget = "#deleteModal";
                deleteBtn.addEventListener('click', () => getDeleteModalWindow(user.id));
            });
        })
        .catch(error => {
            console.error('Error loading users:', error);
            showToast('Error loading users', false);
        });
}

function getDeleteModalWindow(userId) {
    fetch(`/api/admin/user_data/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error('User not found');
            return response.json();
        })
        .then(user => {
            document.getElementById('beingDeletedUserId').value = user.id;
            document.getElementById('beingDeletedUserFirstname').value = user.firstname;
            document.getElementById('beingDeletedUserLastname').value = user.lastname;
            document.getElementById('beingDeletedUserAge').value = user.age;
            document.getElementById('beingDeletedUserUsername').value = user.username;

            const roleSelect = document.getElementById('beingDeletedUserRoles');
            fillRoleSelect(roleSelect, user);
        })
        .catch(error => {
            console.error('Error loading user:', error);
            showToast('Error loading user data', false);
            deleteModal.hide();
    });
}

function getEditModalWindow(userId) {
    fetch(`/api/admin/user_data/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error('User not found');
            return response.json();
        })
        .then(user => {
            document.getElementById('beingChangedUserId').value = user.id;
            document.getElementById('beingChangedUserFirstname').value = user.firstname;
            document.getElementById('beingChangedUserLastname').value = user.lastname;
            document.getElementById('beingChangedUserAge').value = user.age;
            document.getElementById('beingChangedUserUsername').value = user.username;

            const roleSelect = document.getElementById('beingChangedUserRoles');
            fillRoleSelect(roleSelect, user);
        })
        .catch(error => {
            console.error('Error loading user:', error);
            showToast('Error loading user data', false);
            editModal.hide();
        });
}

function addUser() {
    const addButton = document.getElementById('addButton');
    const addButtonText = document.getElementById('addButtonText');
    const addButtonSpinner = document.getElementById('addButtonSpinner');

    addButton.disabled = true;
    addButtonText.classList.add('d-none');
    addButtonSpinner.classList.remove('d-none');

    const beingAddedUser = {
        firstname: document.getElementById('beingAddedUserFirstname').value,
        lastname: document.getElementById('beingAddedUserLastname').value,
        age: document.getElementById('beingAddedUserAge').value,
        username: document.getElementById('beingAddedUserUsername').value,
        password: document.getElementById('beingAddedUserPassword').value,
        roles: Array.from(document.getElementById('beingAddedUserRoles').selectedOptions)
            .map(option => ({ id: option.value, name: option.text }))
    };

    fetch('/api/admin/add_user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(beingAddedUser)
    })
        .then(response => {
            if (!response.ok) throw new Error('Save failed');
            return response.json();
        })
        .then(() => {
            showToast('User added successfully');
            loadUsers();
        })
        .catch(error => {
            console.error('Error adding user:', error);
            showToast('Error adding user', false);
        })
        .finally(() => {
            // Скрыть спиннер загрузки
            addButton.disabled = false;
            addButtonText.classList.remove('d-none');
            addButtonSpinner.classList.add('d-none');
        });
}

function deleteUser() {
    const deleteButton = document.getElementById('deleteButton');
    const deleteButtonText = document.getElementById('deleteButtonText');
    const deleteButtonSpinner = document.getElementById('deleteButtonSpinner');

    deleteButton.disabled = true;
    deleteButtonText.classList.add('d-none');
    deleteButtonSpinner.classList.remove('d-none');

    const beingDeletedUser = {
        id: document.getElementById('beingDeletedUserId').value
    };

    fetch('/api/admin/delete_user', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(beingDeletedUser)
    })
        .then(response => {
            if (!response.ok) throw new Error('Delete failed');
        })
        .then(() => {
            deleteModal.hide();
            showToast('User delete successfully');
            loadUsers();
        })
        .catch(error => {
            console.error('Error deleting user:', error);
            showToast('Error deleting user', false);
        })
        .finally(() => {
            // Скрыть спиннер загрузки
            deleteButton.disabled = false;
            deleteButtonText.classList.remove('d-none');
            deleteButtonSpinner.classList.add('d-none');
        });
}

function updateUser() {
    const saveButton = document.getElementById('saveButton');
    const saveButtonText = document.getElementById('saveButtonText');
    const saveButtonSpinner = document.getElementById('saveButtonSpinner');

    // Показать спиннер загрузки
    saveButton.disabled = true;
    saveButtonText.classList.add('d-none');
    saveButtonSpinner.classList.remove('d-none');

    // Сбор данных формы
    const beingChangedUser = {
        id: document.getElementById('beingChangedUserId').value,
        firstname: document.getElementById('beingChangedUserFirstname').value,
        lastname: document.getElementById('beingChangedUserLastname').value,
        age: document.getElementById('beingChangedUserAge').value,
        username: document.getElementById('beingChangedUserUsername').value,
        roles: Array.from(document.getElementById('beingChangedUserRoles').selectedOptions)
            .map(option => ({ id: option.value, name: option.text }))
    };

    fetch('/api/admin/update_user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(beingChangedUser)
    })
        .then(response => {
            if (!response.ok) throw new Error('Save failed');
            return response.json();
        })
        .then(() => {
            editModal.hide();
            showToast('User saved successfully');
            loadUsers();
        })
        .catch(error => {
            console.error('Error saving user:', error);
            showToast('Error saving user', false);
        })
        .finally(() => {
            // Скрыть спиннер загрузки
            saveButton.disabled = false;
            saveButtonText.classList.remove('d-none');
            saveButtonSpinner.classList.add('d-none');
        });
}

function fillRoleSelect(roleSelect, user = null) {
    roleSelect.innerHTML = '';
    fetch('/api/admin/role_list')
        .then(response => {
            if (!response.ok) throw new Error('Roles not found');
            return response.json();
        })
        .then(responseData => Array.from(responseData)
            .forEach(role => {
                const roleOption = roleSelect.appendChild(document.createElement('option'));
                roleOption.value = role.id;
                roleOption.text = role.name;
                if (user != null) roleOption.selected = user.roles.some(userRole => userRole.id === role.id);
            })
        )
        .catch(error => {
            console.error('Error loading roles:', error);
            showToast('Error loading roles', false);
        });
}

// Показать уведомление
function showToast(message, isSuccess = true) {
    const toast = document.getElementById('successToast');
    const toastMessage = document.getElementById('toastMessage');

    toastMessage.textContent = message;

    if (isSuccess) {
        toast.classList.remove('bg-danger');
        toast.classList.add('bg-success');
    } else {
        toast.classList.remove('bg-success');
        toast.classList.add('bg-danger');
    }

    successToast.show();

    // Скрыть toast через 3 секунды
    setTimeout(() => {
        successToast.hide();
    }, 3000);
}
