document.addEventListener("DOMContentLoaded", function () {
    // Call function to load user data (assuming username is passed as a query parameter or dynamically set)
    const username = 'dew';  // Replace this with dynamic value if needed
    loadUserData(username);

    // Set up logout confirmation
    setupLogoutConfirmation();
});

// Function to load user data
function loadUserData(username) {
    fetch(`/user/${username}/user`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // Log the response data to the console
            if (data) {
                document.querySelector('.profile-info .user-name').textContent = data.fullName || 'N/A';
                document.querySelector('.profile-info .user-username').textContent = data.username || 'N/A';
                document.querySelector('.profile-info .user-email').textContent = data.email || 'N/A';
                document.querySelector('.profile-info .user-role').textContent = data.role || 'N/A';
            } else {
                console.log("User data not found");
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            alert('Failed to load user data.');
        });
}

// Function to set up logout confirmation
function setupLogoutConfirmation() {
    const logoutButton = document.querySelector(".logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", function (event) {
            event.preventDefault(); // Prevent the default logout action (navigate to /logout)
            
            // Show confirmation dialog
            const userConfirmed = confirm("Are you sure you want to logout?");
            if (userConfirmed) {
                window.location.href = '/logout'; // Redirect to logout URL if confirmed
            }
        });
    }
}
