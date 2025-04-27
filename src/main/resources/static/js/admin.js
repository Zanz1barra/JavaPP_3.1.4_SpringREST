document.addEventListener('DOMContentLoaded', function () {
    loadUsers();
})

function loadUsers() {
    fetch('/admin/user_list')
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

                const deleteBtn = userRow
                    .appendChild(document.createElement('td'))
                    .appendChild(document.createElement('button'));
                deleteBtn.className = "btn btn-danger";
                deleteBtn.textContent = "Delete";
            });
        })
        .catch(error => {
            console.error('Error loading users:', error);
            // showToast('Error loading users', false);
        });
}