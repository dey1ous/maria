document.addEventListener("DOMContentLoaded", function () {
    // Load and display user data
    loadUserData();

    // Set up profile interaction
    setupProfileClick();
});


function loadUserData() {
    // Assuming an API endpoint '/api/user' that returns user data
    fetch('/api/user')
        .then(response => response.json())
        .then(data => {

            document.querySelector('.profile-info .user-name').textContent = data.fullName;
            document.querySelector('.profile-info .user-username').textContent = data.username;
            document.querySelector('.profile-info .user-email').textContent = data.email;
            document.querySelector('.profile-info .user-role').textContent = data.role;
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            alert('Failed to load user data.');
        });
}


function setupProfileClick() {
    const profileInfo = document.querySelector(".profile-info");
    if (profileInfo) {
        profileInfo.addEventListener("click", function () {
            alert("Profile clicked! Here you can add functionality to view or edit details.");
        });
    }
}

/**
 @param {Event} event 
 */
function confirmLogout(event) {
    const userConfirmed = confirm("Are you sure you want to logout?");
    if (!userConfirmed) {
        event.preventDefault(); 
    }
}

// Attach logout confirmation handler to the logout link
const logoutButton = document.querySelector(".logout-button");
if (logoutButton) {
    logoutButton.addEventListener("click", confirmLogout);
}