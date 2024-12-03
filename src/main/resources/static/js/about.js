document.addEventListener("DOMContentLoaded", function () {
    function fetchUserData(userId) {
        fetch(`/user/${userId}/about`)  // Fixed URL
            .then(response => response.json())
            .then(data => {
                console.log(data);  // Log data for debugging
                document.getElementById("userId").innerText = userId;
                document.getElementById("fullName").innerText = data.fullName || 'N/A';
                document.getElementById("dob").innerText = data.dateOfBirth || 'N/A';
                document.getElementById("gender").innerText = data.gender || 'N/A';
                document.getElementById("civilStatus").innerText = data.civilStatus || 'N/A';
                document.getElementById("email").innerText = data.email || 'N/A';
                document.getElementById("phone").innerText = data.phone || 'N/A';
                document.getElementById("address").innerText = data.address || 'N/A';
            })
            .catch(error => console.error("Error fetching user data:", error));
    }

    const userId = 1; // Replace with actual logic to get userId
    fetchUserData(userId);
});
