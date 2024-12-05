function loadHomeContent() {
    fetch('/user/details') // Replace with your actual API endpoint
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch user details");
            }
            return response.json();
        })
        .then(data => {
            // Populate dynamic user details
            const welcomeElement = document.querySelector('#welcome-message');
            const usernameElement = document.querySelector('#username');
            const userIdElement = document.querySelector('#user-id');

            if (welcomeElement) {
                welcomeElement.textContent = `Welcome, ${data.fullName || 'User'}!`;
            }
            if (usernameElement) {
                usernameElement.textContent = data.username || 'N/A';
            }
            if (userIdElement) {
                userIdElement.textContent = ` ${data.id || 'N/A'}`;
            }
        })
        .catch(error => console.error('Error fetching user details:', error));
}

// Call `loadHomeContent` when `#home` is loaded
window.addEventListener('hashchange', () => {
    if (window.location.hash === '#home') {
        loadHomeContent();
    }
});

// Call it on initial page load if hash is already #home
if (window.location.hash === '#home') {
    loadHomeContent();
}
