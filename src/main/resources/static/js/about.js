document.addEventListener("DOMContentLoaded", function () {
    // Function to dynamically get the user ID
    function getUserId() {
        const params = new URLSearchParams(window.location.search);
        const userId = params.get('id'); 
        return userId;
    }

    // Validate user ID
    const userId = getUserId();
    if (!userId) {
        alert("User ID is missing in the URL.");
        window.location.href = '/error'; // Redirect or show error message
        return;
    }

    // Fetch user data using the dynamic user ID
    function fetchUserData(id) {
        if (!id) {
            console.error("User ID is not available.");
            return;
        }

        document.getElementById("loadingMessage").style.display = 'block'; // Show loading message
        fetch(`/user/${id}/about`) // Use `id` in the URL
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data); // Log data for debugging
                document.getElementById("userId").innerText = id;
                document.getElementById("fullName").innerText = data.fullName || 'N/A';
                document.getElementById("dob").innerText = data.dateOfBirth || 'N/A';
                document.getElementById("gender").innerText = data.gender || 'N/A';
                document.getElementById("civilStatus").innerText = data.civilStatus || 'N/A';
                document.getElementById("email").innerText = data.email || 'N/A';
                document.getElementById("phone").innerText = data.phone || 'N/A';
                document.getElementById("address").innerText = data.address || 'N/A';
            })
            .catch(error => {
                console.error("Error fetching user data:", error);
                document.getElementById("userInfo").innerHTML = "<p>Failed to load user data. Please try again later.</p>";
            })
            .finally(() => {
                document.getElementById("loadingMessage").style.display = 'none'; // Hide loading message
            });
    }

    // Fetch user data with the retrieved user ID
    fetchUserData(userId);
});
