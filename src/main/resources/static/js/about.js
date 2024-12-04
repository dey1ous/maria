document.addEventListener("DOMContentLoaded", function () {
    function fetchUserData(id) {
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
                alert("Failed to load user data. Please try again later.");
            });
    }

    const id = 14; // Replace with the actual id you want to use
    fetchUserData(id);
});
