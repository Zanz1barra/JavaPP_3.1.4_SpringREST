document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/users/my_data')
        .then(response => {
            if (!response.ok) throw new Error('User data not found');
            return response.json();
        })
        .then(selfUserData => {
            const selfUserDataTableBody = document.getElementById('selfUserDataTableBody');
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.id;
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.firstname;
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.lastname;
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.age;
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.username;
            selfUserDataTableBody.appendChild(document.createElement('td')).textContent = selfUserData.roles.map(role => role.name).join(', ');
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showToast('Error loading user data', false);
        });
});
