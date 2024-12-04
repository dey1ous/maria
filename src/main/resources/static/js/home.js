document.addEventListener("DOMContentLoaded", function () {
    // Dynamically retrieve the username (this could come from various places)
    const username = getUsername(); // Call a function to dynamically get the username
    
    if (username) {
        // Fetch the current user data using the username as a query parameter
        fetch(`/user/current-user?username=${username}`)
            .then(response => response.json())
            .then(user => {
                loadUserData(user); // Load the user data once fetched
            })
            .catch(error => {
                console.error('Error fetching current user:', error);
                alert('Failed to load current user data.');
            });
    } else {
        console.error('Username is not available');
    }

});

// Function to dynamically get the username
function getUsername() {
    // Example 1: Get the username from a meta tag (or hidden field, etc.)
    const usernameMetaTag = document.querySelector('meta[name="username"]');
    if (usernameMetaTag) {
        return usernameMetaTag.getAttribute('content');
    }
    
    // Example 2: Get the username from a hidden field
    const usernameField = document.querySelector('#username');
    if (usernameField) {
        return usernameField.value;
    }

    // Example 3: Retrieve it from a global JavaScript variable (e.g., set in the page template)
    if (window.currentUsername) {
        return window.currentUsername;
    }

    // Example 4: You can also pass it via the URL or a cookie if needed
    // Example: fetch the username from the URL query parameter
    const params = new URLSearchParams(window.location.search);
    return params.get('username');  // Get 'username' from the URL query string
}

// Function to load user data
function loadUserData(user) {
    if (user) {
        document.querySelector('.profile-info .user-name').textContent = user.fullName || 'N/A';
        document.querySelector('.profile-info .user-username').textContent = user.username || 'N/A';
        document.querySelector('.profile-info .user-email').textContent = user.email || 'N/A';
        document.querySelector('.profile-info .user-role').textContent = user.role || 'N/A';
    }
}


    
