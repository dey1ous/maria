document.addEventListener("DOMContentLoaded", function() {
    // Function to fetch and display user data
    function fetchUserData(userId) {
        // Fetch user data from the API (replace this with the actual endpoint)
        fetch(`/user/${userId}/about`)
            .then(response => response.json())
            .then(data => {
                // Populate the user information in the HTML
                document.getElementById("fullName").innerText = data.fullName || 'N/A';
                document.getElementById("dob").innerText = data.dateOfBirth || 'N/A';
                document.getElementById("gender").innerText = data.gender || 'N/A';
                document.getElementById("civilStatus").innerText = data.civilStatus || 'N/A';
                document.getElementById("email").innerText = data.email || 'N/A';
                document.getElementById("phone").innerText = data.phone || 'N/A';
                document.getElementById("address").innerText = data.address || 'N/A';
                document.getElementById("validId").innerText = data.validId || 'N/A';
                
                // Display the user_id
                document.getElementById("userId").innerText = userId;
            })
            .catch(error => console.error("Error fetching user data:", error));
    }

    // Assuming the user ID is static or fetched dynamically from the session/auth context
    const userId = 1; // Replace with actual user ID logic, e.g., from session or JWT
    fetchUserData(userId);
});
