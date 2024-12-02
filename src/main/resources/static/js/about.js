document.addEventListener("DOMContentLoaded", function () {
    // Function to fetch and display user data
    function fetchUserData(userId) {
        fetch(`/user/${userId}/about`)
            .then(response => response.json())
            .then(data => {
                document.getElementById("userId").innerText = userId;  // Display the user_id
                document.getElementById("fullName").innerText = data.fullName || 'N/A';
                document.getElementById("dob").innerText = data.dateOfBirth || 'N/A';
                document.getElementById("gender").innerText = data.gender || 'N/A';
                document.getElementById("civilStatus").innerText = data.civilStatus || 'N/A';
                document.getElementById("email").innerText = data.email || 'N/A';
                document.getElementById("phone").innerText = data.phone || 'N/A';
                document.getElementById("address").innerText = data.address || 'N/A';
                document.getElementById("validId").innerText = data.validId || 'N/A';
            })
            .catch(error => console.error("Error fetching user data:", error));
    }

    // Assuming the user ID is static or fetched dynamically from the session/auth context
    const userId = 1; // Replace with actual user ID logic, e.g., from session or JWT
    fetchUserData(userId);

    // Handle form submission to update user data
    const updateUserForm = document.getElementById("updateUserForm");
    if (updateUserForm) {
        updateUserForm.addEventListener("submit", function (event) {
            event.preventDefault();  // Prevent default form submission

            // Gather form data
            const updatedData = {
                fullName: document.getElementById("fullNameInput").value,
                dateOfBirth: document.getElementById("dobInput").value,
                gender: document.getElementById("genderInput").value,
                civilStatus: document.getElementById("civilStatusInput").value,
                email: document.getElementById("emailInput").value,
                phone: document.getElementById("phoneInput").value,
                address: document.getElementById("addressInput").value,
                validId: document.getElementById("validIdInput").value
            };

            // Send updated data to server
            fetch(`/user/${userId}/update`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("User information updated successfully!");
                        fetchUserData(userId); // Refresh user data after update
                    } else {
                        alert("Failed to update user information. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error updating user data:", error);
                    alert("An error occurred while updating user data.");
                });
        });
    }
});
