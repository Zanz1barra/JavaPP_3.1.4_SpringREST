document.addEventListener('DOMContentLoaded', function () {
    if (location.pathname.startsWith('/admin')) {
        document.getElementById('adminNavLink').classList.add('active', 'bg-primary', 'text-white');
    }
    if (location.pathname.startsWith('/user')) {
        document.getElementById('userNavLink').classList.add('active', 'bg-primary', 'text-white');
    }
})
