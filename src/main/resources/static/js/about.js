// about.js
document.addEventListener("DOMContentLoaded", function() {
    // Function to fetch and display user data
    function fetchUserData(userId) {
        // Fetch user data from the API (you can replace this URL with the actual endpoint)
        fetch(`/user/${userId}/about`)
            .then(response => response.json())
            .then(data => {
                // Populate the user information in the HTML
                document.getElementById("fullName").innerText = data.fullName;
                document.getElementById("dob").innerText = data.dateOfBirth;
                document.getElementById("gender").innerText = data.gender;
                document.getElementById("civilStatus").innerText = data.civilStatus;
                document.getElementById("email").innerText = data.email;
                document.getElementById("phone").innerText = data.phone;
                document.getElementById("address").innerText = data.address;
                document.getElementById("validId").innerText = data.validId; // Display valid ID info
            })
            .catch(error => console.error("Error fetching user data:", error));
    }

    // Assuming the user ID is static or fetched dynamically from the session/auth context
    const userId = 1; // Replace with the actual user ID logic
    fetchUserData(userId);
});
